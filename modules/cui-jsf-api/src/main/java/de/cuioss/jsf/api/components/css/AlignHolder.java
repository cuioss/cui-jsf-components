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
package de.cuioss.jsf.api.components.css;

import static de.cuioss.tools.string.MoreStrings.isEmpty;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * <h2>Alignment Support</h2>
 * <p>
 * This enum defines CSS alignment classes for floating elements within the CUI framework. 
 * It provides a type-safe way to work with alignment values while abstracting the actual 
 * CSS implementation details.
 * 
 * <p>
 * The enum implements {@link StyleClassProvider} interface to make it compatible with the
 * component CSS class generation system.
 * 
 * <p>
 * Supported alignment values:
 * <ul>
 *   <li>LEFT - Elements float to the left side</li>
 *   <li>RIGHT - Elements float to the right side</li>
 *   <li>DEFAULT - No specific alignment (empty CSS class)</li>
 * </ul>
 * 
 * <p>
 * Usage examples:
 * <pre>
 * // Get alignment from a string input
 * AlignHolder align = AlignHolder.getFromString("left"); 
 * // Returns: AlignHolder.LEFT
 * 
 * // Get the CSS class for an alignment
 * String cssClass = AlignHolder.LEFT.getStyleClass();  
 * // Returns the CSS class for left alignment
 * 
 * // Default alignment for invalid or missing input
 * AlignHolder defaultAlign = AlignHolder.getFromString(null);
 * // Returns: AlignHolder.DEFAULT
 * </pre>
 * 
 * <p>
 * This enum is thread-safe and can be safely used in concurrent environments.
 *
 * @author Oliver Wolff
 * @since 1.0
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum AlignHolder implements StyleClassProvider {

    /**
     * Left-align using {@link CssCommon#PULL_LEFT} CSS class.
     */
    LEFT(CssCommon.PULL_LEFT.getStyleClass()),

    /**
     * Right-align using {@link CssCommon#PULL_RIGHT} CSS class.
     */
    RIGHT(CssCommon.PULL_RIGHT.getStyleClass()),

    /**
     * No specific alignment, uses an empty CSS class.
     */
    DEFAULT("");

    private final String styleClass;

    /**
     * Creates an instance of {@link AlignHolder} based on the given string identifier.
     * <p>
     * This method provides a convenient way to convert string-based alignment values
     * (such as from configuration or user input) to the corresponding enum constant.
     * </p>
     *
     * @param align String identifier for the alignment, may be null or empty.
     *              The comparison is case-insensitive.
     *              "left" will result in {@link AlignHolder#LEFT}
     *              "right" will result in {@link AlignHolder#RIGHT}
     *              Any other value (including null or empty) will result in {@link AlignHolder#DEFAULT}
     * @return the corresponding {@link AlignHolder} constant, never null
     */
    public static AlignHolder getFromString(String align) {
        if (!isEmpty(align)) {
            var upperCase = align.toUpperCase();
            switch (upperCase) {
                case "LEFT":
                    return LEFT;
                case "RIGHT":
                    return RIGHT;
                default:
                    break;
            }
        }
        return AlignHolder.DEFAULT;
    }
}
