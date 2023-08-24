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
import javax.faces.component.UIInput;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import de.cuioss.jsf.api.application.projectstage.CuiProjectStageAccessor;
import de.cuioss.jsf.api.security.CuiSanitizer;
import de.cuioss.tools.logging.CuiLogger;

/**
 * Converter utilized for ensuring proper escaping of output fields. Input
 * fields are ignored, only output fields with no escaping are handled.
 * <p>
 * To be configured as fallback converter for all String.
 */
@FacesConverter(value = "cui.converter.FallbackSanitizingConverter")
public class FallbackSanitizingConverter extends AbstractConverter<String> {

    private final CuiLogger log = new CuiLogger(FallbackSanitizingConverter.class);

    private static final CuiSanitizer sanitizer = CuiSanitizer.COMPLEX_HTML_PRESERVE_ENTITIES;

    @Override
    protected String convertToString(final FacesContext context, final UIComponent component, final String value)
            throws ConverterException {
        return checkAndSanitize(component, value);
    }

    @Override
    protected String convertToObject(final FacesContext context, final UIComponent component, final String value)
            throws ConverterException {
        return checkAndSanitize(component, value);
    }

    private String checkAndSanitize(UIComponent component, String value) {
        if (component instanceof UIInput || component instanceof HtmlOutputText text && text.isEscape()) {
            return value;
        }

        if (new CuiProjectStageAccessor().getValue().isDevelopment()) {
            log.warn("CUI-101: Text not correct escaped or sanitized: '{}' in {} (component id {})", value,
                    component.toString(), component.getId());
        }

        var sanitizedValue = sanitizer.apply(value);
        if (null == sanitizedValue) {
            return "";
        }
        return sanitizedValue;
    }

}
