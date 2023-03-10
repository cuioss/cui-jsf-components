package de.cuioss.jsf.bootstrap.layout;

import javax.faces.component.FacesComponent;

import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;

/**
 * <p>
 * Renders a bootstrap conform div with the styleClass 'input-group'.
 * An input group groups a number of input elements to be displayed as one element.
 * </p>
 * <p>
 * The layout relies completely on the grid-system of twitter-bootstrap, see
 * <a href="http://getbootstrap.com/css/#grid">Bootstrap Documentation</a>
 * </p>
 * <p>
 * More information and examples can be found in the <a href=
 * "http://ehf-ui-trunk.ci.dev.icw.int:8080/cui-reference-documentation/faces/pages/documentation/cui_components/demo/layout.jsf"
 * >Reference Documentation</a>
 * </p>
 * <h2>Usage</h2>
 *
 * <pre>
 *  {@code <cui:inputGroup />}
 * </pre>
 *
 * <h2>Styling</h2>
 * <ul>
 * <li>The marker css class is 'input-group'</li>
 * </ul>
 *
 * @author Oliver Wolff
 *
 */
@FacesComponent(BootstrapFamily.LAYOUT_INPUT_GROUP_COMPONENT)
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public class InputGroupComponent extends AbstractLayoutComponent {

    /**
     *
     */
    public InputGroupComponent() {
        super();
        super.setRendererType(BootstrapFamily.LAYOUT_RENDERER);
    }

    @Override
    public StyleClassBuilder resolveStyleClass() {
        return CssBootstrap.INPUT_GROUP.getStyleClassBuilder().append(super.getStyleClass());
    }
}
