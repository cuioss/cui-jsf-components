package de.cuioss.jsf.jqplot.plugin.highlighter;

import static java.util.Objects.requireNonNull;

import lombok.Getter;

/**
 * Which axes to display in tooltip, 'x', 'y' or 'both', 'xy' or 'yx' 'both' and
 * 'xy' are equivalent, 'yx' reverses order of labels.
 *
 * @author Eugen Fischer
 */
public enum TooltipAxes {

    /** */
    ONLY_X("x"),
    /** */
    ONLY_Y("y"),
    /** */
    BOTH_XY("xy"),
    /** */
    BOTH_YX("xy");

    @Getter
    private final String constant;

    TooltipAxes(final String value) {
        constant = requireNonNull(value, "constant must not be null");
    }
}
