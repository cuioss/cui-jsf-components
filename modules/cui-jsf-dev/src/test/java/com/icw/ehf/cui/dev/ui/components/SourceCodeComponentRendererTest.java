package com.icw.ehf.cui.dev.ui.components;

import static com.icw.ehf.cui.dev.ui.components.SourceCodeComponent.LangStyle.LANG_HTML;
import static com.icw.ehf.cui.dev.ui.components.SourceCodeComponentRenderer.PRE_STYLE_CLASS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;

import com.icw.ehf.cui.core.api.components.html.AttributeName;
import com.icw.ehf.cui.core.api.components.html.AttributeValue;
import com.icw.ehf.cui.core.api.components.html.HtmlTreeBuilder;
import com.icw.ehf.cui.core.api.components.html.Node;
import de.cuioss.test.jsf.renderer.AbstractComponentRendererTest;
import de.cuioss.tools.string.MoreStrings;
import org.junit.jupiter.api.Test;

class SourceCodeComponentRendererTest extends AbstractComponentRendererTest<SourceCodeComponentRenderer> {

    private static final String MINIMAL_CONTENT = "hello";

    private static final String MINIMAL_DESCRIPTION = "description";

    private static final String DEFAULT_ID = "j_id__v_0_";

    private static final String COPY_BUTTON_ID = DEFAULT_ID + SourceCodeComponentRenderer.COPY_BTN_IDENTIFIER;

    private static final String TEXT_AREA_ID = DEFAULT_ID + SourceCodeComponentRenderer.ID_INVISIBLE_TEXT_AREA_SUFFIX;

    private static final String PRE_WRAPPER_ID = DEFAULT_ID + SourceCodeComponentRenderer.ID_PRE_WRAPPER;

    @Override
    protected UIComponent getComponent() {
        return new SourceCodeComponent();
    }

    @Test
    void shouldRenderMinimal() {
        final SourceCodeComponent component = new SourceCodeComponent();
        component.setSource(MINIMAL_CONTENT);
        component.setEnableClipboard(false);
        final HtmlTreeBuilder expected = new HtmlTreeBuilder().withNode(Node.PRE).withAttributeNameAndId(PRE_WRAPPER_ID)
                .withStyleClass(PRE_STYLE_CLASS).withNode(Node.CODE).withStyleClass(LANG_HTML.getStyle())
                .withTextContent(MINIMAL_CONTENT);
        assertRenderResult(component, expected.getDocument());
    }

    @Test
    void shouldRenderWithDescription() {
        final SourceCodeComponent component = new SourceCodeComponent();
        component.setSource(MINIMAL_CONTENT);
        component.setDescription(MINIMAL_DESCRIPTION);
        component.setEnableClipboard(false);
        final HtmlTreeBuilder expected = new HtmlTreeBuilder();
        // Description
        expected.withNode(Node.P).withTextContent(MINIMAL_DESCRIPTION).currentHierarchyUp();
        // Actual Content of source area
        expected.withNode(Node.PRE).withAttributeNameAndId(PRE_WRAPPER_ID).withStyleClass(PRE_STYLE_CLASS)
            .withNode(Node.CODE).withStyleClass(LANG_HTML.getStyle()).withTextContent(MINIMAL_CONTENT);
        assertRenderResult(component, expected.getDocument());
    }

    @Test
    void shouldRenderWithCopyButton() {
        final SourceCodeComponent component = new SourceCodeComponent();
        component.setSource(MINIMAL_CONTENT);
        component.setEnableClipboard(true);
        final HtmlTreeBuilder expected = new HtmlTreeBuilder();
        // Copy Button
        expected.withNode(Node.DIV).withStyleClass(SourceCodeComponentRenderer.COPY_BTN_WRAPPER_CLASS);
        expected.withNode(Node.BUTTON).withAttributeNameAndId(COPY_BUTTON_ID)
                .withStyleClass(SourceCodeComponentRenderer.COPY_BTN_CLASS)
                .withAttribute(AttributeName.JS_ON_CLICK,
                        String.format(SourceCodeComponentRenderer.COPY_ON_CLICK_HANDLER, TEXT_AREA_ID, COPY_BUTTON_ID))
                .withAttribute(AttributeName.TITLE, SourceCodeComponentRenderer.COPY_BUTTON_TITLE)
                .withAttribute(AttributeName.DATA_TOGGLE, AttributeValue.TOOLTIP);
        // Text-Content of copy-button
        expected.withNode(Node.SPAN).withTextContent(SourceCodeComponentRenderer.COPY_BUTTON_LABEL).currentHierarchyUp()
                .currentHierarchyUp().currentHierarchyUp();
        // Textarea
        expected.withNode(Node.TEXT_AREA).withAttributeNameAndId(TEXT_AREA_ID)
                .withAttribute(AttributeName.STYLE, AttributeValue.STYLE_DISPLAY_NONE).withTextContent(MINIMAL_CONTENT)
                .currentHierarchyUp();
        // Actual Content of source area
        expected.withNode(Node.PRE).withAttributeNameAndId(PRE_WRAPPER_ID).withStyleClass(PRE_STYLE_CLASS)
                .withNode(Node.CODE).withStyleClass(LANG_HTML.getStyle()).withTextContent(MINIMAL_CONTENT);
        assertRenderResult(component, expected.getDocument());
    }

