package de.cuioss.jsf.api.converter.nameprovider;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import de.cuioss.jsf.api.converter.AbstractConverter;
import de.cuioss.jsf.api.security.CuiSanitizer;
import de.cuioss.uimodel.nameprovider.DisplayName;
import de.cuioss.uimodel.nameprovider.IDisplayNameProvider;

/**
 * Converter for implementation of {@link IDisplayNameProvider} {@link DisplayName}
 *
 * @author Eugen Fischer
 */
@FacesConverter(forClass = DisplayName.class)
public class DisplayNameConverter extends AbstractConverter<DisplayName> {

    @Override
    protected String convertToString(final FacesContext context, final UIComponent component, final DisplayName value)
        throws ConverterException {
        return CuiSanitizer.COMPLEX_HTML_PRESERVE_ENTITIES.apply(value.getContent());
    }

}
