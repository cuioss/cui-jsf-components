package com.icw.ehf.cui.core.api.components.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CuiStateTest {

    @Test
    void shouldHandleBoolean() {
        var component = new CuiStateComponent();
        assertFalse(component.getBooleanValue());
        component.setBooleanValue(true);
        assertTrue(component.getBooleanValue());
        component = new CuiStateComponent();
        assertTrue(component.getBooleanValue(true));
    }

    @Test
    void shouldHandleInt() {
        var component = new CuiStateComponent();
        assertEquals(0, component.getIntValue());
        component.setIntValue(1);
        assertEquals(1, component.getIntValue());
        component = new CuiStateComponent();
        assertEquals(2, component.getIntValue(2));
    }
}
