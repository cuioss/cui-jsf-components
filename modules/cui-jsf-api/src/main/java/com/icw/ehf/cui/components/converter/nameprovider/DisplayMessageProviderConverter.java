package com.icw.ehf.cui.components.converter.nameprovider;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.icw.ehf.cui.core.api.application.bundle.CuiResourceBundleAccessor;
import com.icw.ehf.cui.core.api.converter.AbstractConverter;

import de.cuioss.uimodel.nameprovider.DisplayMessageProvider;

/**
 * Converter for the type {@link DisplayMessageProvider}
 *
 * @author i000576
 */
@FacesConverter(forClass = DisplayMessageProvider.class)
public class DisplayMessageProviderConverter extends AbstractConverter<DisplayMessageProvider> {

    private final CuiResourceBundleAccessor bundleAccessor = new CuiResourceBundleAccessor();

    @Override
    protected String convertToString(final FacesContext context, final UIComponent component,
            final DisplayMessageProvider value)
        throws ConverterException {

        return value.getMessageFormated(bundleAccessor.getValue());
    }
}
