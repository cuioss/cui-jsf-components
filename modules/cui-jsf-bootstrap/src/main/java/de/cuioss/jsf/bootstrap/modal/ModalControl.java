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
 * Enables a parent element like boot:button to control a
 * {@link ModalDialogComponent} to show or hide. It acts as an
 * {@link AbstractParentDecorator} saying it writes Html-5 attributes to the
 * parent components, the actual heavy-lifting is done by the javascript
 * 'javascript.enabler/enabler.modal.js'
 * <h2>Attributes</h2>
 * <ul>
 * <li>for: Defines the id of the modal to be opened. Is required. Results in a
 * corresponding 'data-modal-for'-attribute</li>
 * <li>event: Defines the javascript / dom event, the open call is attached to,
 * defaults to 'click'. Results in a corresponding
 * 'data-modal-event'-attribute</li>
 * <li>action: Defines the parameter to be passed to the modal() call, defaults
 * to 'show'. Results in a corresponding 'data-modal-action'-attribute</li>
 * </ul>
 * <h2>Usage</h2>
 *
 * <pre>
 * &lt;boot:button labelValue="Open"&gt;
 *   &lt;cui:modalControl for="dialogId"/&gt;
 * &lt;/boot:button&gt;
 * &lt;boot:modalDialog id="dialogId" headerValue="Dialog-Header"&gt;
 *   Some Dialog Content
 * &lt;/boot:modalDialog&gt;
 * </pre>
 * <p>
 * <em>Opening</em>: Use {@link ModalControl} attached to button or other
 * control for opening a concrete dialog.
 * </p>
 *
 * @author Oliver Wolff
 *
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
     *
     */
    public ModalControl() {
        state = new State(getStateHelper());
    }

    @Override
    public void decorate(final ComponentModifier parent) {
        var forId = getFor();
        checkArgument(!isEmpty(forId), "for must not be null nor empty");
        parent.addPassThrough(DATA_FOR, forId).addPassThrough(DATA_ACTION, getAction()).addPassThrough(DATA_EVENT,
                getEvent());
    }

    @Override
    public String getFamily() {
        return BootstrapFamily.COMPONENT_FAMILY;
    }

    /**
     * @param forId
     */
    public void setFor(final String forId) {
        state.put(FOR_KEY, forId);
    }

    /**
     * @return the for identifier to be rendered as {@link #DATA_FOR}
     */
    public String getFor() {
        return state.get(FOR_KEY);
    }

    /**
     * @param eventName
     */
    public void setEvent(final String eventName) {
        state.put(EVENT_KEY, eventName);
    }

    /**
     * @return the event to be rendered as {@link #DATA_EVENT}, defaults to
     *         {@link #DEFAULT_EVENT}
     */
    public String getEvent() {
        return state.get(EVENT_KEY, DEFAULT_EVENT);
    }

    /**
     * @param action
     */
    public void setAction(final String action) {
        state.put(ACTION_KEY, action);
    }

    /**
     * @return the action to be rendered as {@link #DATA_ACTION}, defaults to
     *         {@link #DEFAULT_ACTION}
     */
    public String getAction() {
        return state.get(ACTION_KEY, DEFAULT_ACTION);
    }
}
