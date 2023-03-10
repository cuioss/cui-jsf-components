package de.cuioss.jsf.api.components.partial;

import de.cuioss.jsf.api.components.css.StyleClassProvider;

/**
 * <h2>Summary</h2>
 * <p>
 * Implementors of this class manage the state and resolving of the styleClass-attribute. The
 * implementation relies on the correct use of attribute names, saying they must exactly match the
 * accessor methods.
 * </p>
 * <h2>styleClass</h2>
 * <p>
 * Space-separated list of CSS style class(es) to be applied additionally when this element is
 * rendered.
 * </p>
 *
 * @author Oliver Wolff
 */
public interface ComponentStyleClassProvider extends StyleClassProvider {

    /**
     * @param styleClass the styleClass to set
     */
    void setStyleClass(String styleClass);
}
