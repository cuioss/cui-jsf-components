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
package de.cuioss.jsf.bootstrap.tag;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;

import de.cuioss.jsf.api.components.JsfHtmlComponent;
import de.cuioss.jsf.api.components.base.BaseCuiInputComponent;
import de.cuioss.jsf.api.components.events.ModelPayloadEvent;
import de.cuioss.jsf.api.components.partial.*;
import de.cuioss.jsf.api.components.util.CuiState;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssCuiBootstrap;
import de.cuioss.jsf.bootstrap.button.CloseCommandButton;
import de.cuioss.jsf.bootstrap.common.logging.BootstrapLogMessages;
import de.cuioss.tools.logging.CuiLogger;
import jakarta.el.MethodExpression;
import jakarta.faces.application.ResourceDependency;
import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.component.behavior.ClientBehavior;
import jakarta.faces.component.behavior.ClientBehaviorHolder;
import jakarta.faces.event.*;
import lombok.experimental.Delegate;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Renders a styled tag component similar to tags in applications like JIRA.
 * Supports various states, sizes, and can be configured as disposable.
 * 
 * <h2>Attributes</h2>
 * <ul>
 * <li>{@link TitleProvider} - Title/tooltip capabilities</li>
 * <li>{@link ContextSizeProvider} - Tag size control</li>
 * <li>{@link ComponentStyleClassProvider} - Custom CSS classes</li>
 * <li>{@link StyleAttributeProvider} - Custom inline styles</li>
 * <li>{@link ContextStateProvider} - Visual state/appearance</li>
 * <li>{@link ContentProvider} - Text content</li>
 * <li>{@link ModelProvider} - Attached model object</li>
 * <li>disposable: Whether the tag can be removed</li>
 * <li>disposeListener: Method to handle tag removal events</li>
 * </ul>
 * 
 * <h2>Usage Examples</h2>
 * 
 * <p>Basic tag:</p>
 * <pre>
 * &lt;boot:tag contentValue="Feature" /&gt;
 * </pre>
 * 
 * <p>Tag with state and size:</p>
 * <pre>
 * &lt;boot:tag contentValue="Critical Bug" state="DANGER" size="LG" /&gt;
 * </pre>
 * 
 * <p>Disposable tag with listener:</p>
 * <pre>
 * &lt;boot:tag contentValue="#{item.name}" model="#{item}"
 *         disposable="true" disposeListener="#{bean.handleTagRemoved}" /&gt;
 * </pre>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
