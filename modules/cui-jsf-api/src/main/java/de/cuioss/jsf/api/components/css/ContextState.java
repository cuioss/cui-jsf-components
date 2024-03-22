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
 * Represents the bootstrap contextState.
 *
 * @author Oliver Wolff
 * @author Sven Haag
 */
public enum ContextState implements StyleClassProvider {

    /**
     * The default state. Will usually ignored.
     */
    DEFAULT,
    /**
     * Primary.
     */
    PRIMARY,
    /**
     * Success.
     */
    SUCCESS,
    /**
     * Info.
     */
    INFO,
    /**
     * warning.
     */
    WARNING,
    /**
     * A more visual state.
     */
    LIGHT,
    /**
     * Danger.
     */
    DANGER;

    /**
     * Factory method for computing an {@link ContextState} out of a given
     * {@link String}
     *
     * @param state String representation of the state.
     *              It is interpreted case-insensitive.
     *              It can be either null or empty or must be one of
     *              {"DEFAULT","PRIMARY", "SUCCESS", "INFO", "WARNING", "DANGER",
     *              "LIGHT"} (case-insensitive).
     *              The Input will implicitly be
     *              trimmed.
     * @return The {@link ContextState} representation computed of the given String.
     * If it is null or empty {@link ContextState#DEFAULT} will be returned.
     * If the given String does not match to the constants a
     * {@link IllegalArgumentException} will be thrown.
     */
    public static ContextState getFromString(final String state) {
        if (!MoreStrings.isBlank(state)) {
            return valueOf(state.trim().toUpperCase());
        }
        return DEFAULT;
    }

    /**
     * The prefix will be appended with a dash, e.g. "foo-success"
     *
     * @param prefix or null
     * @return Prefixed CSS state class in lower case
     */
    public final String getStyleClassWithPrefix(final String prefix) {
        if (MoreStrings.isEmpty(prefix)) {
            return getStyleClass();
        }
        return prefix + '-' + name().toLowerCase();
    }

    /**
     * The prefix will be appended with a dash, e.g. "foo-success"
     *
     * @param prefix or null
     * @return Prefixed CSS state class in lower case
     */
    public final StyleClassBuilder getStyleClassBuilderWithPrefix(final String prefix) {
        return new StyleClassBuilderImpl(getStyleClassWithPrefix(prefix));
    }

    /**
     * @return The current ContextState in lower case.
     */
    @Override
    public final String getStyleClass() {
        return name().toLowerCase();
    }

    /**
     * @return StyleClassBuilder with the current ContextState in lower case as base
     * class.
     */
    @Override
    public StyleClassBuilder getStyleClassBuilder() {
        return new StyleClassBuilderImpl(getStyleClass());
    }
}
