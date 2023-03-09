package com.icw.ehf.cui.core.api.components.util;

import static com.icw.ehf.cui.core.api.components.util.KeyMappingUtility.mapKeyWithExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class KeyMappingUtilityTest {

    private static final String STRING_KEY = "key";

    private static final String EXTENSION = "extension";

    private static final String EXTENSION_SUFFIX = "_" + EXTENSION;

    private static final Integer INTEGER_KEY = 1;

    @Test
    void shouldMapWithEmptyExtension() {
        assertEquals(STRING_KEY, mapKeyWithExtension(STRING_KEY, null));
        assertEquals(INTEGER_KEY.toString(), mapKeyWithExtension(INTEGER_KEY, null));
    }

    @Test
    void shouldMapWithExtension() {
        assertEquals(STRING_KEY + EXTENSION_SUFFIX, mapKeyWithExtension(STRING_KEY, EXTENSION));
        assertEquals(INTEGER_KEY.toString() + EXTENSION_SUFFIX, mapKeyWithExtension(INTEGER_KEY, EXTENSION));
    }
}
