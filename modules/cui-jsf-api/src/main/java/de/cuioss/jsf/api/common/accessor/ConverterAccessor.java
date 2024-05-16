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
package de.cuioss.jsf.api.common.accessor;

import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;

import de.cuioss.jsf.api.converter.ObjectToStringConverter;
import de.cuioss.jsf.api.converter.StringIdentConverter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Resolves a converter regarding the current context.
 * <ul>
 * <li>First it checks whether the converterId is set. If so it instantiates a
 * corresponding converter.</li>
 * <li>If it is not set it tries to access the converter using the given
 * targetClass</li>
 * <li>If this also returns <code>null</code> and the targetClass is String it
 * will return the converter for the ID
 * {@link StringIdentConverter#CONVERTER_ID}</li>
 * <li>If the target class is not {@link String} it will return the converter
 * for the ID {@link ObjectToStringConverter#CONVERTER_ID}</li>
 * <li>otherwise it will throw an {@link IllegalStateException}</li>
 * </ul>
 *
 * @author Oliver Wolff
 * @param <T> The target type of the converter
 */
@ToString
@EqualsAndHashCode
public class ConverterAccessor<T> implements ManagedAccessor<Converter<T>> {

    private static final long serialVersionUID = -4619233196555638241L;

    @Getter
    @Setter
    private String converterId;

    @Getter
    @Setter
    private Class<T> targetClass;

    private transient Converter<?> loadedConverter;

    @SuppressWarnings("unchecked")
    @Override
    public Converter<T> getValue() {
        if (null == loadedConverter) {
            var application = FacesContext.getCurrentInstance().getApplication();
            if (null != converterId) {
                loadedConverter = application.createConverter(converterId);
                if (null == loadedConverter) {
                    throw new IllegalStateException("No converter could be loaded for converterId=" + converterId);
                }
            } else if (null != targetClass) {
                loadedConverter = application.createConverter(targetClass);
                // Special case here: We have String, but no converter
                // configured therefore
                if (null == loadedConverter && String.class.equals(targetClass)) {
                    loadedConverter = application.createConverter(StringIdentConverter.CONVERTER_ID);
                }
                if (null == loadedConverter) {
                    loadedConverter = application.createConverter(ObjectToStringConverter.CONVERTER_ID);
                }
            } else {
                throw new IllegalStateException(
                        "No converter could be loaded because you need to provide at least converterId or targetClass");
            }
        }
        return (Converter<T>) loadedConverter;
    }

    /**
     * @return boolean indicating whether at least converterId or targetClass is
     *         set.
     */
    public boolean checkContract() {
        return null != converterId || null != targetClass;
    }

}
