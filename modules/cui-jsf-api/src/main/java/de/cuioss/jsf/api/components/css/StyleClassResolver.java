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
package de.cuioss.jsf.api.components.css;

import de.cuioss.jsf.api.components.partial.ComponentStyleClassProvider;

/**
 * Interface for components that need to dynamically resolve CSS style classes from multiple sources.
 * <p>
 * While {@link StyleClassProvider} is focused on providing a predefined style class,
 * this interface is designed for cases where the style class needs to be dynamically computed
 * or combined from various sources. It's particularly useful for UI components that need
 * to merge developer-specified classes with framework-specific classes, or apply conditional
 * styling based on component state.
 * 
 * <p>
 * Implementers of this interface should calculate the complete set of style classes needed
 * for rendering the component, combining any or all of:
 * <ul>
 *   <li>Developer-provided style classes (e.g., from a styleClass attribute)</li>
 *   <li>Framework-specific style classes (e.g., Bootstrap classes)</li>
 *   <li>State-dependent classes (e.g., disabled, active states)</li>
 *   <li>Size or context classes (e.g., responsive sizing)</li>
 * </ul>
 * 
 * <p>
 * Implementation note: The resolver should be designed to recalculate styles each time
 * {@link #resolveStyleClass()} is called, since the component state may have changed.
 * 
 * <p>
 * Usage example:
 * <pre>
 * public class MyComponent extends UIComponent implements StyleClassResolver {
 *     
 *     private String styleClass;
 *     private boolean active;
 *     
 *     &#64;Override
 *     public StyleClassBuilder resolveStyleClass() {
 *         StyleClassBuilder builder = new StyleClassBuilderImpl("my-component");
 *         builder.append(styleClass);
 *         builder.appendIfTrue("active", active);
 *         return builder;
 *     }
 * }
 * </pre>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see StyleClassProvider
 * @see StyleClassBuilder
 * @see ComponentStyleClassProvider
 */
public interface StyleClassResolver {

    /**
     * Calculates and returns the complete set of CSS style classes for a component.
     * <p>
     * In contrast to {@link ComponentStyleClassProvider#getStyleClass()} which
     * typically returns only the explicitly configured styleClass attribute, this method
     * computes a complete style class string from multiple sources, potentially including:
     * </p>
     * <ul>
     *   <li>Base component classes</li>
     *   <li>Developer-provided custom classes</li>
     *   <li>State-specific classes</li>
     *   <li>Framework-specific styling classes</li>
     * </ul>
     * <p>
     * The returned builder contains all resolved classes and can be further modified
     * if needed before generating the final class string.
     * </p>
     *
     * @return a StyleClassBuilder containing all resolved CSS classes for the component,
     *         never {@code null}
     */
    StyleClassBuilder resolveStyleClass();
}
