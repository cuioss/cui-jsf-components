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

import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.api.components.partial.*;
import jakarta.faces.component.StateHelper;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.html.HtmlOutputText;
import jakarta.faces.context.FacesContext;
import lombok.experimental.Delegate;

/**
 * Base class for creating CUI components that extend {@link HtmlOutputText} and are rendered 
 * as span elements.
 * <p>
 * This abstract class provides enhanced functionality for output text components with:
 * </p>
 * <ul>
 *   <li>{@link ComponentBridge} - Provides simplified access to component state and context</li>
 *   <li>{@link TitleProvider} - Manages the title attribute with resolved message support</li>
 *   <li>Style class management - Combines component-specific style classes with user-provided ones</li>
 * </ul>
 * <p>
 * The class uses the delegation pattern via Lombok's {@code @Delegate} annotation to implement
 * the title functionality, keeping the implementation clean and focused.
 * </p>
 * <h2>Attributes</h2>
 * <ul>
 *   <li>{@link TitleProvider}: This mechanism overrides {@link HtmlOutputText#getTitle()}.
 *     In addition it will throw an {@link UnsupportedOperationException} on calling
 *     {@link HtmlOutputText#setTitle(String)}</li>
 *   <li>All attributes from {@link HtmlOutputText}</li>
 * </ul>
 * <p>
 * Subclasses must implement {@link #resolveComponentSpecificStyleClasses()} to provide
 * component-specific CSS classes that will be combined with user-provided styleClass values.
 * </p>
 * <p>
 * Usage example for creating a component subclass:
 * </p>
 * <pre>
 * public class InfoText extends BaseCuiOutputText {
 *     
 *     public static final String COMPONENT_TYPE = "info.text";
 *     
 *     &#64;Override
 *     public String getFamily() {
 *         return "info.text.family";
 *     }
 *     
 *     &#64;Override
 *     public StyleClassBuilder resolveComponentSpecificStyleClasses() {
 *         return StyleClassBuilder.create().append("info-text");
 *     }
 * }
 * </pre>
 * <p>
 * Like other JSF components, this class is not thread-safe and instances
 * should not be shared between requests.
 * </p>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see HtmlOutputText
 * @see ComponentBridge
 * @see TitleProvider
 */
public abstract class BaseCuiOutputText extends HtmlOutputText implements ComponentBridge {

    /**
     * Delegate for implementing the {@link TitleProvider} interface.
     * <p>
     * This delegate handles the "title" attribute with message resolution capabilities.
     * </p>
     */
    @Delegate
    private final TitleProvider titleProvider;

    /**
     * Provider for managing the styleClass attribute.
     * <p>
     * This provider handles the "styleClass" attribute storage and retrieval.
     * </p>
     */
    private final ComponentStyleClassProvider styleClassProvider;

    /**
     * Default constructor that initializes the component delegates.
     * <p>
     * This constructor sets up the title and styleClass attribute providers
     * that implement the corresponding interfaces.
     * </p>
     */
    protected BaseCuiOutputText() {
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
     * Returns the combined style classes from the component-specific classes and
     * the user-provided styleClass attribute.
     * </p>
     * 
     * @return the combined style class string
     */
    @Override
    public String getStyleClass() {
        return resolveComponentSpecificStyleClasses().append(styleClassProvider.getStyleClassBuilder()).getStyleClass();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Sets the user-provided style class.
     * </p>
     * 
     * @param styleClass the style class to set
     */
    @Override
    public void setStyleClass(final String styleClass) {
        styleClassProvider.setStyleClass(styleClass);
    }

    /**
     * Provides component-specific CSS style classes that will be automatically
     * combined with any user-specified styleClass attribute.
     * <p>
     * Subclasses must implement this method to define their specific styling.
     * The returned builder must not be null, but may be empty.
     * </p>
     * <p>
     * This method is used by {@link #getStyleClass()} to combine the component's
     * inherent styles with any user-provided styles.
     * </p>
     *
     * @return a StyleClassBuilder containing the component's specific CSS classes, never null
     */
    public abstract StyleClassBuilder resolveComponentSpecificStyleClasses();

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
}
