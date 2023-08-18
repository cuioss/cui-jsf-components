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
package de.cuioss.jsf.api.components;

import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.html.Node;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class ComponentTestUtils {

    /**
     * @return {@link HtmlTreeBuilder} configured in a way that partial rendering
     *         can be tested. It defines three direct children: one div(header), a
     *         childBreakpoint with some content that should never be rendered and
     *         another div(footer).
     */
    public static HtmlTreeBuilder createSimplePartialRenderBuilder() {
        final var builder = new HtmlTreeBuilder().withNode(Node.DIV).withAttribute(AttributeName.NAME, "header");
        builder.currentHierarchyUp().withNodeChildBreakpoint().withNode(Node.DIV)
                .withAttribute(AttributeName.NAME, "DoNotRender").currentHierarchyUp();
        builder.currentHierarchyUp().withNode(Node.DIV).withAttribute(AttributeName.NAME, "footer");
        return builder;
    }

    /**
     * @return {@link HtmlTreeBuilder} configured in a way that partial rendering
     *         can be tested. It defines a sourround div (wrapper) with three
     *         children: one div(header), a childBreakpoint with some content that
     *         should never be rendered and another div(footer).
     */
    public static HtmlTreeBuilder createWrappedPartialRenderBuilder() {
        final var builder = new HtmlTreeBuilder().withNode(Node.DIV).withAttribute(AttributeName.NAME, "wrapper")
                .withNode(Node.DIV).withAttribute(AttributeName.NAME, "header");
        builder.currentHierarchyUp().withNodeChildBreakpoint().withNode(Node.DIV)
                .withAttribute(AttributeName.NAME, "DoNotRender").currentHierarchyUp();
        builder.currentHierarchyUp().withNode(Node.DIV).withAttribute(AttributeName.NAME, "footer");
        return builder;
    }
}
