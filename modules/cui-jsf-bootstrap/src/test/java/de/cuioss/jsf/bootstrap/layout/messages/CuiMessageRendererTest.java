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
package de.cuioss.jsf.bootstrap.layout.messages;

import static jakarta.faces.application.FacesMessage.SEVERITY_WARN;

import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.config.renderer.VetoRenderAttributeAssert;
import de.cuioss.test.jsf.renderer.AbstractComponentRendererTest;
import de.cuioss.test.jsf.renderer.CommonRendererAsserts;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIPanel;
import jakarta.faces.component.html.HtmlInputText;
import jakarta.faces.component.html.HtmlPanelGrid;
import jakarta.faces.context.FacesContext;
import org.jboss.weld.junit5.ExplicitParamInjection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
@ExplicitParamInjection
@VetoRenderAttributeAssert({CommonRendererAsserts.STYLE, CommonRendererAsserts.STYLE_CLASS, CommonRendererAsserts.ID, CommonRendererAsserts.PASSTHROUGH})
@DisplayName("Tests for CuiMessageRenderer")
class CuiMessageRendererTest extends AbstractComponentRendererTest<CuiMessageRenderer> {

    @BeforeEach
    void configureComponents(ComponentConfigDecorator decorator) {
        decorator.registerMockRenderer(UIPanel.COMPONENT_FAMILY, new HtmlPanelGrid().getRendererType());
    }

    @Override
    protected UIComponent getComponent() {
        UIComponent parent = new HtmlPanelGrid();
        var component = new CuiMessageComponent();
        final var htmlInputText = new HtmlInputText();
        htmlInputText.setId("input");
        parent.getChildren().add(htmlInputText);
        component.setParent(parent);
        return component;
    }

    @Override
    @Test
    @DisplayName("Should handle renderer attribute assertions")
    public void shouldHandleRendererAttributeAsserts(FacesContext facesContext) {
        // Arrange
        new ComponentConfigDecorator(facesContext.getApplication(), facesContext)
                .registerMockRenderer(UIPanel.COMPONENT_FAMILY, new HtmlPanelGrid().getRendererType());

        // Act & Assert
        super.shouldHandleRendererAttributeAsserts(facesContext);
    }

    /**
     * Helper method to prepare a violation message for testing
     */
    private void prepareViolationMessage(final String summary, final String detail, FacesContext facesContext) {
        var message = new FacesMessage(SEVERITY_WARN, summary, detail);
        facesContext.addMessage("input", message);
    }

    @Nested
    @DisplayName("Tests for rendering behavior")
    class RenderingTests {

        @Test
        @DisplayName("Should render minimal component with no messages")
        void shouldRenderMinimal(FacesContext facesContext) {
            // Arrange
            var component = new CuiMessageComponent();

            // Act & Assert
            assertEmptyRenderResult(component, facesContext);
        }

        @Test
        @DisplayName("Should render warning message with summary")
        void shouldRenderWarning(FacesContext facesContext) throws Exception {
            // Arrange
            UIComponent parent = new HtmlPanelGrid();
            var component = new CuiMessageComponent();
            final var htmlInputText = new HtmlInputText();
            htmlInputText.setId("input");
            parent.getChildren().add(htmlInputText);
            final var summary = "warning message";
            final var detail = "detail msg";

            // Act
            prepareViolationMessage(summary, detail, facesContext);
            component.setParent(parent);

            // Assert
            var expected = new HtmlTreeBuilder()
                    .withNode(Node.DIV).withStyleClass(CssBootstrap.CUI_MESSAGE)
                    .withAttribute(AttributeName.ARIA_LIVE, "polite")
                    .withNode(Node.SPAN).withStyleClass("cui_msg_warn")
                    .withAttribute(AttributeName.TITLE, detail)
                    .withTextContent(summary);

            assertRenderResult(component, expected.getDocument(), facesContext);
        }

        @Test
        @DisplayName("Should render both summary and detail when showDetail is true")
        void shouldRenderDetail(FacesContext facesContext) throws Exception {
            // Arrange
            UIComponent parent = new HtmlPanelGrid();
            var component = new CuiMessageComponent();
            component.setShowDetail(true);
            final var htmlInputText = new HtmlInputText();
            htmlInputText.setId("input");
            parent.getChildren().add(htmlInputText);
            final var summary = "warning message";
            final var detail = "detail msg";

            // Act
            prepareViolationMessage(summary, detail, facesContext);
            component.setParent(parent);

            // Assert
            var expected = new HtmlTreeBuilder()
                    .withNode(Node.DIV).withStyleClass(CssBootstrap.CUI_MESSAGE)
                    .withAttribute(AttributeName.ARIA_LIVE, "polite")
                    .withNode(Node.SPAN).withStyleClass("cui_msg_warn")
                    .withTextContent(summary + ' ' + detail);

            assertRenderResult(component, expected.getDocument(), facesContext);
        }
    }
}
