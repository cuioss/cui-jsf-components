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
