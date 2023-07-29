package de.cuioss.jsf.jqplot.options;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DateFormatConverterTest {

    @Test
    void shouldHandleDifferentYear() {
        // YYYY or yyyy
        // YY or yy two letters
        assertEquals("%Y", DateFormatConverter.convertToJavaScriptDateFormat("yyyy"));
        assertEquals("%Y", DateFormatConverter.convertToJavaScriptDateFormat("YYYY"));
        assertEquals("%y", DateFormatConverter.convertToJavaScriptDateFormat("yy"));
        assertEquals("%y", DateFormatConverter.convertToJavaScriptDateFormat("YY"));
    }

    @Test
    void shouldHandleDifferentMonth() {
        // If the number of pattern letters is 3 or more, the month is interpreted as
        // text
        assertEquals("%B", DateFormatConverter.convertToJavaScriptDateFormat("MMMM"));
        assertEquals("%b", DateFormatConverter.convertToJavaScriptDateFormat("MMM"));
        assertEquals("%m", DateFormatConverter.convertToJavaScriptDateFormat("MM"));
        assertEquals("%#m", DateFormatConverter.convertToJavaScriptDateFormat("M"));
    }

    /**
     * D Day in year Number 189 d Day in month Number 10 F Day of week in month
     * Number 2 E Day name in week Text Tuesday; Tue u Day number of week (1 =
     * Monday, ..., 7 = Sunday) Number 1
     */
    @Test
    void shouldHandleDifferentDay() {
        assertEquals("%A", DateFormatConverter.convertToJavaScriptDateFormat("dddd"));
        assertEquals("%a", DateFormatConverter.convertToJavaScriptDateFormat("ddd"));
        assertEquals("%a", DateFormatConverter.convertToJavaScriptDateFormat("DDD"));
        assertEquals("%d", DateFormatConverter.convertToJavaScriptDateFormat("dd"));
        assertEquals("%d", DateFormatConverter.convertToJavaScriptDateFormat("DD"));
        assertEquals("%#d", DateFormatConverter.convertToJavaScriptDateFormat("d"));
        assertEquals("%#d", DateFormatConverter.convertToJavaScriptDateFormat("D"));
        assertEquals("%A", DateFormatConverter.convertToJavaScriptDateFormat("E"));
        assertEquals("%w", DateFormatConverter.convertToJavaScriptDateFormat("F"));
        assertEquals("%o", DateFormatConverter.convertToJavaScriptDateFormat("u"));
    }

    @Test
    void shouldHandleDifferentHours() {
        assertEquals("%#I", DateFormatConverter.convertToJavaScriptDateFormat("hh"));
        assertEquals("%I", DateFormatConverter.convertToJavaScriptDateFormat("h"));
        assertEquals("%H", DateFormatConverter.convertToJavaScriptDateFormat("HH"));
        assertEquals("%#H", DateFormatConverter.convertToJavaScriptDateFormat("H"));
        assertEquals("%#H", DateFormatConverter.convertToJavaScriptDateFormat("k"));
    }

    @Test
    void shouldHandleDifferentMinutes() {
        assertEquals("%M", DateFormatConverter.convertToJavaScriptDateFormat("mm"));
        assertEquals("%#M", DateFormatConverter.convertToJavaScriptDateFormat("m"));
    }

    @Test
    void shouldHandleDifferentSeconds() {
        assertEquals("%S", DateFormatConverter.convertToJavaScriptDateFormat("ss"));
        assertEquals("%#S", DateFormatConverter.convertToJavaScriptDateFormat("s"));
    }

    @Test
    void shouldHandleDifferentMilliSeconds() {
        assertEquals("%N", DateFormatConverter.convertToJavaScriptDateFormat("SSS"));
        assertEquals("%#N", DateFormatConverter.convertToJavaScriptDateFormat("S"));
    }

    @Test
    void shouldHandleDifferentTimeZones() {
        assertEquals("%O", DateFormatConverter.convertToJavaScriptDateFormat("z"));
        assertEquals("%Z", DateFormatConverter.convertToJavaScriptDateFormat("Z"));
        assertEquals("%G", DateFormatConverter.convertToJavaScriptDateFormat("ZZ"));
    }

    @Test
    void shouldHandleComplex() {
        assertEquals("%Y-%m-%d'T'%H:%M:%S%G",
                DateFormatConverter.convertToJavaScriptDateFormat("yyyy-MM-dd'T'HH:mm:ssZZ"));
    }
}
