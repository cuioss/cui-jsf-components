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
package de.cuioss.jsf.bootstrap.icon.support;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.components.css.ContextState;

class IconStateTest {

    @Test
    void shouldMapNullToDefault() {
        assertEquals(IconState.DEFAULT, IconState.getForContextState(null));
        assertNotNull(IconState.getForContextState(null).getStyleClassBuilder());
        assertNotNull(IconState.getForContextState(null).getStyleClass());
    }

    @Test
    void shouldMapCorrectly() {
        assertEquals(IconState.DEFAULT, IconState.getForContextState(ContextState.DEFAULT));
        assertEquals("", IconState.getForContextState(ContextState.DEFAULT).getStyleClass());
        assertEquals(IconState.DANGER, IconState.getForContextState(ContextState.DANGER));
        assertEquals("cui-icon-state-danger", IconState.getForContextState(ContextState.DANGER).getStyleClass());
        assertEquals(IconState.INFO, IconState.getForContextState(ContextState.INFO));
        assertEquals("cui-icon-state-info", IconState.getForContextState(ContextState.INFO).getStyleClass());
        assertEquals(IconState.PRIMARY, IconState.getForContextState(ContextState.PRIMARY));
        assertEquals("cui-icon-state-primary", IconState.getForContextState(ContextState.PRIMARY).getStyleClass());
        assertEquals(IconState.SUCCESS, IconState.getForContextState(ContextState.SUCCESS));
        assertEquals("cui-icon-state-success", IconState.getForContextState(ContextState.SUCCESS).getStyleClass());
        assertEquals(IconState.WARNING, IconState.getForContextState(ContextState.WARNING));
        assertEquals("cui-icon-state-warning", IconState.getForContextState(ContextState.WARNING).getStyleClass());
    }
}
