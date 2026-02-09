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

/**
 * Provider interface for component styling through CSS inline styles.
 * <p>
 * This interface defines the contract for components that need to support direct 
 * CSS styling through the HTML style attribute. Components implementing this
 * interface offer consistent access to inline style attributes across the
 * component library.
 * </p>
 * <p>
 * While this interface supports inline styles for flexibility, it's important to note
 * that using styleClass attributes is generally preferred over inline styles for
 * better maintainability and separation of concerns.
 * </p>
 * 
 * <h2>Usage Guidelines</h2>
 * <ul>
 *   <li>Use this interface when your component needs to support inline CSS styling</li>
 *   <li>Prefer using {@link ComponentStyleClassProvider} instead when possible</li>
 *   <li>Consider combining with {@link StyleAttributeProviderImpl} for implementation</li>
 * </ul>
 * 
 * <h2>Attributes</h2>
 * <h3>style</h3>
 * <p>
 * Defines CSS style(s) to be applied inline when this component is rendered.
 * This corresponds directly to the HTML style attribute. While supported
 * for flexibility, the use of styleClass attributes and external CSS is 
 * generally preferred for better maintainability.
 * </p>
 * 
 * <h2>Implementation Note</h2>
 * <p>
 * Implementations should store the style attribute in the component's state
 * using the standard attribute name "style" to ensure compatibility with
 * the JSF rendering mechanism. The {@link StyleAttributeProviderImpl} class
 * provides a standard implementation of this interface.
 * </p>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see StyleAttributeProviderImpl
 * @see ComponentStyleClassProvider
 * @see UIComponent#getAttributes()
 */
public interface StyleAttributeProvider {

    /**
     * Sets the CSS inline styles to be applied to the component.
     * <p>
     * This corresponds to the HTML style attribute and should contain
     * valid CSS style declarations (e.g., "color: red; margin-left: 10px;").
     * </p>
     * 
     * @param style the CSS inline style string to set
     */
    void setStyle(String style);

    /**
     * Returns the CSS inline styles applied to the component.
     * <p>
     * The returned string represents the content of the HTML style attribute
     * and should contain valid CSS style declarations.
     * </p>
     *
     * @return the CSS style string, may be null if no inline styles are set
     */
    String getStyle();
}
