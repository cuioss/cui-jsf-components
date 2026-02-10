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
package de.cuioss.jsf.bootstrap.layout.input;

import static de.cuioss.jsf.bootstrap.layout.input.LabeledContainerComponent.DATA_LABELED_CONTAINER;
import static de.cuioss.tools.collect.CollectionLiterals.mutableList;

import de.cuioss.jsf.api.components.css.impl.StyleClassBuilderImpl;
import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.jsf.bootstrap.button.CommandButton;
import de.cuioss.jsf.bootstrap.common.partial.ColumnCssResolver;
import de.cuioss.jsf.bootstrap.layout.messages.CuiMessageComponent;
import de.cuioss.jsf.bootstrap.layout.messages.CuiMessageRenderer;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.jsf.test.EnableJSFCDIEnvironment;
import de.cuioss.jsf.test.EnableResourceBundleSupport;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.mocks.CuiMockSearchExpressionHandler;
import de.cuioss.test.jsf.renderer.AbstractComponentRendererTest;
import de.cuioss.test.juli.junit5.EnableTestLogger;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.html.HtmlInputText;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.PostAddToViewEvent;
import jakarta.faces.event.PreRenderComponentEvent;
import jakarta.faces.view.ViewDeclarationLanguage;
import org.jboss.weld.junit5.ExplicitParamInjection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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
@EnableJSFCDIEnvironment
@EnableResourceBundleSupport
@JsfTestConfiguration(CoreJsfTestConfiguration.class)
@ExplicitParamInjection
@EnableTestLogger
@DisplayName("Tests for InputGuardRenderer")
class InputGuardRendererTest extends AbstractComponentRendererTest<LabeledContainerRenderer> {

    private static final String CLIENT_ID = "j_id__v_0";

    private static final String CLIENT_ID_LABEL = CLIENT_ID + "_label";

    private static final String COLON_INPUT = ":input";

    private static final String COL_8 = ColumnCssResolver.COL_PREFIX + "8";

    private static final String COL_4 = ColumnCssResolver.COL_PREFIX + "4";

    private static final String INPUT = "input";

    @Nested
    @DisplayName("Tests for rendering behavior")
    class RenderingTests {

        @Test
        @DisplayName("Should render minimal component correctly")
        void shouldRenderMinimal(FacesContext facesContext) throws Exception {
            // Arrange
            var ajaxId = "thisId";
            CuiMockSearchExpressionHandler.retrieve(facesContext).setResolvedClientIds(mutableList(ajaxId));
            final var htmlInputText = new HtmlInputText();
            htmlInputText.setId(INPUT);
            var component = new LabeledContainerComponent();
            component.getChildren().add(htmlInputText);
            component.getChildren().add(new InputGuardComponent());
            component.processEvent(new PostAddToViewEvent(component));
            component.processEvent(new PreRenderComponentEvent(component));

            // Act & Assert
            final var expected = new HtmlTreeBuilder().withNode(Node.DIV)
                    .withAttribute(DATA_LABELED_CONTAINER, DATA_LABELED_CONTAINER).withStyleClass(CssBootstrap.FORM_GROUP)
                    .withNode(Node.LABEL).withAttribute(AttributeName.ID, CLIENT_ID_LABEL)
                    .withAttribute(AttributeName.NAME, CLIENT_ID_LABEL)
                    .withAttribute(AttributeName.FOR, CLIENT_ID + COLON_INPUT)
                    .withStyleClass(new StyleClassBuilderImpl(COL_4).append(CssBootstrap.CONTROL_LABEL).getStyleClass())
                    .currentHierarchyUp().withNode(Node.DIV).withStyleClass(COL_8).withNode(Node.DIV)
                    .withStyleClass(CssBootstrap.INPUT_GROUP).withNode(Node.INPUT)
                    .withAttribute(AttributeName.ID, CLIENT_ID + COLON_INPUT)
                    .withAttribute(AttributeName.NAME, CLIENT_ID + COLON_INPUT)
                    .withAttribute(AttributeName.DISABLED, AttributeName.DISABLED.getContent())
                    .withAttribute(AttributeName.TYPE, "text").withStyleClass(CssBootstrap.FORM_CONTROL)
                    .currentHierarchyUp().withNode(Node.INPUT)
                    .withAttribute(AttributeName.ID, CLIENT_ID + ":guarded_value")
                    .withAttribute(AttributeName.NAME, CLIENT_ID + ":guarded_value")
                    .withAttribute(AttributeName.VALUE, Boolean.TRUE.toString())
                    .withAttribute(InputGuardComponent.DATA_GUARDED_INPUT, InputGuardComponent.DATA_GUARDED_INPUT)
                    .withAttribute(InputGuardComponentTest.DEFAULT_PROCESS_KEY, ajaxId)
                    .withAttribute(InputGuardComponentTest.DEFAULT_UPDATE_KEY, ajaxId).currentHierarchyUp()
                    .withNode(Node.DIV).withStyleClass(CssBootstrap.INPUT_GROUP_ADDON).withNode("CommandButton")
                    .withAttribute(AttributeName.TITLE, "input_guard.unlock.default.title")
                    .withAttribute(InputGuardComponent.DATA_GUARDED_BUTTON, InputGuardComponent.DATA_GUARDED_BUTTON)
                    .withAttribute(InputGuardComponent.DATA_GUARDED_TARGET, Boolean.FALSE.toString()).currentHierarchyUp()
                    .currentHierarchyUp().currentHierarchyUp();

            assertRenderResult(component, expected.getDocument(), facesContext);
        }
    }

    @Override
    protected UIComponent getComponent() {
        final var htmlInputText = new HtmlInputText();
        htmlInputText.setId(INPUT);
        var containerComponent = new LabeledContainerComponent();
        containerComponent.getChildren().add(new InputGuardComponent());
        return containerComponent;
    }

    @BeforeEach
    void configureComponents(ComponentConfigDecorator decorator) {
        CoreJsfTestConfiguration.configureComponents(decorator);
        decorator.registerUIComponent(CuiMessageComponent.class).registerRenderer(CuiMessageRenderer.class)
                .registerUIComponent(CommandButton.class)
                .registerMockRenderer(BootstrapFamily.COMPONENT_FAMILY, BootstrapFamily.COMMAND_BUTTON_RENDERER);
    }
}
