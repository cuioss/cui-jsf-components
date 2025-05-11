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
package de.cuioss.jsf.components.converter;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("Tests for JQueryDateTimePatternConverter")
class JQueryDateTimePatternConverterTest {

    @ParameterizedTest
    @CsvSource({
            "yyyy-MM-dd T HH:mm:ss, yyyy-MM-dd'T'HH:mm:ss.SSS ZZ",
            "yyyy-MM-dd T HH:mm:ss, yyyy-MM-dd'T'HH:mm:ss.SSS '(UTC'ZZ')'",
            "hh  o  clock  a, hh 'o''clock' a zzzz"
    })
    @DisplayName("Should convert Java date patterns to jQuery compatible patterns")
    void shouldSupportJodaDateTimeFormats(String expected, String patternString) {
        // Arrange
        final var converter = new JQueryDateTimePatternConverter(patternString);

        // Act
        final var pattern = converter.getJQueryDateConformTimePattern();

        // Assert
        assertEquals(expected, pattern, "Pattern should be converted correctly");
    }

    @Test
    @DisplayName("Should handle null input pattern")
    void shouldHandleNullPattern() {
        // Arrange
        final var converter = new JQueryDateTimePatternConverter(null);

        // Act
        final var result = converter.getJQueryDateConformTimePattern();

        // Assert
        assertNull(result, "Should return null for null input pattern");
    }

    @Test
    @DisplayName("Should cache converted pattern")
    void shouldCacheConvertedPattern() {
        // Arrange
        final var converter = new JQueryDateTimePatternConverter("yyyy-MM-dd");

        // Act
        final var firstResult = converter.getJQueryDateConformTimePattern();
        final var secondResult = converter.getJQueryDateConformTimePattern();

        // Assert
        assertSame(firstResult, secondResult, "Should return the same instance on subsequent calls");
    }

    @Test
    @DisplayName("Should filter out unsupported characters")
    void shouldFilterUnsupportedCharacters() {
        // Arrange
        final var converter = new JQueryDateTimePatternConverter("yyyy-MM-dd ZZ SSS (UTC) zzz");

        // Act
        final var result = converter.getJQueryDateConformTimePattern();

        // Assert
        assertEquals("yyyy-MM-dd", result, "Should filter out unsupported characters");
    }
}
