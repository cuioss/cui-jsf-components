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
package de.cuioss.jsf.bootstrap.layout.input;

import static de.cuioss.jsf.bootstrap.layout.input.HelpTextComponent.FIXED_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;

import de.cuioss.jsf.api.components.JsfComponentIdentifier;
import de.cuioss.jsf.bootstrap.button.CommandButton;
import de.cuioss.jsf.bootstrap.layout.messages.CuiMessageComponent;
import de.cuioss.jsf.bootstrap.layout.messages.CuiMessageRenderer;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.component.AbstractComponentTest;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link HelpTextComponent} using the property verification from
 * {@link AbstractComponentTest}.
 */
@VerifyComponentProperties(of = {"titleKey", "titleValue", "contentKey", "contentValue", "contentEscape",
        "buttonAlign", "renderButton"})
@JsfTestConfiguration(CoreJsfTestConfiguration.class)
@DisplayName("Tests for HelpTextComponent")
class HelpTextComponentTest extends AbstractComponentTest<HelpTextComponent> {

    @BeforeEach
    void configureComponents(ComponentConfigDecorator decorator) {
        decorator.registerUIComponent(CommandButton.class)
                .registerUIComponent(CuiMessageComponent.class)
                .registerRenderer(CuiMessageRenderer.class)
                .registerRenderer(LabeledContainerRenderer.class)
                .registerMockRendererForHtmlInputText();
    }

    @Override
    public void configure(HelpTextComponent toBeConfigured) {
        // Set up parent component
        var parent = new LabeledContainerComponent();
        parent.setId("labeledContainer");
        parent.getChildren().add(toBeConfigured);
    }

    @Nested
    @DisplayName("Tests for component properties")
    class PropertyTests {

        @Test
        @DisplayName("Should provide correct component metadata")
        void shouldProvideMetadata() {
            // Arrange
            var component = anyComponent();

            // Act & Assert
            assertEquals(JsfComponentIdentifier.INPUT_FAMILY, component.getFamily(),
                    "Component family should match INPUT_FAMILY");
            assertEquals(JsfComponentIdentifier.HIDDEN_RENDERER_TYPE, component.getRendererType(),
                    "Renderer type should match HIDDEN_RENDERER_TYPE");
        }

        @Test
        @DisplayName("Should have fixed ID attribute")
        void shouldProvideFixedAttributes() {
            // Arrange
            var component = anyComponent();

            // Act & Assert
            assertEquals(FIXED_ID, component.getId(),
                    "Component ID should match FIXED_ID constant");
        }
    }
}
