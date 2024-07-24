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
package de.cuioss.jsf.jqplot.portal.theme;

import java.io.Serial;
import java.io.Serializable;

import de.cuioss.jsf.jqplot.js.types.JsBoolean;
import de.cuioss.jsf.jqplot.js.types.JsString;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Provide JqPlot theme specific grid settings
 *
 * <pre>
 * grid: {
        drawGridlines: true,
        gridLineColor: "#cccccc",
        gridLineWidth: 1,
        backgroundColor: "#fffdf6",
        borderColor: "#999999",
        borderWidth: 2,
        shadow: true
    }
 * </pre>
 *
 * @author i000576
 */
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("java:S6548") // owolff: Legacy code, will be removed
public class GridTheme implements Serializable {

    @Serial
    private static final long serialVersionUID = -1005187429311999511L;

    /**
     * default css class name is 'jqplot-grid'
     */
    public static final String CSS_CLASS_NAME = "jqplot-grid";

    @Getter
    private final Boolean drawGridlines;

    @Getter
    private final String backgroundColor;

    private JsBoolean drawGridlinesJs;

    /**
     * @return drawGridlines setting as JsonValue
     */
    public String getDrawGridlinesAsJs() {
        if (null == drawGridlinesJs) {
            drawGridlinesJs = JsBoolean.create(drawGridlines);
        }
        return drawGridlinesJs.getValueAsString();
    }

    private JsString backgroundColorJs;

    /**
     * @return backgroundColor setting as JsonValue
     */
    public String getBackgroundColorAsJs() {
        if (null == backgroundColorJs) {
            backgroundColorJs = new JsString(backgroundColor);
        }
        return backgroundColorJs.getValueAsString();
    }

    /**
     * Factory Method for {@linkplain GridTheme}
     *
     * @param cssRule {@linkplain CssRule} is optional
     * @return {@linkplain GridTheme} according property values of cssRule. If
     *         parameter cssRule is {@code null} default
     *         {@linkplain GridTheme#DEFAULT_GRID_SETTINGS} will be returned
     */
    public static GridTheme createBy(final CssRule cssRule) {

        if (null == cssRule) {
            return DEFAULT_GRID_SETTINGS;
        }

        final var drawGridlinesValue = Boolean.valueOf(cssRule.getPropertyValue("-jqplot-drawGridlines"));

        final var backgroundColorValue = cssRule.getPropertyValue("-jqplot-backgroundColor");

        return new GridTheme(drawGridlinesValue, backgroundColorValue);
    }

    /**
     * Grid with default theme settings
     */
    public static final GridTheme DEFAULT_GRID_SETTINGS = new GridTheme(Boolean.TRUE, "#fffdf6");
}
