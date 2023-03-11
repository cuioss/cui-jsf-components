package de.cuioss.jsf.api.converter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 * Extends standard {@link javax.faces.convert.DateTimeConverter} to support {@link ZonedDateTime}.
 * <p>
 * Accepts {@link Date}, {@link LocalDateTime} and {@link ZonedDateTime} as input, returns as
 * {@link ZonedDateTime}.
 *
 * @author Matthias Walliczek
 */
@FacesConverter(value = "de.cuioss.jsf.components.converter.DateTimeConverter")
public class CuiDateTimeConverter extends javax.faces.convert.DateTimeConverter {

    /**
     * @throws ConverterException {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component,
            Object value) {

        if (value instanceof ZonedDateTime) {
            var instant = ((ZonedDateTime) value).toInstant();
            return super.getAsString(context, component, Date.from(instant));
        }
        if (value instanceof LocalDateTime) {
            var instant = ((LocalDateTime) value).atZone(ZoneId.of(getTimeZone().getID())).toInstant();
            return super.getAsString(context, component, Date.from(instant));
        }
        if (value instanceof LocalDate) {
            var instant = ((LocalDate) value).atStartOfDay(ZoneId.of(getTimeZone().getID())).toInstant();
            return super.getAsString(context, component, Date.from(instant));
        }
        return super.getAsString(context, component, value);
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        var result = super.getAsObject(context, component, value);
        if (result instanceof Date) {
            var instant = Instant.ofEpochMilli(((Date) result).getTime());
            return ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
        }
        return result;
    }

}
