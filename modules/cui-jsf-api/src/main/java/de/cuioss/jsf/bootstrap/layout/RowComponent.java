package de.cuioss.jsf.bootstrap.layout;

import javax.faces.component.FacesComponent;

import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;

/**
 * <p>
 * Renders a bootstrap conform div with the styleClass 'row'.
 * </p>
 * <p>
 * The layout relies completely on the grid-system of twitter-bootstrap, see
 * <a href="http://getbootstrap.com/css/#grid">Bootstrap Documentation</a>
 * </p>
 * <ul>
 * <li>Rows must be placed within a .container (fixed-width) or .container-fluid
 * (full-width) for proper alignment and padding.</li>
 * <li>Use rows to create horizontal groups of columns.</li>
 * <li>Content should be placed within columns, and only columns may be
 * immediate children of rows.</li>
 * </ul>
 * <p>
 * More information and examples can be found in the <a href=
 * "https://cuioss.de/cui-reference-documentation/faces/pages/documentation/cui_components/demo/layout.jsf"
 * >Reference Documentation</a>
 * </p>
 * <h2>Usage</h2>
 *
 * <pre>
 *  {@code <cui:row />}
 * </pre>
 *
 * <h2>Styling</h2>
 * <ul>
 * <li>The marker css class is row</li>
 * </ul>
 *
 * @author Oliver Wolff
 *
 */
@FacesComponent(BootstrapFamily.LAYOUT_ROW_COMPONENT)
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public class RowComponent extends AbstractLayoutComponent {

    /**
     *
     */
    public RowComponent() {
        super();
        super.setRendererType(BootstrapFamily.LAYOUT_RENDERER);
    }

    @Override
    public StyleClassBuilder resolveStyleClass() {
        return CssBootstrap.ROW.getStyleClassBuilder().append(super.getStyleClass());
    }
}
