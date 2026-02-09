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
 * <h2>Menu Model API for JSF Navigation Components</h2>
 * <p>
 * This package provides a comprehensive set of interfaces and implementations for creating
 * and managing navigation menus programmatically in JSF applications. The API enables
 * developers to:
 * </p>
 * <ul>
 * <li>Define hierarchical menu structures with parent-child relationships</li>
 * <li>Create various types of menu items (containers, separators, links)</li>
 * <li>Support both internal JSF navigation and external URL navigation</li>
 * <li>Control menu item rendering, ordering, and active state</li>
 * <li>Provide internationalized labels for menu items</li>
 * </ul>
 * <p>
 * The core interface is {@link de.cuioss.jsf.api.components.model.menu.NavigationMenuItem} which
 * defines the common contract for all menu items. Specialized sub-interfaces include:
 * </p>
 * <ul>
 * <li>{@link de.cuioss.jsf.api.components.model.menu.NavigationMenuItemContainer} - For dropdown/submenu containers</li>
 * <li>{@link de.cuioss.jsf.api.components.model.menu.NavigationMenuItemSingle} - For simple menu items that link to internal pages</li>
 * <li>{@link de.cuioss.jsf.api.components.model.menu.NavigationMenuItemExternalSingle} - For menu items that link to external URLs</li>
 * <li>{@link de.cuioss.jsf.api.components.model.menu.NavigationMenuItemSeparator} - For menu separators/dividers</li>
 * </ul>
 *
 * @author Oliver Wolff
 */
package de.cuioss.jsf.api.components.model.menu;
