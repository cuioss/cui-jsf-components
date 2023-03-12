package de.cuioss.jsf.api.servlet;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.servlet.http.Cookie;

import org.junit.jupiter.api.Test;

import de.cuioss.test.jsf.junit5.JsfEnabledTestEnvironment;

class CookieMonsterTest extends JsfEnabledTestEnvironment {

    private static final String COUNTRY_COOKIE_NAME = "country";

    private static final String COUNTRY_COOKIE_VALUE = "US";

    @Test
    void testGetRequestCookies() {
        setSomeCookiesAndPutThemIntoContext();
        var requestCookies = CookieMonster.getRequestCookies(getFacesContext());
        assertNotNull(requestCookies);
        assertEquals(3, requestCookies.size());
    }

    @Test
    void testGetRequestCookiesWithNullContext() {
        assertThrows(NullPointerException.class, () -> CookieMonster.getRequestCookies(null));
    }

    @Test
    void testGetRequestCookieForName() {
        setSomeCookiesAndPutThemIntoContext();
        var cookie = CookieMonster.getRequestCookieForName(getFacesContext(), COUNTRY_COOKIE_NAME);
        assertNotNull(cookie);
        assertEquals(COUNTRY_COOKIE_NAME, cookie.getName());
        assertEquals(COUNTRY_COOKIE_VALUE, cookie.getValue());
    }

    @Test
    void testGetRequestCookieForNameWithNullName() {
        var facesContect = getFacesContext();
        assertThrows(IllegalArgumentException.class,
                () -> CookieMonster.getRequestCookieForName(facesContect, null));
    }

    @Test
    void testGetRequestCookieForNameWithNullContext() {
        assertThrows(NullPointerException.class,
                () -> CookieMonster.getRequestCookieForName(null, COUNTRY_COOKIE_NAME));
    }

    @Test
    void testSetSimpleResponseCookie() {
        CookieMonster.setSimpleResponseCookie(getFacesContext(), COUNTRY_COOKIE_NAME, COUNTRY_COOKIE_VALUE);
        var c = getResponse().getCookies();
        assertEquals(1, c.size());
        assertTrue(c.containsKey(COUNTRY_COOKIE_NAME));
        var cookie = c.get(COUNTRY_COOKIE_NAME);
        assertEquals(COUNTRY_COOKIE_VALUE, cookie.getValue());
        assertEquals(COUNTRY_COOKIE_NAME, cookie.getName());
    }

    @Test
    void testSetSimpleResponseCookieWithNullName() {
        var facesContect = getFacesContext();
        assertThrows(IllegalArgumentException.class,
                () -> CookieMonster.setSimpleResponseCookie(facesContect, null, COUNTRY_COOKIE_VALUE));
    }

    @Test
    void testSetSimpleResponseCookieWithNullValue() {
        var facesContect = getFacesContext();
        assertThrows(IllegalArgumentException.class,
                () -> CookieMonster.setSimpleResponseCookie(facesContect, COUNTRY_COOKIE_NAME, null));
    }

    @Test
    void testSetSimpleResponseCookieWithNullContext() {
        assertThrows(NullPointerException.class,
                () -> CookieMonster.setSimpleResponseCookie(null, COUNTRY_COOKIE_NAME, COUNTRY_COOKIE_VALUE));
    }

    @Test
    void testSetResponseCookie() {
        var cookie = new Cookie(COUNTRY_COOKIE_NAME, COUNTRY_COOKIE_VALUE);
        assertDoesNotThrow(() -> CookieMonster.setResponseCookie(getFacesContext(), cookie));
    }

    @Test
    void testSetResponseCookieWithNullContext() {
        var cookie = new Cookie(COUNTRY_COOKIE_NAME, COUNTRY_COOKIE_VALUE);
        assertThrows(NullPointerException.class,
                () -> CookieMonster.setResponseCookie(null, cookie));
    }

    @Test
    void testSetResponseCookieWithNullCookie() {
        var facesContect = getFacesContext();
        assertThrows(NullPointerException.class, () -> CookieMonster.setResponseCookie(facesContect, null));
    }

    private void setSomeCookiesAndPutThemIntoContext() {
        getRequestConfigDecorator().addRequestCookie(new Cookie(COUNTRY_COOKIE_NAME, COUNTRY_COOKIE_VALUE));
        getRequestConfigDecorator().addRequestCookie(new Cookie("language", "en"));
        getRequestConfigDecorator().addRequestCookie(new Cookie("username", "office0"));
    }
}
