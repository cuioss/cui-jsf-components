package de.cuioss.jsf.api.components.partial;

import static de.cuioss.test.generator.Generators.letterStrings;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import de.cuioss.test.jsf.mocks.ReverseConverter;

@VerifyComponentProperties(of = { "closeButtonTitleKey", "closeButtonTitleValue", "closeButtonTitleConverter" })
class CloseButtonTitleProviderImplTest extends AbstractPartialComponentTest {

    @SuppressWarnings("unused")
    @Test
    void shouldFailWithNullConstructor() {
        assertThrows(NullPointerException.class, () -> new CloseButtonTitleProvider(null));
    }

    @Test
    void shouldResolveNullForNoTitleSet() {
        assertNull(anyComponent().resolveCloseButtonTitle());
    }

    @Test
    void shouldresolveCloseButtonTitleValue() {
        final var any = anyComponent();
        any.setCloseButtonTitleValue(MESSAGE_KEY);
        assertEquals(MESSAGE_KEY, any.resolveCloseButtonTitle());
    }

    @Test
    void shouldresolveCloseButtonTitleKey() {
        final var any = anyComponent();
        any.setCloseButtonTitleKey(MESSAGE_KEY);
        assertEquals(MESSAGE_KEY, any.resolveCloseButtonTitle());
    }

    @Test
    void shouldUseConverterAsId() {
        getComponentConfigDecorator().registerConverter(ReverseConverter.class);
        final var any = anyComponent();
        any.setCloseButtonTitleConverter(ReverseConverter.CONVERTER_ID);
        any.setCloseButtonTitleValue("test");
        assertEquals("tset", any.resolveCloseButtonTitle());
    }

    @Test
    void shouldUseConverterAsConverter() {
        final var any = anyComponent();
        any.setCloseButtonTitleConverter(new ReverseConverter());
        any.setCloseButtonTitleValue("test");
        assertEquals("tset", any.resolveCloseButtonTitle());
    }

    @Test
    void shouldDetectTitleAvailabilty() {
        final var any = anyComponent();
        assertFalse(any.isCloseButtonTitleSet());
        final var strings = letterStrings(1, 10);
        any.setCloseButtonTitleKey(strings.next());
        assertTrue(any.isCloseButtonTitleSet());
        any.setCloseButtonTitleValue(strings.next());
        assertTrue(any.isCloseButtonTitleSet());
        any.setCloseButtonTitleKey(null);
        assertTrue(any.isCloseButtonTitleSet());
    }
}
