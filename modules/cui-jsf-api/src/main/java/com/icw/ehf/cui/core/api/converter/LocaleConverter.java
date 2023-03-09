package com.icw.ehf.cui.core.api.converter;

import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

/**
 * Converts a {@link Locale} to String and vice versa
 *
 * @author Oliver Wolff
 *
 */
public class LocaleConverter extends AbstractConverter<Locale> {

    @Override
    protected String convertToString(FacesContext context, UIComponent component, Locale value)
        throws ConverterException {
        return value.toLanguageTag();
    }

    @Override
    protected Locale convertToObject(FacesContext context, UIComponent component, String value)
        throws ConverterException {
        return Locale.forLanguageTag(value);
    }
}
