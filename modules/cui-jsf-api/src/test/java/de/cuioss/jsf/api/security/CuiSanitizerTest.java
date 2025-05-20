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
package de.cuioss.jsf.api.security;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * @author Oliver Wolff
 */
@DisplayName("Tests for CuiSanitizer")
class CuiSanitizerTest {

    private static final String MAIL_ADDRESS = "abc@abc.de";

    public static final String BOLD_STYLE = "style=\"font-weight:bold\"";

    public static final String ELEMENT_CLASS_ATTRIBUTE = "class=\"some-class-name\"";

    public static final String PLAIN_TEXT = "Plain_Text";

    public static final String SIMPLE_HTML = "<div><p>" + PLAIN_TEXT + "</p></div>";

    public static final String COMPLEX_HTML = "<div " + BOLD_STYLE + "><p><span " + BOLD_STYLE + ">" + PLAIN_TEXT
            + "</span></p></div>";

    public static final String COMPLEX_HTML_WITH_TABLE = "<div " + BOLD_STYLE + ">" + "<p>" + "<span " + BOLD_STYLE
            + ">" + PLAIN_TEXT + "</span>" + "</p>" + "<table " + ELEMENT_CLASS_ATTRIBUTE + ">" + "<tbody>" + "<tr>"
            + "<th>Firstname</th>" + "</tr>" + "<tr>" + "<td>Jill</td>" + "</tr>" + "</tbody>" + "</table>" + "</div>";

    public static final String SCRIPT = "<script>alert('malicious')</script>";

    public static final String MALICIOUS_HTML = "<div " + BOLD_STYLE + "><p><span " + BOLD_STYLE + ">" + PLAIN_TEXT
            + "</span></p>" + SCRIPT + "</div>";

    @Nested
    @DisplayName("Plain Text Sanitizer Tests")
    class PlainTextSanitizerTests {

        @Test
        @DisplayName("Should sanitize content to plain text removing all HTML")
        void shouldSanitizeToPlainText() {
            // Arrange - using test constants

            // Act & Assert
            assertEquals(PLAIN_TEXT, CuiSanitizer.PLAIN_TEXT.apply(PLAIN_TEXT), "Plain text should remain unchanged");
            assertEquals(PLAIN_TEXT, CuiSanitizer.PLAIN_TEXT.apply(SIMPLE_HTML), "HTML should be stripped to plain text");
            assertEquals(PLAIN_TEXT, CuiSanitizer.PLAIN_TEXT.apply(COMPLEX_HTML), "Complex HTML should be stripped to plain text");
            assertEquals(PLAIN_TEXT, CuiSanitizer.PLAIN_TEXT.apply(MALICIOUS_HTML), "Malicious HTML should be sanitized to plain text");
        }
    }

    @Nested
    @DisplayName("Simple HTML Sanitizer Tests")
    class SimpleHtmlSanitizerTests {

        @Test
        @DisplayName("Should sanitize content to simple HTML")
        void shouldSanitizeToSimpleHtml() {
            // Arrange - using test constants

            // Act & Assert
            assertEquals(PLAIN_TEXT, CuiSanitizer.SIMPLE_HTML.apply(PLAIN_TEXT), "Plain text should remain unchanged");
            assertEquals(SIMPLE_HTML, CuiSanitizer.SIMPLE_HTML.apply(SIMPLE_HTML), "Simple HTML should remain unchanged");
            assertEquals(SIMPLE_HTML, CuiSanitizer.SIMPLE_HTML.apply(COMPLEX_HTML), "Complex HTML should be sanitized to simple HTML");
            assertEquals(SIMPLE_HTML, CuiSanitizer.SIMPLE_HTML.apply(MALICIOUS_HTML), "Malicious HTML should be sanitized to simple HTML");
        }
    }

    @Nested
    @DisplayName("Complex HTML Sanitizer Tests")
    class ComplexHtmlSanitizerTests {

        @Test
        @DisplayName("Should sanitize content to complex HTML")
        void shouldSanitizeToComplexHtml() {
            // Arrange - using test constants

            // Act & Assert
            assertEquals(PLAIN_TEXT, CuiSanitizer.COMPLEX_HTML.apply(PLAIN_TEXT), "Plain text should remain unchanged");
            assertEquals(SIMPLE_HTML, CuiSanitizer.COMPLEX_HTML.apply(SIMPLE_HTML), "Simple HTML should remain unchanged");
            assertEquals(COMPLEX_HTML, CuiSanitizer.COMPLEX_HTML.apply(COMPLEX_HTML), "Complex HTML should remain unchanged");
            assertEquals(COMPLEX_HTML, CuiSanitizer.COMPLEX_HTML.apply(MALICIOUS_HTML), "Malicious HTML should be sanitized to complex HTML");
        }

        @Test
        @DisplayName("Should handle email addresses correctly")
        void shouldHandleEmailAddresses() {
            // Arrange
            final String mailAddress = MAIL_ADDRESS;

            // Act & Assert
            assertEquals("abc&#64;abc.de", CuiSanitizer.COMPLEX_HTML.apply(mailAddress),
                    "Email addresses should have @ encoded");
        }
    }

