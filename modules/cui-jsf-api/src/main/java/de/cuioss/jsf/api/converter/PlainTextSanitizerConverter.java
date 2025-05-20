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

import de.cuioss.jsf.api.security.CuiSanitizer;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;

/**
 * A specialized converter that sanitizes input and output strings by stripping all HTML tags
 * while preserving special characters and HTML entities.
 * <p>
 * This converter uses {@link CuiSanitizer#PLAIN_TEXT_PRESERVE_ENTITIES} as its sanitization strategy,
 * which removes all HTML markup but keeps HTML entities (like &amp;nbsp; or &amp;copy;) intact.
 * This makes it suitable for displaying text that might contain special characters but where
 * HTML formatting is not desired or could pose security risks.
 * </p>
 * <p>
 * Unlike the more configurable {@link HtmlSanitizingConverter}, this converter has a fixed
 * sanitization strategy and provides a simpler, more direct approach for the common case
 * of plain text handling.
 * </p>
 * <p>
 * Usage example in JSF:
 * </p>
 * <pre>
 * &lt;!-- Basic usage --&gt;
 * &lt;h:inputText value="#{bean.textContent}" converter="de.cuioss.jsf.api.converter.PlainTextSanitizerConverter" /&gt;
 * </pre>
 * <p>
 * Or by converter ID:
 * </p>
 * <pre>
 * &lt;h:inputText value="#{bean.textContent}"&gt;
 *     &lt;f:converter converterId="de.cuioss.jsf.api.converter.PlainTextSanitizerConverter" /&gt;
 * &lt;/h:inputText&gt;
 * </pre>
 * <p>
 * Security Note: This converter helps protect against XSS attacks by removing all HTML tags,
 * making it a good choice for user input fields where raw text is expected.
 * </p>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see CuiSanitizer#PLAIN_TEXT_PRESERVE_ENTITIES
 * @see HtmlSanitizingConverter
 */
@FacesConverter(PlainTextSanitizerConverter.CONVERTER_ID)
public class PlainTextSanitizerConverter extends AbstractConverter<String> {

    /** 
     * The converter ID: "de.cuioss.jsf.api.converter.PlainTextSanitizerConverter".
     * <p>
     * This ID can be used to reference this converter in JSF views.
     * </p>
     */
    public static final String CONVERTER_ID = "de.cuioss.jsf.api.converter.PlainTextSanitizerConverter";

    /**
     * Converts a String value to its sanitized string representation for output.
     * <p>
     * This implementation applies the {@link CuiSanitizer#PLAIN_TEXT_PRESERVE_ENTITIES} 
     * sanitizer to the input value, removing all HTML tags while preserving HTML entities.
     * </p>
     *
     * @param context   {@link FacesContext} for the request being processed
     * @param component {@link UIComponent} with which this value is associated
     * @param value     The String value to be sanitized
     * @return the sanitized string value with HTML tags removed and entities preserved
     * @throws ConverterException if sanitization fails
     */
    @Override
    protected String convertToString(final FacesContext context, final UIComponent component, final String value)
            throws ConverterException {
        return CuiSanitizer.PLAIN_TEXT_PRESERVE_ENTITIES.apply(value);
    }

    /**
     * Sanitizes a String value from user input to protect against XSS attacks.
     * <p>
     * This implementation applies the {@link CuiSanitizer#PLAIN_TEXT_PRESERVE_ENTITIES}
     * sanitizer to the input value, removing all HTML tags while preserving HTML entities.
     * </p>
     *
     * @param context   {@link FacesContext} for the request being processed
     * @param component {@link UIComponent} with which this value is associated
     * @param value     The String value to be sanitized
     * @return the sanitized string value with HTML tags removed and entities preserved
     * @throws ConverterException if sanitization fails
     */
    @Override
    protected String convertToObject(final FacesContext context, final UIComponent component, final String value)
            throws ConverterException {
        return CuiSanitizer.PLAIN_TEXT_PRESERVE_ENTITIES.apply(value);
    }
}
