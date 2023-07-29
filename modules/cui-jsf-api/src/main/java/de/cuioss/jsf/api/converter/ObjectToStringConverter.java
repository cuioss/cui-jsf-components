package de.cuioss.jsf.api.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 * "Last defense converter" that calls object to String. Of course this works
 * only as a formatting converter
 *
 * @author Oliver Wolff
 */
@FacesConverter(ObjectToStringConverter.CONVERTER_ID)
public class ObjectToStringConverter extends AbstractConverter<Object> {

    /** de.cuioss.jsf.components.converter.ObjectToStringConverter */
    public static final String CONVERTER_ID = "de.cuioss.jsf.components.converter.ObjectToStringConverter";

    @Override
    protected String convertToString(final FacesContext context, final UIComponent component, final Object value)
            throws ConverterException {
        return value.toString();
    }

}
