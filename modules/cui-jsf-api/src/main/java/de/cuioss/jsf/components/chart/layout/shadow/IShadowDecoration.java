package de.cuioss.jsf.components.chart.layout.shadow;

import java.io.Serializable;

/**
 * Decorates shadow object which provide follow options
 *
 * @author Eugen Fischer
 * @param <T> fluent api result type
 */
public interface IShadowDecoration<T extends Serializable> {

    /**
     * wether or not to draw a shadow on the line.
     *
     * @param value Boolean
     * @return fluent api style
     */
    T setShadow(final Boolean value);

    /**
     * Shadow angle in degrees
     *
     * @param value Double
     * @return fluent api style
     */
    T setShadowAngle(final Double value);

    /**
     * Shadow offset from line in pixels
     *
     * @param value Integer
     * @return fluent api style
     */
    T setShadowOffset(final Integer value);

    /**
     * Number of times shadow is stroked, each stroke offset shadowOffset from the last.
     *
     * @param value Integer
     * @return fluent api style
     */
    T setShadowDepth(final Integer value);

    /**
     * Alpha channel transparency of shadow. 0 = transparent.
     *
     * @param value css style
     * @return fluent api style
     */
    T setShadowAlpha(final String value);

}
