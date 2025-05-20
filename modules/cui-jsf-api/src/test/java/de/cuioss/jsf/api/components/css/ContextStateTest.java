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
package de.cuioss.jsf.api.components.css;

import static de.cuioss.test.generator.Generators.fixedValues;
import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.generator.junit.EnableGeneratorController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@EnableGeneratorController
@DisplayName("Tests for ContextState enum")
class ContextStateTest {

    private final TypedGenerator<String> states = fixedValues("DEFAULT", "PRIMARY", "SUCCESS", "INFO", "WARNING",
            "DANGER");

    @Nested
    @DisplayName("Tests for getFromString method")
    class GetFromStringTests {

        @Test
        @DisplayName("Should return DEFAULT for null, empty, or whitespace strings")
        void shouldReturnDefaultForNullOrEmpty() {
            // Act & Assert - null input
            assertEquals(ContextState.DEFAULT, ContextState.getFromString(null),
                    "Should return DEFAULT for null input");

            // Act & Assert - empty string
            assertEquals(ContextState.DEFAULT, ContextState.getFromString(""),
                    "Should return DEFAULT for empty string");

            // Act & Assert - whitespace string
            assertEquals(ContextState.DEFAULT, ContextState.getFromString(" "),
                    "Should return DEFAULT for whitespace string");
        }

        @Test
        @DisplayName("Should resolve valid state values")
        void shouldResolveValidStateValues() {
            // Arrange
            String stateValue = states.next();

            // Act & Assert
            assertNotNull(ContextState.getFromString(stateValue),
                    "Should resolve valid state value: " + stateValue);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException for invalid state values")
        void shouldThrowExceptionForInvalidValues() {
            // Act & Assert
            assertThrows(IllegalArgumentException.class,
                    () -> ContextState.getFromString("not.there"),
                    "Should throw IllegalArgumentException for invalid state value");
        }

        @Test
        @DisplayName("Should resolve state values case-insensitively")
        void shouldResolveCaseInsensitively() {
            // Act & Assert - uppercase
            assertEquals(ContextState.PRIMARY, ContextState.getFromString("PRIMARY"),
                    "Should resolve uppercase 'PRIMARY' to PRIMARY state");

            // Act & Assert - lowercase
            assertEquals(ContextState.PRIMARY, ContextState.getFromString("primary"),
                    "Should resolve lowercase 'primary' to PRIMARY state");

            // Act & Assert - mixed case
            assertEquals(ContextState.PRIMARY, ContextState.getFromString("PRImary"),
                    "Should resolve mixed case 'PRImary' to PRIMARY state");
        }

        @Test
        @DisplayName("Should trim whitespace from state values")
        void shouldTrimWhitespace() {
            // Act & Assert - leading whitespace
            assertEquals(ContextState.INFO, ContextState.getFromString(" INFO"),
                    "Should trim leading whitespace from state value");

            // Act & Assert - trailing whitespace
            assertEquals(ContextState.INFO, ContextState.getFromString("INFO   "),
                    "Should trim trailing whitespace from state value");

            // Act & Assert - both leading and trailing whitespace
            assertEquals(ContextState.INFO, ContextState.getFromString(" INFO "),
                    "Should trim both leading and trailing whitespace from state value");
        }
    }

    @Nested
    @DisplayName("Tests for style class methods")
    class StyleClassTests {

        @Test
        @DisplayName("Should correctly handle style class prefixes")
        void shouldHandleStyleClassPrefixes() {
            // Arrange
            final var state = ContextState.SUCCESS;

            // Act & Assert - base style class
            assertEquals("success", state.getStyleClass(),
                    "Base style class should be the lowercase state name");

            // Act & Assert - with prefix
            assertEquals("foo-success", state.getStyleClassWithPrefix("foo"),
                    "Should prepend prefix with hyphen to style class");

            // Act & Assert - with null prefix
            assertEquals("success", state.getStyleClassWithPrefix(null),
                    "Should return base style class when prefix is null");
        }
    }
}
