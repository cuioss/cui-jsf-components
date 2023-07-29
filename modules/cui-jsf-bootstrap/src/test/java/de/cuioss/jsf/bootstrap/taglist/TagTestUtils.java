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
        expected.withNode(Node.LI).withNode("TagComponent").withAttributeNameAndId("j_id__v_" + String.valueOf(count))
                .currentHierarchyUp().currentHierarchyUp();
    }
}
