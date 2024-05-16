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
package de.cuioss.jsf.api.components.util;

import jakarta.faces.component.UIComponent;
import jakarta.faces.component.html.HtmlInputText;
import jakarta.faces.component.html.HtmlSelectOneMenu;

import static java.util.Objects.requireNonNull;

/**
 * Include strategies to access disable attribute of UIComponent and set this
 * true.
 * UIInput doesn't provide the attribute 'disables' and because not each
 * component stores the attribute value in AttributeMap but uses StateHolder, there
 * is no common easy way to set component disabled.
 *
 * @author Eugen Fischer
 */
public enum DisableUIComponentStrategy {

    /**
     * Strategy solve disable HtmlInputText descendants
     */
    INPUT_TEXT(HtmlInputText.class) {
        @Override
        protected void disable(final UIComponent component) {
            ((HtmlInputText) component).setDisabled(true);
        }
    },

    /**
     * Strategy solve disable HtmlSelectOneMenu descendants
     */
    SELECT_MENU(HtmlSelectOneMenu.class) {
        @Override
        protected void disable(final UIComponent component) {
            ((HtmlSelectOneMenu) component).setDisabled(true);
        }
    };

    /**
     * indicate supported component
     */
    private final Class<? extends UIComponent> clazz;

    DisableUIComponentStrategy(final Class<? extends UIComponent> klass) {
        clazz = klass;
    }

    /**
     * @param component {@link UIComponent} to be disabled
     */
    protected abstract void disable(final UIComponent component);

    /**
     * Disable the component which is passed on.
     *
     * @param component {@link UIComponent} must not be null.
     * @throws NullPointerException     id parameter is null
     * @throws IllegalArgumentException if no fitting strategy to disable the
     *                                  component exists
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
