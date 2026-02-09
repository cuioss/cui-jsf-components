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

@VerifyComponentProperties(of = "styleClass")
@ExplicitParamInjection
@DisplayName("Tests for ComponentStyleClassProvider implementation")
class ComponentStyleClassProviderImplTest extends AbstractPartialComponentTest {

    @Test
    @DisplayName("Should throw NullPointerException when constructed with null")
    void shouldFailWithNullConstructor() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> new ComponentStyleClassProviderImpl(null),
                "Constructor should reject null component");
    }

    @Nested
    @DisplayName("Tests for style class builder")
    class StyleClassBuilderTests {

        @Test
        @DisplayName("Should provide empty builder when no style class is set")
        void shouldResolveBuilderForNoStyleClassSet() {
            // Act
            var builder = anyComponent().getStyleClassBuilder();

            // Assert
            assertNotNull(builder, "Style class builder should never be null");
            assertFalse(builder.isAvailable(), "Builder should not have available styles when none set");
        }

        @Test
        @DisplayName("Should provide builder with style class when set")
        void shouldResolveBuilderWithStyleClass() {
            // Arrange
            final var styleClass = "styleClassThingy";
            var any = anyComponent();

            // Act
            any.setStyleClass(styleClass);
            var builder = any.getStyleClassBuilder();

            // Assert
            assertNotNull(builder, "Style class builder should never be null");
            assertTrue(builder.isAvailable(), "Builder should have available styles when set");
            assertEquals(styleClass, builder.getStyleClass(),
                    "Builder should return the style class that was set");
        }
    }
}
