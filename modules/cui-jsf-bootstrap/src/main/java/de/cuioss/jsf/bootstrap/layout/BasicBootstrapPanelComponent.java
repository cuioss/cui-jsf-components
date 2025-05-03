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

import de.cuioss.jsf.api.components.base.BaseCuiPanel;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import jakarta.faces.component.UIComponent;

/**
 * Base class for our layout components. It extends {@link BaseCuiPanel} and
 * provides {@link UIComponent#getFamily()} returning
 * {@link BootstrapFamily#COMPONENT_FAMILY}
 *
 * @author Oliver Wolff
 *
 */
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public abstract class BasicBootstrapPanelComponent extends BaseCuiPanel {

    @Override
    public String getFamily() {
        return BootstrapFamily.COMPONENT_FAMILY;
    }

}
