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
package de.cuioss.jsf.api.application.view.matcher;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;
import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.jsf.api.common.view.ViewDescriptor;
import de.cuioss.jsf.api.common.view.ViewDescriptorImpl;
import de.cuioss.tools.net.UrlParameter;
import org.junit.jupiter.api.Test;

import java.util.Collections;

class ViewMatcherImplTest {

    private static final String FACES_CONTENT = "/content/";

    private static final String FACES_GUEST = "/guest/";

    private static final String FACES_NOT_THERE = "/not/there";

    private static final String LOGIN_VIEW_PATH = FACES_GUEST + "login.jsf";

    private static final ViewDescriptor LOGIN_VIEW = ViewDescriptorImpl.builder().withViewId(LOGIN_VIEW_PATH)
            .withUrlParameter(immutableList(new UrlParameter("somePara", "someValue")))
            .withLogicalViewId(LOGIN_VIEW_PATH).build();

    private static final String LOGOUT_VIEW_PATH = FACES_GUEST + "logout.jsf";

    private static final ViewDescriptor LOGOUT_VIEW = ViewDescriptorImpl.builder().withViewId(LOGOUT_VIEW_PATH)
            .withUrlParameter(Collections.emptyList()).withLogicalViewId(LOGOUT_VIEW_PATH).build();

    private static final String CONTENT_VIEW_PATH = FACES_CONTENT + "content.jsf";

    private static final ViewDescriptor CONTENT_VIEW = ViewDescriptorImpl.builder().withViewId(CONTENT_VIEW_PATH)
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

    @Test
    void shouldFAilOnNullMatchList() {
        assertThrows(NullPointerException.class, () -> new ViewMatcherImpl(null));
    }
}
