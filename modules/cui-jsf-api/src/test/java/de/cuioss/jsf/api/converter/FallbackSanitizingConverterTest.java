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
package de.cuioss.jsf.api.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.cuioss.test.jsf.config.decorator.ApplicationConfigDecorator;
import de.cuioss.test.jsf.junit5.EnableJsfEnvironment;
import de.cuioss.test.juli.LogAsserts;
import de.cuioss.test.juli.TestLogLevel;
import de.cuioss.test.juli.junit5.EnableTestLogger;
import jakarta.faces.application.ProjectStage;
import jakarta.faces.component.html.HtmlInputText;
import jakarta.faces.component.html.HtmlOutputText;
import jakarta.faces.context.FacesContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@EnableTestLogger
@EnableJsfEnvironment
@DisplayName("Tests for FallbackSanitizingConverter")
class FallbackSanitizingConverterTest {

    // HTML content with potentially dangerous script tag
    private static final String SCRIPT_TAG = "<script>";

    @Nested
    @DisplayName("Tests for input components")
    class InputComponentTests {

        @Test
        @DisplayName("Should not sanitize HTML for input components")
        void shouldNotSanitizeWhenInput(FacesContext facesContext) {
            // Arrange
            var component = new HtmlInputText();

            // Act
            var result = new FallbackSanitizingConverter().convertToString(facesContext, component, SCRIPT_TAG);

            // Assert
            assertEquals(SCRIPT_TAG, result,
                    "Input components should not have their content sanitized");
        }
    }

    @Nested
    @DisplayName("Tests for output components")
    class OutputComponentTests {

        @Test
        @DisplayName("Should not sanitize HTML for output components with escaping enabled")
        void shouldNotSanitizeWhenOutputAndEscape(FacesContext facesContext) {
            // Arrange
            var component = new HtmlOutputText();
            // Note: escape=true is the default

            // Act
            var result = new FallbackSanitizingConverter().convertToString(facesContext, component, SCRIPT_TAG);

            // Assert
            assertEquals(SCRIPT_TAG, result,
                    "Output components with escaping should not have their content sanitized");
        }

        @Test
        @DisplayName("Should sanitize HTML and log warning for output components with escaping disabled")
        void shouldSanitizeAndWarnWhenOutputAndNoEscape(FacesContext facesContext,
                ApplicationConfigDecorator applicationConfig) {
            // Arrange
            applicationConfig.setProjectStage(ProjectStage.Development);
            var component = new HtmlOutputText();
            component.setEscape(false);

            // Act
            var result = new FallbackSanitizingConverter().convertToString(facesContext, component, SCRIPT_TAG);

            // Assert
            assertEquals("", result,
                    "Output components without escaping should have dangerous content sanitized");
            LogAsserts.assertSingleLogMessagePresentContaining(TestLogLevel.WARN, "CUI-101");
        }
    }
}
