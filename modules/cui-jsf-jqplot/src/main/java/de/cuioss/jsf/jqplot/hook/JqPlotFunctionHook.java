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
package de.cuioss.jsf.jqplot.hook;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;

/**
 * To be able to override JqPlot intern function this class could be used
 *
 * @author Eugen Fischer
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class JqPlotFunctionHook extends PlotHookFunctionProviderImpl {

    @Serial
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

        return new JqPlotFunctionHook("destroyRedraw_hook", DESTROY_REDRAW.formatted(plotVarId));
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

        return new JqPlotFunctionHook("resetZoomEvent_hook", JQPLOT_RESET_ZOOM_TEMPALTE.formatted(plotId, functionImp));
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

        return new JqPlotFunctionHook("zoomEvent_hook", JQPLOT_ZOOM_TEMPALTE.formatted(plotId, functionImp));
    }
}
