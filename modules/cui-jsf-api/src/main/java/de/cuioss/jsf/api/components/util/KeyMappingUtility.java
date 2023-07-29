package de.cuioss.jsf.api.components.util;

import static java.util.Objects.requireNonNull;

import java.io.Serializable;

import de.cuioss.tools.string.MoreStrings;

/**
 * Simple mapping helper to introduce key extension-mapping
 *
 * @author Oliver Wolff
 */
public final class KeyMappingUtility {

    /**
     * Creates a key String depending on its input.
     *
     * @param key       must not be null, must implement toString properly.
     * @param extension may be null or empty
     * @return If extension-parameter is null or empty it returns key.toString().
     *         Otherwise it returns key.toString()_extension
     */
    public static String mapKeyWithExtension(Serializable key, String extension) {
        requireNonNull(key);
        var mappedKey = key.toString();
        if (!MoreStrings.isEmpty(extension)) {
            mappedKey = new StringBuilder(mappedKey).append('_').append(extension).toString();
        }
        return mappedKey;
    }

    /**
     * Enforce utility class
     */
    private KeyMappingUtility() {

    }
}
