package de.cuioss.jsf.components.chart.options;

import java.io.Serializable;

import de.cuioss.jsf.components.chart.options.color.Color;
import lombok.Data;

/**
 * @author Oliver Wolff
 *
 */
@Data
public class FillOptions implements Serializable {

    /** serial Version UID */
    private static final long serialVersionUID = -4743127216847493275L;

    /** first index (0 based) of series in fill */
    private Integer series1 = null;

    /** second index (0 based) of series in fill */
    private Integer series2 = null;

    /** color of fill [default fillColor of series1] */
    private Color color = null;

    /** fill will be drawn below this series (0 based index) */
    private Integer baseSeries = 0;

    /** false to turn off fill [default true] */
    private boolean fill = true;
}
