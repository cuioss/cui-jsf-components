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
 * <h2>Base Component Classes Package</h2>
 * <p>
 * Provides foundational abstract component classes that serve as the basis for all CUI JSF components.
 * These base classes encapsulate common functionality and configuration that would otherwise need 
 * to be reimplemented across multiple component classes.
 * </p>
 * 
 * <h3>Key Base Components</h3>
 * <ul>
 *   <li>{@link de.cuioss.jsf.api.components.base.AbstractBaseCuiComponent} - Base class for standard components</li>
 *   <li>{@link de.cuioss.jsf.api.components.base.BaseCuiNamingContainer} - Base class for naming container components</li>
 *   <li>{@link de.cuioss.jsf.api.components.base.BaseCuiPanel} - Base class for panel-like components</li>
 *   <li>{@link de.cuioss.jsf.api.components.base.BaseCuiInputComponent} - Base class for input components</li>
 *   <li>{@link de.cuioss.jsf.api.components.base.CuiComponentBase} - Utility class with component convenience methods</li>
 * </ul>
 * 
 * <h3>Common Features</h3>
 * <p>
 * These base classes provide:
 * </p>
 * <ul>
 *   <li>Standard attribute handling (style, styleClass, etc.)</li>
 *   <li>Simplified state management</li>
 *   <li>Unified access to component context</li>
 *   <li>Common rendering hooks and lifecycle methods</li>
 * </ul>
 * 
 * <h3>Usage</h3>
 * <p>
 * Custom components should extend one of these base classes rather than directly extending the JSF component classes.
 * This ensures consistent behavior and reduces boilerplate code across the component library.
 * </p>
 * 
 * <p>
 * All classes in this package are designed to be thread-safe for read operations, but JSF components
 * themselves are not generally thread-safe for modification operations as they are typically accessed
 * in a single-threaded request context.
 * </p>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see jakarta.faces.component.UIComponentBase
 * @see jakarta.faces.component.UINamingContainer
 */
package de.cuioss.jsf.api.components.base;
