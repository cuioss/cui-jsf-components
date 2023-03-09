package com.icw.ehf.cui.application.resources;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.icw.ehf.cui.application.resources.util.LibraryInventory;

class CuiResourceManagerTest {

    private CuiResourceManager cui;

    public LibraryInventory lib;

    @BeforeEach
    void setUp() {
        cui = new CuiResourceManager();
    }

    @Test
    void test() {
        cui.getLibraryInventory("library");
        assertNotNull(cui.getLibraryInventory("library"));
    }
}
