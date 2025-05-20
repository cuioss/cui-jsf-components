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

import de.cuioss.tools.string.MoreStrings;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serial;
import java.util.Map;

/**
 * Extends {@link StringAttributeAccessor} with the ability to provide default values for
 * String attributes that are not present in the attribute map, are null, or are empty strings.
 * <p>
 * This implementation is particularly useful for textual attributes that should have a
 * fallback value when not explicitly specified by the component user. It automatically
 * treats empty strings as null values, simplifying empty string handling.
 * </p>
 * <p>
 * The class always creates the accessor with {@code checkForEmpty=true} to ensure that
 * empty strings are treated as null values, and then applies the default value logic.
 * </p>
 * <p>
 * Usage example:
 * </p>
 * <pre>
 * // Create accessor for a String attribute with a default value
 * DefaultAwareStringAccessorImpl labelAccessor = 
 *     new DefaultAwareStringAccessorImpl("label", false, "Default Label");
 *     
 * // Returns "Default Label" if the attribute is not present, null, or an empty string
 * String label = labelAccessor.value(component.getAttributes());
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
public class DefaultAwareStringAccessorImpl extends StringAttributeAccessor {

    @Serial
    private static final long serialVersionUID = -3779458673451938363L;

    /**
     * The default value to be returned by {@link #value(Map)} when the attribute
     * is not present in the attribute map, has a null value, or is an empty string.
     * If this value is an empty string, it will be stored as null.
     */
    @Getter(value = AccessLevel.PROTECTED)
    private final String defaultValue;

    /**
     * Constructor creating a new {@link DefaultAwareStringAccessorImpl}.
     *
     * @param name          the name of the attribute to access, must not be null or empty
     * @param alwaysResolve if true, always resolve from the attribute map; if false, cache the value after first resolution
     * @param defaultValue  the value to return when the attribute is not present, null, or an empty string;
     *                      may be null or empty (empty strings are stored as null)
     */
    public DefaultAwareStringAccessorImpl(final String name, final boolean alwaysResolve, final String defaultValue) {
        // Always use checkForEmpty=true to handle empty strings consistently
        super(name, alwaysResolve, true);
        // Convert empty strings to null for consistency
        this.defaultValue = MoreStrings.emptyToNull(defaultValue);
    }

    /**
     * Retrieves the string attribute value from the given attribute map or returns the default value
     * if the attribute is not present, has a null value, or is an empty string.
     * <p>
     * This method extends the behavior of {@link StringAttributeAccessor#value(Map)} by
     * providing a fallback default value when needed.
     * </p>
     *
     * @param attributeMap the map containing the attributes, must not be null
     * @return the value of the attribute if available and non-empty, otherwise the default value
     * @throws IllegalStateException if the attribute is found but has an incompatible type
     */
    @Override
    public String value(final Map<String, Object> attributeMap) {
        if (null != super.value(attributeMap)) {
            return super.value(attributeMap);
        }
        return defaultValue;
    }

    /**
     * Checks if the attribute is available in the given attribute map or if a non-null
     * default value has been specified.
     * <p>
     * This method extends the behavior of {@link StringAttributeAccessor#available(Map)} by
     * returning true when a non-null default value is available, even if the attribute
     * itself is not present in the map or is an empty string.
     * </p>
     *
     * @param attributeMap the map containing the attributes, must not be null
     * @return true if either a non-empty attribute exists in the map or a non-null default value is specified
     */
    @Override
    public boolean available(final Map<String, Object> attributeMap) {
        return defaultValue != null || super.available(attributeMap);
    }

}
