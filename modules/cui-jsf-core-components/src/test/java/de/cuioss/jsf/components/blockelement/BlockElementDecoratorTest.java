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
package de.cuioss.jsf.components.blockelement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import de.cuioss.jsf.api.components.base.BaseCuiCommandButton;
import de.cuioss.jsf.api.components.util.modifier.ComponentModifierFactory;
import de.cuioss.test.jsf.component.AbstractComponentTest;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.faces.event.PostAddToViewEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for BlockElementDecorator")
class BlockElementDecoratorTest extends AbstractComponentTest<BlockElementDecorator> {

    private BaseCuiCommandButton parent;

    private ComponentSystemEvent expectedEvent;

    @Nested
    @DisplayName("Decorator Functionality Tests")
    class DecoratorTests {

        @Test
        @DisplayName("Should add block element attribute to parent component")
        void shouldDecorate() {
            // Arrange
            var underTest = anyComponent();
            assertNull(parent.getPassThroughAttributes().get("data-cui-block-element"),
                    "Attribute should not be present before decoration");

            // Act
            underTest.processEvent(expectedEvent);

            // Assert
            assertEquals("data-cui-block-element", parent.getPassThroughAttributes().get("data-cui-block-element"),
                    "Block element attribute should be added to parent component");
        }

        @Test
        @DisplayName("Should correctly implement decorate method")
        void shouldImplementDecorateMethod() {
            // Arrange
            var underTest = anyComponent();
            var modifier = ComponentModifierFactory.findFittingWrapper(parent);

            // Act
            underTest.decorate(modifier);

            // Assert
            assertEquals("data-cui-block-element", parent.getPassThroughAttributes().get("data-cui-block-element"),
                    "Block element attribute should be added via decorate method");
        }
    }

    @Override
    public void configure(final BlockElementDecorator toBeConfigured) {
        super.configure(toBeConfigured);
        parent = new BaseCuiCommandButton();
        toBeConfigured.setParent(parent);
        expectedEvent = new PostAddToViewEvent(toBeConfigured);
    }
}
