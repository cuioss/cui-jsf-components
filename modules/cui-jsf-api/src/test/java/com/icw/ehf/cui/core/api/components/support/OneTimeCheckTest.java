package com.icw.ehf.cui.core.api.components.support;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.icw.ehf.cui.core.api.components.partial.ComponentBridge;
import com.icw.ehf.cui.core.api.components.partial.MockPartialComponent;

class OneTimeCheckTest {

    private ComponentBridge bridge;

    @BeforeEach
    void before() {
        this.bridge = new MockPartialComponent();
    }

    @Test
    void shouldDefaultToFalse() {
        final var check = new OneTimeCheck(this.bridge);
        assertFalse(check.isChecked());
    }

    @Test
    void shouldBeUsableMultipleTimes() {
        final var checkOne = new OneTimeCheck(this.bridge, "one");
        final var checkTwo = new OneTimeCheck(this.bridge, "two");
        assertFalse(checkOne.isChecked());
        assertFalse(checkTwo.isChecked());
        checkOne.setChecked(true);
        assertTrue(checkOne.isChecked());
        assertFalse(checkTwo.isChecked());
        checkTwo.setChecked(true);
        assertTrue(checkOne.isChecked());
        assertTrue(checkTwo.isChecked());
        checkOne.setChecked(false);
        assertFalse(checkOne.isChecked());
        assertTrue(checkTwo.isChecked());
    }

    @Test
    void shouldReadAndSetChecked() {
        final var check = new OneTimeCheck(this.bridge);
        assertFalse(check.isChecked());
        assertFalse(check.readAndSetChecked());
        assertTrue(check.isChecked());
    }
}
