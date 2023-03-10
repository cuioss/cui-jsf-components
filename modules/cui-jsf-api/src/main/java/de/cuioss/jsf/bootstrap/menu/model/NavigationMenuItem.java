package de.cuioss.jsf.bootstrap.menu.model;

import java.io.Serializable;

/**
 * Base class for creating navigation menu elements
 *
 * @author Oliver Wolff
 */
public interface NavigationMenuItem extends Serializable, Comparable<NavigationMenuItem> {

    /**
     * @return the (technical) ID of the menu item. This may be utilized for technical aspects like
     *         access rights or dynamically hiding the corresponding menu. Mandatory.
     */
    String getId();

    /**
     * <p>
     * Ordering of {@link NavigationMenuItem}s: The usual range is 1 &lt; {@link #getOrder()} &lt; 50. For
     * the top-level elements this will result in items with a lower order to be ordered to the left
     * side, while a higher order will result in being put to the right side. For container elements
     * (Dropdowns) a lower ordered item will be put to the top of the list, while a higher ordered
     * item will be be put to the bottom.
     * </p>
     * <p>
     * Sample from the portal:
     * <ul>
     * <li>The logout menu item has the order '48', will therefore be rendered to the very
     * right</li>
     * <li>The user menu item has the order '40', will therefore be rendered left compared to the
     * logout-item, but right to all other items assuming they have a order &lt; 40</li>
     * <li>The user Menu provides two items as default.</li>
     * </ul>
     * </p>
     *
     * @return the number for the ordering for the concrete {@link NavigationMenuItem}. The lower
     *         the number the higher the element is displayed. The default implementations like
     *         {@link NavigationMenuItemImpl} default to 25. The number is used implicitly by
     *         {@link Comparable#compareTo(Object)}.
     */
    Integer getOrder();

    /**
     * @return the ID of the parent {@link NavigationMenuItem}. This is needed for nesting the
     *         Menus dynamically. If it returns 'top' it is assumed to be a top-level
     *         element.
     */
    String getParentId();

    /**
     * @return the resolved title to display for this menu-item: Optional for non separator items
     *         but recommended to be used (accessibility). <em>Caution:</em> check with
     *         {@link #isTitleAvailable()} before accessing
     */
    String getResolvedTitle();

    /**
     * @return titleKey
     */
    String getTitleKey();

    /**
     * @return titleValue
     */
    String getTitleValue();

    /**
     * @return flag indicating whether a title is set and can therefore be rendered
     */
    boolean isTitleAvailable();

    /**
     * @return The css class to be utilized for displaying a corresponding icon. Optional
     */
    String getIconStyleClass();

    /**
     * @return flag indicating whether the navigation element is disabled
     */
    boolean isDisabled();

    /**
     * @return flag indicating whether the navigation element should be rendered
     */
    boolean isRendered();

    /**
     * @return boolean indicating whether the current {@link NavigationMenuItem} is active, aka
     *         selected.
     */
    default boolean isActive() {
        return false;
    }

}
