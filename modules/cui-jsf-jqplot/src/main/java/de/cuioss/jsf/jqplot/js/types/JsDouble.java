package de.cuioss.jsf.jqplot.js.types;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import de.cuioss.jsf.jqplot.js.support.JsValue;
import lombok.Data;

/**
 * Provide safe way to act with Double as JSON property
 *
 * @author Eugen Fischer
 */
@Data
public class JsDouble implements JsValue {

    /** serial VersionUID */
    private static final long serialVersionUID = 369333694678385312L;

    private static final DecimalFormat decimalFormat = initFormatter();

    private final Double value;

    @Override
    public String getValueAsString() {
        if (null == this.value) {
            return null;
        }

        return decimalFormat.format(this.value);
    }

    private static DecimalFormat initFormatter() {
        final var instance = DecimalFormatSymbols.getInstance();
        instance.setDecimalSeparator('.');
        return new DecimalFormat("0.000", instance);
    }
}
