package de.cuioss.jsf.components.blockelement;

import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;

import de.cuioss.jsf.api.components.decorator.AbstractParentDecorator;
import de.cuioss.jsf.api.components.util.ComponentModifier;
import de.cuioss.jsf.components.CuiFamily;
import lombok.ToString;

/**
 * <p>
 * Decorator to activate the "block element" function for an (optional ajax enabled) element: After
 * activating the
 * element (e.g button click or value change) the element will be disabled and the spinner will be
 * added. To reset this
 * behaviour the element should be part of the "update" or "render" attribute of an ajax behaviour.
 * </p>
 * <h2>Usage</h2>
 * <p>
 *
 * <pre>
 * &lt;cui:blockElement /&gt;
 * </pre>
 *
 * </p>
 * <h2>Example</h2>
 * <p>
 *
 * <pre>
 * &lt;cui:commandButton ... &gt;
 *   &lt;f:ajax render="@this" /&gt;
 *   &lt;cui:blockElement /&gt;
 * &lt;/cui:commandButton &gt;
 * </pre>
 * </p>
 *
 * @author Matthias Walliczek
 */
@ResourceDependency(library = "javascript.enabler", name = "enabler.blockElement.js", target = "head")
@FacesComponent(CuiFamily.BLOCKELEMENT_COMPONENT)
@ToString
public class BlockElementDecorator extends AbstractParentDecorator {

    @Override
    public void decorate(ComponentModifier parent) {
        parent.addPassThrough("data-cui-block-element", "data-cui-block-element");
    }

    @Override
    public String getFamily() {
        return CuiFamily.COMPONENT_FAMILY;
    }
}
