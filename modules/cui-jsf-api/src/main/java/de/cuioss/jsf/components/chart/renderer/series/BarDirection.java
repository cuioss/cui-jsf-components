package de.cuioss.jsf.components.chart.renderer.series;

import lombok.Getter;

/**
 * ‘vertical’ = up and down bars, ‘horizontal’ = side to side bars
 *
 * @author Eugen Fischer
 */
public enum BarDirection {

    /** */
    VERTICAL("vertical"),
    /** */
    HORIZONTAL("horizontal");

    @Getter
    private final String direction;

    BarDirection(final String value) {
        this.direction = value;
    }
}
