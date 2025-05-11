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
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.mocks.ReverseConverter;
import org.jboss.weld.junit5.ExplicitParamInjection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@VerifyComponentProperties(of = {"labelKey", "labelValue", "labelEscape", "labelConverter"})
@ExplicitParamInjection
@DisplayName("Tests for LabelProvider implementation")
class LabelProviderImplTest extends AbstractPartialComponentTest {

    @Test
    @DisplayName("Should throw NullPointerException when constructed with null")
    void shouldFailWithNullConstructor() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> new LabelProvider(null),
                "Constructor should reject null component");
    }

    @Nested
    @DisplayName("Tests for label resolution")
    class LabelResolutionTests {

        @Test
        @DisplayName("Should resolve null when no label is set")
        void shouldResolveNullForNoLabelSet() {
            // Act & Assert
            assertNull(anyComponent().resolveLabel(),
                    "Label should be null when no label is set");
        }

        @Test
        @DisplayName("Should resolve label value when set directly")
        void shouldResolveLabelValue() {
            // Arrange
            var any = anyComponent();

            // Act
            any.setLabelValue(MESSAGE_KEY);

            // Assert
            assertEquals(MESSAGE_KEY, any.resolveLabel(),
                    "Should return the directly set label value");
        }

        @Test
        @DisplayName("Should resolve label from resource bundle when key is set")
        void shouldResolveLabelKey() {
            // Arrange
            var any = anyComponent();

            // Act
            any.setLabelKey(MESSAGE_KEY);

            // Assert
            assertEquals(MESSAGE_VALUE, any.resolveLabel(),
                    "Should resolve label from resource bundle using the key");
        }
    }

    @Nested
    @DisplayName("Tests for label conversion")
    class LabelConversionTests {

        @Test
        @DisplayName("Should use converter by ID when registered in application")
        void shouldUseConverterAsId(ComponentConfigDecorator componentConfig) {
            // Arrange
            componentConfig.registerConverter(ReverseConverter.class);
            var any = anyComponent();

            // Act
            any.setLabelConverter(ReverseConverter.CONVERTER_ID);
            any.setLabelValue("test");

            // Assert
            assertEquals("tset", any.resolveLabel(),
                    "Label should be converted using the registered converter");
        }

        @Test
        @DisplayName("Should use converter instance when set directly")
        void shouldUseConverterAsConverter() {
            // Arrange
            var any = anyComponent();

            // Act
            any.setLabelConverter(new ReverseConverter());
            any.setLabelValue("test");

            // Assert
            assertEquals("tset", any.resolveLabel(),
                    "Label should be converted using the direct converter instance");
        }
    }

    @Nested
    @DisplayName("Tests for label escaping")
    class LabelEscapeTests {

        @Test
        @DisplayName("Should default to escaping labels")
        void shouldDefaultToLabelEscape() {
            // Act & Assert
            assertTrue(anyComponent().isLabelEscape(),
                    "Label escaping should be enabled by default");
        }

        @Test
        @DisplayName("Should allow disabling label escaping")
        void shouldAllowDisablingLabelEscape() {
            // Arrange
            var any = anyComponent();

            // Act
            any.setLabelEscape(false);

            // Assert
            assertFalse(any.isLabelEscape(),
                    "Label escaping should be disabled when set to false");
        }
    }
}
