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

import jakarta.faces.component.UIComponent;
import jakarta.faces.view.AttachedObjectTarget;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.beans.FeatureDescriptor;
import java.beans.PropertyDescriptor;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * <p>
 * Container class that manages all property wrappers for a JSF composite component.
 * </p>
 * 
 * <p>
 * This class acts as a central repository for all metadata about a composite
 * component, organizing the component's properties into distinct categories:
 * </p>
 * <ul>
 *   <li>Root descriptor - Metadata about the component as a whole</li>
 *   <li>Attributes - Standard configurable properties</li>
 *   <li>Facets - Named insertion points for child components</li>
 *   <li>Attached objects - Validators, converters, and behaviors</li>
 * </ul>
 * 
 * <p>
 * The wrapper provides:
 * </p>
 * <ul>
 *   <li>Methods to add and categorize property descriptors</li>
 *   <li>Access to categorized lists of property wrappers</li>
 *   <li>Utility methods to check if specific property types are present</li>
 *   <li>Support for lazy initialization with a configuration flag</li>
 * </ul>
 * 
 * <p>
 * Usage example:
 * </p>
 * <pre>{@code
 * // Create and configure a properties wrapper
 * ComponentPropertiesWrapper wrapper = new ComponentPropertiesWrapper();
 * 
 * // Add a parent descriptor (contains facets and attached objects)
 * wrapper.addParentComponentDescriptor(beanDescriptor);
 * 
 * // Add individual attribute descriptors
 * for (PropertyDescriptor descriptor : propertyDescriptors) {
 *     wrapper.addChildComponentDescriptor(descriptor);
 * }
 * 
 * // Mark as fully configured
 * wrapper.setConfigured(true);
 * 
 * // Access the categorized properties
 * AbstractPropertyWrapper rootDescriptor = wrapper.getRootDescriptor();
 * List<AbstractPropertyWrapper> attributes = wrapper.getAttributes();
 * List<AbstractPropertyWrapper> facets = wrapper.getFacetList();
 * List<AbstractPropertyWrapper> attachedObjects = wrapper.getAttachedObjects();
 * }</pre>
 * 
 * <p>
 * <em>Thread Safety:</em> This class is not thread-safe. Instances should not be shared
 * between threads without proper synchronization.
 * </p>
 *
 * @author e0571
 * @since 1.0
 * @see AbstractPropertyWrapper
 * @see AttributePropertyWrapper
 * @see FacetPropertyWrapper
 * @see AttachedObjectPropertyWrapper
 * @see RootComponentPropertyWrapper
 */
@ToString
@EqualsAndHashCode
public class ComponentPropertiesWrapper implements Serializable {

    @Serial
    private static final long serialVersionUID = 8036185753548211952L;

    /**
     * <p>
     * Key for storing the composite component name in additional information.
     * </p>
     * 
     * <p>
     * This key is used when providing additional metadata to the wrapper that is not
     * covered by the standard {@link java.beans.BeanDescriptor} properties.
     * </p>
     */
    public static final String COMPOSITE_NAME_KEY = "compositeNameKey";

    /**
     * <p>
     * Key for storing the composite component library in additional information.
     * </p>
     * 
     * <p>
     * This key is used when providing additional metadata to the wrapper that is not
     * covered by the standard {@link java.beans.BeanDescriptor} properties.
     * </p>
     */
    public static final String COMPOSITE_LIB_KEY = "compositeLibKey";

    /**
     * <p>
     * The list of attached object wrappers for the component.
     * </p>
     * 
     * <p>
     * Attached objects include validators, converters, and behaviors that can be
     * attached to the component. Each attached object is represented by an
     * {@link AttachedObjectPropertyWrapper}.
     * </p>
     */
    @Getter
    private final List<AbstractPropertyWrapper> attachedObjects = new ArrayList<>();

    /**
     * <p>
     * The list of facet wrappers for the component.
     * </p>
     * 
     * <p>
     * Facets are named insertion points where other UI components can be placed.
     * Each facet is represented by a {@link FacetPropertyWrapper}.
     * </p>
     */
    @Getter
    private final List<AbstractPropertyWrapper> facetList = new ArrayList<>();

    /**
     * <p>
     * The list of attribute wrappers for the component.
     * </p>
     * 
     * <p>
     * Attributes are configurable properties that affect the component's behavior
     * and appearance. Each attribute is represented by an {@link AttributePropertyWrapper}.
     * </p>
     */
    @Getter
    private final List<AbstractPropertyWrapper> attributes = new ArrayList<>();

    /**
     * <p>
     * The wrapper for the root component descriptor.
     * </p>
     * 
     * <p>
     * This contains metadata about the component as a whole, such as its name,
     * description, and component-wide properties. It is represented by a
     * {@link RootComponentPropertyWrapper}.
     * </p>
     */
    @Getter
    private AbstractPropertyWrapper rootDescriptor;

    /**
     * <p>
     * Flag indicating whether the wrapper has been fully configured.
     * </p>
     * 
     * <p>
     * This flag is used to support lazy initialization. The wrapper is considered
     * fully configured once all property descriptors have been added and processed.
     * </p>
     */
    @Getter
    @Setter
    private boolean configured = false;

