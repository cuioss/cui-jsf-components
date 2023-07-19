package de.cuioss.jsf.api.components.model.menu;

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
