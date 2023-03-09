package com.icw.ehf.cui.components.chart.renderer;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Oliver Wolff
 *
 */
@ToString
@EqualsAndHashCode(callSuper = false)
public class AxisTickRenderer extends Renderer {

    /** serial Version UID */
    private static final long serialVersionUID = 7702530251397724324L;

    /**
     *
     */
    public AxisTickRenderer() {
        super("$.jqplot.CanvasAxisTickRenderer");
        this.addPlugin("jqplot.canvasTextRenderer.min.js");
        this.addPlugin("jqplot.canvasAxisLabelRenderer.min.js");
        this.addPlugin("jqplot.canvasAxisTickRenderer.min.js");
    }
}
