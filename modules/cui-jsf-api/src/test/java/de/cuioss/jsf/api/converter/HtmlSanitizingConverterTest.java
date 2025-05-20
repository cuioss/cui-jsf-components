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
package de.cuioss.jsf.api.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.cuioss.jsf.api.security.CuiSanitizer;
import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;
import jakarta.faces.context.FacesContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for HtmlSanitizingConverter")
class HtmlSanitizingConverterTest extends AbstractConverterTest<HtmlSanitizingConverter, String> {

    // Plain text content without HTML
    public static final String PLAIN_TEXT = "Plain_Text";

    // HTML content containing the plain text
    public static final String SIMPLE_HTML = "<div><p>" + PLAIN_TEXT + "</p></div>";

    @Override
    @DisplayName("Configure test cases for HTML sanitization")
    public void populate(final TestItems<String> testItems) {
        // Test that plain text passes through unchanged
        testItems.addRoundtripValues(PLAIN_TEXT)
                // Test that HTML is sanitized to plain text
                .addValidObjectWithStringResult(SIMPLE_HTML, PLAIN_TEXT);
    }

    @Nested
    @DisplayName("Tests for sanitization strategy configuration")
    class SanitizationStrategyTests {

        @Test
        @DisplayName("Should allow configuring different sanitization strategies")
        void shouldBeConfigurable(FacesContext facesContext) {
            // Arrange
            var converter = getConverter();

            // Act - set passthrough strategy to allow all HTML
            converter.setStrategy(CuiSanitizer.PASSTHROUGH.name());

            // Assert
            assertEquals(SIMPLE_HTML, converter.getAsString(facesContext, getComponent(), SIMPLE_HTML),
                    "With PASSTHROUGH strategy, HTML should remain unchanged");
        }

        @Test
        @DisplayName("Should ignore empty strategy configuration")
        void shouldIgnoreEmptyConfig(FacesContext facesContext) {
            // Arrange
            var converter = getConverter();

            // Act - set empty strategy (should use default)
            converter.setStrategy("");

            // Assert
            assertEquals(PLAIN_TEXT, converter.getAsString(facesContext, getComponent(), SIMPLE_HTML),
                    "With empty strategy, default sanitization should be applied");
        }
    }

    @Nested
    @DisplayName("Tests for special sanitization cases")
    class SpecialSanitizationTests {

        @Test
        @DisplayName("Should completely sanitize certain HTML elements")
        void shouldHandleFullSanitation(FacesContext facesContext) {
            // Arrange
            var converter = getConverter();

            // Act & Assert
            assertEquals("", converter.getAsString(facesContext, getComponent(), "<p></p>"),
                    "Empty paragraph tags should be completely sanitized");
        }
    }
}
