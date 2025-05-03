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
 * <p>
 * An extension to h:commandButton that conforms to Bootstrap styling and
 * incorporates the display of icons. Caution: do not use the value attribute
 * but the corresponding labelKey / labelValue. The same goes for the title
 * element: use titleKey or titleValue.
 * </p>
 * <h2>Attributes</h2>
 * <ul>
 * <li>{@link TitleProvider}</li>
 * <li>{@link ContextSizeProvider}</li>
 * <li>{@link ContextStateProvider}</li>
 * <li>{@link IconProvider}</li>
 * <li>{@link IconAlignProvider}</li>
 * <li>{@link LabelProvider}</li>
 * <li>{@link KeyBindingProvider}</li>
 * <li>All attributes from {@link HtmlCommandButton}</li>
 * </ul>
 *
 * @author Oliver Wolff
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
     * Constructor.
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


    @Override
    public void processEvent(final ComponentSystemEvent event) {
        if (event instanceof PreRenderComponentEvent) {
            keyBindingProvider.writeBindingToPassThroughAttributes(this);
        }
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

    /**
     * @return boolean indicating whether to display an icon on the right side of
     * the button text
     */
    public boolean isDisplayIconRight() {
        return iconProvider.isIconSet() && AlignHolder.RIGHT.equals(iconAlignProvider.resolveIconAlign());
    }

    /**
     * @return boolean indicating whether to display an icon on the left side of the
     * button text
     */
    public boolean isDisplayIconLeft() {
        return iconProvider.isIconSet() && !AlignHolder.RIGHT.equals(iconAlignProvider.resolveIconAlign());
    }

    /**
     * Factory method to instantiate a concrete instance of {@link CommandButton},
     * usually used if you programmatically add it as a child.
     *
     * @param facesContext must not be null
     * @return concrete instance of {@link CommandButton}
     */
    public static CommandButton create(final FacesContext facesContext) {
        return (CommandButton) facesContext.getApplication().createComponent(BootstrapFamily.COMMAND_BUTTON_COMPONENT);
    }
}
