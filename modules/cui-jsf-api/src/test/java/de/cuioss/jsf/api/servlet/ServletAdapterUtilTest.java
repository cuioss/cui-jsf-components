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

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.test.jsf.junit5.EnableJsfEnvironment;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

@EnableJsfEnvironment
class ServletAdapterUtilTest {

    private static final String PARAM_NAME = "parameterName";

    @Test
    void getRequestParameter(FacesContext facesContext) {
        var s1 = ServletAdapterUtil.getRequestParameter(facesContext, PARAM_NAME);
        var s2 = ServletAdapterUtil.getRequest(facesContext).getParameter(PARAM_NAME);
        assertEquals(s1, s2);
    }

    @Test
    void getRequestParameterWithNullContext() {
        assertThrows(NullPointerException.class, () -> ServletAdapterUtil.getRequestParameter(null, PARAM_NAME));
    }

    @Test
    void getRequestParameterWithNullParameter(FacesContext facesContext) {
        assertThrows(IllegalArgumentException.class,
                () -> ServletAdapterUtil.getRequestParameter(facesContext, null));
    }

    @Test
    void getResponse(FacesContext facesContext) {
        final var response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        assertEquals(response, ServletAdapterUtil.getResponse(facesContext));
    }

    @Test
    void shouldProduceSession(FacesContext facesContext) {
        var session = ServletAdapterUtil.getSession(facesContext);
        assertTrue(session.isPresent());
    }

    @Test
    void getResponseWithNullContext() {
        assertThrows(NullPointerException.class, () -> ServletAdapterUtil.getResponse(null));
    }

    @Test
    void getRequest(FacesContext facesContext) {
        final var request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        assertEquals(request, ServletAdapterUtil.getRequest(facesContext));
    }

    @Test
    void getRequestWithNullContext() {
        assertThrows(NullPointerException.class, () -> ServletAdapterUtil.getRequest(null));
    }

}
