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
package de.cuioss.jsf.api.components.css;

import static de.cuioss.test.generator.Generators.fixedValues;
import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.generator.junit.EnableGeneratorController;
import org.junit.jupiter.api.Test;

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
        assertThrows(IllegalArgumentException.class, () -> ContextSize.getFromString("not.there"));
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
