package de.cuioss.jsf.api.converter;

import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;

class StringIdentConverterTest extends AbstractConverterTest<StringIdentConverter, String> {

    @Override
    public void populate(final TestItems<String> testItems) {
        testItems.addRoundtripValues("1", "hallo", "welt");
    }

}
