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
package de.cuioss.jsf.bootstrap.common.partial;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for ColumnCssResolver")
class ColumnCssResolverTest {

    @Nested
    @DisplayName("Basic functionality tests")
    class BasicFunctionalityTests {

        @Test
        @DisplayName("Should resolve column CSS with size and offset")
        void shouldResolveColumnCssWithSizeAndOffset() {
            // Arrange
            var resolver = new ColumnCssResolver(1, 3, true, null);

            // Act & Assert
            assertEquals("col-md-1 col-md-offset-3", resolver.resolveColumnCss().getStyleClass(),
                    "Should generate correct column classes with size and offset");
        }

        @Test
        @DisplayName("Should resolve column CSS with size only")
        void shouldResolveColumnCssWithSizeOnly() {
            // Arrange
            var resolver = new ColumnCssResolver(4, null, true, null);

            // Act & Assert
            assertEquals("col-md-4", resolver.resolveColumnCss().getStyleClass(),
                    "Should generate correct column class with size only");
        }

        @Test
        @DisplayName("Should include custom style class")
        void shouldIncludeCustomStyleClass() {
            // Arrange
            var resolver = new ColumnCssResolver(6, null, true, "custom-class");

            // Act & Assert
            assertEquals("col-md-6 custom-class", resolver.resolveColumnCss().getStyleClass(),
                    "Should include custom style class");
        }

        @Test
        @DisplayName("Should not render column classes when renderAsColumn is false")
        void shouldNotRenderColumnClasses() {
            // Arrange
            var resolver = new ColumnCssResolver(8, 2, false, "only-this-class");

            // Act & Assert
            assertEquals("only-this-class", resolver.resolveColumnCss().getStyleClass(),
                    "Should only include custom class when renderAsColumn is false");
        }
    }

    @Nested
    @DisplayName("Validation tests")
    class ValidationTests {

        @Test
        @DisplayName("Should throw exception for null size")
        void shouldThrowExceptionForNullSize() {
            // Arrange
            var resolver = new ColumnCssResolver(null, null, true, null);

            // Act & Assert
            assertThrows(NullPointerException.class, resolver::resolveColumnCss,
                    "Should throw NullPointerException for null size");
        }

        @Test
        @DisplayName("Should throw exception for size less than 1")
        void shouldThrowExceptionForSizeLessThan1() {
            // Arrange
            var resolver = new ColumnCssResolver(0, null, true, null);

            // Act & Assert
            assertThrows(IllegalArgumentException.class, resolver::resolveColumnCss,
                    "Should throw IllegalArgumentException for size less than 1");
        }

        @Test
        @DisplayName("Should throw exception for size greater than 12")
        void shouldThrowExceptionForSizeGreaterThan12() {
            // Arrange
            var resolver = new ColumnCssResolver(13, null, true, null);

            // Act & Assert
            assertThrows(IllegalArgumentException.class, resolver::resolveColumnCss,
                    "Should throw IllegalArgumentException for size greater than 12");
        }

        @Test
        @DisplayName("Should throw exception for offset size less than 1")
        void shouldThrowExceptionForOffsetSizeLessThan1() {
            // Arrange
            var resolver = new ColumnCssResolver(6, 0, true, null);

            // Act & Assert
            assertThrows(IllegalArgumentException.class, resolver::resolveColumnCss,
                    "Should throw IllegalArgumentException for offset size less than 1");
        }

        @Test
        @DisplayName("Should throw exception for offset size greater than 12")
        void shouldThrowExceptionForOffsetSizeGreaterThan12() {
            // Arrange
            var resolver = new ColumnCssResolver(6, 13, true, null);

            // Act & Assert
            assertThrows(IllegalArgumentException.class, resolver::resolveColumnCss,
                    "Should throw IllegalArgumentException for offset size greater than 12");
        }
    }
}
