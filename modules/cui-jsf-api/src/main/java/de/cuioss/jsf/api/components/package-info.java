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
 * <h2>Summary</h2>
 * <p>
 * Provides common helper classes, interfaces, and utilities that can be used for the creation
 * and management of {@link jakarta.faces.component.UIComponent} and
 * {@link jakarta.faces.render.Renderer} implementations.
 * </p>
 * 
 * <h2>Key Components</h2>
 * <ul>
 *   <li>{@link de.cuioss.jsf.api.components.JsfComponentIdentifier} - Provides a consistent way to identify JSF components</li>
 *   <li>{@link de.cuioss.jsf.api.components.JsfHtmlComponent} - Defines standard JSF HTML components</li>
 *   <li>{@link de.cuioss.jsf.api.components.JsfBehaviorComponent} - Represents JSF behavior components</li>
 * </ul>
 * 
 * <h2>Related Packages</h2>
 * <ul>
 *   <li>{@link de.cuioss.jsf.api.components.base} - Base classes for JSF component implementations</li>
 *   <li>{@link de.cuioss.jsf.api.components.css} - CSS utilities for styling components</li>
 *   <li>{@link de.cuioss.jsf.api.components.html} - HTML generation utilities</li>
 *   <li>{@link de.cuioss.jsf.api.components.javascript} - JavaScript integration utilities</li>
 * </ul>
 * 
 * <p>
 * This package serves as the foundation for all component-related functionality in the CUI JSF API.
 * It is designed to be thread-safe and compatible with all supported JSF versions.
 * </p>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
package de.cuioss.jsf.api.components;
