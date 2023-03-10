package de.cuioss.jsf.bootstrap.menu.model;

import java.util.List;
import java.util.Map;

/**
 * @author Sven Haag, Sven Haag
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

    /**
     * @return the list of viewIds for that the component should be displayed active.
     *         The renderer must provide a data attribute that is used in CSS to display the
     *         component as 'active'.
     * @deprecated use {@link #isActive()} instead
     */
    @Deprecated
    List<String> getActiveForAdditionalViewId();
}
