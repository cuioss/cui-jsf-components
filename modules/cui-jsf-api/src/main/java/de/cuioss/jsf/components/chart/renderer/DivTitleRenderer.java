package de.cuioss.jsf.components.chart.renderer;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * The default title renderer for jqPlot. This class has no options beyond the <Title> class.
 *
 * @author i000576
 */
@ToString
@EqualsAndHashCode(callSuper = true)
public class DivTitleRenderer extends TitleRenderer {

    /** serial Version UID */
    private static final long serialVersionUID = 3803805522578522544L;

    /**
     *
     */
    public DivTitleRenderer() {
        super("$.jqplot.DivTitleRenderer");
    }

}
