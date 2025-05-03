/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.bootstrap.layout;

import de.cuioss.jsf.api.application.message.MessageProducer;
import de.cuioss.jsf.api.components.partial.CollapseSwitchProvider;
import de.cuioss.jsf.api.components.partial.ContextStateProvider;
import de.cuioss.jsf.api.components.partial.DeferredProvider;
import de.cuioss.jsf.api.components.partial.FooterProvider;
import de.cuioss.jsf.api.components.partial.HeaderProvider;
import de.cuioss.jsf.api.components.util.ComponentUtility;
import de.cuioss.jsf.api.components.util.CuiState;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.portal.common.cdi.PortalBeanManager;
import de.cuioss.tools.logging.CuiLogger;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.application.ResourceDependency;
import jakarta.faces.component.FacesComponent;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.faces.event.ListenerFor;
import jakarta.faces.event.PreRenderComponentEvent;
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
            PortalBeanManager.resolveRequiredBean(MessageProducer.class)
                    .setFacesMessage("message.error.component.noform", FacesMessage.SEVERITY_ERROR, getClientId());
        }
    }

    /**
     * @return true if it is necessary to be in an UIForm.
     */
    private boolean mustAddForm() {
        return (isAsyncUpdate() || isDeferred()) && !getFacesContext().getPartialViewContext().isPartialRequest()
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
     * true, if deferred loading is activated, the panel is collapsed and childs
     * were not rendered yet.
     *
     * @return true if the spinner icon should be rendered, false otherwise.
     */
    public boolean shouldRenderSpinnerIcon() {
        return isDeferred() && !getChildrenLoaded();
    }

    /**
     * @return boolean indicating whether the children are rendered or should be
     *         rendered
     */
    public boolean getChildrenLoaded() {
        return state.getBoolean(CHILDREN_LOADED_KEY);
    }

    /**
     * Remember that childs were already rendered or trigger loading in the next
     * request.
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
     * @return role attribute for header toggle element according to panel
     *         configuration.
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
