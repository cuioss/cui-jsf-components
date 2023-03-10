package de.cuioss.jsf.api.composite.accessor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class StyleClassAttributeAccessorTest {

    @Test
    void shouldHandleStyleClass() {
        var accessor = new StyleClassAttributeAccessor();
        Map<String, Object> map = new HashMap<>();
        assertFalse(accessor.available(map));
        assertNull(accessor.value(map));
        final var value = "set";
        map.put("styleClass", value);
        assertTrue(accessor.available(map));
        assertEquals(value, accessor.value(map));
    }
}
