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

import de.cuioss.jsf.api.components.css.AlignHolder;
import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.api.components.partial.AlignProvider;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import jakarta.faces.component.FacesComponent;
import lombok.experimental.Delegate;

/**
 * <p>
 * A lightweight component for grouping control elements (typically buttons) with consistent
 * alignment and styling. This component provides a simpler alternative to the full-featured
 * {@link ControlGroupComponent} when only basic grouping and alignment are needed.
 * </p>
 * 
 * <h2>Features</h2>
 * <ul>
 *   <li>Automatic application of Bootstrap button group styling</li>
 *   <li>Support for left/right alignment of the button group</li>
 *   <li>Minimal rendering overhead compared to full control groups</li>
 *   <li>Simple API with few configuration options</li>
 * </ul>
 * 
 * <h2>Usage</h2>
 * <pre>
 * &lt;!-- Default left-aligned button group --&gt;
 * &lt;cui:quickControlGroup&gt;
 *   &lt;cui:button labelValue="Save" /&gt;
 *   &lt;cui:button labelValue="Cancel" /&gt;
 * &lt;/cui:quickControlGroup&gt;
 * 
 * &lt;!-- Right-aligned button group --&gt;
 * &lt;cui:quickControlGroup align="RIGHT"&gt;
 *   &lt;cui:button labelValue="Back" /&gt;
 *   &lt;cui:button labelValue="Next" /&gt;
 * &lt;/cui:quickControlGroup&gt;
 * </pre>
 * 
 * <h2>Attributes</h2>
 * <ul>
 *   <li>{@link AlignProvider} - Controls horizontal alignment (LEFT/RIGHT, default: LEFT)</li>
 *   <li>styleClass - Additional CSS classes to apply</li>
 *   <li>style - Inline CSS styles</li>
 * </ul>
 * 
 * <h2>Styling</h2>
 * <p>
 * This component uses the 'quick-control-group' CSS class with additional alignment
 * modifiers ('pull-left' or 'pull-right') depending on the align attribute.
 * </p>
 *
 * @author Sven Haag
 * @see ControlGroupComponent
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
