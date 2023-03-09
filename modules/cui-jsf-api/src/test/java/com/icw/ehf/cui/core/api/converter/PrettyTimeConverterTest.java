package com.icw.ehf.cui.core.api.converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;

@JsfTestConfiguration(LocaleProducerMock.class)
class PrettyTimeConverterTest extends AbstractConverterTest<PrettyTimeConverter, Object> {

    private static final String MOMENTS_AGO = "moments ago";

    @Override
    public void populate(final TestItems<Object> testItems) {
        testItems
                .addValidObjectWithStringResult(new Date(System.currentTimeMillis() - 10000),
                        MOMENTS_AGO)
                .addInvalidObject(2)
                .addValidObjectWithStringResult(Calendar.getInstance(), MOMENTS_AGO)
                .addValidObject(LocalDate.now()).addValidObject(ZonedDateTime.now())
                .addValidObjectWithStringResult(LocalDateTime.now().minusSeconds(10), MOMENTS_AGO);
    }

}
