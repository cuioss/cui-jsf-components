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
package de.cuioss.jsf.bootstrap.icon;

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.jsf.api.components.css.AlignHolder;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.test.jsf.component.AbstractUiComponentTest;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@VerifyComponentProperties(of = {"labelKey", "labelValue", "labelEscape", "iconAlign", "icon", "titleKey",
        "titleValue"})
@DisplayName("Tests for LabeledIconComponent")
class LabeledIconComponentTest extends AbstractUiComponentTest<LabeledIconComponent> {

    private static final String TEST_ICON = "cui-icon-test";
    private static final String TEST_LABEL = "Test Label";
    private static final String TEST_TITLE = "Test Title";

    @Nested
    @DisplayName("Component metadata tests")
    class MetadataTests {

        @Test
        @DisplayName("Should provide correct renderer type")
        void shouldProvideCorrectRendererType() {
            // Arrange
            var component = anyComponent();

            // Act & Assert
            assertEquals(BootstrapFamily.LABELED_ICON_COMPONENT_RENDERER, component.getRendererType(),
                    "Renderer type should match LABELED_ICON_COMPONENT_RENDERER");
        }

        @Test
        @DisplayName("Should provide correct component family")
        void shouldProvideCorrectComponentFamily() {
            // Arrange
            var component = anyComponent();

            // Act & Assert
            assertEquals(BootstrapFamily.COMPONENT_FAMILY, component.getFamily(),
                    "Component family should match COMPONENT_FAMILY");
        }
    }

    @Nested
    @DisplayName("Label property tests")
    class LabelPropertyTests {

        @Test
        @DisplayName("Should set and get label value")
        void shouldSetAndGetLabelValue() {
            // Arrange
            var component = anyComponent();

            // Act & Assert - Initial state
            assertNull(component.getLabelValue(), "Label value should initially be null");

            // Act
            component.setLabelValue(TEST_LABEL);

            // Assert
            assertEquals(TEST_LABEL, component.getLabelValue(), "Label value should match the set value");
        }

        @Test
        @DisplayName("Should set and get label escape property")
        void shouldSetAndGetLabelEscape() {
            // Arrange
            var component = anyComponent();

            // Act & Assert - Initial state
            assertTrue(component.isLabelEscape(), "Label escape should default to true");

            // Act
            component.setLabelEscape(false);

            // Assert
            assertFalse(component.isLabelEscape(), "Label escape should match the set value");
        }

    }

    @Nested
    @DisplayName("Icon property tests")
    class IconPropertyTests {

        @Test
        @DisplayName("Should set and get icon property")
        void shouldSetAndGetIcon() {
            // Arrange
            var component = anyComponent();

            // Act & Assert - Initial state
            assertNull(component.getIcon(), "Icon should initially be null");

            // Act
            component.setIcon(TEST_ICON);

            // Assert
            assertEquals(TEST_ICON, component.getIcon(), "Icon should match the set value");
        }

        @Test
        @DisplayName("Should set and get icon align property")
        void shouldSetAndGetIconAlign() {
            // Arrange
            var component = anyComponent();

            // Act & Assert - Initial state
            assertNull(component.getIconAlign(), "Icon align should initially be null");

            // Act
            component.setIconAlign(AlignHolder.RIGHT.name());

            // Assert
            assertEquals(AlignHolder.RIGHT.name(), component.getIconAlign(),
                    "Icon align should match the set value");
        }

        @Test
        @DisplayName("Should resolve icon align with default value")
        void shouldResolveIconAlign() {
            // Arrange
            var component = anyComponent();

            // Act & Assert - Default value
            assertEquals(AlignHolder.DEFAULT, component.resolveIconAlign(),
                    "Default icon align should be DEFAULT");

            // Act
            component.setIconAlign(AlignHolder.RIGHT.name());

            // Assert
            assertEquals(AlignHolder.RIGHT, component.resolveIconAlign(),
                    "Resolved icon align should match the set value");
        }
    }

    @Nested
    @DisplayName("Title property tests")
    class TitlePropertyTests {

        @Test
        @DisplayName("Should set and get title value")
        void shouldSetAndGetTitleValue() {
            // Arrange
            var component = anyComponent();

            // Act & Assert - Initial state
            assertNull(component.getTitleValue(), "Title value should initially be null");

            // Act
            component.setTitleValue(TEST_TITLE);

            // Assert
            assertEquals(TEST_TITLE, component.getTitleValue(), "Title value should match the set value");
        }

    }
}
