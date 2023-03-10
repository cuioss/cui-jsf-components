package de.cuioss.jsf.api.components.css;

import de.cuioss.tools.string.MoreStrings;

/**
 * Represents the bootstrap context-state.
 *
 * @author Oliver Wolff
 *
 */
public enum ContextSize {

    /** The default size. Will usually ignored. */
    DEFAULT,
    /** Extras Small. */
    XS,
    /** Small. */
    SM,
    /** Medium. */
    MD,
    /** Large. */
    LG,
    /** XL, addition to bootstrap. */
    XL,
    /** XXL, addition to bootstrap. */
    XXL,
    /** XXXL, addition to bootstrap. */
    XXXL;

    /**
     * Factory method for computing an {@link ContextSize} out of a given
     * {@link String}
     *
     * @param size
     *            String representation of the size. It is interpreted case
     *            insensitive. It can be either null or empty or must be one of
     *            {"XS", "SM", "MD", "LG", "XL", "XXL", "XXXL"} (case
     *            insensitive). The Input will implicitly be trimmed.
     * @return The {@link ContextSize} representation computed of the given
     *         String. If it is null or empty {@link ContextSize#DEFAULT} will
     *         be returned. If the given String does not match to the constants
     *         a {@link IllegalArgumentException} will be thrown.
     */
    public static final ContextSize getFromString(String size) {
        var result = DEFAULT;
        if (!MoreStrings.isBlank(size)) {
            result = valueOf(size.trim().toUpperCase());
        }
        return result;
    }

}
