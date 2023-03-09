package com.icw.ehf.cui.core.api.converter;

import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import de.cuioss.tools.string.Joiner;
import de.cuioss.tools.string.Splitter;
import lombok.Getter;
import lombok.Setter;

/**
 * String to List conversion and vice versa, based on the separator character.
 */
@FacesConverter("cui.StringToListConverter")
public class StringToListConverter extends AbstractConverter<Collection<String>> {

    static final String SEPARATOR_DEFAULT = ";";

    static final String TARGET_TYPE_LIST = "list";

    static final String TARGET_TYPE_SORTED_SET = "sorted_set";

    @Getter
    @Setter
    private String separator = SEPARATOR_DEFAULT;

    /**
     * Depending on the use-case for this converter there are different target types that should be
     * supported: {@link List} and {@link SortedSet}. This attribute controls the behavior. In case
     * it is "sorted_set" the resulting collection will be a {@link SortedSet} in all other cases
     * it will be {@link List}
     */
    @Getter
    @Setter
    private String targetType = TARGET_TYPE_LIST;

    @Getter
    @Setter
    private boolean omitEmptyStrings = false;

    @Getter
    @Setter
    private boolean trimResults = false;

    @Override
    protected String convertToString(final FacesContext context, final UIComponent component,
            final Collection<String> list)
        throws ConverterException {
        return Joiner.on(separator).skipNulls().join(list);
    }

    @Override
    protected Collection<String> convertToObject(final FacesContext context,
            final UIComponent component,
            final String value)
        throws ConverterException {

        var splitter = Splitter.on(separator);

        if (omitEmptyStrings) {
            splitter = splitter.omitEmptyStrings();
        }
        if (trimResults) {
            splitter = splitter.trimResults();
        }

        if (TARGET_TYPE_SORTED_SET.equals(targetType)) {
            return new TreeSet<>(splitter.splitToList(value));
        }
        return splitter.splitToList(value);
    }
}
