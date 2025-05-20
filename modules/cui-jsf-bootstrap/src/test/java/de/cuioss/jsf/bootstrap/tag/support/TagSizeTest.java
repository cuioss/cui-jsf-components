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
package de.cuioss.jsf.bootstrap.tag.support;

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.jsf.api.components.css.ContextSize;
import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.generator.junit.EnableGeneratorController;
import org.junit.jupiter.api.Test;

@EnableGeneratorController
class TagSizeTest {

    private final TypedGenerator<ContextSize> validSizes = Generators.fixedValues(ContextSize.LG, ContextSize.DEFAULT,
            ContextSize.XL, ContextSize.SM);

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
        assertThrows(IllegalArgumentException.class, () -> TagSize.getForContextSize(ContextSize.XXXL));
    }
}
