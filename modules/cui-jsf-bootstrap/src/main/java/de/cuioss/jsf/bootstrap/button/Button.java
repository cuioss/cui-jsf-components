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
package de.cuioss.jsf.bootstrap.button;

import static de.cuioss.tools.string.MoreStrings.isEmpty;

import javax.faces.component.FacesComponent;
import javax.faces.component.StateHelper;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutcomeTargetButton;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ListenerFor;
import javax.faces.event.PreRenderComponentEvent;

import org.omnifaces.util.State;

import de.cuioss.jsf.api.components.css.AlignHolder;
import de.cuioss.jsf.api.components.html.AttributeValue;
import de.cuioss.jsf.api.components.partial.ComponentBridge;
import de.cuioss.jsf.api.components.partial.ComponentStyleClassProvider;
import de.cuioss.jsf.api.components.partial.ComponentStyleClassProviderImpl;
import de.cuioss.jsf.api.components.partial.ContextSizeProvider;
import de.cuioss.jsf.api.components.partial.ContextStateProvider;
import de.cuioss.jsf.api.components.partial.IconAlignProvider;
import de.cuioss.jsf.api.components.partial.IconProvider;
import de.cuioss.jsf.api.components.partial.LabelProvider;
import de.cuioss.jsf.api.components.partial.TitleProvider;
import de.cuioss.jsf.api.components.partial.TitleProviderImpl;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.jsf.bootstrap.button.support.ButtonSize;
import de.cuioss.jsf.bootstrap.button.support.ButtonState;
import lombok.experimental.Delegate;

/**
 * <p>
 * An extension to h:button that conforms to Bootstrap styling and incorporates
 * the display of icons. Caution: do not use the value attribute but the
 * corresponding labelKey / labelValue. The same goes for the title element: use
 * titleKey or titleValue.
 * </p>
 * <h2>Attributes</h2>
 * <ul>
 * <li>{@link TitleProvider}</li>
 * <li>{@link ContextSizeProvider}</li>
 * <li>{@link ContextStateProvider}</li>
 * <li>{@link IconProvider}</li>
 * <li>{@link IconAlignProvider}</li>
 * <li>{@link LabelProvider}</li>
 * <li>All attributes from {@link HtmlOutcomeTargetButton}</li>
 * <li>keyBinding: The key-binding for this button, aka keyboard shortcut. The
 * key will be bound as onClickHandler. Caution: The implementor must ensure
 * that there is only one button for the same type existent per page, otherwise
 * the behavior is non-deterministic.</li>
 * </ul>
 *
 * @author Oliver Wolff
 *
 */
@FacesComponent(BootstrapFamily.BUTTON_COMPONENT)
@ListenerFor(systemEventClass = PreRenderComponentEvent.class)
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public class Button extends HtmlOutcomeTargetButton implements ComponentBridge, TitleProvider {

    private static final String KEY_BINDING_KEY = "keyBinding";

    @Delegate
    private final TitleProvider titleProvider;

    @Delegate
    private final ContextSizeProvider contextSizeProvider;

    @Delegate
    private final ContextStateProvider contextStateProvider;

    @Delegate
    private final IconProvider iconProvider;

    @Delegate
    private final IconAlignProvider iconAlignProvider;

    @Delegate
    private final LabelProvider labelProvider;

    private final ComponentStyleClassProvider styleClassProvider;

    private final State state;

    /**
     * Constructor.
     */
    public Button() {
        super.setRendererType(BootstrapFamily.BUTTON_RENDERER);
        titleProvider = new TitleProviderImpl(this);
        contextSizeProvider = new ContextSizeProvider(this);
        contextStateProvider = new ContextStateProvider(this);
        iconProvider = new IconProvider(this);
        labelProvider = new LabelProvider(this);
        styleClassProvider = new ComponentStyleClassProviderImpl(this);
        iconAlignProvider = new IconAlignProvider(this);
        state = new State(getStateHelper());
    }

    @Override
    public String getStyleClass() {
        return CssBootstrap.BUTTON.getStyleClassBuilder()
                .append(ButtonState.getForContextState(contextStateProvider.getState()))
                .append(ButtonSize.getForContextSize(contextSizeProvider.resolveContextSize()))
                .append(styleClassProvider).getStyleClass();
    }

    @Override
    public void processEvent(final ComponentSystemEvent event) {
        if (event instanceof PreRenderComponentEvent && !isEmpty(getKeyBinding())) {
            getPassThroughAttributes().put(AttributeValue.CUI_CLICK_BINDING.getContent(), getKeyBinding());
        }
        super.processEvent(event);
    }

    @Override
    public void setStyleClass(final String styleClass) {
        styleClassProvider.setStyleClass(styleClass);
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
    public String getFamily() {
        return BootstrapFamily.COMPONENT_FAMILY;
    }

    @Override
    public void setValue(final Object value) {
        throw new IllegalArgumentException("element 'value' not permitted, use labelKey or labelValue instead");
    }

    @Override
    public Object getValue() {
        return labelProvider.resolveLabel();
    }

    @Override
    public String getTitle() {
        return titleProvider.resolveTitle();
    }

    /**
     * @return boolean indicating whether to display an icon on the right side of
     *         the button text
     */
    public boolean isDisplayIconRight() {
        return iconProvider.isIconSet() && AlignHolder.RIGHT.equals(iconAlignProvider.resolveIconAlign());
    }

    /**
     * @return boolean indicating whether to display an icon on the left side of the
     *         button text
     */
    public boolean isDisplayIconLeft() {
        return iconProvider.isIconSet() && !AlignHolder.RIGHT.equals(iconAlignProvider.resolveIconAlign());
    }

    /**
     * @param keyBinding
     */
    public void setKeyBinding(final String keyBinding) {
        state.put(KEY_BINDING_KEY, keyBinding);
    }

    /**
     * @return the keyBinding
     */
    public String getKeyBinding() {
        return state.get(KEY_BINDING_KEY);
    }

    /**
     * Factory method to instantiate a concrete instance of {@link Button}, usually
     * used if you programmatically add it as a child.
     *
     * @param facesContext must not be null
     * @return concrete instance of {@link Button}
     */
    public static final Button create(final FacesContext facesContext) {
        return (Button) facesContext.getApplication().createComponent(BootstrapFamily.BUTTON_COMPONENT);
    }
}
