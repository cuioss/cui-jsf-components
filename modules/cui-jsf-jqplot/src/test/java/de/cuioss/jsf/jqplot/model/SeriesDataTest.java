/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.jqplot.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.cuioss.jsf.jqplot.js.types.JsDateTimeFormat;
import de.cuioss.jsf.jqplot.js.types.JsInteger;
import de.cuioss.test.valueobjects.junit5.contracts.ShouldHandleObjectContracts;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.Temporal;

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
