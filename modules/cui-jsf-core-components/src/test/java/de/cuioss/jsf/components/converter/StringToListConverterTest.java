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
package de.cuioss.jsf.components.converter;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;
import jakarta.faces.context.FacesContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.SortedSet;

@DisplayName("Tests for StringToListConverter")
class StringToListConverterTest extends AbstractConverterTest<StringToListConverter, String> {

    private static final List<String> MINIMAL_LIST = immutableList("a", "b");

    @Override
    @DisplayName("Configure test items")
    public void populate(final TestItems<String> testItems) {
        testItems.addRoundtripValues("a;b;c", "a");
    }

    @Nested
    @DisplayName("Separator configuration tests")
    class SeparatorTests {

        @Test
        @DisplayName("Should handle custom separators for string conversion")
        void shouldHandleSeparator(FacesContext facesContext) {
            // Arrange
            var converter = new StringToListConverter();

            // Act & Assert - Default separator
            assertEquals(StringToListConverter.SEPARATOR_DEFAULT, converter.getSeparator(),
                    "Default separator should be semicolon");

            // Act & Assert - Custom dash separator
            converter.setSeparator("-");
            assertEquals("a-b", converter.getAsString(facesContext, getComponent(), MINIMAL_LIST),
                    "Should join list with dash separator");

            // Act & Assert - Space separator
            converter.setSeparator(" ");
            assertEquals("a b", converter.getAsString(facesContext, getComponent(), MINIMAL_LIST),
                    "Should join list with space separator");
            assertEquals(" a   b ", converter.getAsString(facesContext, getComponent(), immutableList(" a ", " b ")),
                    "Should preserve spaces in values when joining");

            // Act & Assert - String to list conversion with space separator
            assertEquals(MINIMAL_LIST, converter.getAsObject(facesContext, getComponent(), "a b"),
                    "Should split string with space separator");
            assertEquals(immutableList("", "a", "b"), converter.getAsObject(facesContext, getComponent(), " a b "),
                    "Should preserve empty strings at start/end when splitting");
            assertEquals(immutableList("", "a", "", "b"),
                    converter.getAsObject(facesContext, getComponent(), " a  b "),
                    "Should preserve consecutive empty strings when splitting");
        }
    }

    @Nested
    @DisplayName("Target type configuration tests")
    class TargetTypeTests {

        @Test
        @DisplayName("Should handle different target collection types")
        void shouldHandleTargetType(FacesContext facesContext) {
            // Arrange
            var converter = new StringToListConverter();

            // Act & Assert - Default target type
            assertEquals(StringToListConverter.TARGET_TYPE_LIST, converter.getTargetType(),
                    "Default target type should be list");
            assertInstanceOf(List.class, converter.getAsObject(facesContext, getComponent(), "a;b"),
                    "Default conversion should return a List");

            // Act & Assert - SortedSet target type
            converter.setTargetType(StringToListConverter.TARGET_TYPE_SORTED_SET);
            assertInstanceOf(SortedSet.class, converter.getAsObject(facesContext, getComponent(), "a;b"),
                    "Should return a SortedSet when target type is set to sorted_set");
        }
    }

    @Nested
    @DisplayName("String processing configuration tests")
    class StringProcessingTests {

        @Test
        @DisplayName("Should omit empty strings when configured")
        void shouldOmitEmptyStrings(FacesContext facesContext) {
            // Arrange
            var converter = new StringToListConverter();
            converter.setOmitEmptyStrings(true);

            // Act & Assert - With default separator
            assertEquals(MINIMAL_LIST, converter.getAsObject(facesContext, getComponent(), ";;a;b;;"),
                    "Should omit empty strings with semicolon separator");

            // Act & Assert - With space separator
            converter.setSeparator(" ");
            assertEquals(MINIMAL_LIST, converter.getAsObject(facesContext, getComponent(), "a b"),
                    "Should handle basic space-separated string");
            assertEquals(MINIMAL_LIST, converter.getAsObject(facesContext, getComponent(), " a b "),
                    "Should omit empty strings at start/end with space separator");
            assertEquals(MINIMAL_LIST, converter.getAsObject(facesContext, getComponent(), " a  b "),
                    "Should omit consecutive empty strings with space separator");
        }

        @Test
        @DisplayName("Should trim results when configured")
        void shouldTrimResults(FacesContext facesContext) {
            // Arrange
            var converter = new StringToListConverter();
            converter.setTrimResults(true);

            // Act & Assert
            assertEquals(MINIMAL_LIST, converter.getAsObject(facesContext, getComponent(), " a ; b "),
                    "Should trim whitespace from values");
            assertEquals(immutableList("a b", "c d"),
                    converter.getAsObject(facesContext, getComponent(), " a b ; c d "),
                    "Should trim whitespace but preserve internal spaces");
        }
    }
}
