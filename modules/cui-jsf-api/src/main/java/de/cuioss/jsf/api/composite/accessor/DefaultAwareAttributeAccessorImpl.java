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
package de.cuioss.jsf.api.composite.accessor;

import de.cuioss.jsf.api.composite.AttributeAccessorImpl;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * Extends {@link AttributeAccessorImpl} with the capability to provide default values
 * for attributes that are not present in the attribute map or have null values.
 * <p>
 * This implementation is useful for attributes that should have a fallback value when
 * not explicitly specified by the component user. It ensures that the {@link #value(Map)}
 * method never returns null if a non-null default value is provided.
 * </p>
 * <p>
 * Additionally, the {@link #available(Map)} method is modified to return true if either
 * the attribute is available in the map or a non-null default value has been specified.
 * </p>
 * <p>
 * Usage example:
 * </p>
 * <pre>
 * // Create accessor for a String attribute with a default value
 * DefaultAwareAttributeAccessorImpl&lt;String&gt; titleAccessor = 
 *     new DefaultAwareAttributeAccessorImpl&lt;&gt;("title", String.class, false, "Default Title");
 *     
 * // Will return "Default Title" if the attribute is not present
 * String title = titleAccessor.value(component.getAttributes());
 * </pre>
 * <p>
 * Thread-safety: Instances of this class are not thread-safe and should not be shared
 * between threads without proper synchronization.
 * </p>
 *
 * @author Oliver Wolff
 * @param <T> The type of the value to be accessed, must implement {@link Serializable}
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DefaultAwareAttributeAccessorImpl<T extends Serializable> extends AttributeAccessorImpl<T> {

    @Serial
    private static final long serialVersionUID = -3779458673451938363L;

    /**
     * The default value to be returned by {@link #value(Map)} when the attribute
     * is not present in the attribute map or has a null value.
     */
    @Getter(value = AccessLevel.PROTECTED)
    private final T defaultValue;

    /**
     * Constructor creating a new {@link DefaultAwareAttributeAccessorImpl}.
     *
     * @param name          the name of the attribute to access, must not be null or empty
     * @param valueType     the concrete type of the attribute value, used for type checking and casting
     * @param alwaysResolve if true, always resolve from the attribute map; if false, cache the value after first resolution
     * @param defaultValue  the value to return when the attribute is not present or null, may be null itself
     */
    public DefaultAwareAttributeAccessorImpl(final String name, final Class<T> valueType, final boolean alwaysResolve,
            final T defaultValue) {
        super(name, valueType, alwaysResolve);
        this.defaultValue = defaultValue;
    }

    /**
     * Retrieves the attribute value from the given attribute map or returns the default value
     * if the attribute is not present or has a null value.
     * <p>
     * This method extends the behavior of {@link AttributeAccessorImpl#value(Map)} by
     * providing a fallback default value when needed.
     * </p>
     *
     * @param attributeMap the map containing the attributes, must not be null
     * @return the value of the attribute if available and non-null, otherwise the default value
     * @throws IllegalStateException if the attribute is found but has an incompatible type
     */
    @Override
    public T value(final Map<String, Object> attributeMap) {
        if (super.available(attributeMap)) {
            return super.value(attributeMap);
        }
        return defaultValue;
    }

    /**
     * Checks if the attribute is available in the given attribute map or if a non-null
     * default value has been specified.
     * <p>
     * This method extends the behavior of {@link AttributeAccessorImpl#available(Map)} by
     * returning true when a non-null default value is available, even if the attribute
     * itself is not present in the map.
     * </p>
     *
     * @param attributeMap the map containing the attributes, must not be null
     * @return true if either the attribute exists in the map or a non-null default value is specified
     */
    @Override
    public boolean available(final Map<String, Object> attributeMap) {
        return defaultValue != null || super.available(attributeMap);
    }

}
