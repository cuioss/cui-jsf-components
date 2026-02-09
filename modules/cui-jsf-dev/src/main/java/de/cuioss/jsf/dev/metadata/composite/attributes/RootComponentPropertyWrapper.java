/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
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
 * <p>
 * Implementation of {@link AbstractPropertyWrapper} for handling the root component
 * metadata in JSF composite components.
 * </p>
 * 
 * <p>
 * The root component represents the composite component as a whole, rather than
 * a specific attribute, facet, or attached object. This wrapper provides access to
 * the component's top-level metadata, such as:
 * </p>
 * <ul>
 *   <li>Display name - The human-readable name of the component</li>
 *   <li>Description - A description of the component's purpose and usage</li>
 * </ul>
 * 
 * <p>
 * This wrapper is typically created from the BeanDescriptor of a composite component's
 * BeanInfo object and used in conjunction with other property wrappers in a
 * {@link ComponentPropertiesWrapper}.
 * </p>
 * 
 * <p>
 * Usage example:
 * </p>
 * <pre>{@code
 * // Create a root component wrapper from a bean descriptor
 * BeanDescriptor beanDescriptor = ...;
 * RootComponentPropertyWrapper rootWrapper = new RootComponentPropertyWrapper(beanDescriptor);
 * 
 * // Access the component metadata
 * List<LabelValueDisplay> metadata = rootWrapper.getDisplayData();
 * PropertyType type = rootWrapper.getPropertyType(); // Always returns PropertyType.ROOT
 * }</pre>
 * 
 * <p>
 * <em>Thread Safety:</em> This class is not thread-safe. Instances should not be shared
 * between threads without proper synchronization.
 * </p>
 *
 * @since 1.0
 * @see AbstractPropertyWrapper
 * @see PropertyType#ROOT
 * @see ComponentPropertiesWrapper
 */
public class RootComponentPropertyWrapper extends AbstractPropertyWrapper {

    @Serial
    private static final long serialVersionUID = -5598320044290880454L;

    /**
     * <p>
     * Constructs a RootComponentPropertyWrapper for a specific feature descriptor.
     * </p>
     * 
     * <p>
     * This constructor:
     * </p>
     * <ol>
     *   <li>Creates display values from the feature descriptor</li>
     *   <li>Passes the feature descriptor and display values to the parent constructor</li>
     * </ol>
     *
     * @param featureDescriptor the feature descriptor containing metadata about the composite component
     * 
     * @see #createDisplayValues(FeatureDescriptor)
     */
    public RootComponentPropertyWrapper(final FeatureDescriptor featureDescriptor) {
        super(featureDescriptor, createDisplayValues(featureDescriptor));
    }

    /**
     * <p>
     * Creates a list of display values from a feature descriptor.
     * </p>
     * 
     * <p>
     * This method extracts the following component metadata:
     * </p>
     * <ul>
     *   <li>Display name - Only included if different from the component name</li>
     *   <li>Description - A short description of the component's purpose</li>
     * </ul>
     * 
     * <p>
     * The data is formatted as {@link LabelValueDisplay} objects for consistent
     * rendering in user interfaces. The method specifically avoids duplicate
     * information by only including the display name if it differs from the
     * component name.
     * </p>
     *
     * @param descriptor the feature descriptor to extract component metadata from
     * @return a list of label-value pairs representing the component's metadata
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

    /**
     * <p>
     * Returns the property type of this wrapper.
     * </p>
     * 
     * <p>
     * For RootComponentPropertyWrapper, this always returns {@link PropertyType#ROOT}
     * to indicate that this wrapper handles the root component metadata.
     * </p>
     *
     * @return the property type {@link PropertyType#ROOT}
     */
    @Override
    public PropertyType getPropertyType() {
        return PropertyType.ROOT;
    }
}
