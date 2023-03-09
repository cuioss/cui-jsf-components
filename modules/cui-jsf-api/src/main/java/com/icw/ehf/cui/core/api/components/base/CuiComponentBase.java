package com.icw.ehf.cui.core.api.components.base;

import javax.faces.component.StateHelper;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

import com.icw.ehf.cui.components.CuiFamily;
import com.icw.ehf.cui.core.api.components.partial.ComponentBridge;

/**
 * Base class for creating custom cui-components that are {@link UIComponentBase} without further
 * specification. It acts as a component bridge.
 *
 * @author Oliver Wolff
 */
public class CuiComponentBase extends UIComponentBase implements ComponentBridge {

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
        return CuiFamily.COMPONENT_FAMILY;
    }

}
