package de.cuioss.jsf.api.converter;

import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;

class ObjectToStringConverterTest
        extends AbstractConverterTest<ObjectToStringConverter, Object> {

    @Override
    public void populate(final TestItems<Object> testItems) {
        testItems.addValidObjectWithStringResult(String.valueOf("1"), "1")
                .addValidObjectWithStringResult(2, "2");

    }

}
