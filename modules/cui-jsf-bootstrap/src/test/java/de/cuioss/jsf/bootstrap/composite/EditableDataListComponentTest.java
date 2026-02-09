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
package de.cuioss.jsf.bootstrap.composite;

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.jsf.api.components.model.datalist.EditStatus;
import de.cuioss.jsf.api.components.model.datalist.ItemWrapper;
import de.cuioss.jsf.api.components.model.datalist.impl.ItemWrapperImpl;
import de.cuioss.jsf.api.converter.ObjectToStringConverter;
import de.cuioss.test.jsf.component.AbstractComponentTest;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import jakarta.faces.component.html.HtmlInputText;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for EditableDataListComponent")
class EditableDataListComponentTest extends AbstractComponentTest<EditableDataListComponent> {

    @Nested
    @DisplayName("Style class tests")
    class StyleClassTests {

        @Test
        @DisplayName("Should render basic and custom style classes")
        void shouldRenderStyleClass() {
            // Arrange
            final var minimal = "list-group";
            final var custom = "customCss";
            var component = anyComponent();

            // Act & Assert - Default style class
            assertEquals(minimal, component.getWrapperStyleClass(),
                    "Default style class should be 'list-group'");

            // Arrange - Add custom style class
            component.getAttributes().put("styleClass", custom);

            // Act & Assert - Combined style classes
            assertEquals(minimal + " " + custom, component.getWrapperStyleClass(),
                    "Should combine default and custom style classes");
        }

        @Test
        @DisplayName("Should add required style class when required is true")
        void shouldRenderRequiredStyleClass() {
            // Arrange
            var component = anyComponent();
            component.getAttributes().put("required", true);

            // Act & Assert
            assertTrue(component.getWrapperStyleClass().contains("required"),
                    "Style class should contain 'required' when required=true");
        }
    }

    @Nested
    @DisplayName("Double-click functionality tests")
    class DoubleClickTests {

        @Test
        @DisplayName("Should determine when double-click edit is enabled or disabled")
        void shouldDetermineDoubleClickEnabled() {
            // Arrange - Setup model and component with double-click enabled
            var model = new MockEditableDataListModel();
            var component = anyComponent();
            component.getAttributes().put("model", model);
            component.getAttributes().put("enableDoubleClickEdit", Boolean.TRUE);

            // Act & Assert - All items saved/canceled
            model.setEveryItemSavedOrCanceled(true);
            assertFalse(component.isEditOnDoubleClickDisabled(),
                    "Double-click should be enabled when enableDoubleClickEdit=true and all items saved/canceled");

            // Act & Assert - Not all items saved/canceled
            model.setEveryItemSavedOrCanceled(false);
            assertTrue(component.isEditOnDoubleClickDisabled(),
                    "Double-click should be disabled when not all items saved/canceled");

            // Arrange - New component with double-click disabled
            component = anyComponent();
            component.getAttributes().put("model", model);
            component.getAttributes().put("enableDoubleClickEdit", Boolean.FALSE);

            // Act & Assert - All items saved/canceled but feature disabled
            model.setEveryItemSavedOrCanceled(true);
            assertTrue(component.isEditOnDoubleClickDisabled(),
                    "Double-click should be disabled when enableDoubleClickEdit=false");

            // Act & Assert - Not all items saved/canceled and feature disabled
            model.setEveryItemSavedOrCanceled(false);
            assertTrue(component.isEditOnDoubleClickDisabled(),
                    "Double-click should be disabled when enableDoubleClickEdit=false");
        }
    }

    @Nested
    @DisplayName("Button visibility tests")
    class ButtonVisibilityTests {

        @Test
        @DisplayName("Should determine delete and undo button visibility based on item status")
        void shouldDetermineDeleteButtonVisibility() {
            // Arrange - Setup model and component with delete enabled
            var model = new MockEditableDataListModel();
            var component = anyComponent();
            component.getAttributes().put("model", model);
            component.getAttributes().put("enableDelete", Boolean.TRUE);
            model.setEveryItemSavedOrCanceled(true);

            // Arrange - Create item in INITIAL state
            ItemWrapper<String> item = new ItemWrapperImpl<>();
            item.setEditStatus(EditStatus.INITIAL);

            // Act & Assert - Initial state with delete enabled
            assertTrue(component.isDeleteButtonVisibleForItem(item),
                    "Delete button should be visible for INITIAL item when delete is enabled");
            assertFalse(component.isUndoButtonVisibleForItem(item),
                    "Undo button should not be visible for INITIAL item");

            // Arrange - Change item to DELETED state
            item.setEditStatus(EditStatus.DELETED);

            // Act & Assert - Deleted state with delete enabled
            assertFalse(component.isDeleteButtonVisibleForItem(item),
                    "Delete button should not be visible for DELETED item");
            assertTrue(component.isUndoButtonVisibleForItem(item),
                    "Undo button should be visible for DELETED item");

            // Arrange - Disable delete functionality
            component.getAttributes().put("enableDelete", Boolean.FALSE);
            item.setEditStatus(EditStatus.INITIAL);

            // Act & Assert - Initial state with delete disabled
            assertFalse(component.isDeleteButtonVisibleForItem(item),
                    "Delete button should not be visible when delete is disabled");
            assertFalse(component.isUndoButtonVisibleForItem(item),
                    "Undo button should not be visible when delete is disabled");
        }

