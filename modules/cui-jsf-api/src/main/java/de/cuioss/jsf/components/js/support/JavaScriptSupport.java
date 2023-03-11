package de.cuioss.jsf.components.js.support;

import java.io.Serializable;

/**
 * @author Eugen Fischer
 */
public interface JavaScriptSupport extends Serializable {

    /**
     * If no content defined return null.
     *
     * @see <a href="http://www.json.org/">json.org</a>
     * @return JavaScriptObjectNotation representation
     */
    String asJavaScriptObjectNotation();

}
