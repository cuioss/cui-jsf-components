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
package de.cuioss.jsf.api.composite;

import static de.cuioss.test.generator.Generators.booleans;
import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.test.valueobjects.ValueObjectTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

@DisplayName("Tests for AttributeAccessorImpl")
class AttributeAccessorImplTest extends ValueObjectTest<AttributeAccessorImpl<String>> {

    private static final String ATTRIBUTE_NAME = "someName";

    private static final String ATTRIBUTE_VALUE = "someValue";

    private static final String ATTRIBUTE_VALUE_ALTERNATIVE = "someOtherValue";

    private Map<String, Object> attributeMap;

    private AttributeAccessor<String> underTest;

    @BeforeEach
    void setUp() {
        attributeMap = new HashMap<>();
        attributeMap.put(ATTRIBUTE_NAME, ATTRIBUTE_VALUE);
        underTest = new AttributeAccessorImpl<>(ATTRIBUTE_NAME, String.class, true);
    }

    @Nested
    @DisplayName("Tests for basic attribute access functionality")
    class BasicAttributeAccessTests {

        @Test
        @DisplayName("Should correctly access attributes in standard mode")
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
    @DisplayName("Tests for caching behavior")
    class CachingBehaviorTests {

        @Test
        @DisplayName("Should cache values when configured to never resolve")
        void shouldCacheValuesWhenConfiguredToNeverResolve() {
            // Arrange
            underTest = new AttributeAccessorImpl<>(ATTRIBUTE_NAME, String.class, false);

            // Act & Assert - initial access
            assertTrue(underTest.available(attributeMap),
                    "Accessor should detect attribute as available");
            assertEquals(ATTRIBUTE_VALUE, underTest.value(attributeMap),
                    "Accessor should return the correct attribute value");

            // Act - clear map
            attributeMap.clear();

            // Assert - cached value should still be returned
            assertEquals(ATTRIBUTE_VALUE, underTest.value(attributeMap),
                    "Accessor should return cached value even when attribute is removed");

            // Act - change attribute value
            attributeMap.put(ATTRIBUTE_NAME, ATTRIBUTE_VALUE_ALTERNATIVE);

            // Assert - cached value should still be returned
            assertTrue(underTest.available(attributeMap),
                    "Accessor should detect attribute as available");
            assertEquals(ATTRIBUTE_VALUE, underTest.value(attributeMap),
                    "Accessor should return cached value even when attribute value changes");
        }
    }

    @Nested
    @DisplayName("Tests for error handling")
    class ErrorHandlingTests {

        @Test
        @DisplayName("Should throw exception when attribute value has invalid type")
        void shouldThrowExceptionForInvalidType() {
            // Arrange
            attributeMap.clear();
            attributeMap.put(ATTRIBUTE_NAME, 4); // Integer instead of String

            // Act & Assert
            assertThrows(IllegalStateException.class, () -> underTest.value(attributeMap),
                    "Accessor should throw IllegalStateException when attribute value has invalid type");
        }
    }

    public interface TestInterfaceA {
        // dummy test interface
    }

    public interface TestInterfaceB extends TestInterfaceA {
        // dummy test interface
    }

    @Override
    protected AttributeAccessorImpl<String> anyValueObject() {
        return new AttributeAccessorImpl<>(ATTRIBUTE_NAME, String.class, booleans().next());
    }
}
