package com.icw.ehf.cui.components.chart.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.temporal.Temporal;

import org.junit.jupiter.api.Test;

import com.icw.ehf.cui.components.js.types.JsDateTimeFormat;
import com.icw.ehf.cui.components.js.types.JsInteger;

import de.cuioss.test.valueobjects.junit5.contracts.ShouldHandleObjectContracts;

class SeriesDataTest implements ShouldHandleObjectContracts<SeriesData> {

    @Override
    public SeriesData getUnderTest() {
        return new SeriesData();
    }

    @Test
    void shouldHandleSeria() {
        final var seria = new Seria<JsInteger, JsInteger>();
        seria.addTupleIfComplete(new SeriaTupelItem<>(new JsInteger(10), new JsInteger(10)));
        final var target = new SeriesData();
        target.addSeriaDataIfNotNull(seria);
        target.addSeriaDataIfNotNull(seria);
        assertEquals("[[[10,10]],[[10,10]]]", target.asJavaScriptObjectNotation());
    }

    @Test
    void verifyBuilder() {
        final Temporal date = LocalDateTime.of(2015, 10, 30, 12, 30);
        final var builder = new SeriesData.Builder();
        // add time line seria with double values
        builder.createTimeLineWithDoubleValues(JsDateTimeFormat.DATE_ONLY).add(date, 10.0).add(date, 20.0);
        // add time line seria with int values
        builder.createTimeLineWithIntegerValues(JsDateTimeFormat.DATE_ONLY).add(date, 5).add(date, 15);
        assertEquals("[[[\"2015-10-30\",10.000],[\"2015-10-30\",20.000]],[[\"2015-10-30\",5],[\"2015-10-30\",15]]]",
                builder.build().asJavaScriptObjectNotation());
    }

}
