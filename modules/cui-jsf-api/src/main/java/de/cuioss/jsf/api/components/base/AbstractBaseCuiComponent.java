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
package de.cuioss.jsf.api.components.base;

import de.cuioss.jsf.api.components.partial.*;
import jakarta.faces.component.StateHelper;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIComponentBase;
import jakarta.faces.component.UINamingContainer;
import jakarta.faces.context.FacesContext;
import lombok.experimental.Delegate;

/**
 * Minimal superset for cui-based components that are no
 * {@link UINamingContainer}. Therefore, it provides the handling of the
 * styleClass and style attribute and the implicit attributes provided by
 * {@link UIComponentBase}. In addition, it acts as a {@link ComponentBridge}
 *
 * @author Oliver Wolff
 */
public abstract class AbstractBaseCuiComponent extends UIComponentBase
        implements ComponentBridge, ComponentStyleClassProvider, StyleAttributeProvider {

    @Delegate
    private final ComponentStyleClassProvider styleClassProvider;

    @Delegate
    private final StyleAttributeProvider styleAttributeProvider;

    protected AbstractBaseCuiComponent() {
        styleClassProvider = new ComponentStyleClassProviderImpl(this);
        styleAttributeProvider = new StyleAttributeProviderImpl(this);
    }

    @Override
    public StateHelper stateHelper() {
        return getStateHelper();
    }

    @Override
    public FacesContext facesContext() {
        return getFacesContext();
    }

    @Override
    public UIComponent facet(String facetName) {
        return getFacet(facetName);
    }
}
