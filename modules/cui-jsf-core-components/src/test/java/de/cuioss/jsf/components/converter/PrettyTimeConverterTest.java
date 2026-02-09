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
package de.cuioss.jsf.components.converter;

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;
import de.cuioss.test.jsf.defaults.BasicApplicationConfiguration;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.ConverterException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

@JsfTestConfiguration(BasicApplicationConfiguration.class)
@DisplayName("Tests for PrettyTimeConverter")
class PrettyTimeConverterTest extends AbstractConverterTest<PrettyTimeConverter, Object> {

    private static final String MOMENTS_AGO = "moments ago";

    @Override
    @DisplayName("Configure test items for pretty time conversion")
    public void populate(final TestItems<Object> testItems) {
        // Test Date objects
        testItems.addValidObjectWithStringResult(new Date(System.currentTimeMillis() - 10000), MOMENTS_AGO);

        // Test Calendar objects
        testItems.addValidObjectWithStringResult(Calendar.getInstance(), MOMENTS_AGO);

        // Test LocalDate objects
        testItems.addValidObject(LocalDate.now());

        // Test ZonedDateTime objects
        testItems.addValidObject(ZonedDateTime.now());

        // Test LocalDateTime objects
        testItems.addValidObjectWithStringResult(LocalDateTime.now().minusSeconds(10), MOMENTS_AGO);

        // Test invalid objects
        testItems.addInvalidObject(2);
        testItems.addInvalidObject("not a date");
        testItems.addInvalidObject(Boolean.TRUE);
    }

    @Test
    @DisplayName("Should convert Date object to pretty time string")
    void shouldConvertDateObject(FacesContext facesContext) {
        // Arrange
        var converter = new PrettyTimeConverter();
        var component = getComponent();
        var date = new Date(System.currentTimeMillis() - 10000); // 10 seconds ago

        // Act
        var result = converter.getAsString(facesContext, component, date);

        // Assert
        assertEquals(MOMENTS_AGO, result, "Should convert recent Date to 'moments ago'");
    }

    @Test
    @DisplayName("Should convert Calendar object to pretty time string")
    void shouldConvertCalendarObject(FacesContext facesContext) {
        // Arrange
        var converter = new PrettyTimeConverter();
        var component = getComponent();
        var calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, -10); // 10 seconds ago

        // Act
        var result = converter.getAsString(facesContext, component, calendar);

        // Assert
        assertEquals(MOMENTS_AGO, result, "Should convert recent Calendar to 'moments ago'");
    }

    @Test
    @DisplayName("Should convert LocalDateTime object to pretty time string")
    void shouldConvertLocalDateTimeObject(FacesContext facesContext) {
        // Arrange
        var converter = new PrettyTimeConverter();
        var component = getComponent();
        var localDateTime = LocalDateTime.now().minusSeconds(10); // 10 seconds ago

        // Act
        var result = converter.getAsString(facesContext, component, localDateTime);

        // Assert
        assertEquals(MOMENTS_AGO, result, "Should convert recent LocalDateTime to 'moments ago'");
    }

    @Test
    @DisplayName("Should convert ZonedDateTime object to pretty time string")
    void shouldConvertZonedDateTimeObject(FacesContext facesContext) {
        // Arrange
        var converter = new PrettyTimeConverter();
        var component = getComponent();
        var zonedDateTime = ZonedDateTime.now().minusSeconds(10); // 10 seconds ago

        // Act
        var result = converter.getAsString(facesContext, component, zonedDateTime);

        // Assert
        assertEquals(MOMENTS_AGO, result, "Should convert recent ZonedDateTime to 'moments ago'");
    }

    @Test
    @DisplayName("Should convert LocalDate object to pretty time string")
    void shouldConvertLocalDateObject(FacesContext facesContext) {
        // Arrange
        var converter = new PrettyTimeConverter();
        var component = getComponent();
        var localDate = LocalDate.now(); // Today

        // Act
        var result = converter.getAsString(facesContext, component, localDate);

        // Assert
        // Since LocalDate is converted to start of day, the result depends on the current time
        // It could be "today", "moments ago", "x hours ago", etc.
        assertTrue(result.contains("ago") || result.contains("today"),
                "Should convert LocalDate to a relative time string");
    }

    @Test
    @DisplayName("Should throw ConverterException for unsupported types")
    void shouldThrowExceptionForUnsupportedTypes(FacesContext facesContext) {
        // Arrange
        var converter = new PrettyTimeConverter();
        var component = getComponent();
        var invalidObject = Integer.valueOf(42);

        // Act & Assert
        var exception = assertThrows(ConverterException.class,
                () -> converter.getAsString(facesContext, component, invalidObject),
                "Should throw ConverterException for Integer object");

        assertTrue(exception.getMessage().contains("Invalid Type given"),
                "Exception message should indicate invalid type");
        assertTrue(exception.getMessage().contains("Integer"),
                "Exception message should mention the actual type");
    }

    @Test
    @DisplayName("Should handle null value")
    void shouldHandleNullValue(FacesContext facesContext) {
        // Arrange
        var converter = new PrettyTimeConverter();
        var component = getComponent();

        // Act
        var result = converter.getAsString(facesContext, component, null);

        // Assert
        assertEquals("", result, "Should return empty string for null value");
    }
}
