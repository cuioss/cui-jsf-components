/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.jqplot.renderer;

import de.cuioss.jsf.jqplot.renderer.series.BarRendererOptions;
import de.cuioss.jsf.jqplot.renderer.series.LineRendererOptions;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;

/**
 * @author Eugen Fischer
 * @param <R> at least {@link RendererOptions}
 */
@ToString
@EqualsAndHashCode(callSuper = true)
public class SeriaRenderer<R extends RendererOptions> extends Renderer {

    /** serial Version UID */
    @Serial
    private static final long serialVersionUID = -3845495653825273506L;

    private static final SeriaRenderer<BarRendererOptions> BAR_RENDERER = new SeriaRenderer<>("$.jqplot.BarRenderer",
            "jqplot.barRenderer.min.js");

    private static final SeriaRenderer<LineRendererOptions> LINE_RENDERER = new SeriaRenderer<>(
            "$.jqplot.LineRenderer");

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
