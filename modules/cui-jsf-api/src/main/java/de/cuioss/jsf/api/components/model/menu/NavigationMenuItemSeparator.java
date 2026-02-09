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
package de.cuioss.jsf.api.components.model.menu;

/**
 * <h2>Interface for menu separator items</h2>
 * <p>
 * This interface represents separator elements within a navigation menu. Separators are
 * non-interactive elements that visually divide groups of related menu items, typically
 * rendered as horizontal lines or dividers in dropdown menus.
 * </p>
 * <p>
 * Key characteristics of menu separators:
 * </p>
 * <ul>
 * <li>Visual dividers with no interactive functionality</li>
 * <li>Used to group related menu items</li>
 * <li>Positioned between menu items based on their order property</li>
 * <li>Stateless elements that can be reused throughout a menu</li>
 * </ul>
 * <p>
 * Since separators are stateless and functionally identical, the implementation
 * {@link NavigationMenuItemSeparatorImpl} provides a singleton-pattern factory method
 * {@link NavigationMenuItemSeparatorImpl#getInstance(Integer)} to avoid creating
 * redundant instances.
 * </p>
 *
 * @author Oliver Wolff
 * @author Sven Haag
 */
public interface NavigationMenuItemSeparator extends NavigationMenuItem {
    // No additional methods required beyond NavigationMenuItem
}
