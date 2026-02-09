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
package de.cuioss.jsf.api.components;

import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.html.Node;
import lombok.experimental.UtilityClass;

/**
 * Utility class providing helper methods for component testing,
 * particularly for testing partial rendering scenarios.
 */
@UtilityClass
public final class ComponentTestUtils {

    /**
     * Creates a simple HTML tree structure for testing partial rendering.
     * 
     * <p>The structure consists of three direct children:
     * <ol>
     *   <li>A div element with name="header"</li>
     *   <li>A child breakpoint element (which should never be rendered in partial rendering tests)</li>
     *   <li>A div element with name="footer"</li>
     * </ol>
     * 
     * <p>This structure allows testing of partial rendering where only the elements
     * before or after the breakpoint should be rendered.
     * 
     * @return {@link HtmlTreeBuilder} configured for simple partial rendering tests
     */
    public static HtmlTreeBuilder createSimplePartialRenderBuilder() {
        final var builder = new HtmlTreeBuilder()
                .withNode(Node.DIV)
                .withAttribute(AttributeName.NAME, "header");

        builder.currentHierarchyUp()
                .withNodeChildBreakpoint()
                .withNode(Node.DIV)
                .withAttribute(AttributeName.NAME, "DoNotRender")
                .currentHierarchyUp();

        builder.currentHierarchyUp()
                .withNode(Node.DIV)
                .withAttribute(AttributeName.NAME, "footer");

        return builder;
    }

    /**
     * Creates a wrapped HTML tree structure for testing partial rendering.
     * 
     * <p>The structure consists of a wrapper div containing three children:
     * <ol>
     *   <li>A div element with name="header"</li>
     *   <li>A child breakpoint element (which should never be rendered in partial rendering tests)</li>
     *   <li>A div element with name="footer"</li>
     * </ol>
     * 
     * <p>This structure allows testing of partial rendering where only the elements
     * before or after the breakpoint should be rendered, all within a containing wrapper.
     * 
     * @return {@link HtmlTreeBuilder} configured for wrapped partial rendering tests
     */
    public static HtmlTreeBuilder createWrappedPartialRenderBuilder() {
        final var builder = new HtmlTreeBuilder()
                .withNode(Node.DIV)
                .withAttribute(AttributeName.NAME, "wrapper")
                .withNode(Node.DIV)
                .withAttribute(AttributeName.NAME, "header");

        builder.currentHierarchyUp()
                .withNodeChildBreakpoint()
                .withNode(Node.DIV)
                .withAttribute(AttributeName.NAME, "DoNotRender")
                .currentHierarchyUp();

        builder.currentHierarchyUp()
                .withNode(Node.DIV)
                .withAttribute(AttributeName.NAME, "footer");

        return builder;
    }
}
