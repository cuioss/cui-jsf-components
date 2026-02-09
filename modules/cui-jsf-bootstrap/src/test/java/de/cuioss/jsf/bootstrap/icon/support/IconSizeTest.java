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
package de.cuioss.jsf.bootstrap.icon.support;

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.jsf.api.components.css.ContextSize;
import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.generator.junit.EnableGeneratorController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@EnableGeneratorController
@DisplayName("Tests for IconSize enum")
class IconSizeTest {

    private final TypedGenerator<ContextSize> validSizes = Generators.fixedValues(ContextSize.LG, ContextSize.DEFAULT,
            ContextSize.XL, ContextSize.XXL, ContextSize.XXXL);

    @Nested
    @DisplayName("Basic functionality tests")
    class BasicFunctionalityTests {

        @Test
        @DisplayName("Should return valid IconSize for supported ContextSize values")
        void shouldReturnValueForValidSize() {
            // Arrange
            final var contextSize = validSizes.next();

            // Act
            final var iconSize = IconSize.getForContextSize(contextSize);

            // Assert
            assertNotNull(iconSize, "IconSize should not be null for valid ContextSize");
            assertNotNull(iconSize.getStyleClassBuilder(), "StyleClassBuilder should not be null");
            assertNotNull(iconSize.getStyleClass(), "StyleClass should not be null");
        }

        @Test
        @DisplayName("Should return DEFAULT when null is provided")
        void shouldReturnDefaultForNullInput() {
            // Act & Assert
            assertEquals(IconSize.DEFAULT, IconSize.getForContextSize(null),
                    "Should return DEFAULT for null input");
        }

        @Test
        @DisplayName("Should throw exception for unsupported ContextSize values")
        void shouldThrowExceptionForUnsupportedSize() {
            // Act & Assert
            assertThrows(IllegalArgumentException.class,
                    () -> IconSize.getForContextSize(ContextSize.SM),
                    "Should throw IllegalArgumentException for unsupported size");
        }
    }

    @Nested
    @DisplayName("Mapping tests")
    class MappingTests {

        @Test
        @DisplayName("Should correctly map each ContextSize to corresponding IconSize with proper style class")
        void shouldMapContextSizesToIconSizesCorrectly() {
            // Arrange & Act & Assert - DEFAULT
            assertEquals(IconSize.DEFAULT, IconSize.getForContextSize(ContextSize.DEFAULT),
                    "Should map DEFAULT ContextSize to DEFAULT IconSize");
            assertEquals("", IconSize.getForContextSize(ContextSize.DEFAULT).getStyleClass(),
                    "DEFAULT IconSize should have empty style class");

            // Arrange & Act & Assert - LG
            assertEquals(IconSize.LG, IconSize.getForContextSize(ContextSize.LG),
                    "Should map LG ContextSize to LG IconSize");
            assertEquals("cui-icon-lg", IconSize.getForContextSize(ContextSize.LG).getStyleClass(),
                    "LG IconSize should have 'cui-icon-lg' style class");

            // Arrange & Act & Assert - XL
            assertEquals(IconSize.XL, IconSize.getForContextSize(ContextSize.XL),
                    "Should map XL ContextSize to XL IconSize");
            assertEquals("cui-icon-xl", IconSize.getForContextSize(ContextSize.XL).getStyleClass(),
                    "XL IconSize should have 'cui-icon-xl' style class");

            // Arrange & Act & Assert - XXL
            assertEquals(IconSize.XXL, IconSize.getForContextSize(ContextSize.XXL),
                    "Should map XXL ContextSize to XXL IconSize");
            assertEquals("cui-icon-xxl", IconSize.getForContextSize(ContextSize.XXL).getStyleClass(),
                    "XXL IconSize should have 'cui-icon-xxl' style class");

            // Arrange & Act & Assert - XXXL
            assertEquals(IconSize.XXXL, IconSize.getForContextSize(ContextSize.XXXL),
                    "Should map XXXL ContextSize to XXXL IconSize");
            assertEquals("cui-icon-xxxl", IconSize.getForContextSize(ContextSize.XXXL).getStyleClass(),
                    "XXXL IconSize should have 'cui-icon-xxxl' style class");
        }
    }
}
