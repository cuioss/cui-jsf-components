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
package de.cuioss.jsf.jqplot.axes;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.cuioss.test.valueobjects.contract.SerializableContractImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for Axes class")
class AxesTest {

    private Axes target;

    @Nested
    @DisplayName("Serialization tests")
    class SerializationTests {

        @Test
        @DisplayName("Should be serializable")
        void shouldBeSerializable() {
            // Arrange & Act & Assert
            assertDoesNotThrow(() -> SerializableContractImpl.serializeAndDeserialize(createAny()));
        }
    }

    @Nested
    @DisplayName("Validation tests")
    class ValidationTests {

        @Test
        @DisplayName("Should verify duplicates")
        void shouldVerifyDuplicates() {
            // Arrange
            target = createEmpty();
            target.addInNotNull(Axis.createXAxis());
            var axis = Axis.createXAxis();

            // Act & Assert
            assertThrows(IllegalArgumentException.class, () -> target.addInNotNull(axis));
        }
    }

    private static Axes createEmpty() {
        return new Axes();
    }

    private static Axes createAny() {
        final var result = new Axes();
        result.addInNotNull(null);
        result.addInNotNull(Axis.createXAxis());
        result.addInNotNull(Axis.createYAxis());
        return result;
    }
}
