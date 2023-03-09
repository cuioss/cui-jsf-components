package com.icw.ehf.cui.core.api.components.css;

import static de.cuioss.test.generator.Generators.fixedValues;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.generator.junit.EnableGeneratorController;

@EnableGeneratorController
class ContextSizeTest {

    private final TypedGenerator<String> sizes = fixedValues("XS", "SM", "MD", "LG", "XL", "XXL", "XXXL", "xs", "sm");

    @Test
    void shouldReturnDefaultForNullOrEmpty() {
        assertEquals(ContextSize.DEFAULT, ContextSize.getFromString(null));
        assertEquals(ContextSize.DEFAULT, ContextSize.getFromString(""));
        assertEquals(ContextSize.DEFAULT, ContextSize.getFromString(" "));
    }

    @Test
    void shouldReturnArbitrary() {
        assertNotNull(ContextSize.getFromString(sizes.next()));
    }

    @Test
    void shouldFailOnInvalidName() {
        assertThrows(IllegalArgumentException.class, () -> {
            ContextSize.getFromString("not.there");
        });
    }

    @Test
    void shouldReturnForCaseInsensitive() {
        assertEquals(ContextSize.MD, ContextSize.getFromString("md"));
        assertEquals(ContextSize.MD, ContextSize.getFromString("MD"));
        assertEquals(ContextSize.MD, ContextSize.getFromString("Md"));
    }

    @Test
    void shouldReturnForPaddedStrings() {
        assertEquals(ContextSize.XL, ContextSize.getFromString(" XL"));
        assertEquals(ContextSize.XL, ContextSize.getFromString("XL   "));
        assertEquals(ContextSize.XL, ContextSize.getFromString(" xL "));
    }
}
