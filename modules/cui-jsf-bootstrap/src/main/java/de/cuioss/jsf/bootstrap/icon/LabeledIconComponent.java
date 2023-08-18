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

import javax.faces.component.FacesComponent;

import de.cuioss.jsf.api.components.base.AbstractBaseCuiComponent;
import de.cuioss.jsf.api.components.partial.ComponentStyleClassProvider;
import de.cuioss.jsf.api.components.partial.IconAlignProvider;
import de.cuioss.jsf.api.components.partial.IconProvider;
import de.cuioss.jsf.api.components.partial.LabelProvider;
import de.cuioss.jsf.api.components.partial.StyleAttributeProvider;
import de.cuioss.jsf.api.components.partial.TitleProvider;
import de.cuioss.jsf.api.components.partial.TitleProviderImpl;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import lombok.experimental.Delegate;

/**
 * <p>
 * Renders an icon with a text. The icon is rendered as a span with the
 * according classes. The label and icons are resolved using the cui standard
 * label-resolving mechanism.
 * </p>
 * <p>
 * Sample can be found at the <a href=
 * "https://cuioss.de/cui-reference-documentation/faces/pages/documentation/icons/icon_components.jsf">
 * Reference Documentation</a>
 * </p>
 *
 * <h2>Attributes</h2>
 * <ul>
 * <li>{@link TitleProvider}</li>
 * <li>{@link IconAlignProvider}</li>
 * <li>{@link ComponentStyleClassProvider}</li>
 * <li>{@link StyleAttributeProvider}</li>
 * <li>{@link LabelProvider}</li>
 * <li>{@link IconProvider}</li>
 * </ul>
 * <h2>Usage</h2>
 *
 * <pre>
 * &lt;cui:labeledIcon icon="cui-icon-alarm" labelValue="Rrrring" titleValue="Some Title" /&gt;
 * </pre>
 *
 * @author Oliver Wolff
 *
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

    /**
     *
     */
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
