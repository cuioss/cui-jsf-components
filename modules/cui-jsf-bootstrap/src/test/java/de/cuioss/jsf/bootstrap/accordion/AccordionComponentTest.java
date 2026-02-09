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
package de.cuioss.jsf.bootstrap.accordion;

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.jsf.api.components.support.ActiveIndexManagerImpl;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.test.jsf.component.AbstractUiComponentTest;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

@DisplayName("Tests for AccordionComponent")
@VerifyComponentProperties(of = {"multiselectable"})
class AccordionComponentTest extends AbstractUiComponentTest<AccordionComponent> {

    @Test
    @DisplayName("Should provide correct renderer type")
    void shouldProvideCorrectMetadata() {
        // Arrange & Act & Assert
        assertEquals(BootstrapFamily.ACCORDION_RENDERER, anyComponent().getRendererType());
    }

    @Nested
    @DisplayName("Tests for multiselectable property")
    class MultiselectableTests {

        @Test
        @DisplayName("Should default to false")
        void shouldDefaultToFalse() {
            // Arrange & Act
            final var component = anyComponent();

            // Assert
            assertFalse(component.isMultiselectable(), "Multiselectable should default to false");
        }

        @Test
        @DisplayName("Should set and get multiselectable property")
        void shouldSetAndGetMultiselectable() {
            // Arrange
            final var component = anyComponent();

            // Act
            component.setMultiselectable(true);

            // Assert
            assertTrue(component.isMultiselectable(), "Multiselectable should be set to true");
        }
    }

    @Nested
    @DisplayName("Tests for activeIndexes property")
    class ActiveIndexesTests {

        @Test
        @DisplayName("Should default to list with 0")
        void shouldDefaultToListWithZero() {
            // Arrange & Act
            final var component = anyComponent();

            // Assert
            assertEquals(List.of(0), component.resolveActiveIndexes(), "ActiveIndexes should default to list with 0");
        }

        @Test
        @DisplayName("Should set and get activeIndexManager property")
        void shouldSetAndGetActiveIndexManager() {
            // Arrange
            final var component = anyComponent();
            final var indexes = List.of(1, 2, 3);
            final var manager = new ActiveIndexManagerImpl(indexes);

            // Act
            component.setActiveIndexManager(manager);

            // Assert
            assertNotNull(component.getActiveIndexManager(), "ActiveIndexManager should not be null");
            assertEquals(indexes, component.resolveActiveIndexes(), "ActiveIndexes should match the set values");
        }
    }
}
