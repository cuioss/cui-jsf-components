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
package de.cuioss.jsf.api.components.util;

import static java.util.Objects.requireNonNull;

import jakarta.faces.component.UIComponent;
import jakarta.faces.component.html.HtmlInputText;
import jakarta.faces.component.html.HtmlSelectOneMenu;

/**
 * <p>This enum provides a strategy-based approach to disable different types of JSF UI components.
 * It addresses the challenge of working with the disabled state across different component
 * implementations.</p>
 * 
 * <p>The challenge stems from the fact that the JSF component hierarchy doesn't define a universal
 * "disabled" property at the {@link UIComponent} level. Different component types implement the
 * disabled state in their own ways:</p>
 * <ul>
 *   <li>Some components expose a specific setter like {@link HtmlInputText#setDisabled(boolean)}</li>
 *   <li>Others might store the state in the component's attribute map</li>
 *   <li>Some might use the JSF state management system behind the scenes</li>
 * </ul>
 * 
 * <p>This enum uses the Strategy pattern to encapsulate these differences, providing a unified
 * way to disable any supported component type.</p>
 * 
 * <p>Usage example:</p>
 * <pre>
 * // Disable a component without needing to know its specific type
 * HtmlInputText inputText = new HtmlInputText();
 * DisableUIComponentStrategy.disableComponent(inputText);
 * 
 * // Will work for different component types
 * HtmlSelectOneMenu selectMenu = new HtmlSelectOneMenu();
 * DisableUIComponentStrategy.disableComponent(selectMenu);
 * </pre>
 *
 * @author Eugen Fischer
 */
public enum DisableUIComponentStrategy {

    /**
     * <p>Strategy implementation for disabling {@link HtmlInputText} components and
     * any components that extend this class.</p>
     * 
     * <p>This strategy handles all text input components like standard text fields,
     * password fields, and other components that inherit from {@link HtmlInputText}.</p>
     */
    INPUT_TEXT(HtmlInputText.class) {
        @Override
        protected void disable(final UIComponent component) {
            ((HtmlInputText) component).setDisabled(true);
        }
    },

    /**
     * <p>Strategy implementation for disabling {@link HtmlSelectOneMenu} components and
     * any components that extend this class.</p>
     * 
     * <p>This strategy handles dropdown selection components and other components
     * that inherit from {@link HtmlSelectOneMenu}.</p>
     */
    SELECT_MENU(HtmlSelectOneMenu.class) {
        @Override
        protected void disable(final UIComponent component) {
            ((HtmlSelectOneMenu) component).setDisabled(true);
        }
    };

    /**
     * <p>The component type this strategy supports.</p>
     * <p>Used to determine if a given component can be handled by this strategy.</p>
     */
    private final Class<? extends UIComponent> clazz;

    /**
     * <p>Constructs a new strategy for a specific component type.</p>
     * 
     * @param klass The component class this strategy can handle
     */
    DisableUIComponentStrategy(final Class<? extends UIComponent> klass) {
        clazz = klass;
    }

    /**
     * <p>Implements the specific logic to disable a component of the type
     * supported by this strategy.</p>
     * 
     * <p>This is the template method that each enum constant must implement
     * to provide component-specific disabling logic.</p>
     *
     * @param component The component to disable, guaranteed to be compatible with this strategy
     */
    protected abstract void disable(final UIComponent component);

    /**
     * <p>Disables the given component by finding and applying the appropriate strategy
     * based on the component's type.</p>
     * 
     * <p>This method attempts to find a strategy that can handle the specific component type
     * by checking if the component is assignable from any of the supported component classes.
     * If found, it applies the corresponding disabling logic.</p>
     *
     * @param component The {@link UIComponent} to disable, must not be null
     * @throws NullPointerException If the component parameter is null
     * @throws IllegalArgumentException If no strategy exists for disabling the given component type
     */
    public static void disableComponent(final UIComponent component) {
        requireNonNull(component, "UIComponent must not be null");
        for (final DisableUIComponentStrategy strategy : DisableUIComponentStrategy.values()) {
            if (strategy.clazz.isAssignableFrom(component.getClass())) {
                strategy.disable(component);
                return;
            }
        }
        throw new IllegalArgumentException(
                "[%s] has no corresponding disable strategy".formatted(component.getClass().getSimpleName()));
    }

}
