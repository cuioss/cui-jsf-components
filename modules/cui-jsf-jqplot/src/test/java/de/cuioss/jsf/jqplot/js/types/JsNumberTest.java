/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.jqplot.js.types;

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.valueobjects.ValueObjectTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for JsNumber class")
class JsNumberTest extends ValueObjectTest<JsNumber<Number>> {

    @Override
    protected JsNumber<Number> anyValueObject() {
        return JsNumber.create(Generators.integers().next());
    }

    @Nested
    @DisplayName("Integer value handling tests")
    class IntegerValueTests {

        @Test
        @DisplayName("Should correctly format integer values")
        void shouldSupportIntegerValue() {
            // Arrange
            final JsNumber<Integer> target = JsNumber.create(10);
            final JsNumber<Integer> targetEmpty = JsNumber.create(null);

            // Act & Assert
            assertEquals("10", target.getValueAsString(), "Integer should be formatted without decimals");
            assertNull(targetEmpty.getValueAsString(), "Null integer should return null string");
        }
    }

    @Nested
    @DisplayName("Double value handling tests")
    class DoubleValueTests {

        @Test
        @DisplayName("Should correctly format double values with three decimal places")
        void shouldSupportDoubleValue() {
            // Arrange
            final JsNumber<Double> target = JsNumber.create(10.1);
            final JsNumber<Double> targetEmpty = JsNumber.create(null);

            // Act & Assert
            assertEquals("10.100", target.getValueAsString(), "Double should be formatted with three decimal places");
            assertNull(targetEmpty.getValueAsString(), "Null double should return null string");
        }
    }

    @Nested
    @DisplayName("Error handling tests")
    class ErrorHandlingTests {

        @Test
        @DisplayName("Should throw exception for unsupported number types")
        void shouldReactOnUnsupportedNumberType() {
            // Act & Assert
            assertThrows(UnsupportedOperationException.class,
                    () -> JsNumber.create(Byte.MAX_VALUE),
                    "Should throw exception for Byte type");
        }
    }
}
