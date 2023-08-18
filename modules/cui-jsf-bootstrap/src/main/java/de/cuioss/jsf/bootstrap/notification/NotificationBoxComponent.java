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

import javax.el.MethodExpression;
import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.event.FacesEvent;

import de.cuioss.jsf.api.components.base.AbstractBaseCuiComponent;
import de.cuioss.jsf.api.components.events.ModelPayloadEvent;
import de.cuioss.jsf.api.components.partial.CloseButtonTitleProvider;
import de.cuioss.jsf.api.components.partial.ComponentStyleClassProvider;
import de.cuioss.jsf.api.components.partial.ContentProvider;
import de.cuioss.jsf.api.components.partial.ContextStateProvider;
import de.cuioss.jsf.api.components.partial.ModelProvider;
import de.cuioss.jsf.api.components.partial.StyleAttributeProvider;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import lombok.experimental.Delegate;

/**
 * <p>
 * Renders a bootstrap alert.
 * </p>
 * <h2>Attributes</h2>
 * <ul>
 * <li>{@link ComponentStyleClassProvider}</li>
 * <li>{@link StyleAttributeProvider}</li>
 * <li>{@link ContextStateProvider}</li>
 * <li>{@link ContentProvider}</li>
 * <li>{@link ModelProvider}</li>
 * <li>dismissible: Indicates whether the notification box can be
 * dismissed.</li>
 * <li>dismissListener: Method expression to listen to dismiss events. The
 * listener must be in the form of
 * {@code void methodName(de.cuioss.jsf.api.components.events.ModelPayloadEvent)}.
 * In case you set it to <code>true</code> you must provide the according model.
 * If not it falls back to #contentValue</li>
 * </ul>
 * <h2>Usage</h2>
 *
 * <pre>
 * &lt;boot:notificationBox state="WARN" /&gt;
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
     * Default constructor.
     */
    public NotificationBoxComponent() {
        super.setRendererType(BootstrapFamily.NOTIFICATION_BOX_RENDERER);
        contentProvider = new ContentProvider(this);
        contextStateProvider = new ContextStateProvider(this);
        modelProvider = new ModelProvider(this);
        closeButtonTitleProvider = new CloseButtonTitleProvider(this);
    }

    /**
     * Store flag for dismissible
     *
     * @param dismissible
     */
    public void setDismissible(final boolean dismissible) {
        getStateHelper().put(DISMISSIBLE_KEY, dismissible);
    }

    /**
     * @return stored flag. Default is {@code false}
     */
    public boolean isDismissible() {
        return (Boolean) getStateHelper().eval(DISMISSIBLE_KEY, Boolean.FALSE);
    }

    /**
     * @return boolean indicating whether the component has been dismissed. Defaults
     *         to <code>false</code>
     */
    public boolean isDismissed() {
        return (Boolean) getStateHelper().eval(DISMISSED_KEY, Boolean.FALSE);
    }

    /**
     * @param dismissed flag
     */
    public void setDismissed(final boolean dismissed) {
        getStateHelper().put(DISMISSED_KEY, dismissed);
    }

    /**
     * @return the dismissListener
     */
    public MethodExpression getDismissListener() {
        return (MethodExpression) getStateHelper().eval(NotificationBoxHandler.DISMISS_LISTENER_NAME);
    }

    /**
     * @param dismissListener the dismissListener to set
     */
    public void setDismissListener(final MethodExpression dismissListener) {
        getStateHelper().put(NotificationBoxHandler.DISMISS_LISTENER_NAME, dismissListener);
    }

    @Override
    public void broadcast(final FacesEvent event) {
        super.broadcast(event);
        final var dismissListener = getDismissListener();
        if (event instanceof ModelPayloadEvent && dismissListener != null) {
            dismissListener.invoke(getFacesContext().getELContext(), new Object[] { event });
        }
    }

    @Override
    public String getFamily() {
        return BootstrapFamily.COMPONENT_FAMILY;
    }
}
