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
 * Shorthand for creating an {@link AttributeAccessor} for Boolean-types. In
 * addition it can be easily configured to inverse the boolean logic.
 *
 * @author Oliver Wolff
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BooleanAttributeAccessor extends AttributeAccessorImpl<Boolean> {

    @Serial
    private static final long serialVersionUID = 5261144187854006347L;

    private final boolean invert;

    /**
     * @param name          of the boolean
     * @param alwaysResolve false: cache the value once it is resolved
     * @param invertBoolean flag indicating whether to invert the boolean logic.
     */
    public BooleanAttributeAccessor(final String name, final boolean alwaysResolve, final boolean invertBoolean) {
        super(name, Boolean.class, alwaysResolve);
        invert = invertBoolean;
    }

    @Override
    public Boolean value(final Map<String, Object> attributeMap) {
        var actual = super.value(attributeMap);
        if (actual != null && invert) {
            actual = !actual;
        }
        return actual;
    }

}
