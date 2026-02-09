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
package de.cuioss.jsf.api.components.partial;

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.jsf.api.components.html.AttributeValue;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import org.jboss.weld.junit5.ExplicitParamInjection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@VerifyComponentProperties(of = "keyBinding")
@ExplicitParamInjection
@DisplayName("Tests for KeyBindingProvider implementation")
class KeyBindingProviderTest extends AbstractPartialComponentTest {

    public static final String BINDING_VALUE = "binding-value";

    @Nested
    @DisplayName("Tests for pass-through attribute handling")
    class PassThroughAttributeTests {

        @Test
        @DisplayName("Should write key binding to pass-through attributes when binding is set")
        void shouldWriteBindingToPassThroughAttributes() {
            // Arrange
            var any = anyComponent();

            // Assert - initial state
            assertFalse(any.getPassThroughAttributes(true).containsKey(AttributeValue.CUI_CLICK_BINDING.getContent()),
                    "Pass-through attributes should not contain binding initially");

            // Act - set binding and write to pass-through
            any.setKeyBinding(BINDING_VALUE);
            any.writeBindingToPassThroughAttributes(any);

            // Assert - after writing binding
            assertTrue(any.getPassThroughAttributes(true).containsKey(AttributeValue.CUI_CLICK_BINDING.getContent()),
                    "Pass-through attributes should contain binding after writing");
            assertEquals(BINDING_VALUE, any.getPassThroughAttributes().get(AttributeValue.CUI_CLICK_BINDING.getContent()),
                    "Pass-through attribute value should match the set binding value");
        }

        @Test
        @DisplayName("Should not write to pass-through attributes when no binding is set")
        void shouldNotWriteToPassThroughWhenNoBindingSet() {
            // Arrange
            var any = anyComponent();

            // Assert - initial state
            assertFalse(any.getPassThroughAttributes(true).containsKey(AttributeValue.CUI_CLICK_BINDING.getContent()),
                    "Pass-through attributes should not contain binding initially");

            // Act - attempt to write to pass-through without setting binding
            any.writeBindingToPassThroughAttributes(any);

            // Assert - after writing attempt
            assertFalse(any.getPassThroughAttributes(true).containsKey(AttributeValue.CUI_CLICK_BINDING.getContent()),
                    "Pass-through attributes should still not contain binding when none was set");
        }
    }
}
