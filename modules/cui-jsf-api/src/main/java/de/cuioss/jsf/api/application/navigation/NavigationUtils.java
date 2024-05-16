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

import static de.cuioss.jsf.api.servlet.ServletAdapterUtil.getRequest;
import static de.cuioss.jsf.api.servlet.ServletAdapterUtil.getResponse;
import static de.cuioss.tools.net.UrlParameter.createParameterString;
import static de.cuioss.tools.string.MoreStrings.nullToEmpty;
import static de.cuioss.tools.string.MoreStrings.requireNotEmpty;
import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import jakarta.faces.application.ConfigurableNavigationHandler;
import jakarta.faces.application.NavigationCase;
import jakarta.faces.component.UIViewRoot;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpServletRequest;

import org.omnifaces.util.Servlets;

import de.cuioss.jsf.api.common.view.ViewDescriptor;
import de.cuioss.jsf.api.common.view.ViewDescriptorImpl;
import de.cuioss.jsf.api.servlet.ServletAdapterUtil;
import de.cuioss.tools.logging.CuiLogger;
import de.cuioss.tools.net.UrlParameter;

/**
 * Class provides navigation utilities.<br>
 * <b>Attention</b> use {@linkplain NavigationUtils} careful and only if Faces
 * Request needs to be redirected to a <b>different</b> web application
 * resource! If you doesn't leave the web application space use Navigation
 * Rules!
 *
 */
public final class NavigationUtils implements Serializable {

    private static final CuiLogger log = new CuiLogger(NavigationUtils.class);

    private static final String JSF = ".jsf";

    private static final String XHTML = ".xhtml";

    private static final long serialVersionUID = -3658719779910485458L;

    private static final String UNSUPPORTED_CONFIGURATION = "Unsupported configuration for fallback navigation detected. Expected NavigationHandler is ConfigurableNavigationHandler, but was ['%s']";

    private static final String INCOMPLETE_CONFIG = "No NavigationCase defined for outcome ['%s']. Verify your faces configuration.";

    private static final String UNABLE_TO_REDIRECT_RESPONSE_ALREADY_COMMITTED = "Unable to redirect, response already committed.";

    /**
     * Try to look up view id or view id expression for outcome by using
     * {@linkplain ConfigurableNavigationHandler}
     *
     * @param facesContext to be utilized
     * @param outcome      navigation case outcome
     * @return view id if can be resolved.
     * @throws IllegalStateException if no
     *                               {@linkplain ConfigurableNavigationHandler} is
     *                               used in application
     * @throws IllegalStateException if the {@linkplain NavigationCase} for outcome
     *                               not available
     */
    public static String lookUpToViewIdBy(final FacesContext facesContext, final String outcome) {
        return findNavigationCaseForOutcome(facesContext, outcome).getToViewId(facesContext);
    }

    /**
     * Try to look up logical view id (=resulting url) of view id or view id
     * expression for outcome by using {@linkplain ConfigurableNavigationHandler}
     *
     * @param facesContext to be utilized
     * @param outcome      navigation case outcome
     * @return logical view id (=resulting url) of the view id if can be resolved.
     * @throws IllegalStateException if no
     *                               {@linkplain ConfigurableNavigationHandler} is
     *                               used in application
     * @throws IllegalStateException if the {@linkplain NavigationCase} for outcome
     *                               not available
     */
    public static String lookUpToLogicalViewIdBy(final FacesContext facesContext, final String outcome) {
        return handleViewIdSuffix(lookUpToViewIdBy(facesContext, outcome));
    }

    /**
     * Try to look up a ViewIdentifier of view id or view id expression for outcome
     * by using {@linkplain ConfigurableNavigationHandler}
     *
     * @param facesContext to be utilized
     * @param outcome      navigation case outcome
     * @return ViewIdentifier of the view id if can be resolved.
     * @throws IllegalStateException if no
     *                               {@linkplain ConfigurableNavigationHandler} is
     *                               used in application
     * @throws IllegalStateException if the {@linkplain NavigationCase} for outcome
     *                               not available
     */
    public static ViewIdentifier lookUpToViewIdentifierBy(final FacesContext facesContext, final String outcome) {
        return new ViewIdentifier(lookUpToLogicalViewIdBy(facesContext, outcome), outcome, Collections.emptyList());
    }

    /**
     * Try to look up a ViewDescriptor of view id or view id expression for outcome
     * by using {@linkplain ConfigurableNavigationHandler}
     *
     * @param facesContext to be utilized
     * @param outcome      navigation case outcome
     * @return ViewDescriptor of the view id if can be resolved.
     * @throws IllegalStateException if no
     *                               {@linkplain ConfigurableNavigationHandler} is
     *                               used in application
     * @throws IllegalStateException if the {@linkplain NavigationCase} for outcome
     *                               not available
     */
    public static ViewDescriptor lookUpToViewDescriptorBy(final FacesContext facesContext, final String outcome) {
        final var viewId = lookUpToViewIdBy(facesContext, outcome);
        return ViewDescriptorImpl.builder().withLogicalViewId(handleViewIdSuffix(viewId)).withViewId(viewId).build();
    }

