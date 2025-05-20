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

import static de.cuioss.jsf.bootstrap.layout.input.LabeledContainerComponent.DATA_LABELED_CONTAINER;

import de.cuioss.jsf.api.components.css.impl.StyleClassBuilderImpl;
import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.jsf.bootstrap.button.Button;
import de.cuioss.jsf.bootstrap.common.partial.ColumnCssResolver;
import de.cuioss.jsf.bootstrap.layout.messages.CuiMessageComponent;
import de.cuioss.jsf.bootstrap.layout.messages.CuiMessageRenderer;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.renderer.AbstractComponentRendererTest;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.html.HtmlInputText;
import jakarta.faces.component.html.HtmlOutputLink;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.PostAddToViewEvent;
import jakarta.faces.event.PreRenderComponentEvent;
import jakarta.faces.view.ViewDeclarationLanguage;
import org.junit.jupiter.api.BeforeEach;
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
class HelpTextRendererTest extends AbstractComponentRendererTest<LabeledContainerRenderer> {

    private static final String CLIENT_ID = "j_id__v_0";

    private static final String CLIENT_ID_LABEL = CLIENT_ID + "_label";

    private static final String COLON_INPUT = ":input";

    private static final String COL_8 = ColumnCssResolver.COL_PREFIX + "8";

    private static final String COL_4 = ColumnCssResolver.COL_PREFIX + "4";

    private static final String INPUT = "input";

    @Test
    void shouldRenderMinimal(FacesContext facesContext) throws IOException {
        final var htmlInputText = new HtmlInputText();
        htmlInputText.setId(INPUT);
        var component = new LabeledContainerComponent();
        component.getChildren().add(htmlInputText);
        var helpTextComponent = new HelpTextComponent();
        helpTextComponent.setContentValue("title");
        component.getChildren().add(helpTextComponent);
        component.processEvent(new PostAddToViewEvent(component));
        component.processEvent(new PreRenderComponentEvent(component));
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
                .withAttribute(HelpTextComponent.DATA_HELP_BLOCK, HelpTextComponent.DATA_HELP_BLOCK).withNode(Node.SPAN)
                .withTextContent("title").currentHierarchyUp().currentHierarchyUp().currentHierarchyUp();
        assertRenderResult(component, expected.getDocument(), facesContext);
    }

    @Test
    void shouldRenderWithChild(FacesContext facesContext) throws IOException {
        final var htmlInputText = new HtmlInputText();
        htmlInputText.setId(INPUT);
        var component = new LabeledContainerComponent();
        component.getChildren().add(htmlInputText);
        var helpTextComponent = new HelpTextComponent();
        helpTextComponent.setTitleValue("title");
        helpTextComponent.setRenderButton(true);
        var content = new HtmlOutputLink();
        content.setTarget("https://www.cuioss.de");
        helpTextComponent.getChildren().add(content);
        component.getChildren().add(helpTextComponent);
        component.processEvent(new PostAddToViewEvent(component));
        component.processEvent(new PreRenderComponentEvent(component));
        final var expected = new HtmlTreeBuilder().withNode(Node.DIV)
                .withAttribute(DATA_LABELED_CONTAINER, DATA_LABELED_CONTAINER).withStyleClass(CssBootstrap.FORM_GROUP)
                .withNode(Node.LABEL).withAttribute(AttributeName.ID, CLIENT_ID_LABEL)
                .withAttribute(AttributeName.NAME, CLIENT_ID_LABEL)
                .withAttribute(AttributeName.FOR, CLIENT_ID + COLON_INPUT)
                .withStyleClass(new StyleClassBuilderImpl(COL_4).append(CssBootstrap.CONTROL_LABEL).getStyleClass())
                .currentHierarchyUp().withNode(Node.DIV).withStyleClass(COL_8).withNode(Node.DIV)
                .withStyleClass(CssBootstrap.INPUT_GROUP).withNode(Node.DIV)
                .withStyleClass(CssBootstrap.INPUT_GROUP_ADDON).withNode("Button")
                .withStyleClass("input-help-text-action")
                .withAttribute(HelpTextComponent.DATA_HELP_BUTTON, HelpTextComponent.DATA_HELP_BUTTON)
                .withAttribute(AttributeName.TITLE, "title").currentHierarchyUp().currentHierarchyUp()
                .withNode(Node.INPUT).withAttribute(AttributeName.ID, CLIENT_ID + COLON_INPUT)
                .withAttribute(AttributeName.NAME, CLIENT_ID + COLON_INPUT).withAttribute(AttributeName.TYPE, "text")
                .withStyleClass(CssBootstrap.FORM_CONTROL).currentHierarchyUp().withNode(Node.A)
                .withAttribute("target", "https://www.cuioss.de").currentHierarchyUp().currentHierarchyUp()
                .withNode(Node.DIV).withStyleClass(CssBootstrap.CUI_ADDITIONAL_MESSAGE.getStyleClass())
                .withAttribute(AttributeName.STYLE, "display: none;")
                .withAttribute(HelpTextComponent.DATA_HELP_BLOCK, HelpTextComponent.DATA_HELP_BLOCK).withNode(Node.A)
                .withAttribute("target", "https://www.cuioss.de").currentHierarchyUp().currentHierarchyUp();
        assertRenderResult(component, expected.getDocument(), facesContext);
    }

    @Override
    protected UIComponent getComponent() {
        final var htmlInputText = new HtmlInputText();
        htmlInputText.setId(INPUT);
        var containerComponent = new LabeledContainerComponent();
        var helpTextComponent = new HelpTextComponent();
        helpTextComponent.setTitleValue("title");
        containerComponent.getChildren().add(helpTextComponent);
        return containerComponent;
    }

    @BeforeEach
    void configureComponents(ComponentConfigDecorator decorator) {
        decorator.registerUIComponent(CuiMessageComponent.class).registerRenderer(CuiMessageRenderer.class)
                .registerUIComponent(Button.class)
                .registerMockRenderer(BootstrapFamily.COMPONENT_FAMILY, BootstrapFamily.BUTTON_RENDERER);
    }
}
