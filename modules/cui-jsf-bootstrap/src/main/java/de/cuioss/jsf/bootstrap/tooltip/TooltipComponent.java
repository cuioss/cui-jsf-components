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
package de.cuioss.jsf.bootstrap.tooltip;

import static de.cuioss.jsf.bootstrap.BootstrapFamily.COMPONENT_FAMILY;
import static java.util.Objects.requireNonNull;

import de.cuioss.jsf.api.components.decorator.AbstractParentDecorator;
import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.AttributeValue;
import de.cuioss.jsf.api.components.partial.ContentProvider;
import de.cuioss.jsf.api.components.util.ComponentModifier;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import jakarta.faces.application.ResourceDependency;
import jakarta.faces.component.FacesComponent;
import lombok.experimental.Delegate;

/**
 * Renders a bootstrap-based tooltip for an existing parent component.
 * Acts as a {@link de.cuioss.jsf.api.components.decorator} that modifies
 * the parent element's attributes rather than producing its own output.
 * The actual tooltip rendering is handled by JavaScript.
 * 
 * <h2>Attributes</h2>
 * <ul>
 * <li>{@link ContentProvider} - The tooltip content</li>
 * <li><b>placement</b>: Tooltip positioning ('left', 'top', 'bottom', 'right'), defaults to {@value #PLACEMENT_DEFAULT}</li>
 * <li><b>trigger</b>: Event to trigger tooltip ('click', 'hover', 'focus', 'manual'), defaults to {@value #TRIGGER_DEFAULT}</li>
 * <li><b>delay</b>: Show/hide delay in ms, defaults to 500</li>
 * </ul>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
@ResourceDependency(library = "javascript.enabler", name = "enabler.tooltip.js", target = "head")
@FacesComponent(BootstrapFamily.TOOLTIP_COMPONENT)
public class TooltipComponent extends AbstractParentDecorator {

    /** "placement" */
    public static final String PLACEMENT_KEY = "placement";

    /** "auto right" */
    public static final String PLACEMENT_DEFAULT = "auto right";

    /** "data-placement" */
    public static final String DATA_PLACEMENT = "data-placement";

    /** tooltip delay component attribute name */
    public static final String DELAY_KEY = "delay";

    /** default tooltip delay in (ms) */
    public static final Integer DEFAULT_DELAY = 500;

    /** Delay showing and hiding the tooltip (ms) */
    public static final String DATA_DELAY = "data-delay";

    /** "data-trigger" */
    public static final String DATA_TRIGGER = "data-trigger";

    /** "trigger" */
    public static final String TRIGGER_KEY = "trigger";

    /** "hover focus" */
    public static final String TRIGGER_DEFAULT = "hover focus";

    /** "tooltip" */
    public static final String TOOLTIP = "tooltip";

    @Delegate
    private final ContentProvider contentProvider;

    /**
     * Constructor
     */
    public TooltipComponent() {
        contentProvider = new ContentProvider(this);
    }

    @Override
    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    @Override
    public void decorate(final ComponentModifier parent) {
        parent.addPassThrough(DATA_PLACEMENT, getPlacement())
                .addPassThrough(AttributeValue.DATA_TOGGLE.getContent(), TOOLTIP)
                .addPassThrough(DATA_TRIGGER, getTrigger()).addPassThrough(DATA_DELAY, getDelay());
        final var content = contentProvider.resolveContent();
        if (null != content) {
            if (parent.isSupportsTitle()) {
                parent.setTitle(content);
            } else {
                parent.addPassThrough(AttributeName.TITLE.getContent(), content);
            }
        }
    }

    /**
     * @param placement to be set
     */
    public void setPlacement(final String placement) {
        getStateHelper().put(PLACEMENT_KEY, placement);
    }

    /**
     * @return the placement-string, defaults to {@value #PLACEMENT_DEFAULT}
     */
    public String getPlacement() {
        return (String) getStateHelper().eval(PLACEMENT_KEY, PLACEMENT_DEFAULT);
    }

    /**
     * @param trigger to be set
     */
    public void setTrigger(final String trigger) {
        getStateHelper().put(TRIGGER_KEY, trigger);
    }

    /**
     * @return the trigger-string, identified, defaults to {@value #TRIGGER_DEFAULT}
     */
    public String getTrigger() {
        return (String) getStateHelper().eval(TRIGGER_KEY, TRIGGER_DEFAULT);
    }

    /**
     * @return delay showing and hiding in (ms)
     */
    public Integer getDelay() {
        return (Integer) getStateHelper().eval(DELAY_KEY, DEFAULT_DELAY);
    }

    /**
     * Set tool tip display delay
     *
     * @param delayValue {@linkplain Integer} in (ms). Must not be {@code null}
     */
    public void setDelay(final Integer delayValue) {
        getStateHelper().put(requireNonNull(DELAY_KEY, "Value must not be null"), delayValue);
    }
}
