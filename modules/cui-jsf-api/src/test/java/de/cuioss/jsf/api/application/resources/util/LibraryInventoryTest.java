package de.cuioss.jsf.api.application.resources.util;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LibraryInventoryTest {

    private final String libraryName = "test";

    private LibraryInventory lib;

    @BeforeEach
    void setUp() {
        lib = new LibraryInventory(libraryName);
        // lib = new LibraryInventory(null);
        // lib = new LibraryInventory("");
    }

    @Test
    void containsMappingtest() {
        assertFalse(lib.containsMapping("name"));
        lib.addMapping("name", "mappedName");
        assertTrue(lib.containsMapping("name"));
    }

    @Test
    void testNullContainsMapping() {
        assertThrows(NullPointerException.class, () -> lib.containsMapping(null));
    }

    @Test
    void getResourceMappingTest() {
        assertDoesNotThrow(() -> lib.getResourceMapping(libraryName));
    }

    @Test
    void testNullGetReourceMapping() {
        assertThrows(NullPointerException.class, () -> lib.getResourceMapping(null));
    }

    @Test
    void addMappingTest() {
        assertFalse(lib.containsMapping("key"));
        lib.addMapping("key", "name");
        assertTrue(lib.containsMapping("key"));
        assertEquals("name", lib.getResourceMapping("key"));
    }

    @Test
    void testNullResourceKey() {
        assertThrows(NullPointerException.class, () -> lib.addMapping(null, "name"));
    }

    @Test
    void testNullResourceValue() {
        assertThrows(NullPointerException.class, () -> lib.addMapping("key", null));
    }

    @Test
    void noResourceValues() {
        assertThrows(NullPointerException.class, () -> lib.addMapping(null, null));
    }
}
