package de.cuioss.jsf.components.chart.layout;

import de.cuioss.jsf.components.chart.options.color.Color;
import de.cuioss.jsf.components.chart.renderer.DivTitleRenderer;
import de.cuioss.jsf.components.chart.renderer.RendererOptions;
import de.cuioss.jsf.components.chart.renderer.TitleRenderer;
import de.cuioss.jsf.components.js.support.JsObject;
import de.cuioss.jsf.components.js.types.JsBoolean;
import de.cuioss.jsf.components.js.types.JsString;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
    private static final long serialVersionUID = 6433109335237410535L;

    /**
     * text of the title
     */
    private JsString text;

    /**
     * wether or not to show the title
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
     * True to escape special characters with their html entity equivalents in title text. "<"
     * becomes &lt; and so on, so html tags are not rendered.
     */
    private JsBoolean escapeHtml;

    /**
     *
     */
    public Title() {
        super(JS_OBJECT_NAME);
    }

    /**
     * @param titleText
     */
    public Title(final String titleText) {
        this();
        this.text = new JsString(titleText);
        this.escapeHtml = JsBoolean.create(Boolean.TRUE);
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
        return this.createAsJSON();
    }

}
