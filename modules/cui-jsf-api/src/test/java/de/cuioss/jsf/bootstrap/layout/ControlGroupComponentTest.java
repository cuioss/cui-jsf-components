package de.cuioss.jsf.bootstrap.layout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.jsf.component.AbstractUiComponentTest;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;

@VerifyComponentProperties(of = { "offsetSize" })
class ControlGroupComponentTest extends AbstractUiComponentTest<ControlGroupComponent> {

    private final TypedGenerator<Integer> validNumbers = Generators.integers(1, 12);

    @Test
    void shouldProvideDefaultSize() {
        final var underTest = new ControlGroupComponent();
        assertEquals(Integer.valueOf(8), underTest.getSize());
    }

    @Test
    void shouldResolveColumnCss() {
        final var underTest = new ControlGroupComponent();
        underTest.setSize(validNumbers.next());
        underTest.setOffsetSize(validNumbers.next());
        assertNotNull(underTest.resolveColumnCss());
    }
}
