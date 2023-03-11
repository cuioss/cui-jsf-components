package de.cuioss.jsf.components.chart.options.color;

import de.cuioss.jsf.components.js.support.JsValue;
import de.cuioss.jsf.components.js.types.JsString;
import de.cuioss.tools.string.MoreStrings;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Colors are displayed combining RED, GREEN, and BLUE light.
 *
 * @see <a href="http://www.w3schools.com/cssref/css_colors.asp">Css Colors</a>
 * @author Eugen Fischer
 */
@ToString
@EqualsAndHashCode(callSuper = false)
public class Color implements JsValue {

    /** serial Version UID */
    private static final long serialVersionUID = 9156599356772054784L;

    /**
     * Currently no value checks are done
     */
    private final JsString value;

    /**
     * Short cut constructor set directly the color value.
     *
     * @param colorValue String value in hex form. (For example #FF5500)
     */
    private Color(final String colorValue) {
        this.value = new JsString(colorValue);
    }

    @Override
    public String getValueAsString() {
        return value.getValueAsString();
    }

    /**
     * transparent color
     */
    public static final Color TRANSPARENT = new Color("transparent");

    /**
     * If parameter colorValue is {@code null} or empty the color will be set to 'transparent'.<br/>
     *
     * @param colorValue string value for color
     * @return {@linkplain Color}
     */
    public final static Color createFrom(final String colorValue) {
        if (MoreStrings.isEmpty(colorValue)) {
            return TRANSPARENT;
        }
        return new Color(colorValue);
    }
}
