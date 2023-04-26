package de.cuioss.jsf.bootstrap.layout;

import javax.faces.application.FacesMessage;
import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ListenerFor;
import javax.faces.event.PreRenderComponentEvent;

import de.cuioss.jsf.api.application.message.MessageProducerAccessor;
import de.cuioss.jsf.api.components.partial.CollapseSwitchProvider;
import de.cuioss.jsf.api.components.partial.ContextStateProvider;
import de.cuioss.jsf.api.components.partial.DeferredProvider;
import de.cuioss.jsf.api.components.partial.FooterProvider;
import de.cuioss.jsf.api.components.partial.HeaderProvider;
import de.cuioss.jsf.api.components.util.ComponentUtility;
import de.cuioss.jsf.api.components.util.CuiState;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.tools.logging.CuiLogger;
import lombok.experimental.Delegate;

/**
 * Bootstrap panel component
 *
 * @author Matthias Walliczek
 * @author Sven Haag
 */
@ResourceDependency(library = "javascript.enabler", name = "enabler.panel.js", target = "head")
@ListenerFor(systemEventClass = PreRenderComponentEvent.class)
@FacesComponent(BootstrapFamily.PANEL_COMPONENT)
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public class BootstrapPanelComponent extends BasicBootstrapPanelComponent {

    private static final CuiLogger log = new CuiLogger(BootstrapPanelComponent.class);

    private static final String ASYNCUPDATE_KEY = "asyncUpdate";

    private static final String CHILDREN_LOADED_KEY = "childrenLoaded";

    @Delegate
    private final HeaderProvider headerProvider;

    @Delegate
    private final FooterProvider footerProvider;

    @Delegate
    private final CollapseSwitchProvider collapseSwitchProvider;

    @Delegate
    private final ContextStateProvider contextStateProvider;

    @Delegate
    private final DeferredProvider deferredProvider;

    private String dataParent;

    private final CuiState state;

    /**
     *
     */
    public BootstrapPanelComponent() {
        super();
        headerProvider = new HeaderProvider(this);
        footerProvider = new FooterProvider(this);
        collapseSwitchProvider = new CollapseSwitchProvider(this);
        contextStateProvider = new ContextStateProvider(this);
        deferredProvider = new DeferredProvider(this);
        state = new CuiState(getStateHelper());
        super.setRendererType(BootstrapFamily.PANEL_RENDERER);
    }

    @Override
    public void processEvent(final ComponentSystemEvent event) {
        if (event instanceof PreRenderComponentEvent && mustAddForm()) {
            log.error("The UI component '{}' needs to have a form tag in its ancestry.", getClientId());
            new MessageProducerAccessor().getValue()
                    .setFacesMessage("message.error.component.noform",
                            FacesMessage.SEVERITY_ERROR,
                            getClientId(),
                            getClientId());
        }
    }

    /**
     * @return true if it is necessary to be in an UIForm.
     */
    private boolean mustAddForm() {
        return (isAsyncUpdate() || isDeferred())
                && !getFacesContext().getPartialViewContext().isPartialRequest()
                && !ComponentUtility.isInForm(this);
    }

    /**
     * @param asyncUpdate to be set
     */
    public void setAsyncUpdate(final boolean asyncUpdate) {
        state.put(ASYNCUPDATE_KEY, asyncUpdate);
    }

    /**
     * @return asyncUpdate previously set
     */
    public boolean isAsyncUpdate() {
        return state.getBoolean(ASYNCUPDATE_KEY);
    }

    /**
     * true, if deferred loading is activated, the panel is collapsed and childs were not rendered
     * yet.
     *
     * @return true if the spinner icon should be rendered, false otherwise.
     */
    public boolean shouldRenderSpinnerIcon() {
        return isDeferred() && !getChildrenLoaded();
    }

    /**
     * @return boolean indicating whether the children are rendered or should be rendered
     */
    public boolean getChildrenLoaded() {
        return state.getBoolean(CHILDREN_LOADED_KEY);
    }

    /**
     * Remember that childs were already rendered or trigger loading in the next request.
     *
     * @param childrenLoaded
     */
    public void setChildrenLoaded(final boolean childrenLoaded) {
        state.put(CHILDREN_LOADED_KEY, childrenLoaded);
    }

    /**
     * @return dataParent - accordion client id
     */
    public String getDataParent() {
        return dataParent;
    }

    /**
     * @param dataParent - accordion client id
     */
    public void setDataParent(String dataParent) {
        this.dataParent = dataParent;
    }

    /**
     * @return role attribute for header toggle element
     *         according to panel configuration.
     */
    public String resolveTogglerRole() {
        if (isCollapsible() && getDataParent() != null) {
            return "tab";
        }
        if (isCollapsible()) {
            return "button";
        }
        return null;
    }
}
