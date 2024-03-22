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

import de.cuioss.tools.string.MoreStrings;

/**
 * Represents the bootstrap context-state.
 *
 * @author Oliver Wolff
 */
public enum ContextSize {

    /**
     * The default size. Will usually ignored.
     */
    DEFAULT,
    /**
     * Extras Small.
     */
    XS,
    /**
     * Small.
     */
    SM,
    /**
     * Medium.
     */
    MD,
    /**
     * Large.
     */
    LG,
    /**
     * XL, addition to bootstrap.
     */
    XL,
    /**
     * XXL, addition to bootstrap.
     */
    XXL,
    /**
     * XXXL, addition to bootstrap.
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

}
