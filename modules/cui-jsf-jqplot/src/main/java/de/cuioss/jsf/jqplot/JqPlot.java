package de.cuioss.jsf.jqplot;

import static de.cuioss.tools.base.Preconditions.checkState;
import static de.cuioss.tools.string.MoreStrings.emptyToNull;
import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.cuioss.jsf.jqplot.hook.HookFunctionsManager;
import de.cuioss.jsf.jqplot.hook.PlotHookFunctionProvider;
import de.cuioss.jsf.jqplot.js.support.JsObject;
import de.cuioss.jsf.jqplot.js.types.JsString;
import de.cuioss.jsf.jqplot.model.SeriesData;
import de.cuioss.jsf.jqplot.options.Options;
import de.cuioss.jsf.jqplot.plugin.PluginSupport;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * jqPlot-| |-seriesColors |-textColor |-fontFamily |-fontSize |-stackSeries
 * |-series(Array) | |-Series1 | | |-lineWidth | | |-shadow | | |-showLine | |
 * |-showMarker | | |-color | |-Series2... | |-... | |-SeriesN | |-grid(Object)
 * | |-drawGridLines | |-background | |-borderColor | |-borderWidth | |-shadow |
 * |-title(Object)-| | |-text | |-show | |-fontFamily | |-fontSize | |-textAlign
 * | |-textColor | |-axes(Object) | |-xais | | |-min | | |-max | | |-numberTicks
 * | | |-showTicks | | |-showTickMarks | | |-pad | | ... and so on
 *
 * @see <a href=
 *      "http://www.jqplot.com/docs/files/optionsTutorial-txt.html">OptionsTutorial</a>
 * @author Eugen Fischer
 */
@ToString(of = "targetId")
@EqualsAndHashCode(of = "targetId", callSuper = false)
public class JqPlot extends JsObject {

    private static final String OBJECT_NAME = "$.jqplot";

    /** serial Version UID */
    private static final long serialVersionUID = -726237452645644137L;

    /**
     * @param targetId must define div id where the graph will be shown in
     * @param data     SeriesData must not be null
     */
    public JqPlot(final String targetId, final SeriesData data) {
        super(OBJECT_NAME);
        this.chartId = targetId;
        this.targetId = new JsString(targetId);
        this.data = checkData(data);
        this.nothingToDisplay = data.isEmpty();
        this.options = null;
    }

    /**
     * @param targetId must define div id where the graph will be shown in
     * @param data     SeriesData must not be null
     * @param options  Options must not be null
     */
    public JqPlot(final String targetId, final SeriesData data, final Options options) {
        super(OBJECT_NAME);
        this.chartId = targetId;
        this.targetId = new JsString(targetId);
        this.data = checkData(data);
        this.nothingToDisplay = data.isEmpty();
        this.options = requireNonNull(options, "Options must not be null");
    }

    /**
     * the jquery object for the dom target. this.target = null; The id of the dom
     * element to render the plot into
     */
    private final JsString targetId;

    @Getter
    private final String chartId;

    /**
     * container to hold all of the merged options
     */
    private final Options options;

    @Getter
    private boolean nothingToDisplay;

    /**
     * @param value
     * @return {@link JqPlot}
     */
    public JqPlot setNothingToDisplay(final boolean value) {
        this.nothingToDisplay = value;
        return this;
    }

    /**
     * user's data. Data should *NOT* be specified in the options object, but be
     * passed in as the second argument to the $.jqplot() function. The data
     * property is described here soley for reference. The data should be in the
     * form of an array of 2D or 1D arrays like > [ [[x1, y1], [x2, y2],...], [y1,
     * y2, ...] ].
     */
    private final SeriesData data;

    private static SeriesData checkData(final SeriesData someData) {
        return requireNonNull(someData, "SeriesData must not be null");
    }

    private String getOptions() {
        if (null == options) {
            return "null";
        }
        return options.asJavaScriptObjectNotation();
    }

    private final HookFunctionsManager hooksManager = new HookFunctionsManager();

    /**
     * Add hook function
     *
     * @param hookFunction {@linkplain PlotHookFunctionProvider}
     * @return {@linkplain JqPlot} in fluent api style
     * @throws IllegalArgumentException if hookFunction is {@code null} or already
     *                                  defined
     */
    public JqPlot addHookFunction(final PlotHookFunctionProvider hookFunction) {
        hooksManager.addHookFunction(hookFunction);
        return this;
    }

    @Override
    public String asJavaScriptObjectNotation() {
        if (nothingToDisplay) {
            return "'';";
        }

        final var builder = new StringBuilder(OBJECT_NAME).append("(").append(targetId.getValueAsString()).append(", ")
                .append(data.asJavaScriptObjectNotation()).append(", ").append(getOptions()).append(");");

        if (null != options) {
            builder.append(options.getHookFunctionCode());
        }

        builder.append(hooksManager.getHooksFunctionCode());

        return builder.toString();
    }

    /* Component specific informations */

    private final PluginSupport plugins = new PluginSupport();

    /**
     * Depending on used renderer a and options there is a need to import more js
     * libraries / extensions.
     *
     * @return the available plugins
     */
    public List<String> getPlugins() {
        if (nothingToDisplay) {
            return new ArrayList<>();
        }
        plugins.add(options);
        return plugins.usedPlugins();
    }

    /**
     * JqPlot provide lazy initialization for parameter objects
     *
     * @author Eugen Fischer
     */
    @ToString
    @EqualsAndHashCode
    public static class Builder implements Serializable {

        /** serial version UID */
        private static final long serialVersionUID = -2769478513025362362L;

        private String _targetId;

        private SeriesData _data;

        private Options _options;

        /**
         * @param value chart id must not be null or empty
         * @return fluent api style
         */
        public Builder useChartId(final String value) {
            final var trimmed = emptyToNull(value);
            final var checked = requireNonNull(trimmed, "Chart id must not be null or empty");
            if (null != _targetId) {
                throw new IllegalStateException("Chart id already defined.");
            }
            _targetId = checked;
            return this;
        }

        /**
         * Initialize lazy SeriesData object
         *
         * @return {@link SeriesData}
         */
        public SeriesData useData() {
            if (null == _data) {
                _data = new SeriesData();
            }
            return _data;
        }

        /**
         * Initialize lazy options object
         *
         * @return {@link Options}
         */
        public Options useOptions() {
            if (null == _options) {
                _options = new Options();
            }
            return _options;
        }

        /**
         * @return {@link JqPlot} according used objects
         * @throws IllegalStateException if chart id is missing
         * @throws IllegalStateException series data is missing
         */
        public JqPlot build() {
            checkState(null != _targetId, "Chart id must be defined");
            checkState(null != _data, "SeriesData id must be defined");
            if (null == _options) {
                return new JqPlot(_targetId, _data);
            }
            return new JqPlot(_targetId, _data, _options);
        }

    }

}
