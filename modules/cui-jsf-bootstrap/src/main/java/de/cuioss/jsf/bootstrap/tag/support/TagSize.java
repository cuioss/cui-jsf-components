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
            switch (contextSize) {
            case DEFAULT:
                result = TagSize.DEFAULT;
                break;
            case SM:
                result = TagSize.SM;
                break;
            case LG:
                result = TagSize.LG;
                break;
            case XL:
                result = TagSize.XL;
                break;
            // $CASES-OMITTED$
            default:
                throw new IllegalArgumentException("No TagSize defined for " + contextSize);
            }
        }
        return result;
    }
}