    /**
     * <p>
     * Initializes the properties for the parent composite component.
     * </p>
     * 
     * <p>
     * This method processes the given feature descriptor and extracts:
     * </p>
     * <ul>
     *   <li>Attached objects (validators, converters, behaviors) - Stored in {@link #attachedObjects}</li>
     *   <li>Facets (named insertion points) - Stored in {@link #facetList}</li>
     *   <li>Root component metadata - Stored in {@link #rootDescriptor}</li>
     * </ul>
     * 
     * <p>
     * This method should be called once with the bean descriptor from the component's
     * BeanInfo object, typically obtained from the ViewDeclarationLanguage's
     * getComponentMetadata method.
     * </p>
     *
     * @param descriptor the feature descriptor containing the component's metadata
     * 
     * @see jakarta.faces.view.ViewDeclarationLanguage#getComponentMetadata
     */
    @SuppressWarnings("unchecked")
    public void addParentComponentDescriptor(final FeatureDescriptor descriptor) {
        final var extraValues = descriptor.attributeNames();
        String curName;
        while (extraValues.hasMoreElements()) {
            // Caution: the PropertyDescriptor is used in two ways: it either contains a
            // single
            // attribute or a list of facets / attached Objects.
            curName = extraValues.nextElement();
            if (AttachedObjectTarget.ATTACHED_OBJECT_TARGETS_KEY.equals(curName)) {
                final var list = (List<AttachedObjectTarget>) descriptor.getValue(curName);
                for (final AttachedObjectTarget attachedObjectTarget : list) {
                    attachedObjects.add(new AttachedObjectPropertyWrapper(descriptor, attachedObjectTarget));
                }
            } else if (UIComponent.FACETS_KEY.equals(curName)) {
                final var loadedFacets = (Map<String, PropertyDescriptor>) descriptor.getValue(curName);
                for (final Entry<String, PropertyDescriptor> key : loadedFacets.entrySet()) {
                    facetList.add(new FacetPropertyWrapper(key.getKey(), loadedFacets.get(key.getKey())));
                }
            }
        }
        // Add root descriptor
        rootDescriptor = new RootComponentPropertyWrapper(descriptor);
    }

    /**
     * <p>
     * Adds an individual property descriptor to the wrapper.
     * </p>
     * 
     * <p>
     * This method processes the given feature descriptor and adds it as an attribute
     * to the {@link #attributes} list. The method validates that the descriptor does not
     * contain attached objects or facets, which should be handled by
     * {@link #addParentComponentDescriptor(FeatureDescriptor)} instead.
     * </p>
     * 
     * <p>
     * This method should be called for each property descriptor from the component's
     * BeanInfo object, typically obtained from the ViewDeclarationLanguage's
     * getComponentMetadata method.
     * </p>
     *
     * @param descriptor the feature descriptor to add as an attribute
     * @throws IllegalArgumentException if the descriptor contains attached objects or facets
     * 
     * @see jakarta.faces.view.ViewDeclarationLanguage#getComponentMetadata
     */
    public void addChildComponentDescriptor(final FeatureDescriptor descriptor) {
        final var extraValues = descriptor.attributeNames();
        String curName;
        if (extraValues.hasMoreElements()) {
            // Caution: the PropertyDescriptor is used in two ways: it either contains a
            // single
            // attribute or a list of facets / attached Objects.
            curName = extraValues.nextElement();
            if (AttachedObjectTarget.ATTACHED_OBJECT_TARGETS_KEY.equals(curName)) {
                throw new IllegalArgumentException("Attached Objects should not be found here");
            }
            if (UIComponent.FACETS_KEY.equals(curName)) {
                throw new IllegalArgumentException("Facets should not be found here");
            }
            attributes.add(new AttributePropertyWrapper(descriptor));
        }
    }

    /**
     * <p>
     * Determines whether this wrapper contains any attributes.
     * </p>
     * 
     * <p>
     * Attributes are configurable properties that affect the component's behavior
     * and appearance. This method checks if the {@link #attributes} list contains
     * any elements.
     * </p>
     *
     * @return {@code true} if this wrapper contains attributes, {@code false} otherwise
     */
    public boolean isAttributeHolder() {
        return !attributes.isEmpty();
    }

    /**
     * <p>
     * Determines whether this wrapper contains any facets.
     * </p>
     * 
     * <p>
     * Facets are named insertion points where other UI components can be placed.
     * This method checks if the {@link #facetList} contains any elements.
     * </p>
     *
     * @return {@code true} if this wrapper contains facets, {@code false} otherwise
     */
    public boolean isFacetHolder() {
        return !facetList.isEmpty();
    }

    /**
     * <p>
     * Determines whether this wrapper contains any attached objects.
     * </p>
     * 
     * <p>
     * Attached objects include validators, converters, and behaviors that can be
     * attached to the component. This method checks if the {@link #attachedObjects}
     * list contains any elements.
     * </p>
     *
     * @return {@code true} if this wrapper contains attached objects, {@code false} otherwise
     */
    public boolean isAttachedObjectsHolder() {
        return !attachedObjects.isEmpty();
    }
}
