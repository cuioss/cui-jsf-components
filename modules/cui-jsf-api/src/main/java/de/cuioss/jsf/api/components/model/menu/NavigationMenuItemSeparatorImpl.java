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

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;

/**
 * <h2>Standard implementation of the NavigationMenuItemSeparator interface</h2>
 * <p>
 * This class extends {@link NavigationMenuItemImpl} and implements {@link NavigationMenuItemSeparator}
 * to provide a concrete implementation of a separator-type menu item. Separators are
 * non-interactive elements that visually divide groups of related menu items.
 * </p>
 * <p>
 * Key features of this implementation:
 * </p>
 * <ul>
 * <li>Simple, stateless implementation focused on presentation</li>
 * <li>Provides a factory method to avoid creating redundant instances</li>
 * <li>Inherits ordering capabilities from NavigationMenuItemImpl</li>
 * </ul>
 * <p>
 * This class is typically used to create visual dividers within dropdown menus
 * or to separate groups of related navigation options.
 * </p>
 *
 * @author Sven Haag
 */
@EqualsAndHashCode(callSuper = true)
@ToString
public class NavigationMenuItemSeparatorImpl extends NavigationMenuItemImpl implements NavigationMenuItemSeparator {

    @Serial
    private static final long serialVersionUID = 2808000723947735968L;

    /**
     * <p>Creates a new separator menu item with the specified order value.</p>
     * <p>The order value determines the position of this separator relative to
     * other menu items within the same container.</p>
     *
     * @param order The order value for positioning this separator. Lower values
     *              position the separator earlier/higher in the menu structure.
     */
    public NavigationMenuItemSeparatorImpl(Integer order) {
        super(order);
    }

    /**
     * <p>Factory method to create instances of NavigationMenuItemSeparator.</p>
     * <p>This method provides a consistent way to create separator instances,
     * and could be extended in the future to implement caching or pooling of
     * separator instances if needed, since separators are stateless elements.</p>
     *
     * @param order The order value for positioning the separator within a menu.
     *              Lower values position the separator earlier/higher in the menu.
     * @return A new instance of {@link NavigationMenuItemSeparator} with the specified order.
     */
    public static NavigationMenuItemSeparator getInstance(Integer order) {
        return new NavigationMenuItemSeparatorImpl(order);
    }
}
