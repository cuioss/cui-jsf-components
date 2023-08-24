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

import javax.faces.component.FacesComponent;
import javax.faces.context.FacesContext;

import de.cuioss.jsf.api.components.base.BaseCuiPanel;
import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.api.components.util.ComponentUtility;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.common.partial.ColumnProvider;
import lombok.experimental.Delegate;

/**
 * <p>
 * Renders a bootstrap conform div with the styleClass 'col-sm-' size, resulting
 * in a column.
 * </p>
 * <p>
 * The layout relies completely on the grid-system of twitter-bootstrap, see
 * <a href="http://getbootstrap.com/css/#grid">Bootstrap Documentation</a>
 * </p>
 * <ul>
 * <li>Columns must always reside within a row as a direct child.</li>
 * <li>The size and offset of a column is always defined in 1/12 steps. A row is
 * limited to 12. If the columnSize (and offsets) are more than 12 the surplus
 * columns will be rendered in the next line.</li>
 * <li>Offsets are a convenient way to define a gap between two columns</li>
 * <li>The size definitions of the components are always related to md. In case
 * you want to change the behavior you need to add additional styleClasses, see
 * example.</li>
 * </ul>
 * <p>
 * More information and examples can be found in the <a href=
 * "https://cuioss.de/cui-reference-documentation/faces/pages/documentation/cui_components/demo/layout.jsf"
 * >Reference Documentation</a>
 * </p>
 * <h2>Usage</h2>
 *
 * <pre>
 * {@code
<cui:row>
        <cui:column size="4">
            <span>Size="4"</span>
        </cui:column>
        <cui:column size="2" >
            <span>Size="2"</span>
        </cui:column>
        <cui:column size="4" offsetSize="2">
            <span>Size="3", Offset="4"</span>
        </cui:column>
</cui:row>
}
 * </pre>
 *
 * <h2>Styling</h2>
 * <ul>
 * <li>The marker css class is 'col-md-' + size attribute</li>
 * <li>The offset css class is 'col-md-offset-' + offsetSize attribute</li>
 * </ul>
 * <h2>Attributes</h2>
 * <ul>
 * <li>{@link BaseCuiPanel}</li>
 * <li>{@link ColumnProvider}</li>
 * </ul>
 *
 * @author Oliver Wolff
 */
@FacesComponent(BootstrapFamily.LAYOUT_COLUMN_COMPONENT)
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public class ColumnComponent extends AbstractLayoutComponent {

    @Delegate
    private final ColumnProvider columnProvider;

    /**
     *
     */
    public ColumnComponent() {
        columnProvider = new ColumnProvider(this);
        super.setRendererType(BootstrapFamily.LAYOUT_RENDERER);
    }

    @Override
    public StyleClassBuilder resolveStyleClass() {
        return columnProvider.resolveColumnCss().append(getStyleClass());
    }

    /**
     * Shorthand for creating a new instance of {@link ColumnComponent}
     *
     * @param facesContext must not be null
     * @return a new instance of {@link ColumnComponent}
     */
    public static ColumnComponent createComponent(final FacesContext facesContext) {
        return ComponentUtility.createComponent(facesContext, BootstrapFamily.LAYOUT_COLUMN_COMPONENT);
    }
}
