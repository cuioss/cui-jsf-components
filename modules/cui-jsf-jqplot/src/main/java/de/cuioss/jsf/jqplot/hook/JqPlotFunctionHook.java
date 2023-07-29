package de.cuioss.jsf.jqplot.hook;

import static java.lang.String.format;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * To be able to override JqPlot intern function this class could be used
 *
 * @author Eugen Fischer
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class JqPlotFunctionHook extends PlotHookFunctionProviderImpl {

    private static final long serialVersionUID = -6023019028614377353L;

    /**
     * {@linkplain JqPlotFunctionHook} Constructor delegate to
     * {@linkplain PlotHookFunctionProviderImpl}
     *
     * @param identifier
     * @param functionContent
     */
    public JqPlotFunctionHook(String identifier, String functionContent) {
        super(identifier, functionContent);
    }

    /*
     * Prepared hooks
     */

    /**
     * Placeholder:
     * <ol>
     * <li>is variable id. example: plot1</li>
     * </ol>
     */
    private static final String DESTROY_REDRAW = "%s.redraw=function(clear){return};";

    /**
     * This shortcut create a JavaScript snippet, which destroy JqPlot redraw
     * function.<br>
     * This could be useful if you have complete mouse event control and want to
     * prevent redraw of graph on any mouse events.
     *
     * @param plotVarId expected is knowledge of plot variable name
     * @return {@linkplain JqPlotFunctionHook}
     */
    public static JqPlotFunctionHook destroyRedrawFunction(final String plotVarId) {

        return new JqPlotFunctionHook("destroyRedraw_hook", format(DESTROY_REDRAW, plotVarId));
    }

    /**
     * Placeholder:
     * <ol>
     * <li>is chart id. example: #chart</li>
     * <li>is function logic</li>
     * </ol>
     */
    private static final String JQPLOT_RESET_ZOOM_TEMPALTE = "$('#%s').bind('jqplotResetZoom',function(){%s});";

    /**
     * Create JavaScript event binding for JqPlot on reset zoom event
     *
     * @param plotId
     * @param functionImp
     * @return {@linkplain JqPlotFunctionHook}
     */
    public static JqPlotFunctionHook resetZoomEventBinding(final String plotId, final String functionImp) {

        return new JqPlotFunctionHook("resetZoomEvent_hook", format(JQPLOT_RESET_ZOOM_TEMPALTE, plotId, functionImp));
    }

    /**
     * Placeholder:
     * <ol>
     * <li>is chart id. example: #chart</li>
     * <li>is function logic</li>
     * </ol>
     */
    private static final String JQPLOT_ZOOM_TEMPALTE = "$('#%s').bind('jqplotZoom',function(ev,gridpos,datapos,plot,cursor){%s});";

    /**
     * Create JavaScript event binding for JqPlot on reset zoom event
     *
     * @param plotId
     * @param functionImp
     * @return {@linkplain JqPlotFunctionHook}
     */
    public static JqPlotFunctionHook zoomEventBinding(final String plotId, final String functionImp) {

        return new JqPlotFunctionHook("zoomEvent_hook", format(JQPLOT_ZOOM_TEMPALTE, plotId, functionImp));
    }
}
