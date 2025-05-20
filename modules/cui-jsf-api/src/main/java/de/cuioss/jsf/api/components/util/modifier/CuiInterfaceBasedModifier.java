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
package de.cuioss.jsf.api.components.util.modifier;

import de.cuioss.jsf.api.components.css.StyleClassProvider;
import de.cuioss.jsf.api.components.partial.ComponentStyleClassProvider;
import de.cuioss.jsf.api.components.partial.StyleAttributeProvider;
import de.cuioss.jsf.api.components.partial.TitleProvider;
import de.cuioss.jsf.api.components.util.ComponentModifier;
import jakarta.faces.component.UIComponent;

import java.util.Optional;

/**
 * Variant that can be used for Cui-components that implements at least one of
 * {@link TitleProvider}, {@link ComponentStyleClassProvider}
 * {@link StyleAttributeProvider}
 *
 * @author Oliver Wolff
 */
public class CuiInterfaceBasedModifier extends ReflectionBasedModifier {

    private final TitleProvider titleProvider;

    private final ComponentStyleClassProvider styleClassProvider;

    private final StyleAttributeProvider styleAttributeProvider;

    /**
     * @param component must not be null
     */
    public CuiInterfaceBasedModifier(final UIComponent component) {
        super(component);
        if (component instanceof TitleProvider provider) {
            titleProvider = provider;
        } else {
            titleProvider = null;
        }
        if (component instanceof ComponentStyleClassProvider provider) {
            styleClassProvider = provider;
        } else {
            styleClassProvider = null;
        }
        if (component instanceof StyleAttributeProvider provider) {
            styleAttributeProvider = provider;
        } else {
            styleAttributeProvider = null;
        }
    }

    @Override
    public boolean isSupportsStyle() {
        if (null == styleAttributeProvider) {
            return super.isSupportsStyle();
        }
        return true;
    }

    @Override
    public String getStyle() {
        if (null == styleAttributeProvider) {
            return super.getStyle();
        }
        return styleAttributeProvider.getStyle();
    }

    @Override
    public void setStyle(final String style) {
        if (null == styleAttributeProvider) {
            super.setStyle(style);
            return;
        }
        styleAttributeProvider.setStyle(style);
    }

    @Override
    public boolean isSupportsStyleClass() {
        if (null == styleClassProvider) {
            return super.isSupportsStyleClass();
        }
        return true;
    }

    @Override
    public void setStyleClass(final String styleClass) {
        if (null == styleClassProvider) {
            super.setStyleClass(styleClass);
            return;
        }
        styleClassProvider.setStyleClass(styleClass);
    }

    @Override
    public String getStyleClass() {
        if (null == styleClassProvider) {
            return super.getStyleClass();
        }
        return styleClassProvider.getStyleClass();
    }

    @Override
    public boolean isSupportsTitle() {
        if (null == titleProvider) {
            return super.isSupportsTitle();
        }
        return true;
    }

    @Override
    public String getTitle() {
        if (null == titleProvider) {
            return super.getTitle();
        }
        return titleProvider.resolveTitle();
    }

    @Override
    public void setTitle(final String title) {
        if (null == titleProvider) {
            super.setTitle(title);
            return;
        }
        titleProvider.setTitleValue(title);
    }

    /**
     * @param component to be checked, must not be null
     * @return {@link Optional} {@link ComponentModifier} valid if the class in hand
     *         implements at least one of {@link TitleProvider},
     *         {@link StyleClassProvider} {@link StyleAttributeProvider},
     *         {@link Optional#empty()} otherwise
     */
    public static Optional<ComponentModifier> wrap(final UIComponent component) {
        if (component instanceof TitleProvider || component instanceof ComponentStyleClassProvider
                || component instanceof StyleAttributeProvider) {
            return Optional.of(new CuiInterfaceBasedModifier(component));
        }
        return Optional.empty();
    }
}
