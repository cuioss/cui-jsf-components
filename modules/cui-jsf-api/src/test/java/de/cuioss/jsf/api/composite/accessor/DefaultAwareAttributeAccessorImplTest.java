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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.cuioss.test.valueobjects.ValueObjectTest;

class DefaultAwareAttributeAccessorImplTest extends ValueObjectTest<DefaultAwareAttributeAccessorImpl<String>> {

    private static final String ATTRIBUTE_NAME = "someName";

    private static final String ATTRIBUTE_VALUE = "someValue";

    private static final String DEFAULT_VALUE = "defaultValue";

    private Map<String, Object> attributeMap;

    private DefaultAwareAttributeAccessorImpl<String> underTest;

    @BeforeEach
    void setUp() {
        attributeMap = new HashMap<>();
        attributeMap.put(ATTRIBUTE_NAME, ATTRIBUTE_VALUE);
        underTest = new DefaultAwareAttributeAccessorImpl<>(ATTRIBUTE_NAME, String.class, true, DEFAULT_VALUE);
    }

    @Test
    void shouldWorkOnHappyCase() {
        assertTrue(underTest.available(attributeMap));
        assertEquals(ATTRIBUTE_VALUE, underTest.value(attributeMap));
        // Clear Map
        attributeMap.clear();
        // Default value is
        assertTrue(underTest.available(attributeMap));
        // always there
        assertEquals(DEFAULT_VALUE, underTest.value(attributeMap));
    }

    @Test
    void shouldHandleNullAsDefault() {
        underTest = new DefaultAwareAttributeAccessorImpl<>(ATTRIBUTE_NAME, String.class, true, null);
        assertTrue(underTest.available(attributeMap));
        assertEquals(ATTRIBUTE_VALUE, underTest.value(attributeMap));
        // Clear Map
        attributeMap.clear();
        // Default value is
        assertFalse(underTest.available(attributeMap));
        // always there
        assertNull(underTest.value(attributeMap));
    }

    @Override
    protected DefaultAwareAttributeAccessorImpl<String> anyValueObject() {
        return new DefaultAwareAttributeAccessorImpl<>(letterStrings(1, 5).next(), String.class, booleans().next(),
                letterStrings(4, 5).next());
    }
}
