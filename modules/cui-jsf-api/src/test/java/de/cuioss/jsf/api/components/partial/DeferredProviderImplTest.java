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

@VerifyComponentProperties(of = "deferred")
@ExplicitParamInjection
@DisplayName("Tests for DeferredProvider implementation")
class DeferredProviderImplTest extends AbstractPartialComponentTest {

    @Test
    @DisplayName("Should throw NullPointerException when constructed with null")
    void shouldFailWithNullConstructor() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> new DeferredProvider(null),
                "Constructor should reject null component");
    }

    @Nested
    @DisplayName("Tests for deferred state management")
    class DeferredStateTests {

        @Test
        @DisplayName("Should default to not deferred when not set")
        void shouldDefaultToNotDeferred() {
            // Act & Assert
            assertFalse(anyComponent().isDeferred(),
                    "Component should not be deferred by default");
        }

        @Test
        @DisplayName("Should reflect deferred state when set to true")
        void shouldReflectDeferredStateWhenSet() {
            // Arrange
            var any = anyComponent();

            // Act
            any.setDeferred(true);

            // Assert
            assertTrue(any.isDeferred(),
                    "Component should be deferred when set to true");
        }
    }
}
