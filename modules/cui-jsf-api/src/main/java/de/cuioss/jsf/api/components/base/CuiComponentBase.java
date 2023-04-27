package de.cuioss.jsf.api.components.base;

import javax.faces.component.StateHelper;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

import de.cuioss.jsf.api.components.partial.ComponentBridge;

/**
 * Base class for creating custom cui-components that are {@link UIComponentBase} without further
 * specification. It acts as a component bridge.
 *
 * @author Oliver Wolff
 */
public class CuiComponentBase extends UIComponentBase implements ComponentBridge {

    /** "de.cuioss.jsf.api.html.family" */
    public static final String COMPONENT_FAMILY = "de.cuioss.jsf.api.html.family";

    @Override
    public FacesContext facesContext() {
        return getFacesContext();
    }

    @Override
    public UIComponent facet(final String facetName) {
        return getFacet(facetName);
    }

    @Override
    public StateHelper stateHelper() {
        return getStateHelper();
    }

    @Override
    public String getFamily() {
        return COMPONENT_FAMILY;
    }

}
