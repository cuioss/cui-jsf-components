package de.cuioss.jsf.api.converter.nameprovider;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import de.cuioss.jsf.api.application.bundle.CuiResourceBundleAccessor;
import de.cuioss.jsf.api.converter.AbstractConverter;
import de.cuioss.uimodel.nameprovider.DisplayMessageProvider;

/**
 * Converter for the type {@link DisplayMessageProvider}
 *
 * @author Eugen Fischer
 */
@FacesConverter(forClass = DisplayMessageProvider.class)
public class DisplayMessageProviderConverter extends AbstractConverter<DisplayMessageProvider> {

    private final CuiResourceBundleAccessor bundleAccessor = new CuiResourceBundleAccessor();

    @Override
    protected String convertToString(final FacesContext context, final UIComponent component,
            final DisplayMessageProvider value) throws ConverterException {

        return value.getMessageFormated(bundleAccessor.getValue());
    }
}
