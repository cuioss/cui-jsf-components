package com.icw.ehf.cui.core.api.converter;

import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;

class DecodingStringConverterTest extends AbstractConverterTest<DecodingStringConverter, String> {

    @Override
    public void populate(final TestItems<String> testItems) {
        testItems.addRoundtripValues("someValue", "%25", "%40").addValidObjectWithStringResult("%", "%25")
                .addValidObjectWithStringResult("@", "%40");

    }

}
