package de.cuioss.jsf.components.chart.plugin.highlighter;

import static java.util.Objects.requireNonNull;

import lombok.Getter;

/**
 * Fade Speed
 *
 * @author Eugen Fischer
 */
public enum FadeSpeed {

    /** */
    FAST("fast"),
    /** */
    SLOW("slow"),
    /** */
    DEFAULT("def");

    @Getter
    private final String constant;

    FadeSpeed(final String value) {
        constant = requireNonNull(value, "constant must not be null");
    }
}
