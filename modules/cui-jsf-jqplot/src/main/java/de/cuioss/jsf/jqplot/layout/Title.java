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
package de.cuioss.jsf.jqplot.layout;

import de.cuioss.jsf.jqplot.js.support.JsObject;
import de.cuioss.jsf.jqplot.js.types.JsBoolean;
import de.cuioss.jsf.jqplot.js.types.JsString;
import de.cuioss.jsf.jqplot.options.color.Color;
import de.cuioss.jsf.jqplot.renderer.DivTitleRenderer;
import de.cuioss.jsf.jqplot.renderer.RendererOptions;
import de.cuioss.jsf.jqplot.renderer.TitleRenderer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * <pre>
 * title: '', // Title for the plot.
 * </pre>
 *
 * Can also be specified as an object like:
 *
 * <pre>
 * title: {
 * text: '', // title for the plot,
 * show: true,
 * }
 * </pre>
 *
 * @author Eugen Fischer
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Title extends JsObject {

    private static final String JS_OBJECT_NAME = "title";

    /** serial Version UID */
    @Serial
    private static final long serialVersionUID = 6433109335237410535L;

    /**
     * text of the title
     */
    private JsString text;

    /**
     * weather or not to show the title
     */
    private JsBoolean show;

    /**
     * css font-family spec for the text.
     */
    private JsString fontFamily;

    /**
     * css font-size spec for the text.
     */
    private JsString fontSize;

    /**
     * css text-align spec for the text
     */
    private JsString textAlign;

    /**
     * css color spec for the text.
     */
    private Color textColor;

    /**
     * A class for creating a DOM element for the title.
     * <p>
     * see {@link DivTitleRenderer} which is a default jqPlot title renderer
     */
    private TitleRenderer renderer;

    /**
     * Renderer specific options passed to the renderer.
     */
    private RendererOptions rendererOptions;

    /**
     * True to escape special characters with their html entity equivalents in title
     * text. "<" becomes &lt; and so on, so html tags are not rendered.
     */
    private JsBoolean escapeHtml;

    public Title() {
        super(JS_OBJECT_NAME);
    }

    /**
     * @param titleText
     */
    public Title(final String titleText) {
        this();
        text = new JsString(titleText);
        escapeHtml = JsBoolean.create(Boolean.TRUE);
    }

    @Override
    public String asJavaScriptObjectNotation() {
        this.addProperty("text", text);
        this.addProperty("show", show);
        this.addProperty("fontFamily", fontFamily);
        this.addProperty("textAlign", textAlign);
        this.addProperty("textColor", textColor);
        this.addProperty("renderer", renderer);
        this.addProperty(rendererOptions);
        this.addProperty("escapeHtml", escapeHtml);
        return createAsJSON();
    }

}
