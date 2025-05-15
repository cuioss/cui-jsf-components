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
package de.cuioss.jsf.api.converter;

import static de.cuioss.tools.string.MoreStrings.isNotBlank;

import de.cuioss.jsf.api.security.CuiSanitizer;
import de.cuioss.tools.logging.CuiLogger;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;
import lombok.AccessLevel;
import lombok.Setter;

/**
 * A JSF converter that sanitizes input and output values to prevent cross-site scripting (XSS)
 * attacks and ensure proper HTML escaping.
 * <p>
 * This converter delegates the sanitization work to {@link CuiSanitizer} implementations,
 * which provide various levels of sanitization from plain text to complex HTML. By default,
 * it uses {@link CuiSanitizer#PLAIN_TEXT_PRESERVE_ENTITIES} which handles most common use cases.
 * 
 * <p>
 * The sanitization strategy can be configured through the {@link #setStrategy(String)} method
 * or by directly setting a {@link CuiSanitizer} instance using the sanitizer setter.
 * 
 * <p>
 * Usage example in JSF:
 * <pre>
 * &lt;!-- Basic usage with default sanitizer --&gt;
 * &lt;h:inputText value="#{bean.htmlContent}" converter="de.cuioss.jsf.components.converter.HtmlSanitizingConverter" /&gt;
 * 
 * &lt;!-- With specific sanitizer strategy --&gt;
 * &lt;h:inputText value="#{bean.htmlContent}"&gt;
 *     &lt;f:converter converterId="de.cuioss.jsf.components.converter.HtmlSanitizingConverter" /&gt;
 *     &lt;f:attribute name="strategy" value="COMPLEX_HTML" /&gt;
 * &lt;/h:inputText&gt;
 * </pre>
 * 
 * <p>
 * Available sanitization strategies:
 * <ul>
 *   <li><b>PLAIN_TEXT</b>: Removes all HTML tags, allowing only plain text</li>
 *   <li><b>PLAIN_TEXT_PRESERVE_ENTITIES</b>: Removes HTML tags but preserves entities like &amp;nbsp;</li>
 *   <li><b>SIMPLE_HTML</b>: Allows basic formatting tags like &lt;b&gt;, &lt;i&gt;, &lt;p&gt;</li>
 *   <li><b>COMPLEX_HTML</b>: Allows a wider range of HTML elements and attributes</li>
 *   <li><b>COMPLEX_HTML_PRESERVE_ENTITIES</b>: Similar to COMPLEX_HTML but also preserves entities</li>
 *   <li><b>PASSTHROUGH</b>: No sanitization (use with caution)</li>
 * </ul>
 * 
 * <p>
 * Security Note: This converter helps protect against XSS attacks by sanitizing user input.
 * The default sanitizer is configured conservatively, but depending on your requirements,
 * you may need to select a different sanitization strategy.
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see CuiSanitizer
 */
@FacesConverter(value = "de.cuioss.jsf.components.converter.HtmlSanitizingConverter")
public class HtmlSanitizingConverter extends AbstractConverter<String> {

    private static final CuiLogger LOGGER = new CuiLogger(HtmlSanitizingConverter.class);

    /**
     * The concrete sanitizer to be utilized for sanitizing input and output values.
     * <p>
     * Defaults to {@link CuiSanitizer#PLAIN_TEXT_PRESERVE_ENTITIES}, which provides
     * a good balance of security and usability for most cases.
     * </p>
     */
    @Setter(AccessLevel.PROTECTED)
    private CuiSanitizer sanitizer = CuiSanitizer.PLAIN_TEXT_PRESERVE_ENTITIES;

    /**
     * Converts a String value to its sanitized string representation for output.
     * <p>
     * This implementation applies the configured sanitizer to the input value.
     * If the sanitized result is null, an empty string is returned according to
     * the JSF converter contract.
     * </p>
     *
     * @param context   {@link FacesContext} for the request being processed
     * @param component {@link UIComponent} with which this value is associated
     * @param value     The String value to be sanitized
     * @return the sanitized string, or an empty string if the sanitized result is null
     * @throws ConverterException if sanitization fails
     */
    @Override
    protected String convertToString(final FacesContext context, final UIComponent component, final String value)
            throws ConverterException {
        var sanitizedValue = sanitizer.apply(value);
        if (null == sanitizedValue) {
            return "";
        }
        return sanitizedValue;
    }

    /**
     * Converts a String value from user input to a sanitized String object.
     * <p>
     * This implementation applies the configured sanitizer to the input value
     * to protect against XSS attacks and other security vulnerabilities.
     * </p>
     *
     * @param context   {@link FacesContext} for the request being processed
     * @param component {@link UIComponent} with which this value is associated
     * @param value     The String value to be sanitized
     * @return the sanitized string value
     * @throws ConverterException if sanitization fails
     */
    @Override
    protected String convertToObject(final FacesContext context, final UIComponent component, final String value)
            throws ConverterException {
        return sanitizer.apply(value);
    }

    /**
     * Sets the sanitization strategy by name.
     * <p>
     * This method allows configuring the converter through JSF component attributes.
     * </p>
     *
     * @param sanitizerIdentifier the name of the sanitizer to use, case-insensitive.
     *                            Valid values are: "PLAIN_TEXT",
     *                            "PLAIN_TEXT_PRESERVE_ENTITIES", "SIMPLE_HTML",
     *                            "COMPLEX_HTML", "COMPLEX_HTML_PRESERVE_ENTITIES",
     *                            and "PASSTHROUGH".
     *                            If null or empty, the default sanitizer will be used.
     * @see CuiSanitizer
     */
    public void setStrategy(final String sanitizerIdentifier) {
        if (isNotBlank(sanitizerIdentifier)) {
            LOGGER.debug("Configure to %s", sanitizerIdentifier);
            sanitizer = CuiSanitizer.valueOf(sanitizerIdentifier.toUpperCase());
        } else {
            LOGGER.debug("No identifier configured, using default");
        }
    }
}
