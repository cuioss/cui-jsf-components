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
package de.cuioss.jsf.jqplot.js.support;

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.jsf.jqplot.js.types.JsString;
import de.cuioss.test.valueobjects.ValueObjectTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@DisplayName("Tests for JsArray class")
class JsArrayTest extends ValueObjectTest<JsArray<JsValue>> {

    @Nested
    @DisplayName("Basic JSON array generation tests")
    class BasicJsonArrayTests {

        @Test
        @DisplayName("Should produce empty JSON array")
        void shouldProduceEmptyJSONArray() {
            // Arrange
            final var target = new JsArray<JsString>();

            // Act & Assert
            assertEquals("[]", target.asJavaScriptObjectNotation());
        }

        @Test
        @DisplayName("Should produce JSON array with one element")
        void shouldProduceJSONArrayWithOneElement() {
            // Arrange
            final var target = new JsArray<JsString>();

            // Act
            target.addValueIfNotNull(new JsString("val1"));

            // Assert
            assertEquals("[\"val1\"]", target.asJavaScriptObjectNotation());
        }

        @Test
        @DisplayName("Should produce JSON array with several elements")
        void shouldProduceJSONArrayWithSeveralElements() {
            // Arrange
            final var target = new JsArray<JsString>();

            // Act
            target.addValueIfNotNull(new JsString("val1"));
            target.addValueIfNotNull(new JsString("val2"));

            // Assert
            assertEquals("[\"val1\",\"val2\"]", target.asJavaScriptObjectNotation());
        }
    }

    @Nested
    @DisplayName("Advanced functionality tests")
    class AdvancedFunctionalityTests {

        @Test
        @DisplayName("Should ignore null values")
        void shouldIgnoreNullValues() {
            // Arrange
            final var target = new JsArray<JsString>();

            // Act
            target.addValueIfNotNull(new JsString("val1"));
            target.addValueIfNotNull(null);
            target.addValueIfNotNull(new JsString("val2"));

            // Assert
            assertEquals("[\"val1\",\"val2\"]", target.asJavaScriptObjectNotation());
        }

        @Test
        @DisplayName("Should implement Iterable interface")
        void shouldImplementIterable() {
            // Arrange
            final var target = new JsArray<JsString>();

            // Act & Assert - iterator
            assertNotNull(target.iterator());

            // Act - add item
            final var item = new JsString("val1");
            target.addValueIfNotNull(item);

            // Assert - value is accessible
            assertTrue(target.getValueAsString().contains(item.getValue()));
        }

        @Test
        @DisplayName("Should support nested arrays")
        void shouldSupportNestedArrays() {
            // Arrange
            final var target = new JsArray<JsArray<JsString>>();
            final var array1 = new JsArray<JsString>();
            array1.addValueIfNotNull(new JsString("arr1-val1"));
            final var array2 = new JsArray<JsString>();
            array2.addValueIfNotNull(new JsString("arr2-val1"));
            array2.addValueIfNotNull(new JsString("arr2-val2"));

            // Act
            target.addValueIfNotNull(array1);
            target.addValueIfNotNull(array2);

            // Assert
            final var expected = """
                    [\
                    ["arr1-val1"]\
                    ,\
                    ["arr2-val1","arr2-val2"]\
                    ]""";
            assertEquals(expected, target.asJavaScriptObjectNotation());
        }

        @Test
        @DisplayName("Should handle adding a collection of entries")
        void shouldHandleAddAmountOfEntries() {
            // Arrange
            final var target = new JsArray<JsString>();
            final List<JsString> items = new ArrayList<>();

            // Act - add empty list
            target.addAll(items);

            // Assert - no entries expected
            assertEquals("[]", target.asJavaScriptObjectNotation());

            // Act - add list with nulls and values
            items.add(null);
            items.add(new JsString("val1"));
            items.add(null);
            target.addAll(items);

            // Assert - only non-null values should be added
            assertEquals("[\"val1\"]", target.asJavaScriptObjectNotation());
        }
    }

    @Override
    protected JsArray<JsValue> anyValueObject() {
        return new JsArray<>();
    }
}
