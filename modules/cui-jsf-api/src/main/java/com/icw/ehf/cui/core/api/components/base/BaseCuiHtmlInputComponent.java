package com.icw.ehf.cui.core.api.components.base;

import javax.faces.component.StateHelper;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;

import com.icw.ehf.cui.core.api.components.partial.ComponentBridge;
import com.icw.ehf.cui.core.api.components.partial.ComponentStyleClassProvider;
import com.icw.ehf.cui.core.api.components.partial.ComponentStyleClassProviderImpl;
import com.icw.ehf.cui.core.api.components.partial.StyleAttributeProvider;
import com.icw.ehf.cui.core.api.components.partial.StyleAttributeProviderImpl;

import lombok.experimental.Delegate;

/**
 * Minimal super-set for cui-based components that are at least {@link HtmlInputText}.
 * Therefore it provides the handling of the styleClass and style attribute and
 * the implicit attributes provided by {@link HtmlInputText}. In addition it acts as a
 * {@link ComponentBridge}
 *
 * @author Sven Haag
 */
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public class BaseCuiHtmlInputComponent extends HtmlInputText
        implements ComponentBridge, ComponentStyleClassProvider, StyleAttributeProvider {

    @Delegate
    private final ComponentStyleClassProvider styleClassProvider;

    @Delegate
    private final StyleAttributeProvider styleAttributeProvider;

    /**
     *
     */
    public BaseCuiHtmlInputComponent() {
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
