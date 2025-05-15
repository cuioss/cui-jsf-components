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
 * <h2>Defines the current persistence status of a newly created item</h2>
 * <p>
 * This enum represents the different states in the lifecycle of a new item in an editable data list.
 * It tracks the item's progress from initial creation through addition to the list and
 * eventual persistence to the underlying data store.
 * </p>
 * <p>
 * The status is always used in conjunction with {@link ItemWrapper} to keep track of item states
 * within an {@link EditableDataListModel}.
 * </p>
 * 
 * @author Matthias Walliczek
 */
public enum AddStatus {

    /**
     * The item has been initially created but not yet saved in the list.
     * This is the initial state when a new item is being constructed.
     */
    CREATED,

    /**
     * The item has been saved in the list but not yet persisted to the underlying data store.
     * This state indicates that the item is part of the model's in-memory collection
     * but hasn't been committed to permanent storage.
     */
    ADDED,

    /**
     * The item has been persisted to the underlying data store.
     * This is the final state in the lifecycle, indicating that the item
     * has been successfully saved to permanent storage.
     */
    PERSISTED
}
