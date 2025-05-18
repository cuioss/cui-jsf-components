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
package de.cuioss.jsf.bootstrap.tab;

import static de.cuioss.jsf.bootstrap.BootstrapFamily.TAB_PANEL_RENDERER;

import de.cuioss.jsf.api.components.partial.TitleProvider;
import de.cuioss.jsf.api.components.partial.TitleProviderImpl;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.layout.BasicBootstrapPanelComponent;
import jakarta.faces.component.FacesComponent;
import lombok.experimental.Delegate;

/**
 * Bootstrap tab panel component for tabbed interfaces.
 * Extends {@link BasicBootstrapPanelComponent} with tab-specific functionality.
 * 
 * <h2>Attributes</h2>
 * <ul>
 *   <li>Inherits all attributes from {@link BasicBootstrapPanelComponent}</li>
 *   <li>{@link TitleProvider} - Title for the tab header</li>
 * </ul>
 * 
 * <h2>Usage</h2>
 * <pre>
 * &lt;cui:tabPanel id="firstTab" titleValue="First Tab"&gt;
 *   &lt;p&gt;Content for the first tab&lt;/p&gt;
 * &lt;/cui:tabPanel&gt;
 * </pre>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
@FacesComponent(BootstrapFamily.TAB_PANEL_COMPONENT)
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public class TabPanelComponent extends BasicBootstrapPanelComponent {

    /** Partial elements. */
    @Delegate
    private final TitleProvider titleProvider;

    /**
     * Constructor that sets the appropriate renderer type and initializes the
     * title provider functionality.
     */
    public TabPanelComponent() {
        super.setRendererType(TAB_PANEL_RENDERER);
        titleProvider = new TitleProviderImpl(this);
    }
}
