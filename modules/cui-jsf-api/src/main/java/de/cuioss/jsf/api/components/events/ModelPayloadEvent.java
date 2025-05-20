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
package de.cuioss.jsf.api.components.events;

import jakarta.faces.component.UIComponent;
import jakarta.faces.event.FacesEvent;
import jakarta.faces.event.FacesListener;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

/**
 * A specialized {@link FacesEvent} that carries a serializable payload model.
 * <p>
 * This event is designed to transport model data within the JSF event system,
 * allowing components to communicate with each other by passing data objects.
 * It is particularly useful for custom components that need to emit events with 
 * associated data, such as the dispose events in tag components.
 * </p>
 * <p>
 * The event does not support standard JSF listener processing and will throw
 * an {@link UnsupportedOperationException} if {@link #processListener(FacesListener)}
 * is called. It is intended to be handled through custom event processing mechanisms.
 * </p>
 * <p>
 * Usage example:
 * </p>
 * <pre>
 * // Creating and queueing the event
 * Serializable modelData = new MyDataObject();
 * UIComponent component = getCurrentComponent();
 * component.queueEvent(new ModelPayloadEvent(component, modelData));
 * 
 * // Later in the event processing phase
 * public void processEvent(ComponentSystemEvent event) {
 *     if (event.getComponent() == this &amp;&amp; event instanceof ModelPayloadEvent) {
 *         ModelPayloadEvent modelEvent = (ModelPayloadEvent) event;
 *         MyDataObject data = (MyDataObject) modelEvent.getModel();
 *         // Process the data
 *     }
 * }
 * </pre>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see FacesEvent
 */
public class ModelPayloadEvent extends FacesEvent {

    @Serial
    private static final long serialVersionUID = 7452809204723547999L;

    /**
     * The payload model carried by this event.
     * <p>
     * This field stores the data object that is transported by the event.
     * It must be serializable to ensure proper state saving in the JSF lifecycle.
     * </p>
     */
    @Getter
    private final Serializable model;

    /**
     * Creates a new ModelPayloadEvent with the specified component and payload data.
     * <p>
     * The component should be the one that is firing the event, and the payload
     * can be any serializable data object that needs to be transported.
     * </p>
     *
     * @param component the UIComponent firing this event, must not be null
     * @param payload the serializable data to be carried by this event
     */
    public ModelPayloadEvent(UIComponent component, Serializable payload) {
        super(component);
        model = payload;
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation always returns false as this event uses a custom
     * processing mechanism rather than the standard JSF listener approach.
     * </p>
     * 
     * @param listener the FacesListener to check
     * @return always returns false
     */
    @Override
    public boolean isAppropriateListener(FacesListener listener) {
        return false;
    }

    /**
     * {@inheritDoc}
     * <p>
     * This method is not supported by ModelPayloadEvent as it uses a custom
     * processing mechanism.
     * </p>
     * 
     * @param listener the FacesListener to process
     * @throws UnsupportedOperationException always thrown as this operation is not supported
     */
    @Override
    public void processListener(FacesListener listener) {
        throw new UnsupportedOperationException();
    }
}
