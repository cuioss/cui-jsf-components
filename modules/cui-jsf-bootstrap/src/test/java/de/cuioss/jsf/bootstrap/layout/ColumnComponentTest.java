package de.cuioss.jsf.bootstrap.layout;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.bootstrap.common.partial.ColumnCssResolver;
import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.jsf.component.AbstractUiComponentTest;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;

@VerifyComponentProperties(of = { "offsetSize", "size" })
class ColumnComponentTest extends AbstractUiComponentTest<ColumnComponent> {

    private static final String COL_SM_OFFSET_PREFIX = " " + ColumnCssResolver.COL_OFFSET_PREFIX;

    private static final String COL_SM_PREFIX = ColumnCssResolver.COL_PREFIX;

    private final TypedGenerator<Integer> validNumbers = Generators.integers(1, 12);

    @Test
    void shouldResolveAndOffsetSize() {
        final var underTest = new ColumnComponent();
        final var size = validNumbers.next();
        final var offsetSize = validNumbers.next();
        underTest.setSize(size);
        underTest.setOffsetSize(offsetSize);
        assertEquals(COL_SM_PREFIX + size + COL_SM_OFFSET_PREFIX + offsetSize,
                underTest.resolveStyleClass().getStyleClass());
    }

    @Test
    void shouldResolveAndOffsetSizeWithStyleClass() {
        final var underTest = new ColumnComponent();
        final var size = validNumbers.next();
        final var offsetSize = validNumbers.next();
        underTest.setSize(size);
        underTest.setOffsetSize(offsetSize);
        assertEquals(COL_SM_PREFIX + size + COL_SM_OFFSET_PREFIX + offsetSize,
                underTest.resolveStyleClass().getStyleClass());
    }
}
