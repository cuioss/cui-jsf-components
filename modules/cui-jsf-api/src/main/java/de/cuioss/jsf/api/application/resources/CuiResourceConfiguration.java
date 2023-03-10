package de.cuioss.jsf.api.application.resources;

import java.io.Serializable;
import java.util.List;

/**
 * Combines the runtime information that are specific to the
 * {@link CuiResourceHandler}. Usually this are version information (for the
 * cache-buster-functionality) and library names that are to be under version
 * control.
 *
 * @author Oliver Wolff
 *
 */
public interface CuiResourceConfiguration extends Serializable {

    /**
     * Returns JSF-resource version.
     *
     * @return JSF-resource version.
     */
    String getVersion();

    /**
     * @return a list of libraries that should be handled by the
     *         {@link CuiResourceHandler}
     */
    List<String> getHandledLibraries();

    /**
     * @return The list of suffixes to be handled, usually "css, js"
     */
    List<String> getHandledSuffixes();

}
