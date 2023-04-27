package de.cuioss.jsf.components.html.fieldset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.faces.event.PostAddToViewEvent;
import javax.faces.event.PreRenderComponentEvent;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.components.CuiFamily;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.component.AbstractComponentTest;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import de.cuioss.test.jsf.mocks.ReverseConverter;

@VerifyComponentProperties(of = { "disabled", "legendKey", "legendValue", "legendConverter", "legendEscape" })
@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class FieldsetComponentTest extends AbstractComponentTest<FieldsetComponent> {

    protected static final String MESSAGE_KEY = "some.key";

    protected static final String MESSAGE_VALUE = "abc";

    @Test
    void shouldResolveLegend() {
        var component = anyComponent();
        assertNull(component.resolveLegend());
        component.setLegendKey(MESSAGE_KEY);
        assertEquals(MESSAGE_KEY, component.resolveLegend());
        component = anyComponent();
        component.setLegendValue(MESSAGE_VALUE);
        assertEquals(MESSAGE_VALUE, component.resolveLegend());
        component = anyComponent();
        component.setLegendValue(MESSAGE_VALUE);
        component.setLegendConverter(new ReverseConverter());
        assertEquals("cba", component.resolveLegend());
    }

    @Test
    void shouldHandleDisableAttribute() {
        var component = anyComponent();
        component.processEvent(new PreRenderComponentEvent(getFacesContext(), component));
        assertFalse(component.getPassThroughAttributes(true).containsKey(FieldsetComponent.DISABLED_ATTRIBUTE_NAME));
        component.setDisabled(true);
        component.processEvent(new PreRenderComponentEvent(getFacesContext(), component));
        assertTrue(component.getPassThroughAttributes(true).containsKey(FieldsetComponent.DISABLED_ATTRIBUTE_NAME));
        component.setDisabled(false);
        component.processEvent(new PreRenderComponentEvent(getFacesContext(), component));
        assertFalse(component.getPassThroughAttributes(true).containsKey(FieldsetComponent.DISABLED_ATTRIBUTE_NAME));
        // Should ignore other events
        component.setDisabled(true);
        component.processEvent(new PostAddToViewEvent(getFacesContext(), component));
        assertFalse(component.getPassThroughAttributes(true).containsKey(FieldsetComponent.DISABLED_ATTRIBUTE_NAME));
    }

    @Test
    void shouldProvideCorrectMetadata() {
        assertEquals(CuiFamily.FIELDSET_RENDERER, anyComponent().getRendererType());
        assertEquals(CuiFamily.COMPONENT_FAMILY, anyComponent().getFamily());
    }
}
