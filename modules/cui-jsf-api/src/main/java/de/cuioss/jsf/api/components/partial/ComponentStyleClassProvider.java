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
package de.cuioss.jsf.api.components.partial;

import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.api.components.css.StyleClassProvider;

/**
 * <h2>Summary</h2>
 * <p>
 * This interface defines the contract for components that need to manage CSS style class
 * attributes through the JSF component lifecycle. It extends {@link StyleClassProvider} to
 * make components directly usable in styling operations.
 * </p>
 * <p>
 * Implementors of this interface manage the state and resolution of the
 * styleClass attribute. The implementation relies on the correct use of
 * attribute names, which must exactly match the accessor methods.
 * </p>
 * 
 * <h2>Attribute: styleClass</h2>
 * <p>
 * Space-separated list of CSS style class(es) to be applied when
 * this element is rendered. This is typically set by the component user to provide
 * custom styling.
 * </p>
 * 
 * <h2>Integration with Component State</h2>
 * <p>
 * Implementations need to store and retrieve the styleClass value using the component's
 * state management system. It's recommended to use the {@link ComponentStyleClassProviderImpl}
 * implementation when possible, which handles the state management details.
 * </p>
 * 
 * <h2>Usage</h2>
 * <p>
 * This provider is typically used in conjunction with other providers to create
 * a complete component. The following pattern is recommended:
 * </p>
 * <pre>
 * public class MyComponent extends UIComponentBase implements ComponentStyleClassProvider {
 *     
 *     private final ComponentStyleClassProviderImpl styleClassProvider;
 *     
 *     public MyComponent() {
 *         styleClassProvider = new ComponentStyleClassProviderImpl(new ComponentBridge() {
 *             // Bridge implementation
 *         });
 *     }
 *     
 *     &#64;Override
 *     public String getStyleClass() {
 *         return styleClassProvider.getStyleClass();
 *     }
 *     
 *     &#64;Override
 *     public void setStyleClass(String styleClass) {
 *         styleClassProvider.setStyleClass(styleClass);
 *     }
 *     
 *     &#64;Override
 *     public String computeAndStoreFinalStyleClass(StyleClassBuilder componentSpecificStyleClass) {
 *         return styleClassProvider.computeAndStoreFinalStyleClass(componentSpecificStyleClass);
 *     }
 * }
 * </pre>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see StyleClassProvider
 * @see ComponentStyleClassProviderImpl
 * @see StyleClassBuilder
 */
public interface ComponentStyleClassProvider extends StyleClassProvider {

    /**
     * Sets the CSS style class(es) to be applied to this component.
     * 
     * @param styleClass the styleClass to set, as a space-separated list of CSS class names.
     *                   Multiple class names can be provided in a single string,
     *                   separated by spaces.
     */
    void setStyleClass(String styleClass);

    /**
     * Computes and stores the final (effective) styleClass by concatenating the component-specific
     * style classes with any user-defined style classes from {@link #getStyleClass()}.
     * <p>
     * This method is typically called during the rendering phase to combine automatically
     * generated style classes with user-defined ones. The resulting combined style class
     * string is both returned and stored for later retrieval.
     * </p>
     *
     * @param componentSpecificStyleClass The style classes created by the corresponding renderer,
     *                                    excluding any user-defined style classes. Must not be null.
     * @return The complete space-separated string of CSS class names that should be applied
     *         to the component when rendered
     */
    String computeAndStoreFinalStyleClass(StyleClassBuilder componentSpecificStyleClass);
}
