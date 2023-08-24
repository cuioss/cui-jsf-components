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

import static de.cuioss.tools.string.MoreStrings.isEmpty;

import de.cuioss.jsf.api.components.css.impl.StyleClassBuilderImpl;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Defines the relative float-alignments.
 *
 * @author Oliver Wolff
 *
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum AlignHolder implements StyleClassProvider {

    /** Left-align */
    LEFT(CssCommon.PULL_LEFT.getStyleClass()),

    /** Right-align */
    RIGHT(CssCommon.PULL_RIGHT.getStyleClass()),

    /** Not defined. */
    DEFAULT("");

    @Getter
    private final String styleClass;

    @Override
    public StyleClassBuilder getStyleClassBuilder() {
        return new StyleClassBuilderImpl(styleClass);
    }

    /**
     * Create an instance of {@link AlignHolder} according to the given String.
     *
     * @param align String identifier, may be null. The call is case insensitive.
     *              "right" will result in {@link AlignHolder#LEFT}, "right" in
     *              {@link AlignHolder#RIGHT}. In all other cases it will return
     *              {@link AlignHolder#DEFAULT}
     * @return the corresponding {@link AlignHolder}
     */
    public static final AlignHolder getFromString(String align) {
        var result = AlignHolder.DEFAULT;
        if (!isEmpty(align)) {
            var upperCase = align.toUpperCase();
            switch (upperCase) {
            case "LEFT":
                result = LEFT;
                break;
            case "RIGHT":
                result = RIGHT;
                break;
            default:
                break;
            }
        }
        return result;
    }
}
