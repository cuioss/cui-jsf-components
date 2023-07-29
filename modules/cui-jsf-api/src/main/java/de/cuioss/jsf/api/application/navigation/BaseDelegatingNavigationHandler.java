package de.cuioss.jsf.api.application.navigation;

import java.util.Map;
import java.util.Set;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.NavigationCase;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;

import de.cuioss.tools.collect.CollectionLiterals;
import lombok.AccessLevel;
import lombok.Getter;

/**
 * Base class simplifying the creation of our own {@link NavigationHandler}. In
 * essence it does the delegating stuff to the wrapped navigationHandler and let
 * the implementor focus on the actual logic needed.
 *
 * @author Oliver Wolff
 */
public class BaseDelegatingNavigationHandler extends ConfigurableNavigationHandler {

    @Getter(value = AccessLevel.PROTECTED)
    private final NavigationHandler wrapped;

    @Getter(value = AccessLevel.PROTECTED)
    private final ConfigurableNavigationHandler configurableNavigationHandler;

    /**
     * Flag indicating whether the wrapped handler is of type
     * {@link ConfigurableNavigationHandler}
     */
    @Getter(value = AccessLevel.PROTECTED)
    private final boolean wrappedConfigurableNavigationHandler;

    /**
     * @param wrapped may be {@link NavigationHandler} or
     *                {@link ConfigurableNavigationHandler}
     */
    public BaseDelegatingNavigationHandler(final NavigationHandler wrapped) {
        if (wrapped instanceof ConfigurableNavigationHandler) {
            configurableNavigationHandler = (ConfigurableNavigationHandler) wrapped;
            this.wrapped = wrapped;
            wrappedConfigurableNavigationHandler = true;
        } else {
            this.wrapped = wrapped;
            configurableNavigationHandler = null;
            wrappedConfigurableNavigationHandler = false;
        }
    }

    @Override
    public NavigationCase getNavigationCase(final FacesContext context, final String fromAction, final String outcome) {
        if (null == configurableNavigationHandler) {
            return null;
        }
        return configurableNavigationHandler.getNavigationCase(context, fromAction, outcome);
    }

    @Override
    public Map<String, Set<NavigationCase>> getNavigationCases() {
        if (null == configurableNavigationHandler) {
            return CollectionLiterals.immutableMap();
        }
        return configurableNavigationHandler.getNavigationCases();
    }

    @Override
    public void handleNavigation(final FacesContext context, final String fromAction, final String outcome) {
        wrapped.handleNavigation(context, fromAction, outcome);
    }
}
