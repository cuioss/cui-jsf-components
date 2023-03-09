package com.icw.ehf.cui.core.api.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 * Special converter for the rare cases where do need a String Converter instead
 * of the default activated HtmlSanitizingConverter. In essence it is an
 * identity converter.
 *
 * @author Oliver Wolff
 */
@FacesConverter(StringIdentConverter.CONVERTER_ID)
public class StringIdentConverter extends AbstractConverter<String> {

    /** com.icw.ehf.cui.core.api.converter.StringIdentConverter. */
    public static final String CONVERTER_ID =
        "com.icw.ehf.cui.core.api.converter.StringIdentConverter";

    @Override
    protected String convertToString(final FacesContext context, final UIComponent component,
            final String value)
        throws ConverterException {
        return value;
    }

    @Override
    protected String convertToObject(final FacesContext context, final UIComponent component,
            final String value)
        throws ConverterException {
        return value;
    }
}
