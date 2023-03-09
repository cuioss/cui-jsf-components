package com.icw.ehf.cui.components.js.support;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Oliver Wolff
 *
 */
@ToString
@EqualsAndHashCode
public class PropertyProvider implements IPropertyProvider, Serializable {

    /** serial Version UID */
    private static final long serialVersionUID = 9198858377353412920L;

    private final List<JavaScriptSupport> properties = new ArrayList<>();

    @Override
    public List<JavaScriptSupport> getProperties() {
        return immutableList(properties);
    }

    /**
     * @param provider
     * @return {@link PropertyProvider}
     */
    public PropertyProvider addProperties(final IPropertyProvider provider) {
        if (null != provider) {
            for (final JavaScriptSupport item : provider.getProperties()) {
                addProperty(item);
            }
        }
        return this;
    }

    /**
     * @param name
     * @param value
     * @return {@link PropertyProvider}
     */
    public PropertyProvider addProperty(final String name, final JsValue value) {
        return addProperty(new JsProperty<>(name, value));
    }

    /**
     * @param property
     * @return {@link PropertyProvider}
     */
    public PropertyProvider addProperty(final JavaScriptSupport property) {
        if (null != property && needToBeAdded(property)) {
            properties.add(property);
        }
        return this;
    }

    private boolean needToBeAdded(final JavaScriptSupport property) {
        if (properties.contains(property)) {
            return false;
        }
        var result = true;
        if (property instanceof JsProperty) {
            final JsProperty<?> jsProp = (JsProperty<?>) property;
            for (final JavaScriptSupport item : properties) {
                if (item instanceof JsProperty) {
                    final JsProperty<?> prop = (JsProperty<?>) item;
                    if (prop.getPropertyName().equals(jsProp.getPropertyName())) {
                        properties.remove(item);
                        break;
                    }
                }
            }
        }
        return result;
    }

}
