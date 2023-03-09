package com.icw.ehf.cui.core.api.components.base;

import javax.faces.component.StateHelper;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.context.FacesContext;

import com.icw.ehf.cui.core.api.components.partial.ComponentBridge;
import com.icw.ehf.cui.core.api.components.partial.TitleProvider;
import com.icw.ehf.cui.core.api.components.partial.TitleProviderImpl;

import lombok.experimental.Delegate;

/**
 * Base class for creating cui variants of {@link HtmlCommandButton} implementing
 * {@link ComponentBridge} and {@link TitleProvider}
 *
 * @author Oliver Wolff
 *
 */
public class BaseCuiCommandButton extends HtmlCommandButton implements ComponentBridge, TitleProvider {

    @Delegate
    private final TitleProvider titleProvider;

    /**
     *
     */
    public BaseCuiCommandButton() {
        titleProvider = new TitleProviderImpl(this);
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
    public UIComponent facet(final String facetName) {
        return getFacet(facetName);
    }

    @Override
    public String getTitle() {
        return titleProvider.resolveTitle();
    }

}
