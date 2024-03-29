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

import de.cuioss.jsf.api.components.css.ContextSize;
import de.cuioss.jsf.api.components.css.StyleClassProvider;
import de.cuioss.tools.string.MoreStrings;
import lombok.Getter;

/**
 * @author Oliver Wolff
 */
public enum IconSize implements StyleClassProvider {

    /**
     * The default.
     */
    DEFAULT(""),
    /**
     * Large.
     */
    LG("lg"),
    /**
     * XL, addition to bootstrap.
     */
    XL("xl"),
    /**
     * XXL, addition to bootstrap.
     */
    XXL("xxl"),
    /**
     * XXXL, addition to bootstrap.
     */
    XXXL("xxxl");

    IconSize(String suffix) {
        if (MoreStrings.isEmpty(suffix)) {
            styleClass = "";
        } else {
            styleClass = PREFIX + suffix;
        }
    }

    private static final String PREFIX = "cui-icon-";

    @Getter
    private final String styleClass;


    /**
     * @param contextSize Maybe null, otherwise must be one of
     *                    {@link ContextSize#LG}, {@link ContextSize#XL},
     *                    {@link ContextSize#XXL}, {@link ContextSize#XXXL}
     * @return the corresponding {@link IconSize} derived by the given
     * {@link ContextSize}.
     * In case of <code>contextSize==null</code> it
     * will return {@link IconSize#DEFAULT}.
     * In case it is none of the
     * supported sizes it will throw an {@link IllegalArgumentException}
     */
    public static IconSize getForContextSize(ContextSize contextSize) {
        var result = DEFAULT;
        if (null != contextSize) {
            result = switch (contextSize) {
                case DEFAULT -> IconSize.DEFAULT;
                case LG -> IconSize.LG;
                case XL -> IconSize.XL;
                case XXL -> IconSize.XXL;
                case XXXL -> IconSize.XXXL;
                default -> throw new IllegalArgumentException("No IconSize defined for " + contextSize);
            };
        }
        return result;
    }
}
