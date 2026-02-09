/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.bootstrap.button.support;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class ButtonStateTest {

    @Test
    void shouldMapNullToDefault() {
        assertEquals(ButtonState.DEFAULT, ButtonState.getForContextState(null));
        assertNotNull(ButtonState.getForContextState(null).getStyleClassBuilder());
        assertNotNull(ButtonState.getForContextState(null).getStyleClass());
    }

    @Test
    void shouldMapCorrectly() {
        assertEquals(ButtonState.DEFAULT, ButtonState.getForContextState(ButtonState.DEFAULT.name()));
        assertEquals("btn-default", ButtonState.getForContextState(ButtonState.DEFAULT.name()).getStyleClass());
        assertEquals(ButtonState.DANGER, ButtonState.getForContextState(ButtonState.DANGER.name()));
        assertEquals("btn-danger", ButtonState.getForContextState(ButtonState.DANGER.name()).getStyleClass());
        assertEquals(ButtonState.INFO, ButtonState.getForContextState(ButtonState.INFO.name()));
        assertEquals("btn-info", ButtonState.getForContextState(ButtonState.INFO.name()).getStyleClass());
        assertEquals(ButtonState.PRIMARY, ButtonState.getForContextState(ButtonState.PRIMARY.name()));
        assertEquals("btn-primary", ButtonState.getForContextState(ButtonState.PRIMARY.name()).getStyleClass());
        assertEquals(ButtonState.SUCCESS, ButtonState.getForContextState(ButtonState.SUCCESS.name()));
        assertEquals("btn-success", ButtonState.getForContextState(ButtonState.SUCCESS.name()).getStyleClass());
        assertEquals(ButtonState.WARNING, ButtonState.getForContextState(ButtonState.WARNING.name()));
        assertEquals("btn-warning", ButtonState.getForContextState(ButtonState.WARNING.name()).getStyleClass());
        assertEquals(ButtonState.LINK, ButtonState.getForContextState(ButtonState.LINK.name()));
        assertEquals("btn-link", ButtonState.getForContextState(ButtonState.LINK.name()).getStyleClass());
    }
}
