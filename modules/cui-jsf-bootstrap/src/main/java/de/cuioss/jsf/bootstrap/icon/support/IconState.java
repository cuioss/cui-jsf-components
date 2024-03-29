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

import de.cuioss.jsf.api.components.css.ContextState;
import de.cuioss.jsf.api.components.css.StyleClassProvider;
import de.cuioss.tools.string.MoreStrings;
import lombok.Getter;

/**
 * @author Oliver Wolff
 */
public enum IconState implements StyleClassProvider {

    /**
     * The default-state (empty)
     */
    DEFAULT(""),
    /**
     * Primary.
     */
    PRIMARY("primary"),
    /**
     * Success.
     */
    SUCCESS("success"),
    /**
     * Info.
     */
    INFO("info"),
    /**
     * Warning.
     */
    WARNING("warning"),
    /**
     * error.
     */
    DANGER("danger");

    IconState(String suffix) {
        if (MoreStrings.isEmpty(suffix)) {
            styleClass = "";
        } else {
            styleClass = PREFIX + suffix;
        }
    }

    private static final String PREFIX = "cui-icon-state-";

    @Getter
    private final String styleClass;

    /**
     * @param contextState Maybe null, otherwise must be one of
     *                     {"DEFAULT","PRIMARY", "SUCCESS", "INFO", "WARNING",
     *                     "DANGER"}
     * @return the corresponding {@link IconState} derived by the given
     * {@link ContextState}.
     * In case of <code>contextSize==null</code> it
     * will return {@link IconState#DEFAULT}.
     */
    public static IconState getForContextState(ContextState contextState) {
        if (null != contextState) {
            return valueOf(contextState.name());
        }
        return DEFAULT;
    }
}
