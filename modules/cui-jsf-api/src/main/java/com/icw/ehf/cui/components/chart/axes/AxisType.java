package com.icw.ehf.cui.components.chart.axes;

import static de.cuioss.tools.collect.CollectionLiterals.immutableSet;

import java.util.EnumSet;
import java.util.Set;

import lombok.Getter;

/**
 * There are 2 x axes, 'xaxis' and 'x2axis', and 9 y axes, 'yaxis', ‘y2axis’. ‘y3axis’, ... Any or
 * all of which may be specified.
 *
 * @author i000576
 * @see <a
 *      href="http://www.jqplot.com/docs/files/jqplot-core-js.html#Axis.Properties">jqplot-core-js
 *      axis properties</a>
 */
public enum AxisType {

    /**
     * name of first x axis
     */
    XAXIS("xaxis"),
    /**
     * name of second x axis
     */
    X2AXIS("x2axis"),
    /**
     * name of first y axis
     */
    YAXIS("yaxis"),
    /**
     * name of second y axis
     */
    Y2AXIS("y2axis"),
    /**
     * name of third y axis
     */
    Y3AXIS("y3axis"),
    /**
     * name of fourth y axis
     */
    Y4AXIS("y4axis"),
    /**
     * name of 5th y axis
     */
    Y5AXIS("y5axis"),
    /**
     * name of sixths y axis
     */
    Y6AXIS("y6axis"),
    /**
     * name of seventh y axis
     */
    Y7AXIS("y7axis"),
    /**
     * name of eighth y axis
     */
    Y8AXIS("y8axis");

    @Getter
    private final String axisName;

    AxisType(final String value) {
        axisName = value;
    }

    /**
     * Set of all x axis
     */
    public static final Set<AxisType> X_AXES = immutableSet(XAXIS, X2AXIS);

    /**
     * Set of all y axis
     */
    public static final Set<AxisType> Y_AXES =
        immutableSet(EnumSet.complementOf(EnumSet.of(XAXIS, X2AXIS)));
}
