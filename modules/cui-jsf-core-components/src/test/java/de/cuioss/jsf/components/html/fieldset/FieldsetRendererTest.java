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
package de.cuioss.jsf.components.html.fieldset;

import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.partial.StyleAttributeProviderImpl;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.renderer.VetoRenderAttributeAssert;
import de.cuioss.test.jsf.renderer.AbstractComponentRendererTest;
import de.cuioss.test.jsf.renderer.CommonRendererAsserts;
import jakarta.faces.component.UIComponent;
import jakarta.faces.event.PreRenderComponentEvent;
import org.junit.jupiter.api.Test;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
@VetoRenderAttributeAssert({CommonRendererAsserts.STYLE, CommonRendererAsserts.STYLE_CLASS})
class FieldsetRendererTest extends AbstractComponentRendererTest<FieldsetRenderer> {

    private static final String LEGEND_TEXT = "Some Legend";

    @Test
    void shouldRenderMinimal() {
        var expected = new HtmlTreeBuilder().withNode(Node.FIELDSET);
        assertRenderResult(getComponent(), expected.getDocument());
    }

    @Test
    void shouldRenderLegend() {
        var expected = new HtmlTreeBuilder().withNode(Node.FIELDSET).withNode(Node.LEGEND).withTextContent(LEGEND_TEXT);
        var component = new FieldsetComponent();
        component.setLegendValue(LEGEND_TEXT);
        assertRenderResult(component, expected.getDocument());
    }

    @Test
    void shouldRenderFull() {
        var expected = new HtmlTreeBuilder().withNode(Node.FIELDSET)
                .withAttribute(FieldsetComponent.DISABLED_ATTRIBUTE_NAME, FieldsetComponent.DISABLED_ATTRIBUTE_NAME)
                .withAttribute(AttributeName.CLASS, "styleClass").withAttribute(StyleAttributeProviderImpl.KEY, "style")
                .withNode(Node.LEGEND).withTextContent(LEGEND_TEXT);
        var component = new FieldsetComponent();
        component.setLegendValue(LEGEND_TEXT);
        component.setDisabled(true);
        component.processEvent(new PreRenderComponentEvent(component));
        component.setStyle("style");
        component.setStyleClass("styleClass");
        assertRenderResult(component, expected.getDocument());
    }

    @Override
    protected UIComponent getComponent() {
        return new FieldsetComponent();
    }
}
