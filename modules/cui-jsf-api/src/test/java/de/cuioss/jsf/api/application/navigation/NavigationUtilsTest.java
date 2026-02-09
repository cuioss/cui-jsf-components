/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.api.application.navigation;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;
import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.jsf.api.common.logging.JsfApiLogMessages;
import de.cuioss.test.jsf.config.decorator.ApplicationConfigDecorator;
import de.cuioss.test.jsf.config.decorator.RequestConfigDecorator;
import de.cuioss.test.jsf.junit5.EnableJsfEnvironment;
import de.cuioss.test.juli.LogAsserts;
import de.cuioss.test.juli.TestLogLevel;
import de.cuioss.test.juli.junit5.EnableTestLogger;
import de.cuioss.tools.net.UrlParameter;
import jakarta.faces.application.Application;
import jakarta.faces.context.FacesContext;
import org.apache.myfaces.test.mock.MockHttpServletResponse;
import org.apache.myfaces.test.mock.MockNavigationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@EnableJsfEnvironment
@EnableTestLogger
@DisplayName("Tests for NavigationUtils")
class NavigationUtilsTest {

    private static final String CUIOSS_DE = "http://cuioss.de/index.html";

    private static final String PARAM_VALUE = "param-value";

    private static final String PARAM_NAME = "param-name";

    private static final String SOMEWHERE_XHTML = "/somewhere.xhtml";

    private static final String SOMEWHERE_JSF = "/somewhere.jsf";

    private static final String CONTEXT_PATH = "contextPath";

    /**
     * The home navigation view
     */
    private static final String VIEW_HOME = "/portal/home.jsf";

    private static final String OUTCOME_HOME = "home";

    /**
     * The home navigation view
     */
    private static final String VIEW_NAVIGATED = "/portal/navigated.jsf";

    private static final String OUTCOME_NAVIGATED = "navigate";

    private final List<UrlParameter> parameters = immutableList(new UrlParameter(PARAM_NAME, PARAM_VALUE));

    @Nested
    @DisplayName("Tests for error handling")
    class ErrorHandlingTests {

        @Test
        @DisplayName("Should throw exception on missing URL")
        void shouldThrowExceptionOnMissingUrl(FacesContext facesContext) {
            // Act & Assert
            assertThrows(IllegalArgumentException.class,
                    () -> NavigationUtils.sendRedirect(facesContext, null, false),
                    "Should throw IllegalArgumentException for null URL");
        }

        @Test
        @DisplayName("Should throw exception on empty URL")
        void shouldThrowExceptionOnEmptyUrl(FacesContext facesContext) {
            // Act & Assert
            assertThrows(IllegalArgumentException.class,
                    () -> NavigationUtils.sendRedirect(facesContext, "", false),
                    "Should throw IllegalArgumentException for empty URL");
        }

        @Test
        @DisplayName("Should throw exception on missing FacesContext")
        void shouldThrowExceptionOnMissingFacesContext() {
            // Act & Assert
            assertThrows(NullPointerException.class,
                    () -> NavigationUtils.sendRedirect(null, "somewhere", false),
                    "Should throw NullPointerException for null FacesContext");
        }

        @Test
        @DisplayName("Should fail to lookup view ID with invalid outcome")
        void shouldFailToLookupViewId(FacesContext facesContext) {
            // Act & Assert
            assertThrows(IllegalStateException.class,
                    () -> NavigationUtils.lookUpToViewIdBy(facesContext, CONTEXT_PATH),
                    "Should throw IllegalStateException for invalid outcome");
        }

        @Test
        @DisplayName("Should fail to lookup view ID with wrong navigation handler")
        void shouldFailToLookupViewIdWithWrongHandler(FacesContext facesContext, Application application) {
            // Arrange
            application.setNavigationHandler(new MockNavigationHandler());

            // Act & Assert
            assertThrows(IllegalStateException.class,
                    () -> NavigationUtils.lookUpToViewIdBy(facesContext, OUTCOME_HOME),
                    "Should throw IllegalStateException when ConfigurableNavigationHandler is not present");
        }

        @Test
        @DisplayName("Should return null when extracting request URI from non-servlet request")
        void shouldReturnNullWhenExtractingRequestUriFromNonServletRequest() {
            // Act & Assert
            assertNull(NavigationUtils.extractRequestUri("No servlet request"),
                    "Should return null for non-servlet request");
            LogAsserts.assertSingleLogMessagePresentContaining(TestLogLevel.WARN,
                    JsfApiLogMessages.WARN.UNEXPECTED_ENVIRONMENT.resolveIdentifierString());
        }
    }

