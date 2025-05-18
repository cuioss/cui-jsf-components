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
package de.cuioss.jsf.bootstrap.button.support;

import de.cuioss.jsf.api.components.css.ContextSize;
import de.cuioss.jsf.api.components.css.StyleClassProvider;
import de.cuioss.tools.string.MoreStrings;
import lombok.Getter;

/**
 * Represents Bootstrap button sizes and maps them to corresponding CSS classes.
 * Implements {@link StyleClassProvider} for easy CSS class retrieval.
 * 
 * <h3>Available Sizes</h3>
 * <ul>
 *   <li>{@link #DEFAULT} - Standard size (no specific class)</li>
 *   <li>{@link #LG} - Large size ("btn-lg")</li>
 *   <li>{@link #SM} - Small size ("btn-sm")</li>
 * </ul>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see ContextSize
 */
@Getter
public enum ButtonSize implements StyleClassProvider {

    /**
     * The default button size.
     * <p>Does not add any specific size-related CSS class.</p>
     */
    DEFAULT(""),

    /**
     * Large button size.
     * <p>Applies the "btn-lg" CSS class to create a larger button.</p>
     */
    LG("lg"),

    /**
     * Small button size.
     * <p>Applies the "btn-sm" CSS class to create a smaller button.</p>
     */
    SM("sm");

    /**
     * The CSS class prefix for all button size classes.
     */
    private static final String PREFIX = "btn-";

    /**
     * The CSS class corresponding to this button size.
     */
    private final String styleClass;

    /**
     * Constructor.
     *
     * @param suffix The size suffix to be appended to the "btn-" prefix.
     *               If empty, an empty string will be used as the style class.
     */
    ButtonSize(final String suffix) {
        if (MoreStrings.isEmpty(suffix)) {
            styleClass = "";
        } else {
            styleClass = PREFIX + suffix;
        }
    }

    /**
     * Maps from the generic {@link ContextSize} to the specific {@link ButtonSize}.
     * <p>
     * This method allows button components to use the application's context size 
     * system and convert it to the appropriate button-specific size.
     * 
     * @param contextSize The generic context size to map from. Can be null, or one of
     *                    {@link ContextSize#LG}, {@link ContextSize#DEFAULT} or
     *                    {@link ContextSize#SM}
     * @return the corresponding {@link ButtonSize} derived from the given
     *         {@link ContextSize}. If {@code contextSize} is {@code null}, returns
     *         {@link ButtonSize#DEFAULT}.
     * @throws IllegalArgumentException if {@code contextSize} is not one of the
     *         supported sizes (LG, DEFAULT, or SM)
     */
    public static ButtonSize getForContextSize(final ContextSize contextSize) {
        if (null != contextSize) {
            return switch (contextSize) {
                case DEFAULT -> ButtonSize.DEFAULT;
                case LG -> ButtonSize.LG;
                case SM -> ButtonSize.SM;
                default -> throw new IllegalArgumentException("No Button-Size defined for " + contextSize);
            };
        }
        return ButtonSize.DEFAULT;
    }
}
