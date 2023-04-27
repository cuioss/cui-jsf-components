package de.cuioss.jsf.bootstrap.notification;

import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlForm;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.components.css.ContextState;
import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.mocks.CuiMockMethodExpression;
import de.cuioss.test.jsf.renderer.AbstractComponentRendererTest;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class NotificationBoxRendererTest extends AbstractComponentRendererTest<NotificationBoxRenderer> {

    private static final String CLIENT_ID = "j_id__v_0:j_id0";

    private static final String SOME_VALUE = "Some Value";

    @Test
    void shouldRenderContent() {
        var component = new NotificationBoxComponent();
        component.setContentValue(SOME_VALUE);
        component.setState(ContextState.INFO.name());
        var expected = new HtmlTreeBuilder().withNode(Node.DIV).withStyleClass("alert alert-info")
                .withAttribute(AttributeName.ROLE, "alert").withTextContent(SOME_VALUE);
        assertRenderResult(component, expected.getDocument());
    }

    @Test
    void shouldRenderDismissible() {
        var component = new NotificationBoxComponent();
        component.setState(ContextState.INFO.name());
        component.setContentValue(SOME_VALUE);
        component.setDismissible(true);
        var expected = new HtmlTreeBuilder().withNode(Node.DIV)
                .withStyleClass("alert alert-info alert-dismissible").withAttribute(AttributeName.ROLE, "alert")
                .withNode(Node.BUTTON).withAttribute(AttributeName.TYPE, "button").withStyleClass("close")
                .withAttribute(AttributeName.DATA_DISMISS, "alert").withNode(Node.SPAN)
                .withAttribute(AttributeName.ARIA_HIDDEN, "true").withTextContent("×").currentHierarchyUp()
                .currentHierarchyUp().withTextContent(SOME_VALUE);
        assertRenderResult(component, expected.getDocument());
    }

    @Test
    void shouldFailWhenRenderingDismissListenerWithoutForm() {
        var component = new NotificationBoxComponent();
        component.setState(ContextState.INFO.name());
        component.setContentValue(SOME_VALUE);
        component.setDismissible(true);
        component.setDismissListener(new CuiMockMethodExpression());
        assertThrows(IllegalStateException.class, () -> renderToString(component));
    }

    @Test
    void shouldRenderDismissibleWithListener() {
        var component = new NotificationBoxComponent();
        var form = new HtmlForm();
        form.getChildren().add(component);
        component.setState(ContextState.INFO.name());
        component.setContentValue(SOME_VALUE);
        component.setDismissible(true);
        component.setDismissListener(new CuiMockMethodExpression());
        var expected = new HtmlTreeBuilder().withNode(Node.DIV).withAttribute(AttributeName.ID, CLIENT_ID)
                .withAttribute(AttributeName.NAME, CLIENT_ID).withStyleClass("alert alert-info alert-dismissible")
                .withAttribute(AttributeName.ROLE, "alert").withNode(Node.BUTTON)
                .withAttribute(AttributeName.TYPE, "button").withStyleClass("close")
                .withAttribute(AttributeName.DATA_DISMISS, "alert")
                .withAttribute(AttributeName.DATA_DISMISS_LISTENER, "true").withNode(Node.SPAN)
                .withAttribute(AttributeName.ARIA_HIDDEN, "true").withTextContent("×").currentHierarchyUp()
                .currentHierarchyUp().withTextContent(SOME_VALUE);
        assertRenderResult(component, expected.getDocument());
    }

    void shouldRenderMinimal() {
        var component = new NotificationBoxComponent();
        component.setState(ContextState.INFO.name());
        var expected = new HtmlTreeBuilder().withNode(Node.DIV).withStyleClass("alert alert-info")
                .withAttribute(AttributeName.ROLE, "alert");
        assertRenderResult(component, expected.getDocument());
    }

    @Override
    protected UIComponent getComponent() {
        return new NotificationBoxComponent();
    }
}
