/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.api.components.css;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class IconLibraryTest {

    private static final String CUI_ICON_PREFIX = "cui-icon";

    private static final String CUI_ICON_WARNING = "cui-icon-warning";

    private static final String CUI_ICON_WARNING_CSS = "cui-icon cui-icon-warning";

    private static final String NO_VALID_ICON = "novalid-icon";

    @Test
    void resolveLibraryFromIconClass() {
        assertEquals(CUI_ICON_PREFIX, IconLibrary.resolveLibraryFromIconClass(CUI_ICON_WARNING));
    }

    @Test
    void resolveCssString() {
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
        assertTrue(result);
        result = IconLibrary.isIconUsagePossible(NO_VALID_ICON);
        assertFalse(result);
    }

    @Test
    void shouldProvideLibraryLibraryPrefix() {
        assertEquals("cui-icon", IconLibrary.CUI.getLibraryPrefix());
    }
}
