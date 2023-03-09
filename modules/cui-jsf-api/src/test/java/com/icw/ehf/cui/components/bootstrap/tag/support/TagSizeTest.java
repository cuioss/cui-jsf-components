package com.icw.ehf.cui.components.bootstrap.tag.support;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.icw.ehf.cui.core.api.components.css.ContextSize;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.generator.junit.EnableGeneratorController;

@EnableGeneratorController
class TagSizeTest {

    private final TypedGenerator<ContextSize> validSizes =
        Generators.fixedValues(ContextSize.LG, ContextSize.DEFAULT, ContextSize.XL, ContextSize.SM);

    @Test
    void shouldReturnValueOnValidSize() {
        final var tagSize = TagSize.getForContextSize(validSizes.next());
        assertNotNull(tagSize);
        assertNotNull(tagSize.getStyleClassBuilder());
        assertNotNull(tagSize.getStyleClass());
    }

    @Test
    void shouldReturnDefaultOnNull() {
        assertEquals(TagSize.DEFAULT, TagSize.getForContextSize(null));
    }

    @Test
    void shouldMapCorrectly() {
        assertEquals(TagSize.DEFAULT, TagSize.getForContextSize(ContextSize.DEFAULT));
        assertEquals("", TagSize.getForContextSize(ContextSize.DEFAULT).getStyleClass());
        assertEquals(TagSize.SM, TagSize.getForContextSize(ContextSize.SM));
        assertEquals("cui-tag-sm", TagSize.getForContextSize(ContextSize.SM).getStyleClass());
        assertEquals(TagSize.LG, TagSize.getForContextSize(ContextSize.LG));
        assertEquals("cui-tag-lg", TagSize.getForContextSize(ContextSize.LG).getStyleClass());
        assertEquals(TagSize.XL, TagSize.getForContextSize(ContextSize.XL));
        assertEquals("cui-tag-xl", TagSize.getForContextSize(ContextSize.XL).getStyleClass());
    }

    @Test
    void shouldFailOnInvalidSize() {
        assertThrows(IllegalArgumentException.class, () -> {
            TagSize.getForContextSize(ContextSize.XXXL);
        });
    }
}
