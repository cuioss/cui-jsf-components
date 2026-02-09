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
package de.cuioss.jsf.bootstrap.icon;

import de.cuioss.jsf.api.components.base.AbstractBaseCuiComponent;
import de.cuioss.jsf.api.components.partial.*;
import de.cuioss.jsf.api.components.util.ComponentUtility;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import jakarta.faces.component.FacesComponent;
import jakarta.faces.context.FacesContext;
import lombok.experimental.Delegate;

/**
 * <p>
 * Renders an icon component following the CUI icon standards and Bootstrap styling patterns.
 * This component outputs an HTML span element with the appropriate CSS classes to display
 * a vector icon from the CUI icon library.
 * </p>
 * 
 * <h2>Features</h2>
 * <ul>
 *   <li>Consistent icon rendering across the application</li>
 *   <li>Support for different icon sizes (xs, sm, md, lg, xl)</li>
 *   <li>Support for contextual states (default, danger, warning, info, etc.)</li>
 *   <li>Tooltip/title support with resource bundle integration</li>
 *   <li>Customizable styling through additional CSS classes and styles</li>
 * </ul>
 * 
 * <h2>Icon Library</h2>
 * <p>
 * The component uses the CUI icon library, which provides a comprehensive set of
 * vector icons for application interfaces. Available icons can be viewed in the
 * <a href="https://cuioss.de/cui-reference-documentation/pages/documentation/icons/cui_icons.jsf">
 * CUI Reference Documentation</a>.
 * </p>
 * 
 * <h2>Attributes</h2>
 * <ul>
 *   <li>{@link TitleProvider} - For tooltip/title support with resource bundle integration</li>
 *   <li>{@link ContextSizeProvider} - For icon size configuration (xs, sm, md, lg, xl)</li>
 *   <li>{@link ComponentStyleClassProvider} - For additional CSS styling</li>
 *   <li>{@link StyleAttributeProvider} - For inline CSS styling</li>
 *   <li>{@link ContextStateProvider} - For contextual states (default, primary, danger, etc.)</li>
 *   <li>{@link IconProvider} - For specifying which icon to display from the CUI icon library</li>
 * </ul>
 * 
 * <h2>Usage Examples</h2>
 * 
 * <p><b>Basic usage:</b></p>
 * <pre>
 * &lt;boot:icon icon="cui-icon-drink" /&gt;
 * </pre>
 * 
 * <p><b>With size and state:</b></p>
 * <pre>
 * &lt;boot:icon icon="cui-icon-warning" size="xl" state="danger" /&gt;
 * </pre>
 * 
 * <p><b>With tooltip:</b></p>
 * <pre>
 * &lt;boot:icon icon="cui-icon-info" titleKey="info.tooltip" /&gt;
 * </pre>
 * 
 * <p><b>With custom styling:</b></p>
 * <pre>
 * &lt;boot:icon icon="cui-icon-home" styleClass="my-custom-icon" style="margin-right: 5px;" /&gt;
 * </pre>
 *
 * @author Oliver Wolff
 * @see IconRenderer
 * @see LabeledIconComponent
 * @see MimeTypeIconComponent
 * @see GenderIconComponent
 */
@FacesComponent(BootstrapFamily.ICON_COMPONENT)
public class IconComponent extends AbstractBaseCuiComponent implements TitleProvider {

    @Delegate
    private final TitleProvider titleProvider;

    @Delegate
    private final ContextSizeProvider contextSizeProvider;

    @Delegate
    private final ContextStateProvider contextStateProvider;

    @Delegate
    private final IconProvider iconProvider;

    public IconComponent() {
        super.setRendererType(BootstrapFamily.ICON_COMPONENT_RENDERER);
        titleProvider = new TitleProviderImpl(this);
        contextSizeProvider = new ContextSizeProvider(this);
        contextStateProvider = new ContextStateProvider(this);
        iconProvider = new IconProvider(this);
    }


    @Override
    public String getFamily() {
        return BootstrapFamily.COMPONENT_FAMILY;
    }

    /**
     * Shortcut for creating and casting a component of type {@link IconComponent}.
     *
     * @param facesContext must not be null
     * @return a newly created {@link IconComponent}
     */
    public static IconComponent createComponent(final FacesContext facesContext) {
        return ComponentUtility.createComponent(facesContext, BootstrapFamily.ICON_COMPONENT);
    }

}
