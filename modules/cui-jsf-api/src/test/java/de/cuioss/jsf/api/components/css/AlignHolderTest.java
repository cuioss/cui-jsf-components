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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for AlignHolder enum")
class AlignHolderTest {

    @Nested
    @DisplayName("Tests for getFromString method")
    class GetFromStringTests {

        @Test
        @DisplayName("Should return DEFAULT for null, empty, or invalid strings")
        void shouldReturnDefaultForInvalidInput() {
            // Act & Assert - null input
            assertEquals(AlignHolder.DEFAULT, AlignHolder.getFromString(null),
                    "Should return DEFAULT for null input");

            // Act & Assert - empty string
            assertEquals(AlignHolder.DEFAULT, AlignHolder.getFromString(""),
                    "Should return DEFAULT for empty string");

            // Act & Assert - invalid string
            assertEquals(AlignHolder.DEFAULT, AlignHolder.getFromString("notThere"),
                    "Should return DEFAULT for invalid alignment value");
        }

        @Test
        @DisplayName("Should resolve RIGHT alignment regardless of case")
        void shouldResolveRightAlignment() {
            // Act & Assert - lowercase
            assertEquals(AlignHolder.RIGHT, AlignHolder.getFromString("right"),
                    "Should resolve lowercase 'right' to RIGHT alignment");

            // Act & Assert - uppercase
            assertEquals(AlignHolder.RIGHT, AlignHolder.getFromString("RIGHT"),
                    "Should resolve uppercase 'RIGHT' to RIGHT alignment");

            // Act & Assert - mixed case
            assertEquals(AlignHolder.RIGHT, AlignHolder.getFromString("RigHt"),
                    "Should resolve mixed case 'RigHt' to RIGHT alignment");
        }

        @Test
        @DisplayName("Should resolve LEFT alignment regardless of case")
        void shouldResolveLeftAlignment() {
            // Act & Assert - lowercase
            assertEquals(AlignHolder.LEFT, AlignHolder.getFromString("left"),
                    "Should resolve lowercase 'left' to LEFT alignment");

            // Act & Assert - uppercase
            assertEquals(AlignHolder.LEFT, AlignHolder.getFromString("LEFT"),
                    "Should resolve uppercase 'LEFT' to LEFT alignment");

            // Act & Assert - mixed case
            assertEquals(AlignHolder.LEFT, AlignHolder.getFromString("lEFt"),
                    "Should resolve mixed case 'lEFt' to LEFT alignment");
        }
    }

    @Nested
    @DisplayName("Tests for style class methods")
    class StyleClassTests {

        @Test
        @DisplayName("Should provide style class information for DEFAULT alignment")
        void shouldProvideStyleClassForDefault() {
            // Act & Assert - style class builder
            assertNotNull(AlignHolder.DEFAULT.getStyleClassBuilder(),
                    "DEFAULT alignment should provide a style class builder");

            // Act & Assert - style class string
            assertNotNull(AlignHolder.DEFAULT.getStyleClass(),
                    "DEFAULT alignment should provide a style class string");
        }
    }
}
