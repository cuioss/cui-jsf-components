package com.icw.ehf.cui.components.bootstrap.button.support;

import static de.cuioss.tools.string.MoreStrings.isEmpty;

import com.icw.ehf.cui.core.api.components.css.ContextState;
import com.icw.ehf.cui.core.api.components.css.StyleClassBuilder;
import com.icw.ehf.cui.core.api.components.css.StyleClassProvider;
import com.icw.ehf.cui.core.api.components.css.impl.StyleClassBuilderImpl;

import de.cuioss.tools.string.MoreStrings;
import lombok.Getter;

/**
 * @author Oliver Wolff
 *
 */
public enum ButtonState implements StyleClassProvider {

    /** The default-state. */
    DEFAULT("default"),
    /** Primary. */
    PRIMARY("primary"),
    /** Success. */
    SUCCESS("success"),
    /** Info. */
    INFO("info"),
    /** Warning. */
    WARNING("warning"),
    /** error. */
    DANGER("danger"),
    /** Light. */
    LINK("link"),;

    ButtonState(final String suffix) {
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
     * @param state
     *            May be null or empty, otherwise must be one of {"default","primary",
     *            "success", "info", "warning", "danger", "link"}
     * @return the corresponding {@link ButtonState} derived by the given
     *         {@link ContextState}. In case of <code>contextSize==null</code>
     *         it will return {@link ButtonState#DEFAULT}.
     */
    public static final ButtonState getForContextState(final String state) {
        if (isEmpty(state)) {
            return ButtonState.DEFAULT;
        }
        return valueOf(state.toUpperCase());
    }
}
