package de.cuioss.jsf.bootstrap.icon;

import javax.faces.component.FacesComponent;
import javax.faces.context.FacesContext;

import de.cuioss.jsf.api.components.base.AbstractBaseCuiComponent;
import de.cuioss.jsf.api.components.partial.ComponentStyleClassProvider;
import de.cuioss.jsf.api.components.partial.ContextSizeProvider;
import de.cuioss.jsf.api.components.partial.ContextStateProvider;
import de.cuioss.jsf.api.components.partial.IconProvider;
import de.cuioss.jsf.api.components.partial.StyleAttributeProvider;
import de.cuioss.jsf.api.components.partial.TitleProvider;
import de.cuioss.jsf.api.components.partial.TitleProviderImpl;
import de.cuioss.jsf.api.components.util.ComponentUtility;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import lombok.experimental.Delegate;

/**
 * <p>
 * Renders an Icon according to the cui-icon contract. The icon is rendered as a
 * span with the according classes. The title is resolved using the cui standard
 * label-resolving mechanism.
 * </p>
 * <p>
 * A list of all available icons can be found at the <a href=
 * "http://ehf-ui-trunk.ci.dev.icw.int:8080/cui-reference-documentation/faces/pages/documentation/icons/cui_icons.jsf">
 * Reference Documentation</a>
 * </p>
 * <h2>Attributes</h2>
 * <ul>
 * <li>{@link TitleProvider}</li>
 * <li>{@link ContextSizeProvider}</li>
 * <li>{@link ComponentStyleClassProvider}</li>
 * <li>{@link StyleAttributeProvider}</li>
 * <li>{@link ContextStateProvider}</li>
 * <li>{@link IconProvider}</li>
 * </ul>
 * <h2>Usage</h2>
 *
 * <pre>
 * &lt;cui:icon icon="cui-icon-drink" size="xl" /&gt;
 * </pre>
 *
 * @author Oliver Wolff
 */
@FacesComponent(BootstrapFamily.ICON_COMPONENT)
public class IconComponent extends AbstractBaseCuiComponent
        implements TitleProvider {

    @Delegate
    private final TitleProvider titleProvider;

    @Delegate
    private final ContextSizeProvider contextSizeProvider;

    @Delegate
    private final ContextStateProvider contextStateProvider;

    @Delegate
    private final IconProvider iconProvider;

    /**
    *
    */
    public IconComponent() {
        super();
        super.setRendererType(BootstrapFamily.ICON_COMPONENT_RENDERER);
        titleProvider = new TitleProviderImpl(this);
        contextSizeProvider = new ContextSizeProvider(this);
        contextStateProvider = new ContextStateProvider(this);
        iconProvider = new IconProvider(this);
    }

    @Override
    public String getFamily() {
        return BootstrapFamily.COMPONENT_FAMILY;
    }

    /**
     * Shortcut for creating and casting a component of type {@link IconComponent}.
     *
     * @param facesContext
     * @return a newly created {@link IconComponent}
     */
    public static IconComponent createComponent(final FacesContext facesContext) {
        return ComponentUtility.createComponent(facesContext, BootstrapFamily.ICON_COMPONENT);
    }
}