@FacesComponent(BootstrapFamily.TAG_COMPONENT)
@ListenerFor(systemEventClass = PostAddToViewEvent.class)
@ResourceDependency(library = "javascript.enabler", name = "enabler.tagdispose.js", target = "head")
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public class TagComponent extends BaseCuiInputComponent
        implements TitleProvider, ClientBehaviorHolder, ValueChangeListener {

    private static final CuiLogger LOGGER = new CuiLogger(TagComponent.class);

    /**
     * Suffix for clientId
     */
    static final String DISPOSE_INFO_SUFFIX = "disposedInfo";

    /**
     * ClientBehaviorHolder.
     */
    private static final String CLICK = "click";

    private static final List<String> EVENT_NAMES = immutableList(CLICK);

    /**
     * Component specific Keys.
     */
    private static final String DISPOSABLE_KEY = "disposable";

    /**
     * Partial elements.
     */
    @Delegate
    private final TitleProvider titleProvider;

    @Delegate
    private final ContentProvider contentProvider;

    @Delegate
    private final ContextSizeProvider contextSizeProvider;

    @Delegate
    private final ContextStateProvider contextStateProvider;

    @Delegate
    private final ModelProvider modelProvider;

    private final CuiState state;

    /**
     * Constructs a new TagComponent and initializes all required providers.
     * Sets the appropriate renderer type and initializes the component state.
     */
    public TagComponent() {
        super.setRendererType(BootstrapFamily.TAG_COMPONENT_RENDERER);
        titleProvider = new TitleProviderImpl(this);
        contentProvider = new ContentProvider(this);
        contextSizeProvider = new ContextSizeProvider(this);
        contextStateProvider = new ContextStateProvider(this);
        modelProvider = new ModelProvider(this);
        state = new CuiState(getStateHelper());
    }

    /**
     * Processes component system events. For {@link PostAddToViewEvent}, ensures that
     * the close button and dispose value holder are properly initialized.
     *
     * @param event the component system event being processed
     */
    @Override
    public void processEvent(final ComponentSystemEvent event) {
        if (event instanceof PostAddToViewEvent) {
            accessCloseButton();
            accessDisposeValueHolder();
        }
        super.processEvent(event);
    }

    /**
     * Accesses or creates the hidden input component used to track the disposed state.
     * This input is only created if the tag is disposable.
     *
     * @return an Optional containing the UIInput if the tag is disposable, or empty Optional otherwise
     */
    private Optional<UIInput> accessDisposeValueHolder() {

        if (!isDisposable()) {
            return Optional.empty();
        }

        final var hiddenInput = getChildren().stream().filter(UIInput.class::isInstance).findFirst();

        if (hiddenInput.isPresent()) {
            return Optional.of((UIInput) hiddenInput.get());
        }

        final UIInput newCreatedHiddenInput = JsfHtmlComponent.HIDDEN.component(getFacesContext());

        newCreatedHiddenInput.setId(DISPOSE_INFO_SUFFIX);
        newCreatedHiddenInput.setValue("false");
        newCreatedHiddenInput.addValueChangeListener(this);
        getChildren().add(newCreatedHiddenInput);

        return Optional.of(newCreatedHiddenInput);
    }

    /**
     * Accesses or creates the close button component for disposable tags.
     * This button is only created if the tag is disposable.
     *
     * @return an Optional containing the CloseCommandButton if the tag is disposable, or empty Optional otherwise
     */
    private Optional<CloseCommandButton> accessCloseButton() {

        if (!isDisposable()) {
            return Optional.empty();
        }

        final var found = getChildren().stream().filter(CloseCommandButton.class::isInstance)
                .findFirst();

        if (found.isPresent()) {
            return Optional.of((CloseCommandButton) found.get());
        }
        final var button = CloseCommandButton.createComponent(getFacesContext());
        button.addClientBehavior(CLICK, new DisposeBehavior());
        button.setId("closeButton");
        button.setStyleClass(CssCuiBootstrap.TAG_DISPOSE_BUTTON.getStyleClass());
        getChildren().add(button);

        return Optional.of(button);
    }

    /**
     * Adds a client behavior to the tag's close button. This is only applicable
     * if the tag is disposable.
     *
     * @param eventName the name of the client event to attach the behavior to
     * @param behavior the ClientBehavior to add
     */
    @Override
    public void addClientBehavior(final String eventName, final ClientBehavior behavior) {
        final var button = accessCloseButton();
        LOGGER.debug("Adding client Behavior name='%s', type = behavior='%s'", eventName, behavior);
        if (button.isPresent()) {
            button.get().addClientBehavior(eventName, behavior);
        } else {
            LOGGER.warn(BootstrapLogMessages.WARN.INVALID_CLIENT_BEHAVIOR_CONFIG, getClientId());
        }
    }

    /**
     * Determines whether the tag is configured to be disposable.
     *
     * @return true if the tag can be disposed, false otherwise
     */
    public boolean isDisposable() {
        return state.getBoolean(DISPOSABLE_KEY, false);
    }

    /**
     * Sets whether the tag should be disposable.
     *
     * @param disposable true to make the tag disposable, false otherwise
     */
    public void setDisposable(final boolean disposable) {
        state.put(DISPOSABLE_KEY, disposable);
    }

    /**
     * Checks the internal state to determine if this tag has been disposed.
     *
     * @return boolean indicating whether the component has been disposed (true) or not (false)
     */
    private boolean isDisposed() {
        final var inputOption = accessDisposeValueHolder();
        return inputOption.map(uiInput -> Boolean.valueOf((String) uiInput.getValue())).orElse(false);
    }

    /**
     * Extends the standard {@link UIComponent#isRendered()} check to account for the tag's
     * disposed state. If the tag has been disposed, it will not be rendered.
     *
     * @return true if the component should be rendered (not disposed and parent check passes),
     *         false otherwise
     */
    @Override
    public boolean isRendered() {
        return super.isRendered() && !isDisposed();
    }

    /**
     * Returns the configured dispose listener method expression, if any.
     * <p>
     * <strong>Warning:</strong> Evaluating the returned MethodExpression may trigger
     * method execution.
     *
     * @return the dispose listener MethodExpression, or null if none is configured
     */
    public MethodExpression getDisposeListener() {
        return (MethodExpression) getStateHelper().eval(TagHandler.DISPOSE_LISTENER_NAME);
    }

    /**
     * Sets the dispose listener method expression that will be called when the tag
     * is disposed.
     *
     * @param disposeListener the MethodExpression to call on tag disposal
     */
    public void setDisposeListener(final MethodExpression disposeListener) {
        getStateHelper().put(TagHandler.DISPOSE_LISTENER_NAME, disposeListener);
    }

    /**
     * {@inheritDoc}
     * 
     * @return the list of supported event names for client behaviors
     */
    @Override
    public Collection<String> getEventNames() {
        return EVENT_NAMES;
    }

    /**
     * {@inheritDoc}
     * 
     * @return "click" as the default event name
     */
    @Override
    public String getDefaultEventName() {
        return CLICK;
    }

    /**
     * Broadcasts the given FacesEvent to registered listeners. For {@link ModelPayloadEvent}s,
     * if a dispose listener is configured, it will be invoked with the event.
     *
     * @param event the FacesEvent to broadcast
     */
    @Override
    public void broadcast(final FacesEvent event) {
        super.broadcast(event);
        final var disposeMethod = getDisposeListener();
        if (event instanceof ModelPayloadEvent && disposeMethod != null) {
            disposeMethod.invoke(getFacesContext().getELContext(), new Object[]{event});
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @return the component family identifier
     */
    @Override
    public String getFamily() {
        return BootstrapFamily.COMPONENT_FAMILY;
    }

    /**
     * Processes value change events from the hidden input that tracks the tag's disposed state.
     * When the value changes, a {@link ModelPayloadEvent} is queued with either the tag's model
     * or content as payload.
     *
     * @param event the ValueChangeEvent to process
     * @throws AbortProcessingException if an error occurs during processing
     */
    @Override
    public void processValueChange(final ValueChangeEvent event) throws AbortProcessingException {
        var payload = getModel();
        if (null == payload) {
            // Fallback to resolved content
            payload = resolveContent();
        }
        queueEvent(new ModelPayloadEvent(this, payload));
    }
}
