package de.cuioss.jsf.api.application.navigation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.common.view.ViewDescriptor;
import de.cuioss.jsf.api.common.view.ViewDescriptorImpl;
import de.cuioss.test.jsf.junit5.JsfEnabledTestEnvironment;
import de.cuioss.test.jsf.mocks.CuiMockConfigurableNavigationHandler;

class ViewIdentifierTest extends JsfEnabledTestEnvironment {

    private static final String FROM_OUTCOME = "fromOutcome";

    private static final String FROM_ACTION = "fromAction";

    private static final String FROM_VIEW_ID = "fromViewId";

    private static final String START_URL = "/faces/some/page.jsf";

    private CuiMockConfigurableNavigationHandler mockNavigationHandler;

    @BeforeEach
    void before() {
        mockNavigationHandler = getApplicationConfigDecorator().getMockNavigationHandler();
    }

    @Test
    void shouldExtractCorrectViewIdentifier() {
        ViewDescriptor descriptor =
            ViewDescriptorImpl.builder().withViewId(START_URL).withLogicalViewId(START_URL).build();
        var identifier = ViewIdentifier.getFromViewDesciptor(descriptor, null);
        assertNotNull(identifier);
        assertEquals(START_URL, identifier.getViewId());
        assertNull(identifier.getOutcome());
        final var navigationCase =
            identifier.toNavigationCase(FROM_VIEW_ID, FROM_ACTION, FROM_OUTCOME, null, true, false);
        assertNotNull(navigationCase);
    }

    @Test
    void handleNullUrlParams() {
        new ViewIdentifier("some/page.jsf", null, null)
            .redirect(getFacesContext());
    }
}
