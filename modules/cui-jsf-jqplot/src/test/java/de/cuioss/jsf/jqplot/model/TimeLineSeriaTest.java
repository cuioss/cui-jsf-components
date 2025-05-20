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

import de.cuioss.jsf.jqplot.js.types.JsDateTimeFormat;
import de.cuioss.test.valueobjects.junit5.contracts.ShouldHandleObjectContracts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.Temporal;

@DisplayName("Tests for TimeLineSeria class")
class TimeLineSeriaTest implements ShouldHandleObjectContracts<TimeLineSeria<?>> {

    @Override
    public TimeLineSeria<Integer> getUnderTest() {
        return new TimeLineSeria<>(JsDateTimeFormat.DATE_ONLY);
    }

    @Nested
    @DisplayName("Constructor validation tests")
    class ConstructorValidationTests {

        @Test
        @DisplayName("Should throw exception when format is null")
        void shouldRejectNullFormat() {
            // Act & Assert
            assertThrows(NullPointerException.class,
                    () -> new TimeLineSeria<Double>(null),
                    "Constructor should reject null format");
        }
    }

    @Nested
    @DisplayName("Parameter validation tests")
    class ParameterValidationTests {

        @Test
        @DisplayName("Should throw exception when Y value is null")
        void shouldRejectNullYValue() {
            // Arrange
            final var timeLineSeria = new TimeLineSeria<Double>(JsDateTimeFormat.DATE_ONLY);
            var now = LocalDateTime.now();

            // Act & Assert
            assertThrows(NullPointerException.class,
                    () -> timeLineSeria.add(now, null),
                    "Should reject null Y value");
        }

        @Test
        @DisplayName("Should throw exception when X value is null")
        void shouldRejectNullXValue() {
            // Arrange
            final var timeLineSeria = new TimeLineSeria<Double>(JsDateTimeFormat.DATE_ONLY);

            // Act & Assert
            assertThrows(NullPointerException.class,
                    () -> timeLineSeria.add(null, 10.0),
                    "Should reject null X value");
        }
    }

    @Nested
    @DisplayName("Data handling tests")
    class DataHandlingTests {

        @Test
        @DisplayName("Should add valid data points and skip invalid ones")
        void shouldAddValidDataPointsAndSkipInvalid() {
            // Arrange
            final var timeLineSeria = new TimeLineSeria<Integer>(JsDateTimeFormat.DATE_ONLY);
            final Temporal date = LocalDateTime.of(2015, 10, 30, 12, 30);

            // Act
            final var result = timeLineSeria
                    .addIfNotEmpty(null, 10)      // Should skip due to null date
                    .addIfNotEmpty(date, 10)      // Should add
                    .addIfNotEmpty(date, null)    // Should skip due to null value
                    .getAsArray()
                    .asJavaScriptObjectNotation();

            // Assert
            assertEquals("[[\"2015-10-30\",10]]", result,
                    "Should only include valid data points in output");
        }
    }

}
