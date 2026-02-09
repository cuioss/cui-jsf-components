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
package de.cuioss.jsf.api.components.css.impl;

import static de.cuioss.test.generator.Generators.letterStrings;
import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.jsf.api.components.css.CssCommon;
import de.cuioss.test.valueobjects.ValueObjectTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for StyleClassBuilderImpl")
class StyleClassBuilderImplTest extends ValueObjectTest<StyleClassBuilderImpl> {

    private static final String COMBINED_CLASS = "some class";

    private static final String CSS_CLASS = "cssClass";

    @Nested
    @DisplayName("Tests for constructors and empty handling")
    class ConstructorTests {

        @Test
        @DisplayName("Should create empty builder with default constructor")
        void shouldCreateEmptyBuilderWithDefaultConstructor() {
            // Arrange & Act
            var builder = new StyleClassBuilderImpl();

            // Assert
            assertFalse(builder.isAvailable(),
                    "Builder should not have available styles");
            assertTrue(builder.getStyleClass().isEmpty(),
                    "Style class string should be empty");
        }

        @Test
        @DisplayName("Should create empty builder with null or empty string")
        void shouldCreateEmptyBuilderWithNullOrEmptyString() {
            // Arrange & Act - empty string
            var builder = new StyleClassBuilderImpl("");

            // Assert
            assertFalse(builder.isAvailable(),
                    "Builder should not have available styles with empty string");
            assertTrue(builder.getStyleClass().isEmpty(),
                    "Style class string should be empty with empty string");

            // Arrange & Act - null string
            builder = new StyleClassBuilderImpl(null);

            // Assert
            assertFalse(builder.isAvailable(),
                    "Builder should not have available styles with null string");
            assertTrue(builder.getStyleClass().isEmpty(),
                    "Style class string should be empty with null string");
        }

        @Test
        @DisplayName("Should ignore empty elements when appending")
        void shouldIgnoreEmptyElements() {
            // Arrange
            var builder = new StyleClassBuilderImpl();

            // Act
            builder.append("");
            builder.append(new StyleClassBuilderImpl());

            // Assert
            assertFalse(builder.isAvailable(),
                    "Builder should not have available styles after appending empty elements");
            assertTrue(builder.getStyleClass().isEmpty(),
                    "Style class string should be empty after appending empty elements");
        }
    }

    @Nested
    @DisplayName("Tests for CSS string handling")
    class CssStringHandlingTests {

        @Test
        @DisplayName("Should handle CSS string in constructor")
        void shouldHandleCssStringInConstructor() {
            // Arrange & Act
            var builder = new StyleClassBuilderImpl(COMBINED_CLASS);

            // Assert
            assertTrue(builder.isAvailable(),
                    "Builder should have available styles when initialized with CSS string");
            assertEquals(COMBINED_CLASS, builder.getStyleClass(),
                    "Style class should match the provided CSS string");
        }

        @Test
        @DisplayName("Should ignore duplicate CSS classes")
        void shouldIgnoreDuplicateCssClasses() {
            // Arrange
            var builder = new StyleClassBuilderImpl();

            // Act
            builder.append(COMBINED_CLASS).append(COMBINED_CLASS);

            // Assert
            assertTrue(builder.isAvailable(),
                    "Builder should have available styles after appending");
            assertEquals(COMBINED_CLASS, builder.getStyleClass(),
                    "Style class should contain only one instance of the class");
        }

        @Test
        @DisplayName("Should create combined CSS string")
        void shouldCreateCombinedCssString() {
            // Arrange
            var builder = new StyleClassBuilderImpl();

            // Act
            builder.append(CSS_CLASS).append(COMBINED_CLASS);

            // Assert
            assertTrue(builder.isAvailable(),
                    "Builder should have available styles after appending");
            assertEquals(CSS_CLASS + " " + COMBINED_CLASS, builder.getStyleClass(),
                    "Style class should contain both classes with space separator");
        }
    }

    @Nested
    @DisplayName("Tests for style class manipulation")
    class StyleClassManipulationTests {

        @Test
        @DisplayName("Should remove class from style string")
        void shouldRemoveClassFromStyleString() {
            // Arrange
            var builder = new StyleClassBuilderImpl();
            builder.append(CSS_CLASS).append(COMBINED_CLASS);

            // Act
            builder.remove(COMBINED_CLASS);

            // Assert
            assertTrue(builder.isAvailable(),
                    "Builder should still have available styles after removal");
            assertEquals(CSS_CLASS, builder.getStyleClass(),
                    "Style class should contain only the remaining class");
        }

