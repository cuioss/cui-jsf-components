package com.icw.ehf.cui.core.api.components.css;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class AlignHolderTest {

    @Test
    void shouldDefaultToUndefined() {
        assertEquals(AlignHolder.DEFAULT, AlignHolder.getFromString(null));
        assertEquals(AlignHolder.DEFAULT, AlignHolder.getFromString(""));
        assertEquals(AlignHolder.DEFAULT, AlignHolder.getFromString("notThere"));
        assertNotNull(AlignHolder.DEFAULT.getStyleClassBuilder());
        assertNotNull(AlignHolder.DEFAULT.getStyleClass());
    }

    @Test
    void shouldDetermineRight() {
        assertEquals(AlignHolder.RIGHT, AlignHolder.getFromString("right"));
        assertEquals(AlignHolder.RIGHT, AlignHolder.getFromString("RIGHT"));
        assertEquals(AlignHolder.RIGHT, AlignHolder.getFromString("RigHt"));
    }

    @Test
    void shouldDetermineLeft() {
        assertEquals(AlignHolder.LEFT, AlignHolder.getFromString("left"));
        assertEquals(AlignHolder.LEFT, AlignHolder.getFromString("LEFT"));
        assertEquals(AlignHolder.LEFT, AlignHolder.getFromString("lEFt"));
    }
}
