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
package de.cuioss.jsf.components.typewatch;

import jakarta.faces.component.behavior.AjaxBehavior;
import jakarta.faces.component.behavior.ClientBehaviorContext;

/**
 * <p>A specialized {@link AjaxBehavior} implementation that prevents the automatic generation
 * of JavaScript code in the rendered HTML output. Unlike the standard AjaxBehavior which
 * writes script blocks (e.g., onclick="jsf.ajax...") into the HTML, this implementation
 * returns null from {@link #getScript(ClientBehaviorContext)}.</p>
 * 
 * <p>This behavior is particularly useful in scenarios where:</p>
 * <ul>
 *   <li>The Ajax functionality needs to be triggered programmatically via JavaScript rather
 *       than through the standard event mechanism</li>
 *   <li>Custom JavaScript code needs to handle when and how the Ajax requests are sent</li>
 *   <li>The component requires full control over the client-side behavior</li>
 * </ul>
 * 
 * <p>While this behavior doesn't generate client-side script, it still registers server-side
 * handlers for Ajax requests, allowing them to be processed properly when manually triggered.</p>
 * 
 * <p>This class is primarily used by the {@link TypewatchComponent} to enable delayed Ajax
 * requests based on user typing patterns.</p>
 * 
 * @author Oliver Wolff
 * @since 1.0
 */
public class NoScriptAjaxBehavior extends AjaxBehavior {

    /**
     * {@inheritDoc}
     * 
     * <p>Overridden to return null, preventing the automatic generation of JavaScript
     * code in the rendered HTML. This allows the component to manually control when
     * and how Ajax requests are triggered.</p>
     * 
     * @param behaviorContext The client behavior context for this behavior
     * @return Always returns null to prevent automatic script generation
     */
    @Override
    public String getScript(final ClientBehaviorContext behaviorContext) {
        return null;
    }
}
