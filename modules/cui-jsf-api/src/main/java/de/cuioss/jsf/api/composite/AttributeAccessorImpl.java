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

import de.cuioss.tools.reflect.MoreReflection;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.util.Map;

/**
 * Basic Implementation for {@link AttributeAccessor}.
 * <p>
 * This implementation provides access to attributes in a JSF composite component's
 * attribute map. It can either always resolve the value from the attribute map or
 * store it locally after the first access, depending on the value of {@code alwaysResolve}.
 * </p>
 * <p>
 * The accessor ensures type safety by checking whether the attribute value
 * is assignable to the expected type.
 * </p>
 * <p>
 * Usage example:
 * </p>
 * <pre>
 * AttributeAccessor&lt;String&gt; accessor = 
 *     new AttributeAccessorImpl&lt;&gt;("label", String.class, false);
 * String label = accessor.value(component.getAttributes());
 * </pre>
 * <p>
 * Thread-safety: Instances of this class are not thread-safe and should not be shared
 * between threads without proper synchronization.
 * </p>
 *
 * @author Oliver Wolff
 * @param <T> The type of the value to be accessed
 * @since 1.0
 */
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class AttributeAccessorImpl<T> implements AttributeAccessor<T> {

    @Serial
    private static final long serialVersionUID = 7522589696642085203L;

    /** 
     * The attribute name to be accessed from the attribute map.
     */
    @NonNull
    private final String name;

    /** 
     * The expected type of the attribute value, used for type checking and casting.
     */
    @NonNull
    private final Class<T> valueType;

    /** 
     * Caches the resolved value to avoid repeated lookups.
     * This field is not serialized.
     */
    private transient T value = null;

    /**
     * Determines the resolution behavior:
     * <ul>
     * <li>{@code true}: Always resolve from the attribute map on each call to {@link #value(Map)}</li>
     * <li>{@code false}: Resolve once and cache the value</li>
     * </ul>
     */
    private final boolean alwaysResolve;

    /** 
     * Indicates whether the value has been resolved at least once.
     * This is necessary to distinguish between a resolved null value and an unresolved value.
     */
    private boolean resolved = false;

    /**
     * Retrieves the attribute value from the given attribute map.
     * <p>
     * If {@code alwaysResolve} is true, this method will always fetch the value
     * from the attribute map. Otherwise, it will use the cached value after the
     * first resolution.
     * </p>
     * <p>
     * The method performs type checking to ensure the attribute value is compatible
     * with the expected type.
     * </p>
     *
     * @param attributeMap the map containing the attributes, must not be null
     * @return the value of the attribute or null if not found
     * @throws IllegalStateException if the attribute is found but has an incompatible type
     */
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

    /**
     * Checks if the attribute is available and non-null in the given attribute map.
     * <p>
     * This method does not perform any type checking and does not modify the
     * internal state of this accessor.
     * </p>
     *
     * @param attributeMap the map containing the attributes, must not be null
     * @return true if the attribute exists and is non-null, false otherwise
     */
    @Override
    public boolean available(final Map<String, Object> attributeMap) {
        return attributeMap.containsKey(name) && null != attributeMap.get(name);
    }

}
