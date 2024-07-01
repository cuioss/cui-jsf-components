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
package de.cuioss.jsf.jqplot.hook;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;

/**
 * JqPlot mouse event hook
 *
 * @author Eugen Fischer
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class JqPlotMouseEventBinding extends PlotHookFunctionProviderImpl {

    @Serial
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
    private static final String MOUSE_DOWN_BINDING_TEMPALTE = "$('#%s').bind('jqplotMouseDown',function(ev,seriesIndex,pointIndex,data){%s});";

    /**
     * Create JavaScript event binding for JqPlot on mouse down event
     *
     * @param plotId
     * @param functionImp
     * @return {@linkplain JqPlotMouseEventBinding}
     */
    public static JqPlotMouseEventBinding mouseDownEventBinding(final String plotId, final String functionImp) {

        return new JqPlotMouseEventBinding("mouseDownEvent_hook",
                MOUSE_DOWN_BINDING_TEMPALTE.formatted(plotId, functionImp));
    }

    /**
     * Placeholder:
     * <ol>
     * <li>is chart id. example: #chart</li>
     * <li>is function logic</li>
     * </ol>
     */
    private static final String MOUSE_UP_BINDING_TEMPALTE = "$('#%s').bind('jqplotMouseUp',function(ev,seriesIndex,pointIndex,data){%s});";

    /**
     * Create JavaScript event binding for JqPlot on mouse up event
     *
     * @param plotId
     * @param functionImp
     * @return {@linkplain JqPlotMouseEventBinding}
     */
    public static JqPlotMouseEventBinding mouseUpEventBinding(final String plotId, final String functionImp) {

        return new JqPlotMouseEventBinding("mouseUpEvent_hook",
                MOUSE_UP_BINDING_TEMPALTE.formatted(plotId, functionImp));
    }

    /**
     * Placeholder:
     * <ol>
     * <li>is chart id. example: #chart</li>
     * <li>is function logic</li>
     * </ol>
     */
    private static final String MOUSE_DBL_CLICK_BINDING_TEMPALTE = "$('#%s').bind('jqplotDblClick',function(ev,seriesIndex,pointIndex,data){%s});";

    /**
     * Create JavaScript event binding for JqPlot on mouse double click event
     *
     * @param plotId
     * @param functionImp
     * @return {@linkplain JqPlotMouseEventBinding}
     */
    public static JqPlotMouseEventBinding mouseDblClickEventBinding(final String plotId, final String functionImp) {

        return new JqPlotMouseEventBinding("mouseDblClickEvent_hook",
                MOUSE_DBL_CLICK_BINDING_TEMPALTE.formatted(plotId, functionImp));
    }
}
