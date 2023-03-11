package de.cuioss.jsf.api.components.css;

import de.cuioss.jsf.api.components.css.impl.StyleClassBuilderImpl;
import de.cuioss.tools.string.MoreStrings;

/**
 * Represents the bootstrap contextState.
 *
 * @author Oliver Wolff
 * @author Sven Haag
 */
public enum ContextState implements StyleClassProvider {

    /** The default state. Will usually ignored. */
    DEFAULT,
    /** Primary. */
    PRIMARY,
    /** Success. */
    SUCCESS,
    /** Info. */
    INFO,
    /** warning. */
    WARNING,
    /** A more visual state. */
    LIGHT,
    /** Danger. */
    DANGER;

    /**
     * Factory method for computing an {@link ContextState} out of a given
     * {@link String}
     *
     * @param state
     *            String representation of the state. It is interpreted case
     *            insensitive. It can be either null or empty or must be one of
     *            {"DEFAULT","PRIMARY", "SUCCESS", "INFO", "WARNING", "DANGER", "LIGHT"}
     *            (case insensitive). The Input will implicitly be trimmed.
     * @return The {@link ContextState} representation computed of the given
     *         String. If it is null or empty {@link ContextState#DEFAULT} will
     *         be returned. If the given String does not match to the constants
     *         a {@link IllegalArgumentException} will be thrown.
     */
    public static final ContextState getFromString(final String state) {
        var result = DEFAULT;
        if (!MoreStrings.isBlank(state)) {
            result = valueOf(state.trim().toUpperCase());
        }
        return result;
    }

    /**
     * The prefix will be appended with a dash, e.g. "foo-success"
     *
     * @param prefix or null
     * @return Prefixed CSS state class in lower case
     */
    public final String getStyleClassWithPrefix(final String prefix) {
        if (MoreStrings.isEmpty(prefix)) {
            return getStyleClass();
        }
        return new StringBuilder()
                .append(prefix)
                .append('-')
                .append(name().toLowerCase())
                .toString();
    }

    /**
     * The prefix will be appended with a dash, e.g. "foo-success"
     *
     * @param prefix or null
     * @return Prefixed CSS state class in lower case
     */
    public final StyleClassBuilder getStyleClassBuilderWithPrefix(final String prefix) {
        return new StyleClassBuilderImpl(getStyleClassWithPrefix(prefix));
    }

    /**
     * @return The current ContextState in lower case.
     */
    @Override
    public final String getStyleClass() {
        return name().toLowerCase();
    }

    /**
     * @return StyleClassBuilder with the current ContextState in lower case as base class.
     */
    @Override
    public StyleClassBuilder getStyleClassBuilder() {
        return new StyleClassBuilderImpl(getStyleClass());
    }
}
