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

import de.cuioss.jsf.api.composite.AttributeAccessor;
import de.cuioss.jsf.api.composite.AttributeAccessorImpl;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;
import java.util.Map;

/**
 * Specialized {@link AttributeAccessor} for Boolean-type attributes, providing convenient access
 * to boolean attributes in JSF composite components.
 * <p>
 * This implementation extends {@link AttributeAccessorImpl} with additional functionality
 * for boolean values, including the ability to invert the boolean logic of the attribute.
 * This is particularly useful for attributes like "disabled" or "readonly" where
 * the component's semantic might need to be inverted from the attribute's value.
 * </p>
 * <p>
 * Usage examples:
 * </p>
 * <pre>
 * // Standard boolean accessor (no inversion)
 * BooleanAttributeAccessor requiredAccessor = 
 *     new BooleanAttributeAccessor("required", false, false);
 *     
 * // Inverted boolean accessor (useful for attributes like "disabled") 
 * BooleanAttributeAccessor enabledAccessor = 
 *     new BooleanAttributeAccessor("disabled", false, true);
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
public class BooleanAttributeAccessor extends AttributeAccessorImpl<Boolean> {

    @Serial
    private static final long serialVersionUID = 5261144187854006347L;

    /**
     * Flag indicating whether to invert the boolean logic of the attribute value.
     * If true, the method {@link #value(Map)} will return the negated value of the
     * actual attribute.
     */
    private final boolean invert;

    /**
     * Constructor creating a new {@link BooleanAttributeAccessor}.
     * 
     * @param name          the name of the attribute to access, must not be null
     * @param alwaysResolve if true, always resolve from the attribute map; if false, cache the value after first resolution
     * @param invertBoolean if true, invert the boolean logic of the resolved value
     */
    public BooleanAttributeAccessor(final String name, final boolean alwaysResolve, final boolean invertBoolean) {
        super(name, Boolean.class, alwaysResolve);
        invert = invertBoolean;
    }
    
    /**
     * Simplified constructor creating a new {@link BooleanAttributeAccessor} without inversion.
     * 
     * @param name          the name of the attribute to access, must not be null
     * @param alwaysResolve if true, always resolve from the attribute map; if false, cache the value after first resolution
     */
    public BooleanAttributeAccessor(final String name, final boolean alwaysResolve) {
        this(name, alwaysResolve, false);
    }

    /**
     * Retrieves the boolean value from the given attribute map, applying inversion if configured.
     * <p>
     * This method extends the behavior of {@link AttributeAccessorImpl#value(Map)} by
     * applying an optional logical inversion to the resolved boolean value.
     * </p>
     *
     * @param attributeMap the map containing the attributes, must not be null
     * @return the resolved boolean value, possibly inverted, or null if not found
     * @throws IllegalStateException if the attribute is found but has an incompatible type
     */
    @Override
    public Boolean value(final Map<String, Object> attributeMap) {
        var actual = super.value(attributeMap);
        if (actual != null && invert) {
            actual = !actual;
        }
        return actual;
    }

}
