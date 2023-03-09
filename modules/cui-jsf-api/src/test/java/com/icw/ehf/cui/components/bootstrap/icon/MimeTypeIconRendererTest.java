package com.icw.ehf.cui.components.bootstrap.icon;

import javax.faces.component.UIComponent;

import org.junit.jupiter.api.Test;

import com.icw.ehf.cui.core.api.components.css.ContextSize;
import com.icw.ehf.cui.core.api.components.html.AttributeName;
import com.icw.ehf.cui.core.api.components.html.HtmlTreeBuilder;
import com.icw.ehf.cui.core.api.components.html.Node;
import com.icw.ehf.cui.core.api.converter.StringIdentConverter;

import de.cuioss.test.jsf.renderer.AbstractComponentRendererTest;

class MimeTypeIconRendererTest extends AbstractComponentRendererTest<MimeTypeIconRenderer> {

    private static final String TITLE = "someTitle";

    @Test
    void shouldRenderMinimal() {
        var expected = new HtmlTreeBuilder().withNode(Node.SPAN).withStyleClass("cui-icon-stack");
        // Layer 1
        expected.withNode(Node.ITALIC).withStyleClass("cui-mime-type cui-icon-stack-1x cui-mime-type-folder")
                .currentHierarchyUp();
        // Layer 2
        expected.withNode(Node.ITALIC).withStyleClass("cui-mime-type cui-icon-stack-1x cui-mime-type-no-decorator")
                .currentHierarchyUp();
        // Layer 3
        expected.withNode(Node.ITALIC)
                .withStyleClass(
                        "cui-mime-type cui-icon-stack-1x cui-mime-type-placeholder cui-mime-type-undefined-placeholder")
                .currentHierarchyUp();
        // Layer 4
        expected.withNode(Node.ITALIC).withStyleClass("cui-mime-type cui-icon-stack-1x cui-mime-type-undefined");
        assertRenderResult(getComponent(), expected.getDocument());
    }

    @Test
    void shouldRenderCustomDecorator() {
        var component = new MimeTypeIconComponent();
        component.setDecoratorClass(TITLE);
        var expected = new HtmlTreeBuilder().withNode(Node.SPAN).withStyleClass("cui-icon-stack");
        // Layer 1
        expected.withNode(Node.ITALIC).withStyleClass("cui-mime-type cui-icon-stack-1x cui-mime-type-folder")
                .currentHierarchyUp();
        // Layer 2
        expected.withNode(Node.ITALIC).withStyleClass("cui-mime-type cui-icon-stack-1x " + TITLE).currentHierarchyUp();
        // Layer 3
        expected.withNode(Node.ITALIC)
                .withStyleClass(
                        "cui-mime-type cui-icon-stack-1x cui-mime-type-placeholder cui-mime-type-undefined-placeholder")
                .currentHierarchyUp();
        // Layer 4
        expected.withNode(Node.ITALIC).withStyleClass("cui-mime-type cui-icon-stack-1x cui-mime-type-undefined");
        assertRenderResult(component, expected.getDocument());
    }

    @Test
    void shouldRenderMimeType() {
        var component = new MimeTypeIconComponent();
        component.setMimeTypeIcon(MimeTypeIcon.AUDIO_BASIC);
        var expected = new HtmlTreeBuilder().withNode(Node.SPAN).withStyleClass("cui-icon-stack");
        // Layer 1
        expected.withNode(Node.ITALIC).withStyleClass("cui-mime-type cui-icon-stack-1x cui-mime-type-folder")
                .currentHierarchyUp();
        // Layer 2
        expected.withNode(Node.ITALIC).withStyleClass("cui-mime-type cui-icon-stack-1x cui-mime-type-no-decorator")
                .currentHierarchyUp();
        // Layer 3
        expected.withNode(Node.ITALIC).withStyleClass("cui-mime-type cui-icon-stack-1x cui-mime-type-placeholder "
                + MimeTypeIcon.AUDIO_BASIC.getPlaceholder()).currentHierarchyUp();
        // Layer 4
        expected.withNode(Node.ITALIC)
                .withStyleClass("cui-mime-type cui-icon-stack-1x " + MimeTypeIcon.AUDIO_BASIC.getIconClass());
        assertRenderResult(component, expected.getDocument());
    }

    @Test
    void shouldRenderCustomSize() {
        var component = new MimeTypeIconComponent();
        component.setSize(ContextSize.LG.name());
        var expected =
            new HtmlTreeBuilder().withNode(Node.SPAN).withStyleClass("cui-icon-stack cui-icon-lg");
        // Layer 1
        expected.withNode(Node.ITALIC).withStyleClass("cui-mime-type cui-icon-stack-1x cui-mime-type-folder")
                .currentHierarchyUp();
        // Layer 2
        expected.withNode(Node.ITALIC).withStyleClass("cui-mime-type cui-icon-stack-1x cui-mime-type-no-decorator")
                .currentHierarchyUp();
        // Layer 3
        expected.withNode(Node.ITALIC)
                .withStyleClass(
                        "cui-mime-type cui-icon-stack-1x cui-mime-type-placeholder cui-mime-type-undefined-placeholder")
                .currentHierarchyUp();
        // Layer 4
        expected.withNode(Node.ITALIC).withStyleClass("cui-mime-type cui-icon-stack-1x cui-mime-type-undefined");
        assertRenderResult(component, expected.getDocument());
    }

    @Test
    void shouldRenderTitle() {
        var component = new MimeTypeIconComponent();
        component.setTitleValue(TITLE);
        component.setTitleConverter(new StringIdentConverter());
        var expected = new HtmlTreeBuilder().withNode(Node.SPAN).withStyleClass("cui-icon-stack")
                .withAttribute(AttributeName.TITLE, TITLE);
        // Layer 1
        expected.withNode(Node.ITALIC).withStyleClass("cui-mime-type cui-icon-stack-1x cui-mime-type-folder")
                .currentHierarchyUp();
        // Layer 2
        expected.withNode(Node.ITALIC).withStyleClass("cui-mime-type cui-icon-stack-1x cui-mime-type-no-decorator")
                .currentHierarchyUp();
        // Layer 3
        expected.withNode(Node.ITALIC)
                .withStyleClass(
                        "cui-mime-type cui-icon-stack-1x cui-mime-type-placeholder cui-mime-type-undefined-placeholder")
                .currentHierarchyUp();
        // Layer 4
        expected.withNode(Node.ITALIC).withStyleClass("cui-mime-type cui-icon-stack-1x cui-mime-type-undefined");
        assertRenderResult(component, expected.getDocument());
    }

    @Override
    protected UIComponent getComponent() {
        return new MimeTypeIconComponent();
    }
}
