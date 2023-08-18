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
package de.cuioss.jsf.api.components.model.datalist;

import java.io.Serializable;

/**
 * An {@link ItemWrapper} wraps a concrete {@link Object} and provides
 * additional information used for list-specific handling
 *
 * @param <T> identifying the type of items to be created. Must be at least
 *            {@link Serializable} and implement {@link Object#hashCode()} and
 *            {@link Object#equals(Object)} correctly.
 *
 * @author Oliver Wolff
 */
public interface ItemWrapper<T extends Serializable> extends Serializable {

    /**
     * @return the list index property for this element
     */
    int getListIndex();

    /**
     * @param listIndex the list index property for this element
     */
    void setListIndex(int listIndex);

    /**
     * @return the payload, the actual wrapped class.
     */
    T getWrapped();

    /**
     * Used for tracking the initially wrapped item.
     *
     * @return the initially wrapped item.
     */
    T getInitialWrapped();

    /**
     * @return the {@link EditStatus} of that very item
     */
    EditStatus getEditStatus();

    /**
     * @param editStatus the {@link EditStatus} of that very item
     */
    void setEditStatus(EditStatus editStatus);

    /**
     * 'Saves' the item to the model. In essence is sets the {@link EditStatus}
     * accordingly to the state
     */
    void doSave();

    /**
     * 'Cancels' the current editing, replaces the {@link #getWrapped()} with the
     * initially set version, see {@link ItemWrapper#doEdit(Serializable)}
     */
    void doCancel();

    /**
     * Switches the current item to edit mode and passing the corresponding copy
     *
     * @param copyToEdit must not be null. Is either a copy of the element, see
     *                   {@link EditableDataListModel#createCopy(Serializable)} or a
     *                   new empty item, see
     *                   {@link EditableDataListModel#createEmptyItem()} depending
     *                   on
     */
    void doEdit(final T copyToEdit);

    /**
     * Marks the current item as to be deleted, see {@link #isMarkedForDelete()}.
     */
    void markForDelete();

    /**
     * Reverts a previous call {@link #markForDelete()}, see
     * {@link #isMarkedForDelete()}.
     */
    void undoMarkForDelete();

    /**
     * @return boolean indicating whether the concrete item is being added
     */
    AddStatus getAddStatus();

    /**
     * @return boolean indicating whether the concrete item has been modified
     */
    boolean isModifiedItem();

    /**
     * @return boolean indicating whether the current item is in edit-mode.
     */
    boolean isEditMode();

    /**
     * @return boolean indicating whether the current item is deleted.
     */
    boolean isMarkedForDelete();

    /**
     * @return boolean indicating whether the current item was added.
     */
    boolean isMarkedAsAdd();
}
