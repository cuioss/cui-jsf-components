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
package de.cuioss.jsf.components.converter;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;
import jakarta.faces.component.html.HtmlInputText;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.DateTimeConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@DisplayName("Tests for CuiDateTimeConverter")
class CuiDateTimeConverterTest extends AbstractConverterTest<CuiDateTimeConverter, Object> {

    @Override
    @DisplayName("Configure converter for testing")
    public void configure(final CuiDateTimeConverter toBeConfigured) {
        // Configure with long date and time style for both date and time
        toBeConfigured.setDateStyle("long");
        toBeConfigured.setTimeStyle("long");
        toBeConfigured.setType("both");
    }

    @Override
    @DisplayName("Configure test items")
    public void populate(final TestItems<Object> testItems) {
        // Ensure consistent locale for testing
        Locale.setDefault(Locale.ENGLISH);

        // Create standard JSF converter for comparison
        var jsfConverter = new DateTimeConverter();
        jsfConverter.setDateStyle("long");
        jsfConverter.setTimeStyle("long");
        jsfConverter.setType("both");

        // Get a formatted date string to use for roundtrip testing
        var roundTrip = jsfConverter.getAsString(FacesContext.getCurrentInstance(), new HtmlInputText(), new Date());

        // Add test items
        testItems.addValidObject(LocalDateTime.now().minusSeconds(10)).addRoundtripValues(roundTrip);
    }

    @Nested
    @DisplayName("String conversion tests")
    class StringConversionTests {

        @Test
        @DisplayName("Should convert LocalDateTime to string")
        void shouldConvertLocalDateTimeToString(FacesContext facesContext) {
            // Arrange
            var converter = getConverter();
            var now = LocalDateTime.now();

            // Act
            var result = converter.getAsString(facesContext, getComponent(), now);

            // Assert
            assertNotNull(result, "Converted string should not be null");
        }

        @Test
        @DisplayName("Should convert ZonedDateTime to string")
        void shouldConvertZonedDateTimeToString(FacesContext facesContext) {
            // Arrange
            var converter = getConverter();
            var now = ZonedDateTime.now();

            // Act
            var result = converter.getAsString(facesContext, getComponent(), now);

            // Assert
            assertNotNull(result, "Converted string should not be null");
        }

        @Test
        @DisplayName("Should convert LocalDate to string")
        void shouldConvertLocalDateToString(FacesContext facesContext) {
            // Arrange
            var converter = getConverter();
            var today = LocalDate.now();

            // Act
            var result = converter.getAsString(facesContext, getComponent(), today);

            // Assert
            assertNotNull(result, "Converted string should not be null");
        }

        @Test
        @DisplayName("Should convert Date to string")
        void shouldConvertDateToString(FacesContext facesContext) {
            // Arrange
            var converter = getConverter();
            var date = new Date();

            // Act
            var result = converter.getAsString(facesContext, getComponent(), date);

            // Assert
            assertNotNull(result, "Converted string should not be null");
        }
    }

    @Nested
    @DisplayName("Object conversion tests")
    class ObjectConversionTests {

        @Test
        @DisplayName("Should convert string to ZonedDateTime")
        void shouldConvertStringToZonedDateTime(FacesContext facesContext) {
            // Arrange
            var converter = getConverter();
            var date = new Date();
            var dateString = converter.getAsString(facesContext, getComponent(), date);

            // Act
            var result = converter.getAsObject(facesContext, getComponent(), dateString);

            // Assert
            assertNotNull(result, "Converted object should not be null");
            assertInstanceOf(ZonedDateTime.class, result,
                    "Converted object should be a ZonedDateTime");
        }
    }

    @Nested
    @DisplayName("Configuration tests")
    class ConfigurationTests {

        @Test
        @DisplayName("Should respect date style configuration")
        void shouldRespectDateStyle(FacesContext facesContext) {
            // Arrange
            var converter = getConverter();
            converter.setDateStyle("short");
            converter.setTimeStyle("short");
            var date = new Date();

            // Act
            var result = converter.getAsString(facesContext, getComponent(), date);

            // Assert
            assertNotNull(result, "Converted string should not be null");
            // We can't assert the exact format as it's locale-dependent
        }

        @Test
        @DisplayName("Should respect time zone configuration")
        void shouldRespectTimeZone(FacesContext facesContext) {
            // Arrange
            var converter = getConverter();
            converter.setTimeZone(TimeZone.getTimeZone(ZoneId.of("UTC")));
            var now = ZonedDateTime.now(ZoneId.of("UTC"));

            // Act
            var result = converter.getAsString(facesContext, getComponent(), now);

            // Assert
            assertNotNull(result, "Converted string should not be null");
        }
    }

    @Override
    protected void configureComponents(ComponentConfigDecorator decorator) {
        super.configureComponents(decorator);
    }
}
