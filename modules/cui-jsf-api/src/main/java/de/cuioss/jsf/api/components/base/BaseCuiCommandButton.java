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

import de.cuioss.jsf.api.components.myfaces.MyFacesDelegateStyleClassAdapter;
import de.cuioss.jsf.api.components.myfaces.MyFacesDelegateTitleAdapter;
import de.cuioss.jsf.api.components.partial.*;
import jakarta.faces.component.StateHelper;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.html.HtmlCommandButton;
import jakarta.faces.context.FacesContext;
import lombok.experimental.Delegate;

/**
 * Base class for creating CUI variants of {@link HtmlCommandButton}
 * that extends the standard JSF command button functionality with CUI-specific features.
 * <p>
 * This class implements several interfaces to provide additional functionality:
 * </p>
 * <ul>
 *   <li>{@link ComponentBridge} - Provides simplified access to component state and context</li>
 *   <li>{@link TitleProvider} - Manages the title attribute with resolved message support</li>
 *   <li>{@link MyFacesDelegateStyleClassAdapter} - Adapter for MyFaces implementation of styleClass</li>
 *   <li>{@link MyFacesDelegateTitleAdapter} - Adapter for MyFaces implementation of title</li>
 * </ul>
 * <p>
 * This class uses the delegation pattern via Lombok's {@code @Delegate} annotation to implement
 * the title and style class functionality, keeping the implementation clean and focused.
 * </p>
 * <p>
 * Usage example for creating a component subclass:
 * </p>
 * <pre>
 * public class CustomButton extends BaseCuiCommandButton {
 *     
 *     public static final String COMPONENT_TYPE = "custom.button";
 *     
 *     &#64;Override
 *     public String getFamily() {
 *         return "custom.button.family";
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
 * @see HtmlCommandButton
 * @see ComponentBridge
 * @see TitleProvider
 */
public class BaseCuiCommandButton extends HtmlCommandButton implements ComponentBridge, TitleProvider, MyFacesDelegateStyleClassAdapter, MyFacesDelegateTitleAdapter {

    /**
     * Delegate for implementing the {@link TitleProvider} interface.
     * <p>
     * This delegate handles the "title" attribute with message resolution capabilities.
     * </p>
     */
    @Delegate
    private final TitleProvider titleProvider;

    /**
     * Delegate for implementing the {@link ComponentStyleClassProvider} interface.
     * <p>
     * This delegate handles the "styleClass" attribute and related functionality.
     * </p>
     */
    @Delegate
    private final ComponentStyleClassProvider styleClassProvider;

    /**
     * Default constructor that initializes the component delegates.
     * <p>
     * This constructor sets up the title and styleClass attribute providers
     * that implement the corresponding interfaces.
     * </p>
     */
    public BaseCuiCommandButton() {
        titleProvider = new TitleProviderImpl(this);
        styleClassProvider = new ComponentStyleClassProviderImpl(this);
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
    public UIComponent facet(final String facetName) {
        return getFacet(facetName);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Returns the resolved title text, which may include message lookups
     * from resource bundles if configured.
     * </p>
     * 
     * @return the resolved title text
     */
    @Override
    public String getTitle() {
        return titleProvider.resolveTitle();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Writes the computed styleClass to the parent class's styleClass property.
     * </p>
     */
    @Override
    public void writeStyleClassToParent() {
        super.setStyleClass(styleClassProvider.getStyleClass());
    }

    /**
     * {@inheritDoc}
     * <p>
     * Writes the computed title to the parent class's title property.
     * </p>
     */
    @Override
    public void writeTitleToParent() {
        super.setTitle(titleProvider.getTitle());
    }
}
