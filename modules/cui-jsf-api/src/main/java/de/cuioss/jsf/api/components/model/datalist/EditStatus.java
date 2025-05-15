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

/**
 * <h2>Defines the current edit status of an item in an editable data list</h2>
 * <p>
 * This enum represents the different states that an item can have within an
 * {@link EditableDataListModel}. It tracks the lifecycle of items from their
 * initial state through editing, modification, and potentially deletion.
 * </p>
 * <p>
 * The status is always used in conjunction with {@link ItemWrapper} which encapsulates
 * both the item itself and metadata about its current state in the model.
 * </p>
 *
 * @author Oliver Wolff
 */
public enum EditStatus {

    /**
     * <p>Represents an item in its initial, unmodified state.</p>
     * <p>This is the status of items that were loaded from the backend system
     * and have not been edited or marked for deletion.</p>
     */
    INITIAL,

    /**
     * <p>Indicates that an item is currently being edited.</p>
     * <p>Items with this status are in the process of being modified by the user,
     * but the changes have not yet been confirmed or saved to the model.</p>
     * <p>Typically, only one item can be in this state at a time within an
     * {@link EditableDataListModel}.</p>
     */
    EDIT,

    /**
     * <p>Indicates that an item has been newly added to the data list but not yet persisted.</p>
     * <p>This status is assigned to items that were created using
     * {@link EditableDataListModel#createEmptyItem()} and added to the model
     * through {@link EditableDataListModel#addItem()}.</p>
     * <p>These items exist only in the model and have not yet been saved
     * to the backend system.</p>
     */
    ADDED,

    /**
     * <p>Indicates that an existing item has been modified.</p>
     * <p>This status is assigned to items that were loaded from the backend system
     * and subsequently edited, with the changes confirmed by the user.</p>
     * <p>Items with this status have pending changes that may need to be
     * persisted to the backend system.</p>
     */
    MODIFIED,

    /**
     * <p>Indicates that an item has been marked for deletion.</p>
     * <p>Items with this status are still part of the model's display items
     * (accessible via {@link EditableDataListModel#getDisplayItems()}) but are
     * excluded from the result items (returned by {@link EditableDataListModel#getResultItems()}).</p>
     * <p>They may be visually distinguished in the UI to indicate their pending deletion status.</p>
     */
    DELETED
}
