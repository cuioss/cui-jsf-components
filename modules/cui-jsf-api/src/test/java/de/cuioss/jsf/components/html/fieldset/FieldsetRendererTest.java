package de.cuioss.jsf.components.html.fieldset;

import javax.faces.component.UIComponent;
import javax.faces.event.PreRenderComponentEvent;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.CoreJsfTestConfiguration;
import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.partial.StyleAttributeProviderImpl;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.renderer.VetoRenderAttributeAssert;
import de.cuioss.test.jsf.renderer.AbstractComponentRendererTest;
import de.cuioss.test.jsf.renderer.CommonRendererAsserts;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
@VetoRenderAttributeAssert({ CommonRendererAsserts.STYLE, CommonRendererAsserts.STYLE_CLASS })
class FieldsetRendererTest extends AbstractComponentRendererTest<FieldsetRenderer> {

    private static final String LEGEND_TEXT = "Some Legend";

    @Test
    void shouldRenderMinimal() {
        var expected = new HtmlTreeBuilder().withNode(Node.FIELDSET);
        assertRenderResult(getComponent(), expected.getDocument());
    }

    @Test
    void shouldRenderLegend() {
        var expected =
            new HtmlTreeBuilder().withNode(Node.FIELDSET).withNode(Node.LEGEND).withTextContent(LEGEND_TEXT);
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
