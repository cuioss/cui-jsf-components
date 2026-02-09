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
package de.cuioss.jsf.bootstrap.checkbox;

import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.bootstrap.common.partial.ColumnCssResolver;
import de.cuioss.jsf.bootstrap.layout.ColumnComponent;
import de.cuioss.jsf.bootstrap.layout.LayoutComponentRenderer;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.renderer.AbstractComponentRendererTest;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.PostAddToViewEvent;
import jakarta.faces.event.PreRenderComponentEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
@DisplayName("Tests for SwitchRenderer")
class SwitchRendererTest extends AbstractComponentRendererTest<SwitchRenderer> {

    private static final String DEFAULT_COLUMN_SIZE = ColumnCssResolver.COL_PREFIX + "3";

    private static final String TEST_COMPONENT = "testComponent";

    private static final String TITLE_VALUE = "titleValue";

    private static final String TITLE_KEY = "titleKey";

    private static final String ON_TEXT = "onText";

    private static final String OFF_TEXT = "offText";

    private static final String ON_TEXT_KEY = "onTextKey";

    private static final String OFF_TEXT_KEY = "offTextKey";

    private static final String STYLE = "style";

    private static final String STYLE_CLASS = "styleClass";

    @BeforeEach
    void setUp(ComponentConfigDecorator decorator) {
        decorator.registerUIComponent(ColumnComponent.class).registerRenderer(LayoutComponentRenderer.class);
    }

    @Override
    protected UIComponent getComponent() {
        final var component = new SwitchComponent();
        component.setId(TEST_COMPONENT);
        component.setTitleValue(TITLE_VALUE);
        component.setTitleKey(TITLE_KEY);
        component.setOnTextValue(ON_TEXT);
        component.setOffTextValue(OFF_TEXT);
        component.setOnTextKey(ON_TEXT_KEY);
        component.setOffTextKey(OFF_TEXT_KEY);
        component.setStyle(STYLE);
        component.setSize(3);
        component.setStyleClass(STYLE_CLASS);
        return component;
    }

    @Nested
    @DisplayName("Rendering tests")
    class RenderingTests {

        @Test
        @DisplayName("Should render minimal switch component")
        void shouldRenderMinimal(FacesContext facesContext) throws Exception {
            // Arrange
            final var component = (SwitchComponent) getComponent();
            component.processEvent(new PostAddToViewEvent(component));
            component.processEvent(new PreRenderComponentEvent(component));

            // Act & Assert
            final var expected = buildHtmlTree(false, false);
            assertRenderResult(component, expected.getDocument(), facesContext);
        }

        @Test
        @DisplayName("Should render disabled switch component")
        void shouldRenderDisabled(FacesContext facesContext) throws Exception {
            // Arrange
            final var component = (SwitchComponent) getComponent();
            component.setDisabled(true);
            component.processEvent(new PostAddToViewEvent(component));
            component.processEvent(new PreRenderComponentEvent(component));

            // Act & Assert
            final var expected = buildHtmlTree(false, true);
            assertRenderResult(component, expected.getDocument(), facesContext);
        }
    }

    /**
     * <div id="testComponent_container" name="testComponent_container"
     * data-switch-disabled="true|false"> <div class="col-sm-6 switch-placing">
     * <label class="switch"> <input id="testComponent" name="testComponent"/>
     * <span class="slider round"/> </label>
     * <span class="switch-text" data-item-active="true">onText</span>
     * <span class="switch-text" data-item-active="false">offText</span> </div>
     * </div>
     */
    private HtmlTreeBuilder buildHtmlTree(final boolean isActive, final boolean isDisabled) {
        return new HtmlTreeBuilder().withNode(Node.DIV).withAttributeNameAndId("testComponent_container")
                .withAttribute("data-switch-disabled", String.valueOf(isDisabled))
                .withAttribute(AttributeName.CLASS, STYLE_CLASS).withAttribute(AttributeName.STYLE, STYLE)
                .withNode(Node.DIV).withAttribute(AttributeName.CLASS, DEFAULT_COLUMN_SIZE + " switch-placing")
                .withNode(Node.LABEL).withAttribute(AttributeName.CLASS, "switch")
                .withAttribute(AttributeName.TITLE, TITLE_VALUE).withNode(Node.INPUT)
                .withAttributeNameAndId("testComponent").currentHierarchyUp().withNode(Node.SPAN)
                .withAttribute(AttributeName.CLASS, "slider round").currentHierarchyUp().currentHierarchyUp()
                .withNode(Node.SPAN).withAttribute(AttributeName.CLASS, "switch-text" + (isActive ? "" : " hidden"))
                .withAttribute(AttributeName.DATA_ITEM_ACTIVE, "true").withTextContent(ON_TEXT).currentHierarchyUp()
                .withNode(Node.SPAN).withAttribute(AttributeName.CLASS, "switch-text" + (isActive ? " hidden" : ""))
                .withAttribute(AttributeName.DATA_ITEM_ACTIVE, "false").withTextContent(OFF_TEXT).currentHierarchyUp()
                .currentHierarchyUp();
    }
}
