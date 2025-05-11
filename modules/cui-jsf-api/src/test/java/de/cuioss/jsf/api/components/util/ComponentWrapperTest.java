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
package de.cuioss.jsf.api.components.util;

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.junit5.EnableJsfEnvironment;
import jakarta.faces.component.UIViewRoot;
import jakarta.faces.component.behavior.AjaxBehavior;
import jakarta.faces.component.html.HtmlInputText;
import jakarta.faces.component.html.HtmlOutputText;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@EnableJsfEnvironment
@DisplayName("Tests for ComponentWrapper")
class ComponentWrapperTest {

    private static final String ID = "id";

    private static final String EXTENSION = "extension";

    private static final String EXTENSION_SUFFIX = "_" + EXTENSION;

    @Nested
    @DisplayName("Tests for client behavior handling")
    class ClientBehaviorTests {

        @Test
        @DisplayName("Should correctly identify and handle client behavior holders")
        void shouldIdentifyAndHandleClientBehaviorHolders() {
            // Arrange
            var wrapper = new ComponentWrapper<>(new HtmlInputText());

            // Act & Assert - initial state
            assertTrue(wrapper.isClientBehaviorHolder(),
                    "HtmlInputText should be identified as a client behavior holder");

            // Act & Assert - cached result
            assertTrue(wrapper.isClientBehaviorHolder(),
                    "Result should be cached and remain true on second call");

            // Act & Assert - empty behaviors
            assertTrue(wrapper.getClientBehaviors().isEmpty(),
                    "Client behaviors should be empty initially");

            // Arrange - add behavior after cache is created
            wrapper.getWrapped().addClientBehavior("click", new AjaxBehavior());

            // Act & Assert - behaviors after modification
            assertTrue(wrapper.getClientBehaviors().isEmpty(),
                    "Client behaviors should still be empty due to caching");

            // Act & Assert - client ID rendering
            assertFalse(wrapper.shouldRenderClientId(),
                    "Should not render client ID when no ID is set");
        }

        @Test
        @DisplayName("Should correctly handle components that are not client behavior holders")
        void shouldHandleNonClientBehaviorHolders() {
            // Arrange
            var wrapper = new ComponentWrapper<>(new HtmlOutputText());

            // Act & Assert - behavior holder check
            assertFalse(wrapper.isClientBehaviorHolder(),
                    "HtmlOutputText should not be identified as a client behavior holder");

            // Act & Assert - client ID rendering without ID
            assertFalse(wrapper.shouldRenderClientId(),
                    "Should not render client ID when no ID is set and not a behavior holder");

            // Arrange - set generated ID
            wrapper.getWrapped().setId(UIViewRoot.UNIQUE_ID_PREFIX);

            // Act & Assert - client ID rendering with generated ID
            assertFalse(wrapper.shouldRenderClientId(),
                    "Should not render client ID when ID is generated");

            // Arrange - set explicit ID
            wrapper.getWrapped().setId(ID);

            // Act & Assert - client ID rendering with explicit ID
            assertTrue(wrapper.shouldRenderClientId(),
                    "Should render client ID when explicit ID is set");
        }
    }

    @Nested
    @DisplayName("Tests for client ID manipulation")
    class ClientIdTests {

        @Test
        @DisplayName("Should correctly suffix client IDs")
        void shouldSuffixClientIds() {
            // Arrange
            var wrapper = new ComponentWrapper<>(new HtmlOutputText());
            wrapper.getWrapped().setId(ID);

            // Act & Assert - with extension
            assertEquals(ID + EXTENSION_SUFFIX, wrapper.getSuffixedClientId(EXTENSION),
                    "Should append suffix to client ID with underscore separator");

            // Act & Assert - with null extension
            assertEquals(ID, wrapper.getSuffixedClientId(null),
                    "Should return original client ID when suffix is null");
        }
    }

    @BeforeEach
    void configureComponents(final ComponentConfigDecorator decorator) {
        decorator.registerMockRendererForHtmlOutputText();
    }
}