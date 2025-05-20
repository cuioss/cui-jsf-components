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

import de.cuioss.jsf.api.components.partial.*;
import jakarta.faces.component.StateHelper;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.context.FacesContext;
import lombok.experimental.Delegate;

/**
 * Base class for CUI components that extend {@link UIInput}.
 * <p>
 * This class provides enhanced functionality for input components by implementing:
 * </p>
 * <ul>
 *   <li>{@link ComponentBridge} - Provides simplified access to component state and context</li>
 *   <li>{@link ComponentStyleClassProvider} - Manages the "styleClass" attribute common to HTML components</li>
 *   <li>{@link StyleAttributeProvider} - Manages the "style" attribute for inline CSS styling</li>
 * </ul>
 * <p>
 * This class differs from {@link BaseCuiHtmlInputComponent} in that it extends
 * directly from {@link UIInput} rather than from an HTML-specific component class,
 * making it more generic and suitable for a wider range of input implementations.
 * </p>
 * <p>
 * The class uses the delegation pattern via Lombok's {@code @Delegate} annotation to implement
 * the style-related interfaces, keeping the implementation clean and focused.
 * </p>
 * <p>
 * Usage example for creating a component subclass:
 * </p>
 * <pre>
 * public class CustomInput extends BaseCuiInputComponent {
 *     
 *     public static final String COMPONENT_TYPE = "custom.input";
 *     
 *     &#64;Override
 *     public String getFamily() {
 *         return "custom.input.family";
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
 * @author Oliver Wolff
 * @since 1.0
 * @see UIInput
 * @see ComponentBridge
 * @see ComponentStyleClassProvider
 * @see StyleAttributeProvider
 */
public class BaseCuiInputComponent extends UIInput
        implements ComponentBridge, ComponentStyleClassProvider, StyleAttributeProvider {

    /**
     * Delegate for implementing the {@link ComponentStyleClassProvider} interface.
     * <p>
     * This delegate handles the "styleClass" attribute and related functionality.
     * </p>
     */
    @Delegate
    private final ComponentStyleClassProvider styleClassProvider;

    /**
     * Delegate for implementing the {@link StyleAttributeProvider} interface.
     * <p>
     * This delegate handles the "style" attribute for inline CSS.
     * </p>
     */
    @Delegate
    private final StyleAttributeProvider styleAttributeProvider;

    /**
     * Default constructor that initializes the component delegates.
     * <p>
     * This constructor sets up the style and styleClass attribute providers
     * that implement the corresponding interfaces.
     * </p>
     */
    public BaseCuiInputComponent() {
        styleClassProvider = new ComponentStyleClassProviderImpl(this);
        styleAttributeProvider = new StyleAttributeProviderImpl(this);
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
