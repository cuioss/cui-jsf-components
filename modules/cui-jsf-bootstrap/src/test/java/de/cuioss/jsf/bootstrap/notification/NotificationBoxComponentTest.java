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
package de.cuioss.jsf.bootstrap.notification;

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.test.jsf.component.AbstractUiComponentTest;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@VerifyComponentProperties(of = {"contentKey", "contentValue", "contentEscape", "state"})
@DisplayName("Tests for NotificationBoxComponent")
class NotificationBoxComponentTest extends AbstractUiComponentTest<NotificationBoxComponent> {

    @Nested
    @DisplayName("Tests for component metadata")
    class ComponentMetadataTests {

        @Test
        @DisplayName("Should provide correct component family and renderer type")
        void shouldProvideCorrectMetadata() {
            // Arrange
            var component = anyComponent();

            // Act & Assert
            assertEquals(BootstrapFamily.COMPONENT_FAMILY, component.getFamily(),
                    "Component family should be COMPONENT_FAMILY");
            assertEquals(BootstrapFamily.NOTIFICATION_BOX_RENDERER, component.getRendererType(),
                    "Renderer type should be NOTIFICATION_BOX_RENDERER");
        }
    }

    @Nested
    @DisplayName("Tests for dismissible behavior")
    class DismissibleBehaviorTests {

        @Test
        @DisplayName("Should have dismissible set to false by default")
        void shouldHaveDismissibleFalseByDefault() {
            // Arrange
            var component = anyComponent();

            // Act & Assert
            assertFalse(component.isDismissible(),
                    "Dismissible should be false by default");
        }

        @Test
        @DisplayName("Should set and get dismissible property correctly")
        void shouldSetAndGetDismissibleProperty() {
            // Arrange
            var component = anyComponent();

            // Act
            component.setDismissible(true);

            // Assert
            assertTrue(component.isDismissible(),
                    "Dismissible should be true after setting it");
        }
    }

    @Nested
    @DisplayName("Tests for dismissed state")
    class DismissedStateTests {

        @Test
        @DisplayName("Should have dismissed set to false by default")
        void shouldHaveDismissedFalseByDefault() {
            // Arrange
            var component = anyComponent();

            // Act & Assert
            assertFalse(component.isDismissed(),
                    "Dismissed should be false by default");
        }

        @Test
        @DisplayName("Should set and get dismissed property correctly")
        void shouldSetAndGetDismissedProperty() {
            // Arrange
            var component = anyComponent();

            // Act
            component.setDismissed(true);

            // Assert
            assertTrue(component.isDismissed(),
                    "Dismissed should be true after setting it");
        }
    }
}