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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.cuioss.test.valueobjects.ValueObjectTest;

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
    void shouldCacheOnNeverResolve() {
        underTest = new AttributeAccessorImpl<>(ATTRIBUTE_NAME, String.class, false);
        assertTrue(underTest.available(attributeMap));
        assertEquals(ATTRIBUTE_VALUE, underTest.value(attributeMap));
        // Clear Map
        attributeMap.clear();
        assertEquals(ATTRIBUTE_VALUE, underTest.value(attributeMap));
        attributeMap.put(ATTRIBUTE_NAME, ATTRIBUTE_VALUE_ALTERNATIVE);
        assertTrue(underTest.available(attributeMap));
        assertEquals(ATTRIBUTE_VALUE, underTest.value(attributeMap));
    }

    @Test
    void shouldFailOnInvalidType() {
        attributeMap.clear();
        attributeMap.put(ATTRIBUTE_NAME, 4);
        assertThrows(IllegalStateException.class, () -> underTest.value(attributeMap));
    }

    public interface TestInterfaceA {
        // dymmy test interface
    }

    public interface TestInterfaceB extends TestInterfaceA {
        // dymmy test interface
    }

    @Override
    protected AttributeAccessorImpl<String> anyValueObject() {
        return new AttributeAccessorImpl<>(ATTRIBUTE_NAME, String.class, booleans().next());
    }
}
