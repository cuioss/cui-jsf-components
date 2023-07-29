package de.cuioss.jsf.api.components.html;

import static de.cuioss.jsf.api.components.ComponentTestUtils.createSimplePartialRenderBuilder;
import static de.cuioss.jsf.api.components.ComponentTestUtils.createWrappedPartialRenderBuilder;
import static de.cuioss.test.jsf.renderer.util.HtmlTreeAsserts.assertHtmlTreeEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.myfaces.test.mock.MockResponseWriter;
import org.junit.jupiter.api.Test;

@SuppressWarnings({ "resource", "javadoc" })
class HtmlTreeBuilderTest {

    public static final String SIMPLE_DIV = "<div />";

    public static final String NESTED_DIV = "<div><div /></div>";

    public static final String SOME_ID_CHILD = "someId:child";

    public static final String SOME_ID = "someId";

    public static final String NESTED_DIV_WITH_ATTRIBUTES = "<div id=\"someId\"><div id=\"someId:child\" /></div>";

    public static final String SOME_TEXT_NODE = "Some text node";

    public static final String SOME_TEXT_NODE_WITHIN_DIV = "<div>" + SOME_TEXT_NODE + "</div>";

    /**
     * Create a simple document with one div element as child
     */
    @Test
    void shouldCreateSimpleDivDocument() {
        var builder = new HtmlTreeBuilder().withNode(Node.DIV);
        assertEquals(SIMPLE_DIV, builder.toHtmlString());
        builder = new HtmlTreeBuilder(SIMPLE_DIV);
        assertEquals(SIMPLE_DIV, builder.toHtmlString());
    }

    /**
     * Create a simple document with nested divs element as children
     */
    @Test
    void shouldCreateNestedDivDocument() {
        var builder = new HtmlTreeBuilder().withNode(Node.DIV).withNode(Node.DIV);
        assertEquals(NESTED_DIV, builder.toHtmlString());
        builder = new HtmlTreeBuilder(NESTED_DIV);
        assertEquals(NESTED_DIV, builder.toHtmlString());
    }

    /**
     * Create a simple document with with nested divs element and id attributes as
     * children
     */
    @Test
    void shouldCreateNestedDivDocumentWithIdAttributes() {
        var builder = new HtmlTreeBuilder().withNode(Node.DIV).withAttribute(AttributeName.ID, SOME_ID)
                .withNode(Node.DIV).withAttribute(AttributeName.ID, SOME_ID_CHILD);
        assertEquals(NESTED_DIV_WITH_ATTRIBUTES, builder.toHtmlString());
        builder = new HtmlTreeBuilder(NESTED_DIV_WITH_ATTRIBUTES);
        assertEquals(NESTED_DIV_WITH_ATTRIBUTES, builder.toHtmlString());
    }

    @Test
    void shouldReparentCurrentNode() {
        final var builder = new HtmlTreeBuilder().withNode(Node.DIV).withNode(Node.SPAN);
        assertEquals(Node.SPAN.getContent(), builder.getCurrent().getName());
        builder.currentHierarchyUp();
        assertEquals(Node.DIV.getContent(), builder.getCurrent().getName());
    }

    @Test
    void shouldRenderTextChild() {
        final var builder = new HtmlTreeBuilder().withNode(Node.DIV).withTextContent(SOME_TEXT_NODE);
        assertEquals(SOME_TEXT_NODE_WITHIN_DIV, builder.toHtmlString());
    }

    @Test
    void shouldRenderNestedElementsToResponseWriter() throws IOException {
        var sink = new StringWriter();
        var mockWriter = new MockResponseWriter(sink);
        final var builder = new HtmlTreeBuilder(NESTED_DIV_WITH_ATTRIBUTES);
        builder.writeToResponseWriter(mockWriter);
        assertHtmlTreeEquals(builder.getDocument(), new HtmlTreeBuilder(sink.toString()).getDocument());
    }

    public static final String PARTIAL_SIMPLE_RENDER_START = "<div name=\"header\"/>";

    public static final String PARTIAL_SIMPLE_RENDER_END = "<div name=\"footer\"/>";

    public static final String PARTIAL_WRAPPED_RENDER_START = "<div name=\"wrapper\"><div name=\"header\"/>";

    public static final String PARTIAL_WRAPPED_RENDER_END = "<div name=\"footer\"></div></div>";

    @Test
    void shouldStartSimpleElementsToResponseWriter() throws IOException {
        var sink = new StringWriter();
        var mockWriter = new MockResponseWriter(sink);
        createSimplePartialRenderBuilder().writeToResponseWriterUntilChildBreakpoint(mockWriter);
        assertEquals(PARTIAL_SIMPLE_RENDER_START, sink.toString());
    }

    @Test
    void shouldEndSimpleElementsToResponseWriter() throws IOException {
        var sink = new StringWriter();
        var mockWriter = new MockResponseWriter(sink);
        createSimplePartialRenderBuilder().writeToResponseWriterFromChildBreakpointOn(mockWriter);
        assertEquals(PARTIAL_SIMPLE_RENDER_END, sink.toString());
    }

    @Test
    void shouldStartWrappedElementsToResponseWriter() throws IOException {
        var sink = new StringWriter();
        var mockWriter = new MockResponseWriter(sink);
        createWrappedPartialRenderBuilder().writeToResponseWriterUntilChildBreakpoint(mockWriter);
        assertEquals(PARTIAL_WRAPPED_RENDER_START, sink.toString());
    }
}
