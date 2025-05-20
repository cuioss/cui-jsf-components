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

import de.cuioss.jsf.api.components.css.AlignHolder;
import de.cuioss.jsf.api.components.myfaces.MyFacesDelegateStyleClassAdapter;
import de.cuioss.jsf.api.components.myfaces.MyFacesDelegateTitleAdapter;
import de.cuioss.jsf.api.components.partial.*;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.StateHelper;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.html.HtmlOutcomeTargetButton;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.faces.event.ListenerFor;
import jakarta.faces.event.PreRenderComponentEvent;
import lombok.experimental.Delegate;

/**
 * Bootstrap styled button component extending {@link HtmlOutcomeTargetButton} with additional features
 * like icons, contextual styling, and internationalization support.
 * 
 * <h2>Features</h2>
 * <ul>
 * <li>Internationalized titles and labels</li>
 * <li>Size options (lg, sm, xs)</li>
 * <li>State styling (primary, success, warning, danger)</li>
 * <li>Icon integration with alignment options</li>
 * <li>Keyboard shortcuts</li>
 * </ul>
 *
 * <h2>Usage Examples</h2>
 * <pre>
 * &lt;boot:button outcome="details" labelValue="View Details" icon="cui-icon-search"/&gt;
 * &lt;boot:button outcome="create" labelKey="button.create" state="primary" icon="cui-icon-plus"/&gt;
 * &lt;boot:button outcome="next" labelKey="button.next" icon="cui-icon-chevron-right" iconAlign="right"/&gt;
 * </pre>
 *
 * <strong>Note:</strong> Use labelKey/labelValue instead of value, and titleKey/titleValue instead of title.
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see CommandButton
 */
@FacesComponent(BootstrapFamily.BUTTON_COMPONENT)
@ListenerFor(systemEventClass = PreRenderComponentEvent.class)
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public class Button extends HtmlOutcomeTargetButton implements ComponentBridge, TitleProvider, MyFacesDelegateStyleClassAdapter, MyFacesDelegateTitleAdapter {


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

    @Delegate
    private final KeyBindingProvider keyBindingProvider;

    @Delegate
    private final ComponentStyleClassProvider styleClassProvider;


    /**
     * Default constructor that initializes all delegate providers and sets the
     * renderer type to {@link BootstrapFamily#BUTTON_RENDERER}.
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
        keyBindingProvider = new KeyBindingProvider(this);
    }

    /**
     * Processes component events, specifically handling the {@link PreRenderComponentEvent}
     * to write keyboard bindings to pass-through attributes before rendering.
     * 
     * @param event The component system event to be processed
     */
    @Override
    public void processEvent(final ComponentSystemEvent event) {
        if (event instanceof PreRenderComponentEvent) {
            keyBindingProvider.writeBindingToPassThroughAttributes(this);
        }
        super.processEvent(event);
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

    /**
     * Overridden to prevent direct setting of the value attribute since it
     * would interfere with the label provider mechanism.
     * 
     * @param value The value to set (not supported)
     * @throws IllegalArgumentException if called, use labelKey or labelValue instead
     */
    @Override
    public void setValue(final Object value) {
        throw new IllegalArgumentException("element 'value' not permitted, use labelKey or labelValue instead");
    }

    /**
     * Overridden to return the resolved label from the label provider instead
     * of the component's value attribute.
     * 
     * @return The resolved label from {@link LabelProvider#resolveLabel()}
     */
    @Override
    public Object getValue() {
        return labelProvider.resolveLabel();
    }

    /**
     * Overridden to return the resolved title from the title provider instead
     * of the component's title attribute.
     * 
     * @return The resolved title from {@link TitleProvider#resolveTitle()}
     */
    @Override
    public String getTitle() {
        return titleProvider.resolveTitle();
    }

    /**
     * Determines if an icon should be displayed on the right side of the button text.
     * This is true when an icon is set and the icon alignment is explicitly set to RIGHT.
     *
     * @return {@code true} if an icon should be displayed on the right side of the button text,
     *         {@code false} otherwise
     */
    public boolean isDisplayIconRight() {
        return iconProvider.isIconSet() && AlignHolder.RIGHT.equals(iconAlignProvider.resolveIconAlign());
    }

    /**
     * Determines if an icon should be displayed on the left side of the button text.
     * This is true when an icon is set and the icon alignment is not set to RIGHT.
     *
     * @return {@code true} if an icon should be displayed on the left side of the button text,
     *         {@code false} otherwise
     */
    public boolean isDisplayIconLeft() {
        return iconProvider.isIconSet() && !AlignHolder.RIGHT.equals(iconAlignProvider.resolveIconAlign());
    }

    /**
     * Factory method to instantiate a concrete instance of {@link Button}, usually
     * used if you programmatically add it as a child.
     *
     * @param facesContext The current FacesContext, must not be null
     * @return concrete instance of {@link Button}
     */
    public static Button create(final FacesContext facesContext) {
        return (Button) facesContext.getApplication().createComponent(BootstrapFamily.BUTTON_COMPONENT);
    }

    /**
     * {@inheritDoc}
     * Writes the computed style class to the parent component's styleClass attribute
     */
    @Override
    public void writeStyleClassToParent() {
        super.setStyleClass(styleClassProvider.getStyleClass());
    }

    /**
     * {@inheritDoc}
     * Writes the resolved title to the parent component's title attribute
     */
    @Override
    public void writeTitleToParent() {
        super.setTitle(titleProvider.getTitle());
    }
}
