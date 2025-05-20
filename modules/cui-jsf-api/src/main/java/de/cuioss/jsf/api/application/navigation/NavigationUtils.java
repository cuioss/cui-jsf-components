/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.api.application.navigation;

import static de.cuioss.jsf.api.common.logging.JsfApiLogMessages.WARN;
import static de.cuioss.jsf.api.servlet.ServletAdapterUtil.getRequest;
import static de.cuioss.jsf.api.servlet.ServletAdapterUtil.getResponse;
import static de.cuioss.tools.net.UrlParameter.createParameterString;
import static de.cuioss.tools.string.MoreStrings.nullToEmpty;
import static de.cuioss.tools.string.MoreStrings.requireNotEmpty;
import static java.util.Objects.requireNonNull;

import de.cuioss.jsf.api.common.view.ViewDescriptor;
import de.cuioss.jsf.api.common.view.ViewDescriptorImpl;
import de.cuioss.jsf.api.servlet.ServletAdapterUtil;
import de.cuioss.tools.logging.CuiLogger;
import de.cuioss.tools.net.UrlParameter;
import jakarta.faces.application.ConfigurableNavigationHandler;
import jakarta.faces.application.NavigationCase;
import jakarta.faces.component.UIViewRoot;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpServletRequest;
import org.omnifaces.util.Servlets;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.net.URL;
import java.util.Collections;
import java.util.List;

