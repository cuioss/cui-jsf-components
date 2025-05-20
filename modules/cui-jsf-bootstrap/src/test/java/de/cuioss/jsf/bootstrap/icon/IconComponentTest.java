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
package de.cuioss.jsf.bootstrap.icon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.test.jsf.component.AbstractUiComponentTest;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@VerifyComponentProperties(of = {"icon", "size", "state", "titleKey", "titleValue"})
@DisplayName("Tests for IconComponent")
class IconComponentTest extends AbstractUiComponentTest<IconComponent> {

    private static final String TEST_ICON = "cui-icon-test";
    private static final String TEST_SIZE = "lg";
    private static final String TEST_STATE = "info";
    private static final String TEST_TITLE_KEY = "test.title.key";
    private static final String TEST_TITLE_VALUE = "Test Title";

    @Nested
    @DisplayName("Component metadata tests")
    class MetadataTests {

        @Test
        @DisplayName("Should provide correct renderer type")
        void shouldProvideCorrectRendererType() {
            // Arrange
            var component = anyComponent();

            // Act & Assert
            assertEquals(BootstrapFamily.ICON_COMPONENT_RENDERER, component.getRendererType(),
                    "Renderer type should match ICON_COMPONENT_RENDERER");
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
    }

    @Nested
    @DisplayName("Size property tests")
    class SizePropertyTests {

        @Test
        @DisplayName("Should set and get size property")
        void shouldSetAndGetSize() {
            // Arrange
            var component = anyComponent();

            // Act & Assert - Initial state
            assertNull(component.getSize(), "Size should initially be null");

            // Act
            component.setSize(TEST_SIZE);

            // Assert
            assertEquals(TEST_SIZE, component.getSize(), "Size should match the set value");
        }
    }

    @Nested
    @DisplayName("State property tests")
    class StatePropertyTests {

        @Test
        @DisplayName("Should set and get state property")
        void shouldSetAndGetState() {
            // Arrange
            var component = anyComponent();

            // Act & Assert - Initial state
            assertNull(component.getState(), "State should initially be null");

            // Act
            component.setState(TEST_STATE);

            // Assert
            assertEquals(TEST_STATE, component.getState(), "State should match the set value");
        }
    }

    @Nested
    @DisplayName("Title property tests")
    class TitlePropertyTests {

        @Test
        @DisplayName("Should set and get title key")
        void shouldSetAndGetTitleKey() {
            // Arrange
            var component = anyComponent();

            // Act & Assert - Initial state
            assertNull(component.getTitleKey(), "Title key should initially be null");

            // Act
            component.setTitleKey(TEST_TITLE_KEY);

            // Assert
            assertEquals(TEST_TITLE_KEY, component.getTitleKey(), "Title key should match the set value");
        }

        @Test
        @DisplayName("Should set and get title value")
        void shouldSetAndGetTitleValue() {
            // Arrange
            var component = anyComponent();

            // Act & Assert - Initial state
            assertNull(component.getTitleValue(), "Title value should initially be null");

            // Act
            component.setTitleValue(TEST_TITLE_VALUE);

            // Assert
            assertEquals(TEST_TITLE_VALUE, component.getTitleValue(), "Title value should match the set value");
        }
    }

}
