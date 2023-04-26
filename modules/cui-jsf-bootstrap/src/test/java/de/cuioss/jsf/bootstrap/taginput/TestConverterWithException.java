package de.cuioss.jsf.bootstrap.taginput;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@SuppressWarnings("javadoc")
@FacesConverter("TestConverterWithException")
public class TestConverterWithException implements Converter<String> {

    @Override
    public String getAsObject(final FacesContext context, final UIComponent component,
            final String value) {
        throw new ConverterException(new FacesMessage("Test"));
    }

    @Override
    public String getAsString(final FacesContext context, final UIComponent component,
            final String value) {
        return null;
    }
}
