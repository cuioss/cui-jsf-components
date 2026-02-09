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
package de.cuioss.jsf.bootstrap.tag.support;

import de.cuioss.jsf.api.components.css.ContextState;
import de.cuioss.jsf.api.components.css.StyleClassProvider;
import de.cuioss.tools.string.MoreStrings;
import lombok.Getter;

/**
 * Represents visual states for Tag components based on Bootstrap contextual styling.
 * Each state maps to a specific CSS class for different semantic meanings.
 * 
 * <h2>Available States</h2>
 * <ul>
 *   <li>DEFAULT - General tags without specific meaning</li>
 *   <li>PRIMARY - Tags with visual emphasis</li>
 *   <li>SUCCESS - Tags for positive outcomes</li>
 *   <li>INFO - Tags for informational content</li>
 *   <li>WARNING - Tags for caution conditions</li>
 *   <li>DANGER - Tags for critical issues</li>
 * </ul>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
@Getter
public enum TagState implements StyleClassProvider {

    /**
     * The default neutral state.
     * <p>Useful for general tags without specific semantic meaning.</p>
     * <p>CSS class: cui-tag-default</p>
     */
    DEFAULT("default"),

    /**
     * Primary highlight state.
     * <p>Useful for tags that need visual emphasis or represent primary actions/statuses.</p>
     * <p>CSS class: cui-tag-primary</p>
     */
    PRIMARY("primary"),

    /**
     * Success state indicating positive outcomes.
     * <p>Useful for tags representing successful operations, completed status, or positive conditions.</p>
     * <p>CSS class: cui-tag-success</p>
     */
    SUCCESS("success"),

    /**
     * Informational state.
     * <p>Useful for tags containing informational content, notes, or neutral information.</p>
     * <p>CSS class: cui-tag-info</p>
     */
    INFO("info"),

    /**
     * Warning state indicating caution.
     * <p>Useful for tags representing warnings, alerts that require attention but are not critical.</p>
     * <p>CSS class: cui-tag-warning</p>
     */
    WARNING("warning"),

    /**
     * Danger state indicating critical issues.
     * <p>Useful for tags representing errors, failures, or critical conditions that require immediate attention.</p>
     * <p>CSS class: cui-tag-danger</p>
     */
    DANGER("danger");

    TagState(String suffix) {
        if (MoreStrings.isEmpty(suffix)) {
            styleClass = "";
        } else {
            styleClass = PREFIX + suffix;
        }
    }

    private static final String PREFIX = "cui-tag-";

    private final String styleClass;

    /**
     * Converts a {@link ContextState} to the corresponding {@link TagState}.
     * This allows for consistent mapping between general context states and
     * tag-specific visual states.
     * 
     * @param contextState The context state to convert. May be null, otherwise must 
     *                     be one of {"DEFAULT", "PRIMARY", "SUCCESS", "INFO", "WARNING", "DANGER"}
     * @return the corresponding {@link TagState} derived from the given
     *         {@link ContextState}. If contextState is null, returns {@link TagState#DEFAULT}
     * @since 1.0
     */
    public static TagState getForContextState(ContextState contextState) {
        if (null != contextState) {
            return valueOf(contextState.name());
        }
        return DEFAULT;
    }
}
