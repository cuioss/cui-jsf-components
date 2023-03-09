package com.icw.ehf.cui.core.api.converter;

import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;

class HtmlSanitizingConverterTest extends AbstractConverterTest<HtmlSanitizingConverter, String> {

    public static final String PLAIN_TEXT = "Plain_Text";
    public static final String SIMPLE_HTML = "<div><p>" + PLAIN_TEXT + "</p></div>";

    @Override
    public void populate(final TestItems<String> testItems) {
        testItems.addRoundtripValues(PLAIN_TEXT).addValidObjectWithStringResult(SIMPLE_HTML, PLAIN_TEXT);

    }

}
