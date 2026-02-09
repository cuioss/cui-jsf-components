/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.jqplot.js.types;

import static java.util.Objects.requireNonNull;

import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 * To be able to work with JavaScript date time the date need to be fit's to
 * defined from. This Enum provide prepared the most popular formatter for Joda
 * DateTime.
 *
 * @author Eugen Fischer
 * @see <a href="http://sandbox.kendsnyder.com/date/?q=sandbox/date" >Date</a>
 *      2007-10-29 (Date only) 2010-03-15T12:34:56.005 (with miliseconds)
 *      2010-03-15T12:34:56+01:00 (full ISO 8601)
 */
public enum JsDateTimeFormat {

    /**
     * provide formatter for Date only. <br>
     * For example : 2007-10-29
     */
    DATE_ONLY(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
    /**
     * provide formatter for Date with time. <br>
     * For example : 2010-03-15T12:34:56
     */
    DATE_TIME(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
    /**
     * provide formatter for Date with time and fraction of second.<br>
     * For example : 2010-03-15T12:34:56.005
     */
    DATE_TIME_WITH_MILISECONDS(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S")),
    /**
     * provide formatter for Date with time, fraction of second and offset. (full
     * ISO 8601)<br>
     * For example : 2010-03-15T12:34:56+01:00
     */
    DATE_TIME_WITH_MILISECONDS_TIMEZONE(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

    @Getter
    private final DateTimeFormatter formatter;

    JsDateTimeFormat(final DateTimeFormatter value) {
        formatter = requireNonNull(value, "Formatter must not be null");
    }

    /**
     * Formats the given {@link TemporalAccessor} with the contained
     * {@link DateTimeFormatter}
     *
     * @param temporalAccessor to be formatted
     * @return the formatted temporalAccessor or {@code null} if given
     *         temporalAccessor is {@code null}
     */
    public String format(final TemporalAccessor temporalAccessor) {
        if (null == temporalAccessor) {
            return null;
        }
        return formatter.format(temporalAccessor);
    }
}
