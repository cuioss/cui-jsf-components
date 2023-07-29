package de.cuioss.jsf.bootstrap.button.support;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.components.css.ContextSize;
import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.generator.junit.EnableGeneratorController;

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
