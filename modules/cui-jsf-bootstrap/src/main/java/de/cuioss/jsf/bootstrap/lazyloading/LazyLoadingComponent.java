/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.bootstrap.lazyloading;

import java.util.Optional;

import jakarta.el.MethodExpression;
import jakarta.faces.application.ResourceDependency;
import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.NamingContainer;
import jakarta.faces.component.StateHelper;
import jakarta.faces.component.UICommand;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.faces.event.ListenerFor;
import jakarta.faces.event.PostAddToViewEvent;
import jakarta.faces.event.PreRenderViewEvent;

import de.cuioss.jsf.api.components.css.ContextState;
import de.cuioss.jsf.api.components.model.lazyloading.LazyLoadingModel;
import de.cuioss.jsf.api.components.partial.ComponentBridge;
import de.cuioss.jsf.api.components.partial.ComponentStyleClassProviderImpl;
import de.cuioss.jsf.api.components.partial.IgnoreAutoUpdateProvider;
import de.cuioss.jsf.api.components.partial.StyleAttributeProviderImpl;
import de.cuioss.jsf.api.components.support.OneTimeCheck;
import de.cuioss.jsf.api.components.util.ComponentWrapper;
import de.cuioss.jsf.api.components.util.CuiState;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.notification.NotificationBoxComponent;
import de.cuioss.jsf.bootstrap.waitingindicator.WaitingIndicatorComponent;
import de.cuioss.tools.string.MoreStrings;
import de.cuioss.uimodel.nameprovider.IDisplayNameProvider;
import lombok.experimental.Delegate;

/**
 * Displays a content that should be loaded lazy after initial page rendering.
 * <p>
 * The initial page will display the waiting indicator and trigger an ajax
 * update of the content. This update will first call an ActionListener (if
 * defined) during Invoke Application phase, and then switch the waiting
 * indicator to be hidden and render the content.
 */
@ResourceDependency(library = "javascript.enabler", name = "enabler.lazyLoading.js", target = "head")
@FacesComponent(BootstrapFamily.LAZYLOADING_COMPONENT)
@ListenerFor(systemEventClass = PreRenderViewEvent.class)
@ListenerFor(systemEventClass = PostAddToViewEvent.class)
public class LazyLoadingComponent extends UICommand implements ComponentBridge, NamingContainer {

    private static final String INITIALIZED_KEY = "initialized";

    private static final String CHILDREN_LOADED_KEY = "childrenLoaded";

    private static final String NOTIFICATION_BOX_VALUE_KEY = "notificationBoxValue";

    private static final String NOTIFICATION_BOX_STATE_KEY = "notificationBoxState";

    private static final String RENDER_CONTENT_KEY = "renderContent";

    private static final String VIEW_MODEL_KEY = "viewModel";

    private static final String ASYNC_KEY = "async";

    private static final String START_INITIALIZE_KEY = "startInitialize";

    private static final String WAITING_INDICATOR_STYLE_CLASS_KEY = "waitingIndicatorStyleClass";

    static final String ID_SUFFIX_ISLOADED = "isloaded";

    static final String DATA_RESULT_NOTIFICATION_BOX = "data-resultNotificationBox";

    static final String WAITING_INDICATOR_ID = "waitingIndicator";

    @Delegate
    private final IgnoreAutoUpdateProvider ignoreAutoUpdateProvider;

    private final CuiState state;

    private final OneTimeCheck oneTimeCheck;

    @Delegate
    private final ComponentStyleClassProviderImpl styleClassProvider;

    @Delegate
    private final StyleAttributeProviderImpl styleAttributeProvider;

    /**
     *
     */
    public LazyLoadingComponent() {
        state = new CuiState(getStateHelper());
        super.setRendererType(BootstrapFamily.LAZYLOADING_RENDERER);
        ignoreAutoUpdateProvider = new IgnoreAutoUpdateProvider(this);
        oneTimeCheck = new OneTimeCheck(this);
        styleClassProvider = new ComponentStyleClassProviderImpl(this);
        styleAttributeProvider = new StyleAttributeProviderImpl(this);
    }

