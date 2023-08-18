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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.myfaces.test.mock.MockHttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.cuioss.test.jsf.junit5.JsfEnabledTestEnvironment;
import de.cuioss.test.jsf.mocks.CuiMockConfigurableNavigationHandler;

class BaseDelegatingNavigationHandlerTest extends JsfEnabledTestEnvironment {

    private static final String SOME = "some";

    private CuiMockConfigurableNavigationHandler mockNavigationHandler;

    @BeforeEach
    void before() {
        mockNavigationHandler = getApplicationConfigDecorator().getMockNavigationHandler();
    }

    @Test
    void shouldDelegateToNavigationHandler() {
        var underTest = new BaseDelegatingNavigationHandler(mockNavigationHandler);
        assertNull(underTest.getNavigationCase(getFacesContext(), null, null));
        assertNull(underTest.getNavigationCase(getFacesContext(), null, null, null));
        underTest.getNavigationCases();

        underTest.handleNavigation(getFacesContext(), null, null);
        assertTrue(mockNavigationHandler.isHandleNavigationCalled());

        getEnvironmentHolder().getExternalContext().setResponse(new MockHttpServletResponse());

        underTest.handleNavigation(getFacesContext(), null, null, null);
        assertTrue(mockNavigationHandler.isHandleNavigationCalled());
    }

    @Test
    void shouldDelegateToConfigurableNavigationHandler() {
        var underTest = new BaseDelegatingNavigationHandler(mockNavigationHandler);
        underTest.getNavigationCase(getFacesContext(), null, null);
        underTest.getNavigationCase(getFacesContext(), null, null, null);
        underTest.getNavigationCases();

        underTest.handleNavigation(getFacesContext(), SOME, SOME);
        assertTrue(mockNavigationHandler.isHandleNavigationCalled());
        assertFalse(mockNavigationHandler.isAddNavigationCalled());
        assertTrue(mockNavigationHandler.isGetNavigationCaseCalled());

        getEnvironmentHolder().getExternalContext().setResponse(new MockHttpServletResponse());

        underTest.handleNavigation(getFacesContext(), SOME, SOME, SOME);
        assertTrue(mockNavigationHandler.isHandleNavigationCalled());
        assertFalse(mockNavigationHandler.isAddNavigationCalled());
        assertTrue(mockNavigationHandler.isGetNavigationCaseCalled());
    }

    @Test
    void shouldGracefullyHandleNullAsHandler() {
        var underTest = new BaseDelegatingNavigationHandler(null);
        assertNull(underTest.getNavigationCase(getFacesContext(), SOME, SOME));
        assertTrue(underTest.getNavigationCases().isEmpty());
    }
}
