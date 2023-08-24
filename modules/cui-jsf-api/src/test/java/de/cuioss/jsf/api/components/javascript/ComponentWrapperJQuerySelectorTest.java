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
package de.cuioss.jsf.api.components.javascript;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlInputText;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.components.util.ComponentWrapper;
import de.cuioss.test.jsf.config.ComponentConfigurator;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.junit5.JsfEnabledTestEnvironment;

class ComponentWrapperJQuerySelectorTest extends JsfEnabledTestEnvironment implements ComponentConfigurator {

    private static final String EXTENSION = "extension";

    private ComponentWrapper<UIComponent> componentWrapper;

    @BeforeEach
    void setUpBefore() {
        var form = new HtmlForm();
        var wrapped = new HtmlInputText();
        wrapped.setId("mock");
        form.setId("form");
        form.getChildren().add(wrapped);
        componentWrapper = new ComponentWrapper<>(wrapped);
    }

    @Test
    void shouldCreateWithoutExtension() {
        var selector = ComponentWrapperJQuerySelector.builder().withComponentWrapper(componentWrapper).build();
        assertNotNull(selector);
        assertEquals("jQuery('#form\\\\:mock')", selector.script());
    }

    @Test
    void shouldCreateWithExtension() {
        var selector = ComponentWrapperJQuerySelector.builder().withComponentWrapper(componentWrapper)
                .withIdExtension(EXTENSION).build();
        assertNotNull(selector);
        assertEquals("jQuery('#form\\\\:mock_extension')", selector.script());
    }

    @Test
    void shouldImplementToStringProperly() {
        assertNotNull(
                ComponentWrapperJQuerySelector.builder().withComponentWrapper(componentWrapper).build().toString());
    }

    @Override
    public void configureComponents(final ComponentConfigDecorator decorator) {
        decorator.registerMockRendererForHtmlForm().registerMockRendererForHtmlInputText();
    }
}
