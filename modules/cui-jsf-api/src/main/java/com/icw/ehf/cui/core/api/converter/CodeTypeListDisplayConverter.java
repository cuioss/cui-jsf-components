package com.icw.ehf.cui.core.api.converter;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.icw.ehf.cui.core.api.application.locale.LocaleProducerAccessor;

import de.cuioss.tools.string.Joiner;
import de.cuioss.uimodel.model.code.CodeType;
import lombok.Getter;
import lombok.Setter;

/**
 * Display a list of CodeTypes.
 *
 * @author Matthias Walliczek
 *
 */
@FacesConverter(CodeTypeListDisplayConverter.CONVERTER_ID)
public class CodeTypeListDisplayConverter extends AbstractConverter<Collection<? extends CodeType>> {

    /** "cui.CodeTypeListDisplayConverter" */
    public static final String CONVERTER_ID = "cui.CodeTypeListDisplayConverter";

    static final String SEPARATOR_DEFAULT = ";";

    @Getter
    @Setter
    private String separator = SEPARATOR_DEFAULT;

    private final LocaleProducerAccessor localeProducerAccessor = new LocaleProducerAccessor();

    @Override
    protected String convertToString(final FacesContext context, final UIComponent component,
            final Collection<? extends CodeType> value)
        throws ConverterException {
        var locale = localeProducerAccessor.getValue().getLocale();
        return Joiner.on(separator).skipNulls()
                .join(value.stream().map(code -> code.getResolved(locale)).collect(Collectors.toList()));
    }

}
