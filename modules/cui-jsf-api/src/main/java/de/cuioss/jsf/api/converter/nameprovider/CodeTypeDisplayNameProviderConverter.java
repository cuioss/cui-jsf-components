package de.cuioss.jsf.api.converter.nameprovider;

import static de.cuioss.jsf.api.security.CuiSanitizer.COMPLEX_HTML;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import de.cuioss.jsf.api.application.locale.LocaleProducerAccessor;
import de.cuioss.jsf.api.converter.AbstractConverter;
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
