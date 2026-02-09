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

import de.cuioss.jsf.api.common.accessor.LocaleAccessor;
import de.cuioss.jsf.api.converter.AbstractConverter;
import de.cuioss.uimodel.model.code.CodeType;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;

import java.util.Locale;

/**
 * <p>JSF converter that formats a {@link CodeType} instance by resolving its
 * localized display label. This converter is particularly useful for displaying
 * human-readable, localized text representations of code values in UI components
 * like output texts, tables, and select menus.</p>
 * 
 * <p>The converter uses {@link LocaleAccessor} to determine the current locale
 * from the faces context, then delegates to the {@link CodeType#getResolved(Locale)}
 * method to get the properly localized display value.</p>
 * 
 * <p>Note that this converter only supports converting from object to string
 * (displaying the CodeType), not from string to object, as it is designed
 * primarily for output scenarios.</p>
 * 
 * <h2>Usage</h2>
 * 
 * <p>Can be used either by converter-id:</p>
 * <pre>
 * &lt;h:outputText value="#{bean.someCodeType}" 
 *              converter="#{codeTypeDisplayConverter}" /&gt;
 * </pre>
 * 
 * <p>Or by its type:</p>
 * <pre>
 * &lt;h:outputText value="#{bean.someCodeType}"&gt;
 *     &lt;f:converter converterId="de.cuioss.jsf.components.converter.CodeTypeDisplayConverter" /&gt;
 * &lt;/h:outputText&gt;
 * </pre>
 * 
 * <p>This converter is thread-safe as it has no mutable instance state
 * affecting the conversion logic.</p>
 *
 * @author Oliver Wolff
 * @see CodeType The type this converter handles
 * @see LocaleAccessor Utility for accessing the current locale
 * @see AbstractConverter The base class providing converter infrastructure
 * @since 1.0
 */
@FacesConverter(CodeTypeDisplayConverter.CONVERTER_ID)
public class CodeTypeDisplayConverter extends AbstractConverter<CodeType> {

    /** "de.cuioss.jsf.components.converter.CodeTypeDisplayConverter" */
    public static final String CONVERTER_ID = "de.cuioss.jsf.components.converter.CodeTypeDisplayConverter";

    private final LocaleAccessor localeAccessor = new LocaleAccessor();

    /**
     * Converts a {@link CodeType} object to its localized string representation.
     * The method uses the current {@link Locale} obtained via {@link LocaleAccessor}
     * to resolve the proper localized display text.
     *
     * @param context the FacesContext for the current request, not null
     * @param component the UIComponent this converter is attached to, not null
     * @param value the CodeType value to be converted, may be null
     * @return the localized display text for the CodeType, or empty string if value is null
     * @throws ConverterException if the conversion fails (rarely thrown for this converter)
     */
    @Override
    protected String convertToString(FacesContext context, UIComponent component, CodeType value)
            throws ConverterException {
        return value.getResolved(localeAccessor.getValue());
    }

}
