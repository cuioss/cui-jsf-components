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

import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.StateHelper;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.html.HtmlOutputLabel;
import jakarta.faces.context.FacesContext;

import de.cuioss.jsf.api.components.partial.ComponentBridge;
import de.cuioss.jsf.api.components.partial.LabelProvider;
import de.cuioss.jsf.api.components.partial.TitleProvider;
import de.cuioss.jsf.api.components.partial.TitleProviderImpl;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import lombok.experimental.Delegate;

/**
 * <p>
 * Renders an Label for input elements. The label and title is resolved using
 * the cui standard label-resolving mechanism.
 * </p>
 * <h2>Attributes</h2>
 * <ul>
 * <li>{@link LabelProvider}</li>
 * <li>{@link TitleProvider}</li>
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

    /**
     *
     */
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
