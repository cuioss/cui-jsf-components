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
package de.cuioss.jsf.api.components;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.faces.FacesException;

import org.junit.jupiter.api.Test;

import de.cuioss.test.jsf.junit5.JsfEnabledTestEnvironment;
import de.cuioss.test.jsf.mocks.CuiMockRenderer;

class JsfHtmlComponentTest extends JsfEnabledTestEnvironment {

    @Test
    void shouldFailToResolveNotConfiguredComponent() {
        assertThrows(FacesException.class,
                () -> JsfHtmlComponent.createComponent(getFacesContext(), JsfHtmlComponent.SPAN));
    }

    @Test
    void shouldResolveConfiguredComponent() {
        registerJsfHtmlComponentWithMockRenderer(JsfHtmlComponent.BUTTON);
        assertNotNull(JsfHtmlComponent.createComponent(getFacesContext(), JsfHtmlComponent.BUTTON));
        registerJsfHtmlComponentWithMockRenderer(JsfHtmlComponent.CHECKBOX);
        assertNotNull(JsfHtmlComponent.createComponent(getFacesContext(), JsfHtmlComponent.CHECKBOX));
        registerJsfHtmlComponentWithMockRenderer(JsfHtmlComponent.COMMAND_BUTTON);
        assertNotNull(JsfHtmlComponent.createComponent(getFacesContext(), JsfHtmlComponent.COMMAND_BUTTON));
        registerJsfHtmlComponentWithMockRenderer(JsfHtmlComponent.FORM);
        assertNotNull(JsfHtmlComponent.createComponent(getFacesContext(), JsfHtmlComponent.FORM));
        registerJsfHtmlComponentWithMockRenderer(JsfHtmlComponent.INPUT);
        assertNotNull(JsfHtmlComponent.createComponent(getFacesContext(), JsfHtmlComponent.INPUT));
        registerJsfHtmlComponentWithMockRenderer(JsfHtmlComponent.HTML_INPUT);
        assertNotNull(JsfHtmlComponent.createComponent(getFacesContext(), JsfHtmlComponent.HTML_INPUT));
        registerJsfHtmlComponentWithMockRenderer(JsfHtmlComponent.PANEL_GROUP);
        assertNotNull(JsfHtmlComponent.createComponent(getFacesContext(), JsfHtmlComponent.PANEL_GROUP));
        registerJsfHtmlComponentWithMockRenderer(JsfHtmlComponent.SPAN);
        assertNotNull(JsfHtmlComponent.createComponent(getFacesContext(), JsfHtmlComponent.SPAN));
        registerJsfHtmlComponentWithMockRenderer(JsfHtmlComponent.HTML_OUTPUT_TEXT);
        assertNotNull(JsfHtmlComponent.createComponent(getFacesContext(), JsfHtmlComponent.HTML_OUTPUT_TEXT));
    }

    @Test
    void shouldFailToResolveNotConfiguredRenderer() {
        assertNull(JsfHtmlComponent.createRenderer(getFacesContext(), JsfHtmlComponent.SPAN));
    }

    @Test
    void shouldResolveConfiguredRenderer() {
        registerJsfHtmlComponentWithMockRenderer(JsfHtmlComponent.BUTTON);
        assertNotNull(JsfHtmlComponent.createRenderer(getFacesContext(), JsfHtmlComponent.BUTTON));
        registerJsfHtmlComponentWithMockRenderer(JsfHtmlComponent.CHECKBOX);
        assertNotNull(JsfHtmlComponent.createRenderer(getFacesContext(), JsfHtmlComponent.CHECKBOX));
        registerJsfHtmlComponentWithMockRenderer(JsfHtmlComponent.COMMAND_BUTTON);
        assertNotNull(JsfHtmlComponent.createRenderer(getFacesContext(), JsfHtmlComponent.COMMAND_BUTTON));
        registerJsfHtmlComponentWithMockRenderer(JsfHtmlComponent.FORM);
        assertNotNull(JsfHtmlComponent.createRenderer(getFacesContext(), JsfHtmlComponent.FORM));
        registerJsfHtmlComponentWithMockRenderer(JsfHtmlComponent.INPUT);
        assertNotNull(JsfHtmlComponent.createRenderer(getFacesContext(), JsfHtmlComponent.INPUT));
        registerJsfHtmlComponentWithMockRenderer(JsfHtmlComponent.HTML_INPUT);
        assertNotNull(JsfHtmlComponent.createRenderer(getFacesContext(), JsfHtmlComponent.HTML_INPUT));
        registerJsfHtmlComponentWithMockRenderer(JsfHtmlComponent.PANEL_GROUP);
        assertNotNull(JsfHtmlComponent.createRenderer(getFacesContext(), JsfHtmlComponent.PANEL_GROUP));
        registerJsfHtmlComponentWithMockRenderer(JsfHtmlComponent.SPAN);
        assertNotNull(JsfHtmlComponent.createRenderer(getFacesContext(), JsfHtmlComponent.SPAN));
        registerJsfHtmlComponentWithMockRenderer(JsfHtmlComponent.HTML_OUTPUT_TEXT);
        assertNotNull(JsfHtmlComponent.createRenderer(getFacesContext(), JsfHtmlComponent.HTML_OUTPUT_TEXT));
    }

    /**
     * Registers a given {@link JsfHtmlComponent} with a corresponding
     * {@link CuiMockRenderer}
     *
     * @param component
     */
    private void registerJsfHtmlComponentWithMockRenderer(final JsfHtmlComponent<?> component) {
        getComponentConfigDecorator().registerUIComponent(component.getComponentType(), component.getComponentClass())
                .registerRenderer(component.getFamily(), component.getRendererType(),
                        new CuiMockRenderer(component.getDefaultHtmlElement().getContent()));
    }
}
