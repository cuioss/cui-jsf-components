package de.cuioss.jsf.bootstrap.link;

import javax.faces.component.FacesComponent;
import javax.faces.component.StateHelper;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputLink;
import javax.faces.context.FacesContext;

import de.cuioss.jsf.api.components.css.AlignHolder;
import de.cuioss.jsf.api.components.partial.ComponentBridge;
import de.cuioss.jsf.api.components.partial.ComponentStyleClassProvider;
import de.cuioss.jsf.api.components.partial.ComponentStyleClassProviderImpl;
import de.cuioss.jsf.api.components.partial.ContextSizeProvider;
import de.cuioss.jsf.api.components.partial.ContextStateProvider;
import de.cuioss.jsf.api.components.partial.IconAlignProvider;
import de.cuioss.jsf.api.components.partial.IconProvider;
import de.cuioss.jsf.api.components.partial.LabelProvider;
import de.cuioss.jsf.api.components.partial.TitleProvider;
import de.cuioss.jsf.api.components.partial.TitleProviderImpl;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.jsf.bootstrap.button.support.ButtonSize;
import de.cuioss.jsf.bootstrap.button.support.ButtonState;
import lombok.experimental.Delegate;

/**
 * @author Oliver Wolff
 *
 */
@FacesComponent(BootstrapFamily.OUTPUT_LINK_BUTTON_COMPONENT)
public class OutputLinkButton extends HtmlOutputLink
        implements ComponentBridge, TitleProvider {

    @Delegate
    private final ContextSizeProvider contextSizeProvider;

    @Delegate
    private final ContextStateProvider contextStateProvider;

    @Delegate
    private final TitleProvider titleProvider;

    @Delegate
    private final IconProvider iconProvider;

    @Delegate
    private final IconAlignProvider iconAlignProvider;

    @Delegate
    private final LabelProvider labelProvider;

    private final ComponentStyleClassProvider styleClassProvider;

    /**
     *
     */
    public OutputLinkButton() {
        super();
        super.setRendererType(BootstrapFamily.OUTPUT_LINK_BUTTON_RENDERER);
        titleProvider = new TitleProviderImpl(this);
        contextSizeProvider = new ContextSizeProvider(this);
        contextStateProvider = new ContextStateProvider(this);
        iconProvider = new IconProvider(this);
        labelProvider = new LabelProvider(this);
        styleClassProvider = new ComponentStyleClassProviderImpl(this);
        iconAlignProvider = new IconAlignProvider(this);
    }

    @Override
    public String getStyleClass() {
        return CssBootstrap.BUTTON.getStyleClassBuilder()
                .append(ButtonState.getForContextState(contextStateProvider.getState()))
                .append(ButtonSize.getForContextSize(contextSizeProvider.resolveContextSize()))
                .append(styleClassProvider)
                .getStyleClass();
    }

    @Override
    public void setStyleClass(final String styleClass) {
        styleClassProvider.setStyleClass(styleClass);
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

    @Override
    public String getFamily() {
        return BootstrapFamily.COMPONENT_FAMILY;
    }

    /**
     * @return boolean indicating whether to display an icon on the right side of the button text
     */
    public boolean isDisplayIconRight() {
        return iconProvider.isIconSet() && AlignHolder.RIGHT.equals(iconAlignProvider.resolveIconAlign());
    }

    /**
     * @return boolean indicating whether to display an icon on the left side of the button text
     */
    public boolean isDisplayIconLeft() {
        return iconProvider.isIconSet() && !AlignHolder.RIGHT.equals(iconAlignProvider.resolveIconAlign());
    }

}
