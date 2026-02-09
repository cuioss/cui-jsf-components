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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.cuioss.jsf.api.components.css.ContextSize;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import org.jboss.weld.junit5.ExplicitParamInjection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@VerifyComponentProperties(of = "size")
@ExplicitParamInjection
@DisplayName("Tests for ContextSizeProvider implementation")
class ContextSizeProviderImplTest extends AbstractPartialComponentTest {

    @Test
    @DisplayName("Should throw NullPointerException when constructed with null")
    void shouldFailWithNullConstructor() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> new ContextSizeProvider(null),
                "Constructor should reject null component");
    }

    @Nested
    @DisplayName("Tests for size resolution")
    class SizeResolutionTests {

        @Test
        @DisplayName("Should resolve default size when none is set")
        void shouldResolveDefaultForNoSizeSet() {
            // Act & Assert
            assertEquals(ContextSize.DEFAULT, anyComponent().resolveContextSize(),
                    "Should return default size when none is set");
        }

        @Test
        @DisplayName("Should resolve specific size when set")
        void shouldResolveSpecificSize() {
            // Arrange
            var any = anyComponent();

            // Act
            any.setSize(ContextSize.XL.name());

            // Assert
            assertEquals(ContextSize.XL, any.resolveContextSize(),
                    "Should return the specific size that was set");
        }
    }
}
