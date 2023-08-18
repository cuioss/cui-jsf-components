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
package de.cuioss.jsf.components.blockelement;

import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;

import de.cuioss.jsf.api.components.decorator.AbstractParentDecorator;
import de.cuioss.jsf.api.components.util.ComponentModifier;
import de.cuioss.jsf.components.CuiFamily;
import lombok.ToString;

/**
 * <p>
 * Decorator to activate the "block element" function for an (optional ajax
 * enabled) element: After activating the element (e.g button click or value
 * change) the element will be disabled and the spinner will be added. To reset
 * this behaviour the element should be part of the "update" or "render"
 * attribute of an ajax behaviour.
 * </p>
 * <h2>Usage</h2>
 *
 * <pre>
 * &lt;cui:blockElement /&gt;
 * </pre>
 *
 * <h2>Example</h2>
 *
 * <pre>
 * &lt;boot:commandButton ... &gt;
 *   &lt;f:ajax render="@this" /&gt;
 *   &lt;cui:blockElement /&gt;
 * &lt;/boot:commandButton &gt;
 * </pre>
 *
 * @author Matthias Walliczek
 */
@ResourceDependency(library = "javascript.enabler", name = "enabler.blockElement.js", target = "head")
@FacesComponent(CuiFamily.BLOCKELEMENT_COMPONENT)
@ToString
public class BlockElementDecorator extends AbstractParentDecorator {

    @Override
    public void decorate(ComponentModifier parent) {
        parent.addPassThrough("data-cui-block-element", "data-cui-block-element");
    }

    @Override
    public String getFamily() {
        return CuiFamily.COMPONENT_FAMILY;
    }
}
