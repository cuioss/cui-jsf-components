package com.icw.ehf.cui.components.bootstrap.icon.support;

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
public enum IconState implements StyleClassProvider {

    /** The default-state (empty) */
    DEFAULT(""),
    /** Primary. */
    PRIMARY("primary"),
    /** Success. */
    SUCCESS("success"),
    /** Info. */
    INFO("info"),
    /** Warning. */
    WARNING("warning"),
    /** error. */
    DANGER("danger");

    IconState(String suffix) {
        if (MoreStrings.isEmpty(suffix)) {
            styleClass = "";
        } else {
            styleClass = new StringBuilder().append(PREFIX).append(suffix).toString();
        }
    }

    private static final String PREFIX = "cui-icon-state-";

    @Getter
    private final String styleClass;

    @Override
    public StyleClassBuilder getStyleClassBuilder() {
        return new StyleClassBuilderImpl(styleClass);
    }

    /**
     * @param contextState
     *            May be null, otherwise must be one of {"DEFAULT","PRIMARY",
     *            "SUCCESS", "INFO", "WARNING", "DANGER"}
     * @return the corresponding {@link IconState} derived by the given
     *         {@link ContextState}. In case of <code>contextSize==null</code>
     *         it will return {@link IconState#DEFAULT}.
     */
    public static final IconState getForContextState(ContextState contextState) {
        var result = DEFAULT;
        if (null != contextState) {
            result = valueOf(contextState.name());
        }
        return result;
    }
}
