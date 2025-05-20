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
package de.cuioss.jsf.api.components.util.modifier;

import static de.cuioss.jsf.api.components.util.modifier.ComponentModifierAssert.assertContracts;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.cuioss.test.jsf.junit5.EnableJsfEnvironment;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIComponentBase;
import jakarta.faces.component.html.HtmlInputText;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@EnableJsfEnvironment
@DisplayName("Tests for ReflectionBasedModifier")
class ReflectionBasedModifierTest {

    @Nested
    @DisplayName("Tests for component capability detection")
    class ComponentCapabilityTests {

        @Test
        @DisplayName("Should correctly identify capabilities of basic UIComponentBase")
        void shouldHandleUIComponentBase() {
            // Arrange
            UIComponentBase component = new UIComponentBase() {
                @Override
                public String getFamily() {
                    return null;
                }
            };

            // Act
            var modifier = new ReflectionBasedModifier(component);

            // Assert - basic contract
            assertContracts(modifier, component);

            // Assert - specific capabilities
            assertFalse(modifier.isSupportsDisabled(),
                    "UIComponentBase should not support disabled property");
            assertFalse(modifier.isSupportsLabel(),
                    "UIComponentBase should not support label property");
            assertFalse(modifier.isSupportsRole(),
                    "UIComponentBase should not support role property");
            assertFalse(modifier.isSupportsStyle(),
                    "UIComponentBase should not support style property");
            assertFalse(modifier.isSupportsStyleClass(),
                    "UIComponentBase should not support styleClass property");
            assertFalse(modifier.isSupportsTitle(),
                    "UIComponentBase should not support title property");
            assertFalse(modifier.isEditableValueHolder(),
                    "UIComponentBase should not be an editable value holder");
            assertFalse(modifier.isSupportsResetValue(),
                    "UIComponentBase should not support reset value");
        }

        @Test
        @DisplayName("Should correctly identify capabilities of HtmlInputText")
        void shouldHandleHtmlInput() {
            // Arrange
            UIComponent component = new HtmlInputText();

            // Act
            var modifier = new ReflectionBasedModifier(component);

            // Assert - basic contract
            assertContracts(modifier, component);

            // Assert - specific capabilities
            assertTrue(modifier.isSupportsDisabled(),
                    "HtmlInputText should support disabled property");
            assertTrue(modifier.isSupportsLabel(),
                    "HtmlInputText should support label property");
            assertTrue(modifier.isSupportsRole(),
                    "HtmlInputText should support role property");
            assertTrue(modifier.isSupportsStyle(),
                    "HtmlInputText should support style property");
            assertTrue(modifier.isSupportsStyleClass(),
                    "HtmlInputText should support styleClass property");
            assertTrue(modifier.isSupportsTitle(),
                    "HtmlInputText should support title property");
            assertTrue(modifier.isEditableValueHolder(),
                    "HtmlInputText should be an editable value holder");
            assertTrue(modifier.isSupportsResetValue(),
                    "HtmlInputText should support reset value");
        }
    }
}
