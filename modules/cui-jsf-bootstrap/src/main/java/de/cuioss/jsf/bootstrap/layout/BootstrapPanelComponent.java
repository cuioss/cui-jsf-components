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
import de.cuioss.jsf.api.components.partial.*;
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
 * <p>A versatile Bootstrap panel component implementing a card-like UI element with
 * header, body, and footer sections. Supports collapsible content, deferred loading,
 * and contextual styling.</p>
 *
 * <h2>Component Features</h2>
 * <ul>
 *   <li>Header and footer sections with facet support</li>
 *   <li>Collapsible content with toggle controls</li>
 *   <li>Contextual states (success, info, warning, danger)</li>
 *   <li>Deferred content loading for improved performance</li>
 *   <li>Asynchronous updates via AJAX</li>
 *   <li>Accordion integration through data-parent support</li>
 * </ul>
 *
 * <h2>Usage</h2>
 * <pre>
 * &lt;cui:panel header="Panel Title"
 *          state="INFO"
 *          collapsible="true"
 *          collapsed="false"&gt;
 *   Panel content goes here
 * &lt;/cui:panel&gt;
 * </pre>
 *
 * <h2>Advanced Usage with Facets</h2>
 * <pre>
 * &lt;cui:panel collapsible="true"&gt;
 *   &lt;f:facet name="header"&gt;
 *     Custom header content
 *   &lt;/f:facet&gt;
 *
 *   Panel body content
 *
 *   &lt;f:facet name="footer"&gt;
 *     Custom footer content
 *   &lt;/f:facet&gt;
 * &lt;/cui:panel&gt;
 * </pre>
 *
 * <h2>Deferred Loading Example</h2>
 * <pre>
 * &lt;cui:panel header="Deferred Content"
 *          collapsible="true"
 *          collapsed="true"
 *          deferred="true"&gt;
 *   &lt;!-- Content loaded only when panel is expanded --&gt;
 *   Heavy content here
 * &lt;/cui:panel&gt;
 * </pre>
 *
 * <h2>Attributes</h2>
 * <ul>
 *   <li>{@link HeaderProvider} - Provides header content and configuration</li>
 *   <li>{@link FooterProvider} - Provides footer content and configuration</li>
 *   <li>{@link CollapseSwitchProvider} - Controls collapse/expand behavior</li>
 *   <li>{@link ContextStateProvider} - Controls contextual state (success, warning, etc.)</li>
 *   <li>{@link DeferredProvider} - Controls deferred content loading</li>
 *   <li><b>asyncUpdate</b>: Boolean flag indicating if the panel should use AJAX for updates</li>
 *   <li><b>childrenLoaded</b>: Boolean flag tracking whether deferred children are loaded</li>
 *   <li><b>dataParent</b>: String ID of parent accordion component for coordinated collapsing</li>
 * </ul>
 *
 * @author Matthias Walliczek
 * @author Sven Haag
 * @see BootstrapPanelRenderer
 * @see BasicBootstrapPanelComponent
 * @see HeaderProvider
 * @see FooterProvider
 * @see CollapseSwitchProvider
 * @see ContextStateProvider
 * @see DeferredProvider
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
     * Default constructor initializing all providers and setting the renderer type.
     * The providers offer various aspects of panel behavior through composition.
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

    /**
     * {@inheritDoc}
     * <p>
     * Handles preprocessing for panel components. Specifically checks if the component
     * requires a form parent (for AJAX/deferred functionality) and logs an error with
     * a user message if the requirement is not met.
     * </p>
     *
     * @param event the component system event being processed
     */
    @Override
    public void processEvent(final ComponentSystemEvent event) {
        if (event instanceof PreRenderComponentEvent && mustAddForm()) {
            log.error("The UI component '{}' needs to have a form tag in its ancestry.", getClientId());
            PortalBeanManager.resolveRequiredBean(MessageProducer.class)
                    .setFacesMessage("message.error.component.noform", FacesMessage.SEVERITY_ERROR, getClientId());
        }
    }

    /**
     * Determines if the component requires a form parent based on its configuration.
     * A form is required when the component has asyncUpdate or deferred loading enabled,
     * the request is not a partial AJAX request, and the component is not already in a form.
     *
     * @return true if the component must be placed within a form, false otherwise
     */
    private boolean mustAddForm() {
        return (isAsyncUpdate() || isDeferred()) && !getFacesContext().getPartialViewContext().isPartialRequest()
                && !ComponentUtility.isInForm(this);
    }

    /**
     * Sets the asynchronous update flag for this panel.
     * When enabled, the panel will use AJAX to update its content.
     *
     * @param asyncUpdate boolean flag indicating whether async updates are enabled
     */
    public void setAsyncUpdate(final boolean asyncUpdate) {
        state.put(ASYNCUPDATE_KEY, asyncUpdate);
    }

    /**
     * Determines if asynchronous updates are enabled for this panel.
     *
     * @return boolean indicating whether async updates are enabled
     */
    public boolean isAsyncUpdate() {
        return state.getBoolean(ASYNCUPDATE_KEY);
    }

    /**
     * Determines if a loading spinner icon should be rendered in the panel body.
     * A spinner is shown when deferred loading is active, the panel is collapsed,
     * and the children have not been loaded yet.
     *
     * @return true if the spinner icon should be rendered, false otherwise
     */
    public boolean shouldRenderSpinnerIcon() {
        return isDeferred() && !getChildrenLoaded();
    }

    /**
     * Retrieves the loaded state of deferred child components.
     *
     * @return boolean indicating whether child components have been loaded
     */
    public boolean getChildrenLoaded() {
        return state.getBoolean(CHILDREN_LOADED_KEY);
    }

    /**
     * Sets the loaded state of deferred child components.
     * Used to track whether deferred content has been rendered or to
     * trigger loading in the next request cycle.
     *
     * @param childrenLoaded boolean flag indicating the loaded state
     */
    public void setChildrenLoaded(final boolean childrenLoaded) {
        state.put(CHILDREN_LOADED_KEY, childrenLoaded);
    }

    /**
     * Gets the ID of the parent accordion component. This is used to coordinate
     * collapse/expand behavior among sibling panels in an accordion.
     *
     * @return the client ID of the parent accordion component, or null if not in an accordion
     */
    public String getDataParent() {
        return dataParent;
    }

    /**
     * Sets the ID of the parent accordion component.
     *
     * @param dataParent the client ID of the parent accordion component
     */
    public void setDataParent(String dataParent) {
        this.dataParent = dataParent;
    }

    /**
     * Resolves the appropriate ARIA role attribute for the header toggle element
     * based on the panel's configuration.
     * <ul>
     *   <li>"tab" - For collapsible panels in an accordion</li>
     *   <li>"button" - For standalone collapsible panels</li>
     *   <li>null - For non-collapsible panels</li>
     * </ul>
     *
     * @return the appropriate ARIA role, or null if not applicable
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
