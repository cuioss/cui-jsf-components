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

import javax.faces.component.FacesComponent;

import de.cuioss.jsf.api.components.css.AlignHolder;
import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.api.components.partial.AlignProvider;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import lombok.experimental.Delegate;

/**
 * Wraps a number of buttons. Quick means solely appending the CSS-class
 * 'quick-control-group' and the styleClass attribute, if set, will be attached
 * to the surrounding DIV. For more complex layouts use controlGroup
 * ({@link ControlGroupComponent}}). Rendered by javax.faces.Group.
 *
 * @author Sven Haag
 */
@FacesComponent(BootstrapFamily.QUICK_CONTROL_GROUP_COMPONENT)
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public class QuickControlGroupComponent extends AbstractLayoutComponent {

    @Delegate
    private final AlignProvider alignProvider;

    /**
     * Creates a QuickControlGroupComponent with
     * {@link BootstrapFamily#LAYOUT_RENDERER} renderer and {@link AlignProvider} as
     * align provider.
     */
    public QuickControlGroupComponent() {
        alignProvider = new AlignProvider(this);
        super.setRendererType(BootstrapFamily.LAYOUT_RENDERER);
    }

    @Override
    public StyleClassBuilder resolveStyleClass() {
        if (AlignHolder.LEFT.equals(alignProvider.resolveAlign())) {
            return CssBootstrap.QUICK_CONTROL_GROUP_LEFT.getStyleClassBuilder().append(super.getStyleClass());
        }
        return CssBootstrap.QUICK_CONTROL_GROUP_RIGHT.getStyleClassBuilder().append(super.getStyleClass());
    }
}
