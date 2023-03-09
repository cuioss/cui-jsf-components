package com.icw.ehf.cui.components.chart.hook;

import static java.lang.String.format;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * JqPlot mouse event hook
 *
 * @author i000576
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class JqPlotMouseEventBinding extends PlotHookFunctionProviderImpl {

    private static final long serialVersionUID = -7011701148617850704L;

    /**
     * {@linkplain JqPlotMouseEventBinding} Constructor delegate to
     * {@linkplain PlotHookFunctionProviderImpl}
     *
     * @param identifier
     * @param functionContent
     */
    public JqPlotMouseEventBinding(String identifier, String functionContent) {
        super(identifier, functionContent);
    }

    /*
     * Prepared hooks
     */

    /**
     * Placeholder:
     * <ol>
     * <li>is chart id. example: #chart</li>
     * <li>is function logic</li>
     * </ol>
     */
    private static final String MOUSE_DOWN_BINDING_TEMPALTE =
        "$('#%s').bind('jqplotMouseDown',function(ev,seriesIndex,pointIndex,data){%s});";

    /**
     * Create JavaScript event binding for JqPlot on mouse down event
     *
     * @param plotId
     * @param functionImp
     * @return {@linkplain JqPlotMouseEventBinding}
     */
    public static JqPlotMouseEventBinding mouseDownEventBinding(final String plotId,
            final String functionImp) {

        return new JqPlotMouseEventBinding("mouseDownEvent_hook",
                format(MOUSE_DOWN_BINDING_TEMPALTE, plotId, functionImp));
    }

    /**
     * Placeholder:
     * <ol>
     * <li>is chart id. example: #chart</li>
     * <li>is function logic</li>
     * </ol>
     */
    private static final String MOUSE_UP_BINDING_TEMPALTE =
        "$('#%s').bind('jqplotMouseUp',function(ev,seriesIndex,pointIndex,data){%s});";

    /**
     * Create JavaScript event binding for JqPlot on mouse up event
     *
     * @param plotId
     * @param functionImp
     * @return {@linkplain JqPlotMouseEventBinding}
     */
    public static JqPlotMouseEventBinding mouseUpEventBinding(final String plotId,
            final String functionImp) {

        return new JqPlotMouseEventBinding("mouseUpEvent_hook",
                format(MOUSE_UP_BINDING_TEMPALTE, plotId, functionImp));
    }

    /**
     * Placeholder:
     * <ol>
     * <li>is chart id. example: #chart</li>
     * <li>is function logic</li>
     * </ol>
     */
    private static final String MOUSE_DBL_CLICK_BINDING_TEMPALTE =
        "$('#%s').bind('jqplotDblClick',function(ev,seriesIndex,pointIndex,data){%s});";

    /**
     * Create JavaScript event binding for JqPlot on mouse double click event
     *
     * @param plotId
     * @param functionImp
     * @return {@linkplain JqPlotMouseEventBinding}
     */
    public static JqPlotMouseEventBinding mouseDblClickEventBinding(final String plotId,
            final String functionImp) {

        return new JqPlotMouseEventBinding("mouseDblClickEvent_hook",
                format(MOUSE_DBL_CLICK_BINDING_TEMPALTE, plotId, functionImp));
    }
}
