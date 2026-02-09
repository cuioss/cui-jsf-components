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
 * <h2>Result Content Component Models Package</h2>
 * <p>
 * Provides interfaces and classes for handling operation results, error states,
 * and user feedback in JSF components. This package enables standardized presentation
 * of success and error states, with appropriate visual feedback and logging.
 * </p>
 * 
 * <h3>Key Components</h3>
 * <ul>
 *   <li>{@link de.cuioss.jsf.api.components.model.result_content.ErrorController} - Interface for controlling message display and content rendering</li>
 *   <li>{@link de.cuioss.jsf.api.components.model.result_content.ResultErrorHandler} - Handler for processing result objects</li>
 *   <li>{@link de.cuioss.jsf.api.components.model.result_content.ResultContent} - Interface for components that render result content</li>
 *   <li>{@link de.cuioss.jsf.api.components.model.result_content.NotificationBoxMessage} - Model for notification messages</li>
 * </ul>
 * 
 * <h3>Integration with Result Objects</h3>
 * <p>
 * This package works closely with the {@link de.cuioss.uimodel.result.ResultObject} framework,
 * providing JSF-specific handling for result states, error details, and user notifications.
 * The combination enables a consistent approach to error handling across the application.
 * </p>
 * 
 * <h3>Message Types</h3>
 * <p>
 * The framework supports several types of user feedback:
 * </p>
 * <ul>
 *   <li><strong>Notification Boxes</strong>: Component-specific messages displayed inline</li>
 *   <li><strong>Faces Messages</strong>: Standard JSF messages for global notifications</li>
 *   <li><strong>Sticky Messages</strong>: Persistent messages that remain until dismissed</li>
 * </ul>
 * 
 * <h3>Usage Patterns</h3>
 * <p>
 * The typical usage flow involves:
 * </p>
 * <ol>
 *   <li>Performing an operation that returns a {@link de.cuioss.uimodel.result.ResultObject}</li>
 *   <li>Using {@link de.cuioss.jsf.api.components.model.result_content.ResultErrorHandler} to process the result</li>
 *   <li>Leveraging an {@link de.cuioss.jsf.api.components.model.result_content.ErrorController} implementation to display appropriate messages</li>
 *   <li>Conditionally rendering content based on the operation result</li>
 * </ol>
 * 
 * <p>
 * Classes in this package are designed to work together to provide a consistent
 * error handling and feedback experience across components. The stateless components
 * in this package are generally thread-safe.
 * </p>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see de.cuioss.uimodel.result.ResultObject
 * @see de.cuioss.jsf.api.components.css.ContextState
 */
package de.cuioss.jsf.api.components.model.result_content;