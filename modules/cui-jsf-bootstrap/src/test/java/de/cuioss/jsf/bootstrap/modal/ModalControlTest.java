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
package de.cuioss.jsf.bootstrap.modal;

import static de.cuioss.jsf.bootstrap.modal.ModalControl.*;
import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.jsf.api.components.JsfHtmlComponent;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.component.AbstractComponentTest;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import jakarta.faces.component.html.HtmlOutcomeTargetButton;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.faces.event.PostAddToViewEvent;
import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
@VerifyComponentProperties(of = {"action", "event", "for"}, defaultValued = {"action", "event"})
@DisplayName("Tests for ModalControl")
class ModalControlTest extends AbstractComponentTest<ModalControl> {

    @BeforeEach
    void configureCuiComponents(ComponentConfigDecorator decorator) {
        CoreJsfTestConfiguration.configureComponents(decorator);
    }

    private static final String FOR_ID = "forId";

    @Getter
    private ModalControl underTest;

    private HtmlOutcomeTargetButton parent;

    private ComponentSystemEvent expectedEvent;

    @BeforeEach
    void before(FacesContext facesContext) {
        underTest = new ModalControl();
        parent = JsfHtmlComponent.createComponent(facesContext, JsfHtmlComponent.BUTTON);
        underTest.setParent(parent);
        underTest.setFor(FOR_ID);
        expectedEvent = new PostAddToViewEvent(underTest);
    }

    @Nested
    @DisplayName("Tests for attribute handling")
    class AttributeHandlingTests {

        @Test
        @DisplayName("Should decorate parent with default attributes")
        void shouldDecorateWithDefaultAttributes() {
            // Arrange
            assertTrue(parent.getPassThroughAttributes(true).isEmpty());

            // Act
            underTest.processEvent(expectedEvent);

            // Assert
            final var attributes = parent.getPassThroughAttributes();
            assertEquals(3, attributes.size(), "Should have 3 pass-through attributes");
            assertEquals(FOR_ID, attributes.get(DATA_FOR), "DATA_FOR attribute should match FOR_ID");
            assertEquals(DEFAULT_ACTION, attributes.get(DATA_ACTION), "DATA_ACTION attribute should match DEFAULT_ACTION");
            assertEquals(DEFAULT_EVENT, attributes.get(DATA_EVENT), "DATA_EVENT attribute should match DEFAULT_EVENT");
        }
    }

    @Nested
    @DisplayName("Tests for validation")
    class ValidationTests {

        @Test
        @DisplayName("Should throw exception when 'for' attribute is missing")
        void shouldFailOnMissingForIdentifier() {
            // Arrange
            underTest.setFor(null);

            // Act & Assert
            assertThrows(IllegalArgumentException.class,
                    () -> underTest.processEvent(expectedEvent),
                    "Should throw IllegalArgumentException when 'for' attribute is missing");
        }
    }
}
