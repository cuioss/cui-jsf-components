package com.icw.ehf.cui.components.inlineconfirm;

import static com.icw.ehf.cui.components.inlineconfirm.InlineConfirmRenderer.DATA_IDENTIFIER;
import static com.icw.ehf.cui.components.inlineconfirm.InlineConfirmRenderer.DATA_TARGET_IDENTIFIER;

import javax.faces.component.UIComponent;

import org.junit.jupiter.api.Test;

import com.icw.ehf.cui.core.api.CoreJsfTestConfiguration;
import com.icw.ehf.cui.core.api.components.JsfHtmlComponent;
import com.icw.ehf.cui.core.api.components.html.AttributeName;
import com.icw.ehf.cui.core.api.components.html.AttributeValue;
import com.icw.ehf.cui.core.api.components.html.HtmlTreeBuilder;
import com.icw.ehf.cui.core.api.components.html.Node;

import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.renderer.VetoRenderAttributeAssert;
import de.cuioss.test.jsf.renderer.AbstractComponentRendererTest;
import de.cuioss.test.jsf.renderer.CommonRendererAsserts;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
@VetoRenderAttributeAssert({ CommonRendererAsserts.STYLE, CommonRendererAsserts.STYLE_CLASS,
    CommonRendererAsserts.PASSTHROUGH, CommonRendererAsserts.ID })
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
        var expected =
            new HtmlTreeBuilder().withNode(Node.BUTTON).withAttribute(DATA_IDENTIFIER, DATA_IDENTIFIER);
        expected.currentHierarchyUp().withNode(Node.BUTTON)
                .withAttribute(DATA_TARGET_IDENTIFIER, DATA_TARGET_IDENTIFIER).withAttribute(AttributeName.STYLE,
                        AttributeValue.STYLE_DISPLAY_NONE.getContent() + "border-radius: 3px;");
        assertRenderResult(getComponent(), expected.getDocument());
    }
}
