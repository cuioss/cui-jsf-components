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
package de.cuioss.jsf.bootstrap.widget;

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.jsf.api.components.support.DummyComponent;
import de.cuioss.test.juli.junit5.EnableTestLogger;
import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.contracts.VerifyBeanProperty;
import jakarta.faces.event.ActionEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@VerifyBeanProperty(exclude = {"primaryActionTitle", "notificationBoxState", "primaryAction", "notificationBoxValue",
        "title", "content", "disableCoreAction", "disablePrimaryAction", "rendered", "compositeComponentId",
        "coreAction", "renderPrimaryAction", "initialized", "renderContent", "id", "titleIcon", "titleValue",
        "noItemsMessage", "renderShowMoreButton", "items"})
@EnableTestLogger
@DisplayName("Tests for BaseDeferredLoadingListItemWidget")
class BaseDeferredLoadingListItemWidgetTest extends ValueObjectTest<BaseDeferredLoadingListItemWidgetMock> {

    @Nested
    @DisplayName("Tests for initialization")
    class InitializationTests {

        @Test
        @DisplayName("Should handle initialization correctly")
        void shouldHandleInitialized() {
            // Arrange
            var sut = anyValueObject();

            // Assert initial state
            assertFalse(sut.isInitialized(), "Widget should not be initialized initially");
            assertFalse(sut.isRenderShowMoreButton(), "Show more button should not be rendered initially");

            // Act
            sut.processAction(new ActionEvent(new DummyComponent()));

            // Assert after initialization
            assertTrue(sut.isInitialized(), "Widget should be initialized after processing action");
            assertTrue(sut.isRenderShowMoreButton(), "Show more button should be rendered after initialization");
        }
    }

    @Nested
    @DisplayName("Tests for widget properties")
    class PropertyTests {

        @Test
        @DisplayName("Should return correct composite component ID")
        void shouldReturnCorrectCompositeComponentId() {
            // Arrange
            var sut = anyValueObject();

            // Act & Assert
            assertEquals("cui-composite:listItemWidget", sut.getCompositeComponentId(),
                    "Composite component ID should match expected value");
        }

        @Test
        @DisplayName("Should return items from content")
        void shouldReturnItemsFromContent() {
            // Arrange
            var sut = anyValueObject();
            sut.processAction(new ActionEvent(new DummyComponent())); // Initialize to populate content

            // Act & Assert
            assertNotNull(sut.getItems(), "Items should not be null after initialization");
            assertEquals(sut.getContent(), sut.getItems(), "Items should be the same as content");
        }
    }
}
