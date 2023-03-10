package de.cuioss.jsf.components.js.types;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Calendar;

import org.junit.jupiter.api.Test;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.valueobjects.ValueObjectTest;

class JsDateTest extends ValueObjectTest<JsDate> {

    @Test
    void shouldReturnNullOnEmptyValue() {
        final var target = new JsDate(null);
        assertNull(target.getValueAsString());
    }

    @Test
    void shouldFormatValidValue() {
        final var cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2010);
        cal.set(Calendar.MONTH, Calendar.OCTOBER);
        cal.set(Calendar.DAY_OF_MONTH, 20);
        cal.set(Calendar.HOUR, 5);
        cal.set(Calendar.MINUTE, 5);
        cal.set(Calendar.SECOND, 5);
        final var date = cal.getTime();
        final var target = new JsDate(date);
        assertEquals("\"2010-10-20\"", target.getValueAsString());
    }

    @Override
    protected JsDate anyValueObject() {
        return new JsDate(Generators.dates().next());
    }
}
