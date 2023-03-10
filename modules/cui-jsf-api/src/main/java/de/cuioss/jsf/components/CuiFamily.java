package de.cuioss.jsf.components;

import de.cuioss.jsf.components.blockelement.BlockElementDecorator;
import de.cuioss.jsf.components.html.fieldset.FieldsetComponent;
import de.cuioss.jsf.components.inlineconfirm.InlineConfirmComponent;
import de.cuioss.jsf.components.typewatch.TypewatchComponent;

/**
 * Simple Container for identifying cui components that are not related to twitter bootstrap
 *
 * @author Oliver Wolff
 */
public final class CuiFamily {

    /** The component for {@link FieldsetComponent} */
    public static final String FIELDSET_COMPONENT =
        "de.cuioss.jsf.api.html.fieldset";

    /** Default Renderer for {@link FieldsetComponent} */
    public static final String FIELDSET_RENDERER = "de.cuioss.jsf.api.html.fieldset_renderer";

    /** "com.icw.cui.components.bootstrap.family" */
    public static final String COMPONENT_FAMILY = "de.cuioss.jsf.api.html.family";

    /** The component for {@link TypewatchComponent} */
    public static final String TYPEWATCH_COMPONENT = "com.icw.cui.components.typewatch";

    /** The component for {@link InlineConfirmComponent} */
    public static final String INLINE_CONFIRM_COMPONENT = "com.icw.cui.components.inline_confirm";

    /** Default Renderer for {@link InlineConfirmComponent} */
    public static final String INLINE_CONFIRM_RENDERER = "com.icw.cui.components.inline_confirm_renderer";

    /** The component for {@link BlockElementDecorator} */
    public static final String BLOCKELEMENT_COMPONENT = "de.cuioss.jsf.components.blockelement";

    /**
     * Enforce utility class
     */
    private CuiFamily() {
    }
}
