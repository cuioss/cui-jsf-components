package com.icw.ehf.cui.components.bootstrap.menu.model;

/**
 * @author Sven Haag, Sven Haag
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
