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
package de.cuioss.jsf.api.components.css;

import static de.cuioss.test.generator.Generators.fixedValues;
import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.generator.junit.EnableGeneratorController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@EnableGeneratorController
@DisplayName("Tests for ContextSize enum")
class ContextSizeTest {

    private final TypedGenerator<String> sizes = fixedValues("XS", "SM", "MD", "LG", "XL", "XXL", "XXXL", "xs", "sm");

    @Nested
    @DisplayName("Tests for getFromString method")
    class GetFromStringTests {

        @Test
        @DisplayName("Should return DEFAULT for null, empty, or whitespace strings")
        void shouldReturnDefaultForNullOrEmpty() {
            // Act & Assert - null input
            assertEquals(ContextSize.DEFAULT, ContextSize.getFromString(null),
                    "Should return DEFAULT for null input");

            // Act & Assert - empty string
            assertEquals(ContextSize.DEFAULT, ContextSize.getFromString(""),
                    "Should return DEFAULT for empty string");

            // Act & Assert - whitespace string
            assertEquals(ContextSize.DEFAULT, ContextSize.getFromString(" "),
                    "Should return DEFAULT for whitespace string");
        }

        @Test
        @DisplayName("Should resolve valid size values")
        void shouldResolveValidSizeValues() {
            // Arrange
            String sizeValue = sizes.next();

            // Act & Assert
            assertNotNull(ContextSize.getFromString(sizeValue),
                    "Should resolve valid size value: " + sizeValue);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException for invalid size values")
        void shouldThrowExceptionForInvalidValues() {
            // Act & Assert
            assertThrows(IllegalArgumentException.class,
                    () -> ContextSize.getFromString("not.there"),
                    "Should throw IllegalArgumentException for invalid size value");
        }

        @Test
        @DisplayName("Should resolve size values case-insensitively")
        void shouldResolveCaseInsensitively() {
            // Act & Assert - lowercase
            assertEquals(ContextSize.MD, ContextSize.getFromString("md"),
                    "Should resolve lowercase 'md' to MD size");

            // Act & Assert - uppercase
            assertEquals(ContextSize.MD, ContextSize.getFromString("MD"),
                    "Should resolve uppercase 'MD' to MD size");

            // Act & Assert - mixed case
            assertEquals(ContextSize.MD, ContextSize.getFromString("Md"),
                    "Should resolve mixed case 'Md' to MD size");
        }

        @Test
        @DisplayName("Should trim whitespace from size values")
        void shouldTrimWhitespace() {
            // Act & Assert - leading whitespace
            assertEquals(ContextSize.XL, ContextSize.getFromString(" XL"),
                    "Should trim leading whitespace from size value");

            // Act & Assert - trailing whitespace
            assertEquals(ContextSize.XL, ContextSize.getFromString("XL   "),
                    "Should trim trailing whitespace from size value");

            // Act & Assert - both leading and trailing whitespace
            assertEquals(ContextSize.XL, ContextSize.getFromString(" xL "),
                    "Should trim both leading and trailing whitespace from size value");
        }
    }
}
