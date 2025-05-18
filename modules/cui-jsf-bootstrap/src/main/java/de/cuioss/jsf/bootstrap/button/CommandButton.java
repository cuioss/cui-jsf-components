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

import de.cuioss.jsf.api.components.base.BaseCuiCommandButton;
import de.cuioss.jsf.api.components.css.AlignHolder;
import de.cuioss.jsf.api.components.partial.*;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.html.HtmlCommandButton;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.faces.event.ListenerFor;
import jakarta.faces.event.PreRenderComponentEvent;
import lombok.experimental.Delegate;

/**
 * Bootstrap styled command button for form submissions and server-side actions.
 * Extends {@link BaseCuiCommandButton} with icons, contextual styling, and internationalization.
 * 
 * <h2>Features</h2>
 * <ul>
 * <li>Internationalized titles and labels</li>
 * <li>Size options (lg, sm, xs)</li>
 * <li>State styling (primary, success, warning, danger)</li>
 * <li>Icon integration with alignment options</li>
 * <li>Keyboard shortcuts</li>
 * <li>Ajax integration</li>
 * </ul>
 *
 * <h2>Usage Examples</h2>
 * <pre>
 * &lt;cui:commandButton action="#{bean.save}" labelValue="Save" icon="cui-icon-floppy-disk"/&gt;
 * 
 * &lt;cui:commandButton action="#{bean.delete}" labelKey="button.delete" state="danger"&gt;
 *   &lt;f:ajax execute="@form" render="resultPanel"/&gt;
 * &lt;/cui:commandButton&gt;
 * 
 * &lt;cui:commandButton action="#{bean.next}" labelKey="button.next" 
 *                  icon="cui-icon-chevron-right" iconAlign="right"/&gt;
 * </pre>
 * 
 * <strong>Note:</strong> Use labelKey/labelValue instead of value attribute.
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see Button
 */
@FacesComponent(BootstrapFamily.COMMAND_BUTTON_COMPONENT)
@ListenerFor(systemEventClass = PreRenderComponentEvent.class)
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public class CommandButton extends BaseCuiCommandButton implements ComponentStyleClassProvider {

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


    /**
     * Default constructor that initializes all delegate providers and sets the
     * renderer type to {@link BootstrapFamily#COMMAND_BUTTON_RENDERER}.
     */
    public CommandButton() {
        super.setRendererType(BootstrapFamily.COMMAND_BUTTON_RENDERER);
        contextSizeProvider = new ContextSizeProvider(this);
        contextStateProvider = new ContextStateProvider(this);
        iconProvider = new IconProvider(this);
        labelProvider = new LabelProvider(this);
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
    }


    /**
     * Returns the component family, which is {@link BootstrapFamily#COMPONENT_FAMILY}.
     * 
     * @return {@link BootstrapFamily#COMPONENT_FAMILY}
     */
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
     * Factory method to instantiate a concrete instance of {@link CommandButton},
     * usually used if you programmatically add it as a child.
     *
     * @param facesContext The current FacesContext, must not be null
     * @return concrete instance of {@link CommandButton}
     */
    public static CommandButton create(final FacesContext facesContext) {
        return (CommandButton) facesContext.getApplication().createComponent(BootstrapFamily.COMMAND_BUTTON_COMPONENT);
    }
}
