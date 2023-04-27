package de.cuioss.jsf.bootstrap.checkbox;

import javax.faces.component.UIComponent;
import javax.faces.event.PostAddToViewEvent;
import javax.faces.event.PreRenderComponentEvent;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.bootstrap.common.partial.ColumnCssResolver;
import de.cuioss.jsf.bootstrap.layout.ColumnComponent;
import de.cuioss.jsf.bootstrap.layout.LayoutComponentRenderer;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.config.ComponentConfigurator;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.renderer.AbstractComponentRendererTest;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class SwitchRendererTest extends AbstractComponentRendererTest<SwitchRenderer> implements ComponentConfigurator {

    private static final String default_column_size = ColumnCssResolver.COL_PREFIX + "3";

    private static final String testComponent = "testComponent";

    private static final String titleValue = "titleValue";

    private static final String titleKey = "titleKey";

    private static final String onText = "onText";

    private static final String offText = "offText";

    private static final String onTextKey = "onTextKey";

    private static final String offTextKey = "offTextKey";

    private static final String style = "style";

    private static final String styleClass = "styleClass";

    @Override
    public void configureComponents(final ComponentConfigDecorator decorator) {
        decorator.registerUIComponent(ColumnComponent.class).registerRenderer(LayoutComponentRenderer.class);
    }

    @Override
    protected UIComponent getComponent() {
        final var component = new SwitchComponent();
        component.setId(testComponent);
        component.setTitleValue(titleValue);
        component.setTitleKey(titleKey);
        component.setOnTextValue(onText);
        component.setOffTextValue(offText);
        component.setOnTextKey(onTextKey);
        component.setOffTextKey(offTextKey);
        component.setStyle(style);
        component.setSize(3);
        component.setStyleClass(styleClass);
        return component;
    }

    @Test
    void shouldRenderMinimal() {
        final var component = (SwitchComponent) getComponent();
        component.processEvent(new PostAddToViewEvent(component));
        component.processEvent(new PreRenderComponentEvent(component));
        final var expected = buildHtmlTree(false, false);
        assertRenderResult(component, expected.getDocument());
    }

    @Test
    void shouldRenderDisabled() {
        final var component = (SwitchComponent) getComponent();
        component.setDisabled(true);
        component.processEvent(new PostAddToViewEvent(component));
        component.processEvent(new PreRenderComponentEvent(component));
        final var expected = buildHtmlTree(false, true);
        assertRenderResult(component, expected.getDocument());
    }

    /**
     * <div id="testComponent_container"
     * name="testComponent_container"
     * data-switch-disabled="true|false">
     * <div class="col-sm-6 switch-placing">
     * <label class="switch">
     * <input id="testComponent" name="testComponent"/>
     * <span class="slider round"/>
     * </label>
     * <span class="switch-text" data-item-active="true">onText</span>
     * <span class="switch-text" data-item-active="false">offText</span>
     * </div>
     * </div>
     */
    private HtmlTreeBuilder buildHtmlTree(final boolean isActive, final boolean isDisabled) {
        final var expected = new HtmlTreeBuilder().withNode(Node.DIV)
                .withAttributeNameAndId("testComponent_container")
                .withAttribute("data-switch-disabled", String.valueOf(isDisabled))
                .withAttribute(AttributeName.CLASS, styleClass).withAttribute(AttributeName.STYLE, style)
                .withNode(Node.DIV).withAttribute(AttributeName.CLASS, default_column_size + " switch-placing")
                .withNode(Node.LABEL).withAttribute(AttributeName.CLASS, "switch")
                .withAttribute(AttributeName.TITLE, titleValue).withNode(Node.INPUT)
                .withAttributeNameAndId("testComponent").currentHierarchyUp().withNode(Node.SPAN)
                .withAttribute(AttributeName.CLASS, "slider round").currentHierarchyUp().currentHierarchyUp()
                .withNode(Node.SPAN).withAttribute(AttributeName.CLASS, "switch-text" + (!isActive ? " hidden" : ""))
                .withAttribute(AttributeName.DATA_ITEM_ACTIVE, "true").withTextContent(onText).currentHierarchyUp()
                .withNode(Node.SPAN).withAttribute(AttributeName.CLASS, "switch-text" + (isActive ? " hidden" : ""))
                .withAttribute(AttributeName.DATA_ITEM_ACTIVE, "false").withTextContent(offText).currentHierarchyUp()
                .currentHierarchyUp();
        return expected;
    }
}
