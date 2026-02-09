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
package de.cuioss.jsf.api.components.util.styleclass;

import de.cuioss.jsf.api.components.util.modifier.ComponentModifierFactory;
import jakarta.faces.component.UIComponent;

/**
 * Factory for creating wrapper classes dealing with modification of styleClass
 * attributes.
 *
 * @author Oliver Wolff (Oliver Wolff)
 */
public enum StyleClassModifierFactory {

    ;

    /**
     * Factory method to wrap {@link UIComponent} with
     * {@link CombinedComponentModifier} functionality.
     *
     * @param toBeWrapped {@linkplain UIComponent} target to be wrapped
     * @return {@link CombinedComponentModifier} if component provide styleClass
     *         attribute
     * @throws IllegalArgumentException if toBeWrapped component doesn't provide
     *                                  styleClass attribute
     */
    public static CombinedComponentModifier findFittingWrapper(final UIComponent toBeWrapped) {
        final var componentModifier = ComponentModifierFactory.findFittingWrapper(toBeWrapped);
        if (componentModifier.isSupportsStyleClass()) {
            return new DelegateStyleClassModifier(componentModifier);
        }
        throw new IllegalArgumentException("No wrapper providing styleClass implemented for " + toBeWrapped.getClass());
    }
}
