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
package de.cuioss.jsf.bootstrap.icon;

import de.cuioss.jsf.api.components.base.AbstractBaseCuiComponent;
import de.cuioss.jsf.api.components.partial.*;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import jakarta.faces.component.FacesComponent;
import lombok.experimental.Delegate;

/**
 * <p>
 * Enhanced icon component that combines an icon with a text label. This component renders
 * both an icon from the CUI icon library and associated text, with configurable positioning
 * of the icon relative to the text.
 * </p>
 * 
 * <h2>Features</h2>
 * <ul>
 *   <li>Displays both an icon and associated text in a single component</li>
 *   <li>Configurable icon positioning (left, right) relative to the text</li>
 *   <li>Support for all icon types available in the CUI icon library</li>
 *   <li>Localization support for labels through resource bundle integration</li>
 *   <li>Tooltip/title support with resource bundle integration</li>
 *   <li>Customizable styling through additional CSS classes and styles</li>
 * </ul>
 * 
 * <h2>Component Structure</h2>
 * <p>
 * The component renders as a span containing both the icon (as a nested span with icon classes)
 * and the text. The order of these elements depends on the iconAlign attribute:
 * </p>
 * <ul>
 *   <li>When iconAlign="left" (default): Icon appears before the text</li>
 *   <li>When iconAlign="right": Icon appears after the text</li>
 * </ul>
 * 
 * <h2>Attributes</h2>
 * <ul>
 *   <li>{@link TitleProvider} - For tooltip/title support with resource bundle integration</li>
 *   <li>{@link IconAlignProvider} - For controlling icon positioning (left, right)</li>
 *   <li>{@link ComponentStyleClassProvider} - For additional CSS styling of the entire component</li>
 *   <li>{@link StyleAttributeProvider} - For inline CSS styling</li>
 *   <li>{@link LabelProvider} - For specifying the text to display alongside the icon</li>
 *   <li>{@link IconProvider} - For specifying which icon to display from the CUI icon library</li>
 * </ul>
 * 
 * <h2>Usage Examples</h2>
 * 
 * <p><b>Basic usage with explicit label:</b></p>
 * <pre>
 * &lt;cui:labeledIcon icon="cui-icon-alarm" labelValue="Rrrring" /&gt;
 * </pre>
 * 
 * <p><b>With localized label from resource bundle:</b></p>
 * <pre>
 * &lt;cui:labeledIcon icon="cui-icon-info" labelKey="info.message" /&gt;
 * </pre>
 * 
 * <p><b>With right-aligned icon:</b></p>
 * <pre>
 * &lt;cui:labeledIcon icon="cui-icon-arrow-right" labelValue="Continue" iconAlign="right" /&gt;
 * </pre>
 * 
 * <p><b>With tooltip and custom styling:</b></p>
 * <pre>
 * &lt;cui:labeledIcon icon="cui-icon-warning" labelValue="Attention required" 
 *                 titleValue="Important notice" styleClass="warning-message" /&gt;
 * </pre>
 *
 * @author Oliver Wolff
 * @see IconComponent
 * @see LabeledIconRenderer
 */
@FacesComponent(BootstrapFamily.LABELED_ICON_COMPONENT)
public class LabeledIconComponent extends AbstractBaseCuiComponent implements TitleProvider {

    @Delegate
    private final TitleProvider titleProvider;

    @Delegate
    private final IconProvider iconProvider;

    @Delegate
    private final LabelProvider labelProvider;

    @Delegate
    private final IconAlignProvider iconAlignProvider;

    public LabeledIconComponent() {
        super.setRendererType(BootstrapFamily.LABELED_ICON_COMPONENT_RENDERER);
        titleProvider = new TitleProviderImpl(this);
        iconProvider = new IconProvider(this);
        labelProvider = new LabelProvider(this);
        iconAlignProvider = new IconAlignProvider(this);
    }

    @Override
    public String getFamily() {
        return BootstrapFamily.COMPONENT_FAMILY;
    }


}
