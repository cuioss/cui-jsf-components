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

import java.io.Serializable;

@VerifyComponentProperties(of = "model")
@ExplicitParamInjection
@DisplayName("Tests for ModelProvider implementation")
class ModelProviderImplTest extends AbstractPartialComponentTest {

    @Test
    @DisplayName("Should throw NullPointerException when constructed with null")
    void shouldFailWithNullConstructor() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> new ModelProvider(null),
                "Constructor should reject null component");
    }

    @Nested
    @DisplayName("Tests for model state management")
    class ModelStateTests {

        @Test
        @DisplayName("Should return null when no model is set")
        void shouldReturnNullWhenNoModelSet() {
            // Arrange
            var any = anyComponent();

            // Act & Assert
            assertNull(any.getModel(),
                    "Model should be null when none is set");
        }

        @Test
        @DisplayName("Should store and retrieve model correctly")
        void shouldStoreAndRetrieveModel() {
            // Arrange
            var any = anyComponent();
            Serializable testModel = "Test Model";

            // Act
            any.setModel(testModel);

            // Assert
            assertEquals(testModel, any.getModel(),
                    "Should return the model that was set");
        }
    }
}
