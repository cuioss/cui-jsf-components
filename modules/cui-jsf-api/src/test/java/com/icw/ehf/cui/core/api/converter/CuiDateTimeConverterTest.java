package com.icw.ehf.cui.core.api.converter;

import static de.cuioss.tools.collect.CollectionLiterals.immutableSet;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Set;

import org.junit.jupiter.api.condition.JRE;

import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;

class CuiDateTimeConverterTest
        extends AbstractConverterTest<CuiDateTimeConverter, Object> {

    @Override
    public void configure(final CuiDateTimeConverter toBeConfigured) {
        toBeConfigured.setDateStyle("long");
        toBeConfigured.setTimeStyle("long");
        toBeConfigured.setType("both");
    }

    @Override
    public void populate(final TestItems<Object> testItems) {
        Locale.setDefault(Locale.ENGLISH);
        var expected = "February 12, 2018 12:00:00 AM GMT"; // until JDK 8
        if (isAtLeastJava11()) {
            expected = "February 12, 2018 at 12:00:00 AM GMT";
        }
        testItems
                .addValidObject(LocalDateTime.now().minusSeconds(10))
                .addValidObject(LocalDate.now())
                .addValidObjectWithStringResult(
                        LocalDate.of(2018, 2, 12),
                        expected);
    }

    /**
     * @return
     */
    private boolean isAtLeastJava11() {
        Set<JRE> elevenPlus = immutableSet(JRE.JAVA_11, JRE.JAVA_12, JRE.JAVA_13, JRE.JAVA_14, JRE.JAVA_15);
        for (JRE jre : elevenPlus) {
            if (jre.isCurrentVersion()) {
                return true;
            }
        }
        return false;
    }
}
