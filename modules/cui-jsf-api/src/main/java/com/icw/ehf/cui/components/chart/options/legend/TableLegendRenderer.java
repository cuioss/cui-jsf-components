package com.icw.ehf.cui.components.chart.options.legend;

import com.icw.ehf.cui.components.chart.renderer.Renderer;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Oliver Wolff
 *
 */
@ToString
@EqualsAndHashCode(callSuper = false)
public class TableLegendRenderer extends Renderer {

    /** serial Version UID */
    private static final long serialVersionUID = -1028698106797046622L;

    /**
     *
     */
    public TableLegendRenderer() {
        super("$.jqplot.EnhancedLegendRenderer");
        this.addPlugin("jqplot.enhancedLegendRenderer.min.js");
    }

}
