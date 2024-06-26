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

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;

/**
 * "Last defense converter" that calls object to String. Of course this works
 * only as a formatting converter
 *
 * @author Oliver Wolff
 */
@FacesConverter(ObjectToStringConverter.CONVERTER_ID)
public class ObjectToStringConverter extends AbstractConverter<Object> {

    /** de.cuioss.jsf.components.converter.ObjectToStringConverter */
    public static final String CONVERTER_ID = "de.cuioss.jsf.components.converter.ObjectToStringConverter";

    @Override
    protected String convertToString(final FacesContext context, final UIComponent component, final Object value)
            throws ConverterException {
        return value.toString();
    }

}
