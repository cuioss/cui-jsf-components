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
package de.cuioss.jsf.bootstrap.modal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.test.jsf.component.AbstractComponentTest;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@VerifyComponentProperties(of = {"closable", "footerKey", "footerValue", "headerKey", "headerValue", "size", "state",
        "style", "animation"})
@DisplayName("Tests for ModalDialogComponent")
class ModalDialogComponentTest extends AbstractComponentTest<ModalDialogComponent> {

    @Nested
    @DisplayName("Tests for component metadata")
    class ComponentMetadataTests {

        @Test
        @DisplayName("Should provide correct renderer type and component family")
        void shouldProvideCorrectMetadata() {
            // Arrange
            var component = anyComponent();

            // Act & Assert
            assertEquals(BootstrapFamily.MODAL_DIALOG_COMPONENT_RENDERER, component.getRendererType(),
                    "Renderer type should be MODAL_DIALOG_COMPONENT_RENDERER");
            assertEquals(BootstrapFamily.COMPONENT_FAMILY, component.getFamily(),
                    "Component family should be COMPONENT_FAMILY");
        }
    }

    @Nested
    @DisplayName("Tests for style class handling")
    class StyleClassTests {

        @Test
        @DisplayName("Should handle style class with various configurations")
        void shouldHandleStyleClassWithVariousConfigurations() {
            // Arrange
            var component = anyComponent();

            // Act & Assert - Default style class
            assertEquals("modal modal-default", component.getStyleClass(),
                    "Default style class should be 'modal modal-default'");

            // Act - Set size
            component.setSize("fluid");

            // Assert - Size should not affect style class
            assertEquals("modal modal-default", component.getStyleClass(),
                    "Size 'fluid' should not affect style class");

            // Act - Set custom style class
            component.setStyleClass("someStyleClass");

            // Assert - Custom style class should be appended
            assertEquals("modal modal-default someStyleClass", component.getStyleClass(),
                    "Custom style class should be appended");

            // Act - Set animation
            component.setAnimation("fade");

            // Assert - Animation should be included in style class
            assertEquals("modal modal-default fade someStyleClass", component.getStyleClass(),
                    "Animation 'fade' should be included in style class");
        }
    }

    @Nested
    @DisplayName("Tests for dialog ID resolution")
    class DialogIdTests {

        @Test
        @DisplayName("Should throw exception when ID is not set")
        void shouldThrowExceptionWhenIdIsNotSet() {
            // Arrange
            var component = anyComponent();

            // Act & Assert
            assertThrows(IllegalArgumentException.class, component::resolveDialogId,
                    "Should throw IllegalArgumentException when ID is not set");
        }

        @Test
        @DisplayName("Should resolve dialog ID to component ID")
        void shouldResolveDialogIdToComponentId() {
            // Arrange
            var component = anyComponent();
            component.setId("dialogid");

            // Act & Assert
            assertEquals("dialogid", component.resolveDialogId(),
                    "Dialog ID should resolve to component ID");
        }
    }
}
