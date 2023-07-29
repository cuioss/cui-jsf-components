package de.cuioss.jsf.bootstrap.button;

import javax.faces.component.FacesComponent;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.context.FacesContext;

import de.cuioss.jsf.api.components.base.BaseCuiCommandButton;
import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.AttributeValue;
import de.cuioss.jsf.api.components.util.ComponentUtility;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;

/**
 * Variant of {@link HtmlCommandButton} that can be directly used as a 'Close'
 * button within a form context.
 *
 * @author Oliver Wolff
 *
 */
@FacesComponent(BootstrapFamily.CLOSE_COMMAND_BUTTON_COMPONENT)
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public class CloseCommandButton extends BaseCuiCommandButton {

    /**
     *
     */
    public CloseCommandButton() {
        super.setRendererType(BootstrapFamily.CLOSE_COMMAND_BUTTON_RENDERER);
        getPassThroughAttributes(true).put(AttributeName.ARIA_LABEL.getContent(),
                AttributeValue.ARIA_CLOSE.getContent());
        super.setStyleClass(CssBootstrap.BUTTON_CLOSE.getStyleClass());
    }

    @Override
    public String getFamily() {
        return BootstrapFamily.COMPONENT_FAMILY;
    }

    /**
     * Shortcut for creating and casting a component of type
     * {@link CloseCommandButton}.
     *
     * @param facesContext
     * @return a newly created {@link CloseCommandButton}
     */
    public static CloseCommandButton createComponent(final FacesContext facesContext) {
        return ComponentUtility.createComponent(facesContext, BootstrapFamily.CLOSE_COMMAND_BUTTON_COMPONENT);
    }
}
