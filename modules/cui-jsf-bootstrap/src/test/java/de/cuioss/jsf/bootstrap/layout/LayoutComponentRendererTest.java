package de.cuioss.jsf.bootstrap.layout;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputText;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.test.jsf.renderer.AbstractComponentRendererTest;

class LayoutComponentRendererTest extends AbstractComponentRendererTest<LayoutComponentRenderer> {

    @Test
    void shouldRenderMinimal() {
        var component = new RowComponent();
        var expected = new HtmlTreeBuilder().withNode(Node.DIV).withStyleClass(CssBootstrap.ROW);
        assertRenderResult(component, expected.getDocument());
    }

    @Test
    void shouldRenderWithChildren() {
        var component = new RowComponent();
        component.getChildren().add(new HtmlOutputText());
        getComponentConfigDecorator().registerMockRendererForHtmlOutputText();
        var expected =
            new HtmlTreeBuilder().withNode(Node.DIV).withStyleClass(CssBootstrap.ROW).withNode("HtmlOutputText");
        assertRenderResult(component, expected.getDocument());
    }

    @Test
    void shouldHandleRenderedSetToFalse() {
        var component = new RowComponent();
        component.setRendered(false);
        component.getChildren().add(new HtmlOutputText());
        getComponentConfigDecorator().registerMockRendererForHtmlOutputText();
        assertEmptyRenderResult(component);
    }

    @Override
    protected UIComponent getComponent() {
        return new RowComponent();
    }
}
