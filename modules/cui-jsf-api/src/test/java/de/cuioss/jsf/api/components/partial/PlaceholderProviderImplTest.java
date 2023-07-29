package de.cuioss.jsf.api.components.partial;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.faces.component.UIComponent;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.components.JsfHtmlComponent;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import de.cuioss.test.jsf.mocks.ReverseConverter;

@VerifyComponentProperties(of = { "placeholderKey", "placeholderValue", "placeholderConverter" })
class PlaceholderProviderImplTest extends AbstractPartialComponentTest {

    @SuppressWarnings("unused")
    @Test
    void shouldFailWithNullConstructor() {
        assertThrows(NullPointerException.class, () -> new PlaceholderProvider(null));
    }

    @Test
    void shouldResolveNullForNoTitleSet() {
        assertNull(anyComponent().resolveTitle());
    }

    @Test
    void shouldResolvePlaceholderValue() {
        var any = anyComponent();
        any.setPlaceholderValue(MESSAGE_KEY);
        assertEquals(MESSAGE_KEY, any.resolvePlaceholder());
    }

    @Test
    void shouldResolveTitleKey() {
        var any = anyComponent();
        any.setPlaceholderKey(MESSAGE_KEY);
        assertEquals(MESSAGE_KEY, any.resolvePlaceholder());
    }

    @Test
    void shouldUseConverterAsId() {
        getComponentConfigDecorator().registerConverter(ReverseConverter.class);
        var any = anyComponent();
        any.setPlaceholderConverter(ReverseConverter.CONVERTER_ID);
        any.setPlaceholderValue("test");
        assertEquals("tset", any.resolvePlaceholder());
    }

    @Test
    void shouldUseConverterAsConverter() {
        var any = anyComponent();
        any.setPlaceholderConverter(new ReverseConverter());
        any.setPlaceholderValue("test");
        assertEquals("tset", any.resolvePlaceholder());
    }

    /**
     * Tests adding and removing of passthrough attributes emulating an ui:repeat
     * scenario.
     */
    @Test
    void shouldRemoveIfNull() {
        var pt = anyComponent();
        UIComponent other = JsfHtmlComponent.INPUT.component(getFacesContext());
        pt.setPlaceholderValue("test");
        pt.setPlaceholder(other, getFacesContext(), pt);
        assertEquals("test", other.getPassThroughAttributes().get(PlaceholderProvider.PT_PLACEHOLDER));
        pt.setPlaceholderValue(null);
        pt.setPlaceholder(other, getFacesContext(), pt);
        assertNull(other.getPassThroughAttributes().get(PlaceholderProvider.PT_PLACEHOLDER));
    }
}
