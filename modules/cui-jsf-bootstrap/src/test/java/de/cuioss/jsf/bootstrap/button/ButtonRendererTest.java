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

import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIOutcomeTarget;
import jakarta.faces.event.PreRenderComponentEvent;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.components.JsfComponentIdentifier;
import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.AttributeValue;
import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.jsf.bootstrap.icon.IconComponent;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.config.ComponentConfigurator;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.mocks.CuiMockRenderer;
import de.cuioss.test.jsf.renderer.AbstractComponentRendererTest;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class ButtonRendererTest extends AbstractComponentRendererTest<ButtonRenderer> implements ComponentConfigurator {

    private static final String KEY_BINDING = "enter";

    private static final String ANY_ICON = "cui-icon-user_add";

    private static final String ICONCOMPONENT = "iconcomponent";

    private static final String DEFAULT_BTN_CLASSES = "btn btn-default";

    private static final String TEXT_VALUE = "Some Text";

    @Test
    void shouldRenderMinimal() {
        var expected = new HtmlTreeBuilder().withNode(Node.BUTTON).withStyleClass(DEFAULT_BTN_CLASSES);
        assertRenderResult(getComponent(), expected.getDocument());
    }

    @Test
    void shouldHandleState() {
        var component = new Button();
        component.setState("info");
        var expected = new HtmlTreeBuilder().withNode(Node.BUTTON).withStyleClass("btn btn-info");
        assertRenderResult(component, expected.getDocument());
    }

    @Test
    void shouldHandleSize() {
        var component = new Button();
        component.setSize("lg");
        var expected = new HtmlTreeBuilder().withNode(Node.BUTTON).withStyleClass("btn btn-default btn-lg");
        assertRenderResult(component, expected.getDocument());
    }

    @Test
    void shouldHandleTitle() {
        var component = new Button();
        component.setTitleValue(TEXT_VALUE);
        var expected = new HtmlTreeBuilder().withNode(Node.BUTTON).withStyleClass(DEFAULT_BTN_CLASSES)
                .withAttribute(AttributeName.TITLE, TEXT_VALUE);
        assertRenderResult(component, expected.getDocument());
    }

    @Test
    void shouldHandleTextValue() {
        var component = new Button();
        component.setLabelValue(TEXT_VALUE);
        var expected = new HtmlTreeBuilder().withNode(Node.BUTTON).withAttribute(AttributeName.VALUE, TEXT_VALUE)
                .withStyleClass(DEFAULT_BTN_CLASSES).withNode(Node.SPAN).withStyleClass(CssBootstrap.BUTTON_TEXT)
                .withTextContent(TEXT_VALUE);
        assertRenderResult(component, expected.getDocument());
    }

    @Test
    void shouldHandleIconOnLeft() {
        var component = new Button();
        component.setLabelValue(TEXT_VALUE);
        component.setIcon(ANY_ICON);
        var expected = new HtmlTreeBuilder().withNode(Node.BUTTON).withAttribute(AttributeName.VALUE, TEXT_VALUE)
                .withStyleClass(DEFAULT_BTN_CLASSES).withNode(ICONCOMPONENT).currentHierarchyUp().withNode(Node.SPAN)
                .withStyleClass(CssBootstrap.BUTTON_TEXT).withTextContent(TEXT_VALUE);
        assertRenderResult(component, expected.getDocument());
    }

    @Test
    void shouldHandleIconOnRight() {
        var component = new Button();
        component.setLabelValue(TEXT_VALUE);
        component.setIconAlign("right");
        component.setIcon(ANY_ICON);
        var expected = new HtmlTreeBuilder().withNode(Node.BUTTON).withAttribute(AttributeName.VALUE, TEXT_VALUE)
                .withStyleClass(DEFAULT_BTN_CLASSES).withNode(Node.SPAN).withStyleClass(CssBootstrap.BUTTON_TEXT)
                .withTextContent(TEXT_VALUE).currentHierarchyUp().withNode(ICONCOMPONENT);
        assertRenderResult(component, expected.getDocument());
    }

    @Test
    void shouldHandleIconOnly() {
        var component = new Button();
        component.setIcon(ANY_ICON);
        var expected = new HtmlTreeBuilder().withNode(Node.BUTTON).withStyleClass(DEFAULT_BTN_CLASSES)
                .withNode(ICONCOMPONENT);
        assertRenderResult(component, expected.getDocument());
    }

    @Test
    void shouldHandleKeyBinding() {
        var component = new Button();
        component.setKeyBinding(KEY_BINDING);
        component.processEvent(new PreRenderComponentEvent(component));
        var expected = new HtmlTreeBuilder().withNode(Node.BUTTON).withStyleClass(DEFAULT_BTN_CLASSES)
                .withAttribute(AttributeValue.CUI_CLICK_BINDING.getContent(), KEY_BINDING);
        assertRenderResult(component, expected.getDocument());
    }

    @Test
    void shouldDecodeWOErrors() {
        assertDoesNotThrow(() -> getRenderer().decode(getFacesContext(), getComponent()));
    }

    @Override
    public void configureComponents(final ComponentConfigDecorator decorator) {
        decorator
                .registerRenderer(UIOutcomeTarget.COMPONENT_FAMILY, JsfComponentIdentifier.BUTTON_RENDERER_TYPE,
                        new CuiMockRenderer("input"))
                .registerUIComponent(IconComponent.class).registerRenderer(BootstrapFamily.COMPONENT_FAMILY,
                        BootstrapFamily.ICON_COMPONENT_RENDERER, new CuiMockRenderer(ICONCOMPONENT));
    }

    @Override
    protected UIComponent getComponent() {
        return new Button();
    }
}
