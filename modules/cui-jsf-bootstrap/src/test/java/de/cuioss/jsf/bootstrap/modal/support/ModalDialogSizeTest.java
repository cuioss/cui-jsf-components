package de.cuioss.jsf.bootstrap.modal.support;

import static de.cuioss.tools.string.MoreStrings.isEmpty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ModalDialogSizeTest {

    @Test
    void shouldMapSizes() {
        assertEquals(ModalDialogSize.DEFAULT, ModalDialogSize.getFromString(""));
        assertEquals(ModalDialogSize.LG, ModalDialogSize.getFromString(ModalDialogSize.LG.name()));
        assertEquals(ModalDialogSize.FLUID, ModalDialogSize.getFromString(ModalDialogSize.FLUID.name()));
        assertEquals(ModalDialogSize.SM, ModalDialogSize.getFromString(ModalDialogSize.SM.name()));
    }

    @Test
    void shouldMapStyleClasses() {
        assertTrue(isEmpty(ModalDialogSize.DEFAULT.getStyleClass()));
        assertEquals(ModalDialogSize.PREFIX + ModalDialogSize.FLUID.name().toLowerCase(),
                ModalDialogSize.FLUID.getStyleClass());
        assertEquals(ModalDialogSize.PREFIX + ModalDialogSize.SM.name().toLowerCase(),
                ModalDialogSize.SM.getStyleClass());
        assertEquals(ModalDialogSize.PREFIX + ModalDialogSize.LG.name().toLowerCase(),
                ModalDialogSize.LG.getStyleClass());
    }

    @Test
    void shouldFailToMapInvalidString() {
        assertThrows(IllegalArgumentException.class, () -> ModalDialogSize.getFromString("not-there"));
    }
}
