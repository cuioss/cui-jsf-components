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

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputText;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.jsf.bootstrap.common.partial.ColumnCssResolver;
import de.cuioss.test.jsf.renderer.AbstractComponentRendererTest;

class ControlGroupRendererTest extends AbstractComponentRendererTest<ControlGroupRenderer> {

    private static final String MINIMAL_COLUMN_CSS = ColumnCssResolver.COL_PREFIX + "8";

    @Test
    void shouldRenderMinimal() {
        final var component = new ControlGroupComponent();
        final var expected = new HtmlTreeBuilder().withNode(Node.DIV).withStyleClass(CssBootstrap.FORM_GROUP)
                .withNode(Node.DIV).withStyleClass(MINIMAL_COLUMN_CSS);
        assertRenderResult(component, expected.getDocument());
    }

    @Test
    void shouldRenderWithChildren() {
        final var component = new ControlGroupComponent();
        component.getChildren().add(new HtmlOutputText());
        getComponentConfigDecorator().registerMockRendererForHtmlOutputText();
        final var expected = new HtmlTreeBuilder().withNode(Node.DIV).withStyleClass(CssBootstrap.FORM_GROUP)
                .withNode(Node.DIV).withStyleClass(MINIMAL_COLUMN_CSS).withNode("HtmlOutputText");
        assertRenderResult(component, expected.getDocument());
    }

    @Override
    protected UIComponent getComponent() {
        return new ControlGroupComponent();
    }
}
