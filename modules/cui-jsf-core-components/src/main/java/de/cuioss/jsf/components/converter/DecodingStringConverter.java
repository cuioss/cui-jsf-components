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

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;

import de.cuioss.jsf.api.converter.AbstractConverter;

/**
 * If any string used in view param, it will be encoded to escape spaces and
 * special char. "ü" will be escaped to "%25C3%25BC" in example. It makes
 * difficult to display such string from URL parameter as text on the UI.<br>
 * This converter decode string and supposed to be used in case of string from
 * view parameter should be displayed as text on the UI.
 *
 * @author Stephan Babkin
 * @see <a href="http://www.w3.org/International/O-URL-code.html">URI
 *      encoding</a>
 */
@FacesConverter(value = "decodingStringConverter")
public class DecodingStringConverter extends AbstractConverter<String> {

    @Override
    protected String convertToString(final FacesContext context, final UIComponent component, final String value)
            throws ConverterException {

        return encode(value);

    }

    @Override
    protected String convertToObject(final FacesContext context, final UIComponent component, final String value)
            throws ConverterException {

        return decode(value);
    }

    private static String decode(final String value) throws ConverterException {
        try {
            return URLDecoder.decode(value, StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            throw new ConverterException(e);
        }
    }

    private static String encode(final String value) throws ConverterException {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

}
