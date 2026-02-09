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
package de.cuioss.jsf.components.inlineconfirm;

import static de.cuioss.jsf.components.CuiFamily.INLINE_CONFIRM_COMPONENT;
import static de.cuioss.jsf.components.CuiFamily.INLINE_CONFIRM_RENDERER;

import de.cuioss.jsf.api.components.base.BaseCuiPanel;
import de.cuioss.jsf.api.components.util.ComponentModifier;
import de.cuioss.jsf.api.components.util.modifier.ComponentModifierFactory;
import de.cuioss.jsf.components.CuiFamily;
import jakarta.faces.application.ResourceDependency;
import jakarta.faces.component.FacesComponent;

/**
 * <p>
 * The InlineConfirmComponent provides a two-step confirmation mechanism where the initial action
 * element is replaced with a confirmation element when the user interacts with it.
 * </p>
 * 
 * <p>
 * This component requires two elements to work properly:
 * </p>
 * 
 * <ol>
 * <li>A facet named 'initial' which is displayed by default and represents the action trigger
 * </li>
 * <li>A child component that will be displayed as the confirmation step when the initial trigger
 * is activated</li>
 * </ol>
 * 
 * <p>
 * The component automatically handles the toggle between these two states. When the user clicks
 * the initial element, it is hidden and the confirmation element is displayed. The confirmation
 * element typically contains options to proceed with the action or cancel it.
 * </p>
 * 
 * <p>
 * This approach provides a more user-friendly alternative to modal dialogs for confirming
 * potentially destructive actions.
 * </p>
 * 
 * <h2>Usage Example</h2>
 * 
 * <pre>
 * &lt;cui:inlineConfirm id="confirmDelete"&gt;
 * &nbsp; &lt;f:facet name="initial"&gt;
 * &nbsp; &nbsp;&lt;button type="button" class="btn btn-danger"&gt;Delete&lt;/button&gt;
 * &nbsp; &lt;/f:facet&gt;
 * &nbsp; &lt;div class="btn-group"&gt;
 * &nbsp; &nbsp;&lt;button type="button" class="btn btn-danger" onclick="performDelete()"&gt;Confirm&lt;/button&gt;
 * &nbsp; &nbsp;&lt;button type="button" class="btn btn-default" onclick="cancelDelete()"&gt;Cancel&lt;/button&gt;
 * &nbsp; &lt;/div&gt;
 * &lt;/cui:inlineConfirm&gt;
 * </pre>
 * 
 * <p>
 * This component is thread-safe when the underlying JSF implementation properly synchronizes
 * concurrent access to the component state.
 * </p>
 * 
 * @author Oliver Wolff
 * @since 1.0
 */
@FacesComponent(INLINE_CONFIRM_COMPONENT)
@ResourceDependency(library = "javascript.enabler", name = "enabler.inline_confirm.js")
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public class InlineConfirmComponent extends BaseCuiPanel {

    static final String INITIAL_FACET_NAME = "initial";

    /**
     * Default constructor that configures the component with the appropriate renderer type.
     */
    public InlineConfirmComponent() {
        super.setRendererType(INLINE_CONFIRM_RENDERER);
    }

    /**
     * Provides access to the child component that will be displayed as the confirmation step.
     * 
     * @return the first child of the component wrapped as a {@link ComponentModifier}
     * @throws IllegalArgumentException if no child is provided for this component
     */
    public ComponentModifier getChildAsModifier() {
        var iterator = getChildren().iterator();
        if (!iterator.hasNext()) {
            throw new IllegalArgumentException("You must provide a child for this component");
        }
        var child = iterator.next();
        return ComponentModifierFactory.findFittingWrapper(child);
    }

    /**
     * Provides access to the initial facet that will be displayed by default.
     * 
     * @return the facet with name 'initial' wrapped as a {@link ComponentModifier}
     * @throws IllegalArgumentException if no facet with name 'initial' is provided
     */
    public ComponentModifier getInitialFacet() {
        var facet = getFacet(INITIAL_FACET_NAME);
        if (null == facet) {
            throw new IllegalArgumentException("You must provide a facet with name = 'initial'");
        }
        return ComponentModifierFactory.findFittingWrapper(facet);
    }

    @Override
    public String getFamily() {
        return CuiFamily.COMPONENT_FAMILY;
    }
}
