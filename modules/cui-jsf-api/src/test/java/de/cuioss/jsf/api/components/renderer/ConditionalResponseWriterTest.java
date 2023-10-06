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

import static de.cuioss.jsf.api.components.ComponentTestUtils.createSimplePartialRenderBuilder;
import static de.cuioss.jsf.api.components.ComponentTestUtils.createWrappedPartialRenderBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.myfaces.test.mock.MockResponseWriter;
import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;

class ConditionalResponseWriterTest {

    public static final String PARTIAL_SIMPLE_RENDER_START = "<div name=\"header\"/>";

    public static final String PARTIAL_SIMPLE_RENDER_END = "<div name=\"footer\"/>";

    public static final String PARTIAL_WRAPPED_RENDER_START = "<div name=\"wrapper\"><div name=\"header\"/>";

    public static final String PARTIAL_WRAPPED_RENDER_END = "<div name=\"footer\"/></div>";

    @Test
    void shouldRenderSimpleStart() throws IOException {
        var builder = createSimplePartialRenderBuilder();
        var sink = new StringWriter();
        var mockWriter = new MockResponseWriter(sink);
        var writer = new ConditionalResponseWriter(mockWriter, HtmlTreeBuilder.CHILD_BREAKPOINT_ELEMENT, true);
        builder.writeToResponseWriter(writer);
        assertEquals(PARTIAL_SIMPLE_RENDER_START, sink.toString());
    }

    @Test
    void shouldRenderSimpleEnd() throws IOException {
        var builder = createSimplePartialRenderBuilder();
        var sink = new StringWriter();
        var mockWriter = new MockResponseWriter(sink);
        var writer = new ConditionalResponseWriter(mockWriter, HtmlTreeBuilder.CHILD_BREAKPOINT_ELEMENT, false);
        builder.writeToResponseWriter(writer);
        assertEquals(PARTIAL_SIMPLE_RENDER_END, sink.toString());
    }

    @Test
    void shouldRenderWrappedStart() throws IOException {
        var builder = createWrappedPartialRenderBuilder();
        var sink = new StringWriter();
        var mockWriter = new MockResponseWriter(sink);
        var writer = new ConditionalResponseWriter(mockWriter, HtmlTreeBuilder.CHILD_BREAKPOINT_ELEMENT, true);
        builder.writeToResponseWriter(writer);
        assertEquals(PARTIAL_WRAPPED_RENDER_START, sink.toString());
    }

    @Test
    void shouldRenderWrappedEnd() throws IOException {
        var builder = createWrappedPartialRenderBuilder();
        var sink = new StringWriter();
        var mockWriter = new MockResponseWriter(sink);
        var writer = new ConditionalResponseWriter(mockWriter, HtmlTreeBuilder.CHILD_BREAKPOINT_ELEMENT, false);
        builder.writeToResponseWriter(writer);
        assertEquals(PARTIAL_WRAPPED_RENDER_END, sink.toString());
    }
}
