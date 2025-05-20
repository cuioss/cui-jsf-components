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
package de.cuioss.jsf.bootstrap.icon.support;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import de.cuioss.jsf.api.components.css.ContextState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for IconState enum")
class IconStateTest {

    @Nested
    @DisplayName("Basic functionality tests")
    class BasicFunctionalityTests {

        @Test
        @DisplayName("Should return DEFAULT when null is provided")
        void shouldReturnDefaultForNullInput() {
            // Act
            var result = IconState.getForContextState(null);

            // Assert
            assertEquals(IconState.DEFAULT, result,
                    "Should return DEFAULT for null input");
            assertNotNull(result.getStyleClassBuilder(),
                    "StyleClassBuilder should not be null");
            assertNotNull(result.getStyleClass(),
                    "StyleClass should not be null");
        }
    }

    @Nested
    @DisplayName("Mapping tests")
    class MappingTests {

        @Test
        @DisplayName("Should correctly map each ContextState to corresponding IconState with proper style class")
        void shouldMapContextStatesToIconStatesCorrectly() {
            // Arrange & Act & Assert - DEFAULT
            assertEquals(IconState.DEFAULT, IconState.getForContextState(ContextState.DEFAULT),
                    "Should map DEFAULT ContextState to DEFAULT IconState");
            assertEquals("", IconState.getForContextState(ContextState.DEFAULT).getStyleClass(),
                    "DEFAULT IconState should have empty style class");

            // Arrange & Act & Assert - DANGER
            assertEquals(IconState.DANGER, IconState.getForContextState(ContextState.DANGER),
                    "Should map DANGER ContextState to DANGER IconState");
            assertEquals("cui-icon-state-danger", IconState.getForContextState(ContextState.DANGER).getStyleClass(),
                    "DANGER IconState should have 'cui-icon-state-danger' style class");

            // Arrange & Act & Assert - INFO
            assertEquals(IconState.INFO, IconState.getForContextState(ContextState.INFO),
                    "Should map INFO ContextState to INFO IconState");
            assertEquals("cui-icon-state-info", IconState.getForContextState(ContextState.INFO).getStyleClass(),
                    "INFO IconState should have 'cui-icon-state-info' style class");

            // Arrange & Act & Assert - PRIMARY
            assertEquals(IconState.PRIMARY, IconState.getForContextState(ContextState.PRIMARY),
                    "Should map PRIMARY ContextState to PRIMARY IconState");
            assertEquals("cui-icon-state-primary", IconState.getForContextState(ContextState.PRIMARY).getStyleClass(),
                    "PRIMARY IconState should have 'cui-icon-state-primary' style class");

            // Arrange & Act & Assert - SUCCESS
            assertEquals(IconState.SUCCESS, IconState.getForContextState(ContextState.SUCCESS),
                    "Should map SUCCESS ContextState to SUCCESS IconState");
            assertEquals("cui-icon-state-success", IconState.getForContextState(ContextState.SUCCESS).getStyleClass(),
                    "SUCCESS IconState should have 'cui-icon-state-success' style class");

            // Arrange & Act & Assert - WARNING
            assertEquals(IconState.WARNING, IconState.getForContextState(ContextState.WARNING),
                    "Should map WARNING ContextState to WARNING IconState");
            assertEquals("cui-icon-state-warning", IconState.getForContextState(ContextState.WARNING).getStyleClass(),
                    "WARNING IconState should have 'cui-icon-state-warning' style class");
        }
    }
}
