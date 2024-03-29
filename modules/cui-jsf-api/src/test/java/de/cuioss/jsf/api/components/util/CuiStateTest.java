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
package de.cuioss.jsf.api.components.util;

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
