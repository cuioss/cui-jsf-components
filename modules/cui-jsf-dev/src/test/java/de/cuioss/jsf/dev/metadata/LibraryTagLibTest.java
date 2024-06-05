package de.cuioss.jsf.dev.metadata;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LibraryTagLibTest {

    @Test
    void shouldLoadTaglibs() {
        for (var lib : LibraryTagLib.values()) {
            var loaded = assertDoesNotThrow(lib::load, lib.name());
            assertNotNull(loaded);
        }
    }

    @Test
    void shouldHandleMyFaces() {
        var loaded = LibraryTagLib.MY_FACES.load();
        assertNotNull(loaded);
        System.out.println(loaded);
    }
}