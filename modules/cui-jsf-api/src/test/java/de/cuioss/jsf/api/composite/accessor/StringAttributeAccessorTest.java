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
package de.cuioss.jsf.api.composite.accessor;

import static de.cuioss.test.generator.Generators.booleans;
import static de.cuioss.test.generator.Generators.letterStrings;
import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.test.valueobjects.ValueObjectTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

@DisplayName("Tests for StringAttributeAccessor")
class StringAttributeAccessorTest extends ValueObjectTest<StringAttributeAccessor> {

    private static final String ATTRIBUTE_NAME = "someName";

    private static final String ATTRIBUTE_VALUE = "someValue";

    private Map<String, Object> attributeMap;

    private StringAttributeAccessor underTest;

    @BeforeEach
    void setUp() {
        attributeMap = new HashMap<>();
        attributeMap.put(ATTRIBUTE_NAME, ATTRIBUTE_VALUE);
        underTest = new StringAttributeAccessor(ATTRIBUTE_NAME, true, true);
    }

    @Nested
    @DisplayName("Tests for basic attribute access functionality")
    class BasicAttributeAccessTests {

        @Test
        @DisplayName("Should correctly access string attributes in standard mode")
        void shouldAccessAttributesInStandardMode() {
            // Arrange - setup done in @BeforeEach

            // Act & Assert - attribute present
            assertTrue(underTest.available(attributeMap),
                    "Accessor should detect attribute as available");
            assertEquals(ATTRIBUTE_VALUE, underTest.value(attributeMap),
                    "Accessor should return the correct attribute value");

            // Act - clear map
            attributeMap.clear();

            // Assert - attribute not present
            assertFalse(underTest.available(attributeMap),
                    "Accessor should detect attribute as not available after clearing map");
            assertNull(underTest.value(attributeMap),
                    "Accessor should return null when attribute is not available");
        }
    }

    @Nested
    @DisplayName("Tests for empty string handling")
    class EmptyStringHandlingTests {

        @Test
        @DisplayName("Should treat empty strings as null when configured to do so")
        void shouldConsiderEmptyStringAsNull() {
            // Arrange
            attributeMap.put(ATTRIBUTE_NAME, "");

            // Act & Assert
            assertNull(underTest.value(attributeMap),
                    "Accessor should return null for empty string when configured to treat empty as null");
        }

        @Test
        @DisplayName("Should preserve empty strings when configured not to treat them as null")
        void shouldNotConsiderEmptyStringAsNull() {
            // Arrange
            underTest = new StringAttributeAccessor(ATTRIBUTE_NAME, true, false);
            attributeMap.put(ATTRIBUTE_NAME, "");

            // Act & Assert
            assertNotNull(underTest.value(attributeMap),
                    "Accessor should preserve empty string when configured not to treat empty as null");
        }
    }

    @Override
    protected StringAttributeAccessor anyValueObject() {
        return new StringAttributeAccessor(letterStrings(1, 5).next(), booleans().next(), booleans().next());
    }
}
