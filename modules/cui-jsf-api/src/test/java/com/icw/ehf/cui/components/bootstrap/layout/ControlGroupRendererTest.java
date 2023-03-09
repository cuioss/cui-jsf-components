package com.icw.ehf.cui.components.bootstrap.layout;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputText;

import org.junit.jupiter.api.Test;

import com.icw.ehf.cui.components.bootstrap.CssBootstrap;
import com.icw.ehf.cui.components.bootstrap.common.partial.ColumnCssResolver;
import com.icw.ehf.cui.core.api.components.html.HtmlTreeBuilder;
import com.icw.ehf.cui.core.api.components.html.Node;

import de.cuioss.test.jsf.renderer.AbstractComponentRendererTest;

class ControlGroupRendererTest extends AbstractComponentRendererTest<ControlGroupRenderer> {

    private static final String MINIMAL_COLUMN_CSS = ColumnCssResolver.COL_PREFIX + "8";

    @Test
    void shouldRenderMinimal() {
        final var component = new ControlGroupComponent();
        final var expected = new HtmlTreeBuilder().withNode(Node.DIV)
                .withStyleClass(CssBootstrap.FORM_GROUP).withNode(Node.DIV).withStyleClass(MINIMAL_COLUMN_CSS);
        assertRenderResult(component, expected.getDocument());
    }

    @Test
    void shouldRenderWithChildren() {
        final var component = new ControlGroupComponent();
        component.getChildren().add(new HtmlOutputText());
        getComponentConfigDecorator().registerMockRendererForHtmlOutputText();
        final var expected =
            new HtmlTreeBuilder().withNode(Node.DIV).withStyleClass(CssBootstrap.FORM_GROUP).withNode(Node.DIV)
                    .withStyleClass(MINIMAL_COLUMN_CSS).withNode("HtmlOutputText");
        assertRenderResult(component, expected.getDocument());
    }

    @Override
    protected UIComponent getComponent() {
        return new ControlGroupComponent();
    }
}
