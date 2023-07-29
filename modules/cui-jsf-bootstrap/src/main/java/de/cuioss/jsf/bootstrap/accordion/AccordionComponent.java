package de.cuioss.jsf.bootstrap.accordion;

import javax.faces.component.FacesComponent;

import de.cuioss.jsf.api.components.partial.ActiveIndexManagerProvider;
import de.cuioss.jsf.api.components.util.CuiState;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.layout.BasicBootstrapPanelComponent;
import lombok.experimental.Delegate;

/**
 * @author Matthias Walliczek
 * @author Sven Haag
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
