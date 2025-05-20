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
package de.cuioss.jsf.bootstrap.accordion;

import de.cuioss.jsf.api.components.partial.ActiveIndexManagerProvider;
import de.cuioss.jsf.api.components.util.CuiState;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.layout.BasicBootstrapPanelComponent;
import jakarta.faces.component.FacesComponent;
import lombok.experimental.Delegate;

/**
 * Bootstrap accordion component for collapsible content panels.
 * Supports single mode (only one panel open) or multi-select mode.
 * 
 * <h3>Usage:</h3>
 * <pre>
 * &lt;boot:accordion id="myAccordion" multiselectable="false"&gt;
 *   &lt;boot:panel title="Panel 1"&gt;Content for panel 1&lt;/boot:panel&gt;
 *   &lt;boot:panel title="Panel 2"&gt;Content for panel 2&lt;/boot:panel&gt;
 * &lt;/boot:accordion&gt;
 * </pre>
 * 
 * @author Matthias Walliczek
 * @author Sven Haag
 * @since 1.0
 */
@FacesComponent(BootstrapFamily.ACCORDION_COMPONENT)
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public class AccordionComponent extends BasicBootstrapPanelComponent {

    /** if true, each panel can be expanded/collapsed independently */
    private static final String MULTISELECTABLE_KEY = "multiselectable";

    @Delegate
    private final ActiveIndexManagerProvider activeIndexManagerProvider;

    private final CuiState state;

    public AccordionComponent() {
        super.setRendererType(BootstrapFamily.ACCORDION_RENDERER);
        activeIndexManagerProvider = new ActiveIndexManagerProvider(this);
        state = new CuiState(getStateHelper());
    }

    /**
     * Sets whether multiple panels can be expanded at the same time.
     * 
     * @param multiSelectable if true, multiple panels can be expanded simultaneously.
     * If false, opening one panel will close all others.
     */
    public void setMultiselectable(final boolean multiSelectable) {
        state.put(MULTISELECTABLE_KEY, multiSelectable);
    }

    /**
     * Determines if multiple panels can be expanded simultaneously.
     * 
     * @return {@code true} if multiple panels can be expanded at the same time, 
     * {@code false} if only one panel can be expanded at a time (default).
     */
    public boolean isMultiselectable() {
        return state.getBoolean(MULTISELECTABLE_KEY, false);
    }
}
