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

import de.cuioss.test.jsf.config.decorator.RequestConfigDecorator;
import de.cuioss.test.jsf.junit5.EnableJsfEnvironment;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.Cookie;
import org.apache.myfaces.test.mock.MockHttpServletResponse;
import org.junit.jupiter.api.Test;

@EnableJsfEnvironment
class CookieMonsterTest {

    private static final String COUNTRY_COOKIE_NAME = "country";

    private static final String COUNTRY_COOKIE_VALUE = "US";

    @Test
    void getRequestCookies(FacesContext facesContext, RequestConfigDecorator requestConfig) {
        setSomeCookiesAndPutThemIntoContext(requestConfig);
        var requestCookies = CookieMonster.getRequestCookies(facesContext);
        assertNotNull(requestCookies);
        assertEquals(3, requestCookies.size());
    }

    @Test
    void getRequestCookiesWithNullContext() {
        assertThrows(NullPointerException.class, () -> CookieMonster.getRequestCookies(null));
    }

    @Test
    void getRequestCookieForName(FacesContext facesContext, RequestConfigDecorator requestConfig) {
        setSomeCookiesAndPutThemIntoContext(requestConfig);
        var cookie = CookieMonster.getRequestCookieForName(facesContext, COUNTRY_COOKIE_NAME);
        assertNotNull(cookie);
        assertEquals(COUNTRY_COOKIE_NAME, cookie.getName());
        assertEquals(COUNTRY_COOKIE_VALUE, cookie.getValue());
    }

    @Test
    void getRequestCookieForNameWithNullName(FacesContext facesContext) {
        assertThrows(IllegalArgumentException.class, () -> CookieMonster.getRequestCookieForName(facesContext, null));
    }

    @Test
    void getRequestCookieForNameWithNullContext() {
        assertThrows(NullPointerException.class,
                () -> CookieMonster.getRequestCookieForName(null, COUNTRY_COOKIE_NAME));
    }

    @Test
    void setSimpleResponseCookie(FacesContext facesContext, MockHttpServletResponse response) {
        CookieMonster.setSimpleResponseCookie(facesContext, COUNTRY_COOKIE_NAME, COUNTRY_COOKIE_VALUE);
        var c = response.getCookies();
        assertEquals(1, c.size());
        assertTrue(c.containsKey(COUNTRY_COOKIE_NAME));
        var cookie = c.get(COUNTRY_COOKIE_NAME);
        assertEquals(COUNTRY_COOKIE_VALUE, cookie.getValue());
        assertEquals(COUNTRY_COOKIE_NAME, cookie.getName());
    }

    @Test
    void setSimpleResponseCookieWithNullName(FacesContext facesContext) {
        assertThrows(IllegalArgumentException.class,
                () -> CookieMonster.setSimpleResponseCookie(facesContext, null, COUNTRY_COOKIE_VALUE));
    }

    @Test
    void setSimpleResponseCookieWithNullValue(FacesContext facesContext) {
        assertThrows(IllegalArgumentException.class,
                () -> CookieMonster.setSimpleResponseCookie(facesContext, COUNTRY_COOKIE_NAME, null));
    }

    @Test
    void setSimpleResponseCookieWithNullContext() {
        assertThrows(NullPointerException.class,
                () -> CookieMonster.setSimpleResponseCookie(null, COUNTRY_COOKIE_NAME, COUNTRY_COOKIE_VALUE));
    }

    @Test
    void setResponseCookie(FacesContext facesContext) {
        var cookie = new Cookie(COUNTRY_COOKIE_NAME, COUNTRY_COOKIE_VALUE);
        assertDoesNotThrow(() -> CookieMonster.setResponseCookie(facesContext, cookie));
    }

    @Test
    void setResponseCookieWithNullContext() {
        var cookie = new Cookie(COUNTRY_COOKIE_NAME, COUNTRY_COOKIE_VALUE);
        assertThrows(NullPointerException.class, () -> CookieMonster.setResponseCookie(null, cookie));
    }

    @Test
    void setResponseCookieWithNullCookie(FacesContext facesContext) {
        assertThrows(NullPointerException.class, () -> CookieMonster.setResponseCookie(facesContext, null));
    }

    private void setSomeCookiesAndPutThemIntoContext(RequestConfigDecorator requestConfig) {
        requestConfig.addRequestCookie(new Cookie(COUNTRY_COOKIE_NAME, COUNTRY_COOKIE_VALUE));
        requestConfig.addRequestCookie(new Cookie("language", "en"));
        requestConfig.addRequestCookie(new Cookie("username", "office0"));
    }
}
