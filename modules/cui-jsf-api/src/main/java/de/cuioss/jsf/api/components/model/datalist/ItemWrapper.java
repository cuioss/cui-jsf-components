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

/**
 * <h2>Wrapper for items in an editable data list</h2>
 * <p>
 * An {@link ItemWrapper} encapsulates a concrete object and provides additional metadata
 * and operations for managing the item within an editable list context. It tracks:
 * </p>
 * <ul>
 * <li>The item's position in the list</li>
 * <li>The item's current edit status</li>
 * <li>The original state of the item for comparison and potential rollback</li>
 * <li>Addition status for newly created items</li>
 * </ul>
 * <p>
 * This interface provides methods to manipulate the item's state, such as entering
 * edit mode, saving changes, canceling edits, and marking items for deletion.
 * </p>
 *
 * @param <T> identifying the type of items to be wrapped. Must be at least
 *            {@link Serializable} and implement {@link Object#hashCode()} and
 *            {@link Object#equals(Object)} correctly to ensure proper tracking of changes.
 *
 * @author Oliver Wolff
 */
public interface ItemWrapper<T extends Serializable> extends Serializable {

    /**
     * <p>Gets the item's position index within the list.</p>
     * <p>This index is used for display ordering and identification purposes.</p>
     *
     * @return The zero-based index of this item in the list.
     */
    int getListIndex();

    /**
     * <p>Sets the item's position index within the list.</p>
     * <p>This method is typically called when the list structure changes,
     * such as when items are added, removed, or reordered.</p>
     *
     * @param listIndex The zero-based index to assign to this item.
     */
    void setListIndex(int listIndex);

    /**
     * <p>Gets the actual wrapped item in its current state.</p>
     * <p>If the item is in edit mode, this will return the working copy
     * that is being modified.</p>
     *
     * @return The wrapped item instance. Never returns {@code null}.
     */
    T getWrapped();

    /**
     * <p>Gets the original state of the wrapped item.</p>
     * <p>This method is used for tracking the initially wrapped item,
     * providing a reference point for detecting changes or reverting edits.</p>
     *
     * @return The initially wrapped item as it was before any edits.
     *         Never returns {@code null}.
     */
    T getInitialWrapped();

    /**
     * <p>Gets the current edit status of the wrapped item.</p>
     * <p>This status indicates the item's state within the edit lifecycle,
     * such as whether it is new, modified, being edited, or deleted.</p>
     *
     * @return The {@link EditStatus} of this item. Never returns {@code null}.
     */
    EditStatus getEditStatus();

    /**
     * <p>Sets the edit status of the wrapped item.</p>
     * <p>This method is typically called by the model's internal operations
     * when the item's state changes.</p>
     *
     * @param editStatus The new {@link EditStatus} to assign to the item.
     *                   Must not be {@code null}.
     */
    void setEditStatus(EditStatus editStatus);

    /**
     * <p>Completes the editing process and saves changes to the model.</p>
     * <p>This operation transitions the item out of edit mode and sets the
     * {@link EditStatus} according to the current state:
     * </p>
     * <ul>
     * <li>For new items: {@link EditStatus#ADDED}</li>
     * <li>For modified existing items: {@link EditStatus#MODIFIED}</li>
     * </ul>
     * <p>The changes remain in the model but aren't necessarily persisted
     * to the backend system yet.</p>
     */
    void doSave();

    /**
     * <p>Cancels the current editing operation and reverts changes.</p>
     * <p>This operation replaces the working copy ({@link #getWrapped()})
     * with the original version ({@link #getInitialWrapped()}) and
     * transitions the item out of edit mode.</p>
     * <p>For new items (with {@link AddStatus#CREATED}), this typically
     * results in the item being removed from the list entirely.</p>
     */
    void doCancel();

    /**
     * <p>Transitions the item into edit mode with a working copy.</p>
     * <p>This operation sets the item's status to {@link EditStatus#EDIT}
     * and replaces the current wrapped item with the provided copy
     * that can be safely modified.</p>
     *
     * @param copyToEdit The working copy to use for editing. Must not be {@code null}.
     *                   This is either a copy of the existing element (via
     *                   {@link EditableDataListModel#createCopy(Serializable)}) or a
     *                   new empty item (via {@link EditableDataListModel#createEmptyItem()}).
     */
    void doEdit(final T copyToEdit);

    /**
     * <p>Marks the item for deletion.</p>
     * <p>This operation sets the item's status to {@link EditStatus#DELETED},
     * making it eligible for removal during the next persistence operation.</p>
     * <p>The item remains in the model's display items but will be excluded
     * from result items.</p>
     * 
     * @see #isMarkedForDelete()
     */
    void markForDelete();

    /**
     * <p>Reverts a previous deletion mark.</p>
     * <p>This operation restores the item to its previous status before
     * it was marked for deletion, making it part of the result items again.</p>
     * 
     * @see #markForDelete()
     */
    void undoMarkForDelete();

    /**
     * <p>Gets the creation status of the wrapped item.</p>
     * <p>This indicates whether the item is newly created and, if so,
     * its progress through the creation lifecycle (created, added, persisted).</p>
     *
     * @return The {@link AddStatus} indicating the creation state of this item.
     *         Never returns {@code null}.
     */
    AddStatus getAddStatus();

    /**
     * <p>Checks if the wrapped item has been modified from its original state.</p>
     * <p>This is typically used to determine if the item has pending changes
     * that may need to be persisted.</p>
     *
     * @return {@code true} if the item has been modified from its original state,
     *         {@code false} otherwise.
     */
    boolean isModifiedItem();

    /**
     * <p>Checks if the item is currently in edit mode.</p>
     * <p>Items in edit mode are being actively modified by the user and
     * have not yet had their changes saved or canceled.</p>
     *
     * @return {@code true} if the item is currently in edit mode
     *         ({@link EditStatus#EDIT}), {@code false} otherwise.
     */
    boolean isEditMode();

    /**
     * <p>Checks if the item is marked for deletion.</p>
     * <p>Items marked for deletion are excluded from result items but
     * still appear in display items, typically with visual indication
     * of their pending deletion status.</p>
     *
     * @return {@code true} if the item's status is {@link EditStatus#DELETED},
     *         {@code false} otherwise.
     */
    boolean isMarkedForDelete();

    /**
     * <p>Checks if the item was newly added to the model.</p>
     * <p>This identifies items that were created within the model and
     * were not part of the initial data loaded from the backend system.</p>
     *
     * @return {@code true} if the item was added via {@link EditableDataListModel#addItem()},
     *         {@code false} for items that were part of the initial data.
     */
    boolean isMarkedAsAdd();
}
