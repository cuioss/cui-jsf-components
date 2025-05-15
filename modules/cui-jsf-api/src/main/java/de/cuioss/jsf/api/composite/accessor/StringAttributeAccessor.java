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
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;
import java.util.Map;

/**
 * Specialized implementation of {@link AttributeAccessorImpl} for String-based attributes
 * with additional handling for empty strings.
 * <p>
 * This implementation extends the standard attribute accessor functionality with the ability
 * to treat empty strings as null values. This is particularly useful in JSF components where
 * empty strings and null values often need to be handled in the same way, especially for
 * attributes like labels, titles, or style classes.
 * </p>
 * <p>
 * The behavior regarding empty strings is controlled by the {@code considerEmptyStringAsNull}
 * parameter, which can be configured during construction.
 * </p>
 * <p>
 * Usage examples:
 * </p>
 * <pre>
 * // Create an accessor that keeps empty strings as-is
 * StringAttributeAccessor labelAccessor = 
 *     new StringAttributeAccessor("label", false, false);
 *     
 * // Create an accessor that treats empty strings as null
 * StringAttributeAccessor titleAccessor = 
 *     new StringAttributeAccessor("title", false, true);
 * </pre>
 * <p>
 * Thread-safety: Instances of this class are not thread-safe and should not be shared
 * between threads without proper synchronization.
 * </p>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StringAttributeAccessor extends AttributeAccessorImpl<String> {

    @Serial
    private static final long serialVersionUID = 490377061753452475L;

    /**
     * Controls how empty strings are handled by this accessor.
     * If true, empty strings are converted to null values.
     * If false, empty strings are kept as-is.
     */
    private final boolean considerEmptyStringAsNull;

    /**
     * Constructor creating a new {@link StringAttributeAccessor}.
     *
     * @param name                      the name of the attribute to access, must not be null or empty
     * @param alwaysResolve             if true, always resolve from the attribute map; if false, cache the value after first resolution
     * @param considerEmptyStringAsNull if true, empty strings will be treated as null values; if false, empty strings are preserved
     */
    public StringAttributeAccessor(final String name, final boolean alwaysResolve,
            final boolean considerEmptyStringAsNull) {
        super(name, String.class, alwaysResolve);
        this.considerEmptyStringAsNull = considerEmptyStringAsNull;
    }

    /**
     * Retrieves the string value from the given attribute map, applying empty-string handling
     * according to the configuration.
     * <p>
     * This method extends the behavior of {@link AttributeAccessorImpl#value(Map)} by
     * conditionally treating empty strings as null values based on the {@code considerEmptyStringAsNull}
     * configuration.
     * </p>
     *
     * @param attributeMap the map containing the attributes, must not be null
     * @return the value of the attribute, possibly converted to null if it's an empty string
     *         and {@code considerEmptyStringAsNull} is true
     * @throws IllegalStateException if the attribute is found but has an incompatible type
     */
    @Override
    public String value(final Map<String, Object> attributeMap) {
        var found = super.value(attributeMap);
        if (considerEmptyStringAsNull && null != found && found.isEmpty()) {
            found = null;
        }
        return found;
    }

    /**
     * Checks if the attribute is available and non-null in the given attribute map,
     * taking into account empty string handling.
     * <p>
     * Unlike the superclass implementation, this method considers the value from {@link #value(Map)},
     * which may convert empty strings to null if {@code considerEmptyStringAsNull} is true.
     * </p>
     *
     * @param attributeMap the map containing the attributes, must not be null
     * @return true if the attribute exists, is non-null, and is not an empty string
     *         (if {@code considerEmptyStringAsNull} is true)
     */
    @Override
    public boolean available(final Map<String, Object> attributeMap) {
        return null != value(attributeMap);
    }
}
