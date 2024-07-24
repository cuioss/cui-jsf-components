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
package de.cuioss.jsf.api.components.util;

import static de.cuioss.jsf.api.components.util.KeyMappingUtility.mapKeyWithExtension;
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
        assertEquals(INTEGER_KEY + EXTENSION_SUFFIX, mapKeyWithExtension(INTEGER_KEY, EXTENSION));
    }
}
