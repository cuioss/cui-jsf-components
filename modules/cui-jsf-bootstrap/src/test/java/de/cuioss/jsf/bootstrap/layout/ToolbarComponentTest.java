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
package de.cuioss.jsf.bootstrap.layout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.test.jsf.component.AbstractUiComponentTest;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import jakarta.faces.event.PreRenderComponentEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link ToolbarComponent}
 */
@VerifyComponentProperties(of = {"pinBottomOffset", "pinTopOffset", "pinToBottom", "pinToTop"}, defaultValued = {
        "pinBottomOffset", "pinTopOffset"})
@DisplayName("Tests for ToolbarComponent")
class ToolbarComponentTest extends AbstractUiComponentTest<ToolbarComponent> {

    @Nested
    @DisplayName("Tests for style class resolution")
    class StyleClassTests {

        @Test
        @DisplayName("Should provide correct default style class")
        void shouldProvideCorrectStyleClass() {
            // Arrange
            final var component = anyComponent();

            // Act & Assert
            assertEquals("toolbar toolbar-default", component.resolveStyleClass().getStyleClass(),
                    "Default style class should be 'toolbar toolbar-default'");
        }

        @Test
        @DisplayName("Should provide complex style class with various configurations")
        void shouldProvideComplexStyleClass() {
            // Arrange
            final var component = anyComponent();
            var expected = "toolbar pinned toolbar-xl";

            // Act - Set pinToTop and size
            component.setPinToTop(true);
            component.setSize("xl");

            // Assert - With pinToTop=true
            assertEquals(expected, component.resolveStyleClass().getStyleClass(),
                    "Style class should include 'pinned' when pinToTop is true");

            // Act - Add pinToBottom=true
            component.setPinToBottom(true);

            // Assert - With both pins set
            assertEquals(expected, component.resolveStyleClass().getStyleClass(),
                    "Style class should remain the same with both pins set");

            // Act - Set pinToTop=false
            component.setPinToTop(false);

            // Assert - With only pinToBottom=true
            assertEquals(expected, component.resolveStyleClass().getStyleClass(),
                    "Style class should remain the same with only pinToBottom set");
        }
    }

    @Nested
    @DisplayName("Tests for component metadata")
    class MetadataTests {

        @Test
        @DisplayName("Should provide correct renderer type")
        void shouldProvideCorrectMetadata() {
            // Arrange
            var component = anyComponent();

            // Act & Assert
            assertEquals(BootstrapFamily.LAYOUT_RENDERER, component.getRendererType(),
                    "Renderer type should be LAYOUT_RENDERER");
        }
    }

    @Nested
    @DisplayName("Tests for pass-through attributes")
    class PassThroughAttributeTests {

        @Test
        @DisplayName("Should not set pass-through attributes by default")
        void shouldNotSetPTAttributes() {
            // Arrange
            var component = anyComponent();

            // Act
            component.processEvent(new PreRenderComponentEvent(component));

            // Assert
            assertTrue(component.getPassThroughAttributes().isEmpty(),
                    "Pass-through attributes should be empty by default");
        }

        @Test
        @DisplayName("Should handle pass-through attributes for top pinning")
        void shouldHandlePTAttributesForTop() {
            // Arrange
            var component = anyComponent();
            component.setPinToTop(true);
            component.setPinTopOffset(10);

            // Act
            component.processEvent(new PreRenderComponentEvent(component));

            // Assert
            var map = component.getPassThroughAttributes();
            assertEquals(2, map.size(), "Should have 2 pass-through attributes");
            assertEquals(ToolbarComponent.DATA_SPY_VALUE, map.get(ToolbarComponent.DATA_SPY_ATTRIBUTE),
                    "Should have correct data-spy attribute");
            assertEquals(10, map.get(ToolbarComponent.DATA_OFFSET_TOP),
                    "Should have correct data-offset-top attribute");
        }

        @Test
        @DisplayName("Should handle pass-through attributes for bottom pinning")
        void shouldHandlePTAttributesForBottom() {
            // Arrange
            var component = anyComponent();
            component.setPinToBottom(true);
            component.setPinBottomOffset(10);

            // Act
            component.processEvent(new PreRenderComponentEvent(component));

            // Assert
            var map = component.getPassThroughAttributes();
            assertEquals(2, map.size(), "Should have 2 pass-through attributes");
            assertEquals(ToolbarComponent.DATA_SPY_VALUE, map.get(ToolbarComponent.DATA_SPY_ATTRIBUTE),
                    "Should have correct data-spy attribute");
            assertEquals(10, map.get(ToolbarComponent.DATA_OFFSET_BOTTOM),
                    "Should have correct data-offset-bottom attribute");
        }
    }
}
