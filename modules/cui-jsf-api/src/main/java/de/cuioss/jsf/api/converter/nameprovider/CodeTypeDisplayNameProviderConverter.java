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
package de.cuioss.jsf.api.converter.nameprovider;

import static de.cuioss.jsf.api.security.CuiSanitizer.COMPLEX_HTML;

import de.cuioss.jsf.api.common.accessor.LocaleAccessor;
import de.cuioss.jsf.api.converter.AbstractConverter;
import de.cuioss.uimodel.nameprovider.CodeTypeDisplayNameProvider;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;

/**
 * A JSF converter that transforms {@link CodeTypeDisplayNameProvider} objects into localized
 * and sanitized string representations.
 * <p>
 * This converter is registered automatically for the {@link CodeTypeDisplayNameProvider} class through
 * the {@link FacesConverter#forClass()} mechanism, making it the default converter used when
 * a CodeTypeDisplayNameProvider object is rendered in a JSF view.
 * 
 * <p>
 * The converter performs the following operations:
 * <ol>
 *   <li>Obtains the current locale using {@link LocaleAccessor}</li>
 *   <li>Calls {@link CodeTypeDisplayNameProvider#getContent()} to get the underlying content provider</li>
 *   <li>Resolves the localized content with {@code LabeledKey#getResolved(java.util.Locale)}</li>
 *   <li>Sanitizes the resolved text using {@code COMPLEX_HTML} to ensure safe rendering</li>
 * </ol>
 * 
 * <p>
 * Usage example (implicit, as it's registered for the CodeTypeDisplayNameProvider class):
 * <pre>
 * &lt;h:outputText value="#{bean.codeTypeDisplayName}" /&gt;
 * </pre>
 * 
 * <p>
 * Or explicitly:
 * <pre>
 * &lt;h:outputText value="#{bean.codeTypeDisplayName}" converter="#{codeTypeDisplayNameProviderConverter}" /&gt;
 * </pre>
 * 
 * <p>
 * This is a formatting-only converter and does not support converting from String back to
 * CodeTypeDisplayNameProvider. The {@link #getAsObject(FacesContext, UIComponent, String)} method will throw an
 * {@link UnsupportedOperationException} if called.
 *
 * @author Eugen Fischer
 * @since 1.0
 * @see CodeTypeDisplayNameProvider
 * @see LocaleAccessor
 * @see de.cuioss.jsf.api.security.CuiSanitizer#COMPLEX_HTML
 */
@FacesConverter(forClass = CodeTypeDisplayNameProvider.class)
public class CodeTypeDisplayNameProviderConverter extends AbstractConverter<CodeTypeDisplayNameProvider> {

    /**
     * Converts a {@link CodeTypeDisplayNameProvider} object to its localized string representation.
     * <p>
     * This method retrieves the localized text from the provider based on the current locale
     * and sanitizes it for safe HTML rendering.
     * </p>
     *
     * @param context   {@link FacesContext} for the request being processed
     * @param component {@link UIComponent} with which this value is associated
     * @param value     The {@link CodeTypeDisplayNameProvider} to be converted, guaranteed to be non-null
     * @return the localized and sanitized string representation
     * @throws ConverterException if conversion fails
     */
    @Override
    protected String convertToString(FacesContext context, UIComponent component, CodeTypeDisplayNameProvider value)
            throws ConverterException {
        return COMPLEX_HTML.apply(value.getContent().getResolved(new LocaleAccessor().getValue()));
    }
}
