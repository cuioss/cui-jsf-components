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
package de.cuioss.jsf.api.components.util;

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.test.jsf.junit5.EnableJsfEnvironment;
import jakarta.faces.component.html.HtmlForm;
import jakarta.faces.component.html.HtmlInputText;
import jakarta.faces.component.html.HtmlSelectOneMenu;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@EnableJsfEnvironment
@DisplayName("Tests for DisableUIComponentStrategy")
class DisableUIComponentStrategyTest {

    @Nested
    @DisplayName("Tests for error handling")
    class ErrorHandlingTests {

        @Test
        @DisplayName("Should throw NullPointerException for null component")
        void shouldRejectNullComponent() {
            // Act & Assert
            assertThrows(NullPointerException.class, () -> DisableUIComponentStrategy.disableComponent(null),
                    "Should throw NullPointerException when component is null");
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException for unsupported component")
        void shouldRejectUnsupportedComponent() {
            // Arrange
            final var component = new HtmlForm();

            // Act & Assert
            assertThrows(IllegalArgumentException.class, () -> DisableUIComponentStrategy.disableComponent(component),
                    "Should throw IllegalArgumentException when component is not supported");
        }
    }

    @Nested
    @DisplayName("Tests for component disabling")
    class ComponentDisablingTests {

        @Test
        @DisplayName("Should disable HtmlInputText component")
        void shouldDisableHtmlInputText() {
            // Arrange
            final var component = new HtmlInputText();

            // Assert - initial state
            assertFalse(component.isDisabled(),
                    "HtmlInputText should not be disabled initially");

            // Act
            DisableUIComponentStrategy.disableComponent(component);

            // Assert - after disabling
            assertTrue(component.isDisabled(),
                    "HtmlInputText should be disabled after calling disableComponent");
        }

        @Test
        @DisplayName("Should disable HtmlSelectOneMenu component")
        void shouldDisableHtmlSelectOneMenu() {
            // Arrange
            final var component = new HtmlSelectOneMenu();

            // Assert - initial state
            assertFalse(component.isDisabled(),
                    "HtmlSelectOneMenu should not be disabled initially");

            // Act
            DisableUIComponentStrategy.disableComponent(component);

            // Assert - after disabling
            assertTrue(component.isDisabled(),
                    "HtmlSelectOneMenu should be disabled after calling disableComponent");
        }
    }
}