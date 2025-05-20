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

import de.cuioss.jsf.api.components.css.StyleClassResolver;

/**
 * <p>Abstract base class for Bootstrap layout components within the CUI JSF component
 * library. This class establishes the common foundation for components that implement
 * Bootstrap's grid system and layout structures.</p>
 * 
 * <h2>Purpose and Functionality</h2>
 * <ul>
 *   <li>Provides a common inheritance point for all layout-related components</li>
 *   <li>Extends {@link BasicBootstrapPanelComponent} to inherit panel-related capabilities</li>
 *   <li>Implements {@link StyleClassResolver} to ensure all layout components can
 *       consistently resolve their CSS classes</li>
 *   <li>Acts as a bridge between the general component framework and specific layout 
 *       implementations like columns, rows, etc.</li>
 * </ul>
 *
 * <h2>Usage</h2>
 * <p>This class is not meant to be used directly but serves as a parent for concrete
 * layout components such as:</p>
 * <ul>
 *   <li>{@link RowComponent} - Implements Bootstrap's row element</li>
 *   <li>{@link ColumnComponent} - Implements Bootstrap's column elements</li>
 *   <li>Other layout-specific components</li>
 * </ul>
 *
 * @author Oliver Wolff
 * @see RowComponent
 * @see ColumnComponent
 * @see BasicBootstrapPanelComponent
 * @see StyleClassResolver
 */
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public abstract class AbstractLayoutComponent extends BasicBootstrapPanelComponent implements StyleClassResolver {

}
