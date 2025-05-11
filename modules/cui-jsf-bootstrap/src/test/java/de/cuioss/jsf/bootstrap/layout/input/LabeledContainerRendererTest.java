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
package de.cuioss.jsf.bootstrap.layout.input;

import static de.cuioss.jsf.bootstrap.common.partial.ColumnCssResolver.COL_OFFSET_PREFIX;
import static de.cuioss.jsf.bootstrap.layout.input.HelpTextComponent.DATA_HELP_BLOCK;
import static de.cuioss.jsf.bootstrap.layout.input.LabeledContainerComponent.DATA_LABELED_CONTAINER;

import de.cuioss.jsf.api.components.css.impl.StyleClassBuilderImpl;
import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.jsf.bootstrap.common.partial.ColumnCssResolver;
import de.cuioss.jsf.bootstrap.layout.LayoutMode;
import de.cuioss.jsf.bootstrap.layout.messages.CuiMessageComponent;
import de.cuioss.jsf.bootstrap.layout.messages.CuiMessageRenderer;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.renderer.AbstractComponentRendererTest;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.html.*;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.PostAddToViewEvent;
import jakarta.faces.event.PreRenderComponentEvent;
import jakarta.faces.view.ViewDeclarationLanguage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * Expectation for renderer cycle :
 * <ul>
 * <li>case the response to be rendered to the client (<b>ignore</b> case :
 * state of the response to be saved for processing on subsequent requests)</li>
 * <li>(<b>ignore</b>
 * {@linkplain ViewDeclarationLanguage#buildView(FacesContext, jakarta.faces.component.UIViewRoot)})
 * </li>
 * <li>Publish the jakarta.faces.event.PreRenderViewEvent</li>
 * <li>renderer.encodeBegin(facesContext, component)</li>
 * <li>renderer.encodeChildren(facesContext, component)</li>
 * <li>renderer.encodeEnd(facesContext, component)</li>
 * </ul>
 *
 * @author Eugen Fischer
 */

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
@DisplayName("Tests for LabeledContainerRenderer")
class LabeledContainerRendererTest extends AbstractComponentRendererTest<LabeledContainerRenderer> {

    private static final String INPUT = "input";

    private static final String COLON_INPUT = ":input";

    private static final String NEED_SOME_HELP = "Need some Help";

    private static final String CLIENT_ID = "j_id__v_0";

    private static final String CLIENT_ID_LABEL = CLIENT_ID + "_label";

    private static final String COL_8 = ColumnCssResolver.COL_PREFIX + "8";

    private static final String COL_OFFSET_4 = COL_OFFSET_PREFIX + "4";

    private static final String COL_4 = ColumnCssResolver.COL_PREFIX + "4";

    private static final String LABELED_CONTAINER_ID = "labeled-container-id";

    private static final String LABELED_CONTAINER_ID_LABEL = LABELED_CONTAINER_ID + "_label";

    private static final String LABELED_CONTAINER_ID_INPUT = LABELED_CONTAINER_ID + COLON_INPUT;

    @Nested
    @DisplayName("Tests for basic rendering")
    class BasicRenderingTests {

        @Test
        @DisplayName("Should render minimal component correctly")
        void shouldRenderMinimal(FacesContext facesContext) throws IOException {
            // Arrange
            final var component = new LabeledContainerComponent();
            component.processEvent(new PostAddToViewEvent(component));
            component.processEvent(new PreRenderComponentEvent(component));

            // Act & Assert
            final var expected = new HtmlTreeBuilder().withNode(Node.DIV).withStyleClass(CssBootstrap.FORM_GROUP)
                    .withAttribute(DATA_LABELED_CONTAINER, DATA_LABELED_CONTAINER).withNode(Node.LABEL)
                    .withAttribute(AttributeName.ID, CLIENT_ID_LABEL).withAttribute(AttributeName.NAME, CLIENT_ID_LABEL)
                    .withStyleClass(new StyleClassBuilderImpl(COL_4).append(CssBootstrap.CONTROL_LABEL).getStyleClass())
                    .currentHierarchyUp().withNode(Node.DIV).withStyleClass(COL_8).withNode(Node.P)
                    .withStyleClass(CssBootstrap.FORM_CONTROL_STATIC).currentHierarchyUp().currentHierarchyUp();
            assertRenderResult(component, expected.getDocument(), facesContext);
        }

        @Test
        @DisplayName("Should render component with input correctly")
        void shouldRenderMinimalWithInput(FacesContext facesContext) throws IOException {
            // Arrange
            final var htmlInputText = new HtmlInputText();
            htmlInputText.setId(INPUT);
            final var component = new LabeledContainerComponent();
            component.getChildren().add(htmlInputText);
            component.processEvent(new PostAddToViewEvent(component));
            component.processEvent(new PreRenderComponentEvent(component));

            // Act & Assert
            final var expected = new HtmlTreeBuilder().withNode(Node.DIV).withStyleClass(CssBootstrap.FORM_GROUP)
                    .withAttribute(DATA_LABELED_CONTAINER, DATA_LABELED_CONTAINER).withNode(Node.LABEL)
                    .withAttribute(AttributeName.ID, CLIENT_ID_LABEL).withAttribute(AttributeName.NAME, CLIENT_ID_LABEL)
                    .withAttribute(AttributeName.FOR, CLIENT_ID + COLON_INPUT)
                    .withStyleClass(new StyleClassBuilderImpl(COL_4).append(CssBootstrap.CONTROL_LABEL).getStyleClass())
                    .currentHierarchyUp().withNode(Node.DIV).withStyleClass(COL_8).withNode(Node.INPUT)
                    .withAttribute(AttributeName.ID, CLIENT_ID + COLON_INPUT)
                    .withAttribute(AttributeName.NAME, CLIENT_ID + COLON_INPUT).withAttribute(AttributeName.TYPE, "text")
                    .withStyleClass(CssBootstrap.FORM_CONTROL).currentHierarchyUp().currentHierarchyUp();
            assertRenderResult(component, expected.getDocument(), facesContext);
        }

        @Test
        @DisplayName("Should render component with non-rendered input correctly")
        void shouldRenderMinimalWithInputRenderedFalse(FacesContext facesContext) throws IOException {
            // Arrange
            final var htmlInputText = new HtmlInputText();
            htmlInputText.setId(INPUT);
            htmlInputText.setRendered(false);
            final var component = new LabeledContainerComponent();
            component.getChildren().add(htmlInputText);
            component.processEvent(new PostAddToViewEvent(component));
            component.processEvent(new PreRenderComponentEvent(component));

            // Act & Assert
            final var expected = new HtmlTreeBuilder().withNode(Node.DIV).withStyleClass(CssBootstrap.FORM_GROUP)
                    .withAttribute(DATA_LABELED_CONTAINER, DATA_LABELED_CONTAINER).withNode(Node.LABEL)
                    .withAttribute(AttributeName.ID, CLIENT_ID_LABEL).withAttribute(AttributeName.NAME, CLIENT_ID_LABEL)
                    .withStyleClass(new StyleClassBuilderImpl(COL_4).append(CssBootstrap.CONTROL_LABEL).getStyleClass())
                    .currentHierarchyUp().withNode(Node.DIV).withStyleClass(COL_8).withNode(Node.P)
                    .withStyleClass(CssBootstrap.FORM_CONTROL_STATIC).currentHierarchyUp().currentHierarchyUp();
            assertRenderResult(component, expected.getDocument(), facesContext);
        }

        @Test
        @DisplayName("Should render component with hidden input correctly")
        void shouldRenderMinimalWithHiddenInput(FacesContext facesContext) throws IOException {
            // Arrange
            final var htmlInputText = new HtmlInputHidden();
            htmlInputText.setId(INPUT);
            final var component = new LabeledContainerComponent();
            component.getChildren().add(htmlInputText);
            component.processEvent(new PostAddToViewEvent(component));
            component.processEvent(new PreRenderComponentEvent(component));

            // Act & Assert
            final var expected = new HtmlTreeBuilder().withNode(Node.DIV).withStyleClass(CssBootstrap.FORM_GROUP)
                    .withAttribute(DATA_LABELED_CONTAINER, DATA_LABELED_CONTAINER).withNode(Node.LABEL)
                    .withAttribute(AttributeName.ID, CLIENT_ID_LABEL).withAttribute(AttributeName.NAME, CLIENT_ID_LABEL)
                    .withStyleClass(new StyleClassBuilderImpl(COL_4).append(CssBootstrap.CONTROL_LABEL).getStyleClass())
                    .currentHierarchyUp().withNode(Node.DIV).withStyleClass(COL_8).withNode(Node.P)
                    .withStyleClass(CssBootstrap.FORM_CONTROL_STATIC).withNode(Node.INPUT)
                    .withAttribute(AttributeName.ID, CLIENT_ID + COLON_INPUT)
                    .withAttribute(AttributeName.NAME, CLIENT_ID + COLON_INPUT).currentHierarchyUp().currentHierarchyUp();
            assertRenderResult(component, expected.getDocument(), facesContext);
        }
    }

    @Nested
    @DisplayName("Tests for component attributes and states")
    class ComponentAttributesAndStatesTests {

        @Test
        @DisplayName("Should render component with disabled flag correctly")
        void shouldRenderDisabledFlag(FacesContext facesContext) throws IOException {
            // Arrange
            final var htmlInputText = new HtmlInputText();
            htmlInputText.setId(INPUT);
            final var component = new LabeledContainerComponent();
            component.getChildren().add(htmlInputText);
            component.setDisabled(true);
            component.processEvent(new PostAddToViewEvent(component));
            component.processEvent(new PreRenderComponentEvent(component));

            // Act & Assert
            final var expected = new HtmlTreeBuilder().withNode(Node.DIV).withStyleClass(CssBootstrap.FORM_GROUP)
                    .withAttribute(DATA_LABELED_CONTAINER, DATA_LABELED_CONTAINER).withNode(Node.LABEL)
                    .withAttribute(AttributeName.ID, CLIENT_ID_LABEL).withAttribute(AttributeName.NAME, CLIENT_ID_LABEL)
                    .withAttribute(AttributeName.FOR, CLIENT_ID + COLON_INPUT)
                    .withStyleClass(new StyleClassBuilderImpl(COL_4).append(CssBootstrap.CONTROL_LABEL).getStyleClass())
                    .currentHierarchyUp().withNode(Node.DIV).withStyleClass(COL_8).withNode(Node.INPUT)
                    .withAttribute(AttributeName.ID, CLIENT_ID + COLON_INPUT)
                    .withAttribute(AttributeName.DISABLED, "disabled")
                    .withAttribute(AttributeName.NAME, CLIENT_ID + COLON_INPUT).withAttribute(AttributeName.TYPE, "text")
                    .withStyleClass(CssBootstrap.FORM_CONTROL).currentHierarchyUp().currentHierarchyUp();
            assertRenderResult(component, expected.getDocument(), facesContext);
        }

        @Test
        @DisplayName("Should not render component when rendered is false")
        void shouldNotRenderMinimalWithInput(FacesContext facesContext) {
            // Arrange
            final var htmlInputText = new HtmlInputText();
            htmlInputText.setId(INPUT);
            final var component = new LabeledContainerComponent();
            component.setRendered(false);
            component.getChildren().add(htmlInputText);
            component.processEvent(new PostAddToViewEvent(component));
            component.processEvent(new PreRenderComponentEvent(component));

            // Act & Assert
            assertEmptyRenderResult(component, facesContext);
        }

        @Test
        @DisplayName("Should render component with title attribute correctly")
        void shouldRenderTitle(FacesContext facesContext) throws IOException {
            // Arrange
            final var component = new LabeledContainerComponent();
            final var titleValue = "titleValue";
            component.setTitleValue(titleValue);
            component.processEvent(new PostAddToViewEvent(component));
            component.processEvent(new PreRenderComponentEvent(component));

            // Act & Assert
            final var expected = new HtmlTreeBuilder().withNode(Node.DIV).withAttribute(AttributeName.TITLE, titleValue)
                    .withStyleClass(CssBootstrap.FORM_GROUP).withAttribute(DATA_LABELED_CONTAINER, DATA_LABELED_CONTAINER)
                    .withNode(Node.LABEL).withAttribute(AttributeName.ID, CLIENT_ID_LABEL)
                    .withAttribute(AttributeName.NAME, CLIENT_ID_LABEL)
                    .withStyleClass(new StyleClassBuilderImpl(COL_4).append(CssBootstrap.CONTROL_LABEL).getStyleClass())
                    .currentHierarchyUp().withNode(Node.DIV).withStyleClass(COL_8).withNode(Node.P)
                    .withStyleClass(CssBootstrap.FORM_CONTROL_STATIC).currentHierarchyUp().currentHierarchyUp();
            assertRenderResult(component, expected.getDocument(), facesContext);
        }
    }

    @Nested
    @DisplayName("Tests for special input types")
    class SpecialInputTypesTests {

        @Test
        @DisplayName("Should render component with checkbox correctly")
        void shouldRenderMinimalWithCheckbox(FacesContext facesContext) throws IOException {
            // Arrange
            final var component = new LabeledContainerComponent();
            final var htmlInputText = new HtmlSelectBooleanCheckbox();
            htmlInputText.setId(INPUT);
            component.getChildren().add(htmlInputText);
            component.processEvent(new PostAddToViewEvent(component));
            component.processEvent(new PreRenderComponentEvent(component));

            // Act & Assert
            final var expected = new HtmlTreeBuilder().withNode(Node.DIV).withStyleClass(CssBootstrap.FORM_GROUP)
                    .withAttribute(DATA_LABELED_CONTAINER, DATA_LABELED_CONTAINER).withNode(Node.DIV)
                    .withStyleClass(new StyleClassBuilderImpl(COL_8).append(COL_OFFSET_4).getStyleClass())
                    .withNode(Node.DIV).withStyleClass(CssBootstrap.CHECKBOX).withNode(Node.LABEL)
                    .withAttribute(AttributeName.ID, CLIENT_ID_LABEL).withAttribute(AttributeName.NAME, CLIENT_ID_LABEL)
                    .withNode(Node.INPUT).withAttribute(AttributeName.ID, CLIENT_ID + COLON_INPUT)
                    .withAttribute(AttributeName.NAME, CLIENT_ID + COLON_INPUT).currentHierarchyUp().currentHierarchyUp()
                    .currentHierarchyUp();
            assertRenderResult(component, expected.getDocument(), facesContext);
        }
    }

    @Nested
    @DisplayName("Tests for ID handling")
    class IdHandlingTests {

        @Test
        @DisplayName("Should render component with ID and input correctly")
        void shouldRenderIdIfSetWithInput(FacesContext facesContext) throws IOException {
            // Arrange
            final var component = new LabeledContainerComponent();
            component.setId(LABELED_CONTAINER_ID);
            final var htmlInputText = new HtmlInputText();
            htmlInputText.setId(INPUT);
            component.getChildren().add(htmlInputText);
            component.processEvent(new PostAddToViewEvent(component));
            component.processEvent(new PreRenderComponentEvent(component));

            // Act & Assert
            final var expected = new HtmlTreeBuilder().withNode(Node.DIV)
                    .withAttribute(DATA_LABELED_CONTAINER, DATA_LABELED_CONTAINER)
                    .withAttribute(AttributeName.ID, LABELED_CONTAINER_ID)
                    .withAttribute(AttributeName.NAME, LABELED_CONTAINER_ID).withStyleClass(CssBootstrap.FORM_GROUP)
                    .withNode(Node.LABEL).withAttribute(AttributeName.ID, LABELED_CONTAINER_ID_LABEL)
                    .withAttribute(AttributeName.NAME, LABELED_CONTAINER_ID_LABEL)
                    .withAttribute(AttributeName.FOR, LABELED_CONTAINER_ID_INPUT)
                    .withStyleClass(new StyleClassBuilderImpl(COL_4).append(CssBootstrap.CONTROL_LABEL).getStyleClass())
                    .currentHierarchyUp().withNode(Node.DIV).withStyleClass(COL_8).withNode(Node.INPUT)
                    .withAttribute(AttributeName.ID, LABELED_CONTAINER_ID_INPUT)
                    .withAttribute(AttributeName.NAME, LABELED_CONTAINER_ID_INPUT).withAttribute(AttributeName.TYPE, "text")
                    .withStyleClass(CssBootstrap.FORM_CONTROL).currentHierarchyUp().currentHierarchyUp();
            assertRenderResult(component, expected.getDocument(), facesContext);
        }
    }

    @Nested
    @DisplayName("Tests for facet handling")
    class FacetHandlingTests {

        @Test
        @DisplayName("Should render component with ID and prepend facet correctly")
        void shouldRenderIdIfSetWithInputAndPrependFacet(FacesContext facesContext) throws IOException {
            // Arrange
            final var component = new LabeledContainerComponent();
            component.setId(LABELED_CONTAINER_ID);
            final var htmlInputText = new HtmlInputText();
            htmlInputText.setId(INPUT);
            component.getChildren().add(htmlInputText);
            final var prepend = new HtmlOutputText();
            final var prependId = "prependId";
            prepend.setId(prependId);
            final var prependClientId = LABELED_CONTAINER_ID + ":" + prependId;
            component.getFacets().put("prepend", prepend);
            component.processEvent(new PostAddToViewEvent(component));
            component.processEvent(new PreRenderComponentEvent(component));

            // Act & Assert
            final var expected = new HtmlTreeBuilder().withNode(Node.DIV)
                    .withAttribute(DATA_LABELED_CONTAINER, DATA_LABELED_CONTAINER)
                    .withAttribute(AttributeName.ID, LABELED_CONTAINER_ID)
                    .withAttribute(AttributeName.NAME, LABELED_CONTAINER_ID).withStyleClass(CssBootstrap.FORM_GROUP)
                    .withNode(Node.LABEL).withAttribute(AttributeName.ID, LABELED_CONTAINER_ID_LABEL)
                    .withAttribute(AttributeName.NAME, LABELED_CONTAINER_ID_LABEL)
                    .withAttribute(AttributeName.FOR, LABELED_CONTAINER_ID_INPUT)
                    .withStyleClass(new StyleClassBuilderImpl(COL_4).append(CssBootstrap.CONTROL_LABEL).getStyleClass())
                    .currentHierarchyUp().withNode(Node.DIV).withStyleClass(COL_8).withNode(Node.DIV)
                    .withStyleClass(CssBootstrap.INPUT_GROUP).withNode(Node.DIV)
                    .withStyleClass(CssBootstrap.INPUT_GROUP_ADDON).withNode(Node.SPAN)
                    .withAttribute(AttributeName.ID, prependClientId).withAttribute(AttributeName.NAME, prependClientId)
                    .currentHierarchyUp().currentHierarchyUp().withNode(Node.INPUT)
                    .withAttribute(AttributeName.ID, LABELED_CONTAINER_ID_INPUT)
                    .withAttribute(AttributeName.NAME, LABELED_CONTAINER_ID_INPUT).withAttribute(AttributeName.TYPE, "text")
                    .withStyleClass(CssBootstrap.FORM_CONTROL).currentHierarchyUp().currentHierarchyUp()
                    .currentHierarchyUp();
            assertRenderResult(component, expected.getDocument(), facesContext);
        }

        @Test
        @DisplayName("Should render component with ID and label facet correctly")
        void shouldRenderIdIfSetWithInputAndLabelFacet(FacesContext facesContext, ComponentConfigDecorator componentConfig) throws IOException {
            // Arrange
            final var component = new LabeledContainerComponent();
            component.setId(LABELED_CONTAINER_ID);
            final var htmlInputText = new HtmlInputText();
            htmlInputText.setId(INPUT);
            component.getChildren().add(htmlInputText);
            final var label = new HtmlOutputLink();
            final var labelId = "labelId";
            label.setId(labelId);
            final var labelClientId = LABELED_CONTAINER_ID + ":" + labelId;
            componentConfig.registerMockRendererForHtmlOutputText();
            component.getFacets().put("label", label);
            component.processEvent(new PostAddToViewEvent(component));
            component.processEvent(new PreRenderComponentEvent(component));

            // Act & Assert
            final var expected = new HtmlTreeBuilder().withNode(Node.DIV)
                    .withAttribute(DATA_LABELED_CONTAINER, DATA_LABELED_CONTAINER)
                    .withAttribute(AttributeName.ID, LABELED_CONTAINER_ID)
                    .withAttribute(AttributeName.NAME, LABELED_CONTAINER_ID).withStyleClass(CssBootstrap.FORM_GROUP)
                    .withNode(Node.LABEL).withAttribute(AttributeName.ID, LABELED_CONTAINER_ID_LABEL)
                    .withAttribute(AttributeName.NAME, LABELED_CONTAINER_ID_LABEL)
                    .withAttribute(AttributeName.FOR, LABELED_CONTAINER_ID_INPUT)
                    .withStyleClass(new StyleClassBuilderImpl(COL_4).append(CssBootstrap.CONTROL_LABEL).getStyleClass())
                    .withNode(Node.A).withAttribute(AttributeName.ID, labelClientId)
                    .withAttribute(AttributeName.NAME, labelClientId).currentHierarchyUp().currentHierarchyUp()
                    .withNode(Node.DIV).withStyleClass(COL_8).withNode(Node.INPUT)
                    .withAttribute(AttributeName.ID, LABELED_CONTAINER_ID_INPUT)
                    .withAttribute(AttributeName.NAME, LABELED_CONTAINER_ID_INPUT).withAttribute(AttributeName.TYPE, "text")
                    .withStyleClass(CssBootstrap.FORM_CONTROL).currentHierarchyUp().currentHierarchyUp();
            assertRenderResult(component, expected.getDocument(), facesContext);
        }
    }

    @Nested
    @DisplayName("Tests for validation state")
    class ValidationStateTests {

        @Test
        @DisplayName("Should render component with invalid input correctly")
        void shouldRenderWithInvalidInput(FacesContext facesContext) throws IOException {
            // Arrange
            final var component = new LabeledContainerComponent();
            component.setId(LABELED_CONTAINER_ID);
            final var htmlInputText = new HtmlInputText();
            htmlInputText.setId(INPUT);
            htmlInputText.setValid(false);
            component.getChildren().add(htmlInputText);
            component.processEvent(new PostAddToViewEvent(component));
            component.processEvent(new PreRenderComponentEvent(component));

            // Act & Assert
            final var expected = new HtmlTreeBuilder().withNode(Node.DIV)
                    .withAttribute(DATA_LABELED_CONTAINER, DATA_LABELED_CONTAINER)
                    .withAttribute(AttributeName.ID, LABELED_CONTAINER_ID)
                    .withAttribute(AttributeName.NAME, LABELED_CONTAINER_ID)
                    .withStyleClass(CssBootstrap.FORM_GROUP.getStyleClassBuilder().append(CssBootstrap.HAS_ERROR))
                    .withNode(Node.LABEL).withAttribute(AttributeName.ID, LABELED_CONTAINER_ID_LABEL)
                    .withAttribute(AttributeName.NAME, LABELED_CONTAINER_ID_LABEL)
                    .withAttribute(AttributeName.FOR, LABELED_CONTAINER_ID_INPUT)
                    .withStyleClass(new StyleClassBuilderImpl(COL_4).append(CssBootstrap.CONTROL_LABEL).getStyleClass())
                    .currentHierarchyUp().withNode(Node.DIV).withStyleClass(COL_8).withNode(Node.INPUT)
                    .withAttribute(AttributeName.ID, LABELED_CONTAINER_ID_INPUT)
                    .withAttribute(AttributeName.NAME, LABELED_CONTAINER_ID_INPUT).withAttribute(AttributeName.TYPE, "text")
                    .withStyleClass(CssBootstrap.FORM_CONTROL).currentHierarchyUp().currentHierarchyUp();
            assertRenderResult(component, expected.getDocument(), facesContext);
        }
    }

    @Nested
    @DisplayName("Tests for layout modes")
    class LayoutModeTests {

        @Test
        @DisplayName("Should render component with PLAIN layout mode correctly")
        void shouldRenderWithLayoutModePlain(FacesContext facesContext) throws IOException {
            // Arrange
            final var component = new LabeledContainerComponent();
            component.setId(LABELED_CONTAINER_ID);
            component.setLayoutMode(LayoutMode.PLAIN.name());
            component.processEvent(new PostAddToViewEvent(component));
            component.processEvent(new PreRenderComponentEvent(component));

            // Act & Assert
            final var expected = new HtmlTreeBuilder().withNode(Node.DIV)
                    .withAttribute(DATA_LABELED_CONTAINER, DATA_LABELED_CONTAINER)
                    .withAttribute(AttributeName.ID, LABELED_CONTAINER_ID)
                    .withAttribute(AttributeName.NAME, LABELED_CONTAINER_ID).withNode(Node.LABEL)
                    .withAttribute(AttributeName.ID, LABELED_CONTAINER_ID_LABEL)
                    .withAttribute(AttributeName.NAME, LABELED_CONTAINER_ID_LABEL)
                    .withStyleClass(CssBootstrap.CONTROL_LABEL).currentHierarchyUp().withNode(Node.P)
                    .withStyleClass(CssBootstrap.FORM_CONTROL_STATIC).currentHierarchyUp().currentHierarchyUp();
            assertRenderResult(component, expected.getDocument(), facesContext);
        }

        @Test
        @DisplayName("Should render component with PLAIN layout mode and input correctly")
        void shouldRenderWithLayoutModePlainWithInput(FacesContext facesContext) throws IOException {
            // Arrange
            final var component = new LabeledContainerComponent();
            component.setId(LABELED_CONTAINER_ID);
            component.setLayoutMode(LayoutMode.PLAIN.name());
            final var htmlInputText = new HtmlInputText();
            htmlInputText.setId(INPUT);
            component.getChildren().add(htmlInputText);
            component.processEvent(new PostAddToViewEvent(component));
            component.processEvent(new PreRenderComponentEvent(component));

            // Act & Assert
            final var expected = new HtmlTreeBuilder().withNode(Node.DIV)
                    .withAttribute(DATA_LABELED_CONTAINER, DATA_LABELED_CONTAINER)
                    .withAttribute(AttributeName.ID, LABELED_CONTAINER_ID)
                    .withAttribute(AttributeName.NAME, LABELED_CONTAINER_ID).withNode(Node.LABEL)
                    .withAttribute(AttributeName.ID, LABELED_CONTAINER_ID_LABEL)
                    .withAttribute(AttributeName.NAME, LABELED_CONTAINER_ID_LABEL)
                    .withAttribute(AttributeName.FOR, LABELED_CONTAINER_ID_INPUT).withStyleClass(CssBootstrap.CONTROL_LABEL)
                    .currentHierarchyUp().withNode(Node.INPUT)
                    .withAttribute(AttributeName.ID, LABELED_CONTAINER_ID_INPUT)
                    .withAttribute(AttributeName.NAME, LABELED_CONTAINER_ID_INPUT).withAttribute(AttributeName.TYPE, "text")
                    .withStyleClass(CssBootstrap.FORM_CONTROL).currentHierarchyUp().currentHierarchyUp();
            assertRenderResult(component, expected.getDocument(), facesContext);
        }

        @Test
        @DisplayName("Should render component with LABEL_SR_ONLY layout mode correctly")
        void shouldRenderWithLayoutModeLabelSrOnly(FacesContext facesContext) throws IOException {
            // Arrange
            final var component = new LabeledContainerComponent();
            component.setId(LABELED_CONTAINER_ID);
            component.setLayoutMode(LayoutMode.LABEL_SR_ONLY.name());
            component.processEvent(new PostAddToViewEvent(component));
            component.processEvent(new PreRenderComponentEvent(component));

            // Act & Assert
            final var expected = new HtmlTreeBuilder().withNode(Node.DIV)
                    .withAttribute(DATA_LABELED_CONTAINER, DATA_LABELED_CONTAINER)
                    .withAttribute(AttributeName.ID, LABELED_CONTAINER_ID)
                    .withAttribute(AttributeName.NAME, LABELED_CONTAINER_ID).withStyleClass(CssBootstrap.FORM_GROUP)
                    .withNode(Node.LABEL).withAttribute(AttributeName.ID, LABELED_CONTAINER_ID_LABEL)
                    .withAttribute(AttributeName.NAME, LABELED_CONTAINER_ID_LABEL)
                    .withStyleClass(CssBootstrap.CONTROL_LABEL.getStyleClassBuilder()
                            .append(CssBootstrap.SR_ONLY.getStyleClass()).getStyleClass())
                    .currentHierarchyUp().withNode(Node.P).withStyleClass(CssBootstrap.FORM_CONTROL_STATIC)
                    .currentHierarchyUp().currentHierarchyUp();
            assertRenderResult(component, expected.getDocument(), facesContext);
        }

        @Test
        @DisplayName("Should render component with LABEL_SR_ONLY layout mode and input correctly")
        void shouldRenderWithLayoutModeLabelSrOnlyWithInput(FacesContext facesContext) throws IOException {
            // Arrange
            final var component = new LabeledContainerComponent();
            component.setId(LABELED_CONTAINER_ID);
            component.setLayoutMode(LayoutMode.LABEL_SR_ONLY.name());
            final var htmlInputText = new HtmlInputText();
            htmlInputText.setId(INPUT);
            component.getChildren().add(htmlInputText);
            component.processEvent(new PostAddToViewEvent(component));
            component.processEvent(new PreRenderComponentEvent(component));

            // Act & Assert
            final var expected = new HtmlTreeBuilder().withNode(Node.DIV)
                    .withAttribute(DATA_LABELED_CONTAINER, DATA_LABELED_CONTAINER)
                    .withAttribute(AttributeName.ID, LABELED_CONTAINER_ID)
                    .withAttribute(AttributeName.NAME, LABELED_CONTAINER_ID).withStyleClass(CssBootstrap.FORM_GROUP)
                    .withNode(Node.LABEL).withAttribute(AttributeName.ID, LABELED_CONTAINER_ID_LABEL)
                    .withAttribute(AttributeName.NAME, LABELED_CONTAINER_ID_LABEL)
                    .withAttribute(AttributeName.FOR, LABELED_CONTAINER_ID_INPUT)
                    .withStyleClass(CssBootstrap.CONTROL_LABEL.getStyleClassBuilder()
                            .append(CssBootstrap.SR_ONLY.getStyleClass()).getStyleClass())
                    .currentHierarchyUp().withNode(Node.INPUT)
                    .withAttribute(AttributeName.ID, LABELED_CONTAINER_ID_INPUT)
                    .withAttribute(AttributeName.NAME, LABELED_CONTAINER_ID_INPUT).withAttribute(AttributeName.TYPE, "text")
                    .withStyleClass(CssBootstrap.FORM_CONTROL).currentHierarchyUp().currentHierarchyUp();
            assertRenderResult(component, expected.getDocument(), facesContext);
        }

        @Test
        @DisplayName("Should render component with FORMGROUP layout mode correctly")
        void shouldRenderWithLayoutModeFormGroup(FacesContext facesContext) throws IOException {
            // Arrange
            final var component = new LabeledContainerComponent();
            component.setId(LABELED_CONTAINER_ID);
            component.setLayoutMode(LayoutMode.FORMGROUP.name());
            component.processEvent(new PostAddToViewEvent(component));
            component.processEvent(new PreRenderComponentEvent(component));

            // Act & Assert
            final var expected = new HtmlTreeBuilder().withNode(Node.DIV)
                    .withAttribute(DATA_LABELED_CONTAINER, DATA_LABELED_CONTAINER)
                    .withAttribute(AttributeName.ID, LABELED_CONTAINER_ID)
                    .withAttribute(AttributeName.NAME, LABELED_CONTAINER_ID).withStyleClass(CssBootstrap.FORM_GROUP)
                    .withNode(Node.LABEL).withAttribute(AttributeName.ID, LABELED_CONTAINER_ID_LABEL)
                    .withAttribute(AttributeName.NAME, LABELED_CONTAINER_ID_LABEL)
                    .withStyleClass(CssBootstrap.CONTROL_LABEL).currentHierarchyUp().withNode(Node.P)
                    .withStyleClass(CssBootstrap.FORM_CONTROL_STATIC).currentHierarchyUp().currentHierarchyUp();
            assertRenderResult(component, expected.getDocument(), facesContext);
        }

        @Test
        @DisplayName("Should render component with FORMGROUP layout mode and input correctly")
        void shouldRenderWithLayoutModeFormGroupWithInput(FacesContext facesContext) throws IOException {
            // Arrange
            final var component = new LabeledContainerComponent();
            component.setId(LABELED_CONTAINER_ID);
            component.setLayoutMode(LayoutMode.FORMGROUP.name());
            final var htmlInputText = new HtmlInputText();
            htmlInputText.setId(INPUT);
            component.getChildren().add(htmlInputText);
            component.processEvent(new PostAddToViewEvent(component));
            component.processEvent(new PreRenderComponentEvent(component));

            // Act & Assert
            final var expected = new HtmlTreeBuilder().withNode(Node.DIV)
                    .withAttribute(DATA_LABELED_CONTAINER, DATA_LABELED_CONTAINER)
                    .withAttribute(AttributeName.ID, LABELED_CONTAINER_ID)
                    .withAttribute(AttributeName.NAME, LABELED_CONTAINER_ID).withStyleClass(CssBootstrap.FORM_GROUP)
                    .withNode(Node.LABEL).withAttribute(AttributeName.ID, LABELED_CONTAINER_ID_LABEL)
                    .withAttribute(AttributeName.NAME, LABELED_CONTAINER_ID_LABEL)
                    .withAttribute(AttributeName.FOR, LABELED_CONTAINER_ID_INPUT).withStyleClass(CssBootstrap.CONTROL_LABEL)
                    .currentHierarchyUp().withNode(Node.INPUT)
                    .withAttribute(AttributeName.ID, LABELED_CONTAINER_ID_INPUT)
                    .withAttribute(AttributeName.NAME, LABELED_CONTAINER_ID_INPUT).withAttribute(AttributeName.TYPE, "text")
                    .withStyleClass(CssBootstrap.FORM_CONTROL).currentHierarchyUp().currentHierarchyUp();
            assertRenderResult(component, expected.getDocument(), facesContext);
        }
    }

    @Nested
    @DisplayName("Tests for help block")
    class HelpBlockTests {

        @Test
        @DisplayName("Should render component with help block correctly")
        void shouldRenderMinimalWithHelpBlock(FacesContext facesContext) throws IOException {
            // Arrange
            final var htmlInputText = new HtmlInputText();
            htmlInputText.setId(INPUT);
            final var helpTextComponent = new HelpTextComponent();
            helpTextComponent.setContentValue(NEED_SOME_HELP);
            final var component = new LabeledContainerComponent();
            component.getChildren().add(htmlInputText);
            component.getChildren().add(helpTextComponent);
            component.processEvent(new PostAddToViewEvent(component));
            component.processEvent(new PreRenderComponentEvent(component));

            // Act & Assert
            final var expected = new HtmlTreeBuilder().withNode(Node.DIV)
                    .withAttribute(DATA_LABELED_CONTAINER, DATA_LABELED_CONTAINER).withStyleClass(CssBootstrap.FORM_GROUP)
                    .withNode(Node.LABEL).withAttribute(AttributeName.ID, CLIENT_ID_LABEL)
                    .withAttribute(AttributeName.NAME, CLIENT_ID_LABEL)
                    .withAttribute(AttributeName.FOR, CLIENT_ID + COLON_INPUT)
                    .withStyleClass(new StyleClassBuilderImpl(COL_4).append(CssBootstrap.CONTROL_LABEL).getStyleClass())
                    .currentHierarchyUp().withNode(Node.DIV).withStyleClass(COL_8).withNode(Node.INPUT)
                    .withAttribute(AttributeName.ID, CLIENT_ID + COLON_INPUT)
                    .withAttribute(AttributeName.NAME, CLIENT_ID + COLON_INPUT).withAttribute(AttributeName.TYPE, "text")
                    .withStyleClass(CssBootstrap.FORM_CONTROL).currentHierarchyUp().withNode(Node.DIV)
                    .withStyleClass(CssBootstrap.CUI_ADDITIONAL_MESSAGE.getStyleClass())
                    .withAttribute(DATA_HELP_BLOCK, DATA_HELP_BLOCK).withNode(Node.SPAN).withTextContent(NEED_SOME_HELP)
                    .currentHierarchyUp().currentHierarchyUp().currentHierarchyUp();
            assertRenderResult(component, expected.getDocument(), facesContext);
        }
    }


    @Override
    protected UIComponent getComponent() {
        return new LabeledContainerComponent();
    }

    @BeforeEach
    void configureComponents(ComponentConfigDecorator decorator) {
        decorator.registerUIComponent(CuiMessageComponent.class).registerRenderer(CuiMessageRenderer.class);
    }
}
