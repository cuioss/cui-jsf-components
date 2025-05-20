/**
 * <h2>Component Events Package</h2>
 * <p>
 * Provides reusable custom event implementations that can be used across different
 * JSF components in the CUI component library. These events extend the standard
 * JSF event system with specialized functionality needed by CUI components.
 * </p>
 * 
 * <h3>Key Components</h3>
 * <ul>
 *   <li>{@link de.cuioss.jsf.api.components.events.ModelPayloadEvent} - An event that carries a serializable payload model</li>
 * </ul>
 * 
 * <h3>Usage Patterns</h3>
 * <p>
 * Events in this package typically follow these usage patterns:
 * </p>
 * <ul>
 *   <li>Component produces events during specific lifecycle phases or user interactions</li>
 *   <li>Events carry relevant data as payload to communicate state changes</li>
 *   <li>Event processing is typically handled in component implementations directly rather than through the standard JSF listener mechanism</li>
 * </ul>
 * 
 * <p>
 * Classes in this package are generally serializable to comply with JSF state
 * management requirements and are designed to be used within the context of
 * the JSF request processing lifecycle.
 * </p>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see jakarta.faces.event.FacesEvent
 * @see jakarta.faces.component.UIComponent#queueEvent(jakarta.faces.event.FacesEvent)
 */
package de.cuioss.jsf.api.components.events;
