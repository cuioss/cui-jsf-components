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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for IconLibrary class")
class IconLibraryTest {

    private static final String CUI_ICON_PREFIX = "cui-icon";

    private static final String CUI_ICON_WARNING = "cui-icon-warning";

    private static final String CUI_ICON_WARNING_CSS = "cui-icon cui-icon-warning";

    private static final String NO_VALID_ICON = "novalid-icon";

    @Nested
    @DisplayName("Tests for icon resolution methods")
    class IconResolutionTests {

        @Test
        @DisplayName("Should resolve library prefix from icon class")
        void shouldResolveLibraryFromIconClass() {
            // Act & Assert
            assertEquals(CUI_ICON_PREFIX, IconLibrary.resolveLibraryFromIconClass(CUI_ICON_WARNING),
                    "Should extract library prefix from icon class");
        }

        @Test
        @DisplayName("Should resolve complete CSS class string from icon name")
        void shouldResolveCssString() {
            // Act & Assert
            assertEquals(CUI_ICON_WARNING_CSS, IconLibrary.resolveCssString(CUI_ICON_WARNING),
                    "Should create complete CSS class string from icon name");
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException for null or invalid icons")
        void shouldThrowExceptionForInvalidIcons() {
            // Act & Assert - null icon
            assertThrows(IllegalArgumentException.class,
                    () -> IconLibrary.resolveCssString(null),
                    "Should throw IllegalArgumentException for null icon");

            // Act & Assert - invalid icon
            assertThrows(IllegalArgumentException.class,
                    () -> IconLibrary.resolveCssString(NO_VALID_ICON),
                    "Should throw IllegalArgumentException for invalid icon");
        }
    }

    @Nested
    @DisplayName("Tests for icon validation methods")
    class IconValidationTests {

        @Test
        @DisplayName("Should throw IllegalArgumentException for null or empty input")
        void shouldThrowExceptionForInvalidInput() {
            // Act & Assert - null input
            assertThrows(IllegalArgumentException.class,
                    () -> IconLibrary.isIconUsagePossible(null),
                    "Should throw IllegalArgumentException for null input");

            // Act & Assert - empty string
            assertThrows(IllegalArgumentException.class,
                    () -> IconLibrary.isIconUsagePossible(""),
                    "Should throw IllegalArgumentException for empty string");
        }

        @Test
        @DisplayName("Should correctly identify valid and invalid icons")
        void shouldIdentifyValidAndInvalidIcons() {
            // Act & Assert - valid icon
            assertTrue(IconLibrary.isIconUsagePossible(CUI_ICON_WARNING),
                    "Should return true for valid icon");

            // Act & Assert - invalid icon
            assertFalse(IconLibrary.isIconUsagePossible(NO_VALID_ICON),
                    "Should return false for invalid icon");
        }
    }

    @Nested
    @DisplayName("Tests for library enum")
    class LibraryEnumTests {

        @Test
        @DisplayName("Should provide correct library prefix")
        void shouldProvideCorrectLibraryPrefix() {
            // Act & Assert
            assertEquals("cui-icon", IconLibrary.CUI.getLibraryPrefix(),
                    "CUI library should have 'cui-icon' prefix");
        }
    }
}
