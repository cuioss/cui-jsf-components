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
package de.cuioss.jsf.components.typewatch;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.cuioss.test.jsf.component.AbstractComponentTest;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import jakarta.faces.component.html.HtmlInputText;
import jakarta.faces.component.html.HtmlPanelGroup;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.faces.event.PostAddToViewEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@VerifyComponentProperties(of = {"process", "update", "allowSubmit", "wait", "highlight", "captureLength"})
@DisplayName("Tests for TypewatchComponent")
class TypewatchComponentTest extends AbstractComponentTest<TypewatchComponent> {

    private HtmlInputText parent;

    private ComponentSystemEvent expectedEvent;

    @BeforeEach
    @DisplayName("Set up test environment")
    void setUp(ComponentConfigDecorator decorator) {
        // Register mock renderer for HtmlInputText to support the test
        decorator.registerMockRendererForHtmlInputText();
    }

    @Override
    @DisplayName("Configure component for testing")
    public void configure(final TypewatchComponent toBeConfigured) {
        // Call parent configuration
        super.configure(toBeConfigured);

        // Create parent component structure
        var panel = new HtmlPanelGroup();
        parent = new HtmlInputText();
        panel.getChildren().add(parent);

        // Set parent for the typewatch component
        toBeConfigured.setParent(parent);

        // Create event for testing
        expectedEvent = new PostAddToViewEvent(toBeConfigured);
    }

    @Nested
    @DisplayName("Component decoration tests")
    class DecorationTests {

        @Test
        @DisplayName("Should decorate with minimal configuration")
        void shouldDecorateMinimal() {
            // Arrange
            var underTest = anyComponent();

            // Verify initial state
            assertEquals(1, parent.getParent().getChildren().size(), "Parent should have one child");
            assertEquals(0, parent.getParent().getChildren().getFirst().getPassThroughAttributes().size(),
                    "No pass-through attributes should be set initially");

            // Act
            underTest.processEvent(expectedEvent);

            // Assert
            assertEquals(1, parent.getParent().getChildren().size(), "Parent should still have one child");
            final var pt = parent.getParent().getChildren().getFirst().getPassThroughAttributes();
            assertEquals("data-typewatch", pt.get("data-typewatch"), "Should have data-typewatch attribute");
            assertEquals(false, pt.get("data-typewatch-allowsubmit"), "Allow submit should be false by default");
            assertEquals(false, pt.get("data-typewatch-highlight"), "Highlight should be false by default");
        }

        @Test
        @DisplayName("Should decorate with maximal configuration")
        void shouldDecorateMaximal() {
            // Arrange
            var underTest = anyComponent();
            underTest.setAllowSubmit(true);
            underTest.setWait(666);
            underTest.setHighlight(true);
            underTest.setCaptureLength(42);

            // Act
            underTest.processEvent(expectedEvent);

            // Assert
            final var pt = parent.getParent().getChildren().getFirst().getPassThroughAttributes();
            assertEquals("data-typewatch", pt.get("data-typewatch"), "Should have data-typewatch attribute");
            assertEquals(true, pt.get("data-typewatch-allowsubmit"), "Allow submit should be true");
            assertEquals(666, pt.get("data-typewatch-wait"), "Wait should be 666");
            assertEquals(true, pt.get("data-typewatch-highlight"), "Highlight should be true");
            assertEquals(42, pt.get("data-typewatch-capturelength"), "Capture length should be 42");
        }
    }
}
