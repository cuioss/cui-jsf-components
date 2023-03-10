package de.cuioss.jsf.api.application.history.impl;

import java.util.ArrayList;
import java.util.List;

import de.cuioss.jsf.api.application.history.HistoryConfiguration;
import de.cuioss.jsf.api.application.view.matcher.EmptyViewMatcher;
import de.cuioss.jsf.api.application.view.matcher.ViewMatcher;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Default Implementation of {@link HistoryConfiguration} defaulting to:
 * <ul>
 * <li>historySize=10</li>
 * <li>excludeFacesParameter=true</li>
 * <li>excludeFromHistoryMatcher = {@link EmptyViewMatcher} that will always return false.
 * <li>
 * </ul>
 *
 * @author Oliver Wolff
 */
@EqualsAndHashCode
@ToString
public class HistoryConfigurationImpl implements HistoryConfiguration {

    private static final long serialVersionUID = -8099557006752461021L;

    @Setter
    @Getter
    private String fallback;

    @Setter
    @Getter
    private String fallbackOutcome;

    @Setter
    @Getter
    private int historySize = HistoryConfigurationImpl.DEFAULT_HISTORY_SIZE;

    @Setter
    @Getter
    private List<String> excludeParameter = new ArrayList<>();

    @Setter
    @Getter
    private boolean excludeFacesParameter = true;

    @Getter
    @Setter
    private ViewMatcher excludeFromHistoryMatcher = new EmptyViewMatcher(false);

    /**
     * The default size for the history-size
     */
    public static final int DEFAULT_HISTORY_SIZE = 10;

    /**
     * Bean name for looking up instances.
     */
    public static final String BEAN_NAME = "historyConfiguration";

}
