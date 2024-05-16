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
package de.cuioss.jsf.api.ui.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.event.ActionEvent;
import jakarta.faces.event.ComponentSystemEvent;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.components.support.DummyComponent;
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
