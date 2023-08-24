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
package de.cuioss.jsf.api.components.support;

import static de.cuioss.test.generator.Generators.nonEmptyStrings;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.faces.convert.NumberConverter;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.CoreJsfTestConfiguration;
import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.junit5.JsfEnabledTestEnvironment;
import de.cuioss.test.jsf.mocks.ReverseConverter;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class LabelResolverTest extends JsfEnabledTestEnvironment {

    private final TypedGenerator<String> someStrings = nonEmptyStrings();

    @Test
    void shouldResolveStringObject() {
        final var test = someStrings.next();
        final var resolver = LabelResolver.builder().withLabelValue(test).build();
        assertEquals(test, resolver.resolve(getFacesContext()));
    }

    @Test
    void shouldFallBackWithNotRegisteredConverterObject() {
        final Double test = 1.0;
        final var resolver = LabelResolver.builder().withLabelValue(test).build();
        assertEquals("1.0", resolver.resolve(getFacesContext()));
    }

    @Test
    void shouldResolveWithConverterId() {
        getComponentConfigDecorator().registerConverter(ReverseConverter.class);
        final var resolver = LabelResolver.builder().withLabelValue("test").withConverter(ReverseConverter.CONVERTER_ID)
                .build();
        assertEquals("tset", resolver.resolve(getFacesContext()));
    }

    @Test
    void shouldResolveWithConverter() {
        final var resolver = LabelResolver.builder().withLabelValue("test").withConverter(new ReverseConverter())
                .build();
        assertEquals("tset", resolver.resolve(getFacesContext()));
    }

    @Test
    void shouldResolveMessageOnConverterError() {
        getFacesContext().getApplication().addConverter(NumberConverter.CONVERTER_ID, NumberConverter.class.getName());
        final var test = someStrings.next();
        final var resolver = LabelResolver.builder().withLabelValue(test).withConverter(NumberConverter.CONVERTER_ID)
                .build();
        assertNotNull(resolver.resolve(getFacesContext()));
    }

    @Test
    void shouldResolveMessageKey() {
        final var resolver = LabelResolver.builder().withLabelKey("some.key").build();
        assertEquals("some.key", resolver.resolve(getFacesContext()));
    }

    @Test
    void shouldFailOnStrictMode() {
        final var resolver = LabelResolver.builder().withStrictMode(true).build();
        var facesContext = getFacesContext();
        assertThrows(IllegalStateException.class, () -> resolver.resolve(facesContext));
    }

    @Test
    void shouldReturnNullOnNonStrictMode() {
        final var resolver = LabelResolver.builder().withStrictMode(false).build();
        assertNull(resolver.resolve(getFacesContext()));
    }

    @Test
    void shouldResolveValueOverKey() {
        final var test = someStrings.next();
        final var resolver = LabelResolver.builder().withLabelValue(test).withLabelKey("some.key").build();
        assertEquals(test, resolver.resolve(getFacesContext()));
    }
}
