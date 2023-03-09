package com.icw.ehf.cui.components.chart.plugin.cursor;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * @author Oliver Wolff
 *
 */
public enum ZoomToConstrain {

    /** */
    NONE("none"),
    /** */
    X("x"),
    /** */
    Y("y");

    @Getter(value = AccessLevel.PACKAGE)
    private final String constant;

    ZoomToConstrain(final String value) {
        this.constant = value;
    }

}
