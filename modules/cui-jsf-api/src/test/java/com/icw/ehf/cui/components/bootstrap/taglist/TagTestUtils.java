package com.icw.ehf.cui.components.bootstrap.taglist;

import com.icw.ehf.cui.core.api.components.html.HtmlTreeBuilder;
import com.icw.ehf.cui.core.api.components.html.Node;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class TagTestUtils {

    public static void insertTag(final HtmlTreeBuilder expected) {
        insertTag(expected, 0);
    }

    public static void insertTag(final HtmlTreeBuilder expected, final int count) {
        expected.withNode(Node.LI).withNode("TagComponent").withAttributeNameAndId("j_id__v_" + String.valueOf(count))
            .currentHierarchyUp().currentHierarchyUp();
    }
}