    private void verifyRedirect(final String expected, FacesContext facesContext) {
        assertEquals(expected,
                ((MockHttpServletResponse) facesContext.getExternalContext().getResponse()).getMessage(),
                "RedirectedUrl is wrong. ");
    }

    @Nested
    @DisplayName("Tests for redirect functionality")
    class RedirectTests {

        @Test
        @DisplayName("Should send redirect to specified URL")
        void shouldSendRedirect(FacesContext facesContext) {
            // Act
            NavigationUtils.sendRedirect(facesContext, SOMEWHERE_JSF, false);

            // Assert
            verifyRedirect(CONTEXT_PATH + SOMEWHERE_JSF, facesContext);
        }

        @Test
        @DisplayName("Should send redirect with URL object")
        void shouldSendRedirectWithUrl(FacesContext facesContext) throws Exception {
            // Arrange
            final var url = URI.create(CUIOSS_DE).toURL();

            // Act
            NavigationUtils.executeRedirect(url);

            // Assert
            verifyRedirect("/index.html", facesContext);
        }

        @Test
        @DisplayName("Should send redirect with URL parameters")
        void shouldSendRedirectWithUrlParameters(FacesContext facesContext) {
            // Act
            NavigationUtils.sendRedirectParameterList(facesContext, SOMEWHERE_JSF, parameters);

            // Assert
            verifyRedirect(CONTEXT_PATH + SOMEWHERE_JSF + UrlParameter.createParameterString(parameters.getFirst()),
                    facesContext);
        }

        @Test
        @DisplayName("Should send redirect with outcome and URL parameters")
        void shouldSendRedirectWithOutcomeAndParameters(FacesContext facesContext) {
            // Act
            NavigationUtils.sendRedirectOutcomeParameterList(facesContext, OUTCOME_NAVIGATED, parameters);

            // Assert
            verifyRedirect(CONTEXT_PATH + VIEW_NAVIGATED + UrlParameter.createParameterString(parameters.getFirst()),
                    facesContext);
        }

        @Test
        @DisplayName("Should send redirect with empty URL parameters")
        void shouldSendRedirectWithEmptyUrlParameters(FacesContext facesContext) {
            // Act
            NavigationUtils.sendRedirectParameterList(facesContext, SOMEWHERE_JSF, Collections.emptyList());

            // Assert
            verifyRedirect(CONTEXT_PATH + SOMEWHERE_JSF, facesContext);
            assertTrue(facesContext.getResponseComplete(),
                    "After redirect a Signal to FacesContext must be set, that request processing lifecycle should be terminated.");
        }

        @Test
        @DisplayName("Should send redirect on AJAX request")
        void shouldSendRedirectOnAjaxRequest(FacesContext facesContext) {
            // Arrange
            facesContext.getPartialViewContext().setPartialRequest(true);

            // Act
            NavigationUtils.sendRedirect(facesContext, SOMEWHERE_JSF, false);

            // Assert
            verifyRedirect(CONTEXT_PATH + SOMEWHERE_JSF, facesContext);
        }

        @Test
        @DisplayName("Should not send second redirect after first one")
        void shouldNotSendSecondRedirect(FacesContext facesContext) {
            // Arrange
            final var urlOne = "/somewhere";
            final var urlTwo = "/somewhereelse";

            // Act - first redirect
            NavigationUtils.sendRedirect(facesContext, urlOne, false);

            // Assert - first redirect successful
            verifyRedirect(CONTEXT_PATH + urlOne, facesContext);

            // Act - attempt second redirect
            NavigationUtils.sendRedirect(facesContext, urlTwo, false);

            // Assert - still shows first redirect and warning was logged
            verifyRedirect(CONTEXT_PATH + urlOne, facesContext);
            LogAsserts.assertSingleLogMessagePresentContaining(TestLogLevel.WARN,
                    JsfApiLogMessages.WARN.RESPONSE_ALREADY_COMMITTED.resolveIdentifierString());
        }
    }

    @Nested
    @DisplayName("Tests for view descriptor functionality")
    class ViewDescriptorTests {

        @Test
        @DisplayName("Should create view descriptor with default values")
        void shouldCreateViewDescriptorWithDefaults(FacesContext facesContext) {
            // Act
            var descriptor = NavigationUtils.getCurrentView(facesContext);

            // Assert
            assertNotNull(descriptor, "View descriptor should not be null");
            assertEquals(VIEW_HOME, descriptor.getViewId(), "View ID should match home view");
            assertEquals(VIEW_HOME, descriptor.getLogicalViewId(), "Logical view ID should match home view");
            assertTrue(descriptor.getUrlParameter().isEmpty(), "URL parameters should be empty");
        }

