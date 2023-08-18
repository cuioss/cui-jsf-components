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
