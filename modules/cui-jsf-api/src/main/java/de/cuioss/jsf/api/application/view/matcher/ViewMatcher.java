package de.cuioss.jsf.api.application.view.matcher;

import java.io.Serializable;

import de.cuioss.jsf.api.common.view.ViewDescriptor;

/**
 * View matchers are used to check whether a given view matches to given set of
 * parameters that are usually configured for the application.
 *
 * @author Oliver Wolff
 */
public interface ViewMatcher extends Serializable {

    /**
     * Checks whether a given view matches to given set of parameters that are
     * usually configured for the application.
     *
     * @param viewDescriptor to be matched. Must not be null
     * @return whether the given view matches.
     */
    boolean match(ViewDescriptor viewDescriptor);
}
