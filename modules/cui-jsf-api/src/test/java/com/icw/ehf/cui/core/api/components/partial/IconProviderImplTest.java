package com.icw.ehf.cui.core.api.components.partial;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import de.cuioss.test.jsf.config.component.VerifyComponentProperties;

@VerifyComponentProperties(of = "icon")
class IconProviderImplTest extends AbstractPartialComponentTest {

    @SuppressWarnings("unused")
    @Test
    void shouldFailWithNullConstructor() {
        assertThrows(NullPointerException.class, () -> {
            new IconProvider(null);
        });
    }

    @Test
    void shouldFallbackOnNoIconSet() {
        assertEquals(IconProvider.FALLBACK_ICON_STRING, anyComponent().resolveIconCss());
    }

    @Test
    void shouldFallbackOnInvalidIconSet() {
        var any = anyComponent();
        any.setIcon(MESSAGE_KEY);
        assertEquals(IconProvider.FALLBACK_ICON_STRING, any.resolveIconCss());
    }

    @Test
    void shouldResolveIcon() {
        var any = anyComponent();
        any.setIcon("cui-icon-alarm");
        assertEquals("cui-icon cui-icon-alarm", any.resolveIconCss());
    }

    @Test
    void shouldDetectWhetherIconIsSet() {
        var any = anyComponent();
        assertFalse(any.isIconSet());
        any.setIcon("cui-icon-alarm");
        assertTrue(any.isIconSet());
    }
}
