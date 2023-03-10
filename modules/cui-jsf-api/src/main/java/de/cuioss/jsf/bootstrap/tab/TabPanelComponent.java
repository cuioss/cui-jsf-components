package de.cuioss.jsf.bootstrap.tab;

import static de.cuioss.jsf.bootstrap.BootstrapFamily.TAB_PANEL_RENDERER;

import javax.faces.component.FacesComponent;

import de.cuioss.jsf.api.components.partial.TitleProvider;
import de.cuioss.jsf.api.components.partial.TitleProviderImpl;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.layout.BasicBootstrapPanelComponent;
import lombok.experimental.Delegate;

/**
 * @author Oliver Wolff
 */
@FacesComponent(BootstrapFamily.TAB_PANEL_COMPONENT)
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public class TabPanelComponent extends BasicBootstrapPanelComponent {

    /** Partial elements. */
    @Delegate
    private final TitleProvider titleProvider;

    /**
     *
     */
    public TabPanelComponent() {
        super.setRendererType(TAB_PANEL_RENDERER);
        titleProvider = new TitleProviderImpl(this);
    }
}
