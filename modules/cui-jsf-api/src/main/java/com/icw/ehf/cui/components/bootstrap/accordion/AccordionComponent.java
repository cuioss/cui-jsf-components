package com.icw.ehf.cui.components.bootstrap.accordion;

import javax.faces.component.FacesComponent;

import com.icw.ehf.cui.components.bootstrap.BootstrapFamily;
import com.icw.ehf.cui.components.bootstrap.layout.BasicBootstrapPanelComponent;
import com.icw.ehf.cui.core.api.components.partial.ActiveIndexManagerProvider;
import com.icw.ehf.cui.core.api.components.util.CuiState;

import lombok.experimental.Delegate;

/**
 * @author Matthias Walliczek
 * @author Sven Haag, Sven Haag
 */
@FacesComponent(BootstrapFamily.ACCORDION_COMPONENT)
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public class AccordionComponent extends BasicBootstrapPanelComponent {

    /** if true, each panel can be expanded/collapsed independently */
    private static final String MULTISELECTABLE_KEY = "multiselectable";

    @Delegate
    private final ActiveIndexManagerProvider activeIndexManagerProvider;

    private final CuiState state;

    /***/
    public AccordionComponent() {
        super();
        super.setRendererType(BootstrapFamily.ACCORDION_RENDERER);
        activeIndexManagerProvider = new ActiveIndexManagerProvider(this);
        state = new CuiState(getStateHelper());
    }

    /**
     * @param multiSelectable
     */
    public void setMultiselectable(final boolean multiSelectable) {
        state.put(MULTISELECTABLE_KEY, multiSelectable);
    }

    /**
     * @return multiSelectable
     */
    public boolean isMultiselectable() {
        return state.getBoolean(MULTISELECTABLE_KEY, false);
    }
}
