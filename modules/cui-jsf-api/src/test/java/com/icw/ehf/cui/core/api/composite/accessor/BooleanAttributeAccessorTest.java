package com.icw.ehf.cui.core.api.composite.accessor;

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
