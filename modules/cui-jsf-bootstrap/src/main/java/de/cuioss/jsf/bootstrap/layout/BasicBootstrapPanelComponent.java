package de.cuioss.jsf.bootstrap.layout;

import javax.faces.component.UIComponent;

import de.cuioss.jsf.api.components.base.BaseCuiPanel;
import de.cuioss.jsf.bootstrap.BootstrapFamily;

/**
 * Base class for our layout components. It extends {@link BaseCuiPanel} and
 * provides {@link UIComponent#getFamily()} returning
 * {@link BootstrapFamily#COMPONENT_FAMILY}
 *
 * @author Oliver Wolff
 *
 */
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public abstract class BasicBootstrapPanelComponent extends BaseCuiPanel {

    @Override
    public String getFamily() {
        return BootstrapFamily.COMPONENT_FAMILY;
    }

}
