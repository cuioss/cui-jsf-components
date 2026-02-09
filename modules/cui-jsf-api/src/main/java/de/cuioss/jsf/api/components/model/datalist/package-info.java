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
/**
 * <h2>Provides API for creating and managing editable data lists in JSF components</h2>
 * <p>
 * This package contains interfaces and implementations for handling editable data lists in JSF applications.
 * It provides functionality for:
 * </p>
 * <ul>
 * <li>Managing editable lists with add, edit, and remove operations</li>
 * <li>Tracking item status (created, added, persisted)</li>
 * <li>Wrapping list items to track their edit state</li>
 * <li>Handling edit events within data lists</li>
 * </ul>
 * <p>
 * The core interface is {@link de.cuioss.jsf.api.components.model.datalist.EditableDataListModel} which
 * defines the operations available for editable data lists.
 * </p>
 * 
 * @author Oliver Wolff
 */
package de.cuioss.jsf.api.components.model.datalist;
