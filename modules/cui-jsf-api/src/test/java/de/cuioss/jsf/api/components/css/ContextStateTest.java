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

    private final TypedGenerator<String> states =
        fixedValues("DEFAULT", "PRIMARY", "SUCCESS", "INFO", "WARNING", "DANGER");

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
        assertThrows(IllegalArgumentException.class, () -> {
            ContextState.getFromString("not.there");
        });
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
