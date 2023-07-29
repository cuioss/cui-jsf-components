package de.cuioss.jsf.api.components.base;

import javax.faces.component.StateHelper;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;

import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.api.components.partial.ComponentBridge;
import de.cuioss.jsf.api.components.partial.ComponentStyleClassProvider;
import de.cuioss.jsf.api.components.partial.ComponentStyleClassProviderImpl;
import de.cuioss.jsf.api.components.partial.TitleProvider;
import de.cuioss.jsf.api.components.partial.TitleProviderImpl;
import lombok.experimental.Delegate;

/**
 * <p>
 * Base class for creating CuiComponents that are based on on simple
 * span-element, The default renderer is "javax.faces.Text". It acts as
 * {@link ComponentBridge}.
 * </p>
 * <h2>Attributes</h2>
 * <ul>
 * <li>{@link TitleProvider}: This mechanism overrides
 * {@link HtmlOutputText#getTitle()}. In addition it will throw an
 * {@link UnsupportedOperationException} on calling
 * {@link HtmlOutputText#setTitle(String)}</li>
 * <li>All attributes from {@link HtmlOutputText}</li>
 * </ul>
 * <p>
 * The only thing the child-component needs to care is implementing
 * {@link #getComponentSpecificStyleClasses()} according to its needs.
 * </p>
 *
 * @author Oliver Wolff
 *
 */
public abstract class BaseCuiOutputText extends HtmlOutputText implements ComponentBridge {

    @Delegate
    private final TitleProvider titleProvider;

    private final ComponentStyleClassProvider styleClassProvider;

    /**
     *
     */
    protected BaseCuiOutputText() {
        titleProvider = new TitleProviderImpl(this);
        styleClassProvider = new ComponentStyleClassProviderImpl(this);
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
    public String getStyleClass() {
        return getComponentSpecificStyleClasses().append(styleClassProvider.getStyleClassBuilder()).getStyleClass();
    }

    @Override
    public void setStyleClass(final String styleClass) {
        styleClassProvider.setStyleClass(styleClass);
    }

    /**
     * @return the component specific style-classes. Must no be null.The parent
     *         component (BaseCuiOutputText) takes care on the configured styleClass
     *         attribute and implements the actual method
     *         {@link HtmlOutputText#getStyleClass()} by calling this method and
     *         appending the styleClass configured by the developer / concrete
     *         usage.
     */
    public abstract StyleClassBuilder getComponentSpecificStyleClasses();

    @Override
    public String getTitle() {
        return titleProvider.resolveTitle();
    }

}
