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
 * Renders a Bootstrap form-group container that provides proper spacing and structure
 * for form elements. The form-group is a fundamental building block in Bootstrap's form system
 * that wraps labels, controls, and help text.
 * </p>
 *
 * <h2>Bootstrap Form Structure</h2>
 * <p>
 * Form groups are the easiest way to add structure to forms and provide a consistent spacing
 * and alignment for labels, controls, validation states, and help text. In Bootstrap's form system,
 * form-groups create optimally spaced blocks that maintain proper vertical rhythm and horizontal
 * alignment, especially when used with grid classes.
 * </p>
 * <p>
 * More information and examples can be found in the <a href=
 * "https://cuioss.de/cui-reference-documentation/pages/documentation/cui_components/demo/layout.jsf"
 * >Reference Documentation</a>
 * </p>
 * <h2>Usage</h2>
 *
 * <pre>
 *  {@code <cui:formGroup>
 *    <cui:outputLabel for="input1" value="Label" />
 *    <h:inputText id="input1" />
 *  </cui:formGroup>}
 * </pre>
 *
 * <h2>Styling</h2>
 * <ul>
 * <li>The marker css class is 'form-group'</li>
 * </ul>
 *
 * @author Oliver Wolff
 */
@FacesComponent(BootstrapFamily.LAYOUT_FORMGROUP_COMPONENT)
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public class FormGroupComponent extends AbstractLayoutComponent {

    public FormGroupComponent() {
        super.setRendererType(BootstrapFamily.LAYOUT_RENDERER);
    }

    @Override
    public StyleClassBuilder resolveStyleClass() {
        return CssBootstrap.FORM_GROUP.getStyleClassBuilder().append(super.getStyleClass());
    }
}
