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

import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.test.jsf.renderer.AbstractRendererTestBase;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.html.HtmlInputText;
import jakarta.faces.context.FacesContext;
import org.apache.myfaces.test.mock.MockResponseWriter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;

@DisplayName("Tests for BaseDecoratorRenderer")
class BaseDecoratorRendererTest extends AbstractRendererTestBase<MockDecoratorRenderer> {

    @Override
    protected UIComponent getComponent() {
        return new HtmlInputText();
    }

    @Nested
    @DisplayName("Tests for rendering behavior")
    class RenderingTests {

        @Test
        @DisplayName("Should render component with children")
        void shouldRenderComponentWithChildren(FacesContext facesContext) throws IOException {
            // Arrange
            var component = getComponent();
            var renderer = new MockDecoratorRenderer(true);

            // Act
            final var actual = renderToTreeBuilder(component, renderer, facesContext);

            // Assert
            final var expected = new HtmlTreeBuilder().withNode(Node.DIV);
            // Rendered output should match expected HTML structure
            assertHtmlTreeEquals(expected.getDocument(), actual.getDocument());
        }

        @Test
        @DisplayName("Should render component without children")
        void shouldRenderComponentWithoutChildren(FacesContext facesContext) throws IOException {
            // Arrange
            var component = getComponent();
            var renderer = new MockDecoratorRenderer(false);

            // Act
            final var actual = renderToTreeBuilder(component, renderer, facesContext);

            // Assert
            final var expected = new HtmlTreeBuilder().withNode(Node.DIV);
            // Rendered output should match expected HTML structure
            assertHtmlTreeEquals(expected.getDocument(), actual.getDocument());
        }

        @Test
        @DisplayName("Should not render component when rendered property is false")
        void shouldNotRenderWhenRenderedIsFalse(FacesContext facesContext) throws IOException {
            // Arrange
            final var component = getComponent();
            component.setRendered(false);
            var renderer = new MockDecoratorRenderer(false);

            // Act
            final var actual = renderToTreeBuilder(component, renderer, facesContext);

            // Assert
            final var expected = new HtmlTreeBuilder();
            // No output should be rendered when component's rendered property is false
            assertHtmlTreeEquals(expected.getDocument(), actual.getDocument());
        }
    }

    /**
     * Renders the given component / renderer into a {@link HtmlTreeBuilder}
     * representation
     *
     * @param component must not be null
     * @param renderer must not be null
     * @param facesContext the FacesContext to use
     * @return the resulting {@link HtmlTreeBuilder}
     * @throws IOException from underlying {@link jakarta.faces.context.ResponseWriter}
     */
    protected HtmlTreeBuilder renderToTreeBuilder(final UIComponent component, final MockDecoratorRenderer renderer,
            FacesContext facesContext) throws IOException {
        var writer = new StringWriter();
        facesContext.setResponseWriter(new MockResponseWriter(writer));
        renderer.encodeBegin(facesContext, component);
        renderer.encodeChildren(facesContext, component);
        renderer.encodeEnd(facesContext, component);
        return new HtmlTreeBuilder(writer.toString());
    }
}
