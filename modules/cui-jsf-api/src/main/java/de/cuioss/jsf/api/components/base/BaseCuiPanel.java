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

import static java.util.Objects.requireNonNull;

import de.cuioss.jsf.api.components.partial.ComponentBridge;
import jakarta.faces.component.StateHelper;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.html.HtmlPanelGroup;
import jakarta.faces.context.FacesContext;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Base class for creating custom panel-based components that extend {@link HtmlPanelGroup}.
 * <p>
 * This class provides a foundation for components that need to render as either div or span 
 * elements, implementing the {@link ComponentBridge} interface for simplified access to 
 * component state and context.
 * </p>
 * <p>
 * The panel type (div or span) is defined using the {@link PanelType} enum, which corresponds 
 * to the layout attribute in the standard {@link HtmlPanelGroup}.
 * </p>
 * <p>
 * Usage example for creating a component subclass:
 * </p>
 * <pre>
 * public class CustomPanel extends BaseCuiPanel {
 *     
 *     public static final String COMPONENT_TYPE = "custom.panel";
 *     
 *     public CustomPanel() {
 *         // Create a panel that renders as a span
 *         super(PanelType.SPAN);
 *     }
 *     
 *     &#64;Override
 *     public String getFamily() {
 *         return "custom.panel.family";
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
 * @see HtmlPanelGroup
 * @see ComponentBridge
 * @see PanelType
 */
public class BaseCuiPanel extends HtmlPanelGroup implements ComponentBridge {

    /**
     * Default Constructor that initializes this component as a div panel using {@link PanelType#DIV}.
     * <p>
     * This constructor is equivalent to calling <code>new BaseCuiPanel(PanelType.DIV)</code>.
     * </p>
     */
    public BaseCuiPanel() {
        this(PanelType.DIV);
    }

    /**
     * Constructor that initializes this component with the specified panel type.
     * <p>
     * The panel type determines whether this component renders as a div or span element.
     * </p>
     * 
     * @param panelType the type of panel to create, must not be null
     * @throws NullPointerException if panelType is null
     */
    public BaseCuiPanel(final PanelType panelType) {
        requireNonNull(panelType);
        super.setLayout(panelType.getIdentifier());
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
     * Enum that defines the two available panel types: div and span elements.
     * <p>
     * This enum corresponds to the layout attribute in {@link HtmlPanelGroup}:
     * </p>
     * <ul>
     *   <li>{@link #DIV} - Renders as a div element (layout="block")</li>
     *   <li>{@link #SPAN} - Renders as a span element (layout="span")</li>
     * </ul>
     *
     * @author Oliver Wolff
     * @since 1.0
     */
    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public enum PanelType {

        /**
         * Div block element type (renders as &lt;div&gt;).
         */
        DIV("block"),

        /**
         * Span inline element type (renders as &lt;span&gt;).
         */
        SPAN("span");

        /**
         * The identifier string used by the JSF rendering system to determine
         * the output element type.
         */
        private final String identifier;
    }
}
