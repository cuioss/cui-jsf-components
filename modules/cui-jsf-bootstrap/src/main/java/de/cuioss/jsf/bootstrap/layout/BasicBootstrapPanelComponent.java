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
 * <p>Foundation class for Bootstrap layout components in the CUI JSF framework.
 * This abstract class provides the common structure and behavior needed by all
 * Bootstrap-styled panel and layout components.</p>
 * 
 * <h2>Purpose and Functionality</h2>
 * <ul>
 *   <li>Extends {@link BaseCuiPanel} to inherit CUI panel functionality</li>
 *   <li>Establishes the component family as {@link BootstrapFamily#COMPONENT_FAMILY}
 *       for all derived components</li>
 *   <li>Serves as a base class for more specialized Bootstrap components like
 *       {@link AbstractLayoutComponent} and {@link BootstrapPanelComponent}</li>
 *   <li>Provides consistent component identification and inheritance for Bootstrap UI elements</li>
 * </ul>
 * 
 * <h2>Usage</h2>
 * <p>This class is not intended to be used directly in views but serves as a parent
 * for concrete Bootstrap components. Direct subclasses include:</p>
 * <ul>
 *   <li>{@link AbstractLayoutComponent} - Base for layout-specific components</li>
 *   <li>{@link BootstrapPanelComponent} - Implementation of Bootstrap panels/cards</li>
 *   <li>Other Bootstrap-specific components</li>
 * </ul>
 *
 * @author Oliver Wolff
 * @see AbstractLayoutComponent
 * @see BootstrapPanelComponent
 * @see BaseCuiPanel
 */
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public abstract class BasicBootstrapPanelComponent extends BaseCuiPanel {

    @Override
    public String getFamily() {
        return BootstrapFamily.COMPONENT_FAMILY;
    }

}
