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
package de.cuioss.jsf.bootstrap.button;

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.jsf.api.components.css.AlignHolder;
import de.cuioss.jsf.api.components.html.AttributeValue;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.test.generator.Generators;
import de.cuioss.test.jsf.component.AbstractComponentTest;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import jakarta.faces.event.PreRenderComponentEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@VerifyComponentProperties(of = {"icon", "size", "state", "titleKey", "titleValue", "iconAlign", "keyBinding", "styleClass"})
@JsfTestConfiguration(CoreJsfTestConfiguration.class)
@DisplayName("Tests for CommandButton component")
class CommandButtonTest extends AbstractComponentTest<CommandButton> {

    private static final String KEY_BINDING = AttributeValue.CUI_CLICK_BINDING.getContent();

    @Nested
    @DisplayName("Metadata tests")
    class MetadataTests {

        @Test
        @DisplayName("Should provide correct component metadata")
        void shouldProvideCorrectMetadata() {
            // Arrange & Act
            var component = anyComponent();

            // Assert
            assertEquals(BootstrapFamily.COMMAND_BUTTON_RENDERER, component.getRendererType(),
                    "Renderer type should match");
            assertEquals(BootstrapFamily.COMPONENT_FAMILY, component.getFamily(),
                    "Component family should match");
        }
    }

    @Nested
    @DisplayName("Value handling tests")
    class ValueHandlingTests {

        @Test
        @DisplayName("Should fail to set value directly")
        void shouldFailToSetValue() {
            // Arrange
            var component = anyComponent();

            // Act & Assert
            assertThrows(IllegalArgumentException.class, () -> component.setValue(""),
                    "Setting value directly should throw IllegalArgumentException");
        }

        @Test
        @DisplayName("Should resolve getValue from label")
        void shouldResolveGetValueFromLabel() {
            // Arrange
            var component = anyComponent();

            // Act & Assert - Initial state
            assertNull(component.getValue(), "Initial value should be null");

            // Arrange - Set label value
            var label = Generators.letterStrings().next();

            // Act
            component.setLabelValue(label);

            // Assert
            assertEquals(label, component.getValue(), "getValue should return the label value");
        }
    }

    @Nested
    @DisplayName("Title handling tests")
    class TitleHandlingTests {

        @Test
        @DisplayName("Should resolve getTitle from titleProvider")
        void shouldResolveGetTitleFromTitleProvider() {
            // Arrange
            var component = anyComponent();

            // Act & Assert - Initial state
            assertNull(component.getTitle(), "Initial title should be null");

            // Arrange - Set title value
            var title = Generators.letterStrings().next();

            // Act
            component.setTitleValue(title);

            // Assert
            assertEquals(title, component.getTitle(), "getTitle should return the title value");
        }
    }

    @Nested
    @DisplayName("Icon handling tests")
    class IconHandlingTests {

        @Test
        @DisplayName("Should determine right icon display")
        void shouldDetermineRightIcon() {
            // Arrange
            var component = anyComponent();

            // Act & Assert - Initial state
            assertFalse(component.isDisplayIconRight(), "Initially, right icon should not be displayed");

            // Arrange - Set icon
            component.setIcon("icon");

            // Act & Assert - With icon but default alignment
            assertFalse(component.isDisplayIconRight(), "With default alignment, right icon should not be displayed");

            // Arrange - Set right alignment
            component.setIconAlign(AlignHolder.RIGHT.name());

            // Act & Assert - With right alignment
            assertTrue(component.isDisplayIconRight(), "With right alignment, right icon should be displayed");
        }

        @Test
        @DisplayName("Should determine left icon display")
        void shouldDetermineLeftIcon() {
            // Arrange
            var component = anyComponent();

            // Act & Assert - Initial state
            assertFalse(component.isDisplayIconLeft(), "Initially, left icon should not be displayed");

            // Arrange - Set icon
            component.setIcon("icon");

            // Act & Assert - With icon and default alignment
            assertTrue(component.isDisplayIconLeft(), "With default alignment, left icon should be displayed");

            // Arrange - Set right alignment
            component.setIconAlign(AlignHolder.RIGHT.name());

            // Act & Assert - With right alignment
            assertFalse(component.isDisplayIconLeft(), "With right alignment, left icon should not be displayed");
        }
    }

    @Nested
    @DisplayName("Key binding tests")
    class KeyBindingTests {

        @Test
        @DisplayName("Should add key binding on pre-render")
        void shouldAddKeyBindingOnPreRender() {
            // Arrange
            var component = anyComponent();

            // Act & Assert - Initial state
            assertFalse(component.getPassThroughAttributes().containsKey(KEY_BINDING),
                    "Initially, key binding should not be in pass-through attributes");

            // Act - Process event without key binding
            component.processEvent(new PreRenderComponentEvent(component));

            // Assert
            assertFalse(component.getPassThroughAttributes().containsKey(KEY_BINDING),
                    "Without key binding set, it should not be added to pass-through attributes");

            // Arrange - Set key binding
            component.setKeyBinding("s");

            // Act - Process event with key binding
            component.processEvent(new PreRenderComponentEvent(component));

            // Assert
            assertTrue(component.getPassThroughAttributes().containsKey(KEY_BINDING),
                    "With key binding set, it should be added to pass-through attributes");
            assertEquals("s", component.getPassThroughAttributes().get(KEY_BINDING),
                    "Key binding value should match");
        }
    }
}
