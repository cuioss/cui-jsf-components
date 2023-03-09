package com.icw.ehf.cui.core.api.components.partial;

/**
 * <h2>Summary</h2>
 * <p>
 * Implementors of this class manage the state and resolving of the style-attribute. The
 * implementation relies on the correct use of attribute names, saying they must exactly match the
 * accessor methods.
 * </p>
 * <h2>style</h2>
 * <p>
 * CSS style(s) to be applied when this component is rendered. <em>Caution</em>: The styleClass
 * attribute is always to be preferred.
 * </p>
 *
 * @author Oliver Wolff
 */
public interface StyleAttributeProvider {

    /**
     * @param style the style to set
     */
    void setStyle(String style);

    /**
     * @return the style-attribute
     */
    String getStyle();
}
