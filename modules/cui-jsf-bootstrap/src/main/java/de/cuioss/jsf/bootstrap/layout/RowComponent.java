/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.bootstrap.layout;

import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import jakarta.faces.component.FacesComponent;

/**
 * <p>
 * Renders a Bootstrap row container element for creating responsive grid layouts.
 * This component is a fundamental part of Bootstrap's 12-column grid system.
 * </p>
 * 
 * <h2>Bootstrap Grid System Overview</h2>
 * <p>
 * The row component serves as a horizontal container for {@link ColumnComponent} elements in the Bootstrap grid system.
 * Rows establish horizontal groupings of columns and handle proper alignment and spacing.
 * See the <a href="https://getbootstrap.com/docs/3.4/css/#grid">Bootstrap Grid Documentation</a> for detailed information.
 * </p>
 * 
 * <h2>Features</h2>
 * <ul>
 *   <li>Creates a horizontal container for columns</li>
 *   <li>Establishes the 12-column grid context</li>
 *   <li>Handles negative margins to counteract container padding</li>
 *   <li>Supports nesting for complex layouts</li>
 * </ul>
 * 
 * <h2>HTML Output Structure</h2>
 * <p>The component renders as a <code>&lt;div&gt;</code> element with the Bootstrap row class:</p>
 * <pre>
 * &lt;div class="row [additional styleClass]"&gt;
 *   [column components]
 * &lt;/div&gt;
 * </pre>
 *
 * <h2>Usage Rules</h2>
 * <ul>
 *   <li>Rows must be placed within a <code>.container</code> (fixed-width) or <code>.container-fluid</code>
 *       (full-width) for proper alignment and padding</li>
 *   <li>Use rows to create horizontal groups of columns</li>
 *   <li>Content should be placed within columns, and only columns may be
 *       immediate children of rows</li>
 *   <li>Rows can be nested within columns to create complex layouts</li>
 * </ul>
 *
 * <h2>Basic Usage Example</h2>
 * <pre>
 *  {@code <cui:row />}
 * </pre>
 * 
 * <h2>Grid Layout Example</h2>
 * <pre>
 * {@code 
 * <cui:row>
 *   <cui:column size="6">First half</cui:column>
 *   <cui:column size="6">Second half</cui:column>
 * </cui:row>
 * }
 * </pre>
 * 
 * <h2>Complex Layout Example</h2>
 * <pre>
 * {@code 
 * <!-- Two-column layout with nested rows -->
 * <cui:row>
 *   <!-- Left sidebar -->
 *   <cui:column size="3">
 *     Sidebar content
 *   </cui:column>
 *   
 *   <!-- Main content area with nested rows -->
 *   <cui:column size="9">
 *     <cui:row>
 *       <cui:column size="12">
 *         Header content
 *       </cui:column>
 *     </cui:row>
 *     <cui:row>
 *       <cui:column size="6">
 *         Left content
 *       </cui:column>
 *       <cui:column size="6">
 *         Right content
 *       </cui:column>
 *     </cui:row>
 *   </cui:column>
 * </cui:row>
 * }
 * </pre>
 *
 * <h2>Styling Classes</h2>
 * <ul>
 *   <li><code>row</code> - Primary Bootstrap class that establishes the grid context</li>
 *   <li>Additional styling can be applied using the styleClass attribute</li>
 *   <li>For special layout needs, Bootstrap offers utility classes like <code>row-no-gutters</code> that can be added via styleClass</li>
 * </ul>
 * 
 * <h2>Attributes</h2>
 * <ul>
 *   <li>{@link AbstractLayoutComponent} - Inherited layout component attributes</li>
 *   <li><b>styleClass</b>: Additional CSS classes to append (optional)</li>
 *   <li><b>style</b>: Inline CSS styles (optional)</li>
 *   <li><b>rendered</b>: Boolean flag to determine if the component should be rendered (default: true)</li>
 * </ul>
 * 
 * <h2>Accessibility</h2>
 * <p>
 * The component renders a standard HTML div element with the Bootstrap row class.
 * For enhanced accessibility in complex layouts, consider adding appropriate ARIA attributes:
 * </p>
 * <pre>
 * {@code
 * <cui:row style="role='region'" styleClass="main-content">
 *   <!-- Accessible row content -->
 * </cui:row>
 * }
 * </pre>
 *
 * <h2>Further Documentation</h2>
 * <p>
 * More information and examples can be found in the <a href=
 * "https://cuioss.de/cui-reference-documentation/pages/documentation/cui_components/demo/layout.jsf"
 * >CUI Reference Documentation</a>
 * </p>
 *
 * @author Oliver Wolff
 * @see ColumnComponent
 * @see AbstractLayoutComponent
 */
@FacesComponent(BootstrapFamily.LAYOUT_ROW_COMPONENT)
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public class RowComponent extends AbstractLayoutComponent {

    /**
     * Default constructor that sets the renderer type to the Bootstrap layout renderer.
     * This ensures the component is rendered using the appropriate renderer for Bootstrap
     * layout components.
     */
    public RowComponent() {
        super.setRendererType(BootstrapFamily.LAYOUT_RENDERER);
    }

    /**
     * {@inheritDoc}
     * 
     * Resolves the CSS style classes for this component by combining:
     * <ul>
     *   <li>Bootstrap's 'row' CSS class - the primary class for row layout</li>
     *   <li>Any custom style classes provided via the styleClass attribute</li>
     * </ul>
     * 
     * @return A {@link StyleClassBuilder} containing all resolved CSS classes
     */
    @Override
    public StyleClassBuilder resolveStyleClass() {
        return CssBootstrap.ROW.getStyleClassBuilder().append(super.getStyleClass());
    }
}
