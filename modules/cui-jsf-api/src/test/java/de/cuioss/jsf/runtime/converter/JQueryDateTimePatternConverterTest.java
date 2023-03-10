package de.cuioss.jsf.runtime.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class JQueryDateTimePatternConverterTest {

    @Test
    void shouldSupportJodaDateTimeFormats() {
        final var converter =
            new JQueryDateTimePatternConverter("yyyy-MM-dd'T'HH:mm:ss.SSS ZZ");
        final var pattern = converter.getJQueryDateConformTimePattern();
        assertEquals("yyyy-MM-dd T HH:mm:ss", pattern);
    }

    @Test
    void shouldSupportCustomPatterns() {
        final var converter =
            new JQueryDateTimePatternConverter("yyyy-MM-dd'T'HH:mm:ss.SSS '(UTC'ZZ')'");
        final var pattern = converter.getJQueryDateConformTimePattern();
        assertEquals("yyyy-MM-dd T HH:mm:ss", pattern);
    }

    @Test
    void shouldSupportSimpleDateFormat() {
        final var converter = new JQueryDateTimePatternConverter("hh 'o''clock' a zzzz");
        final var pattern = converter.getJQueryDateConformTimePattern();
        assertEquals("hh  o  clock  a", pattern);
    }
}
