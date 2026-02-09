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
 * <p>
 * This package provides partial component implementations that follow the composite pattern,
 * specifically designed for Bootstrap UI integration. These partial components can be mixed
 * into other components to provide consistent Bootstrap-related functionality.
 * </p>
 * 
 * <h2>Package Purpose</h2>
 * <p>
 * The classes in this package serve as building blocks for larger components, providing
 * reusable functionality for common Bootstrap UI patterns. This approach:
 * </p>
 * <ul>
 *   <li>Promotes code reuse across multiple components</li>
 *   <li>Ensures consistent application of Bootstrap grid system and styling</li>
 *   <li>Abstracts away the complexity of Bootstrap's CSS classes and layout modes</li>
 *   <li>Simplifies state management for common component attributes</li>
 * </ul>
 * 
 * <h2>Key Providers</h2>
 * <p>
 * The package includes several essential providers:
 * </p>
 * <ul>
 *   <li>{@link de.cuioss.jsf.bootstrap.common.partial.ColumnProvider} - General purpose Bootstrap column configuration</li>
 *   <li>{@link de.cuioss.jsf.bootstrap.common.partial.ContentColumnProvider} - Specialized for content area columns</li>
 *   <li>{@link de.cuioss.jsf.bootstrap.common.partial.LabelColumnProvider} - Specialized for label area columns</li>
 *   <li>{@link de.cuioss.jsf.bootstrap.common.partial.LayoutModeProvider} - Manages different layout rendering modes</li>
 *   <li>{@link de.cuioss.jsf.bootstrap.common.partial.ColumnCssResolver} - Utility for generating Bootstrap column CSS classes</li>
 * </ul>
 * 
 * <h2>Usage Pattern</h2>
 * <p>
 * Components typically use these providers through composition, instantiating them in their
 * constructors and delegating to them for functionality. For example:
 * </p>
 * <pre>
 * public class MyComponent extends UIComponentBase {
 *     
 *     private final ColumnProvider columnProvider;
 *     private final LayoutModeProvider layoutModeProvider;
 *     
 *     public MyComponent() {
 *         columnProvider = new ColumnProvider(this, 6); // Default size of 6
 *         layoutModeProvider = new LayoutModeProvider(this, LayoutMode.PLAIN);
 *     }
 *     
 *     // Delegate methods for size
 *     public Integer getSize() {
 *         return columnProvider.getSize();
 *     }
 *     
 *     public void setSize(Integer size) {
 *         columnProvider.setSize(size);
 *     }
 *     
 *     // Similar delegation for other attributes...
 * }
 * </pre>
 * 
 * <h2>Related Packages</h2>
 * <p>
 * This package is an extension of the core partial components in 
 * {@code de.cuioss.jsf.api.components.partial}, focusing specifically on Bootstrap
 * integration.
 * </p>
 *
 * @author Oliver Wolff
 * @see de.cuioss.jsf.api.components.partial
 * @see de.cuioss.jsf.bootstrap.layout.LayoutMode
 */
package de.cuioss.jsf.bootstrap.common.partial;
