package com.icw.ehf.cui.components.chart.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.time.temporal.Temporal;

import org.junit.jupiter.api.Test;

import com.icw.ehf.cui.components.js.types.JsDateTimeFormat;

import de.cuioss.test.valueobjects.junit5.contracts.ShouldHandleObjectContracts;

class TimeLineSeriaTest implements ShouldHandleObjectContracts<TimeLineSeria<?>> {

    @Override
    public TimeLineSeria<Integer> getUnderTest() {
        return new TimeLineSeria<>(JsDateTimeFormat.DATE_ONLY);
    }

    @Test
    void shouldVerifyMissingFormat() {
        assertThrows(NullPointerException.class, () -> {
            new TimeLineSeria<Double>(null);
        });
    }

    @Test
    void shouldVerifyXParameter() {
        final var timeLineSeria = new TimeLineSeria<Double>(JsDateTimeFormat.DATE_ONLY);
        var now = LocalDateTime.now();
        assertThrows(NullPointerException.class, () -> {
            timeLineSeria.add(now, null);
        });
    }

    @Test
    void shouldVerifyYParameter() {
        final var timeLineSeria = new TimeLineSeria<Double>(JsDateTimeFormat.DATE_ONLY);
        assertThrows(NullPointerException.class, () -> {
            timeLineSeria.add(null, 10.0);
        });
    }

    @Test
    void shouldProvidePossibilityToAddTupel() {
        final var timeLineSeria = new TimeLineSeria<Integer>(JsDateTimeFormat.DATE_ONLY);
        final Temporal date = LocalDateTime.of(2015, 10, 30, 12, 30);
        final var result = timeLineSeria.addIfNotEmpty(null, 10).addIfNotEmpty(date, 10).addIfNotEmpty(date, null)
                .getAsArray().asJavaScriptObjectNotation();
        assertEquals("[[\"2015-10-30\",10]]", result);
    }

}
