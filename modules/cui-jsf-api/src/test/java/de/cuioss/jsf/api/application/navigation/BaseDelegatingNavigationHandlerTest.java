package de.cuioss.jsf.api.application.navigation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.myfaces.test.mock.MockHttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.cuioss.test.jsf.junit5.JsfEnabledTestEnvironment;
import de.cuioss.test.jsf.mocks.CuiMockConfigurableNavigationHandler;

class BaseDelegatingNavigationHandlerTest extends JsfEnabledTestEnvironment {

    private static final String SOME = "some";

    private CuiMockConfigurableNavigationHandler mockNavigationHandler;

    @BeforeEach
    void before() {
        mockNavigationHandler = getApplicationConfigDecorator().getMockNavigationHandler();
    }

    @Test
    void shouldDelegateToNavigationHandler() {
        var underTest = new BaseDelegatingNavigationHandler(mockNavigationHandler);
        assertNull(underTest.getNavigationCase(getFacesContext(), null, null));
        assertNull(underTest.getNavigationCase(getFacesContext(), null, null, null));
        underTest.getNavigationCases();

        underTest.handleNavigation(getFacesContext(), null, null);
        assertTrue(mockNavigationHandler.isHandleNavigationCalled());

        getEnvironmentHolder().getExternalContext().setResponse(new MockHttpServletResponse());

        underTest.handleNavigation(getFacesContext(), null, null, null);
        assertTrue(mockNavigationHandler.isHandleNavigationCalled());
    }

    @Test
    void shouldDelegateToConfigurableNavigationHandler() {
        var underTest = new BaseDelegatingNavigationHandler(mockNavigationHandler);
        underTest.getNavigationCase(getFacesContext(), null, null);
        underTest.getNavigationCase(getFacesContext(), null, null, null);
        underTest.getNavigationCases();

        underTest.handleNavigation(getFacesContext(), SOME, SOME);
        assertTrue(mockNavigationHandler.isHandleNavigationCalled());
        assertFalse(mockNavigationHandler.isAddNavigationCalled());
        assertTrue(mockNavigationHandler.isGetNavigationCaseCalled());

        getEnvironmentHolder().getExternalContext().setResponse(new MockHttpServletResponse());

        underTest.handleNavigation(getFacesContext(), SOME, SOME, SOME);
        assertTrue(mockNavigationHandler.isHandleNavigationCalled());
        assertFalse(mockNavigationHandler.isAddNavigationCalled());
        assertTrue(mockNavigationHandler.isGetNavigationCaseCalled());
    }

    @Test
    void shouldGracefullyHandleNullAsHandler() {
        var underTest = new BaseDelegatingNavigationHandler(null);
        assertNull(underTest.getNavigationCase(getFacesContext(), SOME, SOME));
        assertTrue(underTest.getNavigationCases().isEmpty());
    }
}
