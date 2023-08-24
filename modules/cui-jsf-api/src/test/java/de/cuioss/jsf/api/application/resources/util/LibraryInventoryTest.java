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
