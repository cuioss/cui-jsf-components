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

import static de.cuioss.tools.base.Preconditions.checkState;
import static java.util.Objects.requireNonNull;

import de.cuioss.jsf.api.components.model.datalist.AddStatus;
import de.cuioss.jsf.api.components.model.datalist.EditStatus;
import de.cuioss.jsf.api.components.model.datalist.ItemWrapper;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * <h2>Standard implementation of the ItemWrapper interface</h2>
 * <p>
 * This class provides a concrete implementation of {@link ItemWrapper},
 * handling the state management of wrapped items in an editable data list.
 * It maintains the item's current state, original state, position in the list,
 * and status information needed for proper tracking of changes.
 * </p>
 * <p>
 * Key features include:
 * </p>
 * <ul>
 * <li>Tracking both current and original state of the wrapped item</li>
 * <li>Managing transitions between different edit statuses</li>
 * <li>Preserving state for potential rollback operations</li>
 * <li>Supporting the complete item lifecycle (creation, modification, deletion)</li>
 * </ul>
 *
 * @param <T> identifying the type of items to be wrapped. Must be at least
 *            {@link Serializable} and implement {@link Object#hashCode()} and
 *            {@link Object#equals(Object)} correctly to ensure proper tracking of changes.
 */
@EqualsAndHashCode(of = {"wrapped", "editStatus"})
@ToString(of = {"wrapped", "editStatus"})
public class ItemWrapperImpl<T extends Serializable> implements ItemWrapper<T> {

    @Serial
    private static final long serialVersionUID = 7020783590024372831L;

    @Getter
    private T wrapped;

    /**
     * Used for tracking the initially wrapped item.
     */
    @Getter
    private T initialWrapped;

    @Getter
    @Setter
    private EditStatus editStatus;

    @Getter
    private AddStatus addStatus;

    private EditStatus initalEditStatus;

    @Getter
    @Setter
    private int listIndex;

    /**
     * <p>Creates a new item wrapper with the specified wrapped item and edit status.</p>
     * <p>This constructor allows explicit control over both the wrapped content
     * and its initial edit status. The add status is automatically determined
     * based on whether the edit status is {@link EditStatus#ADDED}.</p>
     *
     * @param wrapped    The item to wrap. May be null, though this is typically
     *                   only appropriate for items that will be edited immediately.
     * @param editStatus The initial edit status. Must not be null.
     * @throws NullPointerException If editStatus is null.
     */
    public ItemWrapperImpl(final T wrapped, final EditStatus editStatus) {
        requireNonNull(editStatus);
        this.wrapped = wrapped;
        this.editStatus = editStatus;
        addStatus = EditStatus.ADDED.equals(editStatus) ? AddStatus.CREATED : AddStatus.PERSISTED;
    }

    /**
     * <p>Creates a new item wrapper for an existing item with {@link EditStatus#INITIAL} status.</p>
     * <p>This constructor is a convenience shortcut for
     * {@link #ItemWrapperImpl(Serializable, EditStatus)} with the edit status
     * preset to {@link EditStatus#INITIAL}.</p>
     *
     * @param wrapped The item to wrap. May be null, although this typically
     *                doesn't make sense for items in INITIAL state.
     */
    public ItemWrapperImpl(final T wrapped) {
        this(wrapped, EditStatus.INITIAL);
    }

    /**
     * <p>Creates a new empty item wrapper with {@link EditStatus#ADDED} status.</p>
     * <p>This constructor is a convenience shortcut for creating new, empty items
     * that will be populated during an edit operation. It sets the wrapped content
     * to null and the edit status to {@link EditStatus#ADDED}.</p>
     */
    public ItemWrapperImpl() {
        this(null, EditStatus.ADDED);
    }

    /**
     * <p>Completes the editing process and saves changes to the model.</p>
     * <p>This implementation updates the item's status based on its previous state:</p>
     * <ul>
     * <li>For new items (ADDED): Maintains ADDED status but updates add status to {@link AddStatus#ADDED}</li>
     * <li>For existing items: Sets status to either INITIAL or MODIFIED based on whether changes were made</li>
     * </ul>
     * <p>The original state reference is cleared after saving.</p>
     *
     * @throws NullPointerException If the wrapped item is null
     * @throws IllegalStateException If called without a prior call to {@link #doEdit}
     */
    @Override
    public void doSave() {
        requireNonNull(wrapped, "Invalid usage: wrapped should not be null");
        switch (initalEditStatus) {
            case ADDED:
                editStatus = EditStatus.ADDED;
                addStatus = AddStatus.ADDED;
                break;
            case EDIT:
                throw new IllegalStateException("Invalid usage: You must not doCancel or doSave prior to doEdit");
            default:
                editStatus = wrapped.equals(initialWrapped) ? EditStatus.INITIAL : EditStatus.MODIFIED;
                break;
        }
        initialWrapped = null;
    }

    /**
     * <p>Cancels the current editing operation and reverts changes.</p>
     * <p>This implementation restores both the wrapped item and the edit status
     * to their original values before the edit operation began.</p>
     */
    @Override
    public void doCancel() {
        wrapped = initialWrapped;
        editStatus = initalEditStatus;
    }

    /**
     * <p>Marks the item for deletion.</p>
     * <p>This implementation saves the current edit status for potential
     * restoration and sets the new status to {@link EditStatus#DELETED}.</p>
     */
    @Override
    public void markForDelete() {
        initalEditStatus = editStatus;
        editStatus = EditStatus.DELETED;
    }

    /**
     * <p>Undoes a previous deletion mark.</p>
     * <p>This implementation restores the edit status to what it was
     * before the item was marked for deletion.</p>
     */
    @Override
    public void undoMarkForDelete() {
        editStatus = initalEditStatus;
    }

    /**
     * <p>Transitions the item into edit mode with a working copy.</p>
     * <p>This implementation saves the current state for potential rollback,
     * replaces the wrapped item with the provided copy, and sets the
     * edit status to {@link EditStatus#EDIT}.</p>
     *
     * @param copyToEdit The working copy to use for editing. Must not be {@code null}.
     * @throws NullPointerException If copyToEdit is null
     * @throws IllegalStateException If the item is currently marked for deletion
     */
    @Override
    public void doEdit(final T copyToEdit) {
        requireNonNull(copyToEdit);
        checkState(!EditStatus.DELETED.equals(getEditStatus()), "Invalid usage: deleted elements can not be edited.");
        initialWrapped = wrapped;
        wrapped = copyToEdit;
        initalEditStatus = editStatus;
        editStatus = EditStatus.EDIT;
    }

    /**
     * <p>Checks if the item is currently in edit mode.</p>
     *
     * @return {@code true} if the item's status is {@link EditStatus#EDIT},
     *         {@code false} otherwise.
     */
    @Override
    public boolean isEditMode() {
        return EditStatus.EDIT.equals(getEditStatus());
    }

    /**
     * <p>Checks if the item is marked for deletion.</p>
     *
     * @return {@code true} if the item's status is {@link EditStatus#DELETED},
     *         {@code false} otherwise.
     */
    @Override
    public boolean isMarkedForDelete() {
        return EditStatus.DELETED.equals(getEditStatus());
    }

    /**
     * <p>Checks if the item was newly added to the model.</p>
     *
     * @return {@code true} if the item's add status is not {@link AddStatus#PERSISTED},
     *         {@code false} otherwise.
     */
    @Override
    public boolean isMarkedAsAdd() {
        return !AddStatus.PERSISTED.equals(getAddStatus());
    }

    /**
     * <p>Checks if the wrapped item has been modified from its original state.</p>
     *
     * @return {@code true} if the item's status is {@link EditStatus#MODIFIED},
     *         {@code false} otherwise.
     */
    @Override
    public boolean isModifiedItem() {
        return EditStatus.MODIFIED.equals(getEditStatus());
    }
}
