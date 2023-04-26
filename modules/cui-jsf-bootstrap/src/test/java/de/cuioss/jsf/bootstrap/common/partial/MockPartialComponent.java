package de.cuioss.jsf.bootstrap.common.partial;

import javax.faces.component.StateHelper;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

import de.cuioss.jsf.api.components.partial.ComponentBridge;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import lombok.experimental.Delegate;

/**
 * Uses all available provider
 *
 * @author Oliver Wolff
 */
@SuppressWarnings("javadoc")
public class MockPartialComponent extends UIComponentBase implements ComponentBridge {

    @Delegate
    private final ColumnProvider columnProvider;

    public MockPartialComponent() {
        columnProvider = new ColumnProvider(this);

    }

    @Override
    public StateHelper stateHelper() {
        return getStateHelper();
    }

    @Override
    public FacesContext facesContext() {
        return getFacesContext();
    }

    @Override
    public String getFamily() {
        return BootstrapFamily.COMPONENT_FAMILY;
    }

    @Override
    public UIComponent facet(String facetName) {
        return getFacet(facetName);
    }
}
