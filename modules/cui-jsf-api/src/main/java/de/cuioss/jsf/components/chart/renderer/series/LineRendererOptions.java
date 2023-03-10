package de.cuioss.jsf.components.chart.renderer.series;

import de.cuioss.jsf.components.chart.renderer.RendererOptions;
import de.cuioss.jsf.components.chart.renderer.highlight.Highlighting;
import de.cuioss.jsf.components.chart.renderer.highlight.IHighlightDecoration;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Delegate;

/**
 * @author i000576
 * @see <a href="http://www.jqplot.com/docs/files/jqplot-lineRenderer-js.html">LineRenderer</a>
 */
@ToString
@EqualsAndHashCode(callSuper = false)
public class LineRendererOptions extends RendererOptions implements
        IHighlightDecoration<LineRendererOptions> {

    /** serial Version UID */
    private static final long serialVersionUID = -6109587609259784491L;

    @Delegate
    private final Highlighting<LineRendererOptions> highlightDecorator;

    /**
     * LineRendererOptions Constructor
     */
    public LineRendererOptions() {
        super();
        highlightDecorator = new Highlighting<>(this);
    }

    @Override
    protected void addPropertiesForJsonObject() {
        this.addProperties(highlightDecorator);
    }

}
