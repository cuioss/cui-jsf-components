package de.cuioss.jsf.api.application.view.matcher;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.application.navigation.NavigationUtils;
import de.cuioss.jsf.api.common.view.ViewDescriptor;
import de.cuioss.jsf.api.common.view.ViewDescriptorImpl;
import de.cuioss.test.jsf.config.ApplicationConfigurator;
import de.cuioss.test.jsf.config.RequestConfigurator;
import de.cuioss.test.jsf.config.decorator.ApplicationConfigDecorator;
import de.cuioss.test.jsf.config.decorator.RequestConfigDecorator;
import de.cuioss.test.jsf.junit5.JsfEnabledTestEnvironment;

class OutcomeBasedViewMatcherTest extends JsfEnabledTestEnvironment
        implements ApplicationConfigurator, RequestConfigurator {

    private static final String CONTEXT_PATH = "contextPath";

    /** The home navigation view */
    private static final String VIEW_HOME = "/faces/portal/home.jsf";

    private static final String OUTCOME_HOME = "home";

    /** The home navigation view */
    private static final String VIEW_NAVIGATED = "/faces/portal/navigated.jsf";

    private static final String OUTCOME_NAVIGATED = "navigate";

    @Override
    public void configureApplication(final ApplicationConfigDecorator decorator) {
        decorator.registerNavigationCase(OUTCOME_HOME, VIEW_HOME)
                .registerNavigationCase(OUTCOME_NAVIGATED, VIEW_NAVIGATED).setContextPath(CONTEXT_PATH);
    }

    @Override
    public void configureRequest(final RequestConfigDecorator decorator) {
        decorator.setViewId(VIEW_HOME);
    }

    @Test
    void shouldHandleRegisteredView() {
        var matcher = new OutcomeBasedViewMatcher(OUTCOME_HOME);
        var current = NavigationUtils.getCurrentView(getFacesContext());
        assertTrue(matcher.match(current));
        assertTrue(matcher.match(current));
        ViewDescriptor navigate = ViewDescriptorImpl.builder().withLogicalViewId(VIEW_NAVIGATED).build();
        assertFalse(matcher.match(navigate));
    }
}
