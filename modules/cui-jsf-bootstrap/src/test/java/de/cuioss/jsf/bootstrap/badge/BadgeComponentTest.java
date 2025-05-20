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
package de.cuioss.jsf.bootstrap.badge;

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.jsf.api.components.JsfHtmlComponent;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.test.jsf.component.AbstractComponentTest;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@VerifyComponentProperties(of = {"titleKey", "titleValue", "style", "escape"})
@DisplayName("Tests for BadgeComponent")
class BadgeComponentTest extends AbstractComponentTest<BadgeComponent> {

    @Test
    @DisplayName("Should provide correct metadata")
    void shouldProvideCorrectMetadata() {
        // Arrange & Act
        final var component = anyComponent();

        // Assert
        assertEquals(JsfHtmlComponent.SPAN.getRendererType(), component.getRendererType(),
                "Renderer type should match");
        assertEquals(JsfHtmlComponent.SPAN.getFamily(), component.getFamily(),
                "Component family should match");
    }

    @Nested
    @DisplayName("Tests for style classes")
    class StyleClassTests {

        @Test
        @DisplayName("Should have badge style class")
        void shouldHaveBadgeStyleClass() {
            // Arrange & Act
            final var component = anyComponent();

            // Assert
            assertNotNull(component.getStyleClass(), "Style class should not be null");
            assertTrue(component.getStyleClass().contains(CssBootstrap.BADGE.getStyleClass()),
                    "Style class should contain badge class");
        }

        @Test
        @DisplayName("Should append custom style class")
        void shouldAppendCustomStyleClass() {
            // Arrange
            final var component = anyComponent();
            final var customClass = "custom-class";

            // Act
            component.setStyleClass(customClass);

            // Assert
            assertNotNull(component.getStyleClass(), "Style class should not be null");
            assertTrue(component.getStyleClass().contains(CssBootstrap.BADGE.getStyleClass()),
                    "Style class should contain badge class");
            assertTrue(component.getStyleClass().contains(customClass),
                    "Style class should contain custom class");
        }
    }
}
