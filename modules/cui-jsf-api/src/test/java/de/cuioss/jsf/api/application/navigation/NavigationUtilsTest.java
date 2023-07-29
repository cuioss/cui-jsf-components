package de.cuioss.jsf.api.application.navigation;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import org.apache.myfaces.test.mock.MockHttpServletResponse;
import org.apache.myfaces.test.mock.MockNavigationHandler;
import org.junit.jupiter.api.Test;

import de.cuioss.test.jsf.config.ApplicationConfigurator;
import de.cuioss.test.jsf.config.RequestConfigurator;
import de.cuioss.test.jsf.config.decorator.ApplicationConfigDecorator;
import de.cuioss.test.jsf.config.decorator.RequestConfigDecorator;
import de.cuioss.test.jsf.junit5.JsfEnabledTestEnvironment;
import de.cuioss.tools.net.UrlParameter;

class NavigationUtilsTest extends JsfEnabledTestEnvironment implements ApplicationConfigurator, RequestConfigurator {

    private static final String CUIOSS_DE = "http://cuioss.de/index.html";

    private static final String PARAM_VALUE = "param-value";

    private static final String PARAM_NAME = "param-name";

    private static final String SOMEWHERE_XHTML = "/somewhere.xhtml";

    private static final String SOMEWHERE_JSF = "/somewhere.jsf";

    private static final String CONTEXT_PATH = "contextPath";

    private final List<UrlParameter> parameters = immutableList(new UrlParameter(PARAM_NAME, PARAM_VALUE));

    @Test
    void testErrorHandlingOnMissingUrl() {
        assertThrows(IllegalArgumentException.class,
                () -> NavigationUtils.sendRedirect(getFacesContext(), null, false));
    }

    @Test
    void testErrorHandlingOnEmptyUrl() {
        assertThrows(IllegalArgumentException.class, () -> NavigationUtils.sendRedirect(getFacesContext(), "", false));
    }

    @Test
    void testErrorHandlingOnMissingFacesContext() {
        assertThrows(NullPointerException.class, () -> NavigationUtils.sendRedirect(null, "somewhere", false));
    }

    private void verifyRedirect(final String expected) {
        assertEquals(expected,
                ((MockHttpServletResponse) getFacesContext().getExternalContext().getResponse()).getMessage(),
                "RedirectedUrl is wrong. ");
    }

    @Test
    void testSendRedirect() {
        NavigationUtils.sendRedirect(getFacesContext(), SOMEWHERE_JSF, false);
        verifyRedirect(CONTEXT_PATH + SOMEWHERE_JSF);
    }

    @Test
    void testSendRedirectWithUrl() throws MalformedURLException {
        final var url = new URL(CUIOSS_DE);
        NavigationUtils.executeRedirect(url);
        verifyRedirect("/index.html");
    }

    @Test
    void testSendRedirectWithUrlParameter() {
        NavigationUtils.sendRedirectParameterList(getFacesContext(), SOMEWHERE_JSF, parameters);
        verifyRedirect(CONTEXT_PATH + SOMEWHERE_JSF + UrlParameter.createParameterString(parameters.get(0)));
    }

    @Test
    void shouldSendRedirectOutcomeParameterList() {
        NavigationUtils.sendRedirectOutcomeParameterList(getFacesContext(), OUTCOME_NAVIGATED, parameters);
        verifyRedirect(CONTEXT_PATH + VIEW_NAVIGATED + UrlParameter.createParameterString(parameters.get(0)));
    }

    @Test
    void testSendRedirectWithEmptyUrlParameter() {
        NavigationUtils.sendRedirectParameterList(getFacesContext(), SOMEWHERE_JSF, Collections.emptyList());
        verifyRedirect(CONTEXT_PATH + SOMEWHERE_JSF);
        assertTrue(getFacesContext().getResponseComplete(),
                "After redirect a Signal to FacesContext must be set, that request processing lifecycle should be terminated.");
    }

    @Test
    void testSendRedirectOnAjax() {
        getFacesContext().getPartialViewContext().setPartialRequest(true);
        NavigationUtils.sendRedirect(getFacesContext(), SOMEWHERE_JSF, false);
        verifyRedirect(CONTEXT_PATH + SOMEWHERE_JSF);
    }

    @Test
    void testSendNoRedirect() {
        final var urlOne = "/somewhere";
        final var urlTwo = "/somewhereelse";
        NavigationUtils.sendRedirect(getFacesContext(), urlOne, false);
        verifyRedirect(CONTEXT_PATH + urlOne);
        // make sure no redirect
        // final MockHttpServletResponse mochResp = (MockHttpServletResponse)
        // getFacesContext()
        // .getExternalContext().getResponse();
        // mochResp.com(true);
        NavigationUtils.sendRedirect(getFacesContext(), urlTwo, false);
        verifyRedirect(CONTEXT_PATH + urlOne);
    }

