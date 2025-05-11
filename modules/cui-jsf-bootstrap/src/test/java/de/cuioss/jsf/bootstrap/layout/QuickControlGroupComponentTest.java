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
package de.cuioss.jsf.bootstrap.layout;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.test.jsf.component.AbstractUiComponentTest;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@VerifyComponentProperties(of = {"align"})
@DisplayName("Tests for QuickControlGroupComponent")
class QuickControlGroupComponentTest extends AbstractUiComponentTest<QuickControlGroupComponent> {

    @Nested
    @DisplayName("Tests for alignment handling")
    class AlignmentTests {

        @Test
        @DisplayName("Should use right alignment by default")
        void ensureDefaultAlignment() {
            // Arrange
            var component = anyComponent();

            // Act & Assert
            assertEquals(CssBootstrap.QUICK_CONTROL_GROUP_RIGHT.getStyleClass(),
                    component.resolveStyleClass().getStyleClass(),
                    "Default alignment should be right");
        }

        @Test
        @DisplayName("Should handle different alignment values correctly")
        void ensureSetAlignment() {
            // Arrange
            var component = anyComponent();

            // Act & Assert - Invalid value should fallback to default
            component.setAlign("fallback-to-default");
            assertEquals(CssBootstrap.QUICK_CONTROL_GROUP_RIGHT.getStyleClass(),
                    component.resolveStyleClass().getStyleClass(),
                    "Invalid alignment should fallback to right");

            // Act & Assert - Left alignment
            component.setAlign("left");
            assertEquals(CssBootstrap.QUICK_CONTROL_GROUP_LEFT.getStyleClass(),
                    component.resolveStyleClass().getStyleClass(),
                    "Alignment should be left when set to 'left'");

            // Act & Assert - Right alignment
            component.setAlign("right");
            assertEquals(CssBootstrap.QUICK_CONTROL_GROUP_RIGHT.getStyleClass(),
                    component.resolveStyleClass().getStyleClass(),
                    "Alignment should be right when set to 'right'");
        }

        @Test
        @DisplayName("Should combine style class with alignment correctly")
        void ensureStyleClassWithAlignment() {
            // Arrange
            var component = anyComponent();
            component.setAlign("left");
            component.setStyleClass("foo");

            // Act & Assert
            assertEquals(CssBootstrap.QUICK_CONTROL_GROUP_LEFT.getStyleClassBuilder().append("foo").getStyleClass(),
                    component.resolveStyleClass().getStyleClass(),
                    "Style class should be combined with alignment");
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
}
