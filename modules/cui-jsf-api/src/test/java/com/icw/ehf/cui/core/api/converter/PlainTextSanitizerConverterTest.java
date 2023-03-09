package com.icw.ehf.cui.core.api.converter;

import static de.cuioss.test.generator.Generators.letterStrings;

import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;

class PlainTextSanitizerConverterTest
        extends AbstractConverterTest<PlainTextSanitizerConverter, String> {

    @Override
    public void populate(final TestItems<String> testItems) {
        testItems.addRoundtripValues(letterStrings().next());
    }

}
