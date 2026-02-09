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
package de.cuioss.jsf.components.typewatch;

import de.cuioss.jsf.api.components.decorator.AbstractParentDecorator;
import de.cuioss.jsf.api.components.partial.AjaxProvider;
import de.cuioss.jsf.api.components.support.OneTimeCheck;
import de.cuioss.jsf.api.components.util.ComponentModifier;
import de.cuioss.jsf.api.components.util.CuiState;
import de.cuioss.jsf.components.CuiFamily;
import jakarta.el.MethodExpression;
import jakarta.faces.application.ResourceDependency;
import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.behavior.ClientBehaviorHolder;
import lombok.ToString;
import lombok.experimental.Delegate;

import java.util.Map;

/**
 * <p>A decorator component that adds "typewatch" functionality to input components,
 * allowing the application to detect when a user has stopped typing in a text field
 * and then trigger an action. This helps to reduce unnecessary server requests while
 * the user is still actively typing.</p>
 * 
 * <p>The component uses the jQuery Typewatch plugin under the hood to monitor user input
 * and execute a configured action (typically an Ajax call) after the user has paused typing
 * for a specified period.</p>
 * 
 * <p>Key features include:</p>
 * <ul>
 *   <li>Configurable wait time before triggering the action</li>
 *   <li>Option to highlight the field during typing</li>
 *   <li>Ability to set a minimum number of characters before triggering the action</li>
 *   <li>Full Ajax support with configurable execute and render targets</li>
 * </ul>
 *
 * <h2>Usage Example</h2>
 *
 * <pre>
 * &lt;h:inputText id="searchInput" value="#{bean.searchTerm}"&gt;
 *   &lt;cui:typewatch wait="500" 
 *                 captureLength="3"
 *                 listener="#{bean.search}"
 *                 render="searchResults"
 *                 execute="@this" /&gt;
 * &lt;/h:inputText&gt;
 * </pre>
 * 
 * <p>In this example, the search method will be called 500ms after the user stops typing,
 * but only if at least 3 characters have been entered.</p>
 * 
 * <h2>Attributes</h2>
 * 
 * <h3>wait</h3>
 * <p>The time in milliseconds to wait after the user has stopped typing before
 * triggering the action. Default depends on the jQuery Typewatch plugin (typically 750ms).</p>
 * 
 * <h3>highlight</h3>
 * <p>If true, the input field will be highlighted while the user is typing.
 * Default is false.</p>
 * 
 * <h3>captureLength</h3>
 * <p>The minimum number of characters that must be typed before
 * triggering the action. Default depends on the jQuery Typewatch plugin (typically 2).</p>
 * 
 * <h3>allowSubmit</h3>
 * <p>If true, allows regular form submission while typewatch is active.
 * Default is false.</p>
 * 
 * <h3>listener</h3>
 * <p>The method expression to be executed when the typewatch is triggered.
 * This is typically a bean method that performs an action such as searching.</p>
 * 
 * <p>The component also supports all standard Ajax attributes such as execute, render, etc.</p>
 *
 * @author Matthias Walliczek
 * @since 1.0
 */
@ResourceDependency(library = "thirdparty.js", name = "jquery.typewatch.js", target = "head")
@ResourceDependency(library = "javascript.enabler", name = "enabler.typewatch.js", target = "head")
@FacesComponent(CuiFamily.TYPEWATCH_COMPONENT)
@ToString(doNotUseGetters = true)
public class TypewatchComponent extends AbstractParentDecorator {

    private static final String LISTENER_KEY = "listener";
    private static final String ALLOW_SUBMIT_KEY = "allowSubmit";
    private static final String WAIT_KEY = "wait";
    private static final String HIGHLIGHT_KEY = "highlight";
    private static final String CAPTURE_LENGTH_KEY = "captureLength";
    private static final String DATA_TYPEWATCH_PREFIX = "data-typewatch-";

    private final OneTimeCheck oneTimeCheck;

    private final CuiState state;

    @Delegate
    private final AjaxProvider ajaxProvider;

    /**
     * Default constructor that initializes the component with its required
     * dependencies and configures the Ajax provider with the appropriate data prefix.
     */
    public TypewatchComponent() {
        oneTimeCheck = new OneTimeCheck(this);
        state = new CuiState(getStateHelper());
        ajaxProvider = new AjaxProvider(this).ajaxDataPrefix(DATA_TYPEWATCH_PREFIX);
    }

