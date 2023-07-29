package de.cuioss.jsf.api.components.javascript;

import javax.faces.component.UIComponent;

import de.cuioss.jsf.api.components.util.ComponentWrapper;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Simple base class for creating JQuery-specific selectors: e.g for a given
 * component providing the id "a:b" it returns "jQuery('#a\\\\:b')" saying it
 * takes care on the proper masking of the clientIds.
 *
 * @author Oliver Wolff
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class ComponentWrapperJQuerySelector extends JQuerySelector {

    /** Runtime access on component specific attributes. */
    @NonNull
    private final ComponentWrapper<? extends UIComponent> componentWrapper;

    /**
     * if not null it will be appended to the derived ClientId. In addition there
     * will be an underscore appended: The result will be component.getClientId() +
     * "_" + idExtension
     */
    private final String idExtension;

    @Override
    protected String getIdString() {
        return componentWrapper.getSuffixedClientId(idExtension);
    }

    /**
     * Builder for creating instances of {@link ComponentWrapperJQuerySelector}
     *
     * @author Oliver Wolff
     */
    public static class ComponentWrapperJQuerySelectorBuilder {

        private ComponentWrapper<? extends UIComponent> componentWrapper;
        private String idExtension;

        /**
         * @param componentWrapper
         * @return an instance of {@link ComponentWrapperJQuerySelectorBuilder}
         */
        public ComponentWrapperJQuerySelectorBuilder withComponentWrapper(
                final ComponentWrapper<? extends UIComponent> componentWrapper) {
            this.componentWrapper = componentWrapper;
            return this;
        }

        /**
         * @param idExtension if not null it will be appended to the derived ClientId.
         *                    In addition there will be an underscore appended: The
         *                    result will be component.getClientId() + "_" + idExtension
         * @return an instance of {@link ComponentWrapperJQuerySelectorBuilder}
         */
        public ComponentWrapperJQuerySelectorBuilder withIdExtension(final String idExtension) {
            this.idExtension = idExtension;
            return this;
        }

        /**
         * @return the created {@link ComponentWrapperJQuerySelector}
         */
        public ComponentWrapperJQuerySelector build() {
            return new ComponentWrapperJQuerySelector(componentWrapper, idExtension);
        }
    }

    /**
     * @return an instance of {@link ComponentWrapperJQuerySelectorBuilder}
     */
    public static ComponentWrapperJQuerySelectorBuilder builder() {
        return new ComponentWrapperJQuerySelectorBuilder();
    }

}
