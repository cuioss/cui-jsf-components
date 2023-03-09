package com.icw.ehf.cui.core.api.components.css;

/**
 * Implementer of this interface can provide styleClasses and
 * {@link StyleClassBuilder}
 *
 * @author Oliver Wolff
 *
 */
public interface StyleClassProvider {

    /**
     * @return the styleClass
     */
    String getStyleClass();

    /**
     * @return newly created {@link StyleClassBuilder} with the current
     *         style-class set
     */
    StyleClassBuilder getStyleClassBuilder();
}
