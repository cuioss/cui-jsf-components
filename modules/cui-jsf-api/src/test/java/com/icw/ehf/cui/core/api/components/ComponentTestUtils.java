package com.icw.ehf.cui.core.api.components;

import com.icw.ehf.cui.core.api.components.html.AttributeName;
import com.icw.ehf.cui.core.api.components.html.HtmlTreeBuilder;
import com.icw.ehf.cui.core.api.components.html.Node;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class ComponentTestUtils {

    /**
     * @return {@link HtmlTreeBuilder} configured in a way that partial rendering can be tested. It
     *         defines three direct children: one div(header), a childBreakpoint with some content
     *         that should never be rendered and another div(footer).
     */
    public static HtmlTreeBuilder createSimplePartialRenderBuilder() {
        final var builder =
            new HtmlTreeBuilder().withNode(Node.DIV).withAttribute(AttributeName.NAME, "header");
        builder.currentHierarchyUp().withNodeChildBreakpoint().withNode(Node.DIV)
            .withAttribute(AttributeName.NAME, "DoNotRender").currentHierarchyUp();
        builder.currentHierarchyUp().withNode(Node.DIV).withAttribute(AttributeName.NAME, "footer");
        return builder;
    }

    /**
     * @return {@link HtmlTreeBuilder} configured in a way that partial rendering can be tested. It
     *         defines a sourround div (wrapper) with three children: one div(header), a
     *         childBreakpoint with some content that should never be rendered and another
     *         div(footer).
     */
    public static HtmlTreeBuilder createWrappedPartialRenderBuilder() {
        final var builder =
            new HtmlTreeBuilder().withNode(Node.DIV).withAttribute(AttributeName.NAME, "wrapper").withNode(Node.DIV)
                .withAttribute(AttributeName.NAME, "header");
        builder.currentHierarchyUp().withNodeChildBreakpoint().withNode(Node.DIV)
            .withAttribute(AttributeName.NAME, "DoNotRender").currentHierarchyUp();
        builder.currentHierarchyUp().withNode(Node.DIV).withAttribute(AttributeName.NAME, "footer");
        return builder;
    }
}
