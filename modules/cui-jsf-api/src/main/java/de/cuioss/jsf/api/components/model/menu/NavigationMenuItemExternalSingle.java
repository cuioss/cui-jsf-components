package de.cuioss.jsf.api.components.model.menu;

/**
 * @author Matthias Walliczek
 */
public interface NavigationMenuItemExternalSingle extends NavigationMenuItem, NavigationMenuLabelProvider {

    /**
     * @return the String HRef for this menu-item.
     */
    String getHRef();

    /**
     * @return the String target for this menu-item.
     */
    String getTarget();

    /**
     * @return java-script which be should executed while on click event
     */
    String getOnClickAction();
}
