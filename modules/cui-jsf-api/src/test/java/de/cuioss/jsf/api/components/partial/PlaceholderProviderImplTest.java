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

import de.cuioss.jsf.api.components.JsfHtmlComponent;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.mocks.ReverseConverter;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import org.jboss.weld.junit5.ExplicitParamInjection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@VerifyComponentProperties(of = {"placeholderKey", "placeholderValue", "placeholderConverter"})
@ExplicitParamInjection
@DisplayName("Tests for PlaceholderProvider implementation")
class PlaceholderProviderImplTest extends AbstractPartialComponentTest {

    @Test
    @DisplayName("Should throw NullPointerException when constructed with null")
    void shouldFailWithNullConstructor() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> new PlaceholderProvider(null),
                "Constructor should reject null component");
    }

    @Nested
    @DisplayName("Tests for placeholder resolution")
    class PlaceholderResolutionTests {

        @Test
        @DisplayName("Should resolve null when no placeholder is set")
        void shouldResolveNullForNoPlaceholderSet() {
            // Act & Assert
            assertNull(anyComponent().resolvePlaceholder(),
                    "Placeholder should be null when none is set");
        }

        @Test
        @DisplayName("Should resolve placeholder value when set directly")
        void shouldResolvePlaceholderValue() {
            // Arrange
            var any = anyComponent();

            // Act
            any.setPlaceholderValue(MESSAGE_KEY);

            // Assert
            assertEquals(MESSAGE_KEY, any.resolvePlaceholder(),
                    "Should return the directly set placeholder value");
        }

        @Test
        @DisplayName("Should resolve placeholder from resource bundle when key is set")
        void shouldResolvePlaceholderKey() {
            // Arrange
            var any = anyComponent();

            // Act
            any.setPlaceholderKey(MESSAGE_KEY);

            // Assert
            assertEquals(MESSAGE_VALUE, any.resolvePlaceholder(),
                    "Should resolve placeholder from resource bundle using the key");
        }
    }

    @Nested
    @DisplayName("Tests for placeholder conversion")
    class PlaceholderConversionTests {

        @Test
        @DisplayName("Should use converter by ID when registered in application")
        void shouldUseConverterById(ComponentConfigDecorator componentConfig) {
            // Arrange
            componentConfig.registerConverter(ReverseConverter.class);
            var any = anyComponent();

            // Act
            any.setPlaceholderConverter(ReverseConverter.CONVERTER_ID);
            any.setPlaceholderValue("test");

            // Assert
            assertEquals("tset", any.resolvePlaceholder(),
                    "Placeholder should be converted using the registered converter");
        }

        @Test
        @DisplayName("Should use converter instance when set directly")
        void shouldUseConverterInstance() {
            // Arrange
            var any = anyComponent();

            // Act
            any.setPlaceholderConverter(new ReverseConverter());
            any.setPlaceholderValue("test");

            // Assert
            assertEquals("tset", any.resolvePlaceholder(),
                    "Placeholder should be converted using the direct converter instance");
        }
    }

    @Nested
    @DisplayName("Tests for pass-through attribute handling")
    class PassThroughAttributeTests {

        @Test
        @DisplayName("Should add and remove placeholder from pass-through attributes")
        void shouldAddAndRemovePlaceholderFromPassThrough(FacesContext facesContext) {
            // Arrange
            var pt = anyComponent();
            UIComponent other = JsfHtmlComponent.INPUT.component(facesContext);

            // Act - set placeholder and apply to component
            pt.setPlaceholderValue("test");
            pt.setPlaceholder(other, facesContext, pt);

            // Assert - placeholder is set in pass-through attributes
            assertEquals("test", other.getPassThroughAttributes().get(PlaceholderProvider.PT_PLACEHOLDER),
                    "Placeholder should be added to pass-through attributes");

            // Act - clear placeholder and apply to component
            pt.setPlaceholderValue(null);
            pt.setPlaceholder(other, facesContext, pt);

            // Assert - placeholder is removed from pass-through attributes
            assertNull(other.getPassThroughAttributes().get(PlaceholderProvider.PT_PLACEHOLDER),
                    "Placeholder should be removed from pass-through attributes when set to null");
        }
    }
}
