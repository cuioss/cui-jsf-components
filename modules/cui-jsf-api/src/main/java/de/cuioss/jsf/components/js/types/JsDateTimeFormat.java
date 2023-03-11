package de.cuioss.jsf.components.js.types;

import static java.util.Objects.requireNonNull;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

import lombok.Getter;

/**
 * To be able to work with JavaScript date time the date need to be fit's to defined from.
 * This Enum provide prepared the most popular formatter for Joda DateTime.
 *
 * @author Eugen Fischer
 * @see <a href="http://sandbox.kendsnyder.com/date/?q=sandbox/date" >Date</a>
 *      2007-10-29 (Date only)
 *      2010-03-15T12:34:56.005 (with miliseconds)
 *      2010-03-15T12:34:56+01:00 (full ISO 8601)
 */
public enum JsDateTimeFormat {

    /**
     * provide formatter for Date only. <br/>
     * For example : 2007-10-29
     */
    DATE_ONLY(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
    /**
     * provide formatter for Date with time. <br/>
     * For example : 2010-03-15T12:34:56
     */
    DATE_TIME(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
    /**
     * provide formatter for Date with time and fraction of second.<br/>
     * For example : 2010-03-15T12:34:56.005
     */
    DATE_TIME_WITH_MILISECONDS(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S")),
    /**
     * provide formatter for Date with time, fraction of second and offset. (full ISO 8601)<br/>
     * For example : 2010-03-15T12:34:56+01:00
     */
    DATE_TIME_WITH_MILISECONDS_TIMEZONE(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

    @Getter
    private final DateTimeFormatter formatter;

    JsDateTimeFormat(final DateTimeFormatter value) {
        formatter = requireNonNull(value, "Formatter must not be null");
    }

    /**
     * Formats the given {@link TemporalAccessor} with the contained {@link DateTimeFormatter}
     *
     * @param temporalAccessor to be formatted
     * @return the formatted temporalAccessor or {@code null} if given temporalAccessor is
     *         {@code null}
     */
    public String format(final TemporalAccessor temporalAccessor) {
        if (null == temporalAccessor) {
            return null;
        }
        return formatter.format(temporalAccessor);
    }
}
