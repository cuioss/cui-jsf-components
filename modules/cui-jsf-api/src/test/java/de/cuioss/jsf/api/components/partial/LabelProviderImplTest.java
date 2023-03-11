package de.cuioss.jsf.api.components.partial;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import de.cuioss.test.jsf.mocks.ReverseConverter;

@VerifyComponentProperties(of = { "labelKey", "labelValue", "labelEscape", "labelConverter" })
class LabelProviderImplTest extends AbstractPartialComponentTest {

    @SuppressWarnings("unused")
    @Test
    void shouldFailWithNullConstructor() {
        assertThrows(NullPointerException.class, () -> new LabelProvider(null));
    }

    @Test
    void shouldResolveNullForNoLabelSet() {
        assertNull(anyComponent().resolveLabel());
    }

    @Test
    void shouldResolveLabelValue() {
        var any = anyComponent();
        any.setLabelValue(MESSAGE_KEY);
        assertEquals(MESSAGE_KEY, any.resolveLabel());
    }

    @Test
    void shouldResolveLabelKey() {
        var any = anyComponent();
        any.setLabelKey(MESSAGE_KEY);
        assertEquals(MESSAGE_KEY, any.resolveLabel());
    }

    @Test
    void shouldUseConverterAsId() {
        getComponentConfigDecorator().registerConverter(ReverseConverter.class);
        var any = anyComponent();
        any.setLabelConverter(ReverseConverter.CONVERTER_ID);
        any.setLabelValue("test");
        assertEquals("tset", any.resolveLabel());
    }

    @Test
    void shouldUseConverterAsConverter() {
        var any = anyComponent();
        any.setLabelConverter(new ReverseConverter());
        any.setLabelValue("test");
        assertEquals("tset", any.resolveLabel());
    }

    @Test
    void shouldDefaultToLabelEscape() {
        assertTrue(anyComponent().isLabelEscape());
    }

    @Test
    void shouldResolveLabelEscape() {
        var any = anyComponent();
        any.setLabelEscape(false);
        assertFalse(any.isLabelEscape());
    }
}