    /**
     * {@inheritDoc}
     * 
     * <p>Decorates the parent component with typewatch functionality by:</p>
     * <ul>
     *   <li>Adding the necessary data attributes to enable typewatch</li>
     *   <li>Configuring the typewatch parameters (wait time, capture length, etc.)</li>
     *   <li>Setting up Ajax behavior with a non-script Ajax behavior to prevent automatic script generation</li>
     *   <li>Connecting the listener method expression to be called when typewatch is triggered</li>
     * </ul>
     * 
     * @param parent The component to be decorated
     * @throws IllegalArgumentException if the parent component does not implement ClientBehaviorHolder
     */
    @Override
    public void decorate(final ComponentModifier parent) {
        if (oneTimeCheck.readAndSetChecked()) {
            return;
        }

        final var ptAttributes = parent.getComponent().getPassThroughAttributes(true);

        ptAttributes.put("data-typewatch", "data-typewatch");

        addPassThroughAttribute(ptAttributes, "allowsubmit", isAllowSubmit());
        addPassThroughAttribute(ptAttributes, "wait", getWait());
        addPassThroughAttribute(ptAttributes, HIGHLIGHT_KEY, isHighlight());
        addPassThroughAttribute(ptAttributes, "capturelength", getCaptureLength());

        ptAttributes.putAll(ajaxProvider.resolveAjaxAttributesAsMap(parent.getComponent()));

        if (!(parent.getComponent() instanceof ClientBehaviorHolder)) {
            throw new IllegalArgumentException(parent.getComponent() + " does not implement ClientBehaviorHolder");
        }

        var ajaxBehavior = new NoScriptAjaxBehavior();
        ajaxBehavior.addAjaxBehaviorListener(event -> {
            var listener = getListener();
            if (null != listener) {
                listener.invoke(event.getFacesContext().getELContext(), null);
            }
        });

        ((ClientBehaviorHolder) parent.getComponent()).addClientBehavior("valueChange", ajaxBehavior);
    }

    /**
     * Helper method to add a pass-through attribute to the component's attribute map.
     * Only adds the attribute if the value is not null.
     *
     * @param ptAttributes The pass-through attributes map to add to
     * @param key The attribute key (will be prefixed with data-typewatch-)
     * @param value The attribute value, will only be added if not null
     */
    private void addPassThroughAttribute(final Map<String, Object> ptAttributes, final String key, final Object value) {
        if (null != value) {
            ptAttributes.put(DATA_TYPEWATCH_PREFIX + key, value);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFamily() {
        return CuiFamily.COMPONENT_FAMILY;
    }

    /**
     * Returns the listener method expression that will be executed when the typewatch is triggered.
     * 
     * <p><strong>Note:</strong> Evaluating the MethodExpression may already trigger executing the
     * method. Use with caution.</p>
     *
     * @return the listener method expression
     */
    public MethodExpression getListener() {
        return state.get(LISTENER_KEY);
    }

    /**
     * Sets the listener method expression to be executed when the typewatch is triggered.
     *
     * @param listener the method expression to be executed
     */
    public void setListener(final MethodExpression listener) {
        state.put(LISTENER_KEY, listener);
    }

    /**
     * Returns whether regular form submission is allowed while typewatch is active.
     *
     * @return true if form submission is allowed, false otherwise (default is false)
     */
    public boolean isAllowSubmit() {
        return state.getBoolean(ALLOW_SUBMIT_KEY, false);
    }

    /**
     * Sets whether regular form submission is allowed while typewatch is active.
     *
     * @param allowSubmit true to allow form submission, false to prevent it
     */
    public void setAllowSubmit(final boolean allowSubmit) {
        state.put(ALLOW_SUBMIT_KEY, Boolean.valueOf(allowSubmit));
    }

    /**
     * Returns the time in milliseconds to wait after the user has stopped typing
     * before triggering the action.
     *
     * @return the wait time in milliseconds, or null if not set (which will use the plugin default)
     */
    public Integer getWait() {
        return state.get(WAIT_KEY);
    }

    /**
     * Sets the time in milliseconds to wait after the user has stopped typing
     * before triggering the action.
     *
     * @param wait the wait time in milliseconds
     */
    public void setWait(final Integer wait) {
        state.put(WAIT_KEY, wait);
    }

    /**
     * Returns whether the input field should be highlighted while the user is typing.
     *
     * @return true if highlighting is enabled, false otherwise (default is false)
     */
    public boolean isHighlight() {
        return state.getBoolean(HIGHLIGHT_KEY, false);
    }

    /**
     * Sets whether the input field should be highlighted while the user is typing.
     *
     * @param highlight true to enable highlighting, false to disable it
     */
    public void setHighlight(final boolean highlight) {
        state.put(HIGHLIGHT_KEY, Boolean.valueOf(highlight));
    }

    /**
     * Returns the minimum number of characters that must be typed before
     * triggering the action.
     *
     * @return the minimum number of characters, or null if not set (which will use the plugin default)
     */
    public Integer getCaptureLength() {
        return state.get(CAPTURE_LENGTH_KEY);
    }

    /**
     * Sets the minimum number of characters that must be typed before
     * triggering the action.
     *
     * @param captureLength the minimum number of characters
     */
    public void setCaptureLength(final Integer captureLength) {
        state.put(CAPTURE_LENGTH_KEY, captureLength);
    }
}
