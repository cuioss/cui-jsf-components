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

import de.cuioss.jsf.api.components.base.BaseCuiPanel;
import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.api.components.util.ComponentUtility;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.common.partial.ColumnProvider;
import jakarta.faces.component.FacesComponent;
import jakarta.faces.context.FacesContext;
import lombok.experimental.Delegate;

/**
 * <p>
 * Renders a Bootstrap column element for creating responsive grid layouts within {@link RowComponent} containers.
 * This component implements Bootstrap's grid system column functionality.
 * </p>
 * 
 * <h2>Bootstrap Grid System Overview</h2>
 * <p>
 * The column component is a core part of Bootstrap's grid system, which divides each row into 12 equal columns.
 * See the <a href="https://getbootstrap.com/docs/3.4/css/#grid">Bootstrap Grid Documentation</a> for detailed information.
 * </p>
 * 
 * <h2>Features</h2>
 * <ul>
 *   <li>Column sizing - Controls column width in 1/12 increments</li>
 *   <li>Column offsetting - Creates gaps between columns</li>
 *   <li>Responsive behavior - Adapts to different screen sizes</li>
 *   <li>Nestable - Can contain other rows and columns for complex layouts</li>
 * </ul>
 * 
 * <h2>HTML Output Structure</h2>
 * <p>The component renders as a <code>&lt;div&gt;</code> element with appropriate Bootstrap grid classes:</p>
 * <pre>
 * &lt;div class="col-md-[size] col-md-offset-[offsetSize] [additional styleClass]"&gt;
 *   [child content]
 * &lt;/div&gt;
 * </pre>
 *
 * <h2>Usage Rules</h2>
 * <ul>
 *   <li>Columns must always reside within a {@link RowComponent} as direct children</li>
 *   <li>The size and offset of a column is always defined in 1/12 steps (a row is limited to 12)</li>
 *   <li>If the combined columnSize (and offsets) exceed 12, the surplus columns will wrap to the next line</li>
 *   <li>Offsets create a gap between columns by pushing a column to the right</li>
 *   <li>The default sizing uses the "md" (medium) breakpoint; use styleClass for other breakpoints</li>
 * </ul>
 *
 * <h2>Basic Usage Example</h2>
 *
 * <pre>
 * {@code
 * <cui:row>
 *     <cui:column size="4">
 *         <span>Size="4" (33% width)</span>
 *     </cui:column>
 *     <cui:column size="2">
 *         <span>Size="2" (17% width)</span>
 *     </cui:column>
 *     <cui:column size="4" offsetSize="2">
 *         <span>Size="4" with Offset="2" (33% width with 17% gap)</span>
 *     </cui:column>
 * </cui:row>
 * }
 * </pre>
 * 
 * <h2>Responsive Layout Example</h2>
 * <p>For multi-device responsive layouts, combine with additional Bootstrap grid classes:</p>
 * 
 * <pre>
 * {@code
 * <cui:row>
 *     <cui:column size="12" styleClass="col-lg-6 col-sm-4">
 *         <!-- This column will be:
 *              - 100% width on medium screens (default md breakpoint)
 *              - 50% width on large screens (lg breakpoint)
 *              - 33% width on small screens (sm breakpoint)
 *          -->
 *     </cui:column>
 * </cui:row>
 * }
 * </pre>
 *
 * <h2>Styling Classes</h2>
 * <ul>
 *   <li><code>col-md-[size]</code> - Primary sizing class based on the size attribute</li>
 *   <li><code>col-md-offset-[offsetSize]</code> - Offset class based on the offsetSize attribute</li>
 *   <li>Any additional classes from the styleClass attribute are appended</li>
 * </ul>
 * 
 * <h2>Attributes</h2>
 * <ul>
 *   <li>{@link BaseCuiPanel} - Inherited base panel attributes</li>
 *   <li>{@link ColumnProvider} - Provides column-specific attributes and behavior</li>
 *   <li><b>size</b>: Integer between 1-12 that defines the width of the column (required)</li>
 *   <li><b>offsetSize</b>: Integer between 0-11 that defines the offset space before the column (optional)</li>
 *   <li><b>styleClass</b>: Additional CSS classes to append (optional)</li>
 *   <li><b>style</b>: Inline CSS styles (optional)</li>
 * </ul>
 *
 * <h2>Further Documentation</h2>
 * <p>
 * More information and examples can be found in the <a href=
 * "https://cuioss.de/cui-reference-documentation/pages/documentation/cui_components/demo/layout.jsf"
 * >CUI Reference Documentation</a>
 * </p>
 *
 * @author Oliver Wolff
 * @see RowComponent
 * @see AbstractLayoutComponent
 * @see ColumnProvider
 */
@FacesComponent(BootstrapFamily.LAYOUT_COLUMN_COMPONENT)
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public class ColumnComponent extends AbstractLayoutComponent {

    @Delegate
    private final ColumnProvider columnProvider;

    /**
     * Default constructor that initializes the component with a column provider and
     * sets the renderer type. The column provider handles the column-specific attributes
     * like size and offsetSize.
     */
    public ColumnComponent() {
        columnProvider = new ColumnProvider(this);
        super.setRendererType(BootstrapFamily.LAYOUT_RENDERER);
    }

    /**
     * {@inheritDoc}
     * 
     * Resolves the CSS style classes for this component by combining:
     * <ul>
     *   <li>The column CSS classes from {@link ColumnProvider#resolveColumnCss()}, which
     *       include the size and offset classes based on the component's attributes</li>
     *   <li>Any custom style classes provided via the styleClass attribute</li>
     * </ul>
     * 
     * @return A {@link StyleClassBuilder} containing all resolved CSS classes
     */
    @Override
    public StyleClassBuilder resolveStyleClass() {
        return columnProvider.resolveColumnCss().append(getStyleClass());
    }

    /**
     * Factory method that creates a new instance of {@link ColumnComponent}.
     * This is a convenience method for programmatically creating column components.
     *
     * @param facesContext the current faces context, must not be null
     * @return a new instance of {@link ColumnComponent} initialized with the given faces context
     * @throws NullPointerException if facesContext is null
     * @throws jakarta.faces.FacesException if the component cannot be created
     */
    public static ColumnComponent createComponent(final FacesContext facesContext) {
        return ComponentUtility.createComponent(facesContext, BootstrapFamily.LAYOUT_COLUMN_COMPONENT);
    }
}
