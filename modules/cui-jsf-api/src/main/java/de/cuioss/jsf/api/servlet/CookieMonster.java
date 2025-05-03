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
 * General purpose Cookie Utility for JSF.
 * <p>
 * This implementation works for the servlet not for the portlet API
 * </p>
 * <p>
 * I like cookies !!!
 * </p>
 *
 * @author Oliver Wolff
 */
public final class CookieMonster {

    /**
     * Access the available cookies for a given request.
     *
     * @param facesContext representing current state. Must not be null
     * @return a list of request-cookies, an empty list if no cookie is found.
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
     * Accesses a cookie for a given name.
     *
     * @param facesContext representing current state. Must not be null or empty
     * @param cookieName   name of the cookie to be looked up. Must not be null or
     *                     empty
     * @return the found cookie or null if none is found.
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
     * Simplified adding of a cookie. It solely sets the payload. It utilizes
     * RequestContextPath as path for the cookie.
     *
     * @param facesContext representing current state. Must not be null or empty
     * @param cookieName   Must not be null or empty
     * @param cookieValue  Must not be null
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
     * Adding a cookie.
     *
     * @param facesContext representing current state. Must not be null or empty
     * @param cookie       Must not be null
     */
    public static void setResponseCookie(final FacesContext facesContext, final Cookie cookie) {
        requireNonNull(cookie, "cookie");
        getResponse(facesContext).addCookie(cookie);
    }

    /**
     * Enforce utility class
     */
    private CookieMonster() {

    }
}
