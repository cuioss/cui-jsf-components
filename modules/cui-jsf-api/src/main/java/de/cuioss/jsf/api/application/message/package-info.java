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
 * <h2>Message Management API</h2>
 * <p>
 * Provides a comprehensive API for creating, managing and displaying
 * {@link jakarta.faces.application.FacesMessage}s in JSF applications.
 * This package simplifies the message handling process with resource bundle
 * integration, severity level management, and display name resolution.
 * 
 * <h2>Key Components</h2>
 * <ul>
 *   <li>{@link de.cuioss.jsf.api.application.message.MessageProducer}: Core interface
 *       that defines methods for creating and setting JSF messages with various
 *       severity levels. Supports resource bundle lookups and parameter substitution.</li>
 *   <li>{@link de.cuioss.jsf.api.application.message.MessageProducerBean}: The default
 *       implementation of MessageProducer, scoped to the request context.</li>
 *   <li>{@link de.cuioss.jsf.api.application.message.DisplayNameMessageProducer}: Specialized
 *       decorator for MessageProducer that handles {@link de.cuioss.uimodel.nameprovider.IDisplayNameProvider}
 *       objects and {@link de.cuioss.uimodel.result.ResultObject} instances.</li>
 * </ul>
 * 
 * <h2>Usage Patterns</h2>
 * <p>
 * These components are typically used in managed beans to display feedback to users:
 * <ul>
 *   <li>Global messages for page-level notifications</li>
 *   <li>Component-specific messages for field validation</li>
 *   <li>Display of result messages from business operations</li>
 * </ul>
 * 
 * <h2>Integration Points</h2>
 * <p>
 * This package integrates with:
 * <ul>
 *   <li>JSF's {@link jakarta.faces.application.FacesMessage} system</li>
 *   <li>CUI's resource bundle localization mechanism</li>
 *   <li>The {@link de.cuioss.uimodel.result.ResultObject} API for operation results</li>
 * </ul>
 *
 * @author Oliver Wolff
 * @author Matthias Walliczek
 * @since 1.0
 */
package de.cuioss.jsf.api.application.message;
