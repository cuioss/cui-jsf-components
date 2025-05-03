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

import de.cuioss.tools.string.Joiner;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Eugen Fischer
 */
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public abstract class JsObject implements JavaScriptSupport {

    @Serial
    private static final long serialVersionUID = 9148909574306233473L;

    private final String objectName;

    private final transient PropertyProvider propProvider = new PropertyProvider();

    protected JsObject addProperties(final IPropertyProvider provider) {
        propProvider.addProperties(provider);
        return this;
    }

    protected JsObject addProperty(final String name, final JsValue value) {
        return addProperty(new JsProperty<>(name, value));
    }

    protected JsObject addProperty(final JavaScriptSupport property) {
        propProvider.addProperty(property);
        return this;
    }

    protected String transformProperties() {
        final var properties = propProvider.getProperties();

        if (properties.isEmpty()) {
            return "";
        }

        final List<String> stringRepresentation = new ArrayList<>(properties.size());
        for (final JavaScriptSupport property : properties) {
            stringRepresentation.add(property.asJavaScriptObjectNotation());
        }
        return Joiner.on(",").skipNulls().join(stringRepresentation);
    }

    /**
     * If no properties denied return {@code null}.<br>
     * If one property is defined, create :
     *
     * <pre>
     *  objectName : {
     *      propertyName : value
     *  }
     * </pre>
     * <p>
     * If more properties are defined, create :
     *
     * <pre>
     *  objectName : {
     *      propertyName1 : value,
     *      ...
     *      propertyNameN : value
     *  }
     * </pre>
     */
    protected String createAsJSON() {

        final var stringRepresentation = transformProperties();
        if (stringRepresentation.isEmpty()) {
            return null;
        }

        return objectName + ": {" + stringRepresentation +
                "}";
    }

    protected String createAsJSONObjectWithoutName() {

        final var stringRepresentation = transformProperties();
        if (stringRepresentation.isEmpty()) {
            return null;
        }

        return "{" + stringRepresentation + "}";
    }
}
