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

    @Test
    void shouldWorkOnHappyCase() {
        assertTrue(underTest.available(attributeMap));
        assertEquals(ATTRIBUTE_VALUE, underTest.value(attributeMap));
        // Clear Map
        attributeMap.clear();
        assertFalse(underTest.available(attributeMap));
        assertNull(underTest.value(attributeMap));
    }

    @Test
    void shouldInverse() {
        underTest = new BooleanAttributeAccessor(ATTRIBUTE_NAME, true, true);
        assertTrue(underTest.available(attributeMap));
        assertEquals(Boolean.FALSE, underTest.value(attributeMap));
        // Clear Map
        attributeMap.clear();
        assertFalse(underTest.available(attributeMap));
        assertNull(underTest.value(attributeMap));
    }

    @Override
    protected BooleanAttributeAccessor anyValueObject() {
        return new BooleanAttributeAccessor(letterStrings(1, 5).next(), booleans().next(), booleans().next());
    }
}
