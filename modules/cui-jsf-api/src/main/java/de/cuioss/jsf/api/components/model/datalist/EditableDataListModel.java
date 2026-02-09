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
package de.cuioss.jsf.api.components.model.datalist;

import java.io.Serializable;
import java.util.List;

/**
 * <h2>Core interface for managing editable data lists in JSF components</h2>
 * <p>
 * This interface defines the contract for model classes that support the EditableDataListComponent.
 * It provides functionality for:
 * </p>
 * <ul>
 * <li>Creating, editing, and deleting items in a list</li>
 * <li>Managing the state of items (new, modified, deleted)</li>
 * <li>Tracking changes to the model</li>
 * <li>Supporting undo operations for deletions</li>
 * <li>Providing different views of the data (all items, deleted items, result items)</li>
 * </ul>
 * <p>
 * Implementations should handle the complexities of item state management and ensure
 * proper synchronization between display items, deleted items, and the final result items.
 * </p>
 *
 * @param <T> identifying the type of items to be managed. Must be at least
 *            {@link Serializable} and implement {@link Object#hashCode()} and
 *            {@link Object#equals(Object)} correctly to ensure proper tracking of changes.
 * @author Matthias Walliczek
 */
public interface EditableDataListModel<T extends Serializable> extends Serializable {

    /**
     * <p>Creates an empty item to be used for adding a new element to the list.</p>
     * <p>This method is called when a new item needs to be created, typically when
     * the user initiates an "add" operation.</p>
     *
     * @return A new empty instance of the item type. Must never be {@code null}.
     */
    T createEmptyItem();

    /**
     * <p>Creates a copy of the given item.</p>
     * <p>This method is typically called when an item is being edited to create
     * a working copy that can be modified without affecting the original until
     * the changes are confirmed.</p>
     *
     * @param item to be copied. Must never be {@code null}
     * @return A new instance that is a copy of the given item. Must never be {@code null}.
     */
    T createCopy(T item);

    /**
     * <p>Retrieves all items that were initially loaded into the model.</p>
     * <p>This represents the baseline state of the model before any edits.</p>
     *
     * @return A list of all initially loaded items. May be empty, but never {@code null}.
     */
    List<T> getLoadedItems();

    /**
     * <p>Retrieves all items that are to be displayed in the editable list.</p>
     * <p>These items are wrapped with {@link ItemWrapper} to track their edit status.</p>
     * <p>This includes items marked for deletion (with {@link EditStatus#DELETED}),
     * which may be visually distinguished in the UI but are still part of the display list.</p>
     *
     * @return A list of all wrapped display items. May be empty, but never {@code null}.
     */
    List<ItemWrapper<T>> getDisplayItems();

    /**
     * <p>Retrieves all items that are marked for deletion from the editable list.</p>
     * <p>These are items where the corresponding {@link ItemWrapper#getEditStatus()} 
     * is {@link EditStatus#DELETED}.</p>
     *
     * @return A list of all items marked for deletion. May be empty, but never {@code null}.
     */
    List<T> getDeletedItems();

    /**
     * <p>Retrieves the effective list of items after applying all pending changes.</p>
     * <p>While {@link #getDisplayItems()} returns all items including those
     * marked as {@link EditStatus#DELETED}, this method filters out the deleted items,
     * returning only those that should be considered part of the final result.</p>
     *
     * @return A list containing all non-deleted items from {@link #getDisplayItems()}.
     *         May be empty, but never {@code null}.
     */
    List<T> getResultItems();

    /**
     * <p>Sets the given item to edit mode while ensuring all other items exit edit mode.</p>
     * <p>This method enforces the constraint that only one item can be in edit mode at a time.</p>
     *
     * @param item The item to be edited. Must not be {@code null}.
     */
    void editItem(ItemWrapper<T> item);

    /**
     * <p>Cancels the editing of the given item.</p>
     * <p>If the item was added using {@link #addItem()}, it will be completely removed
     * from the {@link #getDisplayItems()} list, as it was never part of the original data.</p>
     * <p>For existing items, this will revert any changes made during the current edit operation.</p>
     *
     * @param item The item for which to cancel editing. Must not be {@code null}.
     */
    void cancelEditItem(ItemWrapper<T> item);