/**
 * Utility class that provides navigation functionality for JSF applications.
 * <p>
 * This class offers methods for:
 * <ul>
 *   <li>Looking up view IDs from navigation outcomes</li>
 *   <li>Performing redirects to different views</li>
 *   <li>Extracting current view information</li>
 *   <li>Handling view ID suffixes and URL parameters</li>
 * </ul>
 * 
 * <p>
 * <strong>Important:</strong> Use {@code NavigationUtils} carefully and only when 
 * Faces Request needs to be redirected to a <strong>different</strong> web application
 * resource! For navigation within the web application space, use standard Navigation
 * Rules instead.
 * </p>
 * 
 * <p>
 * This class is thread-safe as it contains no mutable state and all methods are either
 * static or properly synchronized.
 * </p>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
public final class NavigationUtils implements Serializable {

    private static final CuiLogger log = new CuiLogger(NavigationUtils.class);

    /**
     * JSF file extension used for web-accessible views.
     */
    private static final String JSF = ".jsf";

    /**
     * XHTML file extension used for view templates.
     */
    private static final String XHTML = ".xhtml";

    @Serial
    private static final long serialVersionUID = -3658719779910485458L;

    private static final String UNSUPPORTED_CONFIGURATION = "Unsupported configuration for fallback navigation detected. Expected NavigationHandler is ConfigurableNavigationHandler, but was ['%s']";

    private static final String INCOMPLETE_CONFIG = "No NavigationCase defined for outcome ['%s']. Verify your faces configuration.";

    /**
     * Try to look up view id or view id expression for outcome by using
     * {@linkplain ConfigurableNavigationHandler}.
     * <p>
     * This method resolves the navigation outcome to a view ID based on the
     * navigation rules defined in the faces-config.xml.
     * </p>
     *
     * @param facesContext The current faces context, must not be null
     * @param outcome      The navigation case outcome, must not be null
     * @return The resolved view ID if it can be resolved
     * @throws IllegalStateException if no {@linkplain ConfigurableNavigationHandler} is
     *                               used in the application
     * @throws IllegalStateException if the {@linkplain NavigationCase} for outcome
     *                               is not available
     */
    public static String lookUpToViewIdBy(final FacesContext facesContext, final String outcome) {
        return findNavigationCaseForOutcome(facesContext, outcome).getToViewId(facesContext);
    }

    /**
     * Try to look up logical view id (=resulting url) of view id or view id
     * expression for outcome by using {@linkplain ConfigurableNavigationHandler}.
     * <p>
     * This method resolves the navigation outcome to a logical view ID (the resulting URL path)
     * based on the navigation rules defined in the faces-config.xml, and handles the
     * view ID suffix conversion (e.g., .xhtml to .jsf).
     * </p>
     *
     * @param facesContext The current faces context, must not be null
     * @param outcome      The navigation case outcome, must not be null
     * @return The logical view ID (resulting URL) of the view if it can be resolved
     * @throws IllegalStateException if no {@linkplain ConfigurableNavigationHandler} is
     *                               used in the application
     * @throws IllegalStateException if the {@linkplain NavigationCase} for outcome
     *                               is not available
     */
    public static String lookUpToLogicalViewIdBy(final FacesContext facesContext, final String outcome) {
        return handleViewIdSuffix(lookUpToViewIdBy(facesContext, outcome));
    }

    /**
     * Try to look up a ViewIdentifier of view id or view id expression for outcome
     * by using {@linkplain ConfigurableNavigationHandler}.
     * <p>
     * This method creates a {@link ViewIdentifier} based on the resolved navigation outcome,
     * which includes the logical view ID and the original outcome.
     * </p>
     *
     * @param facesContext The current faces context, must not be null
     * @param outcome      The navigation case outcome, must not be null
     * @return A {@link ViewIdentifier} of the view ID if it can be resolved
     * @throws IllegalStateException if no {@linkplain ConfigurableNavigationHandler} is
     *                               used in the application
     * @throws IllegalStateException if the {@linkplain NavigationCase} for outcome
     *                               is not available
     */
    public static ViewIdentifier lookUpToViewIdentifierBy(final FacesContext facesContext, final String outcome) {
        return new ViewIdentifier(lookUpToLogicalViewIdBy(facesContext, outcome), outcome, Collections.emptyList());
    }

    /**
     * Try to look up a ViewDescriptor of view id or view id expression for outcome
     * by using {@linkplain ConfigurableNavigationHandler}.
     * <p>
     * This method creates a {@link ViewDescriptor} based on the resolved navigation outcome,
     * which includes both the original view ID and the logical view ID.
     * </p>
     *
     * @param facesContext The current faces context, must not be null
     * @param outcome      The navigation case outcome, must not be null
     * @return A {@link ViewDescriptor} of the view ID if it can be resolved
     * @throws IllegalStateException if no {@linkplain ConfigurableNavigationHandler} is
     *                               used in the application
     * @throws IllegalStateException if the {@linkplain NavigationCase} for outcome
     *                               is not available
     */
    public static ViewDescriptor lookUpToViewDescriptorBy(final FacesContext facesContext, final String outcome) {
        final var viewId = lookUpToViewIdBy(facesContext, outcome);
        return ViewDescriptorImpl.builder().withLogicalViewId(handleViewIdSuffix(viewId)).withViewId(viewId).build();
    }

    /**
     * Finds a NavigationCase for the provided outcome using the ConfigurableNavigationHandler.
     * <p>
     * This is an internal helper method used to resolve navigation outcomes to
     * NavigationCase objects.
     * </p>
     *
     * @param facesContext The current faces context, must not be null
     * @param outcome      The navigation case outcome, must not be null
     * @return The {@link NavigationCase} if it can be resolved
     * @throws IllegalStateException if no {@linkplain ConfigurableNavigationHandler} is
     *                               used in the application
     * @throws IllegalStateException if the {@linkplain NavigationCase} for outcome
     *                               is not available
     */
    private static NavigationCase findNavigationCaseForOutcome(final FacesContext facesContext, final String outcome) {
        final var navigationHandler = facesContext.getApplication().getNavigationHandler();

        if (navigationHandler instanceof ConfigurableNavigationHandler con) {

            final var navigationCase = con.getNavigationCase(facesContext, null, outcome);

            if (null == navigationCase) {
                throw new IllegalStateException(INCOMPLETE_CONFIG.formatted(outcome));
            }
            return navigationCase;
        }
        throw new IllegalStateException(UNSUPPORTED_CONFIGURATION.formatted(navigationHandler.getClass().getName()));
    }

    /**
     * Sends a redirect to the specified URL with optional URL parameters.
     * <p>
     * This method constructs a complete URL from the context path, the provided URL,
     * and any URL parameters, then performs a redirect. It handles encoding of parameters
     * and checks if the response is already committed.
     * </p>
     *
     * @param facesContext The current faces context, must not be null
     * @param url          The context-relative URL where the response should be
     *                     redirected, must not be null or empty
     * @param encode       Indicates whether to encode the URL parameters
     * @param parameters   Optional {@link UrlParameter} instances to append to the URL
     * @throws IllegalArgumentException if url is null or empty
     */
    public static void sendRedirect(final FacesContext facesContext, final String url, final boolean encode,
            final UrlParameter... parameters) {

        requireNotEmpty(url, "url");

        final var response = getResponse(facesContext);
        if (!response.isCommitted()) {

            final var fullUrl = response.encodeRedirectURL(getRequest(facesContext).getContextPath()) +
                    handleViewIdSuffix(url) + createParameterString(encode, parameters);

            redirect(facesContext, fullUrl);
        } else if (log.isWarnEnabled()) {
            log.warn(WARN.RESPONSE_ALREADY_COMMITTED::format);
        }
    }

    /**
     * Executes a redirect to the specified URL.
     *
     * <p><em>Caution</em>: This method is only for context-relative redirects,
     * therefore only the referenced file will be resolved/redirected to,
     * not the complete {@link URL}.</p>
     *
     * @param facesContext The {@link FacesContext} to use for the redirect
     * @param fullUrl The complete URL to redirect to, must not be null
     * @throws IllegalStateException if the redirect operation fails
     */
    public static void redirect(final FacesContext facesContext, final String fullUrl) {
        try {
            // FlashScope needs to know, whether the next request is a redirect.
            facesContext.getExternalContext().getFlash().setRedirect(true);
            facesContext.getExternalContext().redirect(fullUrl);
            facesContext.responseComplete();
        } catch (final IOException e) {
            throw new IllegalStateException("Unable to redirect to " + fullUrl, e);
        }
    }

    /**
     * Shorthand for using sendRedirect with a List of parameters instead of varargs.
     * <p>
     * This method simplifies redirecting with a list of URL parameters, handling
     * the case where the parameter list is null or empty.
     * </p>
     *
     * @param facesContext  The current faces context, must not be null
     * @param url           The context-relative URL where the response should be
     *                      redirected, must not be null or empty
     * @param parameterList A list of URL parameters, may be null or empty
     */
    public static void sendRedirectParameterList(final FacesContext facesContext, final String url,
            final List<UrlParameter> parameterList) {
        if (parameterList != null && !parameterList.isEmpty()) {
            final var parameters = parameterList.toArray(new UrlParameter[0]);
            sendRedirect(facesContext, url, true, parameters);
        } else {
            sendRedirect(facesContext, url, false);
        }
    }

    /**
     * Shorthand for using sendRedirect with a navigation outcome and a list of parameters.
     * <p>
     * This method resolves the navigation outcome to a logical view ID and then
     * redirects to that URL with the provided parameters.
     * </p>
     *
     * @param facesContext The current faces context, must not be null
     * @param outcome      The navigation case outcome, must not be null
     * @param parameters   Optional list of {@link UrlParameter} instances
     * @throws IllegalStateException if no {@linkplain ConfigurableNavigationHandler} is
     *                               used in the application
     * @throws IllegalStateException if the {@linkplain NavigationCase} for outcome
     *                               is not available
     */
    public static void sendRedirectOutcomeParameterList(final FacesContext facesContext, final String outcome,
            final List<UrlParameter> parameters) {
        final var url = lookUpToLogicalViewIdBy(facesContext, outcome);
        sendRedirectParameterList(facesContext, url, parameters);
    }

    /**
     * Extracts the current view information from the FacesContext.
     * 
     * This method attempts to extract view information from:
     * <ol>
     *   <li>The ViewRoot of the FacesContext, if available</li>
     *   <li>The request URI, if the ViewRoot is not available</li>
     * </ol>
     * It also extracts any URL parameters from the request.
     *
     * @param facesContext The current {@link FacesContext}, must not be null
     * @return A {@link ViewDescriptor} containing information about the current view
     * @throws NullPointerException if facesContext is null
     */
    public static ViewDescriptor getCurrentView(final FacesContext facesContext) {
        requireNonNull(facesContext, "facesContext");

        final String foundId;
        final var root = facesContext.getViewRoot();
        final var builder = ViewDescriptorImpl.builder();
        if (null != root) {
            foundId = root.getViewId();
        } else {
            foundId = extractRequestUri(facesContext.getExternalContext().getRequest());
        }

        if (null != foundId) {
            builder.withViewId(foundId);
            builder.withLogicalViewId(handleViewIdSuffix(foundId));
        }
        builder.withUrlParameter(extractUrlParameters(facesContext.getExternalContext().getRequest()));

        return builder.build();
    }

    /**
     * Extracts URL parameters from the request.
     * <p>
     * This method converts the query string from the request into a list of
     * {@link UrlParameter} objects for easier handling.
     * </p>
     *
     * @param request The request object, expected to be an {@link HttpServletRequest}
     * @return A list of {@link UrlParameter} instances derived from the query string,
     *         or an empty list if the request is not an HttpServletRequest
     */
    public static List<UrlParameter> extractUrlParameters(final Object request) {
        if (request instanceof HttpServletRequest servletRequest) {
            return UrlParameter.fromQueryString(Servlets.getRequestQueryString(servletRequest));
        }
        log.warn(WARN.UNEXPECTED_ENVIRONMENT.format(request));
        return Collections.emptyList();
    }

    /**
     * Handles view ID suffix conversion, replacing .xhtml with .jsf.
     * <p>
     * This method ensures proper handling of file extensions for web-accessible views,
     * converting internal view IDs to their web-accessible form.
     * </p>
     *
     * @param viewId The view ID to process, must not be null
     * @return The view ID with .xhtml replaced by .jsf if applicable, otherwise unchanged
     */
    public static String handleViewIdSuffix(final String viewId) {
        if (viewId.endsWith(XHTML)) {
            return viewId.replace(XHTML, JSF);
        }
        return viewId;
    }

    /**
     * Executes a redirect to the specified URL.
     * 
     * <em>Caution</em>: This method is only for context-relative redirects,
     * therefore only the referenced file will be resolved/redirected to,
     * not the complete {@link URL}.
     *
     * @param newTarget The {@link URL} object to redirect to, must not be null
     * @throws NullPointerException if newTarget is null
     */
    public static void executeRedirect(final URL newTarget) {
        final var checked = requireNonNull(newTarget, "URL must not be null");
        final var facesContext = FacesContext.getCurrentInstance();
        final var calculatedUrl = checked.getFile();
        final var response = ServletAdapterUtil.getResponse(facesContext);

        if (!response.isCommitted()) {
            final var fullUrl = response.encodeRedirectURL(calculatedUrl);
            redirect(facesContext, fullUrl);
        } else if (log.isWarnEnabled()) {
            log.warn(WARN.RESPONSE_ALREADY_COMMITTED::format);
        }
    }

    /**
     * Extracts the URI from the request, removing the context path.
     * 
     * <p>This method tries to extract the URI of the requested request and removes the
     * context path to unify the structure with {@link UIViewRoot#getViewId()}.
     *
     * @param request The request object, expected to be an {@link HttpServletRequest}
     * @return The request URI with the context path removed if it can be determined,
     *         null otherwise
     */
    public static String extractRequestUri(final Object request) {
        if (request instanceof HttpServletRequest servletRequest) {
            return nullToEmpty(servletRequest.getRequestURI())
                    .replaceFirst(nullToEmpty(servletRequest.getContextPath()), "");
        }
        log.warn(WARN.UNEXPECTED_ENVIRONMENT.format(request));
        return null;
    }

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private NavigationUtils() {
        // Utility class should not be instantiated
    }
}