    @Test
    void shouldNotSplitWithoutWhitespace() {
        final String nosplit = "nosplit";
        assertEquals(nosplit, SourceCodeComponentRenderer.limitLineSize(nosplit, 128));
        assertEquals(nosplit, SourceCodeComponentRenderer.limitLineSize(nosplit, 5));
        assertEquals(nosplit, SourceCodeComponentRenderer.limitLineSize(nosplit, 7));
    }

    @Test
    void shouldSplitLinesSimple() {
        final String willSplit = "split1 split2 split3 split4";
        final List<String> result = new ArrayList<>();
        SourceCodeComponentRenderer.handleLineSplit(result, willSplit, 7);
        assertEquals(4, result.size());
        String willSplitStringResult = SourceCodeComponentRenderer.limitLineSize(willSplit, 7);
        assertFalse(willSplit.equals(willSplitStringResult));
        assertTrue(willSplitStringResult.contains(System.lineSeparator()));
        assertEquals(1, MoreStrings.countMatches(willSplitStringResult, "split1"));
        assertEquals(1, MoreStrings.countMatches(willSplitStringResult, "split2"));
        assertEquals(1, MoreStrings.countMatches(willSplitStringResult, "split3"));
        assertEquals(1, MoreStrings.countMatches(willSplitStringResult, "split4"));
        assertEquals(3, MoreStrings.countMatches(willSplitStringResult, System.lineSeparator()));
        willSplitStringResult = SourceCodeComponentRenderer.limitLineSize(willSplit, 15);
        assertTrue(willSplitStringResult.contains(System.lineSeparator()));
        assertEquals(1, MoreStrings.countMatches(willSplitStringResult, "split1"));
        assertEquals(1, MoreStrings.countMatches(willSplitStringResult, "split2"));
        assertEquals(1, MoreStrings.countMatches(willSplitStringResult, "split3"));
        assertEquals(1, MoreStrings.countMatches(willSplitStringResult, "split4"));
        assertEquals(1, MoreStrings.countMatches(willSplitStringResult, System.lineSeparator()));
    }

    @Test
    void shouldHandleSplitLinesComplex() {
        final String withIndent = "   someIndent a=b&gt;";
        final String withIndentResult = "   someIndent" + System.lineSeparator() + "     a=b&gt;";
        final String withIndentReturned = SourceCodeComponentRenderer.limitLineSize(withIndent, 12);
        assertTrue(withIndentReturned.contains(System.lineSeparator()));
        assertEquals(1, MoreStrings.countMatches(withIndentReturned, System.lineSeparator()));
        assertEquals(withIndentResult, withIndentReturned);
    }

    @Test
    void shouldSanitizeLineBreaks() {
        final SourceCodeComponent component = new SourceCodeComponent();
        component.setEnableClipboard(false);
        component.setSource("1\n2\r3\n\r4\r\n5%n6");
        final HtmlTreeBuilder expected = new HtmlTreeBuilder()
            .withNode(Node.PRE)
            .withAttributeNameAndId(PRE_WRAPPER_ID)
            .withStyleClass(PRE_STYLE_CLASS)
            .withNode(Node.CODE)
            .withStyleClass(LANG_HTML.getStyle())
            .withTextContent("1\n2\n3\n4\n5\n6");
        assertRenderResult(component, expected.getDocument());
    }
}
