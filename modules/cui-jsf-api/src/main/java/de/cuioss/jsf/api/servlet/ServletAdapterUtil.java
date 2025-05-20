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

import static de.cuioss.tools.string.MoreStrings.requireNotEmpty;
import static java.util.Objects.requireNonNull;

import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.Optional;

/**
 * Utility class, introducing functionality not covered within
 * {@link ExternalContext}
 * <p>
 * In essence these are mainly convenience methods
 * </p>
 *
 * @author Oliver Wolff
 * @author Nina Petersmann
 */
@UtilityClass
public final class ServletAdapterUtil {

    /**
     * Reads an Parameter from the external Request.
     *
     * @param facesContext  representing current state. Must not be null or empty
     * @param parameterName identifying the wanted parameter. Must not be null or
     *                      empty
     * @return the loaded parameter if available
     */
    public static String getRequestParameter(FacesContext facesContext, String parameterName) {
        requireNotEmpty(parameterName, "parameterName");
        return getRequest(facesContext).getParameter(parameterName);
    }

    /**
     * Accesses the contained HttpServletResponse
     *
     * @param facesContext representing current state. Must not be null
     * @return corresponding HttpServletResponse
     */
    public static HttpServletResponse getResponse(FacesContext facesContext) {
        requireNonNull(facesContext);
        final var externalContext = facesContext.getExternalContext();
        return (HttpServletResponse) externalContext.getResponse();
    }

    /**
     * Accesses the contained RequestHeaderMap
     *
     * @param facesContext representing current state. Must not be null
     * @return corresponding RequestHeaderMap
     */
    public static Map<String, String> getRequestHeaderMap(FacesContext facesContext) {
        requireNonNull(facesContext);
        final var externalContext = facesContext.getExternalContext();
        return externalContext.getRequestHeaderMap();
    }

    /**
     * Accesses the contained HttpServletRequest
     *
     * @param facesContext representing current state. Must not be null
     * @return corresponding HttpServletRequest
     */
    public static HttpServletRequest getRequest(FacesContext facesContext) {
        requireNonNull(facesContext);
        final var externalContext = facesContext.getExternalContext();
        return (HttpServletRequest) externalContext.getRequest();
    }

    /**
     * Returns an already <em>existing</em> {@link HttpSession} if present
     *
     * @param facesContext
     * @return an already <em>existing</em> {@link HttpSession} if present
     */
    public static Optional<HttpSession> getSession(FacesContext facesContext) {
        var request = getRequest(facesContext);
        return Optional.ofNullable(request.getSession(false));
    }
}
