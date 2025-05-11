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
package de.cuioss.jsf.api.components.base;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.cuioss.test.jsf.component.AbstractComponentTest;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@VerifyComponentProperties(of = {"titleKey", "titleValue"})
@DisplayName("Tests for BaseCuiOutputText")
class BaseCuiOutputTextTest extends AbstractComponentTest<MockBaseCuiOutputText> {

    @Nested
    @DisplayName("Tests for style class handling")
    class StyleClassTests {

        @Test
        @DisplayName("Should provide default style class")
        void shouldProvideDefaultStyleClass() {
            // Arrange & Act
            var component = anyComponent();

            // Assert
            assertEquals("mock", component.getStyleClass(),
                    "Default style class should be 'mock'");
        }

        @Test
        @DisplayName("Should extend style class when custom class is set")
        void shouldExtendStyleClass() {
            // Arrange
            var component = anyComponent();

            // Act
            component.setStyleClass("foo");

            // Assert
            assertEquals("mock foo", component.getStyleClass(),
                    "Style class should be extended with custom class");
        }
    }
}
