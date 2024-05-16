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
package de.cuioss.jsf.bootstrap.button;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import jakarta.faces.component.UICommand;
import jakarta.faces.component.UIComponent;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.components.JsfComponentIdentifier;
import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.AttributeValue;
import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.jsf.bootstrap.icon.IconComponent;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.config.ComponentConfigurator;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.mocks.CuiMockRenderer;
import de.cuioss.test.jsf.renderer.AbstractComponentRendererTest;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class CloseCommandButtonRendererTest extends AbstractComponentRendererTest<CloseCommandButtonRenderer>
        implements ComponentConfigurator {

    @Test
    void shouldRenderMinimal() {
        var expected = new HtmlTreeBuilder().withNode(Node.BUTTON)
                .withAttribute(AttributeName.ARIA_LABEL, AttributeValue.ARIA_CLOSE)
                .withStyleClass(CssBootstrap.BUTTON_CLOSE);
        expected.withNode(Node.SPAN).withAttribute(AttributeName.ARIA_HIDDEN, AttributeValue.TRUE)
                .withStyleClass(CssBootstrap.BUTTON_TEXT).withTextContent("&#xD7;");
        assertRenderResult(getComponent(), expected.getDocument());
    }

    @Test
    void shouldDecodeWOErrors() {
        assertDoesNotThrow(() -> getRenderer().decode(getFacesContext(), getComponent()));
    }

    @Override
    public void configureComponents(final ComponentConfigDecorator decorator) {
        decorator.registerRenderer(UICommand.COMPONENT_FAMILY, JsfComponentIdentifier.BUTTON_RENDERER_TYPE,
                new CuiMockRenderer("input")).registerUIComponent(IconComponent.class);
    }

    @Override
    protected UIComponent getComponent() {
        return new CloseCommandButton();
    }
}
