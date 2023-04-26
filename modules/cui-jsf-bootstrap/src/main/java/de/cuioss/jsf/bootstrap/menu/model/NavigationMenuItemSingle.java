package de.cuioss.jsf.bootstrap.menu.model;

import java.util.Map;

/**
 * @author Sven Haag
 */
public interface NavigationMenuItemSingle extends NavigationMenuItem, NavigationMenuLabelProvider {

    /**
     * @return the String outcome for this menu-item.
     */
    String getOutcome();

    /**
     * @return the String target for this menu-item.
     */
    String getTarget();

    /**
     * @return a map of key / value parameters to be added to the {@link #getOutcome()}
     */
    Map<String, String> getOutcomeParameter();

    /**
     * @return java-script which be should executed while on click event
     */
    String getOnClickAction();
}
