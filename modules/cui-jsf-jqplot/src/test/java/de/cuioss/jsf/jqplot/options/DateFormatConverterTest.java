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
package de.cuioss.jsf.jqplot.options;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for DateFormatConverter class")
class DateFormatConverterTest {

    @Nested
    @DisplayName("Year format conversion tests")
    class YearFormatTests {

        @Test
        @DisplayName("Should convert year formats correctly")
        void shouldConvertYearFormatsCorrectly() {
            // Arrange & Act & Assert
            // 4-digit year formats
            assertEquals("%Y", DateFormatConverter.convertToJavaScriptDateFormat("yyyy"),
                    "Should convert lowercase 4-digit year");
            assertEquals("%Y", DateFormatConverter.convertToJavaScriptDateFormat("YYYY"),
                    "Should convert uppercase 4-digit year");

            // 2-digit year formats
            assertEquals("%y", DateFormatConverter.convertToJavaScriptDateFormat("yy"),
                    "Should convert lowercase 2-digit year");
            assertEquals("%y", DateFormatConverter.convertToJavaScriptDateFormat("YY"),
                    "Should convert uppercase 2-digit year");
        }
    }

    @Nested
    @DisplayName("Month format conversion tests")
    class MonthFormatTests {

        @Test
        @DisplayName("Should convert month formats correctly")
        void shouldConvertMonthFormatsCorrectly() {
            // Arrange & Act & Assert
            // Full month name
            assertEquals("%B", DateFormatConverter.convertToJavaScriptDateFormat("MMMM"),
                    "Should convert full month name format");

            // Abbreviated month name
            assertEquals("%b", DateFormatConverter.convertToJavaScriptDateFormat("MMM"),
                    "Should convert abbreviated month name format");

            // 2-digit month
            assertEquals("%m", DateFormatConverter.convertToJavaScriptDateFormat("MM"),
                    "Should convert 2-digit month format");

            // 1-digit month
            assertEquals("%#m", DateFormatConverter.convertToJavaScriptDateFormat("M"),
                    "Should convert 1-digit month format");
        }
    }

    @Nested
    @DisplayName("Day format conversion tests")
    class DayFormatTests {

        @Test
        @DisplayName("Should convert day formats correctly")
        void shouldConvertDayFormatsCorrectly() {
            // Arrange & Act & Assert
            // Full day name
            assertEquals("%A", DateFormatConverter.convertToJavaScriptDateFormat("dddd"),
                    "Should convert full day name format (dddd)");

            // Abbreviated day name
            assertEquals("%a", DateFormatConverter.convertToJavaScriptDateFormat("ddd"),
                    "Should convert abbreviated day name format (ddd)");
            assertEquals("%a", DateFormatConverter.convertToJavaScriptDateFormat("DDD"),
                    "Should convert abbreviated day name format (DDD)");

            // 2-digit day
            assertEquals("%d", DateFormatConverter.convertToJavaScriptDateFormat("dd"),
                    "Should convert 2-digit day format (dd)");
            assertEquals("%d", DateFormatConverter.convertToJavaScriptDateFormat("DD"),
                    "Should convert 2-digit day format (DD)");

            // 1-digit day
            assertEquals("%#d", DateFormatConverter.convertToJavaScriptDateFormat("d"),
                    "Should convert 1-digit day format (d)");
            assertEquals("%#d", DateFormatConverter.convertToJavaScriptDateFormat("D"),
                    "Should convert 1-digit day format (D)");

            // Day name in week
            assertEquals("%A", DateFormatConverter.convertToJavaScriptDateFormat("E"),
                    "Should convert day name in week format");

            // Day of week in month
            assertEquals("%w", DateFormatConverter.convertToJavaScriptDateFormat("F"),
                    "Should convert day of week in month format");

            // Day number of week
            assertEquals("%o", DateFormatConverter.convertToJavaScriptDateFormat("u"),
                    "Should convert day number of week format");
        }
    }

