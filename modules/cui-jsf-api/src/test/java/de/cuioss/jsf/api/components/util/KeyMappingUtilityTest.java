/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.api.components.util;

import static de.cuioss.jsf.api.components.util.KeyMappingUtility.mapKeyWithExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for KeyMappingUtility")
class KeyMappingUtilityTest {

    private static final String STRING_KEY = "key";

    private static final String EXTENSION = "extension";

    private static final String EXTENSION_SUFFIX = "_" + EXTENSION;

    private static final Integer INTEGER_KEY = 1;

    @Nested
    @DisplayName("Tests for key mapping with extensions")
    class KeyMappingTests {

        @Test
        @DisplayName("Should map keys without modification when extension is null")
        void shouldMapWithEmptyExtension() {
            // Arrange - test data defined in class constants

            // Act & Assert - string key with null extension
            assertEquals(STRING_KEY, mapKeyWithExtension(STRING_KEY, null),
                    "String key should remain unchanged with null extension");

            // Act & Assert - integer key with null extension
            assertEquals(INTEGER_KEY.toString(), mapKeyWithExtension(INTEGER_KEY, null),
                    "Integer key should be converted to string and remain unchanged with null extension");
        }

        @Test
        @DisplayName("Should append extension to keys with underscore separator")
        void shouldMapWithExtension() {
            // Arrange - test data defined in class constants

            // Act & Assert - string key with extension
            assertEquals(STRING_KEY + EXTENSION_SUFFIX, mapKeyWithExtension(STRING_KEY, EXTENSION),
                    "String key should have extension appended with underscore separator");

            // Act & Assert - integer key with extension
            assertEquals(INTEGER_KEY + EXTENSION_SUFFIX, mapKeyWithExtension(INTEGER_KEY, EXTENSION),
                    "Integer key should be converted to string and have extension appended with underscore separator");
        }
    }
}
