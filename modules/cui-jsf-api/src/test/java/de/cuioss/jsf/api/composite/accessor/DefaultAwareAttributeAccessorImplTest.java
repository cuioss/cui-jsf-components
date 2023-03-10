package de.cuioss.jsf.api.composite.accessor;

import static de.cuioss.test.generator.Generators.booleans;
import static de.cuioss.test.generator.Generators.letterStrings;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
        assertEquals(null, underTest.value(attributeMap));
    }

    @Override
    protected DefaultAwareAttributeAccessorImpl<String> anyValueObject() {
        return new DefaultAwareAttributeAccessorImpl<>(letterStrings(1, 5).next(), String.class, booleans().next(),
                letterStrings(4, 5).next());
    }
}
