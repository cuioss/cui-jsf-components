package de.cuioss.jsf.api.components.model.menu;

import java.util.List;

/**
 * Implementation of {@linkplain NavigationMenuItem} providing the option to add children of type
 * {@linkplain NavigationMenuItem}.
 *
 * @author Sven Haag
 */
public interface NavigationMenuItemContainer extends NavigationMenuItem, NavigationMenuLabelProvider {

    /**
     * @return the list of children.
     */
    List<NavigationMenuItem> getChildren();

    /**
     * @param children the list of children.
     */
    void setChildren(List<NavigationMenuItem> children);
}
