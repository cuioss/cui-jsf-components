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
package de.cuioss.jsf.components.selection;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.EnumConverter;
import jakarta.faces.model.SelectItem;

import java.io.Serial;
import java.util.List;

/**
 * Variant for simplified usage with enum.
 *
 * @author Oliver Wolff
 * @param <T> enum bounded type
 */
public class EnumSelectMenuModel<T extends Enum<T>> extends AbstractSelectMenuModel<T> {

    @Serial
    private static final long serialVersionUID = -2712127262852018082L;

    private final Class<T> targetClass;
    private transient Converter wrappedConverter;

    /**
     * @param selectableValues
     * @param klass            {@linkplain Class} target type
     */
    public EnumSelectMenuModel(final List<SelectItem> selectableValues, final Class<T> klass) {
        super(selectableValues);
        targetClass = klass;
    }

    @Override
    protected String convertToString(final FacesContext context, final UIComponent component, final T value)
            throws ConverterException {
        return getWrappedConverter().getAsString(context, component, value);
    }

    @Override
    @SuppressWarnings("unchecked")
    // Implicitly checked by using
    // de.cuioss.jsf.components.converter.AbstractConverter<T>
    protected T convertToObject(final FacesContext context, final UIComponent component, final String value)
            throws ConverterException {
        return (T) getWrappedConverter().getAsObject(context, component, value);
    }

    private Converter getWrappedConverter() {
        if (null == wrappedConverter) {
            wrappedConverter = new EnumConverter(targetClass);
        }
        return wrappedConverter;
    }
}
