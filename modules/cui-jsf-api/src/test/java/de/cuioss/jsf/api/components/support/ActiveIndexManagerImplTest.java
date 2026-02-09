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
package de.cuioss.jsf.api.components.support;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;
import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.jsf.api.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
@DisplayName("Tests for ActiveIndexManagerImpl")
class ActiveIndexManagerImplTest {

    @Nested
    @DisplayName("Tests for default index handling")
    class DefaultIndexTests {

        @Test
        @DisplayName("Should reset to default indexes when requested")
        void shouldResetToDefaultIndexes() {
            // Arrange
            ActiveIndexManager underTest = new ActiveIndexManagerImpl(immutableList(1, 2));
            underTest.setActiveIndex(3, 4);

            // Assert - initial state
            assertTrue(underTest.getActiveIndexes().containsAll(immutableList(3, 4)),
                    "Active indexes should contain the set values");

            // Act - reset to default
            underTest.resetToDefaultIndex();

            // Assert - after reset
            assertTrue(underTest.getActiveIndexes().containsAll(immutableList(1, 2)),
                    "Active indexes should be reset to default values");
            assertEquals("1 2", underTest.getActiveIndexesString(),
                    "Active indexes string should match default values");

            // Act - change default and reset again
            underTest.setDefaultIndex(immutableList(5));
            underTest.resetToDefaultIndex();

            // Assert - after changing default
            assertEquals(immutableList(5), underTest.getActiveIndexes(),
                    "Active indexes should match the new default value");
        }
    }

    @Nested
    @DisplayName("Tests for active index manipulation")
    class ActiveIndexManipulationTests {

        @Test
        @DisplayName("Should handle various ways of setting active indexes")
        void shouldHandleSettingActiveIndexes() {
            // Arrange
            ActiveIndexManager underTest = new ActiveIndexManagerImpl(immutableList(5));

            // Assert - initial state
            assertTrue(underTest.hasActiveIndex(),
                    "Should have active index initially");

            // Act - clear active indexes
            underTest.setActiveIndex();

            // Assert - after clearing
            assertFalse(underTest.hasActiveIndex(),
                    "Should not have active index after clearing");

            // Act - set null list
            underTest.setActiveIndex((List<Integer>) null);

            // Assert - after setting null list
            assertFalse(underTest.hasActiveIndex(),
                    "Should not have active index after setting null list");

            // Act - set multiple indexes
            underTest.setActiveIndex(3, 4);

            // Assert - after setting multiple indexes
            assertTrue(underTest.getActiveIndexes().containsAll(immutableList(3, 4)),
                    "Active indexes should contain all set values");

            // Act - set null integer
            underTest.setActiveIndex((Integer) null);

            // Assert - after setting null integer
            assertNotNull(underTest.getActiveIndexes(),
                    "Active indexes list should never be null");
            assertFalse(underTest.hasActiveIndex(),
                    "Should not have active index after setting null integer");
        }
    }

    @Nested
    @DisplayName("Tests for toggling indexes")
    class ToggleIndexTests {

        @Test
        @DisplayName("Should toggle single index correctly")
        void shouldToggleSingleIndex() {
            // Arrange
            ActiveIndexManager underTest = new ActiveIndexManagerImpl(immutableList(5));

            // Assert - initial state
            assertEquals("5", underTest.getActiveIndexesString(),
                    "Initial active index string should be '5'");

            // Act - toggle once
            underTest.toggleSingleIndex();

            // Assert - after first toggle
            assertEquals("", underTest.getActiveIndexesString(),
                    "Active index string should be empty after toggle");

            // Act - set empty and toggle twice
            underTest.setActiveIndex();
            underTest.toggleSingleIndex();
            underTest.toggleSingleIndex();

            // Assert - after multiple toggles
            assertEquals("", underTest.getActiveIndexesString(),
                    "Active index string should remain empty after multiple toggles from empty state");
        }
    }
}
