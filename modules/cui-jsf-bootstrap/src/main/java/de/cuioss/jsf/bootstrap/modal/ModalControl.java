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
package de.cuioss.jsf.bootstrap.modal;

import static de.cuioss.tools.base.Preconditions.checkArgument;
import static de.cuioss.tools.string.MoreStrings.isEmpty;

import de.cuioss.jsf.api.components.decorator.AbstractParentDecorator;
import de.cuioss.jsf.api.components.util.ComponentModifier;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import jakarta.faces.application.ResourceDependency;
import jakarta.faces.component.FacesComponent;
import org.omnifaces.util.State;

/**
 * <p>The {@code ModalControl} component enables a parent element (like a button or link) to control
 * the display behavior of a {@link ModalDialogComponent}. It acts as an {@link AbstractParentDecorator}, 
 * which means it adds HTML5 data attributes to its parent component rather than rendering itself.</p>
 * 
 * <h2>Component Features</h2>
 * <p>This component adds the necessary JavaScript hooks to trigger modal dialog actions:</p>
 * <ul>
 *   <li>Show or hide modal dialogs based on user interaction</li>
 *   <li>Connect trigger elements (buttons, links) to specific modal dialogs</li>
 *   <li>Configure events that will trigger the modal action</li>
 * </ul>
 * 
 * <h2>Attributes</h2>
 * <ul>
 *   <li><b>for</b>: (Required) The ID of the modal dialog to be controlled. Results in a 
 *       corresponding 'data-modal-for' attribute on the parent component.</li>
 *   <li><b>event</b>: The JavaScript/DOM event the modal control is attached to. 
 *       Defaults to 'click'. Results in a 'data-modal-event' attribute.</li>
 *   <li><b>action</b>: The parameter to be passed to the modal() JavaScript call. 
 *       Defaults to 'show'. Results in a 'data-modal-action' attribute.</li>
 * </ul>
 * 
 * <h2>Usage</h2>
 * <p>Basic usage to create a button that opens a modal dialog:</p>
 *
 * <pre>
 * &lt;boot:button labelValue="Open"&gt;
 *   &lt;boot:modalControl for="dialogId"/&gt;
 * &lt;/boot:button&gt;
 * &lt;boot:modalDialog id="dialogId" headerValue="Dialog-Header"&gt;
 *   Some Dialog Content
 * &lt;/boot:modalDialog&gt;
 * </pre>
 * 
 * <h2>Advanced Usage</h2>
 * <p>Example with custom event and action:</p>
 * 
 * <pre>
 * &lt;boot:button labelValue="Hide Dialog" primary="true"&gt;
 *   &lt;boot:modalControl for="dialogId" action="hide" event="mouseover"/&gt;
 * &lt;/boot:button&gt;
 * </pre>
 * 
 * <h2>Implementation Details</h2>
 * <p>The component doesn't render any markup itself but adds data attributes to its parent
 * component. The actual behavior is provided by the JavaScript enabler.modal.js file which
 * handles the Bootstrap modal dialog interactions.</p>
 *
 * @author Oliver Wolff
 * @since 1.0
 * 
 * @see ModalDialogComponent
 */
@ResourceDependency(library = "javascript.enabler", name = "enabler.modal.js", target = "head")
@ResourceDependency(library = "thirdparty.js", name = "bootstrap.js")
@FacesComponent(BootstrapFamily.MODAL_DIALOG_CONTROL)
public class ModalControl extends AbstractParentDecorator {

    private static final String FOR_KEY = "for";
    private static final String EVENT_KEY = "event";
    private static final String ACTION_KEY = "action";

    private static final String DATA_BASE = "data-modal-";

    /** "data-modal-for" */
    public static final String DATA_FOR = DATA_BASE + FOR_KEY;

    /** "data-modal-event" */
    public static final String DATA_EVENT = DATA_BASE + EVENT_KEY;

    /** "data-modal-action" */
    public static final String DATA_ACTION = DATA_BASE + ACTION_KEY;

    /** The default Action for {@link #DATA_ACTION} */
    public static final String DEFAULT_ACTION = "show";

    /** The default event for {@link #DATA_EVENT} */
    public static final String DEFAULT_EVENT = "click";

    private final State state;

    /**
     * Default constructor that initializes the component's state.
     */
    public ModalControl() {
        state = new State(getStateHelper());
    }

    /**
     * {@inheritDoc}
     * <p>
     * Adds the required data attributes to the parent component for controlling the modal dialog:
     * <ul>
     *   <li>data-modal-for: The ID of the target modal dialog</li>
     *   <li>data-modal-action: The action to perform (show, hide, etc.)</li>
     *   <li>data-modal-event: The event that triggers the action</li>
     * </ul>
     * 
     * @param parent The parent component to modify
     * @throws IllegalArgumentException if the "for" attribute is not set
     */
    @Override
    public void decorate(final ComponentModifier parent) {
        var forId = getFor();
        checkArgument(!isEmpty(forId), "for must not be null nor empty");
        parent.addPassThrough(DATA_FOR, forId).addPassThrough(DATA_ACTION, getAction()).addPassThrough(DATA_EVENT,
                getEvent());
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
     * Sets the ID of the modal dialog that this control targets.
     *
     * @param forId the ID of the target modal dialog component
     */
    public void setFor(final String forId) {
        state.put(FOR_KEY, forId);
    }

    /**
     * Gets the ID of the modal dialog that this control targets.
     *
     * @return the ID of the target modal dialog component
     */
    public String getFor() {
        return state.get(FOR_KEY);
    }

    /**
     * Sets the DOM event that will trigger the modal action.
     *
     * @param eventName the name of the DOM event (e.g., "click", "mouseover")
     */
    public void setEvent(final String eventName) {
        state.put(EVENT_KEY, eventName);
    }

    /**
     * Gets the DOM event that will trigger the modal action.
     *
     * @return the event name to be rendered as {@link #DATA_EVENT}, defaults to
     *         {@link #DEFAULT_EVENT} ("click")
     */
    public String getEvent() {
        return state.get(EVENT_KEY, DEFAULT_EVENT);
    }

    /**
     * Sets the action to be performed on the modal dialog.
     *
     * @param action the action name (e.g., "show", "hide", "toggle")
     */
    public void setAction(final String action) {
        state.put(ACTION_KEY, action);
    }

    /**
     * Gets the action to be performed on the modal dialog.
     *
     * @return the action name to be rendered as {@link #DATA_ACTION}, defaults to
     *         {@link #DEFAULT_ACTION} ("show")
     */
    public String getAction() {
        return state.get(ACTION_KEY, DEFAULT_ACTION);
    }
}
