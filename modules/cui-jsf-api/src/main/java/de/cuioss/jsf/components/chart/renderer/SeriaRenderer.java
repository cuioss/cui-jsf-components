package de.cuioss.jsf.components.chart.renderer;

import de.cuioss.jsf.components.chart.renderer.series.BarRendererOptions;
import de.cuioss.jsf.components.chart.renderer.series.LineRendererOptions;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Eugen Fischer
 * @param <R> at least {@link RendererOptions}
 */
@ToString
@EqualsAndHashCode(callSuper = true)
public class SeriaRenderer<R extends RendererOptions> extends Renderer {

    /** serial Version UID */
    private static final long serialVersionUID = -3845495653825273506L;

    private static final SeriaRenderer<BarRendererOptions> BAR_RENDERER =
        new SeriaRenderer<>("$.jqplot.BarRenderer", "jqplot.barRenderer.min.js");

    private static final SeriaRenderer<LineRendererOptions> LINE_RENDERER =
        new SeriaRenderer<>("$.jqplot.LineRenderer");

    private SeriaRenderer(final String rendererName, final String... plugins) {
        super(rendererName);
        for (final String plugin : plugins) {
            super.addPlugin(plugin);
        }
    }

    /**
     * Create BarRenderer
     *
     * @see <a href=
     *      "http://www.jqplot.com/docs/files/plugins/jqplot-barRenderer-js.html">BarRenderer</a>
     * @return SeriaRenderer
     */
    public static SeriaRenderer<BarRendererOptions> createBarRenderer() {
        return BAR_RENDERER;
    }

    /**
     * Create default Line Renderer
     *
     * @see <a href=
     *      "http://www.jqplot.com/docs/files/jqplot-lineRenderer-js.html#$.jqplot.LineRenderer">LineRenderer</a>
     * @return SeriaRenderer
     */
    public static SeriaRenderer<LineRendererOptions> createLineRenderer() {
        return LINE_RENDERER;
    }

    /**
     * Factory Method create fitting Renderer
     *
     * @param renderer
     * @return the fitting {@link RendererOptions}
     */
    @SuppressWarnings("unchecked")
    public static <T extends RendererOptions> T createFitting(final SeriaRenderer<T> renderer) {

        if (BAR_RENDERER.equals(renderer)) {
            return (T) new BarRendererOptions();
        }

        return null;
    }
}
