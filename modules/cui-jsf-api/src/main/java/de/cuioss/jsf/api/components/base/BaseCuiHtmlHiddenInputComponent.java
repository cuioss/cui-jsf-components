package de.cuioss.jsf.api.components.base;

import javax.faces.component.StateHelper;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.context.FacesContext;

import de.cuioss.jsf.api.components.partial.ComponentBridge;

/**
 * Minimal super-set for cui-based components that are at least {@link HtmlInputHidden}.
 * It acts as a {@link ComponentBridge}
 *
 * @author Sven Haag
 */
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public class BaseCuiHtmlHiddenInputComponent extends HtmlInputHidden
        implements ComponentBridge {

    @Override
    public StateHelper stateHelper() {
        return getStateHelper();
    }

    @Override
    public FacesContext facesContext() {
        return getFacesContext();
    }

    @Override
    public UIComponent facet(String facetName) {
        return getFacet(facetName);
    }
}
