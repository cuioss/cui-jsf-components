package com.icw.ehf.cui.components;

import com.icw.ehf.cui.components.html.fieldset.FieldsetComponent;
import com.icw.ehf.cui.components.inlineconfirm.InlineConfirmComponent;
import com.icw.ehf.cui.components.typewatch.TypewatchComponent;

import de.icw.cui.components.blockelement.BlockElementDecorator;

/**
 * Simple Container for identifying cui components that are not related to twitter bootstrap
 *
 * @author Oliver Wolff
 */
public final class CuiFamily {

    /** The component for {@link FieldsetComponent} */
    public static final String FIELDSET_COMPONENT =
        "de.icw.cui.html.fieldset";

    /** Default Renderer for {@link FieldsetComponent} */
    public static final String FIELDSET_RENDERER = "de.icw.cui.html.fieldset_renderer";

    /** "com.icw.cui.components.bootstrap.family" */
    public static final String COMPONENT_FAMILY = "de.icw.cui.html.family";

    /** The component for {@link TypewatchComponent} */
    public static final String TYPEWATCH_COMPONENT = "com.icw.cui.components.typewatch";

    /** The component for {@link InlineConfirmComponent} */
    public static final String INLINE_CONFIRM_COMPONENT = "com.icw.cui.components.inline_confirm";

    /** Default Renderer for {@link InlineConfirmComponent} */
    public static final String INLINE_CONFIRM_RENDERER = "com.icw.cui.components.inline_confirm_renderer";

    /** The component for {@link BlockElementDecorator} */
    public static final String BLOCKELEMENT_COMPONENT = "de.icw.cui.components.blockelement";

    /**
     * Enforce utility class
     */
    private CuiFamily() {
    }
}
