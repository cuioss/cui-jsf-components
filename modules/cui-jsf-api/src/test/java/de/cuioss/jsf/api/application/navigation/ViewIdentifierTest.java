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

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.jsf.api.common.view.ViewDescriptor;
import de.cuioss.jsf.api.common.view.ViewDescriptorImpl;
import de.cuioss.test.jsf.junit5.EnableJsfEnvironment;
import jakarta.faces.context.FacesContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@EnableJsfEnvironment
@DisplayName("Tests for ViewIdentifier")
class ViewIdentifierTest {

    private static final String FROM_OUTCOME = "fromOutcome";

    private static final String FROM_ACTION = "fromAction";

    private static final String FROM_VIEW_ID = "fromViewId";

    private static final String START_URL = "/some/page.jsf";

    @Test
    @DisplayName("Should extract correct view identifier from ViewDescriptor")
    void shouldExtractCorrectViewIdentifierFromViewDescriptor() {
        // Arrange
        ViewDescriptor descriptor = ViewDescriptorImpl.builder()
                .withViewId(START_URL)
                .withLogicalViewId(START_URL)
                .build();

        // Act
        var identifier = ViewIdentifier.getFromViewDesciptor(descriptor, null);

        // Assert
        assertNotNull(identifier, "ViewIdentifier should not be null");
        assertEquals(START_URL, identifier.getViewId(), "ViewId should match the start URL");
        assertNull(identifier.getOutcome(), "Outcome should be null");

        // Act - convert to navigation case
        final var navigationCase = identifier.toNavigationCase(FROM_VIEW_ID, FROM_ACTION, FROM_OUTCOME, null, true,
                false);

        // Assert
        assertNotNull(navigationCase, "NavigationCase should not be null");
    }

    @Test
    @DisplayName("Should handle null URL parameters without throwing exceptions")
    void shouldHandleNullUrlParametersGracefully(FacesContext facesContext) {
        // Act & Assert
        assertDoesNotThrow(() -> new ViewIdentifier("some/page.jsf", null, null).redirect(facesContext),
                "Should not throw exception when URL parameters are null");
    }
}
