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

import de.cuioss.jsf.api.converter.ObjectToStringConverter;
import de.cuioss.jsf.api.converter.StringIdentConverter;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;

/**
 * Accessor for resolving {@link Converter} in a serialization-safe manner.
 * <p>
 * This accessor simplifies access to JSF converters and enables serialization-safe
 * handling in JSF views. It retrieves converters from the
 * {@link jakarta.faces.application.Application}.
 * 
 * <p>
 * Usage example:
 * <pre>
 * private final ConverterAccessor converterAccessor = 
 *   new ConverterAccessor("javax.faces.Number");
 * 
 * public Converter&lt;Number&gt; getNumberConverter() {
 *     return converterAccessor.getValue();
 * }
 * </pre>
 *
 * @author Oliver Wolff
 */
@ToString
@EqualsAndHashCode
public class ConverterAccessor<T> implements ManagedAccessor<Converter<T>> {

    @Serial
    private static final long serialVersionUID = -4619233196555638241L;

    /**
     * The JSF converter ID to look up.
     * This is used as the first resolution strategy if present.
     */
    @Getter
    @Setter
    private String converterId;

    /**
     * The target class for which to find a converter.
     * This is used as the second resolution strategy if converterId is not set.
     */
    @Getter
    @Setter
    private Class<T> targetClass;

    /**
     * Cached converter instance.
     * This is lazily initialized when {@link #getValue()} is called.
     */
    private transient Converter<?> loadedConverter;

    /**
     * {@inheritDoc}
     * <p>
     * Returns the converter resolved according to the hierarchical strategy described in the class documentation.
     * The converter is lazily loaded on the first call and cached for subsequent calls.
     * </p>
     *
     * @return The resolved converter
     * @throws IllegalStateException if neither converterId nor targetClass is set,
     *         or if no converter could be found for the given converterId
     */
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
     * Validates that the accessor has sufficient information to resolve a converter.
     * <p>
     * This method checks whether at least one of {@code converterId} or {@code targetClass} is set,
     * which is the minimum requirement for this accessor to function properly.
     *
     * @return {@code true} if at least converterId or targetClass is set, {@code false} otherwise
     */
    public boolean checkContract() {
        return null != converterId || null != targetClass;
    }
}
