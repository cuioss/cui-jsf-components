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
package de.cuioss.jsf.components.inlineconfirm;

import static de.cuioss.jsf.components.inlineconfirm.InlineConfirmRenderer.DATA_IDENTIFIER;
import static de.cuioss.jsf.components.inlineconfirm.InlineConfirmRenderer.DATA_TARGET_IDENTIFIER;

import de.cuioss.jsf.api.components.JsfHtmlComponent;
import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.AttributeValue;
import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.renderer.VetoRenderAttributeAssert;
import de.cuioss.test.jsf.renderer.AbstractComponentRendererTest;
import de.cuioss.test.jsf.renderer.CommonRendererAsserts;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@DisplayName("Tests for InlineConfirmRenderer")
@JsfTestConfiguration(CoreJsfTestConfiguration.class)
@VetoRenderAttributeAssert({CommonRendererAsserts.STYLE, CommonRendererAsserts.STYLE_CLASS,
        CommonRendererAsserts.PASSTHROUGH, CommonRendererAsserts.ID})
class InlineConfirmRendererTest extends AbstractComponentRendererTest<InlineConfirmRenderer> {

    @Override
    protected UIComponent getComponent() {
        // Create a basic component for tests that don't need facets or children
        return new InlineConfirmComponent();
    }

    /**
     * Creates a component with facets and children for more complex tests
     * 
     * @param facesContext the current FacesContext
     * @return a configured InlineConfirmComponent
     */
    protected UIComponent getComponent(FacesContext facesContext) {
        // Create the main component
        var component = new InlineConfirmComponent();

        // Add the initial facet (button)
        component.getFacets().put(InlineConfirmComponent.INITIAL_FACET_NAME,
                JsfHtmlComponent.BUTTON.component(facesContext));

        // Add a child button with style
        UIComponent button = JsfHtmlComponent.BUTTON.component(facesContext);
        button.getAttributes().put(AttributeName.STYLE.getContent(), "border-radius: 3px;");
        component.getChildren().add(button);

        return component;
    }

    @Test
    @DisplayName("Should render minimal inline confirm component")
    void shouldRenderMinimal(FacesContext facesContext) throws IOException {
        // Arrange
        var component = getComponent(facesContext);

        // Create expected HTML structure
        var expected = new HtmlTreeBuilder()
                .withNode(Node.BUTTON)
                .withAttribute(DATA_IDENTIFIER, DATA_IDENTIFIER);

        expected.currentHierarchyUp()
                .withNode(Node.BUTTON)
                .withAttribute(DATA_TARGET_IDENTIFIER, DATA_TARGET_IDENTIFIER)
                .withAttribute(AttributeName.STYLE,
                        AttributeValue.STYLE_DISPLAY_NONE.getContent() + "border-radius: 3px;");

        // Act & Assert - Component should render with expected structure
        assertRenderResult(component, expected.getDocument(), facesContext);
    }

}
