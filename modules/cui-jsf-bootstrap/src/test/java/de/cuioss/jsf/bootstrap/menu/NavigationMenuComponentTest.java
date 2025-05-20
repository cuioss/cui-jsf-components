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
package de.cuioss.jsf.bootstrap.menu;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;
import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.jsf.api.components.model.menu.NavigationMenuItemSingleImpl;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.component.AbstractUiComponentTest;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link NavigationMenuComponent}
 */
@JsfTestConfiguration(CoreJsfTestConfiguration.class)
@DisplayName("Tests for NavigationMenuComponent")
class NavigationMenuComponentTest extends AbstractUiComponentTest<NavigationMenuComponent> {

    @Nested
    @DisplayName("Tests for rendered state resolution")
    class RenderedStateTests {

        @Test
        @DisplayName("Should resolve rendered state based on model")
        void resolveRenderedModelTest() {
            // Arrange
            var underTest = new NavigationMenuComponent();

            // Act & Assert - Initial state
            assertFalse(underTest.resolveRendered(),
                    "Component should not be rendered without model");

            // Act - Set model
            underTest.setModel(new NavigationMenuItemSingleImpl(10));

            // Assert - With model
            assertTrue(underTest.resolveRendered(),
                    "Component should be rendered with model");

            // Act - Set rendered=false
            underTest.setRendered(false);

            // Assert - With rendered=false
            assertFalse(underTest.resolveRendered(),
                    "Component should not be rendered when rendered=false regardless of model");
        }

        @Test
        @DisplayName("Should resolve rendered state based on modelItems")
        void resolveRenderedTest() {
            // Arrange
            var underTest = new NavigationMenuComponent();

            // Act & Assert - Initial state
            assertFalse(underTest.resolveRendered(),
                    "Component should not be rendered without modelItems");

            // Act - Set modelItems
            underTest.setModelItems(immutableList(new NavigationMenuItemSingleImpl(10)));

            // Assert - With modelItems
            assertTrue(underTest.resolveRendered(),
                    "Component should be rendered with modelItems");

            // Act - Set rendered=false
            underTest.setRendered(false);

            // Assert - With rendered=false
            assertFalse(underTest.resolveRendered(),
                    "Component should not be rendered when rendered=false regardless of modelItems");
        }
    }

    @Nested
    @DisplayName("Tests for model items resolution")
    class ModelItemsTests {

        @Test
        @DisplayName("Should resolve model items from different sources")
        void resolveModelItemsTest() {
            // Arrange
            var underTest = new NavigationMenuComponent();

            // Act & Assert - Initial state
            assertNull(underTest.resolveModelItems(),
                    "Model items should be null initially");

            // Act - Set model
            underTest.setModel(new NavigationMenuItemSingleImpl(10));

            // Assert - With model
            assertNotNull(underTest.resolveModelItems(),
                    "Model items should be resolved from model");

            // Act - Clear model and set modelItems directly
            underTest.setModel(null);
            underTest.setModelItems(immutableList(new NavigationMenuItemSingleImpl(10)));

            // Assert - With modelItems
            assertNotNull(underTest.resolveModelItems(),
                    "Model items should be resolved from modelItems");
        }
    }
}