    @Nested
    @DisplayName("Time format conversion tests")
    class TimeFormatTests {

        @Test
        @DisplayName("Should convert hour formats correctly")
        void shouldConvertHourFormatsCorrectly() {
            // Arrange & Act & Assert
            // 12-hour format
            assertEquals("%#I", DateFormatConverter.convertToJavaScriptDateFormat("hh"),
                    "Should convert 2-digit 12-hour format");
            assertEquals("%I", DateFormatConverter.convertToJavaScriptDateFormat("h"),
                    "Should convert 1-digit 12-hour format");

            // 24-hour format
            assertEquals("%H", DateFormatConverter.convertToJavaScriptDateFormat("HH"),
                    "Should convert 2-digit 24-hour format");
            assertEquals("%#H", DateFormatConverter.convertToJavaScriptDateFormat("H"),
                    "Should convert 1-digit 24-hour format");
            assertEquals("%#H", DateFormatConverter.convertToJavaScriptDateFormat("k"),
                    "Should convert hour in day (1-24) format");
        }

        @Test
        @DisplayName("Should convert minute formats correctly")
        void shouldConvertMinuteFormatsCorrectly() {
            // Arrange & Act & Assert
            assertEquals("%M", DateFormatConverter.convertToJavaScriptDateFormat("mm"),
                    "Should convert 2-digit minute format");
            assertEquals("%#M", DateFormatConverter.convertToJavaScriptDateFormat("m"),
                    "Should convert 1-digit minute format");
        }

        @Test
        @DisplayName("Should convert second formats correctly")
        void shouldConvertSecondFormatsCorrectly() {
            // Arrange & Act & Assert
            assertEquals("%S", DateFormatConverter.convertToJavaScriptDateFormat("ss"),
                    "Should convert 2-digit second format");
            assertEquals("%#S", DateFormatConverter.convertToJavaScriptDateFormat("s"),
                    "Should convert 1-digit second format");
        }

        @Test
        @DisplayName("Should convert millisecond formats correctly")
        void shouldConvertMillisecondFormatsCorrectly() {
            // Arrange & Act & Assert
            assertEquals("%N", DateFormatConverter.convertToJavaScriptDateFormat("SSS"),
                    "Should convert 3-digit millisecond format");
            assertEquals("%#N", DateFormatConverter.convertToJavaScriptDateFormat("S"),
                    "Should convert 1-digit millisecond format");
        }
    }

    @Nested
    @DisplayName("Time zone format conversion tests")
    class TimeZoneFormatTests {

        @Test
        @DisplayName("Should convert time zone formats correctly")
        void shouldConvertTimeZoneFormatsCorrectly() {
            // Arrange & Act & Assert
            assertEquals("%O", DateFormatConverter.convertToJavaScriptDateFormat("z"),
                    "Should convert general time zone format");
            assertEquals("%Z", DateFormatConverter.convertToJavaScriptDateFormat("Z"),
                    "Should convert RFC 822 time zone format");
            assertEquals("%G", DateFormatConverter.convertToJavaScriptDateFormat("ZZ"),
                    "Should convert ISO 8601 time zone format");
        }
    }

    @Nested
    @DisplayName("Complex format conversion tests")
    class ComplexFormatTests {

        @Test
        @DisplayName("Should convert complex date-time formats correctly")
        void shouldConvertComplexFormatsCorrectly() {
            // Arrange
            String javaFormat = "yyyy-MM-dd'T'HH:mm:ssZZ";
            String expectedJsFormat = "%Y-%m-%d'T'%H:%M:%S%G";

            // Act
            String actualJsFormat = DateFormatConverter.convertToJavaScriptDateFormat(javaFormat);

            // Assert
            assertEquals(expectedJsFormat, actualJsFormat,
                    "Should convert ISO 8601 date-time format correctly");
        }
    }
}
