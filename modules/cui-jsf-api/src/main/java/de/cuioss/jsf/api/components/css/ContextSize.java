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
package de.cuioss.jsf.api.components.css;

import de.cuioss.jsf.api.components.css.impl.StyleClassBuilderImpl;
import de.cuioss.tools.string.MoreStrings;

/**
 * Represents the bootstrap size contexts used for styling components with different size variations.
 * <p>
 * These sizes are used throughout the CUI component library to provide consistent sizing options
 * for various UI elements like buttons, inputs, and containers.
 * </p>
 * <h2>Usage</h2>
 * <pre>
 * // Direct usage in Java code
 * StyleClassBuilder builder = new StyleClassBuilder();
 * builder.append(ContextSize.SM.getStyleClass("btn"));
 * 
 * // In component attributes
 * &lt;cui:button size="lg"&gt;Large Button&lt;/cui:button&gt;
 * </pre>
 * <p>
 * The enum is designed to be compatible with Bootstrap's sizing conventions while providing
 * additional size options beyond the standard Bootstrap sizes.
 * </p>
 * <p>
 * This enum is immutable and thread-safe.
 * </p>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see StyleClassProvider
 */
public enum ContextSize implements StyleClassProvider {

    /**
     * The default size. Will usually be ignored in style class generation.
     */
    DEFAULT,

    /**
     * Extra Small size. Represents the smallest size option, typically used for compact UI elements.
     */
    XS,

    /**
     * Small size. Represents a size smaller than the default, commonly used for secondary UI elements.
     */
    SM,

    /**
     * Medium size. Represents a moderate size, commonly used as an alternative to the default size.
     */
    MD,

    /**
     * Large size. Represents a size larger than the default, commonly used for emphasized UI elements.
     */
    LG,

    /**
     * Extra Large size. An extension to the standard Bootstrap sizing, providing an additional
     * larger option.
     */
    XL,

    /**
     * Extra Extra Large size. An extension to the standard Bootstrap sizing, providing an even
     * larger option than XL.
     */
    XXL,

    /**
     * Extra Extra Extra Large size. An extension to the standard Bootstrap sizing, providing the
     * largest size option available in the framework.
     */
    XXXL;

    /**
     * Factory method for computing an {@link ContextSize} out of a given
     * {@link String}
     *
     * @param size String representation of the size.
     *             It is interpreted case-insensitive.
     *             It can be either null or empty or must be one of
     *             {"XS", "SM", "MD", "LG", "XL", "XXL", "XXXL"} (case-insensitive).
     *             The Input will implicitly be trimmed.
     * @return The {@link ContextSize} representation computed of the given String.
     * If it is null or empty {@link ContextSize#DEFAULT} will be returned.
     * If the given String does not match to the constants a
     * {@link IllegalArgumentException} will be thrown.
     */
    public static ContextSize getFromString(String size) {
        if (!MoreStrings.isBlank(size)) {
            return valueOf(size.trim().toUpperCase());
        }
        return DEFAULT;
    }

    /**
     * Returns the style class specific for this context size.
     * <p>
     * For {@link #DEFAULT}, this method returns an empty string since the default size
     * typically doesn't require an additional CSS class.
     * </p>
     * <p>
     * For all other sizes, this method returns the lowercase name of the enum constant,
     * which corresponds to the standard sizing suffix in Bootstrap (e.g., "xs", "sm", "lg").
     * </p>
     *
     * @return the size-specific style class, or an empty string for {@link #DEFAULT}
     */
    @Override
    public String getStyleClass() {
        if (DEFAULT.equals(this)) {
            return "";
        }
        return name().toLowerCase();
    }

    /**
     * Returns the style class with a specified prefix.
     * <p>
     * The prefix is combined with the size name using a hyphen, creating contextual classes
     * like those used in Bootstrap (e.g., "btn-lg", "input-sm").
     * </p>
     * <p>
     * For {@link #DEFAULT}, this method returns only the prefix since the default size
     * typically doesn't require a size suffix.
     * </p>
     *
     * @param prefix the prefix to prepend to the size name, can be null or empty
     * @return the prefixed CSS size class, or just the prefix for {@link #DEFAULT}
     */
    public String getStyleClass(final String prefix) {
        if (DEFAULT.equals(this)) {
            return MoreStrings.nullToEmpty(prefix);
        }
        if (MoreStrings.isEmpty(prefix)) {
            return getStyleClass();
        }
        return prefix + "-" + name().toLowerCase();
    }

    /**
     * Returns a {@link StyleClassBuilder} initialized with the prefixed style class.
     * <p>
     * This is a convenience method that combines {@link #getStyleClass(String)}
     * with creating a new StyleClassBuilder.
     * </p>
     *
     * @param prefix the prefix to prepend to the size name, can be null or empty
     * @return a new StyleClassBuilder initialized with the prefixed size class
     * @see #getStyleClass(String)
     */
    public StyleClassBuilder getStyleClassBuilder(final String prefix) {
        return new StyleClassBuilderImpl(getStyleClass(prefix));
    }
}
