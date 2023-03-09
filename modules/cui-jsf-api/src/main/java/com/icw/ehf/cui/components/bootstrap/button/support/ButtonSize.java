package com.icw.ehf.cui.components.bootstrap.button.support;

import com.icw.ehf.cui.core.api.components.css.ContextSize;
import com.icw.ehf.cui.core.api.components.css.StyleClassBuilder;
import com.icw.ehf.cui.core.api.components.css.StyleClassProvider;
import com.icw.ehf.cui.core.api.components.css.impl.StyleClassBuilderImpl;

import de.cuioss.tools.string.MoreStrings;
import lombok.Getter;

/**
 * @author Oliver Wolff
 *
 */
public enum ButtonSize implements StyleClassProvider {

    /** The default. */
    DEFAULT(""),
    /** Large. */
    LG("lg"),
    /** SM. */
    SM("sm");

    ButtonSize(final String suffix) {
        if (MoreStrings.isEmpty(suffix)) {
            styleClass = "";
        } else {
            styleClass = new StringBuilder().append(PREFIX).append(suffix).toString();
        }
    }

    private static final String PREFIX = "btn-";

    @Getter
    private final String styleClass;

    @Override
    public StyleClassBuilder getStyleClassBuilder() {
        return new StyleClassBuilderImpl(styleClass);
    }

    /**
     * @param contextSize
     *            May be null, otherwise must be one of {@link ContextSize#LG},
     *            {@link ContextSize#DEFAULT} or {@link ContextSize#SM}
     * @return the corresponding {@link ButtonSize} derived by the given
     *         {@link ContextSize}. In case of <code>contextSize==null</code> it
     *         will return {@link ButtonSize#DEFAULT}. In case it is none of the
     *         supported sizes it will throw an {@link IllegalArgumentException}
     */
    public static final ButtonSize getForContextSize(final ContextSize contextSize) {
        if (null != contextSize) {
            switch (contextSize) {
                case DEFAULT:
                    return ButtonSize.DEFAULT;
                case LG:
                    return ButtonSize.LG;
                case SM:
                    return ButtonSize.SM;
                default:
                    throw new IllegalArgumentException("No Button-Size defined for " + contextSize);
            }
        }
        return ButtonSize.DEFAULT;
    }
}
