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
package de.cuioss.jsf.api.components.model.menu;

import java.util.List;

/**
 * <h2>Menu item container interface for hierarchical navigation menus</h2>
 * <p>
 * This interface extends {@link NavigationMenuItem} to support container-type menu items
 * that can hold child menu items. It enables the creation of hierarchical menu structures
 * such as dropdown menus, submenus, and nested navigation trees.
 * </p>
 * <p>
 * Key characteristics of container menu items:
 * </p>
 * <ul>
 * <li>Can contain any number of child menu items of any type</li>
 * <li>Typically rendered as dropdown menus or expandable sections</li>
 * <li>May combine the container behavior with navigation functionality</li>
 * <li>Children are typically ordered based on their order property</li>
 * </ul>
 * <p>
 * This interface also extends {@link NavigationMenuLabelProvider} to ensure
 * proper labeling support for container elements.
 * </p>
 *
 * @author Sven Haag
 */
public interface NavigationMenuItemContainer extends NavigationMenuItem, NavigationMenuLabelProvider {

    /**
     * <p>Retrieves the list of child menu items contained within this container.</p>
     * <p>The children can be any type of {@link NavigationMenuItem}, including other containers,
     * allowing for nested menu structures of arbitrary depth.</p>
     *
     * @return The list of child menu items. May be empty but should not be {@code null}.
     */
    List<NavigationMenuItem> getChildren();

    /**
     * <p>Sets the list of child menu items for this container.</p>
     * <p>This method allows for batch assignment of children, which is useful
     * when building menu structures programmatically.</p>
     *
     * @param children The list of child menu items to set. Should not be {@code null}.
     */
    void setChildren(List<NavigationMenuItem> children);
}
