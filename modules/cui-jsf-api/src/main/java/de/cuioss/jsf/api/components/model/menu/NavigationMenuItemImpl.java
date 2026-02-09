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

import de.cuioss.jsf.api.application.navigation.NavigationUtils;
import de.cuioss.jsf.api.components.support.LabelResolver;
import jakarta.faces.context.FacesContext;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * <h2>Base implementation of the NavigationMenuItem interface</h2>
 * <p>
 * This abstract class provides common functionality for navigation menu items,
 * implementing most of the methods defined in the {@link NavigationMenuItem} interface.
 * It serves as the foundation for concrete menu item types like single menu items,
 * containers, and separators.
 * </p>
 * <p>
 * Key features of this implementation:
 * </p>
 * <ul>
 * <li>Provides standard attributes for menu items (ID, icons, titles, etc.)</li>
 * <li>Manages enabled/disabled and rendered states</li>
 * <li>Implements natural ordering based on the order property</li>
 * <li>Supports internationalized titles through resource bundles</li>
 * <li>Handles active state detection based on view IDs</li>
 * </ul>
 * <p>
 * This class is designed to be extended by concrete menu item implementations
 * that define specific behaviors for different types of menu items.
 * </p>
 *
 * @author Sven Haag
 */
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
public abstract class NavigationMenuItemImpl implements NavigationMenuItem {

    @Serial
    private static final long serialVersionUID = 7357376149469833501L;

    /**
     * The unique identifier for this menu item.
     */
    @Getter
    @Setter
    private String id;

    /**
     * The CSS class name for the menu item's icon.
     */
    @Getter
    @Setter
    private String iconStyleClass;

    /**
     * Flag indicating whether this menu item is disabled.
     * Disabled items are typically displayed but not interactive.
     */
    @Getter
    @Setter
    private boolean disabled = false;

    /**
     * Flag indicating whether this menu item should be rendered.
     * Default is {@code true}.
     */
    @Getter
    @Setter
    private boolean rendered = true;

    /**
     * The order value that determines the position of this menu item.
     * This is initialized through the constructor and cannot be changed afterward.
     */
    @Getter
    private final Integer order;

    /**
     * The resource bundle key for the title.
     */
    @Getter
    @Setter
    private String titleKey;

    /**
     * The explicit title value.
     */
    @Getter
    @Setter
    private String titleValue;

    /**
     * The ID of the parent menu item.
     */
    @Getter
    @Setter
    private String parentId;

    /**
     * <p>A list of view IDs for which this menu item should be considered "active".</p>
     * <p>This allows a menu item to be highlighted as active when navigating to
     * specific views that are conceptually related to the menu item,
     * even if they're not the direct target of the menu item itself.</p>
     */
    @Getter
    @Setter
    private List<String> activeForAdditionalViewId = new ArrayList<>();

    /**
     * <p>Compares this menu item with another based on their order values.</p>
     * <p>This implementation enables natural ordering of menu items,
     * allowing them to be sorted by order within containers.</p>
     *
     * @param other The other menu item to compare with.
     * @return A negative value if this item should appear before the other,
     *         a positive value if it should appear after, or zero if they
     *         have the same order.
     */
    @Override
    public int compareTo(final NavigationMenuItem other) {
        return getOrder().compareTo(other.getOrder());
    }

    /**
     * <p>Retrieves the resolved title text to display for this menu item.</p>
     * <p>This implementation uses the {@link LabelResolver} to resolve the title
     * from either the titleKey (using a resource bundle) or the titleValue.</p>
     *
     * @return The resolved title text, or {@code null} if no title is available.
     */
    @Override
    public String getResolvedTitle() {
        return LabelResolver.builder().withLabelKey(titleKey).withLabelValue(titleValue).build()
                .resolve(FacesContext.getCurrentInstance());
    }

    /**
     * <p>Checks if a title is available for this menu item.</p>
     * <p>This implementation simply checks if {@link #getResolvedTitle()}
     * returns a non-null value.</p>
     *
     * @return {@code true} if a title is available, {@code false} otherwise.
     */
    @Override
    public boolean isTitleAvailable() {
        return null != getResolvedTitle();
    }

    /**
     * <p>Determines whether this menu item is currently active (selected).</p>
     * <p>This implementation checks if the current view ID is contained in the
     * list of additional view IDs for which this menu item should be active.</p>
     *
     * @return {@code true} if the current view ID is in the activeForAdditionalViewId list,
     *         {@code false} otherwise.
     */
    @Override
    public boolean isActive() {
        return !getActiveForAdditionalViewId().isEmpty() && getActiveForAdditionalViewId()
                .contains(NavigationUtils.getCurrentView(FacesContext.getCurrentInstance()).getViewId());
    }
}
