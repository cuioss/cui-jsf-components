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
package de.cuioss.jsf.api.common.accessor;

import static de.cuioss.test.jsf.generator.JsfProvidedConverter.CONVERTER_ID_GENERATOR;
import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.jsf.api.converter.ObjectToStringConverter;
import de.cuioss.jsf.api.converter.StringIdentConverter;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.junit5.EnableJsfEnvironment;
import de.cuioss.test.valueobjects.api.property.PropertyConfig;
import de.cuioss.test.valueobjects.api.property.PropertyReflectionConfig;
import jakarta.faces.convert.IntegerConverter;
import jakarta.faces.convert.NumberConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@PropertyReflectionConfig(skip = true)
@PropertyConfig(name = "converterId", propertyClass = String.class, generator = ConverterIdGenerator.class)
@PropertyConfig(name = "targetClass", propertyClass = Class.class, generator = ConverterTargetClassesGenerator.class)
@EnableJsfEnvironment
@DisplayName("Tests for ConverterAccessor")
class ConverterAccessorTest {

    @BeforeEach
    void configureComponents(final ComponentConfigDecorator decorator) {
        decorator.registerConverter(StringIdentConverter.class).registerConverter(ObjectToStringConverter.class);
    }

    @Nested
    @DisplayName("Tests for contract validation")
    class ContractValidationTests {

        @Test
        @DisplayName("Should validate contract based on configuration")
        void shouldValidateContract() {
            // Arrange
            var accessor = new ConverterAccessor<String>();

            // Assert - initial state
            assertFalse(accessor.checkContract(),
                    "Contract should not be valid without configuration");

            // Act - set converter ID
            accessor.setConverterId(CONVERTER_ID_GENERATOR.next());

            // Assert - after setting converter ID
            assertTrue(accessor.checkContract(),
                    "Contract should be valid with converter ID");

            // Act - set target class
            accessor.setTargetClass(String.class);

            // Assert - after setting target class
            assertTrue(accessor.checkContract(),
                    "Contract should remain valid with both converter ID and target class");
        }
    }

    @Nested
    @DisplayName("Tests for converter resolution")
    class ConverterResolutionTests {

        @Test
        @DisplayName("Should resolve converter by target class type")
        void shouldResolveConverterByType() {
            // Arrange
            var accessor = new ConverterAccessor<Integer>();

            // Act
            accessor.setTargetClass(Integer.class);

            // Assert
            assertEquals(IntegerConverter.class, accessor.getValue().getClass(),
                    "Should resolve to IntegerConverter for Integer class");
        }

        @Test
        @DisplayName("Should resolve converter by converter ID")
        void shouldResolveConverterById() {
            // Arrange
            ConverterAccessor<?> accessor = new ConverterAccessor<>();

            // Act
            accessor.setConverterId(NumberConverter.CONVERTER_ID);

            // Assert
            assertEquals(NumberConverter.class, accessor.getValue().getClass(),
                    "Should resolve to NumberConverter for NumberConverter.CONVERTER_ID");
        }

        @Test
        @DisplayName("Should default to ObjectToStringConverter for Object class")
        void shouldDefaultToObjectToStringConverter() {
            // Arrange
            var accessor = new ConverterAccessor<>();

            // Act
            accessor.setTargetClass(Object.class);

            // Assert
            assertEquals(ObjectToStringConverter.class, accessor.getValue().getClass(),
                    "Should default to ObjectToStringConverter for Object class");
        }
    }

    @Nested
    @DisplayName("Tests for error handling")
    class ErrorHandlingTests {

        @Test
        @DisplayName("Should throw exception for invalid converter ID")
        void shouldFailOnInvalidConverterId() {
            // Arrange
            ConverterAccessor<?> accessor = new ConverterAccessor<>();
            accessor.setConverterId("not.there");

            // Act & Assert
            assertThrows(IllegalStateException.class, accessor::getValue,
                    "Should throw IllegalStateException for invalid converter ID");
        }

        @Test
        @DisplayName("Should throw exception when no configuration is provided")
        void shouldFailOnNoConfiguration() {
            // Arrange
            ConverterAccessor<?> accessor = new ConverterAccessor<>();

            // Act & Assert
            assertThrows(IllegalStateException.class, accessor::getValue,
                    "Should throw IllegalStateException when no configuration is provided");
        }
    }
}
