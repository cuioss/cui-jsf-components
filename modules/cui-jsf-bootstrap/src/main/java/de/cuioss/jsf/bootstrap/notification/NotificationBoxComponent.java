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
package de.cuioss.jsf.bootstrap.notification;

import de.cuioss.jsf.api.components.base.AbstractBaseCuiComponent;
import de.cuioss.jsf.api.components.events.ModelPayloadEvent;
import de.cuioss.jsf.api.components.partial.*;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import jakarta.el.MethodExpression;
import jakarta.faces.application.ResourceDependency;
import jakarta.faces.component.FacesComponent;
import jakarta.faces.event.FacesEvent;
import lombok.experimental.Delegate;

/**
 * <p>A component that renders a Bootstrap-styled alert/notification box with various
 * contextual states and customization options. It provides support for dismissible
 * notifications with client-side and server-side handling of dismiss events.</p>
 * 
 * <h2>Component Features</h2>
 * <ul>
 *   <li>Bootstrap-compatible alert styling with contextual states (success, info, warning, danger)</li>
 *   <li>Optional dismissible alerts with close button and dismiss events</li>
 *   <li>Support for server-side dismiss event handling through method expressions</li>
 *   <li>Flexible content display through string values or complex models</li>
 *   <li>Customizable styling and appearance</li>
 * </ul>
 * 
 * <h2>Attributes</h2>
 * <ul>
 *   <li>{@link ComponentStyleClassProvider} - Provides CSS class customization</li>
 *   <li>{@link StyleAttributeProvider} - Provides inline style customization</li>
 *   <li>{@link ContextStateProvider} - Controls the contextual state (success, info, warning, danger)</li>
 *   <li>{@link ContentProvider} - Provides the notification content</li>
 *   <li>{@link ModelProvider} - Provides model support for more complex content</li>
 *   <li>{@link CloseButtonTitleProvider} - Controls the title/tooltip of the close button</li>
 *   <li><b>dismissible</b>: Boolean flag indicating whether the notification box can be
 *       dismissed by the user. Default is false.</li>
 *   <li><b>dismissed</b>: Boolean flag indicating the current dismissed state. Default is false.</li>
 *   <li><b>dismissListener</b>: Method expression to listen to dismiss events. The
 *       listener must be in the form of
 *       {@code void methodName(de.cuioss.jsf.api.components.events.ModelPayloadEvent)}.
 *       The model provided with the event will be the component's model if set.</li>
 * </ul>
 * 
 * <h2>Usage Examples</h2>
 * <p>Basic usage with context state:</p>
 * <pre>
 * &lt;boot:notificationBox state="WARN" contentValue="Warning message" /&gt;
 * </pre>
 * 
 * <p>Dismissible notification with listener:</p>
 * <pre>
 * &lt;boot:notificationBox state="INFO" contentValue="Information that can be dismissed" 
 *                       dismissible="true" dismissListener="#{bean.onDismiss}" /&gt;
 * </pre>
 * 
 * <p>Complex content with facets:</p>
 * <pre>
 * &lt;boot:notificationBox state="DANGER"&gt;
 *   &lt;f:facet name="header"&gt;Error&lt;/f:facet&gt;
 *   Critical error occurred. Please contact support.
 * &lt;/boot:notificationBox&gt;
 * </pre>
 *
 * @author Matthias Walliczek
 */
@ResourceDependency(library = "javascript.enabler", name = "enabler.notificationbox.js", target = "head")
@FacesComponent(BootstrapFamily.NOTIFICATION_BOX_COMPONENT)
public class NotificationBoxComponent extends AbstractBaseCuiComponent {

    private static final String DISMISSIBLE_KEY = "dismissible";
    private static final String DISMISSED_KEY = "dismissed";

    @Delegate
    private final ContentProvider contentProvider;

    @Delegate
    private final ContextStateProvider contextStateProvider;

    @Delegate
    private final ModelProvider modelProvider;

    @Delegate
    private final CloseButtonTitleProvider closeButtonTitleProvider;

    /**
     * Default constructor that initializes the component providers and sets the renderer type.
     * The component is configured with default providers for content, context state, model,
     * and close button title.
     */
    public NotificationBoxComponent() {
        super.setRendererType(BootstrapFamily.NOTIFICATION_BOX_RENDERER);
        contentProvider = new ContentProvider(this);
        contextStateProvider = new ContextStateProvider(this);
        modelProvider = new ModelProvider(this);
        closeButtonTitleProvider = new CloseButtonTitleProvider(this);
    }

    /**
     * Sets whether this notification box can be dismissed by the user.
     * When set to true, a close button will be displayed in the notification box.
     *
     * @param dismissible boolean flag indicating if the notification should be dismissible
     */
    public void setDismissible(final boolean dismissible) {
        getStateHelper().put(DISMISSIBLE_KEY, dismissible);
    }

    /**
     * Determines if this notification box can be dismissed by the user.
     * When true, a close button will be displayed in the notification box.
     *
     * @return boolean indicating whether the notification is dismissible. Default is {@code false}
     */
    public boolean isDismissible() {
        return (Boolean) getStateHelper().eval(DISMISSIBLE_KEY, Boolean.FALSE);
    }

    /**
     * Determines if this notification box has been dismissed by the user.
     * When a notification is dismissed, it will no longer be displayed.
     *
     * @return boolean indicating whether the component has been dismissed. Defaults
     *         to <code>false</code>
     */
    public boolean isDismissed() {
        return (Boolean) getStateHelper().eval(DISMISSED_KEY, Boolean.FALSE);
    }

    /**
     * Sets the dismissed state of this notification box.
     * Can be used to programmatically dismiss or restore a notification.
     *
     * @param dismissed boolean flag indicating the dismissed state
     */
    public void setDismissed(final boolean dismissed) {
        getStateHelper().put(DISMISSED_KEY, dismissed);
    }

    /**
     * Gets the method expression that will be invoked when this notification is dismissed.
     * The method expression must be in the form of:
     * {@code void methodName(de.cuioss.jsf.api.components.events.ModelPayloadEvent)}
     *
     * @return the dismissListener method expression, or null if not set
     */
    public MethodExpression getDismissListener() {
        return (MethodExpression) getStateHelper().eval(NotificationBoxHandler.DISMISS_LISTENER_NAME);
    }

    /**
     * Sets the method expression that will be invoked when this notification is dismissed.
     * The method expression must be in the form of:
     * {@code void methodName(de.cuioss.jsf.api.components.events.ModelPayloadEvent)}
     *
     * @param dismissListener the dismissListener method expression to set
     */
    public void setDismissListener(final MethodExpression dismissListener) {
        getStateHelper().put(NotificationBoxHandler.DISMISS_LISTENER_NAME, dismissListener);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Overridden to handle dismiss events by invoking the dismissListener method expression
     * if one is configured and the event is a {@link ModelPayloadEvent}.
     * </p>
     * 
     * @param event the FacesEvent to broadcast
     */
    @Override
    public void broadcast(final FacesEvent event) {
        super.broadcast(event);
        final var dismissListener = getDismissListener();
        if (event instanceof ModelPayloadEvent && dismissListener != null) {
            dismissListener.invoke(getFacesContext().getELContext(), new Object[]{event});
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @return the component family, always {@link BootstrapFamily#COMPONENT_FAMILY}
     */
    @Override
    public String getFamily() {
        return BootstrapFamily.COMPONENT_FAMILY;
    }
}
