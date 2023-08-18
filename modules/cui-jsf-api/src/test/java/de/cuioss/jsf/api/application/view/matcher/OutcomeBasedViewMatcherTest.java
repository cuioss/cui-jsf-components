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

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.application.navigation.NavigationUtils;
import de.cuioss.jsf.api.common.view.ViewDescriptor;
import de.cuioss.jsf.api.common.view.ViewDescriptorImpl;
import de.cuioss.test.jsf.config.ApplicationConfigurator;
import de.cuioss.test.jsf.config.RequestConfigurator;
import de.cuioss.test.jsf.config.decorator.ApplicationConfigDecorator;
import de.cuioss.test.jsf.config.decorator.RequestConfigDecorator;
import de.cuioss.test.jsf.junit5.JsfEnabledTestEnvironment;

class OutcomeBasedViewMatcherTest extends JsfEnabledTestEnvironment
        implements ApplicationConfigurator, RequestConfigurator {

    private static final String CONTEXT_PATH = "contextPath";

    /** The home navigation view */
    private static final String VIEW_HOME = "/faces/portal/home.jsf";

    private static final String OUTCOME_HOME = "home";

    /** The home navigation view */
    private static final String VIEW_NAVIGATED = "/faces/portal/navigated.jsf";

    private static final String OUTCOME_NAVIGATED = "navigate";

    @Override
    public void configureApplication(final ApplicationConfigDecorator decorator) {
        decorator.registerNavigationCase(OUTCOME_HOME, VIEW_HOME)
                .registerNavigationCase(OUTCOME_NAVIGATED, VIEW_NAVIGATED).setContextPath(CONTEXT_PATH);
    }

    @Override
    public void configureRequest(final RequestConfigDecorator decorator) {
        decorator.setViewId(VIEW_HOME);
    }

    @Test
    void shouldHandleRegisteredView() {
        var matcher = new OutcomeBasedViewMatcher(OUTCOME_HOME);
        var current = NavigationUtils.getCurrentView(getFacesContext());
        assertTrue(matcher.match(current));
        assertTrue(matcher.match(current));
        ViewDescriptor navigate = ViewDescriptorImpl.builder().withLogicalViewId(VIEW_NAVIGATED).build();
        assertFalse(matcher.match(navigate));
    }
}
