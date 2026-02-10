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
import static org.junit.jupiter.api.Assertions.assertNotNull;

import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.jsf.test.EnableJSFCDIEnvironment;
import de.cuioss.jsf.test.EnableResourceBundleSupport;
import de.cuioss.test.jsf.component.AbstractUiComponentTest;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.juli.junit5.EnableTestLogger;
import org.jboss.weld.junit5.ExplicitParamInjection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
@EnableTestLogger
@EnableJSFCDIEnvironment
@EnableResourceBundleSupport
@ExplicitParamInjection
@DisplayName("Tests for InputGroupComponent")
class InputGroupComponentTest extends AbstractUiComponentTest<InputGroupComponent> {

    @BeforeEach
    void configureComponents(ComponentConfigDecorator decorator) {
        CoreJsfTestConfiguration.configureComponents(decorator);
        decorator.registerUIComponent(InputGroupComponent.class)
                .registerMockRenderer(BootstrapFamily.COMPONENT_FAMILY, BootstrapFamily.LAYOUT_RENDERER);
    }

    @Nested
    @DisplayName("Tests for component properties")
    class PropertyTests {

        @Test
        @DisplayName("Should provide correct style class")
        void shouldProvideCorrectStyleClass() {
            // Arrange
            final var component = anyComponent();
            final var expected = CssBootstrap.INPUT_GROUP.getStyleClass();

            // Act & Assert
            assertEquals(expected, component.resolveStyleClass().getStyleClass(),
                    "Style class should match INPUT_GROUP");
        }

        @Test
        @DisplayName("Should provide correct renderer type")
        void shouldProvideCorrectRendererType() {
            // Arrange
            final var component = anyComponent();

            // Act & Assert
            assertEquals(BootstrapFamily.LAYOUT_RENDERER, component.getRendererType(),
                    "Renderer type should match LAYOUT_RENDERER");
        }
    }

    @Nested
    @DisplayName("Tests for component initialization")
    class InitializationTests {

        @Test
        @DisplayName("Should initialize correctly")
        void shouldInitializeCorrectly() {
            // Arrange
            final var component = anyComponent();

            // Act & Assert
            assertNotNull(component, "Component should be created");
            assertEquals(BootstrapFamily.COMPONENT_FAMILY, component.getFamily(),
                    "Component family should be correct");
        }
    }
}
