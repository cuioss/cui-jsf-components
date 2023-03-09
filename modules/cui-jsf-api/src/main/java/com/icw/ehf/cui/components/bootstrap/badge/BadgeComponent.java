package com.icw.ehf.cui.components.bootstrap.badge;

import javax.faces.component.FacesComponent;

import com.icw.ehf.cui.components.bootstrap.BootstrapFamily;
import com.icw.ehf.cui.components.bootstrap.CssBootstrap;
import com.icw.ehf.cui.core.api.components.base.BaseCuiOutputText;
import com.icw.ehf.cui.core.api.components.css.StyleClassBuilder;

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
