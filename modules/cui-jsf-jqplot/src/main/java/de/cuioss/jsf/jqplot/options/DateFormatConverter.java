/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.jqplot.options;

import de.cuioss.tools.string.MoreStrings;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;
import static de.cuioss.tools.collect.CollectionLiterals.immutableSortedSet;
import static de.cuioss.tools.collect.CollectionLiterals.mutableList;
import static de.cuioss.tools.string.MoreStrings.requireNotEmpty;

/**
 * Supported java keys :
 * <a href="http://joda-time.sourceforge.net/apidocs/org/joda/time/format/DateTimeFormat.html">...</a>
 * Supported java Script : keys
 * <a href="http://sandbox.kendsnyder.com/date/?q=sandbox/date/#src">...</a>
 *
 * @author Eugen Fischer
 */
public class DateFormatConverter {

    @ToString
    @SuppressWarnings("squid:S2386") // owolff: all public collection-types are immutable
    private enum DatePart {

        YEAR_FOUR("yyyy", "%Y"), YEAR_FOUR_BIG("YYYY", "%Y"),

        YEAR_TWO("yy", "%y"), YEAR_TWO_BIG("YY", "%y"),

        YEAR_ONE("y", "%Y"), YEAR_ONE_BIG("Y", "%Y"),

        MONTH_FOUR("MMMM", "%B"), MONTH_THREE("MMM", "%b"), MONTH_TWO("MM", "%m"), MONTH_ONE("M", "%#m"),

        DAY_FOUR("dddd", "%A"), DAY_THREE("ddd", "%a"), DAY_THREE_BIG("DDD", "%a"), DAY_TWO("dd", "%d"),
        DAY_TWO_BIG("DD", "%d"), DAY_ONE("d", "%#d"), DAY_ONE_BIG("D", "%#d"), DAY_OF_WEEK("E", "%A"),
        DAY_OF_WEEK_NUMBER("F", "%w"), DAY_OF_MONTH("u", "%o"),

        HOUR_TWO_12("hh", "%#I"), HOUR_ONE_12("h", "%I"), HOUR_TWO_24("HH", "%H"), HOUR_ONE_24("H", "%#H"),
        HOUR_CLOCK("k", "%#H"),

        HOUR_SUFFIX("a", "%p"),

        MINUTE_TWO("mm", "%M"), MINUTE_ONE("m", "%#M"),

        SECOND_TWO("ss", "%S"), SECOND_ONE("s", "%#S"),

        MILLISECONDS_THREE("SSS", "%N"), MILLISECONDS_ONE("S", "%#N"),

        TIME_ZONE_IN_MINUTES("z", "%O"), TIME_ZONE_HOURS("ZZ", "%G"), TIME_ZONE_NAME("Z", "%Z");

        @Getter
        private final String javaFormatCode;

        @Getter
        private final String jsFormatCode;

        public static final Set<DatePart> HOURS_SUFFIX = immutableSortedSet(HOUR_SUFFIX);

        public static final Set<DatePart> YEARS = immutableSortedSet(YEAR_FOUR, YEAR_FOUR_BIG, YEAR_TWO, YEAR_TWO_BIG,
                YEAR_ONE, YEAR_ONE_BIG);

        public static final Set<DatePart> MONTHS = immutableSortedSet(MONTH_FOUR, MONTH_THREE, MONTH_TWO, MONTH_ONE);

        public static final Set<DatePart> DAYS = immutableSortedSet(DAY_FOUR, DAY_THREE, DAY_THREE_BIG, DAY_TWO,
                DAY_TWO_BIG, DAY_ONE, DAY_ONE_BIG, DAY_OF_WEEK, DAY_OF_WEEK_NUMBER, DAY_OF_MONTH);

        public static final Set<DatePart> HOURS = immutableSortedSet(HOUR_TWO_12, HOUR_ONE_12, HOUR_TWO_24, HOUR_ONE_24,
                HOUR_CLOCK);

        public static final Set<DatePart> MINUTES = immutableSortedSet(MINUTE_TWO, MINUTE_ONE);

        public static final Set<DatePart> SECONDS = immutableSortedSet(SECOND_TWO, SECOND_ONE);

        public static final Set<DatePart> MILLISECONDS = immutableSortedSet(MILLISECONDS_THREE, MILLISECONDS_ONE);

        public static final Set<DatePart> TIMEZONES = immutableSortedSet(TIME_ZONE_HOURS, TIME_ZONE_NAME,
                TIME_ZONE_IN_MINUTES);

        DatePart(final String javaFormat, final String javaScriptFormat) {
            javaFormatCode = requireNotEmpty(javaFormat);
            jsFormatCode = requireNotEmpty(javaScriptFormat);
        }

    }

    private static final List<Set<DatePart>> TACTICS = immutableList(DatePart.HOURS_SUFFIX, DatePart.YEARS,
            DatePart.MONTHS, DatePart.DAYS, DatePart.HOURS, DatePart.MINUTES, DatePart.MILLISECONDS, DatePart.SECONDS,
            DatePart.TIMEZONES);

    /**
     * Convert java / joda date time format to javaScript date time format
     *
     * @param javaDateFormat value of java date time pattern
     * @return javaScript date time pattern
     */
    public static String convertToJavaScriptDateFormat(final String javaDateFormat) {

        final var data = MoreStrings.emptyToNull(javaDateFormat);

        if (null == data) {
            return javaDateFormat;
        }

        return replaceAll(data, new ArrayList<>(mutableList(TACTICS)));
    }

    /**
     * Recursive method
     *
     * @param target  string which should be processed
     * @param tactics list if tactics, each recursion gets less
     * @return replaces
     */
    private static String replaceAll(final String target, final List<Set<DatePart>> tactics) {

        if (tactics.isEmpty()) {
            return target;
        }
        // get out a first tactic set
        final var tactic = tactics.remove(0);
        // process the replacement and call recursion with a reduced tactics list
        return replaceAll(replace(target, tactic), tactics);
    }

    /**
     * Replace java pattern token by javaScript pattern token
     *
     * @param target
     * @param tactic EnumSet<DatePart> use the first which is fitting
     * @return replaced
     */
    private static String replace(final String target, final Set<DatePart> tactic) {
        var result = target;
        for (final DatePart part : tactic) {
            final var javaFormat = part.getJavaFormatCode();
            if (target.contains(javaFormat) && !alreadyReplaced(target, javaFormat)) {
                result = target.replaceFirst(javaFormat, part.getJsFormatCode());
                break;
            }
        }
        return result;
    }

    /**
     * Verify if replace already done
     *
     * @param value        String
     * @param replaceValue String
     * @return {@code true} if already done, {@code false} otherwise
     */
    private static boolean alreadyReplaced(final String value, final String replaceValue) {
        // begin the first occurrence. -1 means not found at all
        final var indexOf = value.indexOf(replaceValue);
        if (indexOf < 0) {
            return false;
        }
        // calculate up to 1 character before
        final var beginIndex = Math.max(indexOf - 1, 0);

        // if the prefix includes "%" or "#" than no it is already replaced
        final var prefix = value.substring(beginIndex, indexOf);
        return prefix.startsWith("%") || prefix.startsWith("#");
    }
}