    /**
     * Try to look up a navigation case outcome by using
     * {@linkplain ConfigurableNavigationHandler}. In
     *
     * @param facesContext to be utilized
     * @param outcome      navigation case outcome
     * @return {@link NavigationCase} id if can be resolved.
     * @throws IllegalStateException if no
     *                               {@linkplain ConfigurableNavigationHandler} is
     *                               used in application
     * @throws IllegalStateException if the {@linkplain NavigationCase} for outcome
     *                               not available
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
     * Send redirect to given URL.
     *
     * @param url          Context-relative url where the response should be
     *                     redirected. Must not be null or empty
     * @param facesContext current faces context, must not be null.
     * @param encode       indicates whether to encode the UrlParameter
     * @param parameters   optional {@link UrlParameter}
     */
    public static void sendRedirect(final FacesContext facesContext, final String url, final boolean encode,
            final UrlParameter... parameters) {

        requireNotEmpty(url, "url");

        final var response = getResponse(facesContext);
        if (!response.isCommitted()) {

            final var fullUrl = new StringBuilder()
                    .append(response.encodeRedirectURL(getRequest(facesContext).getContextPath()))
                    .append(handleViewIdSuffix(url)).append(createParameterString(encode, parameters)).toString();

            redirect(facesContext, fullUrl);
        } else if (log.isWarnEnabled()) {
            log.warn(UNABLE_TO_REDIRECT_RESPONSE_ALREADY_COMMITTED);
        }
    }

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
     * Shorthand for using sendRedirect with List instead of ellipsis
     *
     * @param url           Context-relative url where the response should be
     *                      redirected. Must not be null or empty
     * @param facesContext  current faces context, must not be null.
     * @param parameterList list of url parameter
     */
    public static void sendRedirectParameterList(final FacesContext facesContext, final String url,
            final List<UrlParameter> parameterList) {
        if (parameterList != null && !parameterList.isEmpty()) {
            final var parameters = parameterList.toArray(new UrlParameter[parameterList.size()]);
            sendRedirect(facesContext, url, true, parameters);
        } else {
            sendRedirect(facesContext, url, false);
        }
    }

    /**
     * Shorthand for using sendRedirect with outcome and List instead of ellipsis
     *
     * @param facesContext to be used
     * @param outcome      navigation case outcome
     * @param parameters   optional {@link UrlParameter}
     * @throws IllegalStateException if no
     *                               {@linkplain ConfigurableNavigationHandler} is
     *                               used in application
     * @throws IllegalStateException if the {@linkplain NavigationCase} for outcome
     *                               not available
     */
    public static void sendRedirectOutcomeParameterList(final FacesContext facesContext, final String outcome,
            final List<UrlParameter> parameters) {
        final var url = lookUpToLogicalViewIdBy(facesContext, outcome);
        sendRedirectParameterList(facesContext, url, parameters);
    }

    /**
     * Extracts the viewId from facesContext. If none can be extracted it tries to
     * extract the requested uri from the context. If this fails as well it will
     * return an empty {@link ViewDescriptor}.
     *
     * @param facesContext {@link FacesContext} must not be null
     * @return ViewDescriptor extracted from facesContext.
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
     * Extracts from the faces context a List of {@link UrlParameter}
     *
     * @param request expected to be an {@link HttpServletRequest}, {@link Object}
     *                because of the return value of
     *                {@link ExternalContext#getRequest()}
     *
     * @return the Url-parameter derived from the query-String, in case the given
     *         request does not represent an {@link HttpServletRequest} it will
     *         return an empty List.
     */
    public static List<UrlParameter> extractUrlParameters(final Object request) {
        if (request instanceof HttpServletRequest servletRequest) {
            return UrlParameter.fromQueryString(Servlets.getRequestQueryString(servletRequest));
        }
        log.warn(
                "Unexpected environment. {} is not of type javax.servlet.http.HttpServletRequest. This call therefore returns null",
                request);
        return Collections.emptyList();
    }

    /**
     * Temporary method to ensure proper handling of extensions.
     *
     * @param viewId text representation must not be null
     * @return the view id with "xhtml" replaced by "jsf"
     */
    public static String handleViewIdSuffix(final String viewId) {
        if (viewId.endsWith(XHTML)) {
            return viewId.replace(XHTML, JSF);
        }
        return viewId;
    }

    /**
     * Execute redirect by {@linkplain URL} <em>Caution</em>: This method is only
     * for context relative redirects, therefore only the referenced file will be
     * resolved / redirected to, not the complete {@link URL}
     *
     * @param newTarget {@linkplain URL} must not be null
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
            log.warn(UNABLE_TO_REDIRECT_RESPONSE_ALREADY_COMMITTED);
        }
    }

    /**
     * Tries to extract the uri of the requested request. It removes the context
     * path from the uri in order to unify the structure with
     * {@link UIViewRoot#getViewId()}
     *
     * @param request to be used. It is assumed that it is assignable to
     *                {@link HttpServletRequest} in order to work. The type of
     *                {@link Object} is needed because of
     *                {@link ExternalContext#getRequest()} returning {@link Object}
     * @return the request url if it can be determined, null otherwise
     */
    public static String extractRequestUri(final Object request) {
        if (request instanceof HttpServletRequest servletRequest) {
            return nullToEmpty(servletRequest.getRequestURI())
                    .replaceFirst(nullToEmpty(servletRequest.getContextPath()), "");
        }
        log.warn(
                "Unexpected environment. {} is not of type javax.servlet.http.HttpServletRequest. This call therefore returns null",
                request);
        return null;

    }

    /**
     * Enforce utility class
     */
    private NavigationUtils() {

    }
}
