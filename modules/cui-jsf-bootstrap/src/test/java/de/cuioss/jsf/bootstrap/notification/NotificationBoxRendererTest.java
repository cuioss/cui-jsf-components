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
package de.cuioss.jsf.bootstrap.notification;

import static org.junit.jupiter.api.Assertions.assertThrows;

import de.cuioss.jsf.api.components.css.ContextState;
import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.mocks.CuiMockMethodExpression;
import de.cuioss.test.jsf.renderer.AbstractComponentRendererTest;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.html.HtmlForm;
import org.junit.jupiter.api.Test;

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
        var expected = new HtmlTreeBuilder().withNode(Node.DIV).withStyleClass("alert alert-info alert-dismissible")
                .withAttribute(AttributeName.ROLE, "alert").withNode(Node.BUTTON)
                .withAttribute(AttributeName.TYPE, "button").withStyleClass("close")
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
