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

import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.test.jsf.component.AbstractUiComponentTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link FormGroupComponent}
 */
@DisplayName("Tests for FormGroupComponent")
class FormGroupComponentTest extends AbstractUiComponentTest<FormGroupComponent> {

    @Nested
    @DisplayName("Tests for component properties")
    class PropertyTests {

        @Test
        @DisplayName("Should provide correct style class")
        void shouldProvideCorrectStyleClass() {
            // Arrange
            final var component = anyComponent();
            final var expected = CssBootstrap.FORM_GROUP.getStyleClass();

            // Act & Assert
            assertEquals(expected, component.resolveStyleClass().getStyleClass(),
                    "Style class should match FORM_GROUP");
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
}
