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
package de.cuioss.jsf.api.application.navigation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.common.view.ViewDescriptor;
import de.cuioss.jsf.api.common.view.ViewDescriptorImpl;
import de.cuioss.test.jsf.junit5.JsfEnabledTestEnvironment;
import de.cuioss.test.jsf.mocks.CuiMockConfigurableNavigationHandler;

class ViewIdentifierTest extends JsfEnabledTestEnvironment {

    private static final String FROM_OUTCOME = "fromOutcome";

    private static final String FROM_ACTION = "fromAction";

    private static final String FROM_VIEW_ID = "fromViewId";

    private static final String START_URL = "/some/page.jsf";

    private CuiMockConfigurableNavigationHandler mockNavigationHandler;

    @BeforeEach
    void before() {
        mockNavigationHandler = getApplicationConfigDecorator().getMockNavigationHandler();
    }

    @Test
    void shouldExtractCorrectViewIdentifier() {
        ViewDescriptor descriptor = ViewDescriptorImpl.builder().withViewId(START_URL).withLogicalViewId(START_URL)
                .build();
        var identifier = ViewIdentifier.getFromViewDesciptor(descriptor, null);
        assertNotNull(identifier);
        assertEquals(START_URL, identifier.getViewId());
        assertNull(identifier.getOutcome());
        final var navigationCase = identifier.toNavigationCase(FROM_VIEW_ID, FROM_ACTION, FROM_OUTCOME, null, true,
                false);
        assertNotNull(navigationCase);
    }

    @Test
    void handleNullUrlParams() {
        assertDoesNotThrow(() -> new ViewIdentifier("some/page.jsf", null, null).redirect(getFacesContext()));
    }
}
