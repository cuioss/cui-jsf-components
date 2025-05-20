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
package de.cuioss.jsf.api.components.support;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.cuioss.jsf.api.components.partial.ComponentBridge;
import de.cuioss.jsf.api.components.partial.MockPartialComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for OneTimeCheck")
class OneTimeCheckTest {

    private ComponentBridge bridge;

    @BeforeEach
    void before() {
        bridge = new MockPartialComponent();
    }

    @Nested
    @DisplayName("Tests for initialization and default state")
    class InitializationTests {

        @Test
        @DisplayName("Should initialize with default state of false")
        void shouldInitializeWithDefaultStateFalse() {
            // Arrange & Act
            final var check = new OneTimeCheck(bridge);

            // Assert
            assertFalse(check.isChecked(),
                    "Check should initialize with false state");
        }
    }

    @Nested
    @DisplayName("Tests for multiple instances")
    class MultipleInstanceTests {

        @Test
        @DisplayName("Should allow multiple independent instances with different keys")
        void shouldSupportMultipleIndependentInstances() {
            // Arrange - create two instances with different keys
            final var checkOne = new OneTimeCheck(bridge, "one");
            final var checkTwo = new OneTimeCheck(bridge, "two");

            // Assert - initial state
            assertFalse(checkOne.isChecked(),
                    "First check should initialize with false state");
            assertFalse(checkTwo.isChecked(),
                    "Second check should initialize with false state");

            // Act - set first check to true
            checkOne.setChecked(true);

            // Assert - after setting first check
            assertTrue(checkOne.isChecked(),
                    "First check should be true after setting");
            assertFalse(checkTwo.isChecked(),
                    "Second check should remain false when first is set");

            // Act - set second check to true
            checkTwo.setChecked(true);

            // Assert - after setting second check
            assertTrue(checkOne.isChecked(),
                    "First check should remain true");
            assertTrue(checkTwo.isChecked(),
                    "Second check should be true after setting");

            // Act - set first check back to false
            checkOne.setChecked(false);

            // Assert - after setting first check to false
            assertFalse(checkOne.isChecked(),
                    "First check should be false after resetting");
            assertTrue(checkTwo.isChecked(),
                    "Second check should remain true when first is reset");
        }
    }

    @Nested
    @DisplayName("Tests for read and set operations")
    class ReadAndSetTests {

        @Test
        @DisplayName("Should read current state and set to true in one operation")
        void shouldReadCurrentStateAndSetToTrue() {
            // Arrange
            final var check = new OneTimeCheck(bridge);

            // Assert - initial state
            assertFalse(check.isChecked(),
                    "Check should initialize with false state");

            // Act & Assert - read and set operation
            assertFalse(check.readAndSetChecked(),
                    "readAndSetChecked should return the previous state (false)");

            // Assert - after read and set
            assertTrue(check.isChecked(),
                    "Check should be true after readAndSetChecked operation");
        }
    }
}
