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

import jakarta.faces.component.StateHelper;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.html.HtmlPanelGroup;
import jakarta.faces.context.FacesContext;

import de.cuioss.jsf.api.components.partial.ComponentBridge;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Base class for creating custom panel-based components.
 * Therefore, it extends {@link HtmlPanelGroup} and acts as {@link ComponentBridge}
 *
 * @author Oliver Wolff
 */
public class BaseCuiPanel extends HtmlPanelGroup implements ComponentBridge {

    /**
     * Default Constructor, defines a div panel {@link PanelType#DIV}
     */
    public BaseCuiPanel() {
        this(PanelType.DIV);
    }

    /**
     * @param panelType , must not be null
     */
    public BaseCuiPanel(final PanelType panelType) {
        requireNonNull(panelType);
        super.setLayout(panelType.getIdentifier());
    }

    @Override
    public StateHelper stateHelper() {
        return getStateHelper();
    }

    @Override
    public FacesContext facesContext() {
        return getFacesContext();
    }

    @Override
    public UIComponent facet(final String facetName) {
        return getFacet(facetName);
    }

    /**
     * Simple enum indicating whether to render a div or span element. It
     * corresponds with {@link HtmlPanelGroup#setLayout(String)}
     *
     * @author Oliver Wolff
     */
    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public enum PanelType {

        /**
         * Div block.
         */
        DIV("block"),

        /**
         * Span block.
         */
        SPAN("span");

        private final String identifier;
    }
}
