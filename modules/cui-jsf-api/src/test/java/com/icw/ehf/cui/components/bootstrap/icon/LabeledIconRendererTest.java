package com.icw.ehf.cui.components.bootstrap.icon;

import javax.faces.component.UIComponent;

import org.junit.jupiter.api.Test;

import com.icw.ehf.cui.components.bootstrap.CssCuiBootstrap;
import com.icw.ehf.cui.core.api.CoreJsfTestConfiguration;
import com.icw.ehf.cui.core.api.components.css.AlignHolder;
import com.icw.ehf.cui.core.api.components.html.AttributeName;
import com.icw.ehf.cui.core.api.components.html.HtmlTreeBuilder;
import com.icw.ehf.cui.core.api.components.html.Node;
import com.icw.ehf.cui.core.api.components.partial.IconProvider;

import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.renderer.AbstractComponentRendererTest;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class LabeledIconRendererTest extends AbstractComponentRendererTest<LabeledIconRenderer> {

    private static final String ICON = "cui-icon-alarm";

    private static final String ICON_PREFIX = "cui-icon";

    private static final String ICON_RESULT_CSS = ICON_PREFIX + " " + ICON;

    private static final String SOME_TITLE = "hello";

    @Test
    void shouldRenderMinimal() {
        var expected =
            new HtmlTreeBuilder().withNode(Node.SPAN).withStyleClass(CssCuiBootstrap.LABELED_ICON_WRAPPER);
        // Icon
        expected.withNode(Node.SPAN).withStyleClass(IconProvider.FALLBACK_ICON_STRING).currentHierarchyUp();
        // Text
        expected.withNode(Node.SPAN).withStyleClass(CssCuiBootstrap.LABELED_ICON_TEXT).currentHierarchyUp();
        assertRenderResult(getComponent(), expected.getDocument());
    }

    @Test
    void shouldRenderLabelContent() {
        var component = new LabeledIconComponent();
        component.setLabelValue(SOME_TITLE);
        var expected =
            new HtmlTreeBuilder().withNode(Node.SPAN).withStyleClass(CssCuiBootstrap.LABELED_ICON_WRAPPER);
        // Icon
        expected.withNode(Node.SPAN).withStyleClass(IconProvider.FALLBACK_ICON_STRING).currentHierarchyUp();
        // Text
        expected.withNode(Node.SPAN).withStyleClass(CssCuiBootstrap.LABELED_ICON_TEXT).withTextContent(SOME_TITLE)
                .currentHierarchyUp();
        assertRenderResult(component, expected.getDocument());
    }

    @Test
    void shouldRenderCustomIconWithLabel() {
        var component = new LabeledIconComponent();
        component.setLabelValue(SOME_TITLE);
        component.setIcon(ICON);
        var expected =
            new HtmlTreeBuilder().withNode(Node.SPAN).withStyleClass(CssCuiBootstrap.LABELED_ICON_WRAPPER);
        // Icon
        expected.withNode(Node.SPAN).withStyleClass(ICON_RESULT_CSS).currentHierarchyUp();
        // Text
        expected.withNode(Node.SPAN).withStyleClass(CssCuiBootstrap.LABELED_ICON_TEXT).withTextContent(SOME_TITLE)
                .currentHierarchyUp();
        assertRenderResult(component, expected.getDocument());
    }

    @Test
    void shouldRenderRightAlignedIcon() {
        var component = new LabeledIconComponent();
        component.setIconAlign(AlignHolder.RIGHT.name());
        component.setLabelValue(SOME_TITLE);
        component.setIcon(ICON);
        var expected =
            new HtmlTreeBuilder().withNode(Node.SPAN).withStyleClass(CssCuiBootstrap.LABELED_ICON_WRAPPER);
        // Text
        expected.withNode(Node.SPAN).withStyleClass(CssCuiBootstrap.LABELED_ICON_TEXT).withTextContent(SOME_TITLE)
                .currentHierarchyUp();
        // Icon
        expected.withNode(Node.SPAN).withStyleClass(ICON_RESULT_CSS).currentHierarchyUp();
        assertRenderResult(component, expected.getDocument());
    }

    @Test
    void shouldRenderWithTitle() {
        var component = new LabeledIconComponent();
        component.setLabelValue(SOME_TITLE);
        component.setTitleValue(SOME_TITLE);
        component.setIcon(ICON);
        var expected = new HtmlTreeBuilder().withNode(Node.SPAN)
                .withStyleClass(CssCuiBootstrap.LABELED_ICON_WRAPPER).withAttribute(AttributeName.TITLE, SOME_TITLE);
        // Icon
        expected.withNode(Node.SPAN).withStyleClass(ICON_RESULT_CSS).currentHierarchyUp();
        // Text
        expected.withNode(Node.SPAN).withStyleClass(CssCuiBootstrap.LABELED_ICON_TEXT).withTextContent(SOME_TITLE)
                .currentHierarchyUp();
        assertRenderResult(component, expected.getDocument());
    }

    @Override
    protected UIComponent getComponent() {
        return new LabeledIconComponent();
    }
}
