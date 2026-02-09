/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.bootstrap.lazyloading;

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
import de.cuioss.tools.logging.CuiLogger;
import de.cuioss.tools.string.MoreStrings;
import de.cuioss.uimodel.nameprovider.IDisplayNameProvider;
import jakarta.el.MethodExpression;
import jakarta.faces.application.ResourceDependency;
import jakarta.faces.component.*;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.faces.event.ListenerFor;
import jakarta.faces.event.PostAddToViewEvent;
import jakarta.faces.event.PreRenderComponentEvent;
import lombok.experimental.Delegate;

import java.util.Optional;

/**
 * Container for content that loads asynchronously after initial page rendering.
 * Enhances page performance by deferring resource-intensive component loading.
 * 
 * <h3>Structure</h3>
 * <ul>
 *   <li>Waiting indicator during loading</li>
 *   <li>Notification area for status messages</li>
 *   <li>Content area for lazy-loaded components</li>
 * </ul>
 * 
 * <h3>Usage Modes</h3>
 * <ul>
 *   <li>Simple: Configure with component properties</li>
 *   <li>Model-based: Use {@link LazyLoadingModel} for behavior control</li>
 * </ul>
 *
 * <h3>Key Properties</h3>
 * <ul>
 *   <li>{@code initialized}: Whether content has been loaded</li>
 *   <li>{@code renderContent}: Whether to display child components</li>
 *   <li>{@code async}: Whether to load asynchronously</li>
 *   <li>{@code startInitialize}: Method called at start of loading</li>
 * </ul>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see LazyLoadingRenderer
 * @see LazyLoadingModel
 */
@ResourceDependency(library = "javascript.enabler", name = "enabler.lazyLoading.js", target = "head")
@FacesComponent(BootstrapFamily.LAZYLOADING_COMPONENT)
@ListenerFor(systemEventClass = PreRenderComponentEvent.class)
@ListenerFor(systemEventClass = PostAddToViewEvent.class)
public class LazyLoadingComponent extends UICommand implements ComponentBridge, NamingContainer {

    private static final CuiLogger LOGGER = new CuiLogger(LazyLoadingComponent.class);
    private static final String INITIALIZED_KEY = "initialized";

    private static final String CHILDREN_LOADED_KEY = "childrenLoaded";

    private static final String NOTIFICATION_BOX_VALUE_KEY = "notificationBoxValue";

    private static final String NOTIFICATION_BOX_STATE_KEY = "notificationBoxState";

    private static final String RENDER_CONTENT_KEY = "renderContent";

    private static final String VIEW_MODEL_KEY = "viewModel";

    private static final String ASYNC_KEY = "async";

    private static final String START_INITIALIZE_KEY = "startInitialize";

    private static final String WAITING_INDICATOR_STYLE_CLASS_KEY = "waitingIndicatorStyleClass";

    /**
     * Parameter ID suffix used to identify a content load request.
     */
    static final String ID_SUFFIX_IS_LOADED = "is_loaded";

    /**
     * Data attribute for identifying the notification box component.
     */
    static final String DATA_RESULT_NOTIFICATION_BOX = "data-resultNotificationBox";

