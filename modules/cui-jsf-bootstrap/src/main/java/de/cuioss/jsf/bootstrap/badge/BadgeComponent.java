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
package de.cuioss.jsf.bootstrap.badge;

import jakarta.faces.component.FacesComponent;

import de.cuioss.jsf.api.components.base.BaseCuiOutputText;
import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;

/**
 * Component Class for creating badges. All standard attributes are defined by
 * {@link BaseCuiOutputText}. The defining css class "badge"
 *
 * @author Oliver Wolff
 *
 */
@FacesComponent(BootstrapFamily.BADGE_COMPONENT)
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public class BadgeComponent extends BaseCuiOutputText {

    @Override
    public StyleClassBuilder resolveComponentSpecificStyleClasses() {
        return CssBootstrap.BADGE.getStyleClassBuilder();
    }
}
