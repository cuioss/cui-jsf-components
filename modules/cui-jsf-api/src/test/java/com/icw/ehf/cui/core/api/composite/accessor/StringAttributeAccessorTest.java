package com.icw.ehf.cui.core.api.composite.accessor;

import static de.cuioss.test.generator.Generators.booleans;
import static de.cuioss.test.generator.Generators.letterStrings;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.cuioss.test.valueobjects.ValueObjectTest;

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
    void shouldConsiderEmptyStringAsNull() {
        attributeMap.put(ATTRIBUTE_NAME, "");
        assertNull(underTest.value(attributeMap));
    }

    @Test
    void shouldNotConsiderEmptyStringAsNull() {
        underTest = new StringAttributeAccessor(ATTRIBUTE_NAME, true, false);
        attributeMap.put(ATTRIBUTE_NAME, "");
        assertNotNull(underTest.value(attributeMap));
    }

    @Override
    protected StringAttributeAccessor anyValueObject() {
        return new StringAttributeAccessor(letterStrings(1, 5).next(), booleans().next(), booleans().next());
    }
}
