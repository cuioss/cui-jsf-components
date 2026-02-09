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
 * <h2>Component Partial Implementation Package</h2>
 * <p>
 * This package provides a collection of reusable component traits that implement specific 
 * aspects of UI component functionality following the composite pattern. These traits are 
 * designed to be combined into complete components, allowing for flexible composition of 
 * component behaviors while encouraging code reuse.
 * </p>
 * <p>
 * The package contains provider interfaces and their implementations for common component 
 * attributes and behaviors. Many component implementations in the CUI JSF framework use 
 * these partial implementations to reduce code duplication and maintain consistency across 
 * the component library.
 * </p>
 * <h3>Key Concepts</h3>
 * <ul>
 *   <li><strong>Provider Interfaces</strong>: Define contracts for specific component behaviors 
 *       (e.g., {@link de.cuioss.jsf.api.components.partial.TitleProvider})</li>
 *   <li><strong>Implementations</strong>: Concrete implementations of these interfaces
 *       (e.g., {@link de.cuioss.jsf.api.components.partial.TitleProviderImpl})</li>
 *   <li><strong>Component Bridges</strong>: Facilities for connecting different component parts</li>
 * </ul>
 * <h3>Usage Patterns</h3>
 * <p>
 * Typically, a component will implement multiple provider interfaces to gain access to their 
 * functionality. This is done either by direct implementation or by composition, depending 
 * on the specific needs of the component.
 * </p>
 * <p>
 * Example of combining multiple providers:
 * </p>
 * <pre>
 * public class MyComponent extends UIComponentBase implements TitleProvider, 
 *     StyleAttributeProvider, DisabledComponentProvider {
 *     
 *     // Implementation of provider methods...
 * }
 * </pre>
 * <p>
 * This approach is consistent with the interface segregation principle, allowing components
 * to choose only the behaviors they need.
 * </p>
 * 
 * @author Oliver Wolff
 * @since 1.0
 */
package de.cuioss.jsf.api.components.partial;