        @Test
        @DisplayName("Should toggle class in style string")
        void shouldToggleClassInStyleString() {
            // Arrange
            var builder = new StyleClassBuilderImpl();
            builder.append(CSS_CLASS).append(COMBINED_CLASS);

            // Act - toggle to remove
            builder.toggle(COMBINED_CLASS);

            // Assert
            assertTrue(builder.isAvailable(),
                    "Builder should still have available styles after toggle");
            assertEquals(CSS_CLASS, builder.getStyleClass(),
                    "Style class should contain only the remaining class");
        }

        @Test
        @DisplayName("Should handle style class provider")
        void shouldHandleStyleClassProvider() {
            // Arrange
            var builder = new StyleClassBuilderImpl();

            // Act - append
            builder.append(CssCommon.DISABLED);

            // Assert - after append
            assertTrue(builder.isAvailable(),
                    "Builder should have available styles after appending");
            assertEquals(CssCommon.DISABLED.getStyleClass(), builder.getStyleClass(),
                    "Style class should match the provider's style class");

            // Act - remove
            builder.remove(CssCommon.DISABLED);

            // Assert - after remove
            assertFalse(builder.isAvailable(),
                    "Builder should not have available styles after removal");

            // Act - toggle on
            builder.toggle(CssCommon.DISABLED);

            // Assert - after toggle on
            assertTrue(builder.isAvailable(),
                    "Builder should have available styles after toggle on");
            assertEquals(CssCommon.DISABLED.getStyleClass(), builder.getStyleClass(),
                    "Style class should match the provider's style class after toggle on");

            // Act - toggle off
            builder.toggle(CssCommon.DISABLED);

            // Assert - after toggle off
            assertFalse(builder.isAvailable(),
                    "Builder should not have available styles after toggle off");
        }

        @Test
        @DisplayName("Should handle style class builder")
        void shouldHandleStyleClassBuilder() {
            // Arrange
            var builder = new StyleClassBuilderImpl();

            // Act - append
            builder.append(CssCommon.DISABLED);

            // Assert - after append
            assertTrue(builder.isAvailable(),
                    "Builder should have available styles after appending");
            assertEquals(CssCommon.DISABLED.getStyleClass(), builder.getStyleClass(),
                    "Style class should match the builder's style class");

            // Act - remove using builder
            builder.remove(CssCommon.DISABLED.getStyleClassBuilder());

            // Assert - after remove
            assertFalse(builder.isAvailable(),
                    "Builder should not have available styles after removal");

            // Act - toggle on using builder
            builder.toggle(CssCommon.DISABLED.getStyleClassBuilder());

            // Assert - after toggle on
            assertTrue(builder.isAvailable(),
                    "Builder should have available styles after toggle on");
            assertEquals(CssCommon.DISABLED.getStyleClass(), builder.getStyleClass(),
                    "Style class should match the builder's style class after toggle on");

            // Act - toggle off using builder
            builder.toggle(CssCommon.DISABLED.getStyleClassBuilder());

            // Assert - after toggle off
            assertFalse(builder.isAvailable(),
                    "Builder should not have available styles after toggle off");
        }

        @Test
        @DisplayName("Should handle empty strings and whitespace")
        void shouldHandleEmptyStringsAndWhitespace() {
            // Arrange
            var builder = new StyleClassBuilderImpl();

            // Act
            builder.append("  ");
            builder.append(CssCommon.DISABLED);

            // Assert
            assertTrue(builder.isAvailable(),
                    "Builder should have available styles after appending valid class");
            assertEquals(CssCommon.DISABLED.getStyleClass(), builder.getStyleClass(),
                    "Style class should only contain the valid class, ignoring whitespace");
        }
    }

    @Override
    protected StyleClassBuilderImpl anyValueObject() {
        return new StyleClassBuilderImpl(letterStrings().next());
    }

    @Nested
    @DisplayName("Tests for conditional append operations")
    class ConditionalAppendTests {

        @Test
        @DisplayName("Should conditionally append style classes based on boolean condition")
        void shouldConditionallyAppendStyleClasses() {
            // Arrange
            var builder = new StyleClassBuilderImpl();

            // Act
            builder.append(CSS_CLASS)
                    .appendIfTrue(CssCommon.DISABLED, true)  // Should append
                    .appendIfTrue(CssCommon.PULL_LEFT, false) // Should not append
                    .appendIfTrue(null, true);  // Should handle null safely

            // Assert
            assertTrue(builder.isAvailable(),
                    "Builder should have available styles after appending");
            assertEquals(CSS_CLASS + " " + CssCommon.DISABLED.getStyleClass(), builder.getStyleClass(),
                    "Style class should contain base class and conditionally appended class");
        }
    }
}