    @Override
    public void processEvent(final ComponentSystemEvent event) {
        if (event instanceof PreRenderViewEvent) {
            if (!oneTimeCheck.readAndSetChecked()) {
                var startInitialize = getStartInitialize();
                if (null != startInitialize) {
                    startInitialize.invoke(getFacesContext().getELContext(), new Object[] {});
                }
                if (!evaluateInitialized() && null != getViewModel()) {
                    super.addActionListener(getViewModel());
                }
            }
        } else if (event instanceof PostAddToViewEvent) {
            createWaitingIndicator();
            createNotificationBox();
        }
        super.processEvent(event);
    }

    @Override
    public String getFamily() {
        return BootstrapFamily.COMPONENT_FAMILY;
    }

    @Override
    public StateHelper stateHelper() {
        return getStateHelper();
    }

    @Override
    public FacesContext facesContext() {
        return getFacesContext();
    }

    @Override
    public UIComponent facet(final String facetName) {
        return getFacet(facetName);
    }

    /**
     * ATTENTION: Evaluation the MethodExpression may already trigger executing the
     * method!
     *
     * @return the startInitialize, a function to be called one time at
     *         PreRenderViewEvent
     */
    public MethodExpression getStartInitialize() {
        return (MethodExpression) getStateHelper().eval(START_INITIALIZE_KEY);
    }

    /**
     * @param startInitialize the startInitialize to set
     */
    public void setStartInitialize(final MethodExpression startInitialize) {
        getStateHelper().put(START_INITIALIZE_KEY, startInitialize);
    }

    /**
     * @return the viewModel
     */
    public LazyLoadingModel getViewModel() {
        return state.get(VIEW_MODEL_KEY);
    }

    /**
     * @param viewModel the viewModel to set
     */
    public void setViewModel(final LazyLoadingModel viewModel) {
        state.put(VIEW_MODEL_KEY, viewModel);
    }

    /**
     * @return the notificationBoxValue
     */
    public IDisplayNameProvider<?> getNotificationBoxValue() {
        return state.get(NOTIFICATION_BOX_VALUE_KEY);
    }

    public IDisplayNameProvider<?> evaluateNotificationBoxValue() {
        if (null != getViewModel()) {
            return getViewModel().getNotificationBoxValue();
        }
        return getNotificationBoxValue();
    }

    /**
     * @param notificationBoxValue the notificationBoxValue to set
     */
    public void setNotificationBoxValue(final IDisplayNameProvider<?> notificationBoxValue) {
        state.put(NOTIFICATION_BOX_VALUE_KEY, notificationBoxValue);
    }

    /**
     * @return the notificationBoxState
     */
    public ContextState getNotificationBoxState() {
        return state.get(NOTIFICATION_BOX_STATE_KEY, ContextState.WARNING);
    }

    public ContextState evaluateNotificationBoxState() {
        if (null != getViewModel()) {
            return getViewModel().getNotificationBoxState();
        }
        return getNotificationBoxState();
    }

    /**
     * @param notificationBoxState the notificationBoxState to set
     */
    public void setNotificationBoxState(final ContextState notificationBoxState) {
        state.put(NOTIFICATION_BOX_STATE_KEY, notificationBoxState);
    }

    /**
     * @return the waitingIndicatorStyleClass
     */
    public String getWaitingIndicatorStyleClass() {
        return state.get(WAITING_INDICATOR_STYLE_CLASS_KEY);
    }

    /**
     * @param waitingIndicatorStyleClass the waitingIndicatorStyleClass to set
     */
    public void setWaitingIndicatorStyleClass(final String waitingIndicatorStyleClass) {
        state.put(WAITING_INDICATOR_STYLE_CLASS_KEY, waitingIndicatorStyleClass);
    }

    /**
     * @return initialized
     */
    public boolean isInitialized() {
        return state.getBoolean(INITIALIZED_KEY, false);
    }

    public boolean evaluateInitialized() {
        if (null != getViewModel()) {
            return isInitialized() || getViewModel().isInitialized();
        }
        return isInitialized();
    }

    /**
     * @param initialized the value to set
     */
    public void setInitialized(final boolean initialized) {
        state.put(INITIALIZED_KEY, initialized);
    }

    /**
     * @return async
     */
    public boolean isAsync() {
        return state.getBoolean(ASYNC_KEY, false);
    }

