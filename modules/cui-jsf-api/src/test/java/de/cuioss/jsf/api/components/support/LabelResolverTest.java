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
        final var resolver =
            LabelResolver.builder().withLabelValue("test").withConverter(ReverseConverter.CONVERTER_ID).build();
        assertEquals("tset", resolver.resolve(getFacesContext()));
    }

    @Test
    void shouldResolveWithConverter() {
        final var resolver =
            LabelResolver.builder().withLabelValue("test").withConverter(new ReverseConverter()).build();
        assertEquals("tset", resolver.resolve(getFacesContext()));
    }

    @Test
    void shouldResolveMessageOnConverterError() {
        getFacesContext().getApplication().addConverter(NumberConverter.CONVERTER_ID, NumberConverter.class.getName());
        final var test = someStrings.next();
        final var resolver =
            LabelResolver.builder().withLabelValue(test).withConverter(NumberConverter.CONVERTER_ID).build();
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
