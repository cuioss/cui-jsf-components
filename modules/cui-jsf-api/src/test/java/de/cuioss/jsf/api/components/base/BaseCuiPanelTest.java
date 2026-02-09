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

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.cuioss.jsf.api.components.base.BaseCuiPanel.PanelType;
import de.cuioss.test.jsf.component.AbstractComponentTest;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@VerifyComponentProperties(of = {"styleClass", "rendered", "style"})
@DisplayName("Tests for BaseCuiPanel")
class BaseCuiPanelTest extends AbstractComponentTest<BaseCuiPanel> {

    @Nested
    @DisplayName("Tests for panel layout configuration")
    class LayoutConfigurationTests {

        @Test
        @DisplayName("Should create panel with correct layout based on panel type")
        void shouldCreateWithCorrectLayoutBasedOnType() {
            // Arrange & Act & Assert - testing DIV type
            assertEquals("block", new BaseCuiPanel(PanelType.DIV).getLayout(),
                    "DIV panel type should have 'block' layout");

            // Arrange & Act & Assert - testing SPAN type
            assertEquals("span", new BaseCuiPanel(PanelType.SPAN).getLayout(),
                    "SPAN panel type should have 'span' layout");

            // Arrange & Act & Assert - testing default constructor
            assertEquals("block", new BaseCuiPanel().getLayout(),
                    "Default panel should have 'block' layout");
        }
    }
}
