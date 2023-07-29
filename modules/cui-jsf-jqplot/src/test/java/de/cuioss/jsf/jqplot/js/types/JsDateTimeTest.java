package de.cuioss.jsf.jqplot.js.types;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.Temporal;

import org.junit.jupiter.api.Test;

import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.property.PropertyReflectionConfig;

@PropertyReflectionConfig(skip = true)
class JsDateTimeTest extends ValueObjectTest<JsDateTime> {

    @Override
    protected JsDateTime anyValueObject() {
        return JsDateTime.builder().formatter(JsDateTimeFormat.DATE_ONLY).value(LocalDateTime.now()).build();
    }

    @Test
    void shouldReturnNullOnEmptyValue() {
        final var target = JsDateTime.builder().formatter(JsDateTimeFormat.DATE_ONLY).value(null).build();
        assertNull(target.getValueAsString());
    }

    @Test
    void shouldFormatValidValue() {
        final Temporal date = LocalDateTime.of(2010, 10, 20, 12, 30, 59).atOffset(ZoneOffset.ofHours(1));
        final var onlyDate = JsDateTime.builder().formatter(JsDateTimeFormat.DATE_ONLY).value(date).build();
        final var dateTime = JsDateTime.builder().formatter(JsDateTimeFormat.DATE_TIME).value(date).build();
        final var dateTimeMiliseconds = JsDateTime.builder().formatter(JsDateTimeFormat.DATE_TIME_WITH_MILISECONDS)
                .value(date).build();
        final var fullIso = JsDateTime.builder().formatter(JsDateTimeFormat.DATE_TIME_WITH_MILISECONDS_TIMEZONE)
                .value(date).build();
        assertEquals("\"2010-10-20\"", onlyDate.getValueAsString());
        assertEquals("\"2010-10-20 12:30:59\"", dateTime.getValueAsString());
        assertEquals("\"2010-10-20 12:30:59.0\"", dateTimeMiliseconds.getValueAsString());
        // full ISO 8601
        assertEquals("\"2010-10-20T12:30:59+01:00\"", fullIso.getValueAsString());
    }
}
