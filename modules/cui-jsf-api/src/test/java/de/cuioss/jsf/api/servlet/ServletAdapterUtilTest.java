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

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.test.jsf.junit5.JsfEnabledTestEnvironment;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

class ServletAdapterUtilTest extends JsfEnabledTestEnvironment {

    private static final String PARAM_NAME = "parameterName";

    @Test
    void getRequestParameter() {
        var s1 = ServletAdapterUtil.getRequestParameter(getFacesContext(), PARAM_NAME);
        var s2 = ServletAdapterUtil.getRequest(getFacesContext()).getParameter(PARAM_NAME);
        assertEquals(s1, s2);
    }

    @Test
    void getRequestParameterWithNullContext() {
        assertThrows(NullPointerException.class, () -> ServletAdapterUtil.getRequestParameter(null, PARAM_NAME));
    }

    @Test
    void getRequestParameterWithNullParameter() {
        assertThrows(IllegalArgumentException.class,
                () -> ServletAdapterUtil.getRequestParameter(getFacesContext(), null));
    }

    @Test
    void testGetResponse() {
        final var response = (HttpServletResponse) getFacesContext().getExternalContext().getResponse();
        assertEquals(response, ServletAdapterUtil.getResponse(getFacesContext()));
    }

    @Test
    void shouldProduceSession() {
        var session = ServletAdapterUtil.getSession(getFacesContext());
        assertTrue(session.isPresent());
    }

    @Test
    void getResponseWithNullContext() {
        assertThrows(NullPointerException.class, () -> ServletAdapterUtil.getResponse(null));
    }

    @Test
    void getRequest() {
        final var request = (HttpServletRequest) getFacesContext().getExternalContext().getRequest();
        assertEquals(request, ServletAdapterUtil.getRequest(getFacesContext()));
    }

    @Test
    void getRequestWithNullContext() {
        assertThrows(NullPointerException.class, () -> ServletAdapterUtil.getRequest(null));
    }

}
