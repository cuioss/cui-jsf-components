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

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;

/**
 * Extends standard {@link jakarta.faces.convert.DateTimeConverter} to support
 * {@link ZonedDateTime}.
 * <p>
 * Accepts {@link Date}, {@link LocalDateTime} and {@link ZonedDateTime} as
 * input, returns as {@link ZonedDateTime}.
 *
 * @author Matthias Walliczek
 */
@FacesConverter(value = "de.cuioss.jsf.components.converter.DateTimeConverter")
public class CuiDateTimeConverter extends jakarta.faces.convert.DateTimeConverter {

    /**
     * @throws ConverterException   {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
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
