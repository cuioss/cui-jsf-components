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
import de.cuioss.jsf.bootstrap.common.partial.ColumnCssResolver;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.renderer.AbstractComponentRendererTest;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.html.HtmlOutputText;
import jakarta.faces.context.FacesContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
@DisplayName("Tests for ControlGroupRenderer")
class ControlGroupRendererTest extends AbstractComponentRendererTest<ControlGroupRenderer> {

    private static final String MINIMAL_COLUMN_CSS = ColumnCssResolver.COL_PREFIX + "8";

    @Override
    protected UIComponent getComponent() {
        return new ControlGroupComponent();
    }

    @Nested
    @DisplayName("Tests for rendering behavior")
    class RenderingTests {

        @Test
        @DisplayName("Should render minimal component correctly")
        void shouldRenderMinimal(FacesContext facesContext) throws Exception {
            // Arrange
            final var component = new ControlGroupComponent();

            // Act & Assert
            final var expected = new HtmlTreeBuilder()
                    .withNode(Node.DIV).withStyleClass(CssBootstrap.FORM_GROUP)
                    .withNode(Node.DIV).withStyleClass(MINIMAL_COLUMN_CSS);

            assertRenderResult(component, expected.getDocument(), facesContext);
        }

        @Test
        @DisplayName("Should render component with children correctly")
        void shouldRenderWithChildren(FacesContext facesContext) throws Exception {
            // Arrange
            final var component = new ControlGroupComponent();
            component.getChildren().add(new HtmlOutputText());

            // Act & Assert
            final var expected = new HtmlTreeBuilder()
                    .withNode(Node.DIV).withStyleClass(CssBootstrap.FORM_GROUP)
                    .withNode(Node.DIV).withStyleClass(MINIMAL_COLUMN_CSS)
                    .withNode(Node.SPAN);

            assertRenderResult(component, expected.getDocument(), facesContext);
        }
    }
}
