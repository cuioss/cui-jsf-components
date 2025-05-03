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
package de.cuioss.jsf.bootstrap.layout;

import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.renderer.AbstractComponentRendererTest;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.html.HtmlOutputText;
import org.junit.jupiter.api.Test;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class LayoutComponentRendererTest extends AbstractComponentRendererTest<LayoutComponentRenderer> {

    @Test
    void shouldRenderMinimal() {
        var component = new RowComponent();
        var expected = new HtmlTreeBuilder().withNode(Node.DIV).withStyleClass(CssBootstrap.ROW);
        assertRenderResult(component, expected.getDocument());
    }

    @Test
    void shouldRenderWithChildren() {
        var component = new RowComponent();
        component.getChildren().add(new HtmlOutputText());
        var expected = new HtmlTreeBuilder().withNode(Node.DIV).withStyleClass(CssBootstrap.ROW)
                .withNode(Node.SPAN);
        assertRenderResult(component, expected.getDocument());
    }

    @Test
    void shouldHandleRenderedSetToFalse() {
        var component = new RowComponent();
        component.setRendered(false);
        component.getChildren().add(new HtmlOutputText());
        getComponentConfigDecorator().registerMockRendererForHtmlOutputText();
        assertEmptyRenderResult(component);
    }

    @Override
    protected UIComponent getComponent() {
        return new RowComponent();
    }
}
