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

import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import de.cuioss.jsf.api.common.accessor.LocaleAccessor;
import de.cuioss.jsf.api.converter.AbstractConverter;
import de.cuioss.uimodel.model.code.CodeType;

/**
 * Formatting converter for creating / resolving the label of a given
 * {@link CodeType}. It uses the standard {@link LocaleAccessor} for accessing
 * the needed {@link Locale}
 *
 * @author Oliver Wolff
 *
 */
@FacesConverter(CodeTypeDisplayConverter.CONVERTER_ID)
public class CodeTypeDisplayConverter extends AbstractConverter<CodeType> {

    /** "de.cuioss.jsf.components.converter.CodeTypeDisplayConverter" */
    public static final String CONVERTER_ID = "de.cuioss.jsf.components.converter.CodeTypeDisplayConverter";

    private final LocaleAccessor localeAccessor = new LocaleAccessor();

    @Override
    protected String convertToString(FacesContext context, UIComponent component, CodeType value)
            throws ConverterException {
        return value.getResolved(localeAccessor.getValue());
    }

}
