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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import de.cuioss.jsf.api.components.util.modifier.support.TitleProviderImpl;
import jakarta.faces.component.UIComponentBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for ComponentModifierFactory")
class ComponentModifierFactoryTest {

    @Nested
    @DisplayName("Tests for finding appropriate component modifiers")
    class ModifierResolutionTests {

        @Test
        @DisplayName("Should handle basic UIComponentBase with reflection-based modifier")
        void shouldHandleComponentBase() {
            // Arrange
            UIComponentBase component = new UIComponentBase() {
                @Override
                public String getFamily() {
                    return null;
                }
            };

            // Act
            var result = ComponentModifierFactory.findFittingWrapper(component);

            // Assert
            assertNotNull(result,
                    "Should find a modifier for basic UIComponentBase");
            assertEquals(ReflectionBasedModifier.class, result.getClass(),
                    "Should use ReflectionBasedModifier for basic UIComponentBase");
        }

        @Test
        @DisplayName("Should handle components implementing CUI interfaces with specialized modifier")
        void shouldHandleCuiInterface() {
            // Arrange
            UIComponentBase component = new TitleProviderImpl();

            // Act
            var result = ComponentModifierFactory.findFittingWrapper(component);

            // Assert
            assertNotNull(result,
                    "Should find a modifier for component implementing CUI interface");
            assertEquals(CuiInterfaceBasedModifier.class, result.getClass(),
                    "Should use CuiInterfaceBasedModifier for component implementing CUI interface");
        }
    }
}
