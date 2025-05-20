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

import de.cuioss.jsf.jqplot.js.support.JsArray;
import de.cuioss.jsf.jqplot.js.types.JsString;
import de.cuioss.tools.string.MoreStrings;
import de.cuioss.tools.string.Splitter;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static de.cuioss.tools.string.MoreStrings.requireNotEmpty;

/**
 * SeriesColors object that could be applied to all series in the plot.
 * <h3>Expected css Rule structure</h3>
 *
 * <pre>
 * // color values must be html conform either color names or their hex value
 * .some-usefull-class-name{
 *  -jqplot-seriescolors : "blue, #FF0000, .. colorN"
 * }
 * </pre>
 *
 * @author i000576
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
@SuppressWarnings("java:S6548") // owolff: Legacy code, will be removed
public class SeriesColors implements Serializable {

    @Serial
    private static final long serialVersionUID = 7676175049884241143L;

    /**
     * Default Css class name is 'jqplot-series-styles'
     */
    public static final String CSS_CLASS_NAME = "jqplot-series-styles";

    @Getter
    private final List<String> colors;

    private String colorsAsJs;

    /**
     * @return colors as JsonValue
     */
    public String getColorsAsJs() {
        if (null == colorsAsJs) {
            final var colJs = new JsArray<JsString>();
            colJs.addAll(colors.stream().map(JsString::new).toList());
            colorsAsJs = colJs.getValueAsString();
        }
        return colorsAsJs;
    }

    /**
     * Factory method to create {@linkplain SeriesColors} from {@linkplain CssRule}.
     * If cssRule is missing {@link SeriesColors#DEFAULT_SERIES_COLORS} will be used
     *
     * @param cssRule {@linkplain CssRule} could be {@code null}
     * @return {@linkplain SeriesColors}
     */
    public static SeriesColors createBy(final CssRule cssRule) {

        if (null == cssRule) {
            return DEFAULT_SERIES_COLORS;
        }

        final var seriesColors = cssRule.getPropertyValue("-jqplot-seriescolors");

        requireNotEmpty(seriesColors, "seriesColors");

        return new SeriesColors(extractColors(seriesColors));
    }

    private static List<String> extractColors(final String value) {

        if (MoreStrings.isEmpty(value)) {
            return Collections.emptyList();
        }

        return Splitter.on(",").omitEmptyStrings().trimResults().splitToList(value).stream()
                .map(item -> item.replace("'", "")).map(item -> item.replace("\"", "")).toList();

    }

    /**
     * Fall-back SeriesColors use red and green as colors
     */
    public static final SeriesColors DEFAULT_SERIES_COLORS = new SeriesColors(Arrays.asList("red", "green"));

}
