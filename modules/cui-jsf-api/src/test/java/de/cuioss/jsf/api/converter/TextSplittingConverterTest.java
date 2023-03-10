package de.cuioss.jsf.api.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;

class TextSplittingConverterTest extends AbstractConverterTest<TextSplittingConverter, String> {

    private static final String LONG_TEXT = "123456789abcdef";

    @Override
    public void populate(final TestItems<String> testItems) {
        testItems.addValidObjectWithStringResult(LONG_TEXT, LONG_TEXT);
    }

    @Test
    void shouldAbridgeText() {
        var converter = new TextSplittingConverter();
        converter.setAbridgedLengthCount(10);
        var converted = converter.getAsString(getFacesContext(), getComponent(), LONG_TEXT);
        assertEquals("123456 ...", converted);
    }
}
