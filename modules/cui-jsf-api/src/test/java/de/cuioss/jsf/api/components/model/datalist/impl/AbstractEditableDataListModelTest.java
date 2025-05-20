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
package de.cuioss.jsf.api.components.model.datalist.impl;

import static de.cuioss.test.generator.Generators.letterStrings;
import static de.cuioss.tools.collect.CollectionLiterals.immutableList;
import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.jsf.api.components.model.datalist.EditEvent;
import de.cuioss.jsf.api.components.model.datalist.EditStatus;
import de.cuioss.jsf.api.components.model.datalist.ItemWrapper;
import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.property.PropertyReflectionConfig;
import org.junit.jupiter.api.Test;

import java.io.Serial;
import java.util.List;
import java.util.Objects;

@PropertyReflectionConfig(skip = true)
class AbstractEditableDataListModelTest extends ValueObjectTest<SomeModelEditableDataListModel> {

    private final TypedGenerator<String> strings = letterStrings(1, 64);

    @Override
    protected SomeModelEditableDataListModel anyValueObject() {
        return new SomeModelEditableDataListModel();
    }

    @Test
    void shouldProvideData() {
        final var underTest = anyValueObject();
        assertEquals(underTest.getCount(), underTest.getDisplayItems().size());
    }

    @Test
    void shouldAddData() {
        final var underTest = anyValueObject();
        underTest.addItem();
        assertFalse(underTest.isEveryItemSavedOrCanceled());
        final var addItem = getEditElement(underTest);
        addItem.getWrapped().setName(strings.next());
        underTest.saveEditItem(addItem);
        assertTrue(underTest.isEveryItemSavedOrCanceled());
        assertNull(getEditElement(underTest));
        assertEquals(underTest.getCount() + 1, underTest.getDisplayItems().size());
    }

    @Test
    void shouldEditData() {
        final var underTest = anyValueObject();
        final var edit = underTest.getDisplayItems().iterator().next();
        underTest.editItem(edit);
        assertFalse(underTest.isEveryItemSavedOrCanceled());
        edit.getWrapped().setName(strings.next());
        underTest.saveEditItem(edit);
        assertTrue(underTest.isEveryItemSavedOrCanceled());
        assertNull(getEditElement(underTest));
        assertEquals(underTest.getCount(), underTest.getDisplayItems().size());
    }

    @Test
    void shouldCancelEditData() {
        final var underTest = anyValueObject();
        final var edit = underTest.getDisplayItems().iterator().next();
        final var initial = new SomeModel(edit.getWrapped());
        underTest.editItem(edit);
        edit.getWrapped().setName(strings.next());
        underTest.cancelEditItem(edit);
        assertEquals(initial, edit.getWrapped());
        assertNull(getEditElement(underTest));
        assertEquals(underTest.getCount(), underTest.getDisplayItems().size());
    }

    /**
     * 1.) Add new item --> puts it in edit mode 2.) Cancel ==> item should be non
     * existent
     */
    @Test
    void shouldRemoveAddedOnImmediateCancel() {
        final var underTest = anyValueObject();
        underTest.addItem();
        final var edit = getEditElement(underTest);
        edit.getWrapped().setName(strings.next());
        underTest.cancelEditItem(edit);
        assertNull(getEditElement(underTest));
        assertEquals(underTest.getCount(), underTest.getDisplayItems().size());
    }

    /**
     * 1.) Add new item --> puts it in edit mode 2.) Save 3.) Edit again 4.) Cancel
     * ==> Item should still exist
     */
    @Test
    void shouldNotRemoveAddedOnSaveEditCancel() {
        final var underTest = anyValueObject();
        final var initialCount = underTest.getCount();
        underTest.addItem();
        final var edit = getEditElement(underTest);
        edit.getWrapped().setName(strings.next());
        assertFalse(underTest.isEveryItemSavedOrCanceled());
        underTest.saveEditItem(edit);
        assertTrue(underTest.isEveryItemSavedOrCanceled());
        assertEquals(initialCount + 1, underTest.getDisplayItems().size());
        underTest.editItem(edit);
        underTest.cancelEditItem(edit);
        assertNull(getEditElement(underTest));
        assertEquals(initialCount + 1, underTest.getDisplayItems().size());
    }

    @Test
    void shouldRemoveAddedOnDelete() {
        final var underTest = anyValueObject();
        final var edit = underTest.addItem();
        edit.getWrapped().setName(strings.next());
        underTest.markForDelete(edit);
        assertNull(getEditElement(underTest));
        assertEquals(underTest.getCount(), underTest.getDisplayItems().size());
    }

    @Test
    void shouldRemoveData() {
        final var underTest = anyValueObject();
        final var delete = underTest.getDisplayItems().iterator().next();
        underTest.markForDelete(delete);
        assertNull(getEditElement(underTest));
        assertEquals(1, underTest.getDeletedItems().size());
        assertEquals(underTest.getCount() - 1, underTest.getResultItems().size());
    }

