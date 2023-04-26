package de.cuioss.jsf.bootstrap.icon.support;

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
public enum IconSize implements StyleClassProvider {

    /** The default. */
    DEFAULT(""),
    /** Large. */
    LG("lg"),
    /** XL, addition to bootstrap. */
    XL("xl"),
    /** XXL, addition to bootstrap. */
    XXL("xxl"),
    /** XXXL, addition to bootstrap. */
    XXXL("xxxl");

    IconSize(String suffix) {
        if (MoreStrings.isEmpty(suffix)) {
            styleClass = "";
        } else {
            styleClass = new StringBuilder().append(PREFIX).append(suffix).toString();
        }
    }

    private static final String PREFIX = "cui-icon-";

    @Getter
    private final String styleClass;

    @Override
    public StyleClassBuilder getStyleClassBuilder() {
        return new StyleClassBuilderImpl(styleClass);
    }

    /**
     * @param contextSize
     *            May be null, otherwise must be one of {@link ContextSize#LG},
     *            {@link ContextSize#XL}, {@link ContextSize#XXL},
     *            {@link ContextSize#XXXL}
     * @return the corresponding {@link IconSize} derived by the given
     *         {@link ContextSize}. In case of <code>contextSize==null</code> it
     *         will return {@link IconSize#DEFAULT}. In case it is none of the
     *         supported sizes it will throw an {@link IllegalArgumentException}
     */
    public static final IconSize getForContextSize(ContextSize contextSize) {
        var result = DEFAULT;
        if (null != contextSize) {
            switch (contextSize) {
                case DEFAULT:
                    result = IconSize.DEFAULT;
                    break;
                case LG:
                    result = IconSize.LG;
                    break;
                case XL:
                    result = IconSize.XL;
                    break;
                case XXL:
                    result = IconSize.XXL;
                    break;
                case XXXL:
                    result = IconSize.XXXL;
                    break;
                // $CASES-OMITTED$
                default:
                    throw new IllegalArgumentException("No IconSize defined for " + contextSize);
            }
        }
        return result;
    }
}
