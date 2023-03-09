package com.icw.ehf.cui.components.bootstrap.button;

import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;

import org.junit.jupiter.api.Test;

import com.icw.ehf.cui.components.bootstrap.CssBootstrap;
import com.icw.ehf.cui.components.bootstrap.icon.IconComponent;
import com.icw.ehf.cui.core.api.CoreJsfTestConfiguration;
import com.icw.ehf.cui.core.api.components.JsfComponentIdentifier;
import com.icw.ehf.cui.core.api.components.html.AttributeName;
import com.icw.ehf.cui.core.api.components.html.AttributeValue;
import com.icw.ehf.cui.core.api.components.html.HtmlTreeBuilder;
import com.icw.ehf.cui.core.api.components.html.Node;

import de.cuioss.test.jsf.config.ComponentConfigurator;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.mocks.CuiMockRenderer;
import de.cuioss.test.jsf.renderer.AbstractComponentRendererTest;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class CloseCommandButtonRendererTest extends AbstractComponentRendererTest<CloseCommandButtonRenderer>
        implements ComponentConfigurator {

    @Test
    void shouldRenderMinimal() {
        var expected = new HtmlTreeBuilder().withNode(Node.BUTTON)
                .withAttribute(AttributeName.ARIA_LABEL, AttributeValue.ARIA_CLOSE)
                .withStyleClass(CssBootstrap.BUTTON_CLOSE);
        expected.withNode(Node.SPAN).withAttribute(AttributeName.ARIA_HIDDEN, AttributeValue.TRUE)
                .withStyleClass(CssBootstrap.BUTTON_TEXT).withTextContent("&#xD7;");
        assertRenderResult(getComponent(), expected.getDocument());
    }

    @Test
    void shouldDecodeWOErrors() {
        getRenderer().decode(getFacesContext(), getComponent());
    }

    @Override
    public void configureComponents(final ComponentConfigDecorator decorator) {
        decorator.registerRenderer(UICommand.COMPONENT_FAMILY, JsfComponentIdentifier.BUTTON_RENDERER_TYPE,
                new CuiMockRenderer("input")).registerUIComponent(IconComponent.class);
    }

    @Override
    protected UIComponent getComponent() {
        return new CloseCommandButton();
    }
}
