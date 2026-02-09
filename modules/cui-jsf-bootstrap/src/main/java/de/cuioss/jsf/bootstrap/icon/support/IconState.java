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
package de.cuioss.jsf.bootstrap.icon.support;

import de.cuioss.jsf.api.components.css.ContextState;
import de.cuioss.jsf.api.components.css.StyleClassProvider;
import de.cuioss.tools.string.MoreStrings;
import lombok.Getter;

/**
 * Defines available icon states for the CUI bootstrap framework.
 * Each state corresponds to a Bootstrap contextual state.
 * 
 * @author Oliver Wolff
 */
public enum IconState implements StyleClassProvider {

    /**
     * Default state with no additional CSS class.
     */
    DEFAULT(""),

    /**
     * Primary state for primary actions.
     */
    PRIMARY("primary"),

    /**
     * Success state for positive outcomes.
     */
    SUCCESS("success"),

    /**
     * Info state for informational content.
     */
    INFO("info"),

    /**
     * Warning state for cautionary information.
     */
    WARNING("warning"),

    /**
     * Danger state for errors or destructive actions.
     */
    DANGER("danger");

    private static final String PREFIX = "cui-icon-state-";

    @Getter
    private final String styleClass;

    IconState(String suffix) {
        if (MoreStrings.isEmpty(suffix)) {
            styleClass = "";
        } else {
            styleClass = PREFIX + suffix;
        }
    }

    /**
     * Maps a {@link ContextState} to the corresponding {@link IconState}.
     * 
     * @param contextState The ContextState to convert, may be null
     * @return the matching IconState or {@link IconState#DEFAULT} if null
     * @throws IllegalArgumentException If no matching IconState exists
     */
    public static IconState getForContextState(ContextState contextState) {
        if (null != contextState) {
            return valueOf(contextState.name());
        }
        return DEFAULT;
    }
}
