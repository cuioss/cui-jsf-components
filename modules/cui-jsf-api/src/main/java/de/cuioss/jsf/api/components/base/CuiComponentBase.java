/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
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
 * Base class for creating custom cui-components that are
 * {@link UIComponentBase} without further specification. It acts as a component
 * bridge.
 *
 * @author Oliver Wolff
 */
public class CuiComponentBase extends UIComponentBase implements ComponentBridge {

    /**
     * "de.cuioss.jsf.api.html.family"
     */
    public static final String COMPONENT_FAMILY = "de.cuioss.jsf.api.html.family";

    @Override
    public FacesContext facesContext() {
        return getFacesContext();
    }

    @Override
    public UIComponent facet(final String facetName) {
        return getFacet(facetName);
    }

    @Override
    public StateHelper stateHelper() {
        return getStateHelper();
    }

    @Override
    public String getFamily() {
        return COMPONENT_FAMILY;
    }

}
