/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.components.converter;

import de.cuioss.jsf.api.converter.AbstractConverter;
import de.cuioss.tools.string.Joiner;
import de.cuioss.tools.string.Splitter;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * <p>JSF converter that handles bidirectional conversion between delimited strings
 * and collections of strings. This converter is useful for UI components that need
 * to display or accept multiple values as a single string, such as comma-separated
 * lists or semicolon-delimited values.</p>
 * 
 * <p>The converter is highly configurable with several options:</p>
 * <ul>
 *   <li><strong>separator</strong> - The character(s) used to delimit values 
 *       (defaults to semicolon ";")</li>
 *   <li><strong>targetType</strong> - The type of collection to create when
 *       converting from string to collection (either "list" or "sorted_set")</li>
 *   <li><strong>omitEmptyStrings</strong> - Whether to skip empty strings when
 *       parsing the input</li>
 *   <li><strong>trimResults</strong> - Whether to trim whitespace from each value</li>
 * </ul>
 * 
 * <p>When converting to a string, null values in the collection are always skipped,
 * and the values are joined with the configured separator.</p>
 * 
 * <h2>Usage Examples</h2>
 * 
 * <p>Basic usage with default separator (semicolon):</p>
 * <pre>
 * &lt;h:outputText value="#{bean.stringCollection}"&gt;
 *     &lt;f:converter converterId="cui.StringToListConverter" /&gt;
 * &lt;/h:outputText&gt;
 * </pre>
 * 
 * <p>Custom configuration:</p>
 * <pre>
 * &lt;h:inputText value="#{bean.stringCollection}"&gt;
 *     &lt;f:converter converterId="cui.StringToListConverter" /&gt;
 *     &lt;f:attribute name="separator" value=", " /&gt;
 *     &lt;f:attribute name="targetType" value="sorted_set" /&gt;
 *     &lt;f:attribute name="omitEmptyStrings" value="true" /&gt;
 *     &lt;f:attribute name="trimResults" value="true" /&gt;
 * &lt;/h:inputText&gt;
 * </pre>
 * 
 * <p>This converter is not thread-safe when its configuration attributes are
 * modified during use.</p>
 *
 * @author Not specified in original
 * @see Collection The parent type of collections this converter handles
 * @see List Used when targetType is "list" (default)
 * @see SortedSet Used when targetType is "sorted_set"
 * @since 1.0
 */
@FacesConverter("cui.StringToListConverter")
public class StringToListConverter extends AbstractConverter<Collection<String>> {

    /** Default separator character used to delimit values */
    static final String SEPARATOR_DEFAULT = ";";

    /** Target type value for creating a List */
    static final String TARGET_TYPE_LIST = "list";

    /** Target type value for creating a SortedSet */
    static final String TARGET_TYPE_SORTED_SET = "sorted_set";

    /**
     * The character(s) used to delimit values in the string representation.
     * Defaults to semicolon (";").
     */
    @Getter
    @Setter
    private String separator = SEPARATOR_DEFAULT;

    /**
     * <p>Determines the type of collection to create when converting from string to
     * collection. Valid values are:</p>
     * <ul>
     *   <li>"list" (default) - Creates a standard {@link List}</li>
     *   <li>"sorted_set" - Creates a {@link SortedSet} using {@link TreeSet}, which
     *       automatically sorts and deduplicates the elements</li>
     * </ul>
     * <p>Any value other than "sorted_set" will result in a List being created.</p>
     */
    @Getter
    @Setter
    private String targetType = TARGET_TYPE_LIST;

    /**
     * Whether to skip empty strings when parsing the input.
     * When set to true, consecutive separators result in no additional elements.
     * Defaults to false.
     */
    @Getter
    @Setter
    private boolean omitEmptyStrings = false;

    /**
     * Whether to trim whitespace from each value when parsing the input.
     * Defaults to false.
     */
    @Getter
    @Setter
    private boolean trimResults = false;

    /**
     * <p>Converts a collection of strings to a single delimited string using the
     * configured separator. Null values in the collection are skipped.</p>
     *
     * <p>For example, with the default separator ";", a collection ["a", "b", "c"]
     * would be converted to "a;b;c".</p>
     *
     * @param context The FacesContext for the current request, not null
     * @param component The component associated with this converter, not null
     * @param list The collection of strings to be converted, may be empty but not null
     * @return A string with all values joined by the separator
     * @throws ConverterException If the conversion fails
     */
    @Override
    protected String convertToString(final FacesContext context, final UIComponent component,
            final Collection<String> list) throws ConverterException {
        return Joiner.on(separator).skipNulls().join(list);
    }

    /**
     * <p>Converts a delimited string to a collection of strings using the configured
     * separator. The type of collection created depends on the {@link #targetType}
     * setting.</p>
     * 
     * <p>The parsing behavior is affected by the {@link #omitEmptyStrings} and
     * {@link #trimResults} settings.</p>
     *
     * @param context The FacesContext for the current request, not null
     * @param component The component associated with this converter, not null
     * @param value The delimited string to be converted
     * @return A collection (List or SortedSet) containing the parsed values
     * @throws ConverterException If the conversion fails
     */
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
