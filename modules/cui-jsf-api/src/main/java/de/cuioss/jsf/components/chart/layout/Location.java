package de.cuioss.jsf.components.chart.layout;

import static java.util.Objects.requireNonNull;

import de.cuioss.jsf.components.js.types.JsString;
import lombok.Getter;

/**
 * compass direction, nw, n, ne, e, se, s, sw, w.
 *
 * @author i000576
 */
public enum Location {

    /** */
    NW("nw"),
    /** */
    N("n"),
    /** */
    NE("ne"),
    /** */
    E("e"),
    /** */
    SE("se"),
    /** */
    S("s"),
    /** */
    SW("sw"),
    /** */
    W("w");

    @Getter
    private final String constant;

    @Getter
    private final JsString asJsString;

    Location(final String value) {
        constant = requireNonNull(value, "constant must not be null");
        asJsString = new JsString(constant);
    }

}
