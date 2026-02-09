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
package de.cuioss.jsf.api.converter;

import de.cuioss.jsf.api.common.accessor.CuiProjectStageAccessor;
import de.cuioss.jsf.api.common.logging.JsfApiLogMessages;
import de.cuioss.jsf.api.security.CuiSanitizer;
import de.cuioss.tools.logging.CuiLogger;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.component.html.HtmlOutputText;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;

/**
 * A fallback converter designed to ensure proper security handling of output components
 * that don't already have escaping enabled.
 * <p>
 * This converter is intended to be registered as a fallback converter for String values in JSF
 * applications. It applies the {@link CuiSanitizer#COMPLEX_HTML_PRESERVE_ENTITIES} sanitization
 * strategy, but only to components that need it:
 * </p>
 * <ul>
 *   <li>It bypasses input components ({@link UIInput}), as sanitization should happen during processing,
 *       not during rendering</li>
 *   <li>It bypasses output components that already have escaping enabled ({@link HtmlOutputText} with
 *       escape=true)</li>
 *   <li>It applies sanitization to all other components to prevent XSS vulnerabilities</li>
 * </ul>
 * <p>
 * Additionally, in development mode, this converter will log warnings when it encounters components
 * that should have explicit escaping configured but don't, helping developers identify potential
 * security issues early.
 * </p>
 * <p>
 * Usage in JSF configuration:
 * </p>
 * <pre>
 * &lt;!-- In faces-config.xml --&gt;
 * &lt;converter&gt;
 *     &lt;converter-for-class&gt;java.lang.String&lt;/converter-for-class&gt;
 *     &lt;converter-id&gt;cui.converter.FallbackSanitizingConverter&lt;/converter-id&gt;
 * &lt;/converter&gt;
 * </pre>
 * <p>
 * Security Note: While this converter provides a safety net for unescaped output, 
 * best practice is still to properly configure escaping on individual components where needed.
 * </p>
 * 
 * @author Oliver Wolff
 * @since 1.0
 * @see CuiSanitizer#COMPLEX_HTML_PRESERVE_ENTITIES
 */
@FacesConverter(value = "cui.converter.FallbackSanitizingConverter")
public class FallbackSanitizingConverter extends AbstractConverter<String> {

    /**
     * Logger for this class, used to log warnings about improperly configured components
     * in development mode.
     */
    private static final CuiLogger LOGGER = new CuiLogger(FallbackSanitizingConverter.class);

    /**
     * The sanitizer used for applying security filtering to potentially unsafe content.
     * Uses {@link CuiSanitizer#COMPLEX_HTML_PRESERVE_ENTITIES} which allows a wide range
     * of HTML while still protecting against XSS attacks.
     */
    private static final CuiSanitizer sanitizer = CuiSanitizer.COMPLEX_HTML_PRESERVE_ENTITIES;

    /**
     * Converts a String value to its sanitized version for output rendering.
     * <p>
     * This implementation checks whether the component already handles escaping,
     * and only applies sanitization if necessary.
     * </p>
     *
     * @param context   {@link FacesContext} for the request being processed
     * @param component {@link UIComponent} with which this value is associated
     * @param value     The String value to be potentially sanitized
     * @return the original value or its sanitized version, depending on the component
     * @throws ConverterException if sanitization fails
     */
    @Override
    protected String convertToString(final FacesContext context, final UIComponent component, final String value)
            throws ConverterException {
        return checkAndSanitize(component, value);
    }

    /**
     * Converts a String value from input to its sanitized version.
     * <p>
     * This implementation checks whether the component already handles escaping,
     * and only applies sanitization if necessary.
     * </p>
     *
     * @param context   {@link FacesContext} for the request being processed
     * @param component {@link UIComponent} with which this value is associated
     * @param value     The String value to be potentially sanitized
     * @return the original value or its sanitized version, depending on the component
     * @throws ConverterException if sanitization fails
     */
    @Override
    protected String convertToObject(final FacesContext context, final UIComponent component, final String value)
            throws ConverterException {
        return checkAndSanitize(component, value);
    }

    /**
     * Helper method that checks if sanitization is needed for the given component
     * and applies it if necessary.
     * <p>
     * In development mode, this method also logs warnings for components that should
     * have escaping enabled but don't.
     * </p>
     * 
     * @param component the component being processed
     * @param value the string value to potentially sanitize
     * @return the original or sanitized value, depending on the component type
     */
    private String checkAndSanitize(UIComponent component, String value) {
        // Skip sanitization for input components and output components with escaping enabled
        if (component instanceof UIInput || component instanceof HtmlOutputText text && text.isEscape()) {
            return value;
        }

        // Log warning in development mode to help identify components that should have proper escaping
        if (new CuiProjectStageAccessor().getValue().isDevelopment()) {
            LOGGER.warn(JsfApiLogMessages.WARN.TEXT_NOT_SANITIZED, value, component.toString(), component.getId());
        }

        // Apply sanitization as a fallback protection
        var sanitizedValue = sanitizer.apply(value);
        if (null == sanitizedValue) {
            return "";
        }
        return sanitizedValue;
    }
}
