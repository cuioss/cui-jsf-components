package com.icw.ehf.cui.components.bootstrap.layout;

import javax.faces.component.FacesComponent;

import com.icw.ehf.cui.components.bootstrap.BootstrapFamily;
import com.icw.ehf.cui.components.bootstrap.CssBootstrap;
import com.icw.ehf.cui.core.api.components.css.AlignHolder;
import com.icw.ehf.cui.core.api.components.css.StyleClassBuilder;
import com.icw.ehf.cui.core.api.components.partial.AlignProvider;

import lombok.experimental.Delegate;

/**
 * Wraps a number of buttons. Quick means solely appending the CSS-class
 * 'quick-control-group' and the styleClass attribute, if set, will be attached
 * to the surrounding DIV. For more complex layouts use controlGroup
 * ({@link ControlGroupComponent}}).
 * Rendered by javax.faces.Group.
 *
 * @author Sven Haag, Sven Haag
 */
@FacesComponent(BootstrapFamily.QUICK_CONTROL_GROUP_COMPONENT)
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public class QuickControlGroupComponent extends AbstractLayoutComponent {

    @Delegate
    private final AlignProvider alignProvider;

    /**
     * Creates a QuickControlGroupComponent with {@link BootstrapFamily#LAYOUT_RENDERER} renderer
     * and {@link AlignProvider} as align provider.
     */
    public QuickControlGroupComponent() {
        super();
        alignProvider = new AlignProvider(this);
        super.setRendererType(BootstrapFamily.LAYOUT_RENDERER);
    }

    @Override
    public StyleClassBuilder resolveStyleClass() {
        if (AlignHolder.LEFT.equals(alignProvider.resolveAlign())) {
            return CssBootstrap.QUICK_CONTROL_GROUP_LEFT.getStyleClassBuilder().append(super.getStyleClass());
        }
        return CssBootstrap.QUICK_CONTROL_GROUP_RIGHT.getStyleClassBuilder().append(super.getStyleClass());
    }
}
