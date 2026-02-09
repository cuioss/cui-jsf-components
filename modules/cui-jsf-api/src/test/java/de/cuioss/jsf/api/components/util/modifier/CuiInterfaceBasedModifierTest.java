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
package de.cuioss.jsf.api.components.util.modifier;

import static de.cuioss.jsf.api.components.util.modifier.ComponentModifierAssert.assertContracts;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.cuioss.jsf.api.components.util.modifier.support.StyleClassProvider;
import de.cuioss.jsf.api.components.util.modifier.support.StyleProvider;
import de.cuioss.jsf.api.components.util.modifier.support.TitleProviderImpl;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.html.HtmlInputText;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for CuiInterfaceBasedModifier")
class CuiInterfaceBasedModifierTest {

    @Nested
    @DisplayName("Tests for wrapping components with CUI interfaces")
    class CuiInterfaceWrappingTests {

        @Test
        @DisplayName("Should correctly wrap components implementing TitleProvider")
        void shouldHandleTitleProvider() {
            // Arrange
            UIComponent component = new TitleProviderImpl();

            // Act
            var componentModifier = CuiInterfaceBasedModifier.wrap(component);

            // Assert - modifier presence
            assertTrue(componentModifier.isPresent(),
                    "Should return a present Optional for TitleProvider component");

            // Assert - modifier capabilities
            var modifier = componentModifier.get();
            assertContracts(modifier, component);
            assertFalse(modifier.isSupportsDisabled(),
                    "TitleProvider should not support disabled property");
            assertFalse(modifier.isSupportsLabel(),
                    "TitleProvider should not support label property");
            assertFalse(modifier.isSupportsRole(),
                    "TitleProvider should not support role property");
            assertFalse(modifier.isSupportsStyle(),
                    "TitleProvider should not support style property");
            assertFalse(modifier.isSupportsStyleClass(),
                    "TitleProvider should not support styleClass property");
            assertTrue(modifier.isSupportsTitle(),
                    "TitleProvider should support title property");
        }

        @Test
        @DisplayName("Should correctly wrap components implementing StyleProvider")
        void shouldHandleStyleProvider() {
            // Arrange
            UIComponent component = new StyleProvider();

            // Act
            var componentModifier = CuiInterfaceBasedModifier.wrap(component);

            // Assert - modifier presence
            assertTrue(componentModifier.isPresent(),
                    "Should return a present Optional for StyleProvider component");

            // Assert - modifier capabilities
            var modifier = componentModifier.get();
            assertContracts(modifier, component);
            assertFalse(modifier.isSupportsDisabled(),
                    "StyleProvider should not support disabled property");
            assertFalse(modifier.isSupportsLabel(),
                    "StyleProvider should not support label property");
            assertFalse(modifier.isSupportsRole(),
                    "StyleProvider should not support role property");
            assertTrue(modifier.isSupportsStyle(),
                    "StyleProvider should support style property");
            assertFalse(modifier.isSupportsStyleClass(),
                    "StyleProvider should not support styleClass property");
            assertFalse(modifier.isSupportsTitle(),
                    "StyleProvider should not support title property");
        }

        @Test
        @DisplayName("Should correctly wrap components implementing StyleClassProvider")
        void shouldHandleStyleClassProvider() {
            // Arrange
            UIComponent component = new StyleClassProvider();

            // Act
            var componentModifier = CuiInterfaceBasedModifier.wrap(component);

            // Assert - modifier presence
            assertTrue(componentModifier.isPresent(),
                    "Should return a present Optional for StyleClassProvider component");

            // Assert - modifier capabilities
            var modifier = componentModifier.get();
            assertContracts(modifier, component);
            assertFalse(modifier.isSupportsDisabled(),
                    "StyleClassProvider should not support disabled property");
            assertFalse(modifier.isSupportsLabel(),
                    "StyleClassProvider should not support label property");
            assertFalse(modifier.isSupportsRole(),
                    "StyleClassProvider should not support role property");
            assertFalse(modifier.isSupportsStyle(),
                    "StyleClassProvider should not support style property");
            assertTrue(modifier.isSupportsStyleClass(),
                    "StyleClassProvider should support styleClass property");
            assertFalse(modifier.isSupportsTitle(),
                    "StyleClassProvider should not support title property");
        }
    }

    @Nested
    @DisplayName("Tests for handling non-CUI components")
    class NonCuiComponentTests {

        @Test
        @DisplayName("Should return empty Optional for non-CUI components")
        void shouldIgnoreNonCuiComponent() {
            // Arrange & Act
            var result = CuiInterfaceBasedModifier.wrap(new HtmlInputText());

            // Assert
            assertFalse(result.isPresent(),
                    "Should return an empty Optional for standard JSF components");
        }
    }
}
