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
package de.cuioss.jsf.bootstrap.link;

import de.cuioss.jsf.api.components.css.AlignHolder;
import de.cuioss.jsf.api.components.partial.*;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.jsf.bootstrap.button.support.ButtonSize;
import de.cuioss.jsf.bootstrap.button.support.ButtonState;
import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.StateHelper;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.html.HtmlOutputLink;
import jakarta.faces.context.FacesContext;
import lombok.experimental.Delegate;

/**
 * Bootstrap styled link component that renders as a button.
 * Extends {@link HtmlOutputLink} with Bootstrap styling and enhanced features.
 * 
 * <h3>Features</h3>
 * <ul>
 *   <li>Bootstrap button styling with customizable size and state</li>
 *   <li>Icon support with left/right positioning</li>
 *   <li>Localized labels with optional escaping</li>
 *   <li>Title/tooltip support</li>
 * </ul>
 * 
 * <h3>Example</h3>
 * <pre>
 * &lt;boot:outputLinkButton value="https://example.com" 
 *                      labelValue="Visit Example"
 *                      icon="cui-icon-info"
 *                      state="primary"
 *                      size="lg" /&gt;
 * </pre>
 * 
 * <h3>Generated Markup</h3>
 * <pre>
 * &lt;a href="https://example.com" class="btn btn-primary btn-lg"&gt;
 *   &lt;span class="cui-icon cui-icon-info"&gt;&lt;/span&gt;
 *   &lt;span class="button-text"&gt;Visit Example&lt;/span&gt;
 * &lt;/a&gt;
 * </pre>
 *
 * @author Oliver Wolff
 */
@FacesComponent(BootstrapFamily.OUTPUT_LINK_BUTTON_COMPONENT)
public class OutputLinkButton extends HtmlOutputLink implements ComponentBridge, TitleProvider {

    @Delegate
    private final ContextSizeProvider contextSizeProvider;

    @Delegate
    private final ContextStateProvider contextStateProvider;

    @Delegate
    private final TitleProvider titleProvider;

    @Delegate
    private final IconProvider iconProvider;

    @Delegate
    private final IconAlignProvider iconAlignProvider;

    @Delegate
    private final LabelProvider labelProvider;

    private final ComponentStyleClassProvider styleClassProvider;

    /**
     * Default constructor that initializes the component providers and sets the renderer type.
     * The component is configured with default providers for size, state, title, icon, label,
     * style classes, and icon alignment.
     */
    public OutputLinkButton() {
        super.setRendererType(BootstrapFamily.OUTPUT_LINK_BUTTON_RENDERER);
        titleProvider = new TitleProviderImpl(this);
        contextSizeProvider = new ContextSizeProvider(this);
        contextStateProvider = new ContextStateProvider(this);
        iconProvider = new IconProvider(this);
        labelProvider = new LabelProvider(this);
        styleClassProvider = new ComponentStyleClassProviderImpl(this);
        iconAlignProvider = new IconAlignProvider(this);
    }

    /**
     * {@inheritDoc}
     * 
     * <p>Generates the complete style class string for the button by combining:</p>
     * <ul>
     *   <li>Bootstrap button base class</li>
     *   <li>Button state class (primary, success, etc.)</li>
     *   <li>Button size class (lg, sm, etc.)</li>
     *   <li>Any custom style classes</li>
     * </ul>
     * 
     * @return the complete CSS class string for the component
     */
    @Override
    public String getStyleClass() {
        return CssBootstrap.BUTTON.getStyleClassBuilder()
                .append(ButtonState.getForContextState(contextStateProvider.getState()))
                .append(ButtonSize.getForContextSize(contextSizeProvider.resolveContextSize()))
                .append(styleClassProvider).getStyleClass();
    }

    /**
     * {@inheritDoc}
     * Sets the custom style class for the component.
     * 
     * @param styleClass the CSS class string to set
     */
    @Override
    public void setStyleClass(final String styleClass) {
        styleClassProvider.setStyleClass(styleClass);
    }

    /**
     * {@inheritDoc}
     * 
     * @return the component's state helper
     */
    @Override
    public StateHelper stateHelper() {
        return getStateHelper();
    }

    /**
     * {@inheritDoc}
     * 
     * @return the current faces context
     */
    @Override
    public FacesContext facesContext() {
        return getFacesContext();
    }

    /**
     * {@inheritDoc}
     * 
     * @param facetName the name of the facet to retrieve
     * @return the UIComponent for the specified facet
     */
    @Override
    public UIComponent facet(final String facetName) {
        return getFacet(facetName);
    }

    /**
     * {@inheritDoc}
     * Resolves the title attribute value from the titleProvider.
     * 
     * @return the resolved title text for the button
     */
    @Override
    public String getTitle() {
        return titleProvider.resolveTitle();
    }

    /**
     * {@inheritDoc}
     * 
     * @return the component family, always {@link BootstrapFamily#COMPONENT_FAMILY}
     */
    @Override
    public String getFamily() {
        return BootstrapFamily.COMPONENT_FAMILY;
    }

    /**
     * Determines if the icon should be displayed on the right side of the button text.
     * This is true when an icon is set and the icon alignment is explicitly set to RIGHT.
     *
     * @return boolean indicating whether to display an icon on the right side of
     *         the button text
     */
    public boolean isDisplayIconRight() {
        return iconProvider.isIconSet() && AlignHolder.RIGHT.equals(iconAlignProvider.resolveIconAlign());
    }

    /**
     * Determines if the icon should be displayed on the left side of the button text.
     * This is true when an icon is set and the icon alignment is not explicitly set to RIGHT
     * (the default positioning is LEFT).
     *
     * @return boolean indicating whether to display an icon on the left side of the
     *         button text
     */
    public boolean isDisplayIconLeft() {
        return iconProvider.isIconSet() && !AlignHolder.RIGHT.equals(iconAlignProvider.resolveIconAlign());
    }
}
