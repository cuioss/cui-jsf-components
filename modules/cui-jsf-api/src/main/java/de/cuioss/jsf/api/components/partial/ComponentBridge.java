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
package de.cuioss.jsf.api.components.partial;

import jakarta.faces.component.StateHelper;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;

/**
 * <p>
 * This Interface builds the bridge for the interaction between
 * {@link UIComponent} and the partial elements. In essence it exposes the
 * {@link StateHelper}, {@link FacesContext} and {@link UIComponent Facets} to
 * the corresponding composite element.
 * </p>
 * <p>
 * <em>Caution:</em> With this concept we introduce a cyclic dependency into the
 * component classes. Use it with care. The actual implementations, like
 * {@link TitleProviderImpl} must <em>not</em> contain any state within a field.
 * Especially not {@link FacesContext} or {@link StateHelper}
 * </p>
 *
 * @author Oliver Wolff
 */
public interface ComponentBridge {

    /**
     * @return the {@link StateHelper} encapsulated within the specific component.
     */
    StateHelper stateHelper();

    /**
     * @return the {@link FacesContext} encapsulated within the specific component.
     */
    FacesContext facesContext();

    /**
     * @param facetName the facets name, e.g. 'header', 'footer'.
     * @return the facet or null.
     */
    UIComponent facet(String facetName);
}
