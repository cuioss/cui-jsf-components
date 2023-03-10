package de.cuioss.jsf.components.inlineconfirm;

import static de.cuioss.jsf.components.CuiFamily.INLINE_CONFIRM_COMPONENT;
import static de.cuioss.jsf.components.CuiFamily.INLINE_CONFIRM_RENDERER;

import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;

import de.cuioss.jsf.api.components.base.BaseCuiPanel;
import de.cuioss.jsf.api.components.util.ComponentModifier;
import de.cuioss.jsf.api.components.util.modifier.ComponentModifierFactory;
import de.cuioss.jsf.components.CuiFamily;

/**
 * @author Oliver Wolff
 *
 */
@FacesComponent(INLINE_CONFIRM_COMPONENT)
@ResourceDependency(library = "javascript.enabler", name = "enabler.inline_confirm.js")
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public class InlineConfirmComponent extends BaseCuiPanel {

    static final String INITIAL_FACET_NAME = "initial";

    /**
     *
     */
    public InlineConfirmComponent() {
        super();
        super.setRendererType(INLINE_CONFIRM_RENDERER);
    }

    /**
     * @return the child of the component as {@link ComponentModifier}
     */
    public ComponentModifier getChildAsModifier() {
        var iterator = getChildren().iterator();
        if (!iterator.hasNext()) {
            throw new IllegalArgumentException("You must provide a child for this component");
        }
        var child = iterator.next();
        return ComponentModifierFactory.findFittingWrapper(child);
    }

    /**
     * @return the facet to be displayed initially
     */
    public ComponentModifier getInitialFacet() {
        var facet = getFacet(INITIAL_FACET_NAME);
        if (null == facet) {
            throw new IllegalArgumentException("You must provide a facet with name = 'initial'");
        }
        return ComponentModifierFactory.findFittingWrapper(facet);
    }

    @Override
    public String getFamily() {
        return CuiFamily.COMPONENT_FAMILY;
    }
}
