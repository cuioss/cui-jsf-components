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

import de.cuioss.jsf.api.components.JsfComponentIdentifier;
import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.AttributeValue;
import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.jsf.bootstrap.icon.IconComponent;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.mocks.CuiMockRenderer;
import de.cuioss.test.jsf.renderer.AbstractComponentRendererTest;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIOutcomeTarget;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.PreRenderComponentEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
@DisplayName("Tests for ButtonRenderer")
class ButtonRendererTest extends AbstractComponentRendererTest<ButtonRenderer> {

    private static final String KEY_BINDING = "enter";

    private static final String ANY_ICON = "cui-icon-user_add";

    private static final String ICONCOMPONENT = "iconcomponent";

    private static final String DEFAULT_BTN_CLASSES = "btn btn-default";

    private static final String TEXT_VALUE = "Some Text";

    @BeforeEach
    void setUp(ComponentConfigDecorator decorator) {
        decorator
                .registerRenderer(UIOutcomeTarget.COMPONENT_FAMILY, JsfComponentIdentifier.BUTTON_RENDERER_TYPE,
                        new CuiMockRenderer("input"))
                .registerUIComponent(IconComponent.class).registerRenderer(BootstrapFamily.COMPONENT_FAMILY,
                BootstrapFamily.ICON_COMPONENT_RENDERER, new CuiMockRenderer(ICONCOMPONENT));
    }

    @Nested
    @DisplayName("Basic rendering tests")
    class BasicRenderingTests {

        @Test
        @DisplayName("Should render minimal button")
        void shouldRenderMinimal(FacesContext facesContext) throws IOException {
            // Arrange
            var component = getComponent();

            // Act & Assert
            var expected = new HtmlTreeBuilder().withNode(Node.BUTTON).withStyleClass(DEFAULT_BTN_CLASSES);
            assertRenderResult(component, expected.getDocument(), facesContext);
        }

        @Test
        @DisplayName("Should decode without errors")
        void shouldDecodeWithoutErrors(FacesContext facesContext) {
            // Arrange
            var component = getComponent();

            // Act & Assert
            assertDoesNotThrow(() -> getRenderer().decode(facesContext, component),
                    "Decoder should not throw exceptions");
        }
    }

    @Nested
    @DisplayName("Style tests")
    class StyleTests {

        @Test
        @DisplayName("Should handle state property")
        void shouldHandleState(FacesContext facesContext) throws IOException {
            // Arrange
            var component = new Button();
            component.setState("info");

            // Act & Assert
            var expected = new HtmlTreeBuilder().withNode(Node.BUTTON).withStyleClass("btn btn-info");
            assertRenderResult(component, expected.getDocument(), facesContext);
        }

        @Test
        @DisplayName("Should handle size property")
        void shouldHandleSize(FacesContext facesContext) throws IOException {
            // Arrange
            var component = new Button();
            component.setSize("lg");

            // Act & Assert
            var expected = new HtmlTreeBuilder().withNode(Node.BUTTON).withStyleClass("btn btn-default btn-lg");
            assertRenderResult(component, expected.getDocument(), facesContext);
        }
    }

    @Nested
    @DisplayName("Content rendering tests")
    class ContentRenderingTests {

        @Test
        @DisplayName("Should handle title property")
        void shouldHandleTitle(FacesContext facesContext) throws IOException {
            // Arrange
            var component = new Button();
            component.setTitleValue(TEXT_VALUE);

            // Act & Assert
            var expected = new HtmlTreeBuilder().withNode(Node.BUTTON).withStyleClass(DEFAULT_BTN_CLASSES)
                    .withAttribute(AttributeName.TITLE, TEXT_VALUE);
            assertRenderResult(component, expected.getDocument(), facesContext);
        }

        @Test
        @DisplayName("Should handle text value")
        void shouldHandleTextValue(FacesContext facesContext) throws IOException {
            // Arrange
            var component = new Button();
            component.setLabelValue(TEXT_VALUE);

            // Act & Assert
            var expected = new HtmlTreeBuilder().withNode(Node.BUTTON).withAttribute(AttributeName.VALUE, TEXT_VALUE)
                    .withStyleClass(DEFAULT_BTN_CLASSES).withNode(Node.SPAN).withStyleClass(CssBootstrap.BUTTON_TEXT)
                    .withTextContent(TEXT_VALUE);
            assertRenderResult(component, expected.getDocument(), facesContext);
        }

        @Test
        @DisplayName("Should handle icon on left")
        void shouldHandleIconOnLeft(FacesContext facesContext) throws IOException {
            // Arrange
            var component = new Button();
            component.setLabelValue(TEXT_VALUE);
            component.setIcon(ANY_ICON);

            // Act & Assert
            var expected = new HtmlTreeBuilder().withNode(Node.BUTTON).withAttribute(AttributeName.VALUE, TEXT_VALUE)
                    .withStyleClass(DEFAULT_BTN_CLASSES).withNode(ICONCOMPONENT).currentHierarchyUp().withNode(Node.SPAN)
                    .withStyleClass(CssBootstrap.BUTTON_TEXT).withTextContent(TEXT_VALUE);
            assertRenderResult(component, expected.getDocument(), facesContext);
        }

        @Test
        @DisplayName("Should handle icon on right")
        void shouldHandleIconOnRight(FacesContext facesContext) throws IOException {
            // Arrange
            var component = new Button();
            component.setLabelValue(TEXT_VALUE);
            component.setIconAlign("right");
            component.setIcon(ANY_ICON);

            // Act & Assert
            var expected = new HtmlTreeBuilder().withNode(Node.BUTTON).withAttribute(AttributeName.VALUE, TEXT_VALUE)
                    .withStyleClass(DEFAULT_BTN_CLASSES).withNode(Node.SPAN).withStyleClass(CssBootstrap.BUTTON_TEXT)
                    .withTextContent(TEXT_VALUE).currentHierarchyUp().withNode(ICONCOMPONENT);
            assertRenderResult(component, expected.getDocument(), facesContext);
        }

        @Test
        @DisplayName("Should handle icon only")
        void shouldHandleIconOnly(FacesContext facesContext) throws IOException {
            // Arrange
            var component = new Button();
            component.setIcon(ANY_ICON);

            // Act & Assert
            var expected = new HtmlTreeBuilder().withNode(Node.BUTTON).withStyleClass(DEFAULT_BTN_CLASSES)
                    .withNode(ICONCOMPONENT);
            assertRenderResult(component, expected.getDocument(), facesContext);
        }
    }

    @Nested
    @DisplayName("Behavior tests")
    class BehaviorTests {

        @Test
        @DisplayName("Should handle key binding")
        void shouldHandleKeyBinding(FacesContext facesContext) throws IOException {
            // Arrange
            var component = new Button();
            component.setKeyBinding(KEY_BINDING);
            component.processEvent(new PreRenderComponentEvent(component));

            // Act & Assert
            var expected = new HtmlTreeBuilder().withNode(Node.BUTTON).withStyleClass(DEFAULT_BTN_CLASSES)
                    .withAttribute(AttributeValue.CUI_CLICK_BINDING.getContent(), KEY_BINDING);
            assertRenderResult(component, expected.getDocument(), facesContext);
        }
    }

    @Override
    protected UIComponent getComponent() {
        return new Button();
    }
}
