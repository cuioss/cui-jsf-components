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

@DisplayName("Tests for DefaultAwareStringAccessorImpl")
class DefaultAwareStringAccessorImplTest extends ValueObjectTest<DefaultAwareStringAccessorImpl> {

    private static final String ATTRIBUTE_NAME = "someName";

    private static final String ATTRIBUTE_VALUE = "someValue";

    private static final String DEFAULT_VALUE = "defaultValue";

    private Map<String, Object> attributeMap;

    private DefaultAwareStringAccessorImpl underTest;

    @BeforeEach
    void setUp() {
        attributeMap = new HashMap<>();
        attributeMap.put(ATTRIBUTE_NAME, ATTRIBUTE_VALUE);
        underTest = new DefaultAwareStringAccessorImpl(ATTRIBUTE_NAME, true, DEFAULT_VALUE);
    }

    @Nested
    @DisplayName("Tests for string attribute access with default values")
    class StringAttributeAccessTests {

        @Test
        @DisplayName("Should correctly access string attributes with non-null default value")
        void shouldWorkWithNonNullDefaultValue() {
            // Arrange - setup done in @BeforeEach

            // Act & Assert - attribute present
            assertTrue(underTest.available(attributeMap),
                    "Accessor should detect attribute as available");
            assertEquals(ATTRIBUTE_VALUE, underTest.value(attributeMap),
                    "Accessor should return the actual attribute value when present");

            // Act - clear map to simulate missing attribute
            attributeMap.clear();

            // Assert - default value behavior
            assertTrue(underTest.available(attributeMap),
                    "Accessor should still report attribute as available when default value is set");
            assertEquals(DEFAULT_VALUE, underTest.value(attributeMap),
                    "Accessor should return the default value when attribute is missing");
        }

        @Test
        @DisplayName("Should treat empty strings as null values")
        void shouldConsiderEmptyStringAsNull() {
            // Arrange
            attributeMap.put(ATTRIBUTE_NAME, "");

            // Act & Assert
            assertTrue(underTest.available(attributeMap),
                    "Accessor should detect attribute as available");
            assertEquals(DEFAULT_VALUE, underTest.value(attributeMap),
                    "Accessor should return default value when attribute is an empty string");
        }

        @Test
        @DisplayName("Should correctly handle null as default value")
        void shouldHandleNullAsDefault() {
            // Arrange
            underTest = new DefaultAwareStringAccessorImpl(ATTRIBUTE_NAME, true, null);

            // Act & Assert - attribute present
            assertTrue(underTest.available(attributeMap),
                    "Accessor should detect attribute as available");
            assertEquals(ATTRIBUTE_VALUE, underTest.value(attributeMap),
                    "Accessor should return the actual attribute value when present");

            // Act - clear map to simulate missing attribute
            attributeMap.clear();

            // Assert - null default value behavior
            assertFalse(underTest.available(attributeMap),
                    "Accessor should report attribute as unavailable when default value is null");
            assertNull(underTest.value(attributeMap),
                    "Accessor should return null when attribute is missing and default is null");
        }
    }

    @Override
    protected DefaultAwareStringAccessorImpl anyValueObject() {
        return new DefaultAwareStringAccessorImpl(letterStrings(1, 5).next(), booleans().next(),
                letterStrings(4, 5).next());
    }
}
