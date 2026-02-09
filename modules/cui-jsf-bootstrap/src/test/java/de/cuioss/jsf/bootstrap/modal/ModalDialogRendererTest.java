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
package de.cuioss.jsf.bootstrap.modal;

import static de.cuioss.jsf.api.components.html.AttributeName.*;
import static de.cuioss.jsf.api.components.html.AttributeValue.ROLE_DIALOG;
import static de.cuioss.jsf.api.components.html.Node.DIV;
import static de.cuioss.jsf.api.components.html.Node.SPAN;
import static de.cuioss.jsf.bootstrap.CssBootstrap.*;

import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.AttributeValue;
import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.jsf.bootstrap.modal.support.ModalDialogSize;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.mocks.CuiMockComponent;
import de.cuioss.test.jsf.renderer.AbstractComponentRendererTest;
import jakarta.faces.context.FacesContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
@DisplayName("Tests for ModalDialogRenderer")
class ModalDialogRendererTest extends AbstractComponentRendererTest<ModalDialogRenderer> {

    private final TypedGenerator<String> strings = Generators.letterStrings(1, 12);

    private static final String DEFAULT_ID = "neeededId";

    @BeforeEach
    void setUp(ComponentConfigDecorator decorator) {
        decorator.registerCuiMockComponentWithRenderer();
    }

    @Nested
    @DisplayName("Tests for basic rendering")
    class BasicRenderingTests {

        @Test
        @DisplayName("Should render minimal component correctly")
        void shouldRenderMinimalComponent(FacesContext facesContext) throws Exception {
            // Arrange
            final var component = getComponent();
            final var expected = new HtmlTreeBuilder();

            // Act & Assert
            addDefaultNode(expected);
            expected.withNode(DIV).withStyleClass(MODAL_DIALOG_HEADER);
            addCloseButtonNode(expected);
            expected.currentHierarchyUp();
            expected.withNode(DIV).withStyleClass(MODAL_DIALOG_BODY);
            assertRenderResult(component, expected.getDocument(), facesContext);
        }

        @Test
        @DisplayName("Should render component with children correctly")
        void shouldRenderWithChildren(FacesContext facesContext) throws Exception {
            // Arrange
            final var component = getComponent();
            component.getChildren().add(new CuiMockComponent());
            final var expected = new HtmlTreeBuilder();

            // Act & Assert
            addDefaultNode(expected);
            expected.withNode(DIV).withStyleClass(MODAL_DIALOG_HEADER);
            addCloseButtonNode(expected);
            expected.currentHierarchyUp();
            expected.withNode(DIV).withStyleClass(MODAL_DIALOG_BODY);
            expected.withNode(CuiMockComponent.class.getSimpleName());
            assertRenderResult(component, expected.getDocument(), facesContext);
        }
    }

    @Nested
    @DisplayName("Tests for header rendering")
    class HeaderRenderingTests {

        @Test
        @DisplayName("Should render component with header value correctly")
        void shouldRenderWithHeaderValue(FacesContext facesContext) throws Exception {
            // Arrange
            final var string = strings.next();
            final var component = getComponent();
            component.setHeaderValue(string);
            final var expected = new HtmlTreeBuilder();

            // Act & Assert
            addDefaultNode(expected);
            expected.withNode(DIV).withStyleClass(MODAL_DIALOG_HEADER);
            addCloseButtonNode(expected);
            expected.withNode(Node.H4).withStyleClass(CssBootstrap.MODAL_DIALOG_TITLE).withTextContent(string)
                    .currentHierarchyUp();
            expected.currentHierarchyUp();
            expected.withNode(DIV).withStyleClass(MODAL_DIALOG_BODY);
            assertRenderResult(component, expected.getDocument(), facesContext);
        }

        @Test
        @DisplayName("Should render component with header facet correctly")
        void shouldRenderWithHeaderFacet(FacesContext facesContext) throws Exception {
            // Arrange
            final var component = getComponent();
            component.getFacets().put("header", new CuiMockComponent());
            final var expected = new HtmlTreeBuilder();

            // Act & Assert
            addDefaultNode(expected);
            expected.withNode(DIV).withStyleClass(MODAL_DIALOG_HEADER);
            addCloseButtonNode(expected);
            expected.withNode(CuiMockComponent.class.getSimpleName()).currentHierarchyUp();
            expected.currentHierarchyUp();
            expected.withNode(DIV).withStyleClass(MODAL_DIALOG_BODY);
            assertRenderResult(component, expected.getDocument(), facesContext);
        }
    }

    @Nested
    @DisplayName("Tests for footer rendering")
    class FooterRenderingTests {

        @Test
        @DisplayName("Should render component with footer value correctly")
        void shouldRenderWithFooterValue(FacesContext facesContext) throws Exception {
            // Arrange
            final var string = strings.next();
            final var component = getComponent();
            component.setFooterValue(string);
            final var expected = new HtmlTreeBuilder();

            // Act & Assert
            addDefaultNode(expected);
            expected.withNode(DIV).withStyleClass(MODAL_DIALOG_HEADER);
            addCloseButtonNode(expected);
            expected.currentHierarchyUp();
            expected.withNode(DIV).withStyleClass(MODAL_DIALOG_BODY);
            expected.currentHierarchyUp();
            expected.withNode(DIV).withStyleClass(MODAL_DIALOG_FOOTER).withNode(SPAN)
                    .withStyleClass(MODAL_DIALOG_FOOTER_TEXT).withTextContent(string).currentHierarchyUp();
            assertRenderResult(component, expected.getDocument(), facesContext);
        }

