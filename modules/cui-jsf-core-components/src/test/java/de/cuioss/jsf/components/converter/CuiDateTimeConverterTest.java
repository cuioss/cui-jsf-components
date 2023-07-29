package de.cuioss.jsf.components.converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;

import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;

class CuiDateTimeConverterTest extends AbstractConverterTest<CuiDateTimeConverter, Object> {

    @Override
    public void configure(final CuiDateTimeConverter toBeConfigured) {
        toBeConfigured.setDateStyle("long");
        toBeConfigured.setTimeStyle("long");
        toBeConfigured.setType("both");
    }

    @Override
    public void populate(final TestItems<Object> testItems) {
        Locale.setDefault(Locale.ENGLISH);

        var expected = "February 12, 2018 at 12:00:00 AM GMT";
        testItems.addValidObject(LocalDateTime.now().minusSeconds(10)).addValidObject(LocalDate.now())
                .addValidObjectWithStringResult(LocalDate.of(2018, 2, 12), expected);
    }
}
