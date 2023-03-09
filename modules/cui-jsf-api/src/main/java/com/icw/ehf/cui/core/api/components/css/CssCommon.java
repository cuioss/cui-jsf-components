package com.icw.ehf.cui.core.api.components.css;

import com.icw.ehf.cui.core.api.components.css.impl.StyleClassBuilderImpl;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represents common styleClasses that are not specific to a concrete library
 * like bootstrap.
 *
 * @author Oliver Wolff
 *
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum CssCommon implements StyleClassProvider {

    /** General disabled class. */
    DISABLED("disabled"),
    /** Shorthand for Bootstrap-style float:right. */
    PULL_RIGHT("pull-right"),
    /** Shorthand for Bootstrap-style float:left. */
    PULL_LEFT("pull-left");

    @Getter
    private final String styleClass;

    @Override
    public StyleClassBuilder getStyleClassBuilder() {
        return new StyleClassBuilderImpl(styleClass);
    }
}