        @Test
        @DisplayName("Should render component with footer facet correctly")
        void shouldRenderWithFooterFacet(FacesContext facesContext) throws Exception {
            // Arrange
            final var component = getComponent();
            component.getFacets().put("footer", new CuiMockComponent());
            final var expected = new HtmlTreeBuilder();

            // Act & Assert
            addDefaultNode(expected);
            expected.withNode(DIV).withStyleClass(MODAL_DIALOG_HEADER);
            addCloseButtonNode(expected);
            expected.currentHierarchyUp();
            expected.withNode(DIV).withStyleClass(MODAL_DIALOG_BODY);
            expected.currentHierarchyUp();
            expected.withNode(DIV).withStyleClass(MODAL_DIALOG_FOOTER);
            expected.withNode(CuiMockComponent.class.getSimpleName()).currentHierarchyUp();
            assertRenderResult(component, expected.getDocument(), facesContext);
        }
    }

    @Nested
    @DisplayName("Tests for configuration options")
    class ConfigurationTests {

        @Test
        @DisplayName("Should render component without close button when closable is false")
        void shouldRenderWithoutCloseButton(FacesContext facesContext) throws Exception {
            // Arrange
            final var component = getComponent();
            component.setClosable(false);
            final var expected = new HtmlTreeBuilder();

            // Act & Assert
            expected.withNode(Node.DIV).withAttributeNameAndId(DEFAULT_ID).withStyleClass("modal modal-default")
                    .withAttribute(DATA_MODAL_ID, DEFAULT_ID).withAttribute(TABINDEX, "-1")
                    .withAttribute(AttributeName.DATA_BACKDROP, "static").withAttribute(ROLE, ROLE_DIALOG);
            expected.withNode(Node.DIV).withStyleClass(CssBootstrap.MODAL_DIALOG).withAttribute(ROLE, "document");
            expected.withNode(Node.DIV).withStyleClass(CssBootstrap.MODAL_CONTENT);
            expected.withNode(DIV).withStyleClass(MODAL_DIALOG_HEADER);
            expected.currentHierarchyUp();
            expected.withNode(DIV).withStyleClass(MODAL_DIALOG_BODY);
            assertRenderResult(component, expected.getDocument(), facesContext);
        }

        @Test
        @DisplayName("Should render component with specified size correctly")
        void shouldRenderWithSpecifiedSize(FacesContext facesContext) throws Exception {
            // Arrange
            final var component = getComponent();
            component.setSize("fluid");
            final var expected = new HtmlTreeBuilder();

            // Act & Assert
            addDefaultNodeWithSize(expected, ModalDialogSize.FLUID);
            expected.withNode(DIV).withStyleClass(MODAL_DIALOG_HEADER);
            addCloseButtonNode(expected);
            expected.currentHierarchyUp();
            expected.withNode(DIV).withStyleClass(MODAL_DIALOG_BODY);
            assertRenderResult(component, expected.getDocument(), facesContext);
        }
    }

    private static void addCloseButtonNode(final HtmlTreeBuilder expected) {
        expected.withNode(Node.BUTTON);
        expected.withAttribute(AttributeName.ARIA_LABEL, AttributeValue.ARIA_CLOSE);
        expected.withAttribute(AttributeName.DATA_DISMISS, "modal");
        expected.withAttribute(AttributeName.TYPE, AttributeValue.INPUT_BUTTON);
        expected.withStyleClass(CssBootstrap.BUTTON_CLOSE);
        // Containing span
        expected.withNode(Node.SPAN);
        expected.withAttribute(AttributeName.ARIA_HIDDEN, AttributeValue.TRUE);
        expected.withTextContent("×");
        expected.currentHierarchyUp().currentHierarchyUp();
    }

    private static void addDefaultNode(final HtmlTreeBuilder expected) {
        addDefaultNodeWithSize(expected, null);
    }

    private static void addDefaultNodeWithSize(final HtmlTreeBuilder expected, final ModalDialogSize size) {
        expected.withNode(Node.DIV).withAttributeNameAndId(DEFAULT_ID).withStyleClass("modal modal-default")
                .withAttribute(DATA_MODAL_ID, DEFAULT_ID).withAttribute(TABINDEX, "-1")
                .withAttribute(ROLE, ROLE_DIALOG);
        expected.withNode(Node.DIV).withStyleClass(CssBootstrap.MODAL_DIALOG).withAttribute(ROLE, "document");
        if (null != size) {
            expected.withStyleClass(CssBootstrap.MODAL_DIALOG.getStyleClassBuilder().append(size.getStyleClass()));
        }
        expected.withNode(Node.DIV).withStyleClass(CssBootstrap.MODAL_CONTENT);
    }

    @Override
    protected ModalDialogComponent getComponent() {
        final var component = new ModalDialogComponent();
        component.setId(DEFAULT_ID);
        return component;
    }

}
