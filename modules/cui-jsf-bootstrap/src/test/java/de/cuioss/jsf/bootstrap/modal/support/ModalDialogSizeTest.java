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
package de.cuioss.jsf.bootstrap.modal.support;

import static de.cuioss.tools.string.MoreStrings.isEmpty;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for ModalDialogSize")
class ModalDialogSizeTest {

    @Nested
    @DisplayName("Tests for string to enum mapping")
    class StringMappingTests {

        @Test
        @DisplayName("Should map string values to correct enum values")
        void shouldMapStringValuesToEnumValues() {
            // Act & Assert
            assertEquals(ModalDialogSize.DEFAULT, ModalDialogSize.getFromString(""),
                    "Empty string should map to DEFAULT");
            assertEquals(ModalDialogSize.LG, ModalDialogSize.getFromString(ModalDialogSize.LG.name()),
                    "LG string should map to LG enum");
            assertEquals(ModalDialogSize.FLUID, ModalDialogSize.getFromString(ModalDialogSize.FLUID.name()),
                    "FLUID string should map to FLUID enum");
            assertEquals(ModalDialogSize.SM, ModalDialogSize.getFromString(ModalDialogSize.SM.name()),
                    "SM string should map to SM enum");
        }

        @Test
        @DisplayName("Should throw exception for invalid string value")
        void shouldThrowExceptionForInvalidString() {
            // Act & Assert
            assertThrows(IllegalArgumentException.class,
                    () -> ModalDialogSize.getFromString("not-there"),
                    "Should throw IllegalArgumentException for invalid string");
        }
    }

    @Nested
    @DisplayName("Tests for style class resolution")
    class StyleClassTests {

        @Test
        @DisplayName("Should resolve correct style class for each enum value")
        void shouldResolveCorrectStyleClass() {
            // Act & Assert
            assertTrue(isEmpty(ModalDialogSize.DEFAULT.getStyleClass()),
                    "DEFAULT should have empty style class");

            assertEquals(ModalDialogSize.PREFIX + ModalDialogSize.FLUID.name().toLowerCase(),
                    ModalDialogSize.FLUID.getStyleClass(),
                    "FLUID should have correct style class");

            assertEquals(ModalDialogSize.PREFIX + ModalDialogSize.SM.name().toLowerCase(),
                    ModalDialogSize.SM.getStyleClass(),
                    "SM should have correct style class");

            assertEquals(ModalDialogSize.PREFIX + ModalDialogSize.LG.name().toLowerCase(),
                    ModalDialogSize.LG.getStyleClass(),
                    "LG should have correct style class");
        }
    }
}
