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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.generator.junit.EnableGeneratorController;

@EnableGeneratorController
class ContextStateTest {

    private final TypedGenerator<String> states = fixedValues("DEFAULT", "PRIMARY", "SUCCESS", "INFO", "WARNING",
            "DANGER");

    @Test
    void shouldReturnDefaultForNullOrEmpty() {
        assertEquals(ContextState.DEFAULT, ContextState.getFromString(null));
        assertEquals(ContextState.DEFAULT, ContextState.getFromString(""));
        assertEquals(ContextState.DEFAULT, ContextState.getFromString(" "));
    }

    @Test
    void shouldReturnArbitrary() {
        assertNotNull(ContextState.getFromString(states.next()));
    }

    @Test
    void shouldFailOnInvalidName() {
        assertThrows(IllegalArgumentException.class, () -> ContextState.getFromString("not.there"));
    }

    @Test
    void shouldReturnForCaseInsensitive() {
        assertEquals(ContextState.PRIMARY, ContextState.getFromString("PRIMARY"));
        assertEquals(ContextState.PRIMARY, ContextState.getFromString("primary"));
        assertEquals(ContextState.PRIMARY, ContextState.getFromString("PRImary"));
    }

    @Test
    void shouldReturnForPaddedStrings() {
        assertEquals(ContextState.INFO, ContextState.getFromString(" INFO"));
        assertEquals(ContextState.INFO, ContextState.getFromString("INFO   "));
        assertEquals(ContextState.INFO, ContextState.getFromString(" INFO "));
    }

    @Test
    void shouldReturnPrefixedState() {
        final var state = ContextState.SUCCESS;
        assertEquals("success", state.getStyleClass());
        assertEquals("foo-success", state.getStyleClassWithPrefix("foo"));
        assertEquals("success", state.getStyleClassWithPrefix(null));
    }
}
