package com.icw.ehf.cui.components.js.types;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.valueobjects.ValueObjectTest;

class JsDoubleTest extends ValueObjectTest<JsDouble> {

    @Override
    protected JsDouble anyValueObject() {
        return new JsDouble(Generators.doubles().next());
    }

    @Test
    void shouldReturnNullOnEmptyValue() {
        final var target = new JsDouble(null);
        assertNull(target.getValueAsString());
    }

    @Test
    void shouldFormatValidValue() {
        final var val1 = new JsDouble(Double.valueOf("3.5"));
        assertEquals("3.500", val1.getValueAsString());
        final var val2 = new JsDouble(5d);
        assertEquals("5.000", val2.getValueAsString());
        final var val3 = new JsDouble(50000d);
        assertEquals("50000.000", val3.getValueAsString());
    }
}
