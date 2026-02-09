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
package de.cuioss.jsf.bootstrap.checkbox;

import static de.cuioss.tools.collect.CollectionLiterals.immutableMap;
import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.test.generator.Generators;
import de.cuioss.test.jsf.component.AbstractComponentTest;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@VerifyComponentProperties(of = {"offTextValue", "offTextKey", "onTextValue", "onTextKey", "titleValue", "titleKey",
        "rendered", "disabled"})
@JsfTestConfiguration(CoreJsfTestConfiguration.class)
@DisplayName("Tests for SwitchComponent")
class SwitchComponentTest extends AbstractComponentTest<SwitchComponent> {

    @Nested
    @DisplayName("Metadata tests")
    class MetadataTests {

        @Test
        @DisplayName("Should provide correct component metadata")
        void shouldProvideCorrectMetadata() {
            // Arrange & Act
            var component = anyComponent();

            // Assert
            assertEquals(BootstrapFamily.SWITCH_RENDERER, component.getRendererType(),
                    "Renderer type should match");
            assertEquals(BootstrapFamily.COMPONENT_FAMILY, component.getFamily(),
                    "Component family should match");
        }
    }

    @Nested
    @DisplayName("Text resolution tests")
    class TextResolutionTests {

        @Test
        @DisplayName("Should resolve on/off text values")
        void shouldResolveText() {
            // Arrange
            var onText = Generators.nonEmptyStrings().next();
            var offText = Generators.nonEmptyStrings().next();
            var component = anyComponent();

            // Act
            component.setOnTextValue(onText);
            component.setOffTextValue(offText);

            // Assert
            assertEquals(offText, component.resolveOffText(), "Off text should match set value");
            assertEquals(onText, component.resolveOnText(), "On text should match set value");
        }

        @Test
        @DisplayName("Should resolve title text")
        void shouldResolveTitleText() {
            // Arrange
            var titleText = Generators.nonEmptyStrings().next();
            var component = anyComponent();

            // Act
            component.setTitleValue(titleText);

            // Assert
            assertEquals(titleText, component.resolveTitle(), "Title should match set value");
        }
    }

    @Nested
    @DisplayName("Style handling tests")
    class StyleHandlingTests {

        @Test
        @DisplayName("Should handle style property correctly")
        void shouldResolveStyle() {
            // Arrange
            var styleValue = Generators.nonEmptyStrings().next();
            var component = anyComponent();

            // Act
            component.setStyle(styleValue);

            // Assert
            assertNull(component.getStyle(), "getStyle() should return null");
            assertEquals(styleValue, component.resolveStyle(), "resolveStyle() should return the set value");
        }

        @Test
        @DisplayName("Should handle style class property correctly")
        void shouldResolveStyleClass() {
            // Arrange
            var styleClass = Generators.nonEmptyStrings().next().trim();
            var component = anyComponent();

            // Act
            component.setStyleClass(styleClass);

            // Assert
            assertNull(component.getStyleClass(), "getStyleClass() should return null");
            assertEquals(styleClass, component.getStyleClassBuilder().getStyleClass(),
                    "StyleClassBuilder should contain the set value");
        }

        @Test
        @DisplayName("Should resolve pass-through attributes correctly")
        void shouldResolvePassThroughAttributes() {
            // Arrange
            var component = anyComponent();

            // Act & Assert - Disabled false
            component.setDisabled(false);
            assertEquals(immutableMap("data-switch-disabled", "false"), component.resolvePassThroughAttributes(),
                    "Pass-through attributes should contain disabled=false");

            // Act & Assert - Disabled true
            component.setDisabled(true);
            assertEquals(immutableMap("data-switch-disabled", "true"), component.resolvePassThroughAttributes(),
                    "Pass-through attributes should contain disabled=true");
        }
    }

    @Nested
    @DisplayName("Selected state tests")
    class SelectedStateTests {

        @Test
        @DisplayName("Should handle null submitted value")
        void shouldResolveSelectedWithEmpty() {
            // Arrange
            var component = anyComponent();

            // Act
            component.setSubmittedValue(null);

            // Assert
            assertFalse(component.isSelected(), "Component should not be selected with null value");
        }

        @Test
        @DisplayName("Should handle Boolean submitted values")
        void shouldResolveSelectedWithBoolean() {
            // Arrange
            var component = anyComponent();

            // Act & Assert - True
            component.setSubmittedValue(Boolean.TRUE);
            assertTrue(component.isSelected(), "Component should be selected with Boolean.TRUE");

            // Act & Assert - False
            component.setSubmittedValue(Boolean.FALSE);
            assertFalse(component.isSelected(), "Component should not be selected with Boolean.FALSE");
        }

        @Test
        @DisplayName("Should handle String submitted values")
        void shouldResolveSelectedWithString() {
            // Arrange
            var component = anyComponent();

            // Act & Assert - "true"
            component.setSubmittedValue(Boolean.TRUE.toString());
            assertTrue(component.isSelected(), "Component should be selected with 'true' string");

            // Act & Assert - "false"
            component.setSubmittedValue(Boolean.FALSE.toString());
            assertFalse(component.isSelected(), "Component should not be selected with 'false' string");
        }

        @Test
        @DisplayName("Should handle other object types as submitted values")
        void shouldResolveSelectedWithOther() {
            // Arrange
            var component = anyComponent();

            // Act
            component.setSubmittedValue(Generators.runtimeExceptions().next());

            // Assert
            assertFalse(component.isSelected(), "Component should not be selected with non-boolean object");
        }
    }
}
