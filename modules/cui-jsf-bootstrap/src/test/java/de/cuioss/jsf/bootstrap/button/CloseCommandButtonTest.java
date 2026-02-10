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
package de.cuioss.jsf.bootstrap.button;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.AttributeValue;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.component.AbstractComponentTest;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@VerifyComponentProperties(of = {"titleKey", "titleValue", "styleClass"})
@JsfTestConfiguration(CoreJsfTestConfiguration.class)
@DisplayName("Tests for CloseCommandButton component")
class CloseCommandButtonTest extends AbstractComponentTest<CloseCommandButton> {

    @BeforeEach
    void setUp(ComponentConfigDecorator decorator) {
        CoreJsfTestConfiguration.configureComponents(decorator);
    }

    @Nested
    @DisplayName("Accessibility tests")
    class AccessibilityTests {

        @Test
        @DisplayName("Should provide ARIA label for accessibility")
        void shouldProvidePassThroughAttributes() {
            // Arrange
            var component = anyComponent();

            // Act
            var map = component.getPassThroughAttributes();

            // Assert
            assertTrue(map.containsKey(AttributeName.ARIA_LABEL.getContent()),
                    "Should contain ARIA label attribute");
            assertEquals(AttributeValue.ARIA_CLOSE.getContent(), map.get(AttributeName.ARIA_LABEL.getContent()),
                    "ARIA label should be 'Close'");
        }
    }

    @Nested
    @DisplayName("Metadata tests")
    class MetadataTests {

        @Test
        @DisplayName("Should provide correct component metadata")
        void shouldProvideCorrectMetadata() {
            // Arrange
            var component = anyComponent();

            // Act & Assert
            assertEquals(BootstrapFamily.COMPONENT_FAMILY, component.getFamily(),
                    "Component family should match");
            assertEquals(BootstrapFamily.CLOSE_COMMAND_BUTTON_RENDERER, component.getRendererType(),
                    "Renderer type should match");
        }
    }

}
