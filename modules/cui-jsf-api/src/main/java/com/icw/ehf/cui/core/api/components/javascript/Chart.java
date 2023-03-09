package com.icw.ehf.cui.core.api.components.javascript;

/**
 * @author Oliver Wolff
 *
 */
public class Chart implements ScriptProvider {

    /** "value" */
    public static final String OPTION_KEY_VALUE = "value";

    /** "color" */
    public static final String OPTION_KEY_COLOR = "color";

    /** "highlight" */
    public static final String OPTION_KEY_HIGHLIGHT = "highlight";

    /** "label" */
    public static final String OPTION_KEY_LABEL = "label";

    /** "labels" */
    public static final String OPTION_KEY_LABELS = "labels";

    /** "fillColor" */
    public static final String OPTION_KEY_FILL_COLOR = "fillColor";

    /** "strokeColor" */
    public static final String OPTION_KEY_STROKE_COLOR = "strokeColor";

    /** "highlightFill" */
    public static final String OPTION_KEY_HIGHLIGHT_FILL = "highlightFill";

    /** "highlightStroke" */
    public static final String OPTION_KEY_HIGHLIGHT_STROKE = "highlightStroke";

    /** "data" */
    public static final String OPTION_KEY_DATA = "data";

    @Override
    public String script() {
        return null;
    }

}
