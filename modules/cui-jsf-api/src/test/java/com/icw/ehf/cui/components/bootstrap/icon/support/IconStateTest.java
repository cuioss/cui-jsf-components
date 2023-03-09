package com.icw.ehf.cui.components.bootstrap.icon.support;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.icw.ehf.cui.core.api.components.css.ContextState;

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
