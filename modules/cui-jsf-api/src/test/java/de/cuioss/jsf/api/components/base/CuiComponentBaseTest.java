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
package de.cuioss.jsf.api.components.base;

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.test.jsf.component.AbstractComponentTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for CuiComponentBase")
class CuiComponentBaseTest extends AbstractComponentTest<CuiComponentBase> {

    @Nested
    @DisplayName("Tests for bridge functionality")
    class BridgeFunctionalityTests {

        @Test
        @DisplayName("Should provide access to JSF infrastructure")
        void shouldProvideAccessToJsfInfrastructure() {
            // Arrange
            var component = anyComponent();

            // Act & Assert - FacesContext access
            assertNotNull(component.facesContext(),
                    "Component should provide access to FacesContext");

            // Act & Assert - Facet access
            assertNull(component.facet("notThere"),
                    "Component should return null for non-existent facet");

            // Act & Assert - StateHelper access
            assertNotNull(component.stateHelper(),
                    "Component should provide access to StateHelper");

            // Act & Assert - Component family
            assertEquals(CuiComponentBase.COMPONENT_FAMILY, component.getFamily(),
                    "Component should return the correct component family");
        }
    }
}
