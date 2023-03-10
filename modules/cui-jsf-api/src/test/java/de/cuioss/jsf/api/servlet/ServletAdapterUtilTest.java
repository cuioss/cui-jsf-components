package de.cuioss.jsf.api.servlet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;

import de.cuioss.test.jsf.junit5.JsfEnabledTestEnvironment;

class ServletAdapterUtilTest extends JsfEnabledTestEnvironment {

    private static final String PARAM_NAME = "parameterName";

    @Test
    void testGetRequestParameter() {
        var s1 = ServletAdapterUtil.getRequestParameter(getFacesContext(), PARAM_NAME);
        var s2 = ServletAdapterUtil.getRequest(getFacesContext()).getParameter(PARAM_NAME);
        assertEquals(s1, s2);
    }

    @Test
    void testGetRequestParameterWithNullContext() {
        assertThrows(NullPointerException.class, () -> {
            ServletAdapterUtil.getRequestParameter(null, PARAM_NAME);
        });
    }

    @Test
    void testGetRequestParameterWithNullParameter() {
        assertThrows(IllegalArgumentException.class, () -> {
            ServletAdapterUtil.getRequestParameter(getFacesContext(), null);
        });
    }

    @Test
    void testGetResponse() {
        final var response = (HttpServletResponse) getFacesContext().getExternalContext().getResponse();
        assertEquals(response, ServletAdapterUtil.getResponse(getFacesContext()));
    }

    @Test
    void testGetResponseWithNullContext() {
        assertThrows(NullPointerException.class, () -> {
            ServletAdapterUtil.getResponse(null);
        });
    }

    @Test
    void testGetRequest() {
        final var request = (HttpServletRequest) getFacesContext().getExternalContext().getRequest();
        assertEquals(request, ServletAdapterUtil.getRequest(getFacesContext()));
    }

    @Test
    void testGetRequestWithNullContext() {
        assertThrows(NullPointerException.class, () -> {
            ServletAdapterUtil.getRequest(null);
        });
    }

}
