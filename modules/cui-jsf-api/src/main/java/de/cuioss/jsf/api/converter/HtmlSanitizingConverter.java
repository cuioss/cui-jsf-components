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

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import de.cuioss.jsf.api.security.CuiSanitizer;
import de.cuioss.tools.logging.CuiLogger;
import lombok.AccessLevel;
import lombok.Setter;

/**
 * Converter utilized for ensuring proper escaping of input / output fields.
 *
 * @author Oliver Wolff
 */
@FacesConverter(value = "de.cuioss.jsf.components.converter.HtmlSanitizingConverter")
public class HtmlSanitizingConverter extends AbstractConverter<String> {

    private static final CuiLogger LOGGER = new CuiLogger(HtmlSanitizingConverter.class);

    /**
     * The concrete sanitizer to be utilized. Defaults to
     * {@link CuiSanitizer#PLAIN_TEXT_PRESERVE_ENTITIES}
     */
    @Setter(AccessLevel.PROTECTED)
    private CuiSanitizer sanitizer = CuiSanitizer.PLAIN_TEXT_PRESERVE_ENTITIES;

    @Override
    protected String convertToString(final FacesContext context, final UIComponent component, final String value)
            throws ConverterException {
        var sanitizedValue = sanitizer.apply(value);
        if (null == sanitizedValue) {
            return "";
        }
        return sanitizedValue;
    }

    @Override
    protected String convertToObject(final FacesContext context, final UIComponent component, final String value)
            throws ConverterException {
        return sanitizer.apply(value);
    }

    /**
     * Sets the corresponding sanitizer.
     *
     * @param sanitizerIdentifier to set. One of "PLAIN_TEXT",
     *                            "PLAIN_TEXT_PRESERVE_ENTITIES", "SIMPLE_HTML",
     *                            "COMPLEX_HTML", "COMPLEX_HTML_PRESERVE_ENTITIES",
     *                            "PASSTHROUGH" expected.
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
