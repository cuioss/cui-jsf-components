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
package de.cuioss.jsf.api.common.accessor;

import static de.cuioss.test.jsf.generator.JsfProvidedConverter.CONVERTER_ID_GENERATOR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.convert.IntegerConverter;
import jakarta.faces.convert.NumberConverter;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.converter.ObjectToStringConverter;
import de.cuioss.jsf.api.converter.StringIdentConverter;
import de.cuioss.test.jsf.config.ComponentConfigurator;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.junit5.JsfEnabledTestEnvironment;
import de.cuioss.test.valueobjects.api.property.PropertyConfig;
import de.cuioss.test.valueobjects.api.property.PropertyReflectionConfig;

@PropertyReflectionConfig(skip = true)
@PropertyConfig(name = "converterId", propertyClass = String.class, generator = ConverterIdGenerator.class)
@PropertyConfig(name = "targetClass", propertyClass = Class.class, generator = ConverterTargetClassesGenerator.class)
class ConverterAccessorTest extends JsfEnabledTestEnvironment implements ComponentConfigurator {

    @Override
    public void configureComponents(final ComponentConfigDecorator decorator) {
        decorator.registerConverter(StringIdentConverter.class).registerConverter(ObjectToStringConverter.class);
    }

    @Test
    void shouldCheckContract() {
        var accessor = new ConverterAccessor<String>();
        assertFalse(accessor.checkContract());
        accessor.setConverterId(CONVERTER_ID_GENERATOR.next());
        assertTrue(accessor.checkContract());
        accessor.setTargetClass(String.class);
        assertTrue(accessor.checkContract());
    }

    @Test
    void shouldResolveConverterByType() {
        var accessor = new ConverterAccessor<Integer>();
        accessor.setTargetClass(Integer.class);
        assertEquals(IntegerConverter.class, accessor.getValue().getClass());
    }

    @Test
    void shouldResolveConvertById() {
        ConverterAccessor<?> accessor = new ConverterAccessor<>();
        accessor.setConverterId(NumberConverter.CONVERTER_ID);
        assertEquals(NumberConverter.class, accessor.getValue().getClass());
    }

    @Test
    void shouldFailOnInvalidConverterId() {
        ConverterAccessor<?> accessor = new ConverterAccessor<>();
        accessor.setConverterId("not.there");
        assertThrows(IllegalStateException.class, accessor::getValue);
    }

    @Test
    void shouldFailOnNoConfiguration() {
        ConverterAccessor<?> accessor = new ConverterAccessor<>();
        assertThrows(IllegalStateException.class, accessor::getValue);
    }

    @Test
    void shouldDefaultToObjectToStringConverter() {
        var accessor = new ConverterAccessor<>();
        accessor.setTargetClass(Object.class);
        assertEquals(ObjectToStringConverter.class, accessor.getValue().getClass());
    }
}
