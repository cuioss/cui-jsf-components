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

import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import jakarta.faces.component.FacesComponent;

/**
 * <p>
 * Renders a Bootstrap input-group container that combines form controls with additional text,
 * buttons, or button groups on either side of the input. This component helps create
 * cohesive form control extensions with custom prepended or appended elements.
 * </p>
 * <p>
 * Input groups extend form controls by adding text, buttons, or button groups on either side of textual inputs.
 * They're useful for adding common elements like currency symbols, search icons, or action buttons
 * directly attached to form inputs.
 * </p>
 * <p>
 * More information and examples can be found in the <a href=
 * "https://cuioss.de/cui-reference-documentation/pages/documentation/cui_components/demo/layout.jsf"
 * >Reference Documentation</a>
 * </p>
 * <h2>Usage</h2>
 *
 * <pre>
 *  {@code <cui:inputGroup>
 *    <cui:inputGroupAddon position="prepend">@</cui:inputGroupAddon>
 *    <h:inputText styleClass="form-control" />
 *  </cui:inputGroup>}
 * </pre>
 *
 * <h2>Styling</h2>
 * <ul>
 * <li>The marker css class is 'input-group'</li>
 * </ul>
 *
 * @author Oliver Wolff
 *
 */
@FacesComponent(BootstrapFamily.LAYOUT_INPUT_GROUP_COMPONENT)
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public class InputGroupComponent extends AbstractLayoutComponent {

    public InputGroupComponent() {
        super.setRendererType(BootstrapFamily.LAYOUT_RENDERER);
    }

    @Override
    public StyleClassBuilder resolveStyleClass() {
        return CssBootstrap.INPUT_GROUP.getStyleClassBuilder().append(super.getStyleClass());
    }
}