    /**
     * @param async the value to set
     */
    public void setAsync(final boolean async) {
        state.put(ASYNC_KEY, async);
    }

    /**
     * @return renderContent
     */
    public boolean isRenderContent() {
        return state.getBoolean(RENDER_CONTENT_KEY, true);
    }

    /**
     * @param renderContent the value to set
     */
    public void setRenderContent(final boolean renderContent) {
        state.put(RENDER_CONTENT_KEY, renderContent);
    }

    /**
     * @return boolean indicating whether the children are rendered or should be
     *         rendered
     */
    public boolean getChildrenLoaded() {
        return state.getBoolean(CHILDREN_LOADED_KEY);
    }

    /**
     * Remember that childs were already rendered or trigger loading in the next
     * request.
     *
     * @param childrenLoaded true when the children should be rendered in the next
     *                       render phase.
     */
    public void setChildrenLoaded(final boolean childrenLoaded) {
        state.put(CHILDREN_LOADED_KEY, childrenLoaded);
    }

    public boolean shouldRenderWaitingIndicator(FacesContext facesContext) {
        return !evaluateInitialized() && !getChildrenLoaded() && !isContentLoadRequest(facesContext);
    }

    public boolean evaluateRenderContent() {
        if (null != getViewModel()) {
            return getViewModel().isRenderContent();
        }
        return isRenderContent();
    }

    /**
     * Stop processing if the children are not initialized
     *
     * @param context the FacesContext
     */
    @Override
    public void processValidators(FacesContext context) {
        if (!isContentLoadRequest(context)) {
            super.processValidators(context);
        }
    }

    /**
     * Stop processing if the children are not initialized
     *
     * @param context the FacesContext
     */
    @Override
    public void processUpdates(FacesContext context) {
        if (!isContentLoadRequest(context)) {
            super.processUpdates(context);
        }
    }

    /**
     * Check if the current request was triggered by the ajax request to reload the
     * lazy loading content
     *
     * @param context the FacesContext
     * @return true if the current request was triggered by the ajax request to
     *         reload the lazy loading content
     */
    public boolean isContentLoadRequest(FacesContext context) {
        var componentWrapper = new ComponentWrapper<>(this);
        return context.getExternalContext().getRequestParameterMap()
                .containsKey(componentWrapper.getSuffixedClientId(ID_SUFFIX_ISLOADED));
    }

    /**
     * Create a waiting indicator based on the composite component if not already
     * existing.
     *
     * @return the waiting indicator as {@link UIComponent}.
     */
    UIComponent createWaitingIndicator() {
        var result = retrieveWaitingIndicator();

        if (result.isEmpty()) {
            var waitingIndicator = WaitingIndicatorComponent.createComponent(FacesContext.getCurrentInstance());
            if (!MoreStrings.isEmpty(getWaitingIndicatorStyleClass())) {
                waitingIndicator.setStyleClass(getWaitingIndicatorStyleClass());
            }
            waitingIndicator.setId(WAITING_INDICATOR_ID);
            getChildren().add(waitingIndicator);
            return waitingIndicator;
        }
        return result.get();
    }

    /**
     * Create a result notification box if not already existing.
     *
     * @return the notification box as {@link UIComponent}.
     */
    NotificationBoxComponent createNotificationBox() {
        var result = retrieveNotificationBox();
        if (result.isEmpty()) {
            var notificationBoxComponent = new NotificationBoxComponent();
            notificationBoxComponent.getPassThroughAttributes().put(DATA_RESULT_NOTIFICATION_BOX,
                    DATA_RESULT_NOTIFICATION_BOX);
            getChildren().add(0, notificationBoxComponent);
            return notificationBoxComponent;
        }
        return (NotificationBoxComponent) result.get();
    }

    public Optional<UIComponent> retrieveWaitingIndicator() {
        return getChildren().stream().filter(child -> child.getClass() == WaitingIndicatorComponent.class).findFirst();

    }

    public Optional<UIComponent> retrieveNotificationBox() {
        return getChildren().stream()
                .filter(child -> child.getPassThroughAttributes().containsKey(DATA_RESULT_NOTIFICATION_BOX))
                .findFirst();
    }
}
