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
package de.cuioss.jsf.api.components;

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.junit5.EnableJsfEnvironment;
import de.cuioss.test.jsf.mocks.CuiMockRenderer;
import jakarta.faces.FacesException;
import jakarta.faces.context.FacesContext;
import org.junit.jupiter.api.Test;

@EnableJsfEnvironment
class JsfHtmlComponentTest {

    @Test
    void shouldFailToResolveNotConfiguredComponent(FacesContext facesContext) {
        assertThrows(FacesException.class,
                () -> JsfHtmlComponent.createComponent(facesContext, JsfHtmlComponent.SPAN));
    }

    @Test
    void shouldResolveConfiguredComponent(FacesContext facesContext, ComponentConfigDecorator componentConfig) {
        registerJsfHtmlComponentWithMockRenderer(JsfHtmlComponent.BUTTON, componentConfig);
        assertNotNull(JsfHtmlComponent.createComponent(facesContext, JsfHtmlComponent.BUTTON));
        registerJsfHtmlComponentWithMockRenderer(JsfHtmlComponent.CHECKBOX, componentConfig);
        assertNotNull(JsfHtmlComponent.createComponent(facesContext, JsfHtmlComponent.CHECKBOX));
        registerJsfHtmlComponentWithMockRenderer(JsfHtmlComponent.COMMAND_BUTTON, componentConfig);
        assertNotNull(JsfHtmlComponent.createComponent(facesContext, JsfHtmlComponent.COMMAND_BUTTON));
        registerJsfHtmlComponentWithMockRenderer(JsfHtmlComponent.FORM, componentConfig);
        assertNotNull(JsfHtmlComponent.createComponent(facesContext, JsfHtmlComponent.FORM));
        registerJsfHtmlComponentWithMockRenderer(JsfHtmlComponent.INPUT, componentConfig);
        assertNotNull(JsfHtmlComponent.createComponent(facesContext, JsfHtmlComponent.INPUT));
        registerJsfHtmlComponentWithMockRenderer(JsfHtmlComponent.HTML_INPUT, componentConfig);
        assertNotNull(JsfHtmlComponent.createComponent(facesContext, JsfHtmlComponent.HTML_INPUT));
        registerJsfHtmlComponentWithMockRenderer(JsfHtmlComponent.PANEL_GROUP, componentConfig);
        assertNotNull(JsfHtmlComponent.createComponent(facesContext, JsfHtmlComponent.PANEL_GROUP));
        registerJsfHtmlComponentWithMockRenderer(JsfHtmlComponent.SPAN, componentConfig);
        assertNotNull(JsfHtmlComponent.createComponent(facesContext, JsfHtmlComponent.SPAN));
        registerJsfHtmlComponentWithMockRenderer(JsfHtmlComponent.HTML_OUTPUT_TEXT, componentConfig);
        assertNotNull(JsfHtmlComponent.createComponent(facesContext, JsfHtmlComponent.HTML_OUTPUT_TEXT));
    }

    @Test
    void shouldFailToResolveNotConfiguredRenderer(FacesContext facesContext) {
        assertNull(JsfHtmlComponent.createRenderer(facesContext, JsfHtmlComponent.SPAN));
    }

    @Test
    void shouldResolveConfiguredRenderer(FacesContext facesContext, ComponentConfigDecorator componentConfig) {
        registerJsfHtmlComponentWithMockRenderer(JsfHtmlComponent.BUTTON, componentConfig);
        assertNotNull(JsfHtmlComponent.createRenderer(facesContext, JsfHtmlComponent.BUTTON));
        registerJsfHtmlComponentWithMockRenderer(JsfHtmlComponent.CHECKBOX, componentConfig);
        assertNotNull(JsfHtmlComponent.createRenderer(facesContext, JsfHtmlComponent.CHECKBOX));
        registerJsfHtmlComponentWithMockRenderer(JsfHtmlComponent.COMMAND_BUTTON, componentConfig);
        assertNotNull(JsfHtmlComponent.createRenderer(facesContext, JsfHtmlComponent.COMMAND_BUTTON));
        registerJsfHtmlComponentWithMockRenderer(JsfHtmlComponent.FORM, componentConfig);
        assertNotNull(JsfHtmlComponent.createRenderer(facesContext, JsfHtmlComponent.FORM));
        registerJsfHtmlComponentWithMockRenderer(JsfHtmlComponent.INPUT, componentConfig);
        assertNotNull(JsfHtmlComponent.createRenderer(facesContext, JsfHtmlComponent.INPUT));
        registerJsfHtmlComponentWithMockRenderer(JsfHtmlComponent.HTML_INPUT, componentConfig);
        assertNotNull(JsfHtmlComponent.createRenderer(facesContext, JsfHtmlComponent.HTML_INPUT));
        registerJsfHtmlComponentWithMockRenderer(JsfHtmlComponent.PANEL_GROUP, componentConfig);
        assertNotNull(JsfHtmlComponent.createRenderer(facesContext, JsfHtmlComponent.PANEL_GROUP));
        registerJsfHtmlComponentWithMockRenderer(JsfHtmlComponent.SPAN, componentConfig);
        assertNotNull(JsfHtmlComponent.createRenderer(facesContext, JsfHtmlComponent.SPAN));
        registerJsfHtmlComponentWithMockRenderer(JsfHtmlComponent.HTML_OUTPUT_TEXT, componentConfig);
        assertNotNull(JsfHtmlComponent.createRenderer(facesContext, JsfHtmlComponent.HTML_OUTPUT_TEXT));
    }

    /**
     * Registers a given {@link JsfHtmlComponent} with a corresponding
     * {@link CuiMockRenderer}
     *
     * @param component the component to register
     * @param decorator the component config decorator to use
     */
    private void registerJsfHtmlComponentWithMockRenderer(final JsfHtmlComponent<?> component,
            final ComponentConfigDecorator decorator) {
        decorator.registerUIComponent(component.getComponentType(), component.getComponentClass())
                .registerRenderer(component.getFamily(), component.getRendererType(),
                        new CuiMockRenderer(component.getDefaultHtmlElement().getContent()));
    }
}
