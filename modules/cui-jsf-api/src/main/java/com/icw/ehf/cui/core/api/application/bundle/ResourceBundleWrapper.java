package com.icw.ehf.cui.core.api.application.bundle;

import java.io.Serializable;
import java.util.Collections;
import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Wraps the Bundles managed by cui. It contains a method for accessing the actual message and a
 * technical method for the caching of the underlying {@link ResourceBundle}
 *
 * @author Oliver Wolff
 */
public interface ResourceBundleWrapper extends Serializable {

    /**
     * Returns resolved message by given key.
     *
     * @param key to be looked up
     * @return the resolved message if key is defined, otherwise "??[key value]??" will be
     *         returned and warning will be written
     * @throws MissingResourceException if key is unknown and application is in development mode
     */
    String getMessage(String key);

    /**
     * Technical method for {@link ResourceBundle} caching the resolved keys and values @see
     * {@link ResourceBundle#getKeys()}
     *
     * @return an Enumeration of the keys contained in this ResourceBundle and its parent bundles.
     */
    default Enumeration<String> getKeys() {
        return Collections.enumeration(keySet());
    }

    /**
     * @return set of all supported keys of this bundle
     */
    Set<String> keySet();

    /**
     * @return a list of all configured bundles to be used for debugging.
     */
    String getBundleContent();

}
