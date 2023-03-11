package de.cuioss.jsf.components.chart.model;

import java.io.Serializable;
import java.util.ArrayList;

import de.cuioss.jsf.components.js.support.JavaScriptSupport;
import de.cuioss.jsf.components.js.support.JsArray;
import de.cuioss.jsf.components.js.support.JsValue;
import de.cuioss.jsf.components.js.types.JsDateTimeFormat;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * user's data. Data should *NOT* be specified in the options object,
 * but be passed in as the second argument to the $.jqplot() function.
 * The data property is described here soley for reference.
 * The data should be in the form of an array of 2D or 1D arrays like
 * {@code [ [[x1, y1], [x2, y2],...], [y1, y2, ...] ]}. <br/>
 * In sum it's a list of lists which could represent single or tuple data for different series.
 *
 * @author Eugen Fischer ( Eugen Fischer )
 */
@ToString
@EqualsAndHashCode
public class SeriesData implements JavaScriptSupport {

    /** serial Version UID */
    private static final long serialVersionUID = 130324845834722962L;

    private final JsArray<JsArray<? super JsValue>> nested = new JsArray<>();

    /**
     * Null values and empty seria objects will be ignored
     *
     * @param data {@link JsArray} as seria holder
     * @return fluent api style
     */
    public SeriesData addSeriaDataIfNotNull(final JsArray<? super JsValue> data) {
        if (null != data && !data.isEmpty()) {
            nested.addValueIfNotNull(data);
        }
        return this;
    }

    /**
     * Null values and empty seria objects will be ignored
     *
     * @param data {@link JsArrayContainer}
     * @return fluent api style
     */
    public SeriesData addSeriaDataIfNotNull(final JsArrayContainer data) {
        if (null != data) {
            this.addSeriaDataIfNotNull(data.getAsArray());
        }
        return this;
    }

    /**
     * @return true if no data available
     */
    public boolean isEmpty() {
        return nested.isEmpty();
    }

    @Override
    public String asJavaScriptObjectNotation() {
        return nested.asJavaScriptObjectNotation();
    }

    /**
     * @author Oliver Wolff
     */
    public static class Builder implements Serializable {

        /** serial version UID */
        private static final long serialVersionUID = 7624014531106478811L;

        private final ArrayList<JsArrayContainer> candidates = new ArrayList<>();

        /**
         * @param format
         * @return {@link TimeLineSeria}
         */
        public TimeLineSeria<Double> createTimeLineWithDoubleValues(
                @NonNull final JsDateTimeFormat format) {
            final var timeLineSeria = new TimeLineSeria<Double>(format);
            candidates.add(timeLineSeria);
            return timeLineSeria;
        }

        /**
         * @param format
         * @return {@link TimeLineSeria}
         */
        public TimeLineSeria<Integer> createTimeLineWithIntegerValues(
                @NonNull final JsDateTimeFormat format) {
            final var timeLineSeria = new TimeLineSeria<Integer>(format);
            candidates.add(timeLineSeria);
            return timeLineSeria;
        }

        /**
         * @return {@link SeriesData}
         */
        public SeriesData build() {
            final var seriesData = new SeriesData();
            for (final JsArrayContainer container : candidates) {
                seriesData.addSeriaDataIfNotNull(container.getAsArray());
            }
            return seriesData;
        }
    }
}
