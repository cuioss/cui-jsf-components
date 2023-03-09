package com.icw.ehf.cui.components.converter.nameprovider;

import static com.icw.ehf.cui.core.api.security.CuiSanitizer.COMPLEX_HTML;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.icw.ehf.cui.core.api.application.locale.LocaleProducerAccessor;
import com.icw.ehf.cui.core.api.converter.AbstractConverter;

import de.cuioss.uimodel.nameprovider.CodeTypeDisplayNameProvider;

@FacesConverter(forClass = CodeTypeDisplayNameProvider.class)
public class CodeTypeDisplayNameProviderConverter extends AbstractConverter<CodeTypeDisplayNameProvider> {

    @Override
    protected String convertToString(FacesContext context, UIComponent component, CodeTypeDisplayNameProvider value)
        throws ConverterException {
        return COMPLEX_HTML
                .apply(value.getContent().getResolved(new LocaleProducerAccessor().getValue().getLocale()));
    }
}
