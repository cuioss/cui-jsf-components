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
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.ConverterException;

import java.util.Locale;

/**
 * <p>JSF converter that handles conversion between {@link Locale} objects and their
 * string representations using BCP 47 language tags. This converter is useful for
 * displaying and selecting locales in UI components.</p>
 * 
 * <p>The converter uses {@link Locale#toLanguageTag()} for converting to string, which
 * produces IETF BCP 47 language tags like "en-US" or "de-DE". For parsing, it uses
 * {@link Locale#forLanguageTag(String)}, which can handle standard language tags.</p>
 * 
 * <h2>Examples of supported language tags:</h2>
 * <ul>
 *   <li>en - English</li>
 *   <li>en-US - English (United States)</li>
 *   <li>de-DE - German (Germany)</li>
 *   <li>fr-FR - French (France)</li>
 *   <li>ja-JP - Japanese (Japan)</li>
 * </ul>
 * 
 * <h2>Usage Example</h2>
 * 
 * <pre>
 * &lt;!-- Display a locale --&gt;
 * &lt;h:outputText value="#{bean.userLocale}"&gt;
 *     &lt;f:converter converterId="localeConverter" /&gt;
 * &lt;/h:outputText&gt;
 * 
 * &lt;!-- Select a locale --&gt;
 * &lt;h:selectOneMenu value="#{bean.selectedLocale}"&gt;
 *     &lt;f:converter converterId="localeConverter" /&gt;
 *     &lt;f:selectItems value="#{localeBean.availableLocales}" /&gt;
 * &lt;/h:selectOneMenu&gt;
 * </pre>
 * 
 * <p>This converter is thread-safe as it contains no mutable instance state.</p>
 *
 * @author Oliver Wolff
 * @see Locale#toLanguageTag() For information on language tag format
 * @see Locale#forLanguageTag(String) For parsing language tags
 * @see AbstractConverter The parent class providing converter infrastructure
 * @since 1.0
 */
public class LocaleConverter extends AbstractConverter<Locale> {

    /**
     * Converts a {@link Locale} object to its string representation using
     * the IETF BCP 47 language tag format (e.g., "en-US", "de-DE").
     *
     * @param context The FacesContext for the current request, not null
     * @param component The component associated with this converter, not null
     * @param value The Locale to be converted, may be null
     * @return The language tag string representation of the locale, or null if input is null
     * @throws ConverterException If the conversion fails
     */
    @Override
    protected String convertToString(FacesContext context, UIComponent component, Locale value)
            throws ConverterException {
        return value.toLanguageTag();
    }

    /**
     * Converts a language tag string (e.g., "en-US", "de-DE") to a {@link Locale} object.
     * Uses {@link Locale#forLanguageTag(String)} for parsing the language tag.
     *
     * @param context The FacesContext for the current request, not null
     * @param component The component associated with this converter, not null
     * @param value The language tag string to be converted, may be null or empty
     * @return The corresponding Locale object, or null if input is null or empty
     * @throws ConverterException If the conversion fails
     */
    @Override
    protected Locale convertToObject(FacesContext context, UIComponent component, String value)
            throws ConverterException {
        return Locale.forLanguageTag(value);
    }
}
