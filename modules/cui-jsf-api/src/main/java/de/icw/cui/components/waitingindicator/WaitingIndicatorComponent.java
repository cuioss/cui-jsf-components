package de.icw.cui.components.waitingindicator;

import javax.faces.component.FacesComponent;
import javax.faces.context.FacesContext;

import com.icw.ehf.cui.components.bootstrap.BootstrapFamily;
import com.icw.ehf.cui.core.api.components.base.AbstractBaseCuiComponent;
import com.icw.ehf.cui.core.api.components.partial.ContextSizeProvider;
import com.icw.ehf.cui.core.api.components.util.ComponentUtility;

import lombok.experimental.Delegate;

/**
 * Displays a waiting indicator.
 */
@FacesComponent(BootstrapFamily.WAITING_INDICATOR_COMPONENT)
public class WaitingIndicatorComponent extends AbstractBaseCuiComponent {

    @Delegate
    private final ContextSizeProvider contextSizeProvider;

    /**
     *
     */
    public WaitingIndicatorComponent() {
        super();
        super.setRendererType(BootstrapFamily.WAITING_INDICATOR_RENDERER);
        contextSizeProvider = new ContextSizeProvider(this, "md");
    }

    @Override
    public String getFamily() {
        return BootstrapFamily.COMPONENT_FAMILY;
    }

    /**
     * Create a new instance using the faces context.
     *
     * @param facesContext the current faces context
     * @return a new instance
     */
    public static WaitingIndicatorComponent createComponent(final FacesContext facesContext) {
        return ComponentUtility.createComponent(facesContext, BootstrapFamily.WAITING_INDICATOR_COMPONENT);
    }
}
