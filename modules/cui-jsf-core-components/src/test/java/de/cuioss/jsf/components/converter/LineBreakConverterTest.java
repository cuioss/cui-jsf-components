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
package de.cuioss.jsf.components.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.cuioss.jsf.api.security.CuiSanitizer;
import de.cuioss.test.jsf.converter.AbstractSanitizingConverterTest;
import de.cuioss.test.jsf.converter.TestItems;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for LineBreakConverter")
class LineBreakConverterTest extends AbstractSanitizingConverterTest<LineBreakConverter, String> {

    private static final String RESULT = "this is<br>a new line";

    private static final String WINODWS_LINE_BREAK = "\r\n";

    private static final String UNIX_LINE_BREAK = "\n";

    private static final String APPLE_LINE_BREAK = "\r";

    private static final String WINDOWS_SAMPLE = "this is" + WINODWS_LINE_BREAK + "a new line";

    private static final String UNIX_SAMPLE = "this is" + UNIX_LINE_BREAK + "a new line";

    private static final String APPLE_SAMPLE = "this is" + APPLE_LINE_BREAK + "a new line";

    private static final String DEFAULT_DELIMITER = "<br>";

    @Override
    @DisplayName("Configure test items for line break conversion")
    public void populate(final TestItems<String> testItems) {
        testItems.addValidObjectWithStringResult(WINDOWS_SAMPLE, RESULT)
                .addValidObjectWithStringResult(UNIX_SAMPLE, RESULT)
                .addValidObjectWithStringResult(APPLE_SAMPLE, RESULT);
    }

    @Test
    @DisplayName("Should handle sanitizer configuration correctly")
    void shouldHandleSanitizer() {
        // Arrange
        var converter = new LineBreakConverter();

        // Act & Assert - Default sanitizer
        assertEquals(CuiSanitizer.PLAIN_TEXT_PRESERVE_ENTITIES, converter.getSanitizer(),
                "Default sanitizer should be PLAIN_TEXT_PRESERVE_ENTITIES");

        // Act & Assert - Null sanitizer strategy
        converter.setSanitizingStrategy(null);
        assertEquals(CuiSanitizer.PLAIN_TEXT_PRESERVE_ENTITIES, converter.getSanitizer(),
                "Sanitizer should remain default when setting null strategy");

        // Act & Assert - Custom sanitizer strategy
        converter.setSanitizingStrategy(CuiSanitizer.COMPLEX_HTML.name());
        assertEquals(CuiSanitizer.COMPLEX_HTML, converter.getSanitizer(),
                "Sanitizer should be updated to COMPLEX_HTML");
    }

    @Test
    @DisplayName("Should handle delimiter configuration correctly")
    void shouldHandleDelimiter() {
        // Arrange
        var converter = new LineBreakConverter();

        // Act & Assert - Default delimiter
        assertEquals(DEFAULT_DELIMITER, converter.getDelimiter(),
                "Default delimiter should be <br>");

        // Act & Assert - Custom delimiter
        converter.setDelimiter(WINODWS_LINE_BREAK);
        assertEquals(WINODWS_LINE_BREAK, converter.getDelimiter(),
                "Delimiter should be updated to Windows line break");
    }

    @Override
    @DisplayName("Create test object with malicious content")
    protected String createTestObjectWithMaliciousContent(String content) {
        return content;
    }
}
