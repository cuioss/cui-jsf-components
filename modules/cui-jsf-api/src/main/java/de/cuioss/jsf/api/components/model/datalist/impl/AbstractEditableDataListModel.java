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

import static de.cuioss.jsf.api.components.model.datalist.AddStatus.PERSISTED;

import de.cuioss.jsf.api.components.model.datalist.EditEvent;
import de.cuioss.jsf.api.components.model.datalist.EditStatus;
import de.cuioss.jsf.api.components.model.datalist.EditableDataListModel;
import de.cuioss.jsf.api.components.model.datalist.ItemWrapper;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * <h2>Base implementation for editable data list models</h2>
 * <p>
 * This abstract class provides a common implementation of {@link EditableDataListModel},
 * serving as the usual starting point for concrete implementations. It handles the core
 * functionality for:
 * </p>
 * <ul>
 * <li>Managing the lifecycle of list items (add, edit, delete)</li>
 * <li>Maintaining the correct state of items and their wrappers</li>
 * <li>Tracking changes to the model</li>
 * <li>Ensuring consistent indexing of items</li>
 * </ul>
 * <p>
 * Subclasses need to implement only a minimal set of methods:
 * </p>
 * <ul>
 * <li>{@link #createEmptyItem()} - To create new empty items</li>
 * <li>{@link #createCopy(Serializable)} - To create copies of existing items</li>
 * <li>{@link #getLoadedItems()} - To provide the initial set of items</li>
 * </ul>
 *
 * @author Oliver Wolff
 * @param <T> identifying the type of items to be managed. Must be at least
 *            {@link Serializable} and implement {@link Object#hashCode()} and
 *            {@link Object#equals(Object)} correctly to ensure proper tracking of changes.
 */
@ToString(doNotUseGetters = true)
@EqualsAndHashCode(doNotUseGetters = true)
public abstract class AbstractEditableDataListModel<T extends Serializable> implements EditableDataListModel<T> {

    @Serial
    private static final long serialVersionUID = 2719782028119450821L;
    private List<ItemWrapper<T>> displayItems;

    /**
     * <p>Retrieves the list of display items, initializing it if necessary.</p>
     * <p>This implementation lazily creates the display items list the first time
     * it is requested, populating it with wrapped versions of the items returned
     * by {@link #getLoadedItems()}.</p>
     *
     * @return A list of all wrapped display items. May be empty, but never {@code null}.
     */
    @Override
    public List<ItemWrapper<T>> getDisplayItems() {
        if (null == displayItems) {
            initDisplayItems();
        }
        return displayItems;
    }

    /**
     * <p>Sets the given item to edit mode while canceling edit mode for all other items.</p>
     * <p>This implementation ensures that only one item can be in edit mode at a time,
     * automatically canceling any other items that might be in edit mode.</p>
     *
     * @param item The item to be edited. Must not be {@code null}.
     */
    @Override
    public void editItem(final ItemWrapper<T> item) {
        cancelAllEditItems();
        item.doEdit(createCopy(item.getWrapped()));
    }

    /**
     * <p>Adds a new empty item to the list and puts it in edit mode.</p>
     * <p>This implementation cancels any existing items in edit mode,
     * creates a new empty item, and adds it to the list at either the
     * beginning or the end based on {@link #isNewCreatedItemShouldBeAddedAsFirst()}.</p>
     *
     * @return The newly created and wrapped item that is now in edit mode.
     *         Never returns {@code null}.
     */
    @Override
    public ItemWrapper<T> addItem() {
        final var newWrapper = getNewItemWrapper();
        cancelAllEditItems();
        newWrapper.doEdit(createEmptyItem());
        return addNewDisplayItem(newWrapper);
    }

    /**
     * <p>Adds a specific item to the model with {@linkplain EditStatus#ADDED} status.</p>
     * <p>This helper method allows direct addition of existing items to the model
     * without going through the edit mode first.</p>
     *
     * @param item The item to be added to the model. Must not be {@code null}.
     * @return The wrapped item that was added to the display items.
     */
    public ItemWrapper<T> addItem(final T item) {
        return addNewDisplayItem(new ItemWrapperImpl<>(item, EditStatus.ADDED));
    }

    private ItemWrapper<T> addNewDisplayItem(final ItemWrapper<T> newWrapper) {
        if (isNewCreatedItemShouldBeAddedAsFirst()) {
            getDisplayItems().add(0, newWrapper);
        } else {
            getDisplayItems().add(newWrapper);
        }
        handleListIndices();
        return newWrapper;
    }

    /**
     * <p>Cancels the editing of the given item.</p>
     * <p>This implementation reverts any changes made to the item during editing.
     * For newly added items that have not been persisted, it removes them from
     * the display list entirely.</p>
     *
     * @param item The item for which to cancel editing. Must not be {@code null}.
     */
    @Override
    public void cancelEditItem(final ItemWrapper<T> item) {
        item.doCancel();
        if (!PERSISTED.equals(item.getAddStatus()) && null == item.getInitialWrapped()) {
            getDisplayItems().remove(item.getListIndex());
            handleListIndices();
        }
    }

    /**
     * <p>Saves the changes made to the given item.</p>
     * <p>This implementation completes the editing process and triggers appropriate
     * callback notifications based on whether the item was newly added or
     * an existing item was modified.</p>
     *
     * @param item The edited item to save. Must not be {@code null}.
     */
    @Override
    public void saveEditItem(final ItemWrapper<T> item) {
        final var oldValue = item.getInitialWrapped();
        item.doSave();
        final var newValue = item.getWrapped();

        if (null != oldValue && !oldValue.equals(newValue)) {
            elementModified(EditEvent.MODIFIED, oldValue, newValue);
        } else if (null == oldValue && null != newValue) {
            elementModified(EditEvent.ADDED, null, newValue);
        }
    }

    /**
     * <p>Marks the given item for deletion.</p>
     * <p>This implementation handles the different cases for deletion:
     * </p>
     * <ul>
     * <li>For non-persisted items: Removes them from the display list entirely</li>
     * <li>For persisted items: Marks them with {@link EditStatus#DELETED}</li>
     * </ul>
     * <p>In both cases, appropriate callback notifications are triggered.</p>
     *
     * @param item The item to mark for deletion. Must not be {@code null}.
     */
    @Override
    public void markForDelete(final ItemWrapper<T> item) {
        if (!PERSISTED.equals(item.getAddStatus())) {
            getDisplayItems().remove(item.getListIndex());
            handleListIndices();
        } else {
            item.markForDelete();
        }
        elementModified(EditEvent.MARK_DELETE, item.getWrapped(), null);
    }

    /**
     * <p>Undoes a previous delete operation on the given item.</p>
     * <p>This implementation restores the item to its previous status and
     * triggers the appropriate callback notification.</p>
     *
     * @param item The item for which to undo deletion. Must not be {@code null}.
     */
    @Override
    public void undoMarkForDelete(final ItemWrapper<T> item) {
        item.undoMarkForDelete();
        elementModified(EditEvent.UNMARK_DELETE, null, item.getWrapped());
    }

    /**
     * <p>Cancels edit mode for all items in the list.</p>
     * <p>This helper method ensures that no items are left in edit mode,
     * and removes any newly created items that were canceled before being saved.</p>
     */
    protected void cancelAllEditItems() {
        getDisplayItems().stream().filter(ItemWrapper::isEditMode).forEach(ItemWrapper::doCancel);
        removeNullItems();
    }

    /**
     * <p>Removes items that were newly created but then canceled before being saved.</p>
     * <p>This helper method cleans up the display items list by removing
     * wrapper entries with null content that are no longer needed.</p>
     */
    private void removeNullItems() {
        getDisplayItems().removeIf(item -> !PERSISTED.equals(item.getAddStatus()) && null == item.getWrapped());
    }

    /**
     * <p>Checks if all items in the model are in a stable state (not being edited).</p>
     * <p>This implementation iterates through all display items to verify
     * that none have {@link ItemWrapper#isEditMode()} returning true.</p>
     *
     * @return {@code true} if there are no items currently being edited,
     *         {@code false} if at least one item is in edit mode.
     */
    @Override
    public boolean isEveryItemSavedOrCanceled() {
        for (final ItemWrapper<T> item : getDisplayItems()) {
            if (item.isEditMode()) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Retrieves all items that are marked for deletion.</p>
     * <p>This implementation filters the display items to find those that
     * are both marked for deletion and were previously persisted.</p>
     *
     * @return A list of all items marked for deletion. May be empty, but never {@code null}.
     */
    @Override
    public List<T> getDeletedItems() {
        return getDisplayItems().stream()
                .filter(item -> item.isMarkedForDelete() && PERSISTED.equals(item.getAddStatus()))
                .map(ItemWrapper::getWrapped).toList();
    }

    /**
     * <p>Retrieves the effective list of items after applying all pending changes.</p>
     * <p>This implementation filters out any items marked for deletion from
     * the display items list.</p>
     *
     * @return A list containing all non-deleted items. May be empty, but never {@code null}.
     */
    @Override
    public List<T> getResultItems() {
        return getDisplayItems().stream().filter(item -> !item.isMarkedForDelete()).map(ItemWrapper::getWrapped)
                .toList();
    }

    /**
     * <p>Determines if the model has been modified compared to its initial state.</p>
     * <p>This implementation checks for any of the following conditions:</p>
     * <ul>
     * <li>Items marked for deletion</li>
     * <li>Different number of items between loaded and result lists</li>
     * <li>Different content between loaded and result lists</li>
     * </ul>
     *
     * @return {@code true} if the model has changes, {@code false} otherwise.
     */
    @Override
    public boolean hasChanges() {
        if (!getDeletedItems().isEmpty()) {
            return true;
        }
        final var loaded = getLoadedItems();
        final var current = getResultItems();
        if (loaded.size() != current.size()) {
            return true;
        }

        return !new HashSet<>(loaded).containsAll(current) || !new HashSet<>(current).containsAll(loaded);
    }

    /**
     * <p>Updates the list indices for all display items.</p>
     * <p>This helper method ensures that each item has its position index
     * correctly set after any operations that might alter the list structure.</p>
     */
    private void handleListIndices() {
        var index = 0;
        for (final ItemWrapper<T> item : getDisplayItems()) {
            item.setListIndex(index);
            index++;
        }
    }

    /**
     * <p>Creates a new empty item wrapper.</p>
     * <p>This helper method provides a factory for creating new item wrappers
     * used when adding items to the model.</p>
     *
     * @return A new empty item wrapper instance.
     */
    protected ItemWrapper<T> getNewItemWrapper() {
        return new ItemWrapperImpl<>();
    }

    /**
     * <p>Creates a new item wrapper for an existing item.</p>
     * <p>This helper method provides a factory for creating item wrappers
     * around existing items, typically used when initializing the model.</p>
     *
     * @param item The item to wrap. Must not be {@code null}.
     * @return A new item wrapper containing the provided item.
     */
    protected ItemWrapper<T> getNewItemWrapper(final T item) {
        return new ItemWrapperImpl<>(item);
    }

    /**
     * <p>Initializes or reinitializes the display items list with the loaded items.</p>
     * <p>This method creates a fresh display items list containing wrapped versions
     * of all items returned by {@link #getLoadedItems()}, ensuring proper indexing.</p>
     * <p>This is called automatically the first time {@link #getDisplayItems()} is invoked,
     * and can be called manually to reset the model to its initial state.</p>
     */
    public void initDisplayItems() {

        if (displayItems == null) {
            displayItems = new ArrayList<>(0);
        } else {
            displayItems.clear();
        }

        for (final T item : getLoadedItems()) {
            displayItems.add(getNewItemWrapper(item));
        }

        handleListIndices();
    }
}
