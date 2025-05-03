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
 * Implementation for the core component.
 *
 * @author e0571
 */
public class RootComponentPropertyWrapper extends AbstractPropertyWrapper {

    @Serial
    private static final long serialVersionUID = -5598320044290880454L;

    /**
     * Constructor
     *
     * @param featureDescriptor to be wrapped
     */
    public RootComponentPropertyWrapper(final FeatureDescriptor featureDescriptor) {
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
        // Only add display name if it is not the same like name
        if (!descriptor.getName().equals(descriptor.getDisplayName())) {
            result.add(new LabelValueDisplay(LABEL_DISPLAY_NAME, descriptor.getDisplayName()));
        }
        result.add(new LabelValueDisplay(LABEL_SHORT_DESCRIPTION, descriptor.getShortDescription()));
        return result;
    }

    @Override
    public PropertyType getPropertyType() {
        return PropertyType.ROOT;
    }

}
