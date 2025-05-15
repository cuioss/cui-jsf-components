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

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represents common CSS style classes that are not specific to a concrete library
 * like Bootstrap, but are used throughout the CUI framework.
 * <p>
 * This enum provides a type-safe way to access commonly used CSS classes while
 * maintaining consistency across the application. Each enum constant corresponds
 * to a specific CSS class that serves a general purpose in UI styling.
 * </p>
 * <p>
 * The enum implements {@link StyleClassProvider} interface to integrate with
 * the component CSS class generation system.
 * </p>
 * <p>
 * Usage examples:
 * </p>
 * <pre>
 * // Get the disabled CSS class
 * String disabledClass = CssCommon.DISABLED.getStyleClass(); // returns "disabled"
 * 
 * // Use with StyleClassBuilder
 * StyleClassBuilder builder = new StyleClassBuilderImpl();
 * builder.append(CssCommon.PULL_LEFT);
 * </pre>
 * <p>
 * This enum is thread-safe and immutable.
 * </p>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see StyleClassProvider
 * @see StyleClassBuilder
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum CssCommon implements StyleClassProvider {

    /**
     * General disabled class.
     * <p>
     * Represents a CSS class that indicates a disabled state of a UI element.
     * This class is commonly used to visually indicate that an element is not
     * interactive or currently unavailable.
     * </p>
     */
    DISABLED("disabled"),
    
    /**
     * Shorthand for Bootstrap-style float:right.
     * <p>
     * Represents a CSS class that floats an element to the right side of its container.
     * This is a common utility class used for alignment purposes.
     * </p>
     */
    PULL_RIGHT("pull-right"),
    
    /**
     * Shorthand for Bootstrap-style float:left.
     * <p>
     * Represents a CSS class that floats an element to the left side of its container.
     * This is a common utility class used for alignment purposes.
     * </p>
     */
    PULL_LEFT("pull-left");

    private final String styleClass;

}
