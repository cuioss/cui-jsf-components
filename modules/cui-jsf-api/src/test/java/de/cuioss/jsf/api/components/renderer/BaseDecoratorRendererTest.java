package de.cuioss.jsf.api.components.renderer;

import static de.cuioss.test.jsf.renderer.util.HtmlTreeAsserts.assertHtmlTreeEquals;

import java.io.IOException;
import java.io.StringWriter;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;

import org.apache.myfaces.test.mock.MockResponseWriter;
import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.test.jsf.renderer.AbstractRendererTestBase;

@SuppressWarnings({ "resource" })
class BaseDecoratorRendererTest extends AbstractRendererTestBase<MockDecoratorRenderer> {

    @Override
    protected UIComponent getComponent() {
        return new HtmlInputText();
    }

    @Test
    void shouldRenderDummyWithChildren() throws IOException {
        final var actual = renderToTreeBuilder(getComponent(), new MockDecoratorRenderer(true));
        final var expected = new HtmlTreeBuilder().withNode(Node.DIV);
        assertHtmlTreeEquals(expected.getDocument(), actual.getDocument());
    }

    @Test
    void shouldRenderDummyWithoutChildren() throws IOException {
        final var actual = renderToTreeBuilder(getComponent(), new MockDecoratorRenderer(false));
        final var expected = new HtmlTreeBuilder().withNode(Node.DIV);
        assertHtmlTreeEquals(expected.getDocument(), actual.getDocument());
    }

    @Test
    void shouldIgnoreIfRenderedIsfalse() throws IOException {
        final var component = getComponent();
        component.setRendered(false);
        final var actual = renderToTreeBuilder(component, new MockDecoratorRenderer(false));
        final var expected = new HtmlTreeBuilder();
        assertHtmlTreeEquals(expected.getDocument(), actual.getDocument());
    }

    /**
     * Renders the given component / renderer into a {@link HtmlTreeBuilder} representation
     *
     * @param component
     * @param renderer
     * @return
     * @throws IOException
     */
    protected HtmlTreeBuilder renderToTreeBuilder(final UIComponent component, final MockDecoratorRenderer renderer)
        throws IOException {
        var writer = new StringWriter();
        getFacesContext().setResponseWriter(new MockResponseWriter(writer));
        renderer.encodeBegin(getFacesContext(), component);
        renderer.encodeChildren(getFacesContext(), component);
        renderer.encodeEnd(getFacesContext(), component);
        return new HtmlTreeBuilder(writer.toString());
    }
}
