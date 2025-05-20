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

import java.io.Serializable;

/**
 * <h2>Core interface for navigation menu elements</h2>
 * <p>
 * This interface defines the base contract for all navigation menu elements,
 * providing the common attributes and behaviors required for menu functionality.
 * It forms the foundation of the menu model hierarchy, with specialized subtypes
 * implementing specific menu item behaviors.
 * </p>
 * <p>
 * Key features include:
 * </p>
 * <ul>
 * <li>Unique identification through IDs</li>
 * <li>Parent-child relationships for building hierarchical menus</li>
 * <li>Ordering capabilities for controlling the position of items</li>
 * <li>Internationalization support for menu labels</li>
 * <li>Icon support for visual representation</li>
 * <li>State management (active state, rendered state)</li>
 * </ul>
 * <p>
 * This interface extends {@link Comparable} to provide natural ordering of menu items
 * based on their order property, allowing for consistent menu presentation.
 * </p>
 *
 * @author Oliver Wolff
 */
public interface NavigationMenuItem extends Serializable, Comparable<NavigationMenuItem> {

    /**
     * <p>Retrieves the unique identifier for this menu item.</p>
     * <p>The ID serves multiple purposes:</p>
     * <ul>
     * <li>Uniquely identifies the menu item within a menu structure</li>
     * <li>Can be used to associate access rights or visibility rules</li>
     * <li>Serves as a reference point for parent-child relationships</li>
     * <li>May be used for client-side identification in HTML/CSS/JavaScript</li>
     * </ul>
     *
     * @return The unique identifier of the menu item. Must not be {@code null}.
     */
    String getId();

    /**
     * <p>Retrieves the order value that determines the position of this menu item.</p>
     * <p>The order value controls the positioning of menu items within their respective
     * containers. Lower values position items earlier/higher in the menu structure.</p>
     * <p>
     * Ordering of {@link NavigationMenuItem}s: The usual range is 1 &lt;
     * {@link #getOrder()} &lt; 50. For the top-level elements this will result in
     * items with a lower order to be ordered to the left side, while a higher order
     * will result in being put to the right side. For container elements
     * (Dropdowns) a lower ordered item will be put to the top of the list, while a
     * higher ordered item will be be put to the bottom.
     * </p>
     * Sample from the portal:
     * <ul>
     * <li>The logout menu item has the order '48', will therefore be rendered to
     * the very right</li>
     * <li>The user menu item has the order '40', will therefore be rendered left
     * compared to the logout-item, but right to all other items assuming they have
     * a order &lt; 40</li>
     * <li>The user Menu provides two items as default.</li>
     * </ul>
     *
     * @return The order value for positioning this menu item.
     * The lower the number, the higher/earlier the element is displayed.
     * The default implementations like {@link NavigationMenuItemImpl} default to 25.
     * The number is used implicitly by {@link Comparable#compareTo(Object)}.
     */
    Integer getOrder();

    /**
     * <p>Retrieves the ID of this menu item's parent.</p>
     * <p>This method establishes the hierarchical structure of the menu by
     * identifying the parent menu item that contains this item. This allows
     * for dynamic building of nested menu structures.</p>
     *
     * @return The ID of the parent {@link NavigationMenuItem}.
     * A value of "top" indicates this is a top-level menu item with no parent.
     */
    String getParentId();

    /**
     * <p>Retrieves the resolved title text to display for this menu item.</p>
     * <p>This method returns the actual text that should be displayed in the UI,
     * which may be resolved from a resource bundle if a title key was specified.</p>
     * <p>Always check {@link #isTitleAvailable()} before calling this method to
     * ensure a title exists.</p>
     *
     * @return The resolved, display-ready title text for this menu item.
     * May be {@code null} if no title is available.
     * @see #isTitleAvailable()
     */
    String getResolvedTitle();

    /**
     * <p>Retrieves the resource bundle key for the title.</p>
     * <p>This key can be used to look up internationalized title text
     * in a resource bundle.</p>
     *
     * @return The resource bundle key for the title. May be {@code null}
     * if no key was specified.
     */
    String getTitleKey();

    /**
     * <p>Retrieves the explicit title value for this menu item.</p>
     * <p>This is a direct title value that can be used instead of a 
     * resource bundle key.</p>
     *
     * @return The explicit title value. May be {@code null} if no direct
     * value was specified.
     */
    String getTitleValue();

    /**
     * <p>Checks if a title is available for this menu item.</p>
     * <p>This method determines whether a title can be displayed for this
     * menu item, either from a title key or a direct title value.</p>
     *
     * @return {@code true} if a title is available and can be rendered,
     * {@code false} otherwise.
     */
    boolean isTitleAvailable();

    /**
     * <p>Retrieves the CSS class name for the menu item's icon.</p>
     * <p>This defines the visual representation of the menu item through
     * a CSS class that typically represents an icon.</p>
     *
     * @return The CSS class name for the icon. May be {@code null} or empty
     * if no icon is specified.
     */
    String getIconStyleClass();

    /**
     * <p>Determines whether this menu item should be rendered.</p>
     * <p>This method controls the visibility of the menu item in the UI.
     * It can be used to conditionally show or hide menu items based on
     * application state, user permissions, or other criteria.</p>
     *
     * @return {@code true} if the menu item should be rendered,
     * {@code false} if it should be hidden.
     */
    boolean isRendered();

    /**
     * <p>Determines whether this menu item is currently active (selected).</p>
     * <p>This method indicates whether this menu item represents the
     * current location or selected option in the application. Active
     * menu items are typically highlighted or styled differently.</p>
     * <p>The default implementation returns {@code false}, but subclasses
     * should override this to provide proper active state detection.</p>
     *
     * @return {@code true} if the menu item is active/selected,
     * {@code false} otherwise. Default is {@code false}.
     */
    default boolean isActive() {
        return false;
    }
}
