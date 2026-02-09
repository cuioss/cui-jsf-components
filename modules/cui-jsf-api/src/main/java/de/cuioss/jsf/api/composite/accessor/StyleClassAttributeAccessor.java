/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.api.composite.accessor;

import java.io.Serial;

/**
 * Specialized implementation of {@link StringAttributeAccessor} specifically designed
 * for accessing the common 'styleClass' attribute in JSF components.
 * <p>
 * This class provides a convenient shorthand for creating an accessor for the standard
 * 'styleClass' attribute that is commonly used across JSF components. It creates an
 * accessor with the name 'styleClass', sets {@code alwaysResolve} to true to ensure
 * style classes are always freshly resolved, and sets {@code considerEmptyStringAsNull}
 * to true to treat empty style classes as null.
 * </p>
 * <p>
 * Usage example:
 * </p>
 * <pre>
 * // Create an accessor for the styleClass attribute
 * StyleClassAttributeAccessor styleClassAccessor = new StyleClassAttributeAccessor();
 * 
 * // Access the style class value
 * String styleClass = styleClassAccessor.value(component.getAttributes());
 * </pre>
 * <p>
 * This class is particularly useful when working with component implementations that
 * need to access the standard 'styleClass' attribute, as it reduces boilerplate code
 * and ensures consistent handling of style class attributes.
 * </p>
 * <p>
 * Thread-safety: Instances of this class are not thread-safe and should not be shared
 * between threads without proper synchronization.
 * </p>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see StringAttributeAccessor
 */
public class StyleClassAttributeAccessor extends StringAttributeAccessor {

    @Serial
    private static final long serialVersionUID = 4957486497287214005L;

    /** 
     * The constant name of the style class attribute: "styleClass".
     * This is the standard name used across JSF components for CSS class attributes.
     */
    public static final String NAME = "styleClass";

    /**
     * Default constructor creating a new {@link StyleClassAttributeAccessor}.
     * <p>
     * Creates the accessor with fixed settings:
     * <ul>
     *   <li>name = "styleClass"</li>
     *   <li>alwaysResolve = true (style classes are always freshly resolved)</li>
     *   <li>considerEmptyStringAsNull = true (empty style classes are treated as null)</li>
     * </ul>
     */
    public StyleClassAttributeAccessor() {
        super(NAME, true, true);
    }
}
