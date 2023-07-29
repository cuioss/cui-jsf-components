package de.cuioss.jsf.components.converter;

import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import de.cuioss.jsf.api.application.locale.LocaleProducerAccessor;
import de.cuioss.jsf.api.converter.AbstractConverter;
import de.cuioss.uimodel.model.code.CodeType;

/**
 * Formatting converter for creating / resolving the label of a given
 * {@link CodeType}. It uses the standard {@link LocaleProducerAccessor} for
 * accessing the needed {@link Locale}
 *
 * @author Oliver Wolff
 *
 */
@FacesConverter(CodeTypeDisplayConverter.CONVERTER_ID)
public class CodeTypeDisplayConverter extends AbstractConverter<CodeType> {

    /** "de.cuioss.jsf.components.converter.CodeTypeDisplayConverter" */
    public static final String CONVERTER_ID = "de.cuioss.jsf.components.converter.CodeTypeDisplayConverter";

    private final LocaleProducerAccessor localeProducerAccessor = new LocaleProducerAccessor();

    @Override
    protected String convertToString(FacesContext context, UIComponent component, CodeType value)
            throws ConverterException {
        return value.getResolved(localeProducerAccessor.getValue().getLocale());
    }

}
