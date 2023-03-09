package com.icw.ehf.cui.core.api.components.css;

import static java.lang.Boolean.valueOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

class IconLibraryTest {

    private static final String CUI_ICON_PREFIX = "cui-icon";

    private static final String CUI_ICON_WARNING = "cui-icon-warning";

    private static final String CUI_ICON_WARNING_CSS = "cui-icon cui-icon-warning";

    private static final String NO_VALID_ICON = "novalid-icon";

    @Test
    void testResolveLibraryFromIconClass() {
        assertEquals(CUI_ICON_PREFIX, IconLibrary.resolveLibraryFromIconClass(CUI_ICON_WARNING));
    }

    @Test
    void testResolveCssString() {
        assertEquals(CUI_ICON_WARNING_CSS, IconLibrary.resolveCssString(CUI_ICON_WARNING));
    }

    /**
     * Should throw {@link IllegalArgumentException} if invalid icon is used
     */
    @Test
    void shouldFailOnInvalidIcon() {
        try {
            IconLibrary.resolveCssString(null);
            fail("IllegalArgumentException should occurs before");
        } catch (final IllegalArgumentException e) {
            // is expected
        }
        try {
            IconLibrary.resolveCssString(NO_VALID_ICON);
            fail("IllegalArgumentException should occurs before");
        } catch (final IllegalArgumentException e) {
            // is expected
        }
    }

    /**
     * Protect code from wrong usage
     */
    @Test
    void shouldFailOnWrongUsage() {
        try {
            IconLibrary.isIconUsagePossible(null);
            fail("IllegalArgumentException should occurs before");
        } catch (final IllegalArgumentException e) {
            // is expected
        }
        try {
            IconLibrary.isIconUsagePossible("");
            fail("IllegalArgumentException should occurs before");
        } catch (final IllegalArgumentException e) {
            // is expected
        }
    }

    /**
     * Verify check method
     */
    @Test
    void shouldProvideCheckMethod() {
        var result = IconLibrary.isIconUsagePossible(CUI_ICON_WARNING);
        assertTrue(valueOf(result));
        result = IconLibrary.isIconUsagePossible(NO_VALID_ICON);
        assertFalse(valueOf(result));
    }

    @Test
    void shouldProvideLibraryLibraryPrefix() {
        assertEquals("cui-icon", IconLibrary.CUI.getLibraryPrefix());
    }
}
