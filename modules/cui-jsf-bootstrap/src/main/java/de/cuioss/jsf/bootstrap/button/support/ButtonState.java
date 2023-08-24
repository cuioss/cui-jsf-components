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
import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.api.components.css.StyleClassProvider;
import de.cuioss.jsf.api.components.css.impl.StyleClassBuilderImpl;
import de.cuioss.tools.string.MoreStrings;
import lombok.Getter;

/**
 * @author Oliver Wolff
 *
 */
public enum ButtonState implements StyleClassProvider {

    /** The default-state. */
    DEFAULT("default"),
    /** Primary. */
    PRIMARY("primary"),
    /** Success. */
    SUCCESS("success"),
    /** Info. */
    INFO("info"),
    /** Warning. */
    WARNING("warning"),
    /** error. */
    DANGER("danger"),
    /** Light. */
    LINK("link"),;

    ButtonState(final String suffix) {
        if (MoreStrings.isEmpty(suffix)) {
            styleClass = "";
        } else {
            styleClass = new StringBuilder().append(PREFIX).append(suffix).toString();
        }
    }

    private static final String PREFIX = "btn-";

    @Getter
    private final String styleClass;

    @Override
    public StyleClassBuilder getStyleClassBuilder() {
        return new StyleClassBuilderImpl(styleClass);
    }

    /**
     * @param state May be null or empty, otherwise must be one of
     *              {"default","primary", "success", "info", "warning", "danger",
     *              "link"}
     * @return the corresponding {@link ButtonState} derived by the given
     *         {@link ContextState}. In case of <code>contextSize==null</code> it
     *         will return {@link ButtonState#DEFAULT}.
     */
    public static final ButtonState getForContextState(final String state) {
        if (isEmpty(state)) {
            return ButtonState.DEFAULT;
        }
        return valueOf(state.toUpperCase());
    }
}
