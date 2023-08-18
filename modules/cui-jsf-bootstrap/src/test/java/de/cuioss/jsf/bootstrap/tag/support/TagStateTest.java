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
package de.cuioss.jsf.bootstrap.tag.support;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.components.css.ContextState;

class TagStateTest {

    @Test
    void shouldMapNullToDefault() {
        assertEquals(TagState.DEFAULT, TagState.getForContextState(null));
        assertNotNull(TagState.getForContextState(null).getStyleClassBuilder());
        assertNotNull(TagState.getForContextState(null).getStyleClass());
    }

    @Test
    void shouldMapCorrectly() {
        assertEquals(TagState.DEFAULT, TagState.getForContextState(ContextState.DEFAULT));
        assertEquals("cui-tag-default", TagState.getForContextState(ContextState.DEFAULT).getStyleClass());
        assertEquals(TagState.DANGER, TagState.getForContextState(ContextState.DANGER));
        assertEquals("cui-tag-danger", TagState.getForContextState(ContextState.DANGER).getStyleClass());
        assertEquals(TagState.INFO, TagState.getForContextState(ContextState.INFO));
        assertEquals("cui-tag-info", TagState.getForContextState(ContextState.INFO).getStyleClass());
        assertEquals(TagState.PRIMARY, TagState.getForContextState(ContextState.PRIMARY));
        assertEquals("cui-tag-primary", TagState.getForContextState(ContextState.PRIMARY).getStyleClass());
        assertEquals(TagState.SUCCESS, TagState.getForContextState(ContextState.SUCCESS));
        assertEquals("cui-tag-success", TagState.getForContextState(ContextState.SUCCESS).getStyleClass());
        assertEquals(TagState.WARNING, TagState.getForContextState(ContextState.WARNING));
        assertEquals("cui-tag-warning", TagState.getForContextState(ContextState.WARNING).getStyleClass());
    }
}
