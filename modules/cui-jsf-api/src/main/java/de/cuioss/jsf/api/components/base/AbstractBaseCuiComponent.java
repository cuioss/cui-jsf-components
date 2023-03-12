package de.cuioss.jsf.api.components.base;

import javax.faces.component.StateHelper;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;

import de.cuioss.jsf.api.components.partial.ComponentBridge;
import de.cuioss.jsf.api.components.partial.ComponentStyleClassProvider;
import de.cuioss.jsf.api.components.partial.ComponentStyleClassProviderImpl;
import de.cuioss.jsf.api.components.partial.StyleAttributeProvider;
import de.cuioss.jsf.api.components.partial.StyleAttributeProviderImpl;
import lombok.experimental.Delegate;

/**
 * Minimal super-set for cui-based components that are no
 * {@link UINamingContainer}. Therefore it provides the handling of the
 * styleClass and style attribute and the implicit attributes provided by
 * {@link UIComponentBase}. In addition it acts as a {@link ComponentBridge}
 *
 * @author Oliver Wolff
 */
public abstract class AbstractBaseCuiComponent extends UIComponentBase
        implements ComponentBridge, ComponentStyleClassProvider, StyleAttributeProvider {

    @Delegate
    private final ComponentStyleClassProvider styleClassProvider;

    @Delegate
    private final StyleAttributeProvider styleAttributeProvider;

    /**
     *
     */
    protected AbstractBaseCuiComponent() {
        styleClassProvider = new ComponentStyleClassProviderImpl(this);
        styleAttributeProvider = new StyleAttributeProviderImpl(this);
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
    public UIComponent facet(String facetName) {
        return getFacet(facetName);
    }
}
