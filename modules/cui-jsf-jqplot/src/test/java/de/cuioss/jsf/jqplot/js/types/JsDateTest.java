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

import de.cuioss.test.generator.Generators;
import de.cuioss.test.valueobjects.ValueObjectTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

@DisplayName("Tests for JsDate class")
class JsDateTest extends ValueObjectTest<JsDate> {

    @Nested
    @DisplayName("Value handling tests")
    class ValueHandlingTests {

        @Test
        @DisplayName("Should return null when date is null")
        void shouldReturnNullOnEmptyValue() {
            // Arrange
            final var target = new JsDate(null);

            // Act & Assert
            assertNull(target.getValueAsString());
        }

        @Test
        @DisplayName("Should format date in YYYY-MM-DD format")
        void shouldFormatValidValue() {
            // Arrange
            final var cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, 2010);
            cal.set(Calendar.MONTH, Calendar.OCTOBER);
            cal.set(Calendar.DAY_OF_MONTH, 20);
            cal.set(Calendar.HOUR, 5);
            cal.set(Calendar.MINUTE, 5);
            cal.set(Calendar.SECOND, 5);
            final var date = cal.getTime();

            // Act
            final var target = new JsDate(date);

            // Assert
            assertEquals("\"2010-10-20\"", target.getValueAsString());
        }
    }

    @Override
    protected JsDate anyValueObject() {
        return new JsDate(Generators.dates().next());
    }
}
