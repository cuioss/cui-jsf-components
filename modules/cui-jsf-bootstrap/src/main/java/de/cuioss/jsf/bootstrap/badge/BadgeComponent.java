package de.cuioss.jsf.bootstrap.badge;

import javax.faces.component.FacesComponent;

import de.cuioss.jsf.api.components.base.BaseCuiOutputText;
import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;

/**
 * Component Class for creating badges. All standard attributes are defined by
 * {@link BaseCuiOutputText}. The defining css class "badge"
 *
 * @author Oliver Wolff
 *
 */
@FacesComponent(BootstrapFamily.BADGE_COMPONENT)
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public class BadgeComponent extends BaseCuiOutputText {

    @Override
    public StyleClassBuilder getComponentSpecificStyleClasses() {
        return CssBootstrap.BADGE.getStyleClassBuilder();
    }
}
