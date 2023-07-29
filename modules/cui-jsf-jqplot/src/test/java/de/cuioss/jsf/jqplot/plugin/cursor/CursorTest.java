package de.cuioss.jsf.jqplot.plugin.cursor;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import de.cuioss.test.valueobjects.junit5.contracts.ShouldHandleObjectContracts;

class CursorTest implements ShouldHandleObjectContracts<Cursor> {

    @Test
    void shouldNotReturnObjectOnEmptyProperties() {
        final var target = new Cursor();
        assertNull(target.asJavaScriptObjectNotation());
    }

    @Override
    public Cursor getUnderTest() {
        return new Cursor();
    }
}
