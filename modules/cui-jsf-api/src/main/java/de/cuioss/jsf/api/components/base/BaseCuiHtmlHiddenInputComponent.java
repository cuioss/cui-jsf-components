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
package de.cuioss.jsf.api.components.base;

import de.cuioss.jsf.api.components.partial.ComponentBridge;
import jakarta.faces.component.StateHelper;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.html.HtmlInputHidden;
import jakarta.faces.context.FacesContext;

/**
 * Minimal super-set for CUI-based components that extend {@link HtmlInputHidden}.
 * <p>
 * This class serves as a base for components requiring hidden input functionality while
 * providing the {@link ComponentBridge} interface for simplified access to component
 * state and context.
 * </p>
 * <p>
 * Usage example for creating a component subclass:
 * </p>
 * <pre>
 * public class CustomHiddenField extends BaseCuiHtmlHiddenInputComponent {
 *     
 *     public static final String COMPONENT_TYPE = "custom.hiddenField";
 *     
 *     &#64;Override
 *     public String getFamily() {
 *         return "custom.hiddenField.family";
 *     }
 *     
 *     // Additional functionality...
 * }
 * </pre>
 * <p>
 * Like other JSF components, this class is not thread-safe and instances
 * should not be shared between requests.
 * </p>
 *
 * @author Sven Haag
 * @since 1.0
 * @see HtmlInputHidden
 * @see ComponentBridge
 */
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public class BaseCuiHtmlHiddenInputComponent extends HtmlInputHidden implements ComponentBridge {

    /**
     * {@inheritDoc}
     * <p>
     * Provides access to the component's StateHelper for storing component state.
     * </p>
     * 
     * @return the StateHelper for this component
     */
    @Override
    public StateHelper stateHelper() {
        return getStateHelper();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Provides access to the current FacesContext.
     * </p>
     * 
     * @return the current FacesContext
     */
    @Override
    public FacesContext facesContext() {
        return getFacesContext();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Provides access to the named facet of this component.
     * </p>
     * 
     * @param facetName the name of the facet to retrieve
     * @return the UIComponent that corresponds to the requested facet, or null if no such facet exists
     */
    @Override
    public UIComponent facet(String facetName) {
        return getFacet(facetName);
    }
}
