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

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for CuiState")
class CuiStateTest {

    @Nested
    @DisplayName("Tests for boolean value handling")
    class BooleanValueTests {

        @Test
        @DisplayName("Should store and retrieve boolean values correctly")
        void shouldHandleBooleanValues() {
            // Arrange
            var component = new CuiStateComponent();

            // Act & Assert - default value
            assertFalse(component.getBooleanValue(),
                    "Boolean value should default to false when not set");

            // Act - set value to true
            component.setBooleanValue(true);

            // Assert - after setting to true
            assertTrue(component.getBooleanValue(),
                    "Boolean value should be true after setting");

            // Arrange - new component instance
            component = new CuiStateComponent();

            // Act & Assert - with explicit default value
            assertTrue(component.getBooleanValue(true),
                    "Boolean value should use provided default value when not set");
        }
    }

    @Nested
    @DisplayName("Tests for integer value handling")
    class IntegerValueTests {

        @Test
        @DisplayName("Should store and retrieve integer values correctly")
        void shouldHandleIntegerValues() {
            // Arrange
            var component = new CuiStateComponent();

            // Act & Assert - default value
            assertEquals(0, component.getIntValue(),
                    "Integer value should default to 0 when not set");

            // Act - set value to 1
            component.setIntValue(1);

            // Assert - after setting to 1
            assertEquals(1, component.getIntValue(),
                    "Integer value should be 1 after setting");

            // Arrange - new component instance
            component = new CuiStateComponent();

            // Act & Assert - with explicit default value
            assertEquals(2, component.getIntValue(2),
                    "Integer value should use provided default value when not set");
        }
    }
}