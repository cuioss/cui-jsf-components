package com.icw.ehf.cui.components.bootstrap.layout;

import javax.faces.component.UIComponent;

import com.icw.ehf.cui.components.bootstrap.BootstrapFamily;
import com.icw.ehf.cui.core.api.components.base.BaseCuiPanel;

/**
 * Base class for our layout components. It extends {@link BaseCuiPanel} and provides
 * {@link UIComponent#getFamily()} returning
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
