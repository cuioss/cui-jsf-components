package de.cuioss.jsf.api.converter.nameprovider;

import static de.cuioss.tools.string.MoreStrings.isEmpty;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import de.cuioss.jsf.api.application.locale.LocaleProducerAccessor;
import de.cuioss.jsf.api.converter.AbstractConverter;
import de.cuioss.jsf.api.security.CuiSanitizer;
import de.cuioss.uimodel.nameprovider.I18nDisplayNameProvider;

/**
 * Converter handle to convert {@linkplain I18nDisplayNameProvider} to locale specific text
 * representation
 *
 * @author Eugen Fischer
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
