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

import de.cuioss.jsf.api.components.myfaces.MyFacesDelegateStyleClassAdapter;
import de.cuioss.jsf.api.components.myfaces.MyFacesDelegateTitleAdapter;
import de.cuioss.jsf.api.components.partial.*;
import lombok.experimental.Delegate;

import jakarta.faces.component.StateHelper;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.html.HtmlCommandButton;
import jakarta.faces.context.FacesContext;

/**
 * Base class for creating cui variants of {@link HtmlCommandButton}
 * implementing {@link ComponentBridge} and {@link TitleProvider}
 *
 * @author Oliver Wolff
 */
public class BaseCuiCommandButton extends HtmlCommandButton implements ComponentBridge, TitleProvider, MyFacesDelegateStyleClassAdapter, MyFacesDelegateTitleAdapter {

    @Delegate
    private final TitleProvider titleProvider;

    @Delegate
    private final ComponentStyleClassProvider styleClassProvider;

    public BaseCuiCommandButton() {
        titleProvider = new TitleProviderImpl(this);
        styleClassProvider = new ComponentStyleClassProviderImpl(this);
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
    public UIComponent facet(final String facetName) {
        return getFacet(facetName);
    }

    @Override
    public String getTitle() {
        return titleProvider.resolveTitle();
    }

    @Override
    public void writeStyleClassToParent() {
        super.setStyleClass(styleClassProvider.getStyleClass());
    }

    @Override
    public void writeTitleToParent() {
        super.setTitle(titleProvider.getTitle());
    }
}
