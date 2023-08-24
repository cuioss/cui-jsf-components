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
package de.cuioss.jsf.dev.metadata.composite.attributes;

import java.beans.FeatureDescriptor;
import java.util.ArrayList;
import java.util.List;

import de.cuioss.jsf.dev.metadata.composite.util.LabelValueDisplay;

/**
 * Implementation for an attribute.
 *
 * @author e0571
 */
public class AttributePropertyWrapper extends AbstractPropertyWrapper {

    private static final long serialVersionUID = -5712689755612400568L;

    /** Wraps the read type to el wrapper. */
    private static final String TYPE_WRAPPER = "javax.el.ValueExpression (must evaluate to %s)";

    /**
     * Constructor
     *
     * @param featureDescriptor to be wrapped
     */
    public AttributePropertyWrapper(final FeatureDescriptor featureDescriptor) {
        super(featureDescriptor, createDisplayValues(featureDescriptor));
    }

    /**
     * Creates the data to be displayed.
     *
     * @param descriptor
     * @return the list of display data
     */
    private static List<LabelValueDisplay> createDisplayValues(final FeatureDescriptor descriptor) {
        final List<LabelValueDisplay> result = new ArrayList<>();
        result.add(new LabelValueDisplay(LABEL_NAME, descriptor.getName()));
        result.add(new LabelValueDisplay(LABEL_REQUIRED, getRequired(descriptor)));
        result.add(new LabelValueDisplay(LABEL_TYPE, getType(descriptor)));
        result.add(new LabelValueDisplay(LABEL_SHORT_DESCRIPTION, descriptor.getShortDescription()));
        return result;
    }

    /**
     * Extracts the type attribute
     *
     * @param featureDescriptor
     * @return the extracted type attribute
     */
    private static String getType(final FeatureDescriptor featureDescriptor) {
        var type = QA_NO_TYPE_DEFINED;
        final Class<?> clazz = (Class<?>) featureDescriptor.getValue(ATTRIBUTE_TYPE);
        if (null != clazz) {
            type = TYPE_WRAPPER.formatted(clazz.getName());
        }
        return type;
    }

    @Override
    public PropertyType getPropertyType() {
        return PropertyType.ATTRIBUTE;
    }

}
