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
package de.cuioss.jsf.bootstrap.tag.support;

import de.cuioss.jsf.api.components.css.ContextSize;
import de.cuioss.jsf.api.components.css.StyleClassProvider;
import de.cuioss.tools.string.MoreStrings;
import lombok.Getter;

/**
 * Defines the available size options for Tag components with corresponding CSS classes.
 * Maps between generic {@link ContextSize} values and tag-specific CSS classes.
 * 
 * <h2>CSS Classes</h2>
 * <ul>
 *   <li>DEFAULT - No specific size class</li>
 *   <li>SM - "cui-tag-sm" for small tags</li>
 *   <li>LG - "cui-tag-lg" for large tags</li>
 *   <li>XL - "cui-tag-xl" for extra large tags</li>
 * </ul>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
@Getter
public enum TagSize implements StyleClassProvider {

    /**
     * The default size.
     * <p>Does not add any specific size-related CSS class, using the standard tag size.</p>
     */
    DEFAULT(""),

    /**
     * Small size.
     * <p>Applies the CSS class "cui-tag-sm" to create a smaller tag.</p>
     */
    SM("sm"),

    /**
     * Large size.
     * <p>Applies the CSS class "cui-tag-lg" to create a larger tag.</p>
     */
    LG("lg"),

    /**
     * Extra large size.
     * <p>Applies the CSS class "cui-tag-xl" to create an extra large tag.
     * This is a CUI extension beyond standard Bootstrap sizes.</p>
     */
    XL("xl");

    /**
     * Constructor for TagSize enum values.
     * 
     * @param suffix the size suffix to append to the CSS class prefix, or empty string for default size
     */
    TagSize(String suffix) {
        if (MoreStrings.isEmpty(suffix)) {
            styleClass = "";
        } else {
            styleClass = PREFIX + suffix;
        }
    }

    private static final String PREFIX = "cui-tag-";

    private final String styleClass;

    /**
     * Converts a generic {@link ContextSize} to the corresponding {@link TagSize}.
     * This allows for consistent mapping between standard context sizes and
     * tag-specific sizing.
     * 
     * @param contextSize The context size to convert. May be null, or one of
     *                    {@link ContextSize#SM}, {@link ContextSize#XL},
     *                    {@link ContextSize#LG}
     *                    
     * @return the corresponding {@link TagSize} derived from the given {@link ContextSize}.
     *         If contextSize is null, returns {@link TagSize#DEFAULT}.
     *         
     * @throws IllegalArgumentException if the provided context size is not supported
     *         (currently only DEFAULT, SM, LG, and XL are supported)
     * @since 1.0
     */
    public static TagSize getForContextSize(ContextSize contextSize) {
        var result = DEFAULT;
        if (null != contextSize) {
            result = switch (contextSize) {
                case DEFAULT -> TagSize.DEFAULT;
                case SM -> TagSize.SM;
                case LG -> TagSize.LG;
                case XL -> TagSize.XL;
                default -> throw new IllegalArgumentException("No TagSize defined for " + contextSize);
            };
        }
        return result;
    }
}
