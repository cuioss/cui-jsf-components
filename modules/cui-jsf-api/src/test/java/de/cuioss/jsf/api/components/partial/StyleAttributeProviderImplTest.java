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

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import org.jboss.weld.junit5.ExplicitParamInjection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@VerifyComponentProperties(of = "style")
@ExplicitParamInjection
@DisplayName("Tests for StyleAttributeProviderImpl implementation")
class StyleAttributeProviderImplTest extends AbstractPartialComponentTest {

    @Test
    @DisplayName("Should throw NullPointerException when constructed with null")
    void shouldFailWithNullConstructor() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> new StyleAttributeProviderImpl(null),
                "Constructor should reject null component");
    }

    @Nested
    @DisplayName("Tests for style attribute management")
    class StyleAttributeTests {

        @Test
        @DisplayName("Should return null when no style is set")
        void shouldReturnNullWhenNoStyleSet() {
            // Arrange
            var any = anyComponent();

            // Act & Assert
            assertNull(any.getStyle(),
                    "Style should be null when none is set");
        }

        @Test
        @DisplayName("Should store and retrieve style correctly")
        void shouldStoreAndRetrieveStyle() {
            // Arrange
            var any = anyComponent();
            String testStyle = "color: red; font-weight: bold;";

            // Act
            any.setStyle(testStyle);

            // Assert
            assertEquals(testStyle, any.getStyle(),
                    "Should return the style that was set");
        }
    }
}
