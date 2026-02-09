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

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.jsf.component.AbstractComponentTest;
import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for ColumnProvider")
class ColumnProviderImplTest extends AbstractComponentTest<MockPartialComponent> {

    public static final String COL_MD_PREFIX = ColumnCssResolver.COL_PREFIX;

    public static final String COL_MD_OFFSET_PREFIX = ColumnCssResolver.COL_OFFSET_PREFIX;

    @Getter
    private MockPartialComponent underTest;

    private final TypedGenerator<Integer> validNumbers = Generators.integers(1, 12);

    private final TypedGenerator<Integer> invalidNumbers = Generators.integers(13, 17);

    @BeforeEach
    void before() {
        underTest = new MockPartialComponent();
    }

    @Nested
    @DisplayName("Basic property tests")
    class BasicPropertyTests {

        @Test
        @DisplayName("Should handle valid size and offset values")
        void shouldHandleValidNumbers() {
            // Arrange
            var valid = validNumbers.next();

            // Act
            underTest.setSize(valid);

            // Assert
            assertEquals(valid, underTest.getSize(), "Size should match the set value");

            // Arrange
            valid = validNumbers.next();

            // Act
            underTest.setOffsetSize(valid);

            // Assert
            assertEquals(valid, underTest.getOffsetSize(), "Offset size should match the set value");
        }
    }

    @Nested
    @DisplayName("CSS resolution tests")
    class CssResolutionTests {

        @Test
        @DisplayName("Should resolve CSS with size only")
        void shouldResolveSize() {
            // Arrange
            final var valid = validNumbers.next();
            underTest.setSize(valid);

            // Act & Assert
            assertEquals(COL_MD_PREFIX + valid, underTest.resolveColumnCss().getStyleClass(),
                    "CSS class should contain only size class");
        }

        @Test
        @DisplayName("Should resolve CSS with size and offset")
        void shouldResolveAndOffsetSize() {
            // Arrange
            final var size = validNumbers.next();
            final var offsetSize = validNumbers.next();
            underTest.setSize(size);
            underTest.setOffsetSize(offsetSize);

            // Act & Assert
            assertEquals(COL_MD_PREFIX + size + " " + COL_MD_OFFSET_PREFIX + offsetSize,
                    underTest.resolveColumnCss().getStyleClass(),
                    "CSS class should contain both size and offset classes");
        }
    }

    @Nested
    @DisplayName("Validation tests")
    class ValidationTests {

        @Test
        @DisplayName("Should throw exception when size is not set")
        void shouldFailOnResolveWithNoSize() {
            // Act & Assert
            assertThrows(NullPointerException.class, () -> underTest.resolveColumnCss(),
                    "Should throw NullPointerException when size is not set");
        }

        @Test
        @DisplayName("Should throw exception for invalid size")
        void shouldFailOnInvalidSize() {
            // Arrange
            underTest.setSize(invalidNumbers.next());

            // Act & Assert
            assertThrows(IllegalArgumentException.class, () -> underTest.resolveColumnCss(),
                    "Should throw IllegalArgumentException for invalid size");
        }

        @Test
        @DisplayName("Should throw exception for invalid offset size")
        void shouldFailOnInvalidOffsetSize() {
            // Arrange
            underTest.setOffsetSize(invalidNumbers.next());

            // Act & Assert
            assertThrows(NullPointerException.class, () -> underTest.resolveColumnCss(),
                    "Should throw NullPointerException when size is not set but offset is invalid");
        }

        @Test
        @DisplayName("Should throw exception for null constructor parameter")
        void shouldFailWithNullConstructor() {
            // Act & Assert
            assertThrows(NullPointerException.class, () -> new ColumnProvider(null),
                    "Should throw NullPointerException for null constructor parameter");
        }
    }
}
