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
import jakarta.faces.convert.FacesConverter;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * <p>JSF converter that handles URL encoding and decoding for strings used in view parameters
 * and other URL-based contexts. This converter addresses the issue where special characters
 * in URL parameters (like spaces, umlauts, or other non-ASCII characters) are automatically
 * URL-encoded, making them difficult to display directly in the UI.</p>
 * 
 * <p>For example, when a string containing the character "ü" appears in a URL parameter,
 * it gets encoded as "%C3%BC". This converter provides automatic decoding of such strings
 * for display purposes and encoding for storage or transmission.</p>
 * 
 * <p>The converter uses {@link URLDecoder} and {@link URLEncoder} with UTF-8 encoding
 * to perform the conversion operations, ensuring proper handling of international
 * characters across different platforms.</p>
 * 
 * <h2>Primary Use Cases</h2>
 * <ul>
 *   <li>Displaying URL parameters with special characters in UI components</li>
 *   <li>Converting user input to properly encoded URL parameters</li>
 *   <li>Normalizing encoded strings from external sources</li>
 * </ul>
 * 
 * <h2>Usage</h2>
 * 
 * <pre>
 * &lt;!-- For display purposes, decode the URL parameter --&gt;
 * &lt;h:outputText value="#{param.encodedText}"&gt;
 *     &lt;f:converter converterId="decodingStringConverter" /&gt;
 * &lt;/h:outputText&gt;
 * 
 * &lt;!-- For form input that will be sent as a URL parameter --&gt;
 * &lt;h:inputText value="#{bean.parameterValue}"&gt;
 *     &lt;f:converter converterId="decodingStringConverter" /&gt;
 * &lt;/h:inputText&gt;
 * </pre>
 *
 * <p>This converter is thread-safe as it contains no mutable instance state.</p>
 * 
 * @author Stephan Babkin
 * @see URLEncoder For the underlying encoding mechanism
 * @see URLDecoder For the underlying decoding mechanism
 * @see AbstractConverter The parent class providing converter infrastructure
 * @see <a href="http://www.w3.org/International/O-URL-code.html">URI encoding</a> For more information on URI encoding standards
 * @since 1.0
 */
@FacesConverter(value = "decodingStringConverter")
public class DecodingStringConverter extends AbstractConverter<String> {

    /**
     * Converts a string to its URL-encoded form. This method is used when
     * submitting values from UI components, ensuring the value is properly
     * encoded for transmission as a URL parameter.
     * 
     * @param context The FacesContext for the current request, not null
     * @param component The component associated with this converter, not null
     * @param value The string value to be encoded
     * @return The URL-encoded representation of the input string, or null if input is null
     * @throws ConverterException If encoding fails
     */
    @Override
    protected String convertToString(final FacesContext context, final UIComponent component, final String value)
            throws ConverterException {

        return encode(value);

    }

    /**
     * Converts a URL-encoded string back to its original form. This method is used when
     * displaying values from URL parameters, ensuring they are properly displayed with
     * all special characters intact.
     * 
     * @param context The FacesContext for the current request, not null
     * @param component The component associated with this converter, not null
     * @param value The URL-encoded string to be decoded
     * @return The decoded string, or null if input is null
     * @throws ConverterException If decoding fails due to invalid encoding
     */
    @Override
    protected String convertToObject(final FacesContext context, final UIComponent component, final String value)
            throws ConverterException {

        return decode(value);
    }

    /**
     * Decodes a URL-encoded string using UTF-8 character encoding.
     * 
     * @param value The URL-encoded string to decode
     * @return The decoded string
     * @throws ConverterException If the string contains invalid URL encoding
     */
    private static String decode(final String value) throws ConverterException {
        try {
            return URLDecoder.decode(value, StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            throw new ConverterException(e);
        }
    }

    /**
     * Encodes a string for use in URLs using UTF-8 character encoding.
     * 
     * @param value The string to encode
     * @return The URL-encoded string
     * @throws ConverterException If encoding fails
     */
    private static String encode(final String value) throws ConverterException {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

}
