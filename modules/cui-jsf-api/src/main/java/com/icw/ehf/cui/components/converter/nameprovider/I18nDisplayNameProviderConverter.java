package com.icw.ehf.cui.components.converter.nameprovider;

import static de.cuioss.tools.string.MoreStrings.isEmpty;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.icw.ehf.cui.core.api.application.locale.LocaleProducerAccessor;
import com.icw.ehf.cui.core.api.converter.AbstractConverter;
import com.icw.ehf.cui.core.api.security.CuiSanitizer;

import de.cuioss.uimodel.nameprovider.I18nDisplayNameProvider;

/**
 * Converter handle to convert {@linkplain I18nDisplayNameProvider} to locale specific text
 * representation
 *
 * @author i000576
 */
@FacesConverter(forClass = I18nDisplayNameProvider.class)
public class I18nDisplayNameProviderConverter extends AbstractConverter<I18nDisplayNameProvider> {

    private final LocaleProducerAccessor localeProducerAccessor = new LocaleProducerAccessor();

    @Override
    protected String convertToString(final FacesContext context, final UIComponent component,
            final I18nDisplayNameProvider value)
        throws ConverterException {

        final var locale = localeProducerAccessor.getValue().getLocale();

        var text = value.lookupTextFor(locale);

        if (isEmpty(text)) {
            text = value.lookupTextWithFallbackFirstFittingLanguageOnly(locale);
        }

        if (!isEmpty(text)) {
            return CuiSanitizer.COMPLEX_HTML_PRESERVE_ENTITIES.apply(text);
        }

        return "";
    }

}
