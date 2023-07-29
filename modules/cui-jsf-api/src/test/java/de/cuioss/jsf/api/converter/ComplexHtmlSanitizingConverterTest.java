package de.cuioss.jsf.api.converter;

import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;

class ComplexHtmlSanitizingConverterTest extends AbstractConverterTest<ComplexHtmlSanitizingConverter, String> {

    static final String SIMPLE_HTML = "<div><p>Test</p></div>";
    static final String MALICIOS_HTML = "<div><p>Test<script>alert('Hallo');</script></p></div>";

    @Override
    public void populate(final TestItems<String> testItems) {
        testItems.addValidObjectWithStringResult(SIMPLE_HTML, SIMPLE_HTML)
            .addValidStringWithObjectResult(MALICIOS_HTML, SIMPLE_HTML);

    }
}