    /**
     * Fixed ID for the waiting indicator component.
     */
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
     * Default constructor.
     * <p>
     * Initializes the component with the appropriate renderer type and
     * creates the necessary delegate objects for style handling, state management,
     * and behavior tracking.
     */
    public LazyLoadingComponent() {
        state = new CuiState(getStateHelper());
        super.setRendererType(BootstrapFamily.LAZYLOADING_RENDERER);
        ignoreAutoUpdateProvider = new IgnoreAutoUpdateProvider(this);
        oneTimeCheck = new OneTimeCheck(this);
        styleClassProvider = new ComponentStyleClassProviderImpl(this);
        styleAttributeProvider = new StyleAttributeProviderImpl(this);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Processes the component system events:
     * <ul>
     *   <li>{@link PreRenderComponentEvent}: Invokes the startInitialize method expression if present 
     *       and if the component hasn't been initialized yet</li>
     *   <li>{@link PostAddToViewEvent}: Creates the waiting indicator and notification box components</li>
     * </ul>
     */
    @Override
    public void processEvent(final ComponentSystemEvent event) {
        if (event instanceof PreRenderComponentEvent) {
            LOGGER.debug("Handling PreRenderComponentEvent %s before OneTimeCheck", event);
            if (!oneTimeCheck.readAndSetChecked()) {
                LOGGER.debug("Handling PreRenderComponentEvent %s after OneTimeCheck", event);
                var startInitialize = getStartInitialize();
                if (startInitialize.isPresent()) {
                    LOGGER.debug("Invoking startInitialize");
                    startInitialize.get().invoke(getFacesContext().getELContext(), new Object[]{});
                }
            }
        } else if (event instanceof PostAddToViewEvent) {
            LOGGER.debug("Handling PostAddToViewEvent %s", event);
            createWaitingIndicator();
            createNotificationBox();
        }
        super.processEvent(event);
    }

    /**
     * {@inheritDoc}
     * 
     * @return The component family, always {@link BootstrapFamily#COMPONENT_FAMILY}
     */
    @Override
    public String getFamily() {
        return BootstrapFamily.COMPONENT_FAMILY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StateHelper stateHelper() {
        return getStateHelper();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FacesContext facesContext() {
        return getFacesContext();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UIComponent facet(final String facetName) {
        return getFacet(facetName);
    }

    /**
     * Retrieves the method expression that should be executed once during the component initialization.
     * <p>
     * <strong>Note:</strong> Evaluation of the MethodExpression may already trigger executing the method!
     *
     * @return An {@link Optional} containing the startInitialize method expression if present
     */
    private Optional<MethodExpression> getStartInitialize() {
        return Optional.ofNullable((MethodExpression) getStateHelper().eval(START_INITIALIZE_KEY));
    }

    /**
     * Sets the method expression to be called once at the PreRenderViewEvent.
     * <p>
     * This method is typically used to initialize data needed for the lazy-loaded content.
     *
     * @param startInitialize The method expression to set
     */
    public void setStartInitialize(final MethodExpression startInitialize) {
        getStateHelper().put(START_INITIALIZE_KEY, startInitialize);
    }

    /**
     * Returns the view model that controls the component's behavior.
     *
     * @return The LazyLoadingModel instance or null if not set
     */
    public LazyLoadingModel getViewModel() {
        return state.get(VIEW_MODEL_KEY);
    }

    /**
     * Sets the view model that will control this component's behavior.
     * <p>
     * When a view model is set, its properties take precedence over the component's properties.
     *
     * @param viewModel The LazyLoadingModel to set
     */
    public void setViewModel(final LazyLoadingModel viewModel) {
        state.put(VIEW_MODEL_KEY, viewModel);
    }

    /**
     * Returns the value to be displayed in the notification box.
     *
     * @return The notification box display text provider
     */
    @SuppressWarnings("java:S1452") // Wildcard return type required by public API
    public IDisplayNameProvider<?> getNotificationBoxValue() {
        return state.get(NOTIFICATION_BOX_VALUE_KEY);
    }

    /**
     * Evaluates the notification box value to display, using the view model if available,
     * otherwise using the component's own value.
     *
     * @return The notification box value to be displayed
     */
    @SuppressWarnings("java:S1452") // Wildcard return type required by public API
    public IDisplayNameProvider<?> evaluateNotificationBoxValue() {
        if (null != getViewModel()) {
            return getViewModel().getNotificationBoxValue();
        }
        return getNotificationBoxValue();
    }

    /**
     * Sets the text to be displayed in the notification box.
     *
     * @param notificationBoxValue The notification box display text provider
     */
    public void setNotificationBoxValue(final IDisplayNameProvider<?> notificationBoxValue) {
        state.put(NOTIFICATION_BOX_VALUE_KEY, notificationBoxValue);
    }

    /**
     * Returns the contextual state of the notification box.
     *
     * @return The notification box state, defaults to {@link ContextState#WARNING} if not set
     */
    public ContextState getNotificationBoxState() {
        return state.get(NOTIFICATION_BOX_STATE_KEY, ContextState.WARNING);
    }

    /**
     * Evaluates the notification box state to use, using the view model if available,
     * otherwise using the component's own state.
     *
     * @return The notification box state to be used
     */
    public ContextState evaluateNotificationBoxState() {
        if (null != getViewModel()) {
            return getViewModel().getNotificationBoxState();
        }
        return getNotificationBoxState();
    }

    /**
     * Sets the contextual state for the notification box.
     *
     * @param notificationBoxState The contextual state to set
     */
    public void setNotificationBoxState(final ContextState notificationBoxState) {
        state.put(NOTIFICATION_BOX_STATE_KEY, notificationBoxState);
    }

    /**
     * Returns any additional style classes to be applied to the waiting indicator.
     *
     * @return The waiting indicator style class or null if not set
     */
    public String getWaitingIndicatorStyleClass() {
        return state.get(WAITING_INDICATOR_STYLE_CLASS_KEY);
    }

    /**
     * Sets additional style classes to be applied to the waiting indicator.
     *
     * @param waitingIndicatorStyleClass The style classes to set
     */
    public void setWaitingIndicatorStyleClass(final String waitingIndicatorStyleClass) {
        state.put(WAITING_INDICATOR_STYLE_CLASS_KEY, waitingIndicatorStyleClass);
    }

    /**
     * Returns whether the component has been initialized.
     *
     * @return {@code true} if the component has been initialized, otherwise {@code false}
     */
    public boolean isInitialized() {
        return state.getBoolean(INITIALIZED_KEY, false);
    }

    /**
     * Evaluates whether the component is not initialized, considering both the component
     * state and the view model if available.
     *
     * @return {@code true} if the component is not initialized, otherwise {@code false}
     */
    public boolean evaluateNotInitialized() {
        if (null != getViewModel()) {
            return !isInitialized() && !getViewModel().isInitialized();
        }
        return !isInitialized();
    }

    /**
     * Sets the initialization state of the component.
     *
     * @param initialized {@code true} to mark the component as initialized, {@code false} otherwise
     */
    public void setInitialized(final boolean initialized) {
        state.put(INITIALIZED_KEY, initialized);
    }

    /**
     * Returns whether the content should be loaded asynchronously.
     *
     * @return {@code true} if async loading is enabled, otherwise {@code false}
     */
    public boolean isAsync() {
        return state.getBoolean(ASYNC_KEY, false);
    }

    /**
     * Sets whether the content should be loaded asynchronously.
     *
     * @param async {@code true} to enable async loading, {@code false} otherwise
     */
    public void setAsync(final boolean async) {
        state.put(ASYNC_KEY, async);
    }

    /**
     * Returns whether the child content should be rendered.
     *
     * @return {@code true} if the content should be rendered, otherwise {@code false}
     */
    public boolean isRenderContent() {
        return state.getBoolean(RENDER_CONTENT_KEY, true);
    }

    /**
     * Sets whether the child content should be rendered.
     *
     * @param renderContent {@code true} to render the content, {@code false} otherwise
     */
    public void setRenderContent(final boolean renderContent) {
        state.put(RENDER_CONTENT_KEY, renderContent);
    }

    /**
     * Returns whether the children have been loaded.
     *
     * @return {@code true} if the children have been loaded, otherwise {@code false}
     */
    public boolean getChildrenLoaded() {
        return state.getBoolean(CHILDREN_LOADED_KEY);
    }

    /**
     * Sets whether the children have been loaded or should be loaded in the next request.
     *
     * @param childrenLoaded {@code true} when the children should be rendered in the next render phase
     */
    public void setChildrenLoaded(final boolean childrenLoaded) {
        state.put(CHILDREN_LOADED_KEY, childrenLoaded);
    }

    /**
     * Determines whether the waiting indicator should be rendered.
     * <p>
     * The indicator is shown when:
     * <ul>
     *   <li>The component is not initialized</li>
     *   <li>Children are not loaded yet</li>
     *   <li>The current request is not a content load request</li>
     * </ul>
     *
     * @param facesContext The current faces context
     * @return {@code true} if the waiting indicator should be rendered, otherwise {@code false}
     */
    public boolean shouldRenderWaitingIndicator(FacesContext facesContext) {
        return evaluateNotInitialized() && !getChildrenLoaded() && isNotContentLoadRequest(facesContext);
    }

    /**
     * Evaluates whether the content should be rendered, using the view model if available,
     * otherwise using the component's own setting.
     *
     * @return {@code true} if the content should be rendered, otherwise {@code false}
     */
    public boolean evaluateRenderContent() {
        if (null != getViewModel()) {
            return getViewModel().isRenderContent();
        }
        return isRenderContent();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Overrides the validator processing to skip validation if this is a content load request.
     * This prevents validation of uninitialized child components during the loading phase.
     */
    @Override
    public void processValidators(FacesContext context) {
        if (isNotContentLoadRequest(context)) {
            super.processValidators(context);
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Overrides the update processing to skip updates if this is a content load request.
     * This prevents processing model updates from uninitialized child components during 
     * the loading phase.
     */
    @Override
    public void processUpdates(FacesContext context) {
        if (isNotContentLoadRequest(context)) {
            super.processUpdates(context);
        }
    }

    /**
     * Checks if the current request was triggered by the ajax request to reload the
     * lazy loading content.
     * <p>
     * This is determined by checking for a specific request parameter that is added
     * by the JavaScript code when triggering the content load.
     *
     * @param context The FacesContext for the current request
     * @return {@code true} if this is NOT a content load request, {@code false} if it is
     */
    public boolean isNotContentLoadRequest(FacesContext context) {
        var componentWrapper = new ComponentWrapper<>(this);
        return !context.getExternalContext().getRequestParameterMap()
                .containsKey(componentWrapper.getSuffixedClientId(ID_SUFFIX_IS_LOADED));
    }

    /**
     * Creates a waiting indicator component if one does not already exist.
     * <p>
     * The indicator is added as a child component with any configured style classes.
     */
    void createWaitingIndicator() {
        if (retrieveWaitingIndicator().isEmpty()) {
            var waitingIndicator = WaitingIndicatorComponent.createComponent(getFacesContext());
            if (!MoreStrings.isEmpty(getWaitingIndicatorStyleClass())) {
                waitingIndicator.setStyleClass(getWaitingIndicatorStyleClass());
            }
            waitingIndicator.setId(WAITING_INDICATOR_ID);
            getChildren().add(waitingIndicator);
        }
    }

    /**
     * Creates a notification box component if one does not already exist.
     * <p>
     * The notification box is added as the first child component and is marked
     * with a data attribute for identification.
     */
    void createNotificationBox() {
        if (retrieveNotificationBox().isEmpty()) {
            var notificationBoxComponent = new NotificationBoxComponent();
            notificationBoxComponent.getPassThroughAttributes().put(DATA_RESULT_NOTIFICATION_BOX,
                    DATA_RESULT_NOTIFICATION_BOX);
            getChildren().addFirst(notificationBoxComponent);
        }
    }

    /**
     * Retrieves the waiting indicator component if it exists.
     *
     * @return An {@link Optional} containing the waiting indicator component if found
     */
    public Optional<UIComponent> retrieveWaitingIndicator() {
        return getChildren().stream().filter(child -> child.getClass() == WaitingIndicatorComponent.class).findFirst();
    }

    /**
     * Retrieves the notification box component if it exists.
     *
     * @return An {@link Optional} containing the notification box component if found
     */
    public Optional<UIComponent> retrieveNotificationBox() {
        return getChildren().stream()
                .filter(child -> child.getPassThroughAttributes().containsKey(DATA_RESULT_NOTIFICATION_BOX))
                .findFirst();
    }
}
