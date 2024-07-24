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
package de.cuioss.jsf.bootstrap.taglist;

import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.html.Node;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class TagTestUtils {

    public static void insertTag(final HtmlTreeBuilder expected) {
        insertTag(expected, 0);
    }

    public static void insertTag(final HtmlTreeBuilder expected, final int count) {
        expected.withNode(Node.LI).withNode("TagComponent").withAttributeNameAndId("j_id__v_" + count)
                .currentHierarchyUp().currentHierarchyUp();
    }
}
