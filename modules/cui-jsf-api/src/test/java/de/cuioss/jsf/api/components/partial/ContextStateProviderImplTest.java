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
package de.cuioss.jsf.api.components.partial;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.cuioss.jsf.api.components.css.ContextState;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import org.jboss.weld.junit5.ExplicitParamInjection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@VerifyComponentProperties(of = "state")
@ExplicitParamInjection
@DisplayName("Tests for ContextStateProvider implementation")
class ContextStateProviderImplTest extends AbstractPartialComponentTest {

    @Test
    @DisplayName("Should throw NullPointerException when constructed with null")
    void shouldFailWithNullConstructor() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> new ContextStateProvider(null),
                "Constructor should reject null component");
    }

    @Nested
    @DisplayName("Tests for state resolution")
    class StateResolutionTests {

        @Test
        @DisplayName("Should resolve default state when none is set")
        void shouldResolveDefaultForNoStateSet() {
            // Act & Assert
            assertEquals(ContextState.DEFAULT, anyComponent().resolveContextState(),
                    "Should return default state when none is set");
        }

        @Test
        @DisplayName("Should resolve specific state when set")
        void shouldResolveSpecificState() {
            // Arrange
            var any = anyComponent();

            // Act
            any.setState(ContextState.INFO.name());

            // Assert
            assertEquals(ContextState.INFO, any.resolveContextState(),
                    "Should return the specific state that was set");
        }
    }
}
