package de.cuioss.jsf.api.components.util;

import static java.util.Objects.requireNonNull;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlSelectOneMenu;

/**
 * Include strategies to access disable attribute of UIComponent and set this
 * true. UIInput doesn't provide the disable attribute and because not each
 * component store the attribute value in AttributeMap but use StateHolder there
 * is no common easy way to set component disabled.
 *
 * @author i000576
 */
public enum DisableUIComponentStrategy {

    /** Strategy solve disable HtmlInputText descendants */
    INPUT_TEXT(HtmlInputText.class) {

        @Override
        protected void disable(final UIComponent component) {
            ((HtmlInputText) component).setDisabled(true);
        }
    },

    /** Strategy solve disable HtmlSelectOneMenu descendants */
    SELECT_MENU(HtmlSelectOneMenu.class) {

        @Override
        protected void disable(final UIComponent component) {
            ((HtmlSelectOneMenu) component).setDisabled(true);
        }
    };

    /** indicate supported component */
    private final Class<? extends UIComponent> clazz;

    DisableUIComponentStrategy(final Class<? extends UIComponent> klass) {
        this.clazz = klass;
    }

    /**
     * @param component
     *            {@link UIComponent} to be disabled
     */
    protected abstract void disable(final UIComponent component);

    /**
     * Disable the component which is passed on.
     *
     * @param component
     *            {@link UIComponent} must not be null.
     * @throws NullPointerException
     *             id parameter is null
     * @throws IllegalArgumentException
     *             if no fitting strategy to disable the component exists
     */
    public static final void disableComponent(final UIComponent component) {
        requireNonNull(component, "UIComponent must not be null");
        for (final DisableUIComponentStrategy strategy : DisableUIComponentStrategy.values()) {
            if (strategy.clazz.isAssignableFrom(component.getClass())) {
                strategy.disable(component);
                return;
            }
        }
        throw new IllegalArgumentException(
                String.format("[%s] has no coresponding disable strategy",
                        component.getClass().getSimpleName()));
    }

}
