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
package de.cuioss.jsf.bootstrap.icon.support;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.components.css.ContextSize;
import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.generator.junit.EnableGeneratorController;

@EnableGeneratorController
class IconSizeTest {

    private final TypedGenerator<ContextSize> validSizes = Generators.fixedValues(ContextSize.LG, ContextSize.DEFAULT,
            ContextSize.XL, ContextSize.XXL, ContextSize.XXXL);

    @Test
    void shouldReturnValueOnValidSize() {
        final var iconSize = IconSize.getForContextSize(validSizes.next());
        assertNotNull(iconSize);
        assertNotNull(iconSize.getStyleClassBuilder());
        assertNotNull(iconSize.getStyleClass());
    }

    @Test
    void shouldReturnDefaultOnNull() {
        assertEquals(IconSize.DEFAULT, IconSize.getForContextSize(null));
    }

    @Test
    void shouldMapCorrectly() {
        assertEquals(IconSize.DEFAULT, IconSize.getForContextSize(ContextSize.DEFAULT));
        assertEquals("", IconSize.getForContextSize(ContextSize.DEFAULT).getStyleClass());
        assertEquals(IconSize.LG, IconSize.getForContextSize(ContextSize.LG));
        assertEquals("cui-icon-lg", IconSize.getForContextSize(ContextSize.LG).getStyleClass());
        assertEquals(IconSize.XL, IconSize.getForContextSize(ContextSize.XL));
        assertEquals("cui-icon-xl", IconSize.getForContextSize(ContextSize.XL).getStyleClass());
        assertEquals(IconSize.XXL, IconSize.getForContextSize(ContextSize.XXL));
        assertEquals("cui-icon-xxl", IconSize.getForContextSize(ContextSize.XXL).getStyleClass());
        assertEquals(IconSize.XXXL, IconSize.getForContextSize(ContextSize.XXXL));
        assertEquals("cui-icon-xxxl", IconSize.getForContextSize(ContextSize.XXXL).getStyleClass());
    }

    @Test
    void shouldFailOnInvalidSize() {
        assertThrows(IllegalArgumentException.class, () -> IconSize.getForContextSize(ContextSize.SM));
    }
}
