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
 * <h2>Defines the types of edit events that can occur within an editable data list</h2>
 * <p>
 * This enum represents the different kinds of modifications that can occur to items
 * in an {@link EditableDataListModel}. These events are typically used to trigger
 * callbacks and notify listeners about changes to the model.
 * </p>
 * <p>
 * The events are used by the {@link EditableDataListModel#elementModified} callback
 * to indicate what kind of change occurred to an item.
 * </p>
 *
 * @author Oliver Wolff
 */
public enum EditEvent {

    /**
     * <p>Indicates that a new item was added to the data list.</p>
     * <p>This event is triggered when a new item is created via
     * {@link EditableDataListModel#createEmptyItem()} and added to the model
     * through the {@link EditableDataListModel#addItem()} method.</p>
     * <p>In callbacks, the old value will typically be null, and the new value
     * will be the newly created item.</p>
     */
    ADDED,

    /**
     * <p>Indicates that an existing item was modified.</p>
     * <p>This event is triggered when an item that was previously loaded from
     * the back-end system is edited and the changes are saved to the model
     * through {@link EditableDataListModel#saveEditItem}.</p>
     * <p>In callbacks, both the old and new values will be non-null, representing
     * the item before and after modification.</p>
     */
    MODIFIED,

    /**
     * <p>Indicates that an item previously marked for deletion has been unmarked.</p>
     * <p>This event is triggered when {@link EditableDataListModel#undoMarkForDelete}
     * is called on an item that was previously marked for deletion.</p>
     * <p>In callbacks, the old value may be null, and the new value will be
     * the item that is no longer marked for deletion.</p>
     */
    UNMARK_DELETE,

    /**
     * <p>Indicates that an item has been marked for deletion.</p>
     * <p>This event is triggered when {@link EditableDataListModel#markForDelete}
     * is called on an item.</p>
     * <p>In callbacks, the old value will be the item being marked for deletion,
     * and the new value may be null.</p>
     */
    MARK_DELETE
}
