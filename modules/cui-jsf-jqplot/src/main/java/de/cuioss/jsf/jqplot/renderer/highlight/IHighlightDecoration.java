package de.cuioss.jsf.jqplot.renderer.highlight;

import java.io.Serializable;

/**
 * Provide Highlight Decoration Methods
 *
 * @author Eugen Fischer
 * @param <T>
 *            at least {@link Serializable}
 */
public interface IHighlightDecoration<T extends Serializable> {

    /**
     * True to highlight slice when moused over.
     *
     * @param value
     *            {@link Boolean}
     * @return fluent api style
     */
    T setHighlightMouseOver(final Boolean value);

    /**
     * True to highlight when a mouse button is pressed over an area on a filled
     * plot.
     *
     * @param value
     *            {@link Boolean}
     * @return fluent api style
     */
    T setHighlightMouseDown(final Boolean value);

}
