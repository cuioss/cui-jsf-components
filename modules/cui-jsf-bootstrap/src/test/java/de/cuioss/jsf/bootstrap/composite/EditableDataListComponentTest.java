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
package de.cuioss.jsf.bootstrap.composite;

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.jsf.api.components.model.datalist.EditStatus;
import de.cuioss.jsf.api.components.model.datalist.ItemWrapper;
import de.cuioss.jsf.api.components.model.datalist.impl.ItemWrapperImpl;
import de.cuioss.jsf.api.converter.ObjectToStringConverter;
import de.cuioss.test.jsf.component.AbstractComponentTest;
import jakarta.faces.component.html.HtmlInputText;
import jakarta.faces.validator.ValidatorException;
import org.junit.jupiter.api.Test;

class EditableDataListComponentTest extends AbstractComponentTest<EditableDataListComponent> {

    @Test
    void shouldRenderStyleClass() {
        final var minimal = "list-group";
        final var custom = "customCss";
        var component = anyComponent();
        assertEquals(minimal, component.getWrapperStyleClass());
        component.getAttributes().put("styleClass", custom);
        assertEquals(minimal + " " + custom, component.getWrapperStyleClass());
    }

    @Test
    void shouldRenderRequiredStyleClass() {
        var component = anyComponent();
        component.getAttributes().put("required", true);
        assertTrue(component.getWrapperStyleClass().contains("required"));
    }

    @Test
    void shouldDetermineDoubleClickEnabled() {
        var model = new MockEditableDataListModel();
        var component = anyComponent();
        component.getAttributes().put("model", model);
        component.getAttributes().put("enableDoubleClickEdit", Boolean.TRUE);
        model.setEveryItemSavedOrCanceled(true);
        assertFalse(component.isEditOnDoubleClickDisabled());
        model.setEveryItemSavedOrCanceled(false);
        assertTrue(component.isEditOnDoubleClickDisabled());
        component = anyComponent();
        component.getAttributes().put("model", model);
        component.getAttributes().put("enableDoubleClickEdit", Boolean.FALSE);
        model.setEveryItemSavedOrCanceled(true);
        assertTrue(component.isEditOnDoubleClickDisabled());
        model.setEveryItemSavedOrCanceled(false);
        assertTrue(component.isEditOnDoubleClickDisabled());
    }

    @Test
    void shouldDetermineDeleteButtonVisibility() {
        var model = new MockEditableDataListModel();
        var component = anyComponent();
        component.getAttributes().put("model", model);
        component.getAttributes().put("enableDelete", Boolean.TRUE);
        model.setEveryItemSavedOrCanceled(true);
        ItemWrapper<String> item = new ItemWrapperImpl<>();
        item.setEditStatus(EditStatus.INITIAL);
        assertTrue(component.isDeleteButtonVisibleForItem(item));
        assertFalse(component.isUndoButtonVisibleForItem(item));
        item.setEditStatus(EditStatus.DELETED);
        assertFalse(component.isDeleteButtonVisibleForItem(item));
        assertTrue(component.isUndoButtonVisibleForItem(item));
        component.getAttributes().put("enableDelete", Boolean.FALSE);
        item.setEditStatus(EditStatus.INITIAL);
        assertFalse(component.isDeleteButtonVisibleForItem(item));
        assertFalse(component.isUndoButtonVisibleForItem(item));
    }

    @Test
    void shouldDetermineRenderedOfAddonButtons() {
        var underTest = anyComponent();
        underTest.getAttributes().put("renderAddonButtonsInAddMode", Boolean.TRUE);
        underTest.getAttributes().put("renderAddonButtonsInEditMode", Boolean.TRUE);
        ItemWrapper<String> item = new ItemWrapperImpl<>("test", EditStatus.ADDED);
        assertTrue(underTest.isAddonButtonsInAddModeRendered(item));
        assertFalse(underTest.isAddonButtonsInEditModeRendered(item));
        item = new ItemWrapperImpl<>("test", EditStatus.EDIT);
        assertFalse(underTest.isAddonButtonsInAddModeRendered(item));
        assertTrue(underTest.isAddonButtonsInEditModeRendered(item));
        underTest.getAttributes().put("renderAddonButtonsInAddMode", Boolean.FALSE);
        underTest.getAttributes().put("renderAddonButtonsInEditMode", Boolean.FALSE);
        item = new ItemWrapperImpl<>("test", EditStatus.ADDED);
        assertFalse(underTest.isAddonButtonsInAddModeRendered(item));
        assertFalse(underTest.isAddonButtonsInEditModeRendered(item));
        item = new ItemWrapperImpl<>("test", EditStatus.EDIT);
        assertFalse(underTest.isAddonButtonsInAddModeRendered(item));
        assertFalse(underTest.isAddonButtonsInEditModeRendered(item));
    }

    @Test
    void shouldResolveErrorMessage() {
        var underTest = anyComponent();
        getComponentConfigDecorator().registerConverter(ObjectToStringConverter.class);
        underTest.getAttributes().put("requiredMessageValue", "requiredMessageValue");
        assertEquals("requiredMessageValue", underTest.getResolvedErrorMessage());
    }

    @Test
    void shouldResolveEmptyMessage() {
        var underTest = anyComponent();
        getComponentConfigDecorator().registerConverter(ObjectToStringConverter.class);
        underTest.getAttributes().put("emptyMessageValue", "emptyMessageValue");
        assertEquals("emptyMessageValue", underTest.getResolvedEmptyMessage());
    }

    @Test
    void shouldRenderEmptyMessage() {
        var underTest = anyComponent();
        var model = new MockEditableDataListModel();
        getComponentConfigDecorator().registerConverter(ObjectToStringConverter.class);
        underTest.getAttributes().put("emptyMessageValue", "emptyMessageValue");
        underTest.getAttributes().put("model", model);
        assertTrue(underTest.isEmptyMessageRendered());
        model.addItem();
        assertFalse(underTest.isEmptyMessageRendered());
    }

    @Test
    void shouldValidateRequired() {
        var model = new MockEditableDataListModel();
        var underTest = anyComponent();
        underTest.getAttributes().put("model", model);
        underTest.getAttributes().put("required", true);
        assertThrows(ValidatorException.class,
                () -> underTest.validate(getFacesContext(), new HtmlInputText(), "irrelevant"));
    }

    @Test
    void shouldValidateModel() {
        var model = new MockEditableDataListModel();
        var underTest = anyComponent();
        underTest.getAttributes().put("model", model);
        underTest.getAttributes().put("modelValidator", "test.EditableDataListValidator");
        getFacesContext().getApplication().addValidator("test.EditableDataListValidator",
                EditableDataListValidator.class.getName());
        assertThrows(ValidatorException.class,
                () -> underTest.validate(getFacesContext(), new HtmlInputText(), "irrelevant"));
    }
}
