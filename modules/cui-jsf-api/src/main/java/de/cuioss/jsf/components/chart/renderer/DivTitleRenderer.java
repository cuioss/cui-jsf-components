package de.cuioss.jsf.components.chart.renderer;

import de.cuioss.jsf.components.chart.layout.Title;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * The default title renderer for jqPlot. This class has no options beyond the {@link Title}> class.
 *
 * @author Eugen Fischer
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
