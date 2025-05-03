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
 * @author Oliver Wolff
 *
 */
@FacesComponent(INLINE_CONFIRM_COMPONENT)
@ResourceDependency(library = "javascript.enabler", name = "enabler.inline_confirm.js")
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public class InlineConfirmComponent extends BaseCuiPanel {

    static final String INITIAL_FACET_NAME = "initial";

    /**
     *
     */
    public InlineConfirmComponent() {
        super.setRendererType(INLINE_CONFIRM_RENDERER);
    }

    /**
     * @return the child of the component as {@link ComponentModifier}
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
     * @return the facet to be displayed initially
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
