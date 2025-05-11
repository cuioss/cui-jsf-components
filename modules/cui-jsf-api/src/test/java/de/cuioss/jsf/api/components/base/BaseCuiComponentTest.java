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
package de.cuioss.jsf.api.components.base;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import de.cuioss.test.jsf.component.AbstractComponentTest;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@VerifyComponentProperties(of = {"styleClass", "rendered", "style"})
@DisplayName("Tests for BaseCuiComponent")
class BaseCuiComponentTest extends AbstractComponentTest<MockCuiComponent> {

    @Test
    @DisplayName("Should provide access to FacesContext through bridge")
    void shouldBridgeFacesContext() {
        // Arrange
        var component = anyComponent();

        // Act & Assert
        assertNotNull(component.facesContext(),
                "FacesContext should be accessible through the component bridge");
    }
}
