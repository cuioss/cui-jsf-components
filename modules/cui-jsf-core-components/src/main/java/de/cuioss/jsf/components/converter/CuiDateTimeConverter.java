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

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.DateTimeConverter;
import jakarta.faces.convert.FacesConverter;

import java.time.*;
import java.util.Date;

/**
 * <p>Enhanced date/time converter that extends the standard JSF {@link DateTimeConverter}
 * to add support for Java 8+ date/time API types. This converter bridges the gap between
 * the legacy {@link Date} handling in JSF and modern Java date/time types.</p>
 * 
 * <p>The converter supports the following input types:</p>
 * <ul>
 *   <li>{@link Date} - Standard Java legacy date (fully supported by parent converter)</li>
 *   <li>{@link ZonedDateTime} - Date and time with timezone information</li>
 *   <li>{@link LocalDateTime} - Date and time without timezone information</li>
 *   <li>{@link LocalDate} - Date without time components</li>
 * </ul>
 * 
 * <p>When converting from string to object (parse mode), the converter returns a
 * {@link ZonedDateTime} instance, providing full timezone support. The timezone
 * is determined from the converter's configured timezone setting.</p>
 * 
 * <p>All formatting options from the standard JSF DateTimeConverter are supported,
 * including:</p>
 * <ul>
 *   <li>dateStyle - (default, short, medium, long, full)</li>
 *   <li>timeStyle - (default, short, medium, long, full)</li>
 *   <li>pattern - Custom date/time pattern</li>
 *   <li>locale - Specific locale for formatting</li>
 *   <li>timeZone - Specific timezone for formatting</li>
 * </ul>
 * 
 * <h2>Usage</h2>
 * 
 * <pre>
 * &lt;h:outputText value="#{bean.zonedDateTime}"&gt;
 *     &lt;f:converter converterId="de.cuioss.jsf.components.converter.DateTimeConverter" /&gt;
 *     &lt;f:attribute name="pattern" value="yyyy-MM-dd HH:mm:ss Z" /&gt;
 * &lt;/h:outputText&gt;
 * </pre>
 * 
 * <p>This converter is thread-safe as it extends the thread-safe standard JSF DateTimeConverter.</p>
 *
 * @author Matthias Walliczek
 * @see DateTimeConverter The parent class providing the base functionality
 * @see ZonedDateTime Primary return type for parsed dates
 * @since 1.0
 */
@FacesConverter(value = "de.cuioss.jsf.components.converter.DateTimeConverter")
public class CuiDateTimeConverter extends DateTimeConverter {

    /**
     * <p>Converts a date/time object to its string representation. This method extends
     * the standard JSF date/time conversion by adding support for Java 8+ date/time types.</p>
     * 
     * <p>The method handles the following types:</p>
     * <ul>
     *   <li>{@link ZonedDateTime} - Converted using its Instant representation</li>
     *   <li>{@link LocalDateTime} - Converted using the converter's timezone</li>
     *   <li>{@link LocalDate} - Converted as start of day in the converter's timezone</li>
     *   <li>Other types - Delegated to the parent converter</li>
     * </ul>
     *
     * @param context The FacesContext for the current request, not null
     * @param component The component associated with this converter, not null
     * @param value The value to be converted (may be null)
     * @return A formatted string representation of the date value, or null if value is null
     * @throws ConverterException If the conversion fails
     * @throws NullPointerException If context or component is null
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {

        if (value instanceof ZonedDateTime time) {
            var instant = time.toInstant();
            return super.getAsString(context, component, Date.from(instant));
        }
        if (value instanceof LocalDateTime time) {
            var instant = time.atZone(ZoneId.of(getTimeZone().getID())).toInstant();
            return super.getAsString(context, component, Date.from(instant));
        }
        if (value instanceof LocalDate date) {
            var instant = date.atStartOfDay(ZoneId.of(getTimeZone().getID())).toInstant();
            return super.getAsString(context, component, Date.from(instant));
        }
        return super.getAsString(context, component, value);
    }

    /**
     * <p>Converts a string to a date/time object. This method parses the string
     * representation of a date using the standard JSF date/time conversion and
     * then converts the resulting {@link Date} to a {@link ZonedDateTime}.</p>
     * 
     * <p>If the input cannot be parsed or is null, the method returns null.</p>
     *
     * @param context The FacesContext for the current request, not null
     * @param component The component associated with this converter, not null
     * @param value The string value to be converted
     * @return A ZonedDateTime object representing the parsed date, or null if parsing fails
     * @throws ConverterException If the conversion fails
     */
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        var result = super.getAsObject(context, component, value);
        if (result instanceof Date date) {
            var instant = Instant.ofEpochMilli(date.getTime());
            return ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
        }
        return result;
    }

}
