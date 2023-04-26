package de.cuioss.jsf.bootstrap.waitingindicator;

import javax.faces.component.FacesComponent;
import javax.faces.context.FacesContext;

import de.cuioss.jsf.api.components.base.AbstractBaseCuiComponent;
import de.cuioss.jsf.api.components.partial.ContextSizeProvider;
import de.cuioss.jsf.api.components.util.ComponentUtility;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
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
