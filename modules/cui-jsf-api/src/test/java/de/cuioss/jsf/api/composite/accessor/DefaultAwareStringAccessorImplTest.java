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
    void shouldConsiderEmptyStringAsNull() {
        attributeMap.put(ATTRIBUTE_NAME, "");
        // Default value is
        assertTrue(underTest.available(attributeMap));
        // always there
        assertEquals(DEFAULT_VALUE, underTest.value(attributeMap));
    }

    @Test
    void shouldHandleNullAsDefault() {
        underTest = new DefaultAwareStringAccessorImpl(ATTRIBUTE_NAME, true, null);
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
    protected DefaultAwareStringAccessorImpl anyValueObject() {
        return new DefaultAwareStringAccessorImpl(letterStrings(1, 5).next(), booleans().next(),
                letterStrings(4, 5).next());
    }
}
