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

import static de.cuioss.tools.string.MoreStrings.isEmpty;

import de.cuioss.jsf.api.components.css.ContextState;
import de.cuioss.jsf.api.components.css.StyleClassProvider;
import de.cuioss.tools.string.MoreStrings;
import lombok.Getter;

/**
 * Represents Bootstrap button states/styles and maps them to corresponding CSS classes.
 * Implements {@link StyleClassProvider} for easy CSS class retrieval.
 * 
 * <h3>Available States</h3>
 * <ul>
 *   <li>{@link #DEFAULT} - Standard gray button ("btn-default")</li>
 *   <li>{@link #PRIMARY} - Blue button for primary actions ("btn-primary")</li>
 *   <li>{@link #SUCCESS} - Green button for successful actions ("btn-success")</li>
 *   <li>{@link #INFO} - Light blue button for information ("btn-info")</li>
 *   <li>{@link #WARNING} - Yellow button for warnings ("btn-warning")</li>
 *   <li>{@link #DANGER} - Red button for dangerous actions ("btn-danger")</li>
 *   <li>{@link #LINK} - Button styled as a link ("btn-link")</li>
 * </ul>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see ContextState
 */
@Getter
public enum ButtonState implements StyleClassProvider {

    /**
     * Default state button style.
     * <p>Applies the "btn-default" CSS class for a standard gray button.</p>
     */
    DEFAULT("default"),

    /**
     * Primary action button style.
     * <p>Applies the "btn-primary" CSS class for a blue button, 
     * typically used for the main action on a page or form.</p>
     */
    PRIMARY("primary"),

    /**
     * Success state button style.
     * <p>Applies the "btn-success" CSS class for a green button,
     * typically used for successful or positive actions.</p>
     */
    SUCCESS("success"),

    /**
     * Information state button style.
     * <p>Applies the "btn-info" CSS class for a light blue button,
     * typically used for informational or neutral actions.</p>
     */
    INFO("info"),

    /**
     * Warning state button style.
     * <p>Applies the "btn-warning" CSS class for a yellow button,
     * typically used for actions that require caution.</p>
     */
    WARNING("warning"),

    /**
     * Danger state button style.
     * <p>Applies the "btn-danger" CSS class for a red button,
     * typically used for destructive or irreversible actions.</p>
     */
    DANGER("danger"),

    /**
     * Link style button.
     * <p>Applies the "btn-link" CSS class to make the button appear as a link
     * while maintaining button behavior.</p>
     */
    LINK("link");

    /**
     * The CSS class prefix for all button state classes.
     */
    private static final String PREFIX = "btn-";

    /**
     * The CSS class corresponding to this button state.
     */
    private final String styleClass;

    /**
     * Constructor.
     *
     * @param suffix The state suffix to be appended to the "btn-" prefix.
     *               If empty, an empty string will be used as the style class.
     */
    ButtonState(final String suffix) {
        if (MoreStrings.isEmpty(suffix)) {
            styleClass = "";
        } else {
            styleClass = PREFIX + suffix;
        }
    }

    /**
     * Converts a string state name to the corresponding {@link ButtonState} enum value.
     * <p>
     * This method allows for case-insensitive string lookup of button states,
     * which is useful when processing user input or configuration values.
     * 
     * @param state The state name to convert. May be null or empty.
     *              Valid values are: "default", "primary", "success", "info",
     *              "warning", "danger", and "link" (case-insensitive).
     *              
     * @return The corresponding {@link ButtonState}. Returns {@link ButtonState#DEFAULT}
     *         if the input is null or empty.
     *         
     * @throws IllegalArgumentException if the state string doesn't match any defined button state
     */
    public static ButtonState getForContextState(final String state) {
        if (isEmpty(state)) {
            return ButtonState.DEFAULT;
        }
        return valueOf(state.toUpperCase());
    }
}
