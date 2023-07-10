package de.cuioss.jsf.api.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.security.CuiSanitizer;
import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;

class HtmlSanitizingConverterTest extends AbstractConverterTest<HtmlSanitizingConverter, String> {

    public static final String PLAIN_TEXT = "Plain_Text";
    public static final String SIMPLE_HTML = "<div><p>" + PLAIN_TEXT + "</p></div>";

    @Override
    public void populate(final TestItems<String> testItems) {
        testItems.addRoundtripValues(PLAIN_TEXT).addValidObjectWithStringResult(SIMPLE_HTML, PLAIN_TEXT);
    }

    @Test
    void shouldBeConfigurable() {
        var converter = getConverter();
        converter.setStrategy(CuiSanitizer.PASSTHROUGH.name());
        assertEquals(SIMPLE_HTML, converter.getAsString(getFacesContext(), getComponent(), SIMPLE_HTML));
    }

    @Test
    void shouldIgnoreEmptyConfig() {
        var converter = getConverter();
        converter.setStrategy("");
        assertEquals(PLAIN_TEXT, converter.getAsString(getFacesContext(), getComponent(), SIMPLE_HTML));
    }

    @Test
    void shouldHandleFullSanitation() {
        var converter = getConverter();
        assertEquals("", converter.getAsString(getFacesContext(), getComponent(), "<p></p>"));
    }

}
