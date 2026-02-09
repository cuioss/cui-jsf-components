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
package de.cuioss.jsf.bootstrap.layout;

import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.renderer.AbstractComponentRendererTest;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.html.HtmlOutputText;
import jakarta.faces.context.FacesContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
@DisplayName("Tests for LayoutComponentRenderer")
class LayoutComponentRendererTest extends AbstractComponentRendererTest<LayoutComponentRenderer> {

    @Nested
    @DisplayName("Tests for rendering behavior")
    class RenderingTests {

        @Test
        @DisplayName("Should render minimal component correctly")
        void shouldRenderMinimal(FacesContext facesContext) throws Exception {
            // Arrange
            var component = new RowComponent();

            // Act & Assert
            var expected = new HtmlTreeBuilder().withNode(Node.DIV).withStyleClass(CssBootstrap.ROW);
            assertRenderResult(component, expected.getDocument(), facesContext);
        }

        @Test
        @DisplayName("Should render component with children correctly")
        void shouldRenderWithChildren(FacesContext facesContext) throws Exception {
            // Arrange
            var component = new RowComponent();
            component.getChildren().add(new HtmlOutputText());

            // Act & Assert
            var expected = new HtmlTreeBuilder().withNode(Node.DIV).withStyleClass(CssBootstrap.ROW)
                    .withNode(Node.SPAN);
            assertRenderResult(component, expected.getDocument(), facesContext);
        }
    }

    @Nested
    @DisplayName("Tests for component states")
    class ComponentStateTests {

        @Test
        @DisplayName("Should handle rendered set to false correctly")
        void shouldHandleRenderedSetToFalse(FacesContext facesContext, ComponentConfigDecorator componentConfig) {
            // Arrange
            var component = new RowComponent();
            component.setRendered(false);
            component.getChildren().add(new HtmlOutputText());
            componentConfig.registerMockRendererForHtmlOutputText();

            // Act & Assert
            assertEmptyRenderResult(component, facesContext);
        }
    }

    @Override
    protected UIComponent getComponent() {
        return new RowComponent();
    }
}
