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
 * Represents the available contextual states in Bootstrap's styling system.
 * <p>
 * These states are used throughout Bootstrap's components to indicate different
 * semantic meanings through color. Each enum constant corresponds to a specific
 * contextual class in Bootstrap (e.g., "success", "danger").
 * </p>
 * <p>
 * This enum implements {@link StyleClassProvider}, allowing it to be used directly
 * with components that accept style class providers. The style class returned is
 * the lowercase name of the enum constant.
 * </p>
 * <p>
 * Usage example:
 * </p>
 * <pre>
 * // Get the style class for a danger alert
 * String dangerClass = ContextState.DANGER.getStyleClass(); // returns "danger"
 * 
 * // Create prefixed style class for a button
 * String buttonClass = ContextState.SUCCESS.getStyleClassWithPrefix("btn"); // returns "btn-success"
 * 
 * // Use with a StyleClassBuilder
 * StyleClassBuilder builder = ContextState.WARNING.getStyleClassBuilder();
 * builder.append("additional-class");
 * </pre>
 * <p>
 * This enum is thread-safe and immutable.
 * </p>
 *
 * @author Oliver Wolff
 * @author Sven Haag
 * @since 1.0
 * @see StyleClassProvider
 */
public enum ContextState implements StyleClassProvider {

    /**
     * The default state.
     * <p>
     * Typically used when no specific contextual meaning is needed.
     * In many Bootstrap components, this state is rendered without any special styling.
     * </p>
     */
    DEFAULT,

    /**
     * The primary state.
     * <p>
     * Indicates the primary or main action in a set of actions.
     * Typically represented with a blue color in Bootstrap.
     * </p>
     */
    PRIMARY,

    /**
     * The success state.
     * <p>
     * Indicates a successful or positive action or message.
     * Typically represented with a green color in Bootstrap.
     * </p>
     */
    SUCCESS,

    /**
     * The info state.
     * <p>
     * Indicates an informational message or neutral action.
     * Typically represented with a cyan/blue color in Bootstrap.
     * </p>
     */
    INFO,

    /**
     * The warning state.
     * <p>
     * Indicates a warning that might need attention but is not necessarily an error.
     * Typically represented with an amber/yellow color in Bootstrap.
     * </p>
     */
    WARNING,

    /**
     * The light state.
     * <p>
     * Provides a low-emphasis, subdued visual style.
     * Typically represented with a light gray color in Bootstrap.
     * </p>
     */
    LIGHT,

    /**
     * The danger state.
     * <p>
     * Indicates a dangerous or potentially negative action or error message.
     * Typically represented with a red color in Bootstrap.
     * </p>
     */
    DANGER;

    /**
     * Factory method for converting a string representation to a {@link ContextState} enum constant.
     * <p>
     * This method handles case-insensitive matching and null/empty input gracefully.
     * </p>
     *
     * @param state String representation of the state.
     *              It is interpreted case-insensitive.
     *              It can be either null or empty or must be one of
     *              {"DEFAULT","PRIMARY", "SUCCESS", "INFO", "WARNING", "DANGER",
     *              "LIGHT"} (case-insensitive).
     *              The input will implicitly be trimmed.
     * @return The {@link ContextState} representation computed of the given String.
     *         If it is null or empty {@link ContextState#DEFAULT} will be returned.
     * @throws IllegalArgumentException if the given String does not match any of the enum constants
     */
    public static ContextState getFromString(final String state) {
        if (!MoreStrings.isBlank(state)) {
            return valueOf(state.trim().toUpperCase());
        }
        return DEFAULT;
    }

    /**
     * Returns the style class with a specified prefix.
     * <p>
     * The prefix is combined with the state name using a hyphen, creating contextual classes
     * like those used in Bootstrap (e.g., "btn-primary", "alert-success").
     * </p>
     *
     * @param prefix the prefix to prepend to the state name, can be null or empty
     * @return the prefixed CSS state class in lowercase, or just the state name if the prefix is null or empty
     */
    public final String getStyleClassWithPrefix(final String prefix) {
        if (MoreStrings.isEmpty(prefix)) {
            return getStyleClass();
        }
        return prefix + '-' + name().toLowerCase();
    }

    /**
     * Returns a {@link StyleClassBuilder} initialized with the prefixed style class.
     * <p>
     * This is a convenience method that combines {@link #getStyleClassWithPrefix(String)}
     * with creating a new StyleClassBuilder.
     * </p>
     *
     * @param prefix the prefix to prepend to the state name, can be null or empty
     * @return a new StyleClassBuilder initialized with the prefixed state class
     * @see #getStyleClassWithPrefix(String)
     */
    public final StyleClassBuilder getStyleClassBuilderWithPrefix(final String prefix) {
        return new StyleClassBuilderImpl(getStyleClassWithPrefix(prefix));
    }

    /**
     * Returns the state name in lowercase as the CSS style class.
     * <p>
     * This method is part of the {@link StyleClassProvider} implementation and
     * provides the contextual class name in the format expected by Bootstrap.
     * </p>
     *
     * @return the state name in lowercase (e.g., "success", "danger")
     */
    @Override
    public final String getStyleClass() {
        return name().toLowerCase();
    }
}