    @Nested
    @DisplayName("More Complex HTML Sanitizer Tests")
    class MoreComplexHtmlSanitizerTests {

        @Test
        @DisplayName("Should sanitize content to more complex HTML including tables")
        void shouldSanitizeToMoreComplexHtml() {
            // Arrange - using test constants

            // Act & Assert
            assertEquals(PLAIN_TEXT, CuiSanitizer.MORE_COMPLEX_HTML.apply(PLAIN_TEXT),
                    "Plain text should remain unchanged");
            assertEquals(SIMPLE_HTML, CuiSanitizer.MORE_COMPLEX_HTML.apply(SIMPLE_HTML),
                    "Simple HTML should remain unchanged");
            assertEquals(COMPLEX_HTML, CuiSanitizer.MORE_COMPLEX_HTML.apply(COMPLEX_HTML),
                    "Complex HTML should remain unchanged");
            assertEquals(COMPLEX_HTML, CuiSanitizer.MORE_COMPLEX_HTML.apply(MALICIOUS_HTML),
                    "Malicious HTML should be sanitized to complex HTML");
            assertEquals(COMPLEX_HTML_WITH_TABLE, CuiSanitizer.MORE_COMPLEX_HTML.apply(COMPLEX_HTML_WITH_TABLE),
                    "HTML with tables should be preserved");
        }
    }

    @Nested
    @DisplayName("Passthrough Sanitizer Tests")
    class PassthroughSanitizerTests {

        @Test
        @DisplayName("Should pass through content without sanitization")
        void shouldPassthroughIfConfigured() {
            // Arrange - using test constants

            // Act & Assert
            assertEquals(PLAIN_TEXT, CuiSanitizer.PASSTHROUGH.apply(PLAIN_TEXT),
                    "Plain text should remain unchanged");
            assertEquals(SIMPLE_HTML, CuiSanitizer.PASSTHROUGH.apply(SIMPLE_HTML),
                    "Simple HTML should remain unchanged");
            assertEquals(COMPLEX_HTML, CuiSanitizer.PASSTHROUGH.apply(COMPLEX_HTML),
                    "Complex HTML should remain unchanged");
        }
    }

    @Nested
    @DisplayName("Entity Preservation Tests")
    class EntityPreservationTests {

        @Test
        @DisplayName("Should preserve HTML entities when configured")
        void shouldPreserveHtmlEntities() {
            // Arrange
            final String empty = "";
            final String plainText = "abc";
            final String scriptText = "abc<script>javascript</script>";
            final String entityText = "&lt;script&gt;javascript</script>abc";
            final String complexEntityText = "a&amp;lt;script&amp;amp;gt;javascript</script>bc";

            // Act & Assert
            assertEquals(empty, CuiSanitizer.COMPLEX_HTML_PRESERVE_ENTITIES.apply(empty),
                    "Empty string should remain empty");
            assertEquals(plainText, CuiSanitizer.COMPLEX_HTML_PRESERVE_ENTITIES.apply(plainText),
                    "Plain text should remain unchanged");
            assertEquals(plainText, CuiSanitizer.COMPLEX_HTML_PRESERVE_ENTITIES.apply(scriptText),
                    "Script tags should be removed");
            assertEquals(plainText, CuiSanitizer.COMPLEX_HTML_PRESERVE_ENTITIES.apply(entityText),
                    "Entities should be preserved but script tags removed");
            assertEquals(plainText, CuiSanitizer.COMPLEX_HTML_PRESERVE_ENTITIES.apply(complexEntityText),
                    "Complex entities should be preserved but script tags removed");
        }

        @Test
        @DisplayName("Should handle email entities correctly")
        void shouldHandleEmailEntities() {
            // Arrange
            final String mailAddress = MAIL_ADDRESS;

            // Act & Assert
            assertEquals(mailAddress, CuiSanitizer.COMPLEX_HTML_PRESERVE_ENTITIES.apply(mailAddress),
                    "Email should be preserved with entities");
            assertEquals(mailAddress, CuiSanitizer.PLAIN_TEXT_PRESERVE_ENTITIES.apply(mailAddress),
                    "Email should be preserved with plain text and entities");
        }
    }

    @Nested
    @DisplayName("Edge Case Tests")
    class EdgeCaseTests {

        @Test
        @DisplayName("Should handle null and empty inputs correctly")
        void shouldHandleNullAndEmptyInputs() {
            // Arrange - using all sanitizer values

            // Act & Assert
            for (final CuiSanitizer sanitizer : CuiSanitizer.values()) {
                assertEquals("", sanitizer.apply(null),
                        "null must be converted to empty string, using sanitizer: " + sanitizer);

                assertEquals("", sanitizer.apply(""),
                        "empty string must be returned, using sanitizer: " + sanitizer);

                assertEquals(" ", sanitizer.apply(" "),
                        "whitespaces should not be trimmed, using sanitizer: " + sanitizer);
            }
        }
    }
}
