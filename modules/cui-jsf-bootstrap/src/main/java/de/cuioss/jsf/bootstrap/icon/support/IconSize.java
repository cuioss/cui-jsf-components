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
package de.cuioss.jsf.bootstrap.icon.support;

import de.cuioss.jsf.api.components.css.ContextSize;
import de.cuioss.jsf.api.components.css.StyleClassProvider;
import de.cuioss.tools.string.MoreStrings;
import lombok.Getter;

/**
 * Defines available icon sizes for the CUI bootstrap framework.
 * Provides corresponding CSS class names for rendering.
 * 
 * @author Oliver Wolff
 */
public enum IconSize implements StyleClassProvider {

    /**
     * Default size with no additional CSS class.
     */
    DEFAULT(""),

    /**
     * Large size (lg).
     */
    LG("lg"),

    /**
     * Extra large size (xl).
     */
    XL("xl"),

    /**
     * Extra extra large size (xxl).
     */
    XXL("xxl"),

    /**
     * Extra extra extra large size (xxxl).
     */
    XXXL("xxxl");

    private static final String PREFIX = "cui-icon-";

    @Getter
    private final String styleClass;

    IconSize(String suffix) {
        if (MoreStrings.isEmpty(suffix)) {
            styleClass = "";
        } else {
            styleClass = PREFIX + suffix;
        }
    }

    /**
     * Maps a {@link ContextSize} to the corresponding {@link IconSize}.
     * 
     * @param contextSize May be null, returns {@link IconSize#DEFAULT} in that case
     * @return The matching IconSize for the given contextSize
     * @throws IllegalArgumentException If the provided contextSize has no matching IconSize
     */
    public static IconSize getForContextSize(ContextSize contextSize) {
        var result = DEFAULT;
        if (null != contextSize) {
            result = switch (contextSize) {
                case DEFAULT -> IconSize.DEFAULT;
                case LG -> IconSize.LG;
                case XL -> IconSize.XL;
                case XXL -> IconSize.XXL;
                case XXXL -> IconSize.XXXL;
                default -> throw new IllegalArgumentException("No IconSize defined for " + contextSize);
            };
        }
        return result;
    }
}
