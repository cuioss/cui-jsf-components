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
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;

class CookieMonsterTest extends JsfEnabledTestEnvironment {

    private static final String COUNTRY_COOKIE_NAME = "country";

    private static final String COUNTRY_COOKIE_VALUE = "US";

    @Test
    void getRequestCookies() {
        setSomeCookiesAndPutThemIntoContext();
        var requestCookies = CookieMonster.getRequestCookies(getFacesContext());
        assertNotNull(requestCookies);
        assertEquals(3, requestCookies.size());
    }

    @Test
    void getRequestCookiesWithNullContext() {
        assertThrows(NullPointerException.class, () -> CookieMonster.getRequestCookies(null));
    }

    @Test
    void getRequestCookieForName() {
        setSomeCookiesAndPutThemIntoContext();
        var cookie = CookieMonster.getRequestCookieForName(getFacesContext(), COUNTRY_COOKIE_NAME);
        assertNotNull(cookie);
        assertEquals(COUNTRY_COOKIE_NAME, cookie.getName());
        assertEquals(COUNTRY_COOKIE_VALUE, cookie.getValue());
    }

    @Test
    void getRequestCookieForNameWithNullName() {
        var facesContect = getFacesContext();
        assertThrows(IllegalArgumentException.class, () -> CookieMonster.getRequestCookieForName(facesContect, null));
    }

    @Test
    void getRequestCookieForNameWithNullContext() {
        assertThrows(NullPointerException.class,
                () -> CookieMonster.getRequestCookieForName(null, COUNTRY_COOKIE_NAME));
    }

    @Test
    void setSimpleResponseCookie() {
        CookieMonster.setSimpleResponseCookie(getFacesContext(), COUNTRY_COOKIE_NAME, COUNTRY_COOKIE_VALUE);
        var c = getResponse().getCookies();
        assertEquals(1, c.size());
        assertTrue(c.containsKey(COUNTRY_COOKIE_NAME));
        var cookie = c.get(COUNTRY_COOKIE_NAME);
        assertEquals(COUNTRY_COOKIE_VALUE, cookie.getValue());
        assertEquals(COUNTRY_COOKIE_NAME, cookie.getName());
    }

    @Test
    void setSimpleResponseCookieWithNullName() {
        var facesContect = getFacesContext();
        assertThrows(IllegalArgumentException.class,
                () -> CookieMonster.setSimpleResponseCookie(facesContect, null, COUNTRY_COOKIE_VALUE));
    }

    @Test
    void setSimpleResponseCookieWithNullValue() {
        var facesContect = getFacesContext();
        assertThrows(IllegalArgumentException.class,
                () -> CookieMonster.setSimpleResponseCookie(facesContect, COUNTRY_COOKIE_NAME, null));
    }

    @Test
    void setSimpleResponseCookieWithNullContext() {
        assertThrows(NullPointerException.class,
                () -> CookieMonster.setSimpleResponseCookie(null, COUNTRY_COOKIE_NAME, COUNTRY_COOKIE_VALUE));
    }

    @Test
    void setResponseCookie() {
        var cookie = new Cookie(COUNTRY_COOKIE_NAME, COUNTRY_COOKIE_VALUE);
        assertDoesNotThrow(() -> CookieMonster.setResponseCookie(getFacesContext(), cookie));
    }

    @Test
    void setResponseCookieWithNullContext() {
        var cookie = new Cookie(COUNTRY_COOKIE_NAME, COUNTRY_COOKIE_VALUE);
        assertThrows(NullPointerException.class, () -> CookieMonster.setResponseCookie(null, cookie));
    }

    @Test
    void setResponseCookieWithNullCookie() {
        var facesContect = getFacesContext();
        assertThrows(NullPointerException.class, () -> CookieMonster.setResponseCookie(facesContect, null));
    }

    private void setSomeCookiesAndPutThemIntoContext() {
        getRequestConfigDecorator().addRequestCookie(new Cookie(COUNTRY_COOKIE_NAME, COUNTRY_COOKIE_VALUE));
        getRequestConfigDecorator().addRequestCookie(new Cookie("language", "en"));
        getRequestConfigDecorator().addRequestCookie(new Cookie("username", "office0"));
    }
}
