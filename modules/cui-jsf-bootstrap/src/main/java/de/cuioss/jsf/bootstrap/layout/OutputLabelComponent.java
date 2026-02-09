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
package de.cuioss.jsf.bootstrap.layout;

import de.cuioss.jsf.api.components.partial.ComponentBridge;
import de.cuioss.jsf.api.components.partial.LabelProvider;
import de.cuioss.jsf.api.components.partial.TitleProvider;
import de.cuioss.jsf.api.components.partial.TitleProviderImpl;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.StateHelper;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.html.HtmlOutputLabel;
import jakarta.faces.context.FacesContext;
import lombok.experimental.Delegate;

/**
 * <p>
 * A Bootstrap-styled label component for form elements that integrates with CUI's
 * standardized label resolution mechanism, providing consistent labeling across the application.
 * </p>
 * 
 * <h2>Features</h2>
 * <ul>
 * <li>Extends the standard JSF {@link HtmlOutputLabel} with CUI label resolution</li>
 * <li>Supports label text from resource bundles through labelKey/labelValue attributes</li>
 * <li>Provides tooltip/title support with consistent resolution patterns</li>
 * <li>Automatic integration with form validation states</li>
 * </ul>
 * 
 * <h2>Usage</h2>
 * <pre>
 * &lt;!-- Basic usage with direct value --&gt;
 * &lt;boot:outputLabel for="inputId" labelValue="First Name" /&gt;
 * 
 * &lt;!-- With resource bundle key --&gt;
 * &lt;boot:outputLabel for="inputId" labelKey="label.firstName" /&gt;
 * 
 * &lt;!-- With title/tooltip --&gt;
 * &lt;boot:outputLabel for="inputId" labelKey="label.firstName" titleKey="title.firstName" /&gt;
 * </pre>
 * 
 * <h2>Attributes</h2>
 * <ul>
 * <li>{@link LabelProvider} - Provides label text resolution (labelKey/labelValue)</li>
 * <li>{@link TitleProvider} - Provides title/tooltip resolution (titleKey/titleValue)</li>
 * <li>All standard attributes from {@link HtmlOutputLabel}</li>
 * </ul>
 *
 * @author Matthias Walliczek
 */
@FacesComponent(BootstrapFamily.OUTPUT_LABEL_COMPONENT)
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public class OutputLabelComponent extends HtmlOutputLabel implements ComponentBridge, TitleProvider {

    @Delegate
    private final LabelProvider labelProvider;

    @Delegate
    private final TitleProvider titleProvider;

    public OutputLabelComponent() {
        labelProvider = new LabelProvider(this);
        titleProvider = new TitleProviderImpl(this);
    }

    @Override
    public StateHelper stateHelper() {
        return this.getStateHelper();
    }

    @Override
    public FacesContext facesContext() {
        return getFacesContext();
    }

    @Override
    public Object getValue() {
        return labelProvider.resolveLabel();
    }

    @Override
    public UIComponent facet(String facetName) {
        return getFacet(facetName);
    }
}
