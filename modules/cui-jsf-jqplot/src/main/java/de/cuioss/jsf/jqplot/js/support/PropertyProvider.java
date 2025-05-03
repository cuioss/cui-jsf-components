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
package de.cuioss.jsf.jqplot.js.support;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Oliver Wolff
 *
 */
@ToString
@EqualsAndHashCode
public class PropertyProvider implements IPropertyProvider, Serializable {

    /** serial Version UID */
    @Serial
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
        if (property instanceof JsProperty jsProp) {
            for (final JavaScriptSupport item : properties) {
                if ((item instanceof JsProperty prop) && prop.getPropertyName().equals(jsProp.getPropertyName())) {
                    properties.remove(item);
                    break;
                }
            }
        }
        return result;
    }

}
