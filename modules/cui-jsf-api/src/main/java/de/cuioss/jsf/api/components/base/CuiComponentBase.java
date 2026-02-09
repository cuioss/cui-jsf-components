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
import jakarta.faces.component.UIComponentBase;
import jakarta.faces.context.FacesContext;

/**
 * Base class for creating custom CUI components that extend {@link UIComponentBase}
 * without further specification.
 * <p>
 * This is the most basic component class in the CUI component hierarchy, extending
 * directly from {@link UIComponentBase} and implementing the {@link ComponentBridge}
 * interface for simplified access to component state and context.
 * </p>
 * <p>
 * Unlike other base component classes like {@link AbstractBaseCuiComponent}, this class
 * does not provide style class or other attribute-specific functionality, making it
 * suitable for components that need minimal JSF infrastructure.
 * </p>
 * <p>
 * This class predefines the component family as {@code de.cuioss.jsf.api.html.family}.
 * </p>
 * <p>
 * Usage example for creating a component subclass:
 * </p>
 * <pre>
 * public class SimpleComponent extends CuiComponentBase {
 *     
 *     public static final String COMPONENT_TYPE = "simple.component";
 *     
 *     // Additional functionality...
 * }
 * </pre>
 * <p>
 * Like other JSF components, this class is not thread-safe and instances
 * should not be shared between requests.
 * </p>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see UIComponentBase
 * @see ComponentBridge
 */
public class CuiComponentBase extends UIComponentBase implements ComponentBridge {

    /**
     * The component family identifier used by CUI components.
     * <p>
     * Value: "de.cuioss.jsf.api.html.family"
     * </p>
     */
    public static final String COMPONENT_FAMILY = "de.cuioss.jsf.api.html.family";

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
    public UIComponent facet(final String facetName) {
        return getFacet(facetName);
    }

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
     * Returns the component family identifier for CUI components.
     * </p>
     * 
     * @return the component family identifier (COMPONENT_FAMILY constant)
     */
    @Override
    public String getFamily() {
        return COMPONENT_FAMILY;
    }
}
