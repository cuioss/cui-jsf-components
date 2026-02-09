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
package de.cuioss.jsf.api.application.view.matcher;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;
import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.jsf.api.common.view.ViewDescriptor;
import de.cuioss.jsf.api.common.view.ViewDescriptorImpl;
import de.cuioss.tools.net.UrlParameter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collections;

@DisplayName("Tests for ViewMatcherImpl")
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

    @Nested
    @DisplayName("Tests for match method with different patterns")
    class MatchTests {

        @Test
        @DisplayName("Should match views in guest directory")
        void shouldMatchViewsInGuestDirectory() {
            // Arrange
            var underTest = new ViewMatcherImpl(immutableList(LOGIN_VIEW_PATH, FACES_GUEST));

            // Act & Assert - login view should match
            assertTrue(underTest.match(LOGIN_VIEW),
                    "Matcher should match login view with explicit path and directory pattern");

            // Act & Assert - logout view should match due to directory pattern
            assertTrue(underTest.match(LOGOUT_VIEW),
                    "Matcher should match logout view due to guest directory pattern");

            // Act & Assert - content view should not match
            assertFalse(underTest.match(CONTENT_VIEW),
                    "Matcher should not match content view outside guest directory");
        }

        @Test
        @DisplayName("Should match views in content directory")
        void shouldMatchViewsInContentDirectory() {
            // Arrange
            var underTest = new ViewMatcherImpl(immutableList(FACES_CONTENT));

            // Act & Assert - login view should not match
            assertFalse(underTest.match(LOGIN_VIEW),
                    "Matcher should not match login view outside content directory");

            // Act & Assert - logout view should not match
            assertFalse(underTest.match(LOGOUT_VIEW),
                    "Matcher should not match logout view outside content directory");

            // Act & Assert - content view should match
            assertTrue(underTest.match(CONTENT_VIEW),
                    "Matcher should match content view in content directory");
        }

        @Test
        @DisplayName("Should match all views with wildcard pattern")
        void shouldMatchAllViewsWithWildcard() {
            // Arrange
            var underTest = new ViewMatcherImpl(immutableList("/"));

            // Act & Assert - all views should match with wildcard
            assertTrue(underTest.match(LOGIN_VIEW),
                    "Wildcard matcher should match login view");
            assertTrue(underTest.match(LOGOUT_VIEW),
                    "Wildcard matcher should match logout view");
            assertTrue(underTest.match(CONTENT_VIEW),
                    "Wildcard matcher should match content view");
        }

        @Test
        @DisplayName("Should not match any views with empty patterns")
        void shouldNotMatchAnyViewsWithEmptyPatterns() {
            // Arrange
            var underTest = new ViewMatcherImpl(immutableList("", "  "));

            // Act & Assert - no views should match with empty patterns
            assertFalse(underTest.match(LOGIN_VIEW),
                    "Empty pattern matcher should not match login view");
            assertFalse(underTest.match(LOGOUT_VIEW),
                    "Empty pattern matcher should not match logout view");
            assertFalse(underTest.match(CONTENT_VIEW),
                    "Empty pattern matcher should not match content view");
        }

        @Test
        @DisplayName("Should not match any views with non-existent path")
        void shouldNotMatchAnyViewsWithNonExistentPath() {
            // Arrange
            var underTest = new ViewMatcherImpl(immutableList(FACES_NOT_THERE));

            // Act & Assert - no views should match with non-existent path
            assertFalse(underTest.match(LOGIN_VIEW),
                    "Non-existent path matcher should not match login view");
            assertFalse(underTest.match(LOGOUT_VIEW),
                    "Non-existent path matcher should not match logout view");
            assertFalse(underTest.match(CONTENT_VIEW),
                    "Non-existent path matcher should not match content view");
        }
    }

    @Nested
    @DisplayName("Tests for constructor validation")
    class ConstructorTests {

        @Test
        @DisplayName("Should throw NullPointerException for null match list")
        void shouldThrowExceptionForNullMatchList() {
            // Act & Assert
            assertThrows(NullPointerException.class, () -> new ViewMatcherImpl(null),
                    "Constructor should reject null match list");
        }
    }
}
