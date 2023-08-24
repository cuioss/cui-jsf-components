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
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.time.temporal.Temporal;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.jqplot.js.types.JsDateTimeFormat;
import de.cuioss.test.valueobjects.junit5.contracts.ShouldHandleObjectContracts;

class TimeLineSeriaTest implements ShouldHandleObjectContracts<TimeLineSeria<?>> {

    @Override
    public TimeLineSeria<Integer> getUnderTest() {
        return new TimeLineSeria<>(JsDateTimeFormat.DATE_ONLY);
    }

    @Test
    void shouldVerifyMissingFormat() {
        assertThrows(NullPointerException.class, () -> new TimeLineSeria<Double>(null));
    }

    @Test
    void shouldVerifyXParameter() {
        final var timeLineSeria = new TimeLineSeria<Double>(JsDateTimeFormat.DATE_ONLY);
        var now = LocalDateTime.now();
        assertThrows(NullPointerException.class, () -> timeLineSeria.add(now, null));
    }

    @Test
    void shouldVerifyYParameter() {
        final var timeLineSeria = new TimeLineSeria<Double>(JsDateTimeFormat.DATE_ONLY);
        assertThrows(NullPointerException.class, () -> timeLineSeria.add(null, 10.0));
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
