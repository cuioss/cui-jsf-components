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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.cuioss.jsf.api.application.navigation.NavigationUtils;
import de.cuioss.jsf.api.common.view.ViewDescriptor;
import de.cuioss.jsf.api.common.view.ViewDescriptorImpl;
import de.cuioss.test.jsf.config.decorator.ApplicationConfigDecorator;
import de.cuioss.test.jsf.config.decorator.RequestConfigDecorator;
import de.cuioss.test.jsf.junit5.EnableJsfEnvironment;
import jakarta.faces.context.FacesContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@EnableJsfEnvironment
@DisplayName("Tests for OutcomeBasedViewMatcher")
class OutcomeBasedViewMatcherTest {

    private static final String CONTEXT_PATH = "contextPath";

    /** The home navigation view */
    private static final String VIEW_HOME = "/portal/home.jsf";

    private static final String OUTCOME_HOME = "home";

    /** The navigated view */
    private static final String VIEW_NAVIGATED = "/portal/navigated.jsf";

    private static final String OUTCOME_NAVIGATED = "navigate";

    @BeforeEach
    void setUp(ApplicationConfigDecorator applicationConfig, RequestConfigDecorator requestConfig) {
        applicationConfig.registerNavigationCase(OUTCOME_HOME, VIEW_HOME)
                .registerNavigationCase(OUTCOME_NAVIGATED, VIEW_NAVIGATED).setContextPath(CONTEXT_PATH);
        requestConfig.setViewId(VIEW_HOME);
    }

    @Nested
    @DisplayName("Tests for match method")
    class MatchTests {

        @Test
        @DisplayName("Should correctly match views based on outcome")
        void shouldMatchViewsBasedOnOutcome(FacesContext facesContext) {
            // Arrange
            var matcher = new OutcomeBasedViewMatcher(OUTCOME_HOME);
            var current = NavigationUtils.getCurrentView(facesContext);

            // Act & Assert - should match current view with home outcome
            assertTrue(matcher.match(current),
                    "Matcher should match view with the same outcome");

            // Act & Assert - verify consistent matching behavior
            assertTrue(matcher.match(current),
                    "Matcher should consistently match the same view");

            // Arrange - create a different view
            ViewDescriptor navigate = ViewDescriptorImpl.builder()
                    .withLogicalViewId(VIEW_NAVIGATED)
                    .build();

            // Act & Assert - should not match different view
            assertFalse(matcher.match(navigate),
                    "Matcher should not match view with different outcome");
        }
    }
}
