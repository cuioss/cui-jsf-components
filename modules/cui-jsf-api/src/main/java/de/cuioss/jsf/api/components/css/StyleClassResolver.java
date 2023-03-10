package de.cuioss.jsf.api.components.css;

import de.cuioss.jsf.api.components.partial.ComponentStyleClassProvider;

/**
 * <p>
 * Simple interface defining a way to resolve-style-class(es).
 * <p>
 *
 * @author Oliver Wolff
 */
public interface StyleClassResolver {

    /**
     * In contrast to {@link ComponentStyleClassProvider#getStyleClass()} that
     * returns the previously set / configured styleClasss-attribute this method
     * computes a style-class from different sources.
     *
     * @return the resolved (combined) styleclass for a component.
     */
    StyleClassBuilder resolveStyleClass();
}
