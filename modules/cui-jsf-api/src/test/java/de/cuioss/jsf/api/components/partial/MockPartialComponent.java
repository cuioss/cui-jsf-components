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
package de.cuioss.jsf.api.components.partial;

import de.cuioss.jsf.api.components.html.Node;
import jakarta.faces.component.StateHelper;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIComponentBase;
import jakarta.faces.context.FacesContext;
import lombok.experimental.Delegate;

/**
 * Uses all available provider
 *
 * @author Oliver Wolff
 */
public class MockPartialComponent extends UIComponentBase implements ComponentBridge {

    @Delegate
    private final TitleProvider titleProvider;

    @Delegate
    private final CloseButtonTitleProvider closeButtonTitleProvider;

    @Delegate
    private final ContentProvider contentProvider;

    @Delegate
    private final ContextSizeProvider contextSizeProvider;

    @Delegate
    private final ContextStateProvider contextStateProvider;

    @Delegate
    private final ComponentStyleClassProvider styleClassProvider;

    @Delegate
    private final HeaderProvider headerProvider;

    @Delegate
    private final FooterProvider footerProvider;

    @Delegate
    private final IconProvider iconProvider;

    @Delegate
    private final LabelProvider labelProvider;

    @Delegate
    private final AlignProvider alignProvider;

    @Delegate
    private final IconAlignProvider iconAlignProvider;

    @Delegate
    private final ModelProvider modelProvider;

    @Delegate
    private final StyleAttributeProvider styleAttributeProvider;

    @Delegate
    private final PlaceholderProvider placeholderProvider;

    @Delegate
    private final DisabledComponentProvider disabledComponentProvider;

    @Delegate
    private final CollapseSwitchProvider collapseSwitchProvider;

    @Delegate
    private final ActiveIndexManagerProvider activeIndexManagerProvider;

    @Delegate
    private final DeferredProvider deferredProvider;

    @Delegate
    private final HtmlElementProvider htmlElementProvider;

    @Delegate
    private final AjaxProvider ajaxProvider;

    @Delegate
    private final KeyBindingProvider keyBindingProvider;

    public MockPartialComponent() {
        titleProvider = new TitleProviderImpl(this);
        closeButtonTitleProvider = new CloseButtonTitleProvider(this);
        contentProvider = new ContentProvider(this);
        contextSizeProvider = new ContextSizeProvider(this);
        contextStateProvider = new ContextStateProvider(this);
        styleClassProvider = new ComponentStyleClassProviderImpl(this);
        headerProvider = new HeaderProvider(this);
        footerProvider = new FooterProvider(this);
        iconProvider = new IconProvider(this);
        labelProvider = new LabelProvider(this);
        alignProvider = new AlignProvider(this);
        iconAlignProvider = new IconAlignProvider(this);
        modelProvider = new ModelProvider(this);
        styleAttributeProvider = new StyleAttributeProviderImpl(this);
        placeholderProvider = new PlaceholderProvider(this);
        disabledComponentProvider = new DisabledComponentProvider(this);
        collapseSwitchProvider = new CollapseSwitchProvider(this);
        activeIndexManagerProvider = new ActiveIndexManagerProvider(this);
        deferredProvider = new DeferredProvider(this);
        htmlElementProvider = new HtmlElementProvider(this, Node.DIV);
        ajaxProvider = new AjaxProvider(this);
        keyBindingProvider = new KeyBindingProvider(this);
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
    public String getFamily() {
        return "MockPartialComponent";
    }

    @Override
    public UIComponent facet(String facetName) {
        return getFacet(facetName);
    }
}
