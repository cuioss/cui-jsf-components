package de.cuioss.jsf.api.application.history;

import java.io.Serializable;
import java.util.List;

import de.cuioss.jsf.api.application.history.impl.HistoryManagerBean;
import de.cuioss.jsf.api.application.view.matcher.ViewMatcher;

/**
 * Provides the concrete configuration for {@link HistoryManagerBean}
 *
 * @author Oliver Wolff
 */
public interface HistoryConfiguration extends Serializable {

    /**
     * @return The fallback url to be used if history is empty. <em>Caution:</em>
     *         Currently we do not consider parameter for this url, we utilize it as
     *         outcome only. May be null but than {@link #getFallbackOutcome()} must
     *         not be null
     */
    String getFallback();

    /**
     * @return The outcome-variant of the fallback. The configuration must either
     *         provide a {@link #getFallback()} or {@link #getFallbackOutcome()}
     */
    String getFallbackOutcome();

    /**
     * @return Defines the count of the available history entries. Sensible default
     *         is 10 It must be in the range 1 &lt; historySize &lt; 100
     */
    int getHistorySize();

    /**
     * @return The String ids of the parameters to be excluded. <em>Caution: </em>
     *         the checks are against lower-case, therefore the excludes should be
     *         lower-case as well, because else they will be ignored.
     */
    List<String> getExcludeParameter();

    /**
     * Indicates whether to filter jsf specific technical parameter. Sensible
     * default is true
     *
     * @return the boolean flag
     */
    boolean isExcludeFacesParameter();

    /**
     * @return a view matcher that checks whether the given view is to be added to
     *         history at all.
     */
    ViewMatcher getExcludeFromHistoryMatcher();
}
