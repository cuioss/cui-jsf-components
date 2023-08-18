/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.jqplot.axes;

import static de.cuioss.tools.collect.CollectionLiterals.immutableSet;

import java.util.EnumSet;
import java.util.Set;

import lombok.Getter;

/**
 * There are 2 x axes, 'xaxis' and 'x2axis', and 9 y axes, 'yaxis', ‘y2axis’.
 * ‘y3axis’, ... Any or all of which may be specified.
 *
 * @author Eugen Fischer
 * @see <a href=
 *      "http://www.jqplot.com/docs/files/jqplot-core-js.html#Axis.Properties">jqplot-core-js
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
    public static final Set<AxisType> Y_AXES = immutableSet(EnumSet.complementOf(EnumSet.of(XAXIS, X2AXIS)));
}
