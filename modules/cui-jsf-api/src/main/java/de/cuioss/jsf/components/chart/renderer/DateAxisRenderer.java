package de.cuioss.jsf.components.chart.renderer;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Date axis renderer
 *
 * @see <a href=
 *      "http://www.jqplot.com/docs/files/plugins/jqplot-dateAxisRenderer-js.html">DateAxisRenderer</a>
 * @author i000576
 */
@ToString
@EqualsAndHashCode(callSuper = true)
public class DateAxisRenderer extends Renderer {

    /** serial Version UID */
    private static final long serialVersionUID = 9217420263322358353L;

    /**
     *
     */
    public DateAxisRenderer() {
        super("$.jqplot.DateAxisRenderer");
        this.addPlugin("jqplot.dateAxisRenderer.min.js");
    }

}
