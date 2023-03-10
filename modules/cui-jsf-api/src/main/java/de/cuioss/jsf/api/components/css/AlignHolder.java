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
     *
     * @param align
     *            String identifier, may be null. The call is case insensitive.
     *            "right" will result in {@link AlignHolder#LEFT}, "right" in
     *            {@link AlignHolder#RIGHT}. In all other cases it will return
     *            {@link AlignHolder#DEFAULT}
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
