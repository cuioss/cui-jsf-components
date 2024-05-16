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
package de.cuioss.jsf.components.inlineconfirm;

import de.cuioss.jsf.api.components.JsfHtmlComponent;
import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.AttributeValue;
import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.renderer.VetoRenderAttributeAssert;
import de.cuioss.test.jsf.renderer.AbstractComponentRendererTest;
import de.cuioss.test.jsf.renderer.CommonRendererAsserts;
import jakarta.faces.component.UIComponent;
import org.junit.jupiter.api.Test;

import static de.cuioss.jsf.components.inlineconfirm.InlineConfirmRenderer.DATA_IDENTIFIER;
import static de.cuioss.jsf.components.inlineconfirm.InlineConfirmRenderer.DATA_TARGET_IDENTIFIER;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
@VetoRenderAttributeAssert({CommonRendererAsserts.STYLE, CommonRendererAsserts.STYLE_CLASS,
    CommonRendererAsserts.PASSTHROUGH, CommonRendererAsserts.ID})
class InlineConfirmRendererTest extends AbstractComponentRendererTest<InlineConfirmRenderer> {

    @Override
    protected UIComponent getComponent() {
        var component = new InlineConfirmComponent();
        component.getFacets().put(InlineConfirmComponent.INITIAL_FACET_NAME,
            JsfHtmlComponent.BUTTON.component(getFacesContext()));
        UIComponent button = JsfHtmlComponent.BUTTON.component(getFacesContext());
        button.getAttributes().put(AttributeName.STYLE.getContent(), "border-radius: 3px;");
        component.getChildren().add(button);
        return component;
    }

    @Test
    void shouldRenderMinimal() {
        var expected = new HtmlTreeBuilder().withNode(Node.BUTTON).withAttribute(DATA_IDENTIFIER, DATA_IDENTIFIER);
        expected.currentHierarchyUp().withNode(Node.BUTTON)
            .withAttribute(DATA_TARGET_IDENTIFIER, DATA_TARGET_IDENTIFIER).withAttribute(AttributeName.STYLE,
                AttributeValue.STYLE_DISPLAY_NONE.getContent() + "border-radius: 3px;");
        assertRenderResult(getComponent(), expected.getDocument());
    }

}
