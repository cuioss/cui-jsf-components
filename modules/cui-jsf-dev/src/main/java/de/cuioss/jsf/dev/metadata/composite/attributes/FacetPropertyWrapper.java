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
 * Implementation of {@link AbstractPropertyWrapper} for handling facets in
 * JSF composite components.
 * </p>
 * 
 * <p>
 * Facets in JSF components are named insertion points where other UI components
 * can be placed. They allow for flexible component composition by defining specific
 * areas where child components can be inserted. Common examples include:
 * </p>
 * <ul>
 *   <li><strong>header</strong> - for content at the top of a component</li>
 *   <li><strong>footer</strong> - for content at the bottom of a component</li>
 *   <li><strong>actions</strong> - for buttons or other action elements</li>
 *   <li><strong>toolbar</strong> - for toolbar content</li>
 * </ul>
 * 
 * <p>
 * This wrapper extracts and organizes metadata about facets, including:
 * </p>
 * <ul>
 *   <li>Facet name - The identifier used to reference the facet</li>
 *   <li>Display name - A human-readable name (if different from the facet name)</li>
 *   <li>Description - A description of the facet's purpose</li>
 *   <li>Required status - Whether the facet must be provided</li>
 * </ul>
 * 
 * <p>
 * Example of facet definitions in a composite component:
 * </p>
 * <pre>{@code
 * <composite:interface>
 *   <composite:facet name="header" required="false" 
 *                    shortDescription="Content to display at the top of the component"/>
 *   <composite:facet name="footer" required="false"
 *                    shortDescription="Content to display at the bottom of the component"/>
 * </composite:interface>
 * 
 * <!-- Usage example -->
 * <my:component>
 *   <f:facet name="header">
 *     <h:outputText value="Header Content"/>
 *   </f:facet>
 * </my:component>
 * }</pre>
 * 
 * <p>
 * <em>Thread Safety:</em> This class is not thread-safe. Instances should not be shared
 * between threads without proper synchronization.
 * </p>
 *
 * @since 1.0
 * @see AbstractPropertyWrapper
 * @see PropertyType#FACET
 */
public class FacetPropertyWrapper extends AbstractPropertyWrapper {

    @Serial
    private static final long serialVersionUID = 674844732715688425L;

    /**
     * <p>
     * Constructs a FacetPropertyWrapper for a specific facet.
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
     * <p>
     * Note that while the name parameter is provided, it appears to be unused in the
     * current implementation as the name is extracted from the feature descriptor
     * in the {@link #createDisplayValues(FeatureDescriptor)} method.
     * </p>
     *
     * @param name the name of the facet (currently unused in implementation)
     * @param featureDescriptor the feature descriptor containing metadata about the facet
     * 
     * @see #createDisplayValues(FeatureDescriptor)
     */
    public FacetPropertyWrapper(final String name, final FeatureDescriptor featureDescriptor) {
        super(featureDescriptor, createDisplayValues(featureDescriptor));
    }

    /**
     * <p>
     * Creates a list of display values from a feature descriptor.
     * </p>
     * 
     * <p>
     * This method extracts the following facet metadata:
     * </p>
     * <ul>
     *   <li>Name - The identifier of the facet</li>
     *   <li>Display name - Only included if different from the name</li>
     *   <li>Description - A short description of the facet's purpose</li>
     *   <li>Required status - Whether the facet must be provided</li>
     * </ul>
     * 
     * <p>
     * The data is formatted as {@link LabelValueDisplay} objects for consistent
     * rendering in user interfaces. The method specifically avoids duplicate 
     * information by only including the display name if it differs from the 
     * facet name.
     * </p>
     *
     * @param descriptor the feature descriptor to extract facet metadata from
     * @return a list of label-value pairs representing the facet's metadata
     * 
     * @see AbstractPropertyWrapper#getRequired(FeatureDescriptor)
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

    /**
     * <p>
     * Returns the property type of this wrapper.
     * </p>
     * 
     * <p>
     * For FacetPropertyWrapper, this always returns {@link PropertyType#FACET}
     * to indicate that this wrapper handles component facets.
     * </p>
     *
     * @return the property type {@link PropertyType#FACET}
     */
    @Override
    public PropertyType getPropertyType() {
        return PropertyType.FACET;
    }
}
