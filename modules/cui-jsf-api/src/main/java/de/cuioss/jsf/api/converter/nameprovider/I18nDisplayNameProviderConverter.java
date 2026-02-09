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

import static de.cuioss.tools.string.MoreStrings.isEmpty;

import de.cuioss.jsf.api.common.accessor.LocaleAccessor;
import de.cuioss.jsf.api.converter.AbstractConverter;
import de.cuioss.jsf.api.security.CuiSanitizer;
import de.cuioss.uimodel.nameprovider.I18nDisplayNameProvider;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;

/**
 * A specialized JSF converter that transforms {@link I18nDisplayNameProvider} instances into 
 * properly localized and sanitized text representations.
 * <p>
 * This converter is registered automatically for the {@link I18nDisplayNameProvider} class through
 * the {@link FacesConverter#forClass()} mechanism, making it the default converter used when
 * an I18nDisplayNameProvider object is rendered in a JSF view.
 * 
 * <p>
 * The converter performs the following steps when converting to a string:
 * <ol>
 *   <li>Retrieves the current locale from the {@link LocaleAccessor}</li>
 *   <li>Attempts to look up the text for the specific locale using
 *       {@link I18nDisplayNameProvider#lookupTextFor(java.util.Locale)}</li>
 *   <li>If no text is found for the specific locale, falls back to
 *       {@link I18nDisplayNameProvider#lookupTextWithFallbackFirstFittingLanguageOnly(java.util.Locale)}
 *       which tries to find text for a similar locale</li>
 *   <li>Sanitizes the resulting text using {@link CuiSanitizer#COMPLEX_HTML_PRESERVE_ENTITIES}
 *       to ensure safe HTML rendering</li>
 *   <li>Returns an empty string if no suitable text could be found</li>
 * </ol>
 * 
 * <p>
 * Usage example (implicit, as it's registered for the I18nDisplayNameProvider class):
 * <pre>
 * &lt;h:outputText value="#{bean.i18nDisplayName}" /&gt;
 * </pre>
 * 
 * <p>
 * Or explicitly:
 * <pre>
 * &lt;h:outputText value="#{bean.i18nDisplayName}" converter="#{i18nDisplayNameProviderConverter}" /&gt;
 * </pre>
 * 
 * <p>
 * This is a formatting-only converter and does not support converting from String back to
 * I18nDisplayNameProvider. The {@link #getAsObject(FacesContext, UIComponent, String)} method will throw an
 * {@link UnsupportedOperationException} if called.
 *
 * @author Eugen Fischer
 * @since 1.0
 * @see I18nDisplayNameProvider
 * @see LocaleAccessor
 * @see CuiSanitizer#COMPLEX_HTML_PRESERVE_ENTITIES
 */
@FacesConverter(forClass = I18nDisplayNameProvider.class)
public class I18nDisplayNameProviderConverter extends AbstractConverter<I18nDisplayNameProvider> {

    /**
     * Accessor for obtaining the current locale to use for internationalization.
     * This is used to determine which localized text to retrieve from the I18nDisplayNameProvider.
     */
    private final LocaleAccessor localeProducerAccessor = new LocaleAccessor();

    /**
     * Converts an {@link I18nDisplayNameProvider} to its localized string representation.
     * <p>
     * This method retrieves the appropriate localized text from the provider based on the
     * current locale, applying fallback strategies if needed, and sanitizes the result
     * for safe HTML rendering.
     * </p>
     *
     * @param context   {@link FacesContext} for the request being processed
     * @param component {@link UIComponent} with which this value is associated
     * @param value     The {@link I18nDisplayNameProvider} to be converted, guaranteed to be non-null
     * @return the localized and sanitized string representation, or an empty string if no suitable text was found
     * @throws ConverterException if conversion fails
     */
    @Override
    protected String convertToString(final FacesContext context, final UIComponent component,
            final I18nDisplayNameProvider value) throws ConverterException {

        // Get the current locale for internationalization
        final var locale = localeProducerAccessor.getValue();

        // Try to get text for the exact locale
        var text = value.lookupTextFor(locale);

        // If no exact match, try fallback to a similar locale
        if (isEmpty(text)) {
            text = value.lookupTextWithFallbackFirstFittingLanguageOnly(locale);
        }

        // Return sanitized text if available, otherwise empty string
        if (!isEmpty(text)) {
            return CuiSanitizer.COMPLEX_HTML_PRESERVE_ENTITIES.apply(text);
        }

        return "";
    }
}
