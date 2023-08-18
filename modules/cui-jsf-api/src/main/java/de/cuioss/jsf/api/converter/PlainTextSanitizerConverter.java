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

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import de.cuioss.jsf.api.security.CuiSanitizer;

/**
 * String converter that uses {@link CuiSanitizer#PLAIN_TEXT} as
 * sanitizing-strategy, saying all html will be stripped, but the special
 * characters are preserved
 *
 * @author Oliver Wolff
 */
@FacesConverter(PlainTextSanitizerConverter.CONVERTER_ID)
public class PlainTextSanitizerConverter extends AbstractConverter<String> {

    /** de.cuioss.jsf.api.converter.PlainTextSanitizerConverter. */
    public static final String CONVERTER_ID = "de.cuioss.jsf.api.converter.PlainTextSanitizerConverter";

    @Override
    protected String convertToString(final FacesContext context, final UIComponent component, final String value)
            throws ConverterException {
        return CuiSanitizer.PLAIN_TEXT_PRESERVE_ENTITIES.apply(value);
    }

    @Override
    protected String convertToObject(final FacesContext context, final UIComponent component, final String value)
            throws ConverterException {
        return CuiSanitizer.PLAIN_TEXT_PRESERVE_ENTITIES.apply(value);
    }
}
