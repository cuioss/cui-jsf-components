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

import de.cuioss.jsf.api.components.base.BaseCuiOutputText;
import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import jakarta.faces.component.FacesComponent;

/**
 * Component for Bootstrap badge elements - small count or labeling elements.
 * Extends {@link BaseCuiOutputText} with Bootstrap badge styling.
 * 
 * <h3>Usage:</h3>
 * <pre>
 * &lt;cui:badge value="42"/&gt;
 * &lt;cui:badge value="New!" styleClass="badge-primary"/&gt;
 * &lt;h3&gt;Example Heading &lt;cui:badge value="New"/&gt;&lt;/h3&gt;
 * </pre>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
@FacesComponent(BootstrapFamily.BADGE_COMPONENT)
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public class BadgeComponent extends BaseCuiOutputText {

    /**
     * {@inheritDoc}
     * 
     * <p>For BadgeComponent, this method adds the Bootstrap "badge" CSS class to ensure
     * proper styling according to Bootstrap conventions.</p>
     * 
     * @return The {@link StyleClassBuilder} with the Bootstrap badge class applied
     */
    @Override
    public StyleClassBuilder resolveComponentSpecificStyleClasses() {
        return CssBootstrap.BADGE.getStyleClassBuilder();
    }
}
