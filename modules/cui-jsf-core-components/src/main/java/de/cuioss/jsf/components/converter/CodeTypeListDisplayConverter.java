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

import java.util.Collection;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;

import de.cuioss.jsf.api.common.accessor.LocaleAccessor;
import de.cuioss.jsf.api.converter.AbstractConverter;
import de.cuioss.tools.string.Joiner;
import de.cuioss.uimodel.model.code.CodeType;
import lombok.Getter;
import lombok.Setter;

/**
 * Display a list of CodeTypes.
 *
 * @author Matthias Walliczek
 *
 */
@FacesConverter(CodeTypeListDisplayConverter.CONVERTER_ID)
public class CodeTypeListDisplayConverter extends AbstractConverter<Collection<? extends CodeType>> {

    /** "cui.CodeTypeListDisplayConverter" */
    public static final String CONVERTER_ID = "cui.CodeTypeListDisplayConverter";

    static final String SEPARATOR_DEFAULT = ";";

    @Getter
    @Setter
    private String separator = SEPARATOR_DEFAULT;

    private final LocaleAccessor localeAccessor = new LocaleAccessor();

    @Override
    protected String convertToString(final FacesContext context, final UIComponent component,
            final Collection<? extends CodeType> value) throws ConverterException {
        var locale = localeAccessor.getValue();
        return Joiner.on(separator).skipNulls().join(value.stream().map(code -> code.getResolved(locale)).toList());
    }

}
