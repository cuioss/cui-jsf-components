package de.cuioss.jsf.api.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import de.cuioss.jsf.api.converter.JQueryDateTimePatternConverter;

class JQueryDateTimePatternConverterTest {

    @ParameterizedTest
    @CsvSource({
        "yyyy-MM-dd T HH:mm:ss, yyyy-MM-dd'T'HH:mm:ss.SSS ZZ",
        "yyyy-MM-dd T HH:mm:ss, yyyy-MM-dd'T'HH:mm:ss.SSS '(UTC'ZZ')'",
        "hh  o  clock  a, hh 'o''clock' a zzzz"
    })
    void shouldSupportJodaDateTimeFormats(String expected, String patternString) {
        final var converter =
            new JQueryDateTimePatternConverter(patternString);
        final var pattern = converter.getJQueryDateConformTimePattern();
        assertEquals(expected, pattern);
    }
}
