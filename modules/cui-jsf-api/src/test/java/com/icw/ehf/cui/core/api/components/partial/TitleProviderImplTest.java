package com.icw.ehf.cui.core.api.components.partial;

import static de.cuioss.test.generator.Generators.letterStrings;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import de.cuioss.test.jsf.mocks.ReverseConverter;

@VerifyComponentProperties(of = { "titleKey", "titleValue", "titleConverter" })
class TitleProviderImplTest extends AbstractPartialComponentTest {

    @SuppressWarnings("unused")
    @Test
    void shouldFailWithNullConstructor() {
        assertThrows(NullPointerException.class, () -> {
            new TitleProviderImpl(null);
        });
    }

    @Test
    void shouldResolveNullForNoTitleSet() {
        assertNull(anyComponent().resolveTitle());
    }

    @Test
    void shouldResolveTitleValue() {
        final var any = anyComponent();
        any.setTitleValue(MESSAGE_KEY);
        assertEquals(MESSAGE_KEY, any.resolveTitle());
    }

    @Test
    void shouldResolveTitleKey() {
        final var any = anyComponent();
        any.setTitleKey(MESSAGE_KEY);
        assertEquals(MESSAGE_KEY, any.resolveTitle());
    }

    @Test
    void shouldUseConverterAsId() {
        getComponentConfigDecorator().registerConverter(ReverseConverter.class);
        final var any = anyComponent();
        any.setTitleConverter(ReverseConverter.CONVERTER_ID);
        any.setTitleValue("test");
        assertEquals("tset", any.resolveTitle());
    }

    @Test
    void shouldUseConverterAsConverter() {
        final var any = anyComponent();
        any.setTitleConverter(new ReverseConverter());
        any.setTitleValue("test");
        assertEquals("tset", any.resolveTitle());
    }

    @Test
    void shouldDetectTitleAvailabilty() {
        final var any = anyComponent();
        assertFalse(any.isTitleSet());
        final var strings = letterStrings(1, 10);
        any.setTitleKey(strings.next());
        assertTrue(any.isTitleSet());
        any.setTitleValue(strings.next());
        assertTrue(any.isTitleSet());
        any.setTitleKey(null);
        assertTrue(any.isTitleSet());
    }
}