    /**
     * <p>Saves the changes made to the given item to the local model.</p>
     * <p>This commits the changes to the model but does not persist them to the
     * underlying data source. That would typically be handled separately.</p>
     *
     * @param item The edited item to save. Must not be {@code null}.
     */
    void saveEditItem(ItemWrapper<T> item);

    /**
     * <p>Adds a new item to the model and puts it in edit mode.</p>
     * <p>This will create a new empty item via {@link #createEmptyItem()},
     * wrap it in an {@link ItemWrapper}, set it to edit mode, and add it to the display list.</p>
     *
     * @return The newly created and wrapped item that is now in edit mode.
     *         Never returns {@code null}.
     */
    ItemWrapper<T> addItem();

    /**
     * <p>Marks the given item for deletion.</p>
     * <p>If the item was added using {@link #addItem()} and has not been persisted,
     * it will be completely removed from the model. Otherwise, it will be marked with
     * {@link EditStatus#DELETED} and added to the deleted items list.</p>
     *
     * @param item The item to be marked for deletion. Must not be {@code null}.
     */
    void markForDelete(ItemWrapper<T> item);

    /**
     * <p>Undoes a previous delete operation on the given item.</p>
     * <p>This reverses the effect of {@link #markForDelete(ItemWrapper)},
     * restoring the item to its previous state in the model.</p>
     *
     * @param item The item for which to undo the delete operation. Must not be {@code null}.
     */
    void undoMarkForDelete(ItemWrapper<T> item);

    /**
     * <p>Checks if all items in the model are in a stable state (not being edited).</p>
     * <p>This is useful for determining if operations that require a consistent
     * model state can proceed safely.</p>
     *
     * @return {@code true} if there are no items currently in {@link ItemWrapper#isEditMode()},
     *         {@code false} if at least one item is still being edited.
     */
    boolean isEveryItemSavedOrCanceled();

    /**
     * <p>Determines if the model has been modified compared to its initial state.</p>
     * <p>This checks for any additions, modifications, or deletions relative
     * to the items initially loaded into the model.</p>
     *
     * @return {@code true} if the model has changes, {@code false} if it is in
     *         its initial state or all changes have been reverted.
     */
    boolean hasChanges();

    /**
     * <p>Callback method that is invoked whenever an element in the model is modified.</p>
     * <p>This provides a hook for reacting to changes in the model, such as updating
     * related UI components or triggering business logic.</p>
     * <p>To detect modifications, {@link Object#equals(Object)} will be used to
     * compare old and new values.</p>
     *
     * @param editEvent The type of change that occurred, one of
     *                  {@link EditEvent#ADDED}, {@link EditEvent#MARK_DELETE},
     *                  {@link EditEvent#MODIFIED}, {@link EditEvent#UNMARK_DELETE}
     * @param oldValue  The value before the modification. May be null for
     *                  {@link EditEvent#ADDED} or {@link EditEvent#UNMARK_DELETE}.
     * @param newValue  The value after the modification. May be null for
     *                  {@link EditEvent#MARK_DELETE}.
     */
    default void elementModified(final EditEvent editEvent, final T oldValue, final T newValue) {
        // Default implementation is empty
    }

    /**
     * <p>Determines the insertion position for newly created items in the list.</p>
     * <p>By default, new items are added at the beginning of the list, but
     * this behavior can be overridden.</p>
     *
     * @return {@code true} if newly created items should be added as the first element
     *         (default), {@code false} if they should be added at the end.
     */
    default boolean isNewCreatedItemShouldBeAddedAsFirst() {
        return true;
    }

    /**
     * <p>Checks if a specific element should be treated as read-only.</p>
     * <p>Read-only elements will not display edit or delete buttons in the UI.</p>
     * <p>This can be used to implement permission-based editing or to protect
     * certain items from modification.</p>
     *
     * @param element The element to check.
     * @return {@code true} if the element should be handled as read-only,
     *         {@code false} otherwise. Default is {@code false}.
     */
    default boolean isReadOnly(T element) {
        return false;
    }

    /**
     * <p>Checks if a specific element can be deleted.</p>
     * <p>This determines whether a delete button should be displayed for the item
     * and whether deletion operations are allowed.</p>
     * <p>This can be used to implement permission-based deletion or to protect
     * certain items from being removed.</p>
     *
     * @param element The element to check.
     * @return {@code true} if the element can be deleted (default),
     *         {@code false} if deletion should be prevented.
     */
    default boolean isDeletable(T element) {
        return true;
    }
}
