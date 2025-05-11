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

import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.property.PropertyReflectionConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.Temporal;

@PropertyReflectionConfig(skip = true)
@DisplayName("Tests for JsDateTime class")
class JsDateTimeTest extends ValueObjectTest<JsDateTime> {

    @Override
    protected JsDateTime anyValueObject() {
        return JsDateTime.builder().formatter(JsDateTimeFormat.DATE_ONLY).value(LocalDateTime.now()).build();
    }

    @Nested
    @DisplayName("Value handling tests")
    class ValueHandlingTests {

        @Test
        @DisplayName("Should return null when date is null")
        void shouldReturnNullOnEmptyValue() {
            // Arrange
            final var target = JsDateTime.builder().formatter(JsDateTimeFormat.DATE_ONLY).value(null).build();

            // Act & Assert
            assertNull(target.getValueAsString());
        }
    }

    @Nested
    @DisplayName("Formatting tests")
    class FormattingTests {

        @Test
        @DisplayName("Should format date and time according to specified format")
        void shouldFormatValidValue() {
            // Arrange
            final Temporal date = LocalDateTime.of(2010, 10, 20, 12, 30, 59).atOffset(ZoneOffset.ofHours(1));

            // Act
            final var onlyDate = JsDateTime.builder().formatter(JsDateTimeFormat.DATE_ONLY).value(date).build();
            final var dateTime = JsDateTime.builder().formatter(JsDateTimeFormat.DATE_TIME).value(date).build();
            final var dateTimeMiliseconds = JsDateTime.builder().formatter(JsDateTimeFormat.DATE_TIME_WITH_MILISECONDS)
                    .value(date).build();
            final var fullIso = JsDateTime.builder().formatter(JsDateTimeFormat.DATE_TIME_WITH_MILISECONDS_TIMEZONE)
                    .value(date).build();

            // Assert
            assertEquals("\"2010-10-20\"", onlyDate.getValueAsString(), "Should format date only");
            assertEquals("\"2010-10-20 12:30:59\"", dateTime.getValueAsString(), "Should format date and time");
            assertEquals("\"2010-10-20 12:30:59.0\"", dateTimeMiliseconds.getValueAsString(), "Should format date, time and milliseconds");
            // full ISO 8601
            assertEquals("\"2010-10-20T12:30:59+01:00\"", fullIso.getValueAsString(), "Should format as full ISO 8601");
        }
    }
}