    @Test
    void shouldCreateViewDescriptor() {
        // First fallback with nothing set
        var descriptor = NavigationUtils.getCurrentView(getFacesContext());
        assertNotNull(descriptor);
        assertEquals(VIEW_HOME, descriptor.getViewId());
        assertEquals(VIEW_HOME, descriptor.getLogicalViewId());
        assertTrue(descriptor.getUrlParameter().isEmpty());
        getFacesContext().getViewRoot().setViewId(SOMEWHERE_XHTML);
        getFacesContext().getViewRoot().setTransient(true);
        getRequestConfigDecorator()
                .setQueryString(UrlParameter.createParameterString(new UrlParameter(PARAM_NAME, PARAM_VALUE)));
        descriptor = NavigationUtils.getCurrentView(getFacesContext());
        assertEquals(SOMEWHERE_XHTML, descriptor.getViewId());
        assertEquals(SOMEWHERE_JSF, descriptor.getLogicalViewId());
        assertFalse(descriptor.getUrlParameter().isEmpty());
        assertEquals(PARAM_NAME, descriptor.getUrlParameter().get(0).getName());
        assertEquals(PARAM_VALUE, descriptor.getUrlParameter().get(0).getValue());
    }

    @Test
    void shouldCreateViewDescriptorWithMultipleParameters() {
        // First fallback with nothing set
        var descriptor = NavigationUtils.getCurrentView(getFacesContext());
        assertNotNull(descriptor);
        assertEquals(VIEW_HOME, descriptor.getViewId());
        assertEquals(VIEW_HOME, descriptor.getLogicalViewId());
        assertTrue(descriptor.getUrlParameter().isEmpty());
        getFacesContext().getViewRoot().setViewId(SOMEWHERE_XHTML);
        getFacesContext().getViewRoot().setTransient(true);
        getRequestConfigDecorator().setQueryString(UrlParameter.createParameterString(
                new UrlParameter(PARAM_NAME, PARAM_VALUE), new UrlParameter("param-name1", ""),
                new UrlParameter("param-name2", null),
                new UrlParameter("param-name-%C3%A4%C3%B6%C3%BC", "value%20%C3%A4%C3%B6%C3%BC-*%2F%3F%2F%3D", false)));
        descriptor = NavigationUtils.getCurrentView(getFacesContext());
        assertEquals(SOMEWHERE_XHTML, descriptor.getViewId());
        assertEquals(SOMEWHERE_JSF, descriptor.getLogicalViewId());
        assertFalse(descriptor.getUrlParameter().isEmpty());
        assertEquals(PARAM_NAME, descriptor.getUrlParameter().get(0).getName());
        assertEquals(PARAM_VALUE, descriptor.getUrlParameter().get(0).getValue());
        assertEquals("param-name1", descriptor.getUrlParameter().get(1).getName());
        assertNull(descriptor.getUrlParameter().get(1).getValue());
        assertEquals("param-name2", descriptor.getUrlParameter().get(2).getName());
        assertNull(descriptor.getUrlParameter().get(2).getValue());
        assertEquals("param-name-äöü", descriptor.getUrlParameter().get(3).getName());
        assertEquals("value äöü-*/?/=", descriptor.getUrlParameter().get(3).getValue());
    }

    @Test
    void shouldLookupToViewId() {
        assertEquals(VIEW_HOME, NavigationUtils.lookUpToViewIdBy(getFacesContext(), OUTCOME_HOME));
    }

    @Test
    void shouldFailToLookupToViewId() {
        assertThrows(IllegalStateException.class,
                () -> NavigationUtils.lookUpToViewIdBy(getFacesContext(), CONTEXT_PATH));
    }

    /**
     * In case of an invalid configuration: No ConfigurableNavigationHandler
     * present.
     */
    @Test
    void shouldFailToLookupToViewIdWrongHandler() {
        getApplication().setNavigationHandler(new MockNavigationHandler());
        assertThrows(IllegalStateException.class,
                () -> NavigationUtils.lookUpToViewIdBy(getFacesContext(), OUTCOME_HOME));
    }

    @Test
    void shouldFailToExtractRequestUri() {
        assertNull(NavigationUtils.extractRequestUri("No servlet request"));
    }

    /**
     * The home navigation view
     */
    private static final String VIEW_HOME = "/faces/portal/home.jsf";

    private static final String OUTCOME_HOME = "home";

    /**
     * The home navigation view
     */
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
}
