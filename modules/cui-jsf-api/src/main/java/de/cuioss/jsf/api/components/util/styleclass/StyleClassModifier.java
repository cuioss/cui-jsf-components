package de.cuioss.jsf.api.components.util.styleclass;

/**
 * This interface is needed to streamline the access of components with the
 * styleClass attribute.
 *
 * @author Oliver Wolff (Oliver Wolff)
 */
public interface StyleClassModifier {

    /**
     * Analogous to jQuery.toggleClass(): If a class is present remove it. If not
     * add it.
     *
     * @param styleClass must not be null or empty.
     * @return the modified String
     */
    String toggleStyleClass(String styleClass);

    /**
     * Add an additional StyleClass to already existing StyleClasses. The method
     * ensures that there are no duplicates of the same name
     *
     * @param styleClass must not be null or empty
     * @return the modified StyleClass String
     */
    String addStyleClass(String styleClass);

    /**
     * Removes a concrete StyleClass. If it is not there, nothing happens.
     *
     * @param styleClass must not be null
     * @return The resulting StyleClass String
     */
    String removeStyleClass(String styleClass);
}
