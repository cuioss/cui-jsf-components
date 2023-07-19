package de.cuioss.jsf.api.components.model.menu;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Basic Implementation of a {@link NavigationMenuItem}
 *
 * @author Sven Haag
 */
@EqualsAndHashCode(callSuper = true)
@ToString
public class NavigationMenuItemSeparatorImpl extends NavigationMenuItemImpl implements NavigationMenuItemSeparator {

    private static final long serialVersionUID = 2808000723947735968L;

    /**
     * @param order
     */
    public NavigationMenuItemSeparatorImpl(Integer order) {
        super(order);
    }

    /**
     * Factory method
     *
     * @param order for the instance
     *
     * @return provide instance of {@linkplain NavigationMenuItemSeparatorImpl}
     */
    public static NavigationMenuItemSeparator getInstance(Integer order) {
        return new NavigationMenuItemSeparatorImpl(order);
    }
}
