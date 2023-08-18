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
package de.cuioss.jsf.api.components.support;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.components.partial.ComponentBridge;
import de.cuioss.jsf.api.components.partial.MockPartialComponent;

class OneTimeCheckTest {

    private ComponentBridge bridge;

    @BeforeEach
    void before() {
        bridge = new MockPartialComponent();
    }

    @Test
    void shouldDefaultToFalse() {
        final var check = new OneTimeCheck(bridge);
        assertFalse(check.isChecked());
    }

    @Test
    void shouldBeUsableMultipleTimes() {
        final var checkOne = new OneTimeCheck(bridge, "one");
        final var checkTwo = new OneTimeCheck(bridge, "two");
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
        final var check = new OneTimeCheck(bridge);
        assertFalse(check.isChecked());
        assertFalse(check.readAndSetChecked());
        assertTrue(check.isChecked());
    }
}
