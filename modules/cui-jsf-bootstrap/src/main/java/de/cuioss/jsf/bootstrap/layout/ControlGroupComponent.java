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
import de.cuioss.jsf.bootstrap.common.partial.ColumnProvider;
import jakarta.faces.component.FacesComponent;
import lombok.experimental.Delegate;

/**
 * <p>
 * Renders a Bootstrap form-group for placing buttons and controls within forms. Combines
 * a form-group with an embedded column for proper alignment with other form elements.
 * </p>
 * <p>
 * The component uses Bootstrap's grid system to control size and positioning. 
 * It requires placement within a container having 'form-horizontal' class.
 * </p>
 * <ul>
 * <li>controlGoups must be within container with 'form-horizontal'.</li>
 * <li>They act similar to formGroups or labeledContainer.</li>
 * <li>The size and offset of a controlGoup is always defined in 1/12 steps. A
 * row is limited to 12. If the columnSize (and offsets) are more than 12 the
 * surplus columns will be rendered in the next line.</li>
 * <li>The offset is used for the inner spacing of the column</li>
 * <li>The size definitions of the components are always related to sm. In case
 * you want to change the behavior you need to add additional styleClasses, see
 * example. The default size is '8'</li>
 * </ul>
 * <p>
 * More information and examples can be found in the <a href=
 * "https://cuioss.de/cui-reference-documentation/pages/documentation/cui_components/demo/layout.jsf"
 * >Reference Documentation</a>
 * </p>
 * <h2>Styling</h2>
 * <ul>
 * <li>The marker css class is 'form-group' with 'col-sm-' + size attribute</li>
 * <li>The offset css class is 'col-sm-offset-' + offsetSize attribute</li>
 * </ul>
 * <h2>Attributes</h2>
 * <ul>
 * <li>{@link BaseCuiPanel}</li>
 * <li>{@link ColumnProvider} - Size defaults to 8 columns</li>
 * </ul>
 *
 * @author Oliver Wolff
 */
@FacesComponent(BootstrapFamily.LAYOUT_CONTROL_GROUP_COMPONENT)
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public class ControlGroupComponent extends BasicBootstrapPanelComponent {

    private static final Integer DEFAULT_COLUMN_SIZE = 8;

    @Delegate
    private final ColumnProvider columnProvider;

    public ControlGroupComponent() {
        columnProvider = new ColumnProvider(this, DEFAULT_COLUMN_SIZE);
        super.setRendererType(BootstrapFamily.LAYOUT_CONTROL_GROUP_RENDERER);
    }

}