        @Test
        @DisplayName("Should determine when addon buttons are rendered based on item status")
        void shouldDetermineRenderedOfAddonButtons() {
            // Arrange - Setup component with addon buttons enabled
            var underTest = anyComponent();
            underTest.getAttributes().put("renderAddonButtonsInAddMode", Boolean.TRUE);
            underTest.getAttributes().put("renderAddonButtonsInEditMode", Boolean.TRUE);

            // Arrange - Create item in ADDED state
            ItemWrapper<String> item = new ItemWrapperImpl<>("test", EditStatus.ADDED);

            // Act & Assert - ADDED state with addon buttons enabled
            assertTrue(underTest.isAddonButtonsInAddModeRendered(item),
                    "Add mode buttons should be rendered for ADDED item when enabled");
            assertFalse(underTest.isAddonButtonsInEditModeRendered(item),
                    "Edit mode buttons should not be rendered for ADDED item");

            // Arrange - Create item in EDIT state
            item = new ItemWrapperImpl<>("test", EditStatus.EDIT);

            // Act & Assert - EDIT state with addon buttons enabled
            assertFalse(underTest.isAddonButtonsInAddModeRendered(item),
                    "Add mode buttons should not be rendered for EDIT item");
            assertTrue(underTest.isAddonButtonsInEditModeRendered(item),
                    "Edit mode buttons should be rendered for EDIT item when enabled");

            // Arrange - Disable addon buttons
            underTest.getAttributes().put("renderAddonButtonsInAddMode", Boolean.FALSE);
            underTest.getAttributes().put("renderAddonButtonsInEditMode", Boolean.FALSE);

            // Arrange - Create item in ADDED state
            item = new ItemWrapperImpl<>("test", EditStatus.ADDED);

            // Act & Assert - ADDED state with addon buttons disabled
            assertFalse(underTest.isAddonButtonsInAddModeRendered(item),
                    "Add mode buttons should not be rendered when disabled");
            assertFalse(underTest.isAddonButtonsInEditModeRendered(item),
                    "Edit mode buttons should not be rendered when disabled");

            // Arrange - Create item in EDIT state
            item = new ItemWrapperImpl<>("test", EditStatus.EDIT);

            // Act & Assert - EDIT state with addon buttons disabled
            assertFalse(underTest.isAddonButtonsInAddModeRendered(item),
                    "Add mode buttons should not be rendered when disabled");
            assertFalse(underTest.isAddonButtonsInEditModeRendered(item),
                    "Edit mode buttons should not be rendered when disabled");
        }
    }

    @Nested
    @DisplayName("Message resolution tests")
    class MessageResolutionTests {

        @Test
        @DisplayName("Should resolve error message from attributes")
        void shouldResolveErrorMessage(ComponentConfigDecorator componentConfig) {
            // Arrange
            var underTest = anyComponent();
            componentConfig.registerConverter(ObjectToStringConverter.class);
            underTest.getAttributes().put("requiredMessageValue", "requiredMessageValue");

            // Act & Assert
            assertEquals("requiredMessageValue", underTest.getResolvedErrorMessage(),
                    "Should resolve error message from requiredMessageValue attribute");
        }

        @Test
        @DisplayName("Should resolve empty message from attributes")
        void shouldResolveEmptyMessage(ComponentConfigDecorator componentConfig) {
            // Arrange
            var underTest = anyComponent();
            componentConfig.registerConverter(ObjectToStringConverter.class);
            underTest.getAttributes().put("emptyMessageValue", "emptyMessageValue");

            // Act & Assert
            assertEquals("emptyMessageValue", underTest.getResolvedEmptyMessage(),
                    "Should resolve empty message from emptyMessageValue attribute");
        }

        @Test
        @DisplayName("Should determine when empty message should be rendered")
        void shouldRenderEmptyMessage(ComponentConfigDecorator componentConfig) {
            // Arrange - Setup component with empty message
            var underTest = anyComponent();
            var model = new MockEditableDataListModel();
            componentConfig.registerConverter(ObjectToStringConverter.class);
            underTest.getAttributes().put("emptyMessageValue", "emptyMessageValue");
            underTest.getAttributes().put("model", model);

            // Act & Assert - Empty model
            assertTrue(underTest.isEmptyMessageRendered(),
                    "Empty message should be rendered when model is empty");

            // Arrange - Add item to model
            model.addItem();

            // Act & Assert - Non-empty model
            assertFalse(underTest.isEmptyMessageRendered(),
                    "Empty message should not be rendered when model has items");
        }
    }

    @Nested
    @DisplayName("Validation tests")
    class ValidationTests {

        @Test
        @DisplayName("Should throw exception when required validation fails")
        void shouldValidateRequired(FacesContext facesContext) {
            // Arrange
            var model = new MockEditableDataListModel();
            var underTest = anyComponent();
            underTest.getAttributes().put("model", model);
            underTest.getAttributes().put("required", true);

            // Act & Assert
            assertThrows(ValidatorException.class,
                    () -> underTest.validate(facesContext, new HtmlInputText(), "irrelevant"),
                    "Should throw ValidatorException when required=true and model is empty");
        }

        @Test
        @DisplayName("Should validate using custom model validator")
        void shouldValidateModel(FacesContext facesContext) {
            // Arrange
            var model = new MockEditableDataListModel();
            var underTest = anyComponent();
            underTest.getAttributes().put("model", model);
            underTest.getAttributes().put("modelValidator", "test.EditableDataListValidator");
            facesContext.getApplication().addValidator("test.EditableDataListValidator",
                    EditableDataListValidator.class.getName());
            var component = new HtmlInputText();

            // Act & Assert
            assertThrows(ValidatorException.class,
                    () -> underTest.validate(facesContext, component, "irrelevant"),
                    "Should throw ValidatorException when custom validator fails");
        }
    }
}
