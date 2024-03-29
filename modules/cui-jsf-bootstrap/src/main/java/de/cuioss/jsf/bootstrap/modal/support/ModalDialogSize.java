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
package de.cuioss.jsf.bootstrap.modal.support;

import de.cuioss.jsf.api.components.css.StyleClassProvider;
import de.cuioss.tools.string.MoreStrings;
import lombok.Getter;

import static de.cuioss.tools.string.MoreStrings.nullToEmpty;

/**
 * Represents the different size for bootstrap modal dialog
 *
 * @author Oliver Wolff
 */
@Getter
public enum ModalDialogSize implements StyleClassProvider {

    /**
     * The default.
     */
    DEFAULT(""),
    /**
     * Large.
     */
    LG("lg"),
    /**
     * SM.
     */
    SM("sm"),
    /**
     * Fluid.
     */
    FLUID("fluid");

    ModalDialogSize(final String suffix) {
        if (MoreStrings.isEmpty(suffix)) {
            styleClass = "";
        } else {
            styleClass = PREFIX + suffix;
        }
    }

    static final String PREFIX = "modal-";

    private final String styleClass;

    /**
     * @param size Maybe null, otherwise must be one of "lg", "sm", "default" or
     *             "fluid"
     * @return the corresponding {@link ModalDialogSize} derived by the given
     * string.
     * In case of <code>contextSize==null</code> it will return
     * {@link ModalDialogSize#DEFAULT}.
     * In case it is none of the supported
     * sizes it will throw an {@link IllegalArgumentException}
     */
    public static ModalDialogSize getFromString(final String size) {
        if (nullToEmpty(size).trim().isEmpty()) {
            return ModalDialogSize.DEFAULT;
        }
        return valueOf(size.toUpperCase());
    }
}
