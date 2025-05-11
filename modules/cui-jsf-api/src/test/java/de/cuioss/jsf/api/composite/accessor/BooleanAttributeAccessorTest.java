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

@DisplayName("Tests for BooleanAttributeAccessor")
class BooleanAttributeAccessorTest extends ValueObjectTest<BooleanAttributeAccessor> {

    private static final String ATTRIBUTE_NAME = "someName";

    private static final Boolean ATTRIBUTE_VALUE = Boolean.TRUE;

    private Map<String, Object> attributeMap;

    private BooleanAttributeAccessor underTest;

    @BeforeEach
    void setUp() {
        attributeMap = new HashMap<>();
        attributeMap.put(ATTRIBUTE_NAME, ATTRIBUTE_VALUE);
        underTest = new BooleanAttributeAccessor(ATTRIBUTE_NAME, true, false);
    }

    @Nested
    @DisplayName("Tests for attribute access functionality")
    class AttributeAccessTests {

        @Test
        @DisplayName("Should correctly access boolean attributes in standard mode")
        void shouldWorkOnHappyCase() {
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

        @Test
        @DisplayName("Should correctly invert boolean values when inverse mode is enabled")
        void shouldInverseValues() {
            // Arrange
            underTest = new BooleanAttributeAccessor(ATTRIBUTE_NAME, true, true);

            // Act & Assert - attribute present
            assertTrue(underTest.available(attributeMap),
                    "Accessor should detect attribute as available");
            assertEquals(Boolean.FALSE, underTest.value(attributeMap),
                    "Accessor should return the inverted attribute value");

            // Act - clear map
            attributeMap.clear();

            // Assert - attribute not present
            assertFalse(underTest.available(attributeMap),
                    "Accessor should detect attribute as not available after clearing map");
            assertNull(underTest.value(attributeMap),
                    "Accessor should return null when attribute is not available");
        }
    }

    @Override
    protected BooleanAttributeAccessor anyValueObject() {
        return new BooleanAttributeAccessor(letterStrings(1, 5).next(), booleans().next(), booleans().next());
    }
}
