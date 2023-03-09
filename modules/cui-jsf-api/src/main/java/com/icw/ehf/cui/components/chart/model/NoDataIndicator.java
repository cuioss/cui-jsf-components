package com.icw.ehf.cui.components.chart.model;

import java.io.Serializable;

import com.icw.ehf.cui.components.chart.axes.Axes;

import lombok.Data;

/**
 * Options to set up a mock plot with a data loading indicator if no data is specified.
 * Example:
 *
 * <pre>
 * show: false,
 *           indicator: 'Loading Data...',
 *           axes: {
 *               xaxis: {
 *                   min: 0,
 *                   max: 10,
 *                   tickInterval: 2,
 *                   show: true
 *               },
 *               yaxis: {
 *                   min: 0,
 *                   max: 12,
 *                   tickInterval: 3,
 *                   show: true
 *               }
 *           }
 * </pre>
 *
 * @author i000576
 */
@Data
public class NoDataIndicator implements Serializable {

    /** serial Version UID */
    private static final long serialVersionUID = -1690798530650850562L;

    private boolean show = false;

    private String indicator;

    private Axes axes;
}