        @Test
        @DisplayName("Should create view descriptor with view ID and parameters")
        void shouldCreateViewDescriptorWithViewIdAndParameters(FacesContext facesContext,
                RequestConfigDecorator requestConfigDecorator) {
            // Arrange
            facesContext.getViewRoot().setViewId(SOMEWHERE_XHTML);
            facesContext.getViewRoot().setTransient(true);
            requestConfigDecorator
                    .setQueryString(UrlParameter.createParameterString(new UrlParameter(PARAM_NAME, PARAM_VALUE)));

            // Act
            var descriptor = NavigationUtils.getCurrentView(facesContext);

            // Assert
            assertEquals(SOMEWHERE_XHTML, descriptor.getViewId(), "View ID should match set view");
            assertEquals(SOMEWHERE_JSF, descriptor.getLogicalViewId(), "Logical view ID should be converted to JSF");
            assertFalse(descriptor.getUrlParameter().isEmpty(), "URL parameters should not be empty");
            assertEquals(PARAM_NAME, descriptor.getUrlParameter().getFirst().getName(),
                    "Parameter name should match");
            assertEquals(PARAM_VALUE, descriptor.getUrlParameter().getFirst().getValue(),
                    "Parameter value should match");
        }

        @Test
        @DisplayName("Should create view descriptor with multiple parameters")
        void shouldCreateViewDescriptorWithMultipleParameters(FacesContext facesContext,
                RequestConfigDecorator requestConfigDecorator) {
            // Arrange
            facesContext.getViewRoot().setViewId(SOMEWHERE_XHTML);
            facesContext.getViewRoot().setTransient(true);
            requestConfigDecorator.setQueryString(UrlParameter.createParameterString(
                    new UrlParameter(PARAM_NAME, PARAM_VALUE), new UrlParameter("param-name1", ""),
                    new UrlParameter("param-name2", null),
                    new UrlParameter("param-name-%C3%A4%C3%B6%C3%BC", "value%20%C3%A4%C3%B6%C3%BC-*%2F%3F%2F%3D", false)));

            // Act
            var descriptor = NavigationUtils.getCurrentView(facesContext);

            // Assert
            assertEquals(SOMEWHERE_XHTML, descriptor.getViewId(), "View ID should match set view");
            assertEquals(SOMEWHERE_JSF, descriptor.getLogicalViewId(), "Logical view ID should be converted to JSF");
            assertFalse(descriptor.getUrlParameter().isEmpty(), "URL parameters should not be empty");

            // Verify first parameter
            assertEquals(PARAM_NAME, descriptor.getUrlParameter().getFirst().getName(),
                    "First parameter name should match");
            assertEquals(PARAM_VALUE, descriptor.getUrlParameter().getFirst().getValue(),
                    "First parameter value should match");

            // Verify second parameter
            assertEquals("param-name1", descriptor.getUrlParameter().get(1).getName(),
                    "Second parameter name should match");
            assertNull(descriptor.getUrlParameter().get(1).getValue(),
                    "Second parameter value should be null");

            // Verify third parameter
            assertEquals("param-name2", descriptor.getUrlParameter().get(2).getName(),
                    "Third parameter name should match");
            assertNull(descriptor.getUrlParameter().get(2).getValue(),
                    "Third parameter value should be null");

            // Verify fourth parameter
            assertEquals("param-name-äöü", descriptor.getUrlParameter().get(3).getName(),
                    "Fourth parameter name should be properly decoded");
            assertEquals("value äöü-*/?/=", descriptor.getUrlParameter().get(3).getValue(),
                    "Fourth parameter value should be properly decoded");
        }

        @Test
        @DisplayName("Should lookup view ID by outcome")
        void shouldLookupViewIdByOutcome(FacesContext facesContext) {
            // Act & Assert
            assertEquals(VIEW_HOME, NavigationUtils.lookUpToViewIdBy(facesContext, OUTCOME_HOME),
                    "Should return correct view ID for outcome");
        }
    }

    // These tests have been moved to the ErrorHandlingTests nested class

    @BeforeEach
    void setUp(ApplicationConfigDecorator applicationConfig, RequestConfigDecorator requestConfig) {
        applicationConfig.registerNavigationCase(OUTCOME_HOME, VIEW_HOME)
                .registerNavigationCase(OUTCOME_NAVIGATED, VIEW_NAVIGATED).setContextPath(CONTEXT_PATH);
        requestConfig.setViewId(VIEW_HOME);
    }
}
