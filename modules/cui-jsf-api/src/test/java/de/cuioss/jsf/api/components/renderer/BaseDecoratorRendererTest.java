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
     * Renders the given component / renderer into a {@link HtmlTreeBuilder}
     * representation
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
