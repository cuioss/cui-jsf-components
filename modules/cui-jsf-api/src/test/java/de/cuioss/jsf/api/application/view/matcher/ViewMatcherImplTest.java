package de.cuioss.jsf.api.application.view.matcher;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.common.view.ViewDescriptor;
import de.cuioss.jsf.api.common.view.ViewDescriptorImpl;
import de.cuioss.tools.net.UrlParameter;

class ViewMatcherImplTest {

    private static final String FACES_CONTENT = "/faces/content/";

    private static final String FACES_GUEST = "/faces/guest/";

    private static final String FACES_NOT_THERE = "/faces/not/there";

    private static final String LOGIN_VIEW_PATH = FACES_GUEST + "login.jsf";

    private static ViewDescriptor LOGIN_VIEW = ViewDescriptorImpl.builder().withViewId(LOGIN_VIEW_PATH)
            .withUrlParameter(immutableList(new UrlParameter("somePara", "someValue")))
            .withLogicalViewId(LOGIN_VIEW_PATH).build();

    private static final String LOGOUT_VIEW_PATH = FACES_GUEST + "logout.jsf";

    private static ViewDescriptor LOGOUT_VIEW = ViewDescriptorImpl.builder().withViewId(LOGOUT_VIEW_PATH)
            .withUrlParameter(Collections.emptyList()).withLogicalViewId(LOGOUT_VIEW_PATH).build();

    private static final String CONTENT_VIEW_PATH = FACES_CONTENT + "content.jsf";

    private static ViewDescriptor CONTENT_VIEW = ViewDescriptorImpl.builder().withViewId(CONTENT_VIEW_PATH)
            .withUrlParameter(Collections.emptyList()).withLogicalViewId(CONTENT_VIEW_PATH).build();

    @Test
    void shouldMatchForGuestAndLoginMatcher() {
        var underTest = new ViewMatcherImpl(immutableList(LOGIN_VIEW_PATH, FACES_GUEST));
        assertTrue(underTest.match(LOGIN_VIEW));
        assertTrue(underTest.match(LOGOUT_VIEW));
        assertFalse(underTest.match(CONTENT_VIEW));
    }

    @Test
    void shouldMatchForContentMatcher() {
        var underTest = new ViewMatcherImpl(immutableList(FACES_CONTENT));
        assertFalse(underTest.match(LOGIN_VIEW));
        assertFalse(underTest.match(LOGOUT_VIEW));
        assertTrue(underTest.match(CONTENT_VIEW));
    }

    @Test
    void shouldMatchAllForWildcard() {
        var underTest = new ViewMatcherImpl(immutableList("/"));
        assertTrue(underTest.match(LOGIN_VIEW));
        assertTrue(underTest.match(LOGOUT_VIEW));
        assertTrue(underTest.match(CONTENT_VIEW));
    }

    @Test
    void shouldMatchNoneForEmpty() {
        var underTest = new ViewMatcherImpl(immutableList("", "  "));
        assertFalse(underTest.match(LOGIN_VIEW));
        assertFalse(underTest.match(LOGOUT_VIEW));
        assertFalse(underTest.match(CONTENT_VIEW));
    }

    @Test
    void shouldMatchNoneForNotThere() {
        var underTest = new ViewMatcherImpl(immutableList(FACES_NOT_THERE));
        assertFalse(underTest.match(LOGIN_VIEW));
        assertFalse(underTest.match(LOGOUT_VIEW));
        assertFalse(underTest.match(CONTENT_VIEW));
    }

    @SuppressWarnings("unused")
    @Test
    void shouldFAilOnNullMatchList() {
        assertThrows(NullPointerException.class, () -> new ViewMatcherImpl(null));
    }
}
