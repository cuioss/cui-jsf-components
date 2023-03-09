package com.icw.ehf.cui.core.api.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.icw.ehf.cui.core.api.security.CuiSanitizer;

/**
 * String converter that uses {@link CuiSanitizer#PLAIN_TEXT} as sanitizing-strategy, saying all
 * html will be stripped, but the special characters are preserved
 *
 * @author Oliver Wolff
 */
@FacesConverter(PlainTextSanitizerConverter.CONVERTER_ID)
public class PlainTextSanitizerConverter extends AbstractConverter<String> {

    /** com.icw.ehf.cui.core.api.converter.PlainTextSanitizerConverter. */
    public static final String CONVERTER_ID =
        "com.icw.ehf.cui.core.api.converter.PlainTextSanitizerConverter";

    @Override
    protected String convertToString(final FacesContext context, final UIComponent component,
            final String value)
        throws ConverterException {
        return CuiSanitizer.PLAIN_TEXT_PRESERVE_ENTITIES.apply(value);
    }

    @Override
    protected String convertToObject(final FacesContext context, final UIComponent component,
            final String value)
        throws ConverterException {
        return CuiSanitizer.PLAIN_TEXT_PRESERVE_ENTITIES.apply(value);
    }
}
