package de.cuioss.jsf.api.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.security.CuiSanitizer;
import de.cuioss.test.jsf.converter.AbstractSanitizingConverterTest;
import de.cuioss.test.jsf.converter.TestItems;

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
