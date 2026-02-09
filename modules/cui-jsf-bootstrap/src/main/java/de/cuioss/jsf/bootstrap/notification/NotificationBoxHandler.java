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
package de.cuioss.jsf.bootstrap.notification;

import de.cuioss.jsf.api.components.events.ModelPayloadEvent;
import de.cuioss.jsf.api.components.util.MethodRule;
import jakarta.faces.view.facelets.ComponentConfig;
import jakarta.faces.view.facelets.ComponentHandler;
import jakarta.faces.view.facelets.MetaRule;
import jakarta.faces.view.facelets.MetaRuleset;

/**
 * <p>A specialized JSF component handler for {@link NotificationBoxComponent} that enables
 * method expression binding for notification dismiss events. This handler extends the
 * standard {@link ComponentHandler} by adding support for the "dismissListener" attribute,
 * which allows server-side handling of notification dismissal.</p>
 * 
 * <h2>Handler Features</h2>
 * <ul>
 *   <li>Provides proper EL method binding for dismiss event listeners</li>
 *   <li>Configures the correct method signature for dismiss listeners ({@code void method(ModelPayloadEvent)})</li>
 *   <li>Integrates with JSF's MetaRuleset system for declarative method binding</li>
 * </ul>
 * 
 * <h2>Usage in Facelet Tags</h2>
 * <p>This handler is automatically used when the notification box component is used in a facelet.
 * The handler enables the use of the dismissListener attribute:</p>
 * 
 * <pre>
 * &lt;boot:notificationBox dismissListener="#{bean.onNotificationDismissed}" ... /&gt;
 * </pre>
 * 
 * <h2>Listener Method Requirements</h2>
 * <p>The dismiss listener method must have the following signature:</p>
 * 
 * <pre>
 * public void methodName(de.cuioss.jsf.api.components.events.ModelPayloadEvent event) {
 *     // Handle the dismiss event
 *     Object payload = event.getPayload();
 *     // payload will be the notification's model or content
 * }
 * </pre>
 *
 * @author Matthias Walliczek
 * @see NotificationBoxComponent
 * @see ModelPayloadEvent
 */
public class NotificationBoxHandler extends ComponentHandler {

    /** Constant defining the name of the dismissListener attribute: "dismissListener". */
    public static final String DISMISS_LISTENER_NAME = "dismissListener";

    /**
     * MetaRule for the dismiss listener method that defines the method signature
     * as void return type with a single ModelPayloadEvent parameter.
     */
    private static final MetaRule DISMISS_METHOD = new MethodRule(DISMISS_LISTENER_NAME, null,
            new Class[]{ModelPayloadEvent.class});

    /**
     * Constructor that initializes the handler with the given component configuration.
     * Delegates to the superclass constructor to establish the basic component handling.
     *
     * @param config The component configuration for the facelet being built
     */
    public NotificationBoxHandler(final ComponentConfig config) {
        super(config);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Extends the standard MetaRuleset by adding support for the dismiss listener
     * method binding. This enables the component to accept and properly process
     * the dismissListener attribute with the correct method signature.
     * </p>
     * 
     * @param type The component type being processed
     * @return An enhanced MetaRuleset with support for the dismiss listener
     */
    @Override
    protected MetaRuleset createMetaRuleset(final Class type) { // Rawtype due to API
        final var metaRuleset = super.createMetaRuleset(type);

        metaRuleset.addRule(DISMISS_METHOD);

        return metaRuleset;
    }
}
