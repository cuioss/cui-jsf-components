/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.jqplot.portal.theme;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.valueobjects.ValueObjectTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for GridTheme class")
class GridThemeTest extends ValueObjectTest<GridTheme> implements TypedGenerator<GridTheme> {

    private GridTheme underTest;

    @Override
    public GridTheme next() {
        return GridTheme.DEFAULT_GRID_SETTINGS;
    }

    @Override
    public Class<GridTheme> getType() {
        return GridTheme.class;
    }

    @Override
    protected GridTheme anyValueObject() {
        return next();
    }

    @Nested
    @DisplayName("Default settings tests")
    class DefaultSettingsTests {

        @Test
        @DisplayName("Should use default settings when CSS is null")
        void shouldUseDefaultSettingsWhenCssIsNull() {
            // Arrange & Act
            underTest = GridTheme.createBy(null);

            // Assert
            assertEquals(GridTheme.DEFAULT_GRID_SETTINGS, underTest,
                    "Should return default grid settings when CSS is null");
        }
    }

    @Nested
    @DisplayName("CSS parsing tests")
    class CssParsingTests {

        @Test
        @DisplayName("Should parse CSS and extract background color")
        void shouldParseCssAndExtractBackgroundColor() {
            // Arrange
            final var cssText = """
                    someRule{\
                    -jqplot-drawGridlines:false;\
                    -jqplot-backgroundColor:green\
                    }""";

            // Act
            underTest = GridTheme.createBy(from(cssText));

            // Assert - raw background color
            assertEquals("green", underTest.getBackgroundColor(),
                    "Should extract correct background color from CSS");

            // Act - get JavaScript formatted background color
            final var backgroundColorAsJs = underTest.getBackgroundColorAsJs();

            // Assert - JavaScript formatted background color
            assertEquals("\"green\"", backgroundColorAsJs,
                    "Should format background color correctly for JavaScript");

            // Assert - verify lazy loading works
            assertEquals(underTest.getBackgroundColorAsJs(), backgroundColorAsJs,
                    "Should return same instance on subsequent calls (lazy loading)");
        }
    }

    /**
     * Helper method to create a CssRule from a CSS text string.
     * 
     * @param cssText the CSS text to parse
     * @return a CssRule instance
     */
    private static CssRule from(final String cssText) {
        return CssRule.createBy(cssText);
    }
}
