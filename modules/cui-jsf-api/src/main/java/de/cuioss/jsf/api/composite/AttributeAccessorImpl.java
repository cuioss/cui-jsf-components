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
package de.cuioss.jsf.api.composite;

import java.io.Serial;
import java.util.Map;

import de.cuioss.tools.reflect.MoreReflection;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Basic Implementation for {@link AttributeAccessor}. Depending on the value of
 * alwaysResolve it always resolves the value or stores it locally.
 *
 * @author Oliver Wolff
 * @param <T>
 */
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class AttributeAccessorImpl<T> implements AttributeAccessor<T> {

    @Serial
    private static final long serialVersionUID = 7522589696642085203L;

    /** The attribute name. */
    @NonNull
    private final String name;

    /** Holder for the value type to be provided by this accessor. */
    @NonNull
    private final Class<T> valueType;

    /** The 'safely' stored value. */
    private transient T value = null;

    /**
     * Flag defining whether to store locally or always resolve from the attribute
     * map.
     */
    private final boolean alwaysResolve;

    /** Needed for attributes that resolve to null. */
    private boolean resolved = false;

    @Override
    public T value(final Map<String, Object> attributeMap) {
        if (null == value && !resolved || alwaysResolve) {

            var found = attributeMap.get(name);
            if (null != found) {
                if (!MoreReflection.checkWhetherParameterIsAssignable(valueType, found.getClass())) {
                    throw new IllegalStateException(
                            "Invalid attribute type: Expected=" + valueType + ", found=" + found.getClass());
                }
                value = valueType.cast(found);
            } else {
                value = null;
            }
            resolved = true;
        }
        return value;
    }

    @Override
    public boolean available(final Map<String, Object> attributeMap) {
        return attributeMap.containsKey(name) && null != attributeMap.get(name);
    }

}
