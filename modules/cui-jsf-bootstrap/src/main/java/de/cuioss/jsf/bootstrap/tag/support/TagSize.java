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
package de.cuioss.jsf.bootstrap.tag.support;

import de.cuioss.jsf.api.components.css.ContextSize;
import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.api.components.css.StyleClassProvider;
import de.cuioss.jsf.api.components.css.impl.StyleClassBuilderImpl;
import de.cuioss.tools.string.MoreStrings;
import lombok.Getter;

/**
 * @author Oliver Wolff
 *
 */
public enum TagSize implements StyleClassProvider {

    /** The default. */
    DEFAULT(""),
    /** Small. */
    SM("sm"),
    /** Large. */
    LG("lg"),
    /** XL, addition to bootstrap. */
    XL("xl");

    TagSize(String suffix) {
        if (MoreStrings.isEmpty(suffix)) {
            styleClass = "";
        } else {
            styleClass = new StringBuilder().append(PREFIX).append(suffix).toString();
        }
    }

    private static final String PREFIX = "cui-tag-";

    @Getter
    private final String styleClass;

    @Override
    public StyleClassBuilder getStyleClassBuilder() {
        return new StyleClassBuilderImpl(styleClass);
    }

    /**
     * @param contextSize May be null, otherwise must be one of
     *                    {@link ContextSize#SM}, {@link ContextSize#XL},
     *                    {@link ContextSize#LG}
     * @return the corresponding {@link TagSize} derived by the given
     *         {@link ContextSize}. In case of <code>contextSize==null</code> it
     *         will return {@link TagSize#DEFAULT}. In case it is none of the
     *         supported sizes it will throw an {@link IllegalArgumentException}
     */
    public static final TagSize getForContextSize(ContextSize contextSize) {
        var result = DEFAULT;
        if (null != contextSize) {
            result = switch (contextSize) {
            case DEFAULT -> TagSize.DEFAULT;
            case SM -> TagSize.SM;
            case LG -> TagSize.LG;
            case XL -> TagSize.XL;
            default -> throw new IllegalArgumentException("No TagSize defined for " + contextSize);
            };
        }
        return result;
    }
}