    @Test
    void shouldEmitModifiedEvent() {
        final var underTest = anyValueObject();
        underTest.resetEventData();
        final var edit = underTest.getDisplayItems().iterator().next();
        // First Check: Event should not be called if no change has happened
        underTest.editItem(edit);
        underTest.saveEditItem(edit);
        assertNull(underTest.getOldValue());
        assertNull(underTest.getNewValue());
        assertNull(underTest.getEditEvent());
        // Now check for changes
        final var oldValue = new SomeModel(edit.getWrapped());
        underTest.editItem(edit);
        edit.getWrapped().setAge(1 + edit.getWrapped().getAge());
        underTest.saveEditItem(edit);
        assertEquals(oldValue, underTest.getOldValue());
        assertEquals(edit.getWrapped(), underTest.getNewValue());
        assertEquals(EditEvent.MODIFIED, underTest.getEditEvent());
    }

    @Test
    void shouldEmitAddedEvent() {
        final var underTest = anyValueObject();
        underTest.resetEventData();
        underTest.addItem();
        final var addItem = getEditElement(underTest);
        // Now check for changes
        underTest.saveEditItem(addItem);
        assertNull(underTest.getOldValue());
        assertEquals(addItem.getWrapped(), underTest.getNewValue());
        assertEquals(EditEvent.ADDED, underTest.getEditEvent());
    }

    @Test
    void shouldEmitDeleteEvent() {
        final var underTest = anyValueObject();
        underTest.resetEventData();
        final var delete = underTest.getDisplayItems().iterator().next();
        // Now check for changes
        underTest.markForDelete(delete);
        assertNull(underTest.getNewValue());
        assertEquals(delete.getWrapped(), underTest.getOldValue());
        assertEquals(EditEvent.MARK_DELETE, underTest.getEditEvent());
        underTest.resetEventData();
        underTest.undoMarkForDelete(delete);
        assertNull(underTest.getOldValue());
        assertEquals(delete.getWrapped(), underTest.getNewValue());
        assertEquals(EditEvent.UNMARK_DELETE, underTest.getEditEvent());
    }

    @Test
    void shouldTrackChangesForDelete() {
        final var underTest = anyValueObject();
        assertFalse(underTest.hasChanges());
        final var delete = underTest.getDisplayItems().iterator().next();
        underTest.markForDelete(delete);
        assertTrue(underTest.hasChanges());
        underTest.undoMarkForDelete(delete);
        assertFalse(underTest.hasChanges());
    }

    @Test
    void shouldTrackChangesForEdit() {
        final var underTest = anyValueObject();
        assertFalse(underTest.hasChanges());
        final var edit = underTest.getDisplayItems().iterator().next();
        // First Check: Event should not be called if no change has happened
        underTest.editItem(edit);
        edit.getWrapped().setAge(1 + edit.getWrapped().getAge());
        underTest.cancelEditItem(edit);
        assertFalse(underTest.hasChanges());
        underTest.editItem(edit);
        edit.getWrapped().setAge(1 + edit.getWrapped().getAge());
        underTest.saveEditItem(edit);
        assertTrue(underTest.hasChanges());
    }

    @Test
    void shouldTrackChangesForAdd() {
        final var underTest = anyValueObject();
        assertFalse(underTest.hasChanges());
        underTest.addItem();
        final var addItem = getEditElement(underTest);
        // Now check for changes
        underTest.saveEditItem(addItem);
        assertTrue(underTest.hasChanges());
        underTest.markForDelete(addItem);
        assertFalse(underTest.hasChanges());
    }

    /**
     * Add a new item, then switch to edit mode of another item. This should cancel
     * and remove the first one.
     */
    @Test
    void shouldRemoveAddItemFromResultList() {
        final var underTest = anyValueObject();
        underTest.addItem();
        underTest.editItem(new ItemWrapperImpl<>(new SomeModel("foo", 666)));
        final var nullItemCount = underTest.getResultItems().stream().filter(Objects::isNull).count();
        assertEquals(0L, nullItemCount);
    }

    private static ItemWrapper<SomeModel> getEditElement(final AbstractEditableDataListModel<SomeModel> modelProvder) {
        return modelProvder.getDisplayItems().stream().filter(ItemWrapper::isEditMode).findFirst().orElse(null);
    }

    @Test
    void shouldAdd() {
        final var model = new TestModel();
        assertEquals(2, model.getDisplayItems().size());
        final var newItem = model.addItem("test");
        assertNotNull(newItem);
        assertEquals(EditStatus.ADDED, newItem.getEditStatus());
        assertEquals(3, model.getDisplayItems().size());
        assertTrue(model.isEveryItemSavedOrCanceled());
    }

    static class TestModel extends AbstractEditableDataListModel<String> {

        @Serial
        private static final long serialVersionUID = 7357592096129340809L;

        @Override
        public String createEmptyItem() {
            return "";
        }

        @Override
        public String createCopy(final String item) {
            return item;
        }

        @Override
        public List<String> getLoadedItems() {
            return immutableList("item1", "item2");
        }
    }
}
