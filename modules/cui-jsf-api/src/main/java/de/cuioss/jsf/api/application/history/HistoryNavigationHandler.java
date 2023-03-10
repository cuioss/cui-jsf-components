package de.cuioss.jsf.api.application.history;

import javax.faces.application.NavigationCase;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;

import de.cuioss.jsf.api.application.history.accessor.HistoryManagerAccessor;
import de.cuioss.jsf.api.application.navigation.BaseDelegatingNavigationHandler;

/**
 * @author Oliver Wolff
 */
public class HistoryNavigationHandler extends BaseDelegatingNavigationHandler {

    private static final String BACK = "back";

    /** Access the history bean. */
    private final HistoryManagerAccessor historyAccessor = new HistoryManagerAccessor();

    /**
     * @param wrapped
     */
    public HistoryNavigationHandler(final NavigationHandler wrapped) {
        super(wrapped);
    }

    @Override
    public void handleNavigation(final FacesContext context, final String from, final String outcome) {
        if (BACK.equals(outcome)) {
            historyAccessor.getValue().popPrevious().redirect(context);
        } else {
            super.handleNavigation(context, from, outcome);
        }
    }

    @Override
    public NavigationCase getNavigationCase(final FacesContext context, final String fromAction, final String outcome) {
        if (BACK.equals(outcome)) {
            return historyAccessor.getValue().peekPrevious().toBackNavigationCase();
        }
        return super.getNavigationCase(context, fromAction, outcome);
    }
}
