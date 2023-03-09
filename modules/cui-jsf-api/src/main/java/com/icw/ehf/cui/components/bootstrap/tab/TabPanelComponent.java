package com.icw.ehf.cui.components.bootstrap.tab;

import static com.icw.ehf.cui.components.bootstrap.BootstrapFamily.TAB_PANEL_RENDERER;

import javax.faces.component.FacesComponent;

import com.icw.ehf.cui.components.bootstrap.BootstrapFamily;
import com.icw.ehf.cui.components.bootstrap.layout.BasicBootstrapPanelComponent;
import com.icw.ehf.cui.core.api.components.partial.TitleProvider;
import com.icw.ehf.cui.core.api.components.partial.TitleProviderImpl;

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
