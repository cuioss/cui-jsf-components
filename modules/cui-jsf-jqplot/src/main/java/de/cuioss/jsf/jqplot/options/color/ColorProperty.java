/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.jqplot.options.color;

import static de.cuioss.tools.string.MoreStrings.requireNotEmpty;

import de.cuioss.jsf.jqplot.js.support.JavaScriptSupport;
import de.cuioss.jsf.jqplot.js.support.JsObject;
import de.cuioss.jsf.jqplot.js.support.JsProperty;
import de.cuioss.jsf.jqplot.js.support.JsValue;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;

/**
 * Color property combine {@linkplain Color} as {@linkplain JsValue} and
 * {@linkplain JsProperty} to be able to provide simple way to add any color
 * property to an complex {@linkplain JsObject}
 *
 * @author Eugen Fischer
 */
@ToString
@EqualsAndHashCode
public class ColorProperty implements JavaScriptSupport {

    @Serial
    private static final long serialVersionUID = 7155841535779637990L;

    private final String colorPropertyName;

    private Color colorValue;

    /**
     * Create a ColorProperty which has empty color value. This means it will be
     * ignored on creation of JSON object
     *
     * @param propertyName must not be {@code null} or empty
     */
    public ColorProperty(final String propertyName) {
        colorPropertyName = requireNotEmpty(propertyName, "propertyName");
    }

    /**
     * Alternative constructor
     *
     * @param propertyName must not be {@code null} or empty
     * @param defaultColor {@linkplain Color} is optional parameter
     */
    public ColorProperty(final String propertyName, final Color defaultColor) {
        this(propertyName);
        colorValue = defaultColor;
    }

    /**
     * Set color value for property. If parameter color is {@code null} or empty
     * color value will be set to {@linkplain Color#TRANSPARENT}
     *
     * @param color any css color value representation.
     */
    public void setColorValue(final String color) {
        colorValue = Color.createFrom(color);
    }

    /**
     * Change value for color value, no checks will be done here
     *
     * @param color {@linkplain Color}
     */
    public void setColorValue(final Color color) {
        colorValue = color;
    }

    @Override
    public String asJavaScriptObjectNotation() {
        return new JsProperty<>(colorPropertyName, colorValue).asJavaScriptObjectNotation();
    }

}
