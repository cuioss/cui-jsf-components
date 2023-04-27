package de.cuioss.jsf.components.converter;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import de.cuioss.jsf.api.converter.AbstractConverter;

/**
 * If any string used in view param, it will be encoded to escape spaces and special char. "ü" will
 * be escaped to "%25C3%25BC" in example. It makes difficult to display such string from URL
 * parameter as text on the UI.<br>
 * This converter decode string and supposed to be used in case of string from view parameter should
 * be displayed as text on the UI.
 *
 * @author Stephan Babkin
 * @see <a href="http://www.w3.org/International/O-URL-code.html">URI encoding</a>
 */
@FacesConverter(value = "decodingStringConverter")
public class DecodingStringConverter extends AbstractConverter<String> {

    @Override
    protected String convertToString(final FacesContext context, final UIComponent component,
            final String value)
        throws ConverterException {

        return encode(value);

    }

    @Override
    protected String convertToObject(final FacesContext context, final UIComponent component,
            final String value)
        throws ConverterException {

        return decode(value);
    }

    private static String decode(final String value) throws ConverterException {
        try {
            return URLDecoder.decode(value, "UTF-8");
        } catch (UnsupportedEncodingException | IllegalArgumentException e) {
            throw new ConverterException(e);
        }
    }

    private static String encode(final String value) throws ConverterException {
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new ConverterException(e);
        }
    }

}
