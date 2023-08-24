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

import java.util.Map;

import de.cuioss.jsf.api.composite.AttributeAccessorImpl;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Implementation for String based attributes. Depending on
 * considerEmptyStringAsNull it interprets empty String as null.
 *
 * @author Oliver Wolff
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StringAttributeAccessor extends AttributeAccessorImpl<String> {

    private static final long serialVersionUID = 490377061753452475L;

    private final boolean considerEmptyStringAsNull;

    /**
     * Constructor.
     *
     * @param name                      of the attribute to accessed. Must not be
     *                                  null or empty
     * @param alwaysResolve             Flag defining whether to store locally or
     *                                  always resolve from the attribute map
     * @param considerEmptyStringAsNull indicates how to treat empty Strings
     */
    public StringAttributeAccessor(final String name, final boolean alwaysResolve,
            final boolean considerEmptyStringAsNull) {
        super(name, String.class, alwaysResolve);
        this.considerEmptyStringAsNull = considerEmptyStringAsNull;
    }

    @Override
    public String value(final Map<String, Object> attributeMap) {
        var found = super.value(attributeMap);
        if (considerEmptyStringAsNull && null != found && found.isEmpty()) {
            found = null;
        }
        return found;
    }

    @Override
    public boolean available(final Map<String, Object> attributeMap) {
        return null != value(attributeMap);
    }
}
