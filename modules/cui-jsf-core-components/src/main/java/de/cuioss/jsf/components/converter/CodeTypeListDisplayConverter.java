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

import de.cuioss.jsf.api.common.accessor.LocaleAccessor;
import de.cuioss.jsf.api.converter.AbstractConverter;
import de.cuioss.tools.string.Joiner;
import de.cuioss.uimodel.model.code.CodeType;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

/**
 * <p>JSF converter that displays a collection of {@link CodeType} objects as a
 * delimited string of their localized labels. The converter iterates through the
 * collection, resolves each CodeType's display label using the current locale,
 * and joins them using a configurable separator (semicolon by default).</p>
 * 
 * <p>This converter is useful for displaying multiple code values in a compact,
 * human-readable format, such as in tables, tooltips, or summary views. It uses
 * {@link LocaleAccessor} to determine the current locale from the faces context
 * for proper localization.</p>
 * 
 * <p>The converter provides a configurable separator that can be set via the 
 * {@code separator} property. By default, it uses a semicolon (;).</p>
 *
 * <p>Note that this converter only supports converting from object to string
 * (displaying the CodeTypes), not from string to object, as it is designed
 * primarily for output scenarios.</p>
 * 
 * <h2>Usage</h2>
 * 
 * <p>Basic usage with default separator:</p>
 * <pre>
 * &lt;h:outputText value="#{bean.codeTypeCollection}"&gt;
 *     &lt;f:converter converterId="cui.CodeTypeListDisplayConverter" /&gt;
 * &lt;/h:outputText&gt;
 * </pre>
 * 
 * <p>Custom separator example (using a comma):</p>
 * <pre>
 * &lt;h:outputText value="#{bean.codeTypeCollection}"&gt;
 *     &lt;f:converter converterId="cui.CodeTypeListDisplayConverter" /&gt;
 *     &lt;f:attribute name="separator" value=", " /&gt;
 * &lt;/h:outputText&gt;
 * </pre>
 * 
 * <p>This converter is thread-safe when used with the default separator. When the
 * separator is modified, thread safety depends on how instances are managed by
 * the JSF runtime.</p>
 *
 * @author Matthias Walliczek
 * @see CodeTypeDisplayConverter Similar converter for individual CodeType objects
 * @see Collection The collection type this converter handles
 * @see CodeType The element type in the collection
 * @since 1.0
 */
@FacesConverter(CodeTypeListDisplayConverter.CONVERTER_ID)
public class CodeTypeListDisplayConverter extends AbstractConverter<Collection<? extends CodeType>> {

    /** "cui.CodeTypeListDisplayConverter" */
    public static final String CONVERTER_ID = "cui.CodeTypeListDisplayConverter";

    /** Default separator used to join the resolved CodeType values */
    static final String SEPARATOR_DEFAULT = ";";

    /**
     * The separator used to join the resolved CodeType values.
     * Defaults to {@value #SEPARATOR_DEFAULT}. Can be changed via
     * the JSF attribute mechanism or by directly calling the setter.
     */
    @Getter
    @Setter
    private String separator = SEPARATOR_DEFAULT;

    private final LocaleAccessor localeAccessor = new LocaleAccessor();

    /**
     * Converts a collection of {@link CodeType} objects to a single delimited string
     * of their localized display values. The method iterates through the collection,
     * resolves each CodeType's display text using the current {@link java.util.Locale},
     * and joins them using the configured separator.
     * <p>
     * Null elements in the collection are skipped.
     *
     * @param context the FacesContext for the current request, not null
     * @param component the UIComponent this converter is attached to, not null
     * @param value the collection of CodeType objects to be converted, may be empty but not null
     * @return a delimited string of localized display texts, or empty string if collection is empty
     * @throws ConverterException if the conversion fails
     */
    @Override
    protected String convertToString(final FacesContext context, final UIComponent component,
            final Collection<? extends CodeType> value) throws ConverterException {
        var locale = localeAccessor.getValue();
        return Joiner.on(separator).skipNulls().join(value.stream().map(code -> code.getResolved(locale)).toList());
    }

}
