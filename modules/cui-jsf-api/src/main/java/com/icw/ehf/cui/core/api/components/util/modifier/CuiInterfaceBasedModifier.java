package com.icw.ehf.cui.core.api.components.util.modifier;

import java.util.Optional;

import javax.faces.component.UIComponent;

import com.icw.ehf.cui.core.api.components.css.StyleClassProvider;
import com.icw.ehf.cui.core.api.components.partial.ComponentStyleClassProvider;
import com.icw.ehf.cui.core.api.components.partial.StyleAttributeProvider;
import com.icw.ehf.cui.core.api.components.partial.TitleProvider;
import com.icw.ehf.cui.core.api.components.util.ComponentModifier;

/**
 * Variant that can be used for Cui-components that implements at least one of
 * {@link TitleProvider}, {@link ComponentStyleClassProvider} {@link StyleAttributeProvider}
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
        if (component instanceof TitleProvider) {
            titleProvider = (TitleProvider) component;
        } else {
            titleProvider = null;
        }
        if (component instanceof ComponentStyleClassProvider) {
            styleClassProvider = (ComponentStyleClassProvider) component;
        } else {
            styleClassProvider = null;
        }
        if (component instanceof StyleAttributeProvider) {
            styleAttributeProvider = (StyleAttributeProvider) component;
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
        }
        return super.getStyleClass();
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
     * @return {@link Optional} {@link ComponentModifier} valid if the class in hand implements at
     *         least one of {@link TitleProvider}, {@link StyleClassProvider}
     *         {@link StyleAttributeProvider}, {@link Optional#empty()} otherwise
     */
    public static Optional<ComponentModifier> wrap(final UIComponent component) {
        if (component instanceof TitleProvider
                || component instanceof ComponentStyleClassProvider
                || component instanceof StyleAttributeProvider) {
            return Optional.of(new CuiInterfaceBasedModifier(component));
        }
        return Optional.empty();
    }
}
