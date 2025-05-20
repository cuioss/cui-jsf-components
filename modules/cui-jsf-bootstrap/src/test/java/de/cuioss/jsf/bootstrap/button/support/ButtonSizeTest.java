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
package de.cuioss.jsf.bootstrap.button.support;

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.jsf.api.components.css.ContextSize;
import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.generator.junit.EnableGeneratorController;
import org.junit.jupiter.api.Test;

@EnableGeneratorController
class ButtonSizeTest {

    private final TypedGenerator<ContextSize> validSizes = Generators.fixedValues(ContextSize.LG, ContextSize.DEFAULT,
            ContextSize.SM);

    @Test
    void shouldReturnValueOnValidSize() {
        final var buttonSize = ButtonSize.getForContextSize(validSizes.next());
        assertNotNull(buttonSize);
        assertNotNull(buttonSize.getStyleClassBuilder());
        assertNotNull(buttonSize.getStyleClass());
    }

    @Test
    void shouldReturnDefaultOnNull() {
        assertEquals(ButtonSize.DEFAULT, ButtonSize.getForContextSize(null));
    }

    @Test
    void shouldMapCorrectly() {
        assertEquals(ButtonSize.DEFAULT, ButtonSize.getForContextSize(ContextSize.DEFAULT));
        assertEquals("", ButtonSize.getForContextSize(ContextSize.DEFAULT).getStyleClass());
        assertEquals(ButtonSize.LG, ButtonSize.getForContextSize(ContextSize.LG));
        assertEquals("btn-lg", ButtonSize.getForContextSize(ContextSize.LG).getStyleClass());
        assertEquals(ButtonSize.SM, ButtonSize.getForContextSize(ContextSize.SM));
    }

    @Test
    void shouldFailOnInvalidSize() {
        assertThrows(IllegalArgumentException.class, () -> ButtonSize.getForContextSize(ContextSize.XL));
    }
}
