package com.icw.ehf.cui.core.api.converter;

import java.util.Locale;

import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;

class LocaleConverterTest extends AbstractConverterTest<LocaleConverter, Locale> {

    @Override
    public void populate(final TestItems<Locale> testItems) {
        testItems.addRoundtripValues("en");
    }

}
