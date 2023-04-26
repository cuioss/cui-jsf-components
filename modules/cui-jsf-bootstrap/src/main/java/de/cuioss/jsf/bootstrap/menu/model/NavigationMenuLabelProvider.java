package de.cuioss.jsf.bootstrap.menu.model;

/**
 * @author Sven Haag
 */
public interface NavigationMenuLabelProvider {

    /**
     * @return labelValue
     */
    String getLabelValue();

    /**
     * @return labelKey
     */
    String getLabelKey();

    /**
     * @return the resolved label to display.
     */
    String getResolvedLabel();
}
