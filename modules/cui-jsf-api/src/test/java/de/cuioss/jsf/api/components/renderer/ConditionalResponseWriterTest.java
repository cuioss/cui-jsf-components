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
package de.cuioss.jsf.api.components.renderer;

import static de.cuioss.jsf.api.components.ComponentTestUtils.createSimplePartialRenderBuilder;
import static de.cuioss.jsf.api.components.ComponentTestUtils.createWrappedPartialRenderBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;

import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import org.apache.myfaces.test.mock.MockResponseWriter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

@DisplayName("Tests for ConditionalResponseWriter")
class ConditionalResponseWriterTest {

    public static final String PARTIAL_SIMPLE_RENDER_START = "<div name=\"header\"/>";

    public static final String PARTIAL_SIMPLE_RENDER_END = "<div name=\"footer\"/>";

    public static final String PARTIAL_WRAPPED_RENDER_START = "<div name=\"wrapper\"><div name=\"header\"/>";

    public static final String PARTIAL_WRAPPED_RENDER_END = "<div name=\"footer\"/></div>";

    @Nested
    @DisplayName("Tests for simple partial rendering")
    class SimpleRenderingTests {

        @Test
        @DisplayName("Should render start part of simple partial render")
        void shouldRenderSimpleStartPart() throws Exception {
            // Arrange
            var builder = createSimplePartialRenderBuilder();
            var sink = new StringWriter();
            var mockWriter = new MockResponseWriter(sink);

            // Act - create writer with renderStart=true and write to it
            var writer = new ConditionalResponseWriter(mockWriter, HtmlTreeBuilder.CHILD_BREAKPOINT_ELEMENT, true);
            builder.writeToResponseWriter(writer);

            // Assert
            assertEquals(PARTIAL_SIMPLE_RENDER_START, sink.toString(),
                    "Should render only the start part of simple partial render");
        }

        @Test
        @DisplayName("Should render end part of simple partial render")
        void shouldRenderSimpleEndPart() throws Exception {
            // Arrange
            var builder = createSimplePartialRenderBuilder();
            var sink = new StringWriter();
            var mockWriter = new MockResponseWriter(sink);

            // Act - create writer with renderStart=false and write to it
            var writer = new ConditionalResponseWriter(mockWriter, HtmlTreeBuilder.CHILD_BREAKPOINT_ELEMENT, false);
            builder.writeToResponseWriter(writer);

            // Assert
            assertEquals(PARTIAL_SIMPLE_RENDER_END, sink.toString(),
                    "Should render only the end part of simple partial render");
        }
    }

    @Nested
    @DisplayName("Tests for wrapped partial rendering")
    class WrappedRenderingTests {

        @Test
        @DisplayName("Should render start part of wrapped partial render")
        void shouldRenderWrappedStartPart() throws Exception {
            // Arrange
            var builder = createWrappedPartialRenderBuilder();
            var sink = new StringWriter();
            var mockWriter = new MockResponseWriter(sink);

            // Act - create writer with renderStart=true and write to it
            var writer = new ConditionalResponseWriter(mockWriter, HtmlTreeBuilder.CHILD_BREAKPOINT_ELEMENT, true);
            builder.writeToResponseWriter(writer);

            // Assert
            assertEquals(PARTIAL_WRAPPED_RENDER_START, sink.toString(),
                    "Should render only the start part of wrapped partial render");
        }

        @Test
        @DisplayName("Should render end part of wrapped partial render")
        void shouldRenderWrappedEndPart() throws Exception {
            // Arrange
            var builder = createWrappedPartialRenderBuilder();
            var sink = new StringWriter();
            var mockWriter = new MockResponseWriter(sink);

            // Act - create writer with renderStart=false and write to it
            var writer = new ConditionalResponseWriter(mockWriter, HtmlTreeBuilder.CHILD_BREAKPOINT_ELEMENT, false);
            builder.writeToResponseWriter(writer);

            // Assert
            assertEquals(PARTIAL_WRAPPED_RENDER_END, sink.toString(),
                    "Should render only the end part of wrapped partial render");
        }
    }
}
