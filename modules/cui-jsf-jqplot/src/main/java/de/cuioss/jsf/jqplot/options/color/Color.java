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
package de.cuioss.jsf.jqplot.options.color;

import de.cuioss.jsf.jqplot.js.support.JsValue;
import de.cuioss.jsf.jqplot.js.types.JsString;
import de.cuioss.tools.string.MoreStrings;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;

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
    @Serial
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
        value = new JsString(colorValue);
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
     * If parameter colorValue is {@code null} or empty the color will be set to
     * 'transparent'.<br>
     *
     * @param colorValue string value for color
     * @return {@linkplain Color}
     */
    public static Color createFrom(final String colorValue) {
        if (MoreStrings.isEmpty(colorValue)) {
            return TRANSPARENT;
        }
        return new Color(colorValue);
    }
}
