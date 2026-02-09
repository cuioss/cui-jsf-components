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

import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import org.jboss.weld.junit5.ExplicitParamInjection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@VerifyComponentProperties(of = "disabled")
@ExplicitParamInjection
@DisplayName("Tests for DisabledComponentProvider implementation")
class DisabledComponentProviderImplTest extends AbstractPartialComponentTest {

    @Test
    @DisplayName("Should throw NullPointerException when constructed with null")
    void shouldFailWithNullConstructor() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> new DisabledComponentProvider(null),
                "Constructor should reject null component");
    }

    @Nested
    @DisplayName("Tests for disabled state")
    class DisabledStateTests {

        @Test
        @DisplayName("Should default to not disabled when not set")
        void shouldDefaultToNotDisabled() {
            // Act & Assert
            assertFalse(anyComponent().isDisabled(),
                    "Component should not be disabled by default");
        }

        @Test
        @DisplayName("Should reflect disabled state when set to true")
        void shouldReflectDisabledStateWhenSet() {
            // Arrange
            var any = anyComponent();

            // Act
            any.setDisabled(true);

            // Assert
            assertTrue(any.isDisabled(),
                    "Component should be disabled when set to true");
        }
    }
}
