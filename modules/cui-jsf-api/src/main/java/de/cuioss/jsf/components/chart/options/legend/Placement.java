package de.cuioss.jsf.components.chart.options.legend;

import static java.util.Objects.requireNonNull;

import lombok.Getter;

/**
 * "insideGrid" places legend inside the grid area of the plot. "outsideGrid" places the legend
 * outside the grid but inside the plot container, shrinking the grid to accomodate the legend.
 * "inside" synonym for "insideGrid", "outside" places the legend ouside the grid area, but does not
 * shrink the grid which can cause the legend to overflow the plot container.
 *
 * @author Eugen Fischer
 */
public enum Placement {

    /** */
    INSIDE("insideGrid"),
    /** */
    OUTSIDE("outsideGrid");

    @Getter
    private final String constant;

    Placement(final String value) {
        constant = requireNonNull(value, "constant must not be null");
    }
}
