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
 * Bridge interface that connects partial component implementations to their owning {@link UIComponent}.
 * <p>
 * This interface provides a standardized way for partial component implementations (like
 * {@link TitleProviderImpl}) to access essential elements of the JSF component infrastructure
 * without directly coupling to specific component implementations. It exposes the
 * {@link StateHelper}, {@link FacesContext} and component facets to the partial implementations.
 * </p>
 * <p>
 * The bridge pattern used here allows for better separation of concerns and more modular
 * component design by delegating specific functional aspects to focused implementation classes
 * while maintaining access to the core JSF infrastructure.
 * </p>
 * 
 * <h3>Implementation Note</h3>
 * <p>
 * <strong>Caution:</strong> This design introduces a cyclic dependency between the component
 * and its partial implementations. Use it with care. Partial implementations like 
 * {@link TitleProviderImpl} must <em>not</em> store any state in instance fields,
 * especially not references to {@link FacesContext} or {@link StateHelper}.
 * These should always be accessed through the bridge to ensure proper state management
 * within the JSF lifecycle.
 * </p>
 * 
 * <h3>Usage Example</h3>
 * <pre>
 * // In a UIComponent subclass:
 * public class MyComponent extends UIComponentBase implements TitleProvider {
 *     
 *     private final TitleProviderImpl titleProvider;
 *     
 *     public MyComponent() {
 *         titleProvider = new TitleProviderImpl(new ComponentBridge() {
 *             &#64;Override
 *             public StateHelper stateHelper() {
 *                 return getStateHelper();
 *             }
 *             
 *             &#64;Override
 *             public FacesContext facesContext() {
 *                 return getFacesContext();
 *             }
 *             
 *             &#64;Override
 *             public UIComponent facet(String facetName) {
 *                 return getFacet(facetName);
 *             }
 *         });
 *     }
 *     
 *     // Delegate to the provider implementation
 *     &#64;Override
 *     public void setTitleKey(String titleKey) {
 *         titleProvider.setTitleKey(titleKey);
 *     }
 *     
 *     // Other delegate methods...
 * }
 * </pre>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
public interface ComponentBridge {

    /**
     * Provides access to the component's state helper.
     * <p>
     * The state helper is used by partial implementations to store and retrieve
     * component state in a way that integrates with JSF's state management system.
     * </p>
     *
     * @return the {@link StateHelper} of the owning component
     */
    StateHelper stateHelper();

    /**
     * Provides access to the current faces context.
     * <p>
     * The faces context gives access to the current request, response,
     * application context, and other JSF infrastructure necessary for
     * component operations.
     * </p>
     *
     * @return the current {@link FacesContext}
     */
    FacesContext facesContext();

    /**
     * Retrieves a named facet from the owning component.
     * <p>
     * Facets are named components that serve special roles within a parent component,
     * such as headers, footers, or detail sections.
     * </p>
     *
     * @param facetName the name of the facet to retrieve, such as "header" or "footer"
     * @return the {@link UIComponent} representing the facet, or null if no facet
     *         with the specified name exists
     */
    UIComponent facet(String facetName);
}
