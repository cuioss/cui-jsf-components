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
package de.cuioss.jsf.jqplot.js.types;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Calendar;

import org.junit.jupiter.api.Test;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.valueobjects.ValueObjectTest;

class JsDateTest extends ValueObjectTest<JsDate> {

    @Test
    void shouldReturnNullOnEmptyValue() {
        final var target = new JsDate(null);
        assertNull(target.getValueAsString());
    }

    @Test
    void shouldFormatValidValue() {
        final var cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2010);
        cal.set(Calendar.MONTH, Calendar.OCTOBER);
        cal.set(Calendar.DAY_OF_MONTH, 20);
        cal.set(Calendar.HOUR, 5);
        cal.set(Calendar.MINUTE, 5);
        cal.set(Calendar.SECOND, 5);
        final var date = cal.getTime();
        final var target = new JsDate(date);
        assertEquals("\"2010-10-20\"", target.getValueAsString());
    }

    @Override
    protected JsDate anyValueObject() {
        return new JsDate(Generators.dates().next());
    }
}
