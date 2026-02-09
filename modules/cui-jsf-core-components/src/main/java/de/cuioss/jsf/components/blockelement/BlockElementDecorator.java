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
package de.cuioss.jsf.components.blockelement;

import de.cuioss.jsf.api.components.decorator.AbstractParentDecorator;
import de.cuioss.jsf.api.components.util.ComponentModifier;
import de.cuioss.jsf.components.CuiFamily;
import jakarta.faces.application.ResourceDependency;
import jakarta.faces.component.FacesComponent;
import lombok.ToString;

/**
 * <p>Decorator component that enhances the parent component with "block element" 
 * functionality. When activated, it disables the parent element and displays a 
 * loading spinner, creating a better user experience during Ajax operations.</p>
 * 
 * <p>This component adds the necessary HTML attributes to the parent component
 * that are used by the accompanying JavaScript to manage the blocking behavior.
 * The component must be nested within another JSF component to function correctly.</p>
 * 
 * <p>Typical usage scenarios include:</p>
 * <ul>
 *   <li>Preventing double-clicks on command buttons</li>
 *   <li>Providing visual feedback during longer-running Ajax requests</li>
 *   <li>Disabling forms or input fields during submission</li>
 * </ul>
 *
 * <p><strong>Important:</strong> To reset the blocked state, the parent element 
 * should be included in the "update" or "render" attribute of the Ajax behavior.
 * The parent will then be re-enabled once the Ajax request completes.</p>
 * 
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
 * <p>The component is designed to work seamlessly with all standard JSF components
 * and CUI components that support child components.</p>
 * 
 * <p>This component is thread-safe as it contains no mutable instance state.</p>
 *
 * @author Matthias Walliczek
 * @see AbstractParentDecorator The parent class providing decorator functionality
 * @see ResourceDependency The annotation registering the required JavaScript resources
 * @since 1.0
 */
@ResourceDependency(library = "javascript.enabler", name = "enabler.blockElement.js", target = "head")
@FacesComponent(CuiFamily.BLOCKELEMENT_COMPONENT)
@ToString
public class BlockElementDecorator extends AbstractParentDecorator {

    /**
     * Decorates the parent component by adding data attributes that enable
     * the block element functionality. The JavaScript resource referenced by
     * the {@link ResourceDependency} annotation handles the actual blocking behavior.
     * 
     * @param parent The parent component to be decorated, never {@code null}
     */
    @Override
    public void decorate(ComponentModifier parent) {
        parent.addPassThrough("data-cui-block-element", "data-cui-block-element");
    }

    /**
     * Returns the component family for this component.
     * 
     * @return The component family identifier from {@link CuiFamily#COMPONENT_FAMILY}
     */
    @Override
    public String getFamily() {
        return CuiFamily.COMPONENT_FAMILY;
    }
}
