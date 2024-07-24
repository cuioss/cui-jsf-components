/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.components.converter;

import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;

import de.cuioss.jsf.api.converter.AbstractConverter;
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
     * Depending on the use-case for this converter there are different target types
     * that should be supported: {@link List} and {@link SortedSet}. This attribute
     * controls the behavior. In case it is "sorted_set" the resulting collection
     * will be a {@link SortedSet} in all other cases it will be {@link List}
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
            final Collection<String> list) throws ConverterException {
        return Joiner.on(separator).skipNulls().join(list);
    }

    @Override
    protected Collection<String> convertToObject(final FacesContext context, final UIComponent component,
            final String value) throws ConverterException {

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
