package de.cuioss.jsf.bootstrap.modal.support;

import static de.cuioss.tools.string.MoreStrings.nullToEmpty;

import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.api.components.css.StyleClassProvider;
import de.cuioss.jsf.api.components.css.impl.StyleClassBuilderImpl;
import de.cuioss.tools.string.MoreStrings;
import lombok.Getter;

/**
 * Represents the different size for bootstrap modal dialog
 *
 * @author Oliver Wolff
 *
 */
public enum ModalDialogSize implements StyleClassProvider {

    /** The default. */
    DEFAULT(""),
    /** Large. */
    LG("lg"),
    /** SM. */
    SM("sm"),
    /** Fluid. */
    FLUID("fluid");

    ModalDialogSize(final String suffix) {
        if (MoreStrings.isEmpty(suffix)) {
            styleClass = "";
        } else {
            styleClass = new StringBuilder().append(PREFIX).append(suffix).toString();
        }
    }

    static final String PREFIX = "modal-";

    @Getter
    private final String styleClass;

    @Override
    public StyleClassBuilder getStyleClassBuilder() {
        return new StyleClassBuilderImpl(styleClass);
    }

    /**
     * @param size
     *            May be null, otherwise must be one of "lg", "sm", "default" or "fluid"
     * @return the corresponding {@link ModalDialogSize} derived by the given
     *         string. In case of <code>contextSize==null</code> it
     *         will return {@link ModalDialogSize#DEFAULT}. In case it is none of the
     *         supported sizes it will throw an {@link IllegalArgumentException}
     */
    public static final ModalDialogSize getFromString(final String size) {
        if (nullToEmpty(size).trim().isEmpty()) {
            return ModalDialogSize.DEFAULT;
        }
        return valueOf(size.toUpperCase());
    }
}
