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
package de.cuioss.jsf.api.components.support;

import static de.cuioss.test.generator.Generators.nonEmptyStrings;
import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.jsf.api.CoreJsfTestConfiguration;
import de.cuioss.jsf.api.EnableJSFCDIEnvironment;
import de.cuioss.jsf.api.EnableResourceBundleSupport;
import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.junit5.EnableJsfEnvironment;
import de.cuioss.test.jsf.mocks.ReverseConverter;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.NumberConverter;
import org.jboss.weld.junit5.ExplicitParamInjection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@EnableJSFCDIEnvironment
@EnableResourceBundleSupport
@JsfTestConfiguration(CoreJsfTestConfiguration.class)
@EnableJsfEnvironment
@ExplicitParamInjection
@DisplayName("Tests for LabelResolver")
class LabelResolverTest {

    @BeforeEach
    void configureCuiComponents(ComponentConfigDecorator decorator) {
        CoreJsfTestConfiguration.configureComponents(decorator);
    }

    protected static final String MESSAGE_KEY = "de.cuioss.common.email.invalid";

    protected static final String MESSAGE_VALUE = "invalid e-Mail Address syntax";

    private final TypedGenerator<String> someStrings = nonEmptyStrings();

    @Nested
    @DisplayName("Tests for basic label resolution")
    class BasicResolutionTests {

        @Test
        @DisplayName("Should resolve string object directly")
        void shouldResolveStringObject(FacesContext facesContext) {
            // Arrange
            final var test = someStrings.next();

            // Act
            final var resolver = LabelResolver.builder().withLabelValue(test).build();

            // Assert
            assertEquals(test, resolver.resolve(facesContext),
                    "Resolver should return the original string value");
        }

        @Test
        @DisplayName("Should resolve message key from resource bundle")
        void shouldResolveMessageKey(FacesContext facesContext) {
            // Arrange
            final var resolver = LabelResolver.builder().withLabelKey(MESSAGE_KEY).build();

            // Act & Assert
            assertEquals(MESSAGE_VALUE, resolver.resolve(facesContext),
                    "Resolver should return the message value from the resource bundle");
        }

        @Test
        @DisplayName("Should prioritize value over key when both are set")
        void shouldResolveValueOverKey(FacesContext facesContext) {
            // Arrange
            final var test = someStrings.next();

            // Act
            final var resolver = LabelResolver.builder()
                    .withLabelValue(test)
                    .withLabelKey(MESSAGE_KEY)
                    .build();

            // Assert
            assertEquals(test, resolver.resolve(facesContext),
                    "Resolver should prioritize label value over label key");
        }
    }

    @Nested
    @DisplayName("Tests for converter integration")
    class ConverterTests {

        @Test
        @DisplayName("Should fall back to toString for non-registered converter objects")
        void shouldFallBackWithNotRegisteredConverterObject(FacesContext facesContext) {
            // Arrange
            final Double test = 1.0;

            // Act
            final var resolver = LabelResolver.builder().withLabelValue(test).build();

            // Assert
            assertEquals("1.0", resolver.resolve(facesContext),
                    "Resolver should fall back to toString for non-string objects");
        }

        @Test
        @DisplayName("Should resolve with converter ID when registered")
        void shouldResolveWithConverterId(FacesContext facesContext, ComponentConfigDecorator componentConfig) {
            // Arrange
            componentConfig.registerConverter(ReverseConverter.class);

            // Act
            final var resolver = LabelResolver.builder()
                    .withLabelValue("test")
                    .withConverter(ReverseConverter.CONVERTER_ID)
                    .build();

            // Assert
            assertEquals("tset", resolver.resolve(facesContext),
                    "Resolver should use the registered converter by ID");
        }

        @Test
        @DisplayName("Should resolve with converter instance")
        void shouldResolveWithConverter(FacesContext facesContext) {
            // Arrange
            final var resolver = LabelResolver.builder()
                    .withLabelValue("test")
                    .withConverter(new ReverseConverter())
                    .build();

            // Act & Assert
            assertEquals("tset", resolver.resolve(facesContext),
                    "Resolver should use the provided converter instance");
        }

        @Test
        @DisplayName("Should handle converter errors gracefully")
        void shouldResolveMessageOnConverterError(FacesContext facesContext) {
            // Arrange
            facesContext.getApplication().addConverter(NumberConverter.CONVERTER_ID, NumberConverter.class.getName());
            final var test = someStrings.next();

            // Act
            final var resolver = LabelResolver.builder()
                    .withLabelValue(test)
                    .withConverter(NumberConverter.CONVERTER_ID)
                    .build();

            // Assert
            assertNotNull(resolver.resolve(facesContext),
                    "Resolver should not return null when converter fails");
        }
    }

    @Nested
    @DisplayName("Tests for strict mode behavior")
    class StrictModeTests {

        @Test
        @DisplayName("Should throw exception in strict mode when no label is set")
        void shouldFailOnStrictMode(FacesContext facesContext) {
            // Arrange
            final var resolver = LabelResolver.builder().withStrictMode(true).build();

            // Act & Assert
            assertThrows(IllegalStateException.class, () -> resolver.resolve(facesContext),
                    "Resolver should throw IllegalStateException in strict mode when no label is set");
        }

        @Test
        @DisplayName("Should return null in non-strict mode when no label is set")
        void shouldReturnNullOnNonStrictMode(FacesContext facesContext) {
            // Arrange
            final var resolver = LabelResolver.builder().withStrictMode(false).build();

            // Act & Assert
            assertNull(resolver.resolve(facesContext),
                    "Resolver should return null in non-strict mode when no label is set");
        }
    }
}
