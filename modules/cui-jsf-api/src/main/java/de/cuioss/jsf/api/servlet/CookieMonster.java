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
package de.cuioss.jsf.api.servlet;

import static de.cuioss.jsf.api.servlet.ServletAdapterUtil.getRequest;
import static de.cuioss.jsf.api.servlet.ServletAdapterUtil.getResponse;
import static de.cuioss.tools.string.MoreStrings.requireNotEmpty;
import static java.util.Objects.requireNonNull;

import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.Cookie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * General purpose Cookie utility for JSF applications built on the Servlet API.
 * <p>
 * This class provides methods to easily access and manipulate cookies in a JSF context.
 * It simplifies common cookie operations like retrieving request cookies, finding cookies
 * by name, and setting response cookies with appropriate security settings.
 * </p>
 * <p>
 * Important: This implementation works only with the Servlet API, not with the Portlet API.
 * </p>
 * <p>
 * Usage examples:
 * </p>
 * <pre>
 * // Get all cookies from the current request
 * List&lt;Cookie&gt; cookies = CookieMonster.getRequestCookies(facesContext);
 * 
 * // Find a specific cookie by name
 * Cookie userCookie = CookieMonster.getRequestCookieForName(facesContext, "userName");
 * 
 * // Set a simple cookie with secure defaults
 * CookieMonster.setSimpleResponseCookie(facesContext, "userName", "johndoe");
 * </pre>
 * <p>
 * By default, cookies set through this utility are marked as HttpOnly and Secure
 * to enhance security.
 * </p>
 *
 * @author Oliver Wolff
 */
public final class CookieMonster {

    /**
     * Access the available cookies for a given request.
     * <p>
     * This method retrieves all cookies sent with the current HTTP request.
     * </p>
     *
     * @param facesContext representing current JSF context. Must not be null
     * @return a list of request-cookies, an empty list if no cookie is found
     * @throws NullPointerException if facesContext is null
     */
    public static List<Cookie> getRequestCookies(final FacesContext facesContext) {
        final var cookies = getRequest(facesContext).getCookies();
        final List<Cookie> result = new ArrayList<>();
        if (null != cookies) {
            result.addAll(Arrays.asList(cookies));
        }
        return result;
    }

    /**
     * Accesses a cookie for a given name from the current request.
     * <p>
     * This method searches through all cookies in the current request and
     * returns the first one matching the specified name.
     * </p>
     *
     * @param facesContext representing current JSF context. Must not be null
     * @param cookieName   name of the cookie to be looked up. Must not be null or
     *                     empty
     * @return the found cookie or null if none is found with the given name
     * @throws IllegalArgumentException if cookieName is null or empty
     * @throws NullPointerException if facesContext is null
     */
    public static Cookie getRequestCookieForName(final FacesContext facesContext, final String cookieName) {
        requireNotEmpty(cookieName, "cookieName");
        Cookie found = null;
        for (final Cookie cookie : getRequestCookies(facesContext)) {
            if (cookieName.equals(cookie.getName())) {
                found = cookie;
                break;
            }
        }
        return found;
    }

    /**
     * Simplified method for adding a cookie to the response with secure defaults.
     * <p>
     * This method creates a cookie with the provided name and value, sets it to be
     * HttpOnly and Secure, and uses the current request context path as the cookie path.
     * This ensures the cookie is accessible only to the current application and only
     * sent over HTTPS connections.
     * </p>
     *
     * @param facesContext representing current JSF context. Must not be null
     * @param cookieName   name of the cookie to be created. Must not be null or empty
     * @param cookieValue  value of the cookie to be set. Must not be null or empty
     * @throws IllegalArgumentException if cookieName or cookieValue is null or empty
     * @throws NullPointerException if facesContext is null
     */
    public static void setSimpleResponseCookie(final FacesContext facesContext, final String cookieName,
            final String cookieValue) {
        requireNotEmpty(cookieName, "cookieName");
        requireNotEmpty(cookieValue, "cookieValue");
        requireNonNull(facesContext, "facesContext");
        final var cookie = new Cookie(cookieName, cookieValue);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath(facesContext.getExternalContext().getRequestContextPath());
        setResponseCookie(facesContext, cookie);
    }

    /**
     * Adds a pre-configured cookie to the response.
     * <p>
     * This method allows for adding a fully configured cookie to the response.
     * Use this method when you need more control over cookie properties than
     * what {@link #setSimpleResponseCookie(FacesContext, String, String)} provides.
     * </p>
     *
     * @param facesContext representing current JSF context. Must not be null
     * @param cookie       the pre-configured cookie to add to the response. Must not be null
     * @throws NullPointerException if facesContext or cookie is null
     */
    public static void setResponseCookie(final FacesContext facesContext, final Cookie cookie) {
        requireNonNull(cookie, "cookie");
        getResponse(facesContext).addCookie(cookie);
    }

    /**
     * Private constructor to prevent instantiation of utility class.
     */
    private CookieMonster() {
        // Enforce utility class pattern
    }
}
