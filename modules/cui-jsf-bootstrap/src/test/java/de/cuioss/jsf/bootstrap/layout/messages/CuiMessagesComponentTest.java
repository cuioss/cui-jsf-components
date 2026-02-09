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
package de.cuioss.jsf.bootstrap.layout.messages;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import de.cuioss.jsf.bootstrap.CssCuiBootstrap;
import de.cuioss.test.jsf.component.AbstractComponentTest;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link CuiMessagesComponent} using the property verification from
 * {@link AbstractComponentTest}.
 */
@VerifyComponentProperties(of = {"rendered", "showDetail", "showSummary", "for"})
@DisplayName("Tests for CuiMessagesComponent")
class CuiMessagesComponentTest extends AbstractComponentTest<CuiMessagesComponent> {

    private static final String STYLE_CLASS = CssCuiBootstrap.CUI_MESSAGES_CLASS.getStyleClass();

    @Nested
    @DisplayName("Tests for style class handling")
    class StyleClassTests {

        @Test
        @DisplayName("Should provide default style classes")
        void shouldProvideDefaultClasses() {
            // Arrange
            var component = anyComponent();

            // Act & Assert
            assertEquals(STYLE_CLASS, component.getStyleClass(),
                    "Default style class should match CUI_MESSAGES_CLASS");
            assertNotNull(component.getFatalClass(), "Fatal class should not be null");
            assertNotNull(component.getWarnClass(), "Warn class should not be null");
            assertNotNull(component.getErrorClass(), "Error class should not be null");
            assertNotNull(component.getInfoClass(), "Info class should not be null");
        }

        @Test
        @DisplayName("Should append custom style class to default class")
        void shouldAppendCustomStyleClass() {
            // Arrange
            var component = anyComponent();

            // Act
            component.setStyleClass("added");

            // Assert
            assertEquals(STYLE_CLASS + " added", component.getStyleClass(),
                    "Custom style class should be appended to default class");
            assertNotNull(component.getFatalClass(), "Fatal class should not be null");
            assertNotNull(component.getWarnClass(), "Warn class should not be null");
            assertNotNull(component.getErrorClass(), "Error class should not be null");
            assertNotNull(component.getInfoClass(), "Info class should not be null");
        }
    }
}
