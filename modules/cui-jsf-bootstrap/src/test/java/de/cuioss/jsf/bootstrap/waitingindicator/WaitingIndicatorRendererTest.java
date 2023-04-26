package de.cuioss.jsf.bootstrap.waitingindicator;

import javax.faces.component.UIComponent;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.bootstrap.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.renderer.AbstractComponentRendererTest;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class WaitingIndicatorRendererTest extends AbstractComponentRendererTest<WaitingIndicatorRenderer> {

    @Test
    void shouldRenderMinimal() {
        var expected = new HtmlTreeBuilder().withNode(Node.DIV).withNode(Node.DIV)
                .withStyleClass("waiting-indicator waiting-indicator-size-md").withNode(Node.DIV)
                .withStyleClass("item-1 item-size-md").currentHierarchyUp().withNode(Node.DIV)
                .withStyleClass("item-2 item-size-md").currentHierarchyUp().withNode(Node.DIV)
                .withStyleClass("item-3 item-size-md").currentHierarchyUp().withNode(Node.DIV)
                .withStyleClass("item-4 item-size-md").currentHierarchyUp().withNode(Node.DIV)
                .withStyleClass("item-5 item-size-md").currentHierarchyUp().currentHierarchyUp().currentHierarchyUp();
        assertRenderResult(getComponent(), expected.getDocument());
    }

    @Override
    protected UIComponent getComponent() {
        return new WaitingIndicatorComponent();
    }
}
