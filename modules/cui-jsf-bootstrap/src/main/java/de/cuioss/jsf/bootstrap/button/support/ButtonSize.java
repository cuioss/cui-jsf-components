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

import de.cuioss.jsf.api.components.css.ContextSize;
import de.cuioss.jsf.api.components.css.StyleClassProvider;
import de.cuioss.tools.string.MoreStrings;
import lombok.Getter;

/**
 * @author Oliver Wolff
 */
@Getter
public enum ButtonSize implements StyleClassProvider {

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
    SM("sm");

    ButtonSize(final String suffix) {
        if (MoreStrings.isEmpty(suffix)) {
            styleClass = "";
        } else {
            styleClass = PREFIX + suffix;
        }
    }

    private static final String PREFIX = "btn-";

    private final String styleClass;

    /**
     * @param contextSize Maybe null, otherwise must be one of
     *                    {@link ContextSize#LG}, {@link ContextSize#DEFAULT} or
     *                    {@link ContextSize#SM}
     * @return the corresponding {@link ButtonSize} derived by the given
     * {@link ContextSize}. In case of <code>contextSize==null</code> it
     * will return {@link ButtonSize#DEFAULT}. In case it is none of the
     * supported sizes it will throw an {@link IllegalArgumentException}
     */
    public static ButtonSize getForContextSize(final ContextSize contextSize) {
        if (null != contextSize) {
            return switch (contextSize) {
                case DEFAULT -> ButtonSize.DEFAULT;
                case LG -> ButtonSize.LG;
                case SM -> ButtonSize.SM;
                default -> throw new IllegalArgumentException("No Button-Size defined for " + contextSize);
            };
        }
        return ButtonSize.DEFAULT;
    }
}
