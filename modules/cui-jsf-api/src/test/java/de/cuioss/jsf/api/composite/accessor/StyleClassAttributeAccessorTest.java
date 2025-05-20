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
package de.cuioss.jsf.api.composite.accessor;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

@DisplayName("Tests for StyleClassAttributeAccessor")
class StyleClassAttributeAccessorTest {

    @Nested
    @DisplayName("Tests for style class attribute access")
    class StyleClassAttributeAccessTests {

        @Test
        @DisplayName("Should correctly access style class attributes")
        void shouldHandleStyleClassAttributes() {
            // Arrange
            var accessor = new StyleClassAttributeAccessor();
            Map<String, Object> map = new HashMap<>();

            // Act & Assert - empty map
            assertFalse(accessor.available(map),
                    "Accessor should report style class as unavailable when not in map");
            assertNull(accessor.value(map),
                    "Accessor should return null when style class is not in map");

            // Arrange - add style class to map
            final var value = "set";
            map.put("styleClass", value);

            // Act & Assert - map with style class
            assertTrue(accessor.available(map),
                    "Accessor should report style class as available when in map");
            assertEquals(value, accessor.value(map),
                    "Accessor should return the correct style class value");
        }
    }
}
