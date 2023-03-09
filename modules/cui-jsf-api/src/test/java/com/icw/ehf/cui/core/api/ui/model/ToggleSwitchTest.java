package com.icw.ehf.cui.core.api.ui.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.faces.event.ActionEvent;
import javax.faces.event.ComponentSystemEvent;

import org.junit.jupiter.api.Test;

import com.icw.ehf.cui.core.api.components.support.DummyComponent;

import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.contracts.VerifyBeanProperty;

@VerifyBeanProperty
class ToggleSwitchTest extends ValueObjectTest<ToggleSwitch> {

    @Test
    void shouldToggle() {
        var toggle = new ToggleSwitch(Boolean.TRUE);
        assertTrue(toggle.isToggled());
        toggle.toggle();
        assertFalse(toggle.isToggled());
        toggle.toggle(new ActionEvent(new DummyComponent()));
        assertTrue(toggle.isToggled());
        toggle.resetToggle(new ComponentSystemEvent(new DummyComponent()) {

            private static final long serialVersionUID = -7634273629880530157L;
        });
        assertFalse(toggle.isToggled());
        toggle = new ToggleSwitch(null);
        assertFalse(toggle.isToggled());
    }
}
