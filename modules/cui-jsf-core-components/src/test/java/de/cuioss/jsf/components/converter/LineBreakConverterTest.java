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

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.cuioss.jsf.api.security.CuiSanitizer;
import de.cuioss.test.jsf.converter.AbstractSanitizingConverterTest;
import de.cuioss.test.jsf.converter.TestItems;
import org.junit.jupiter.api.Test;

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
    public void populate(final TestItems<String> testItems) {
        testItems.addValidObjectWithStringResult(WINDOWS_SAMPLE, RESULT)
                .addValidObjectWithStringResult(UNIX_SAMPLE, RESULT)
                .addValidObjectWithStringResult(APPLE_SAMPLE, RESULT);
    }

    @Test
    void shouldHandleSanitizer() {
        var converter = new LineBreakConverter();
        assertEquals(CuiSanitizer.PLAIN_TEXT_PRESERVE_ENTITIES, converter.getSanitizer());
        converter.setSanitizingStrategy(null);
        assertEquals(CuiSanitizer.PLAIN_TEXT_PRESERVE_ENTITIES, converter.getSanitizer());
        converter.setSanitizingStrategy(CuiSanitizer.COMPLEX_HTML.name());
        assertEquals(CuiSanitizer.COMPLEX_HTML, converter.getSanitizer());
    }

    @Test
    void shouldHandleDelimiter() {
        var converter = new LineBreakConverter();
        assertEquals(DEFAULT_DELIMITER, converter.getDelimiter());
        converter.setDelimiter(WINODWS_LINE_BREAK);
        assertEquals(WINODWS_LINE_BREAK, converter.getDelimiter());
    }

    @Override
    protected String createTestObjectWithMaliciousContent(String content) {
        return content;
    }
}
