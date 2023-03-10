package de.cuioss.jsf.api.application.history.impl;

import static de.cuioss.tools.string.MoreStrings.emptyToNull;
import static de.cuioss.tools.string.MoreStrings.isEmpty;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.FacesContext;

import de.cuioss.jsf.api.application.history.HistoryConfiguration;
import de.cuioss.jsf.api.application.history.HistoryManager;
import de.cuioss.jsf.api.application.navigation.NavigationUtils;
import de.cuioss.jsf.api.application.navigation.ViewIdentifier;
import de.cuioss.jsf.api.application.view.matcher.ViewMatcher;
import de.cuioss.jsf.api.common.view.ViewDescriptor;
import de.cuioss.tools.logging.CuiLogger;
import de.cuioss.tools.net.ParameterFilter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Bean keeping track of the view history. For configuration see package-info
 * The implementation utilizes a stack to store the history.
 *
 * @author Oliver Wolff
 */
@EqualsAndHashCode
@ToString
public class HistoryManagerImpl implements HistoryManager {

    private static final long serialVersionUID = 2205593126500409010L;

    private static final CuiLogger log = new CuiLogger(HistoryManagerImpl.class);

    /**
     * Bean name for looking up instances.
     */
    public static final String BEAN_NAME = "historyManager";

    private final int historySize;

    /** The technical parameter filter. */
    @Getter
    private final ParameterFilter parameterFilter;

    @Getter
    @Setter
    private boolean pageReload;

    /**
     * The current view, that is not put history yet.
     */
    private ViewIdentifier currentView;

    /** The storage for the history. */
    private List<ViewIdentifier> history;

    private final ViewMatcher excludeFromHistoryMatcher;

    private final HistoryConfiguration historyConfiguration;

    /**
     * Constructor.
     *
     * @param historyConfiguration
     *            must not be null
     */
    public HistoryManagerImpl(final HistoryConfiguration historyConfiguration) {
        this.historyConfiguration = historyConfiguration;
        currentView = null;

        historySize = historyConfiguration.getHistorySize();
        if (historySize < 2 || historySize > 99) {
            throw new IllegalStateException(
                    "Sensible default is 10 It must be in the range 1 < historySize < 100, but was " + historySize);
        }
        parameterFilter = new ParameterFilter(historyConfiguration.getExcludeParameter(),
                historyConfiguration.isExcludeFacesParameter());

        excludeFromHistoryMatcher = historyConfiguration.getExcludeFromHistoryMatcher();
    }

    @Override
    public ViewIdentifier getCurrentView() {
        if (null != currentView) {
            return currentView;
        }
        return getFallbackIdentifier();
    }

    @Override
    public void addCurrentUriToHistory(final ViewDescriptor viewDescriptor) {
        log.trace("addCurrentUriToHistory(id = {}, viewDescriptor = {})", String.valueOf(System.identityHashCode(this)),
                viewDescriptor);
        if (viewDescriptor.isViewDefined() && !excludeFromHistoryMatcher.match(viewDescriptor)) {
            final var currentViewIdentifier =
                ViewIdentifier.getFromViewDesciptor(viewDescriptor, parameterFilter);
            log.trace("currentViewIdentifier = {}", currentViewIdentifier);
            if (null != currentViewIdentifier) {
                handleAddCurrentView(currentViewIdentifier);
            }
        }
    }

    /**
     * Adds a view identifier as history entry. <em>Caution: </em> While the
     * method {@link #addCurrentUriToHistory(ViewDescriptor)} explicitly
     * checks against {@link HistoryConfiguration#getExcludeFromHistoryMatcher()}
     * this method assumes this is already done, therefore the caller must ensure this.
     *
     * @param viewIdentifier must not be null
     */
    private void handleAddCurrentView(final ViewIdentifier viewIdentifier) {
        log.trace("handleAddCurrentView(id = {}, viewIdentifier = {})", String.valueOf(System.identityHashCode(this)),
                viewIdentifier);
        var oldCurrentView = getCurrentView();
        // Ensure that reloading the page will not duplicate history entries
        if (null != oldCurrentView && !oldCurrentView.equals(viewIdentifier)) {
            ensureCapacity();
            getHistory().add(oldCurrentView);
        }
        currentView = viewIdentifier;
    }

    @Override
    public ViewIdentifier popPrevious() {
        var previous = getFallbackIdentifier();
        if (areTwoEntriesInHistoryAvailable()) {
            previous = getHistory().remove(getHistory().size() - 1);
        }
        return previous;
    }

    @Override
    public ViewIdentifier peekPrevious() {
        var previous = getFallbackIdentifier();
        if (areTwoEntriesInHistoryAvailable()) {
            previous = getHistory().get(getHistory().size() - 1);
        }
        return previous;
    }

    @Override
    public Iterator<ViewIdentifier> iterator() {
        return getHistory().iterator();
    }

    /**
     * @return true if 2 entries are available, false otherwise
     */
    private boolean areTwoEntriesInHistoryAvailable() {
        return getHistory().size() > 1;
    }

    /**
     * verify if history has enough space, if not remove the first entry that is
     * not the fallback
     */
    private void ensureCapacity() {
        if (getHistory().size() > historySize) {
            // forget first that is not the fallback
            getHistory().remove(1);
        }
    }

    /**
     * Calculate the fallback identifier (e.g. "home"). Take care of conditional navigation!
     *
     * @return the fallback identifier for the given state of the application.
     */
    private ViewIdentifier getFallbackIdentifier() {
        var fallback = historyConfiguration.getFallback();
        final var fallbackOutcome = historyConfiguration.getFallbackOutcome();
        if (isEmpty(fallback) && isEmpty(fallbackOutcome)) {
            throw new IllegalStateException("Neither fallback url nor fallbackOutcome configured");
        }

        var fallbackIdentifier = new ViewIdentifier(fallback, fallbackOutcome,
                Collections.emptyList());
        if (null == emptyToNull(fallback) && null != emptyToNull(fallbackOutcome)) {
            fallbackIdentifier =
                NavigationUtils.lookUpToViewIdentifierBy(FacesContext.getCurrentInstance(), fallbackOutcome);
            log.debug("fallback was calculated to : ['{}']", fallbackIdentifier);
        }
        return fallbackIdentifier;
    }

    private List<ViewIdentifier> getHistory() {
        if (null == history) {
            history = new CopyOnWriteArrayList<>();
            history.add(getFallbackIdentifier());
        }
        return history;
    }

}
