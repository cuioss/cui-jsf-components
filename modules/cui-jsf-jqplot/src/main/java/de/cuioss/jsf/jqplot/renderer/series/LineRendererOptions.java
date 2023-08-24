/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.jqplot.renderer.series;

import de.cuioss.jsf.jqplot.renderer.RendererOptions;
import de.cuioss.jsf.jqplot.renderer.highlight.Highlighting;
import de.cuioss.jsf.jqplot.renderer.highlight.IHighlightDecoration;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Delegate;

/**
 * @author Eugen Fischer
 * @see <a href=
 *      "http://www.jqplot.com/docs/files/jqplot-lineRenderer-js.html">LineRenderer</a>
 */
@ToString
@EqualsAndHashCode(callSuper = false)
public class LineRendererOptions extends RendererOptions implements IHighlightDecoration<LineRendererOptions> {

    /** serial Version UID */
    private static final long serialVersionUID = -6109587609259784491L;

    @Delegate
    private final Highlighting<LineRendererOptions> highlightDecorator;

    /**
     * LineRendererOptions Constructor
     */
    public LineRendererOptions() {
        highlightDecorator = new Highlighting<>(this);
    }

    @Override
    protected void addPropertiesForJsonObject() {
        addProperties(highlightDecorator);
    }

}
