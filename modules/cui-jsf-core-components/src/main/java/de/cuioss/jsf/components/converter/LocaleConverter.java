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

import de.cuioss.jsf.api.converter.AbstractConverter;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.ConverterException;

import java.util.Locale;

/**
 * Converts a {@link Locale} to String and vice versa
 *
 * @author Oliver Wolff
 *
 */
public class LocaleConverter extends AbstractConverter<Locale> {

    @Override
    protected String convertToString(FacesContext context, UIComponent component, Locale value)
            throws ConverterException {
        return value.toLanguageTag();
    }

    @Override
    protected Locale convertToObject(FacesContext context, UIComponent component, String value)
            throws ConverterException {
        return Locale.forLanguageTag(value);
    }
}
