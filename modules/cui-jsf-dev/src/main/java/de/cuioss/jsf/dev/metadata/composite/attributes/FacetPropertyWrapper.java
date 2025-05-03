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

import de.cuioss.jsf.dev.metadata.composite.util.LabelValueDisplay;

import java.beans.FeatureDescriptor;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation for a facet.
 *
 * @author e0571
 */
public class FacetPropertyWrapper extends AbstractPropertyWrapper {

    /** serialVersionUID. */
    @Serial
    private static final long serialVersionUID = 674844732715688425L;

    /**
     * Constructor
     *
     * @param name              of the facet
     * @param featureDescriptor to be wrapped
     */
    public FacetPropertyWrapper(final String name, final FeatureDescriptor featureDescriptor) {
        super(featureDescriptor, createDisplayValues(featureDescriptor));
    }

    /**
     * Creates the data to be displayed.
     *
     * @param descriptor
     * @return the list of display data
     */
    private static List<LabelValueDisplay> createDisplayValues(final FeatureDescriptor descriptor) {
        List<LabelValueDisplay> result = new ArrayList<>();
        result.add(new LabelValueDisplay(LABEL_NAME, descriptor.getName()));
        // Only add display name if it is not the same like name
        if (!descriptor.getName().equals(descriptor.getDisplayName())) {
            result.add(new LabelValueDisplay(LABEL_DISPLAY_NAME, descriptor.getDisplayName()));
        }
        result.add(new LabelValueDisplay(LABEL_SHORT_DESCRIPTION, descriptor.getShortDescription()));
        result.add(new LabelValueDisplay(LABEL_REQUIRED, getRequired(descriptor)));
        return result;
    }

    /** {@inheritDoc} */
    @Override
    public PropertyType getPropertyType() {
        return PropertyType.FACET;
    }

}
