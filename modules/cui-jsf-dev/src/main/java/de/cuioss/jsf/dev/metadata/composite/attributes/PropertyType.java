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

/**
 * <p>
 * Enumeration that defines the different types of properties found in JSF composite
 * components.
 * </p>
 * 
 * <p>
 * This enum is used throughout the property wrapper hierarchy to categorize properties
 * based on their role within a composite component. Each property type corresponds to
 * a specific wrapper implementation class:
 * </p>
 * 
 * <ul>
 *   <li>{@link #ROOT} - The root component metadata, wrapped by {@link RootComponentPropertyWrapper}</li>
 *   <li>{@link #ATTRIBUTE} - Standard component attributes, wrapped by {@link AttributePropertyWrapper}</li>
 *   <li>{@link #FACET} - Named insertion points, wrapped by {@link FacetPropertyWrapper}</li>
 *   <li>{@link #ATTACHED_OBJECT} - Validators, converters, etc., wrapped by {@link AttachedObjectPropertyWrapper}</li>
 *   <li>{@link #COMPONENT_TYPE} - Component type metadata</li>
 *   <li>{@link #UNDEFINED} - Properties that don't fit other categories</li>
 * </ul>
 * 
 * <p>
 * Usage example:
 * </p>
 * <pre>{@code
 * // Determine the type of a property wrapper
 * AbstractPropertyWrapper wrapper = ...;
 * PropertyType type = wrapper.getPropertyType();
 * 
 * // Check if a property is of a specific type
 * if (PropertyType.ATTRIBUTE.equals(type)) {
 *     // Handle attribute-specific logic
 * } else if (PropertyType.FACET.equals(type)) {
 *     // Handle facet-specific logic
 * }
 * 
 * // Use switch statement for type-based handling
 * switch (type) {
 *     case ATTRIBUTE:
 *         // Handle attributes
 *         break;
 *     case FACET:
 *         // Handle facets
 *         break;
 *     case ATTACHED_OBJECT:
 *         // Handle attached objects
 *         break;
 *     default:
 *         // Handle other types
 * }
 * }</pre>
 *
 * @since 1.0
 * @see AbstractPropertyWrapper#getPropertyType()
 * @see ComponentPropertiesWrapper
 */
public enum PropertyType {
    /**
     * <p>
     * Represents the root composite component itself.
     * </p>
     * 
     * <p>
     * The root property contains metadata about the composite component as a whole,
     * such as its name, description, and component-wide settings. This type is used
     * by {@link RootComponentPropertyWrapper}.
     * </p>
     */
    ROOT,

    /**
     * <p>
     * Represents an undefined or unrecognized property type.
     * </p>
     * 
     * <p>
     * This type is used as a fallback when a property doesn't fit into any of the
     * other defined categories, or when the type cannot be determined.
     * </p>
     */
    UNDEFINED,

    /**
     * <p>
     * Represents an attached object property.
     * </p>
     * 
     * <p>
     * Attached objects are JSF elements that can be attached to components, such as
     * validators, converters, and behaviors. This type is used by
     * {@link AttachedObjectPropertyWrapper}.
     * </p>
     * 
     * <p>
     * Example in composite component definition:
     * </p>
     * <pre>{@code
     * <composite:interface>
     *   <composite:actionSource name="button" targets="myButton"/>
     *   <composite:valueHolder name="input" targets="myInput"/>
     * </composite:interface>
     * }</pre>
     */
    ATTACHED_OBJECT,

    /**
     * <p>
     * Represents a facet property.
     * </p>
     * 
     * <p>
     * Facets are named insertion points in a component where other components can be
     * placed. Common examples include "header", "footer", and "actions". This type
     * is used by {@link FacetPropertyWrapper}.
     * </p>
     * 
     * <p>
     * Example in composite component definition:
     * </p>
     * <pre>{@code
     * <composite:interface>
     *   <composite:facet name="header"/>
     *   <composite:facet name="footer"/>
     * </composite:interface>
     * }</pre>
     */
    FACET,

    /**
     * <p>
     * Represents the component type metadata.
     * </p>
     * 
     * <p>
     * This type is used for properties related to the component's type information,
     * including its class, implementation details, and rendering characteristics.
     * </p>
     */
    COMPONENT_TYPE,

    /**
     * <p>
     * Represents a standard component attribute.
     * </p>
     * 
     * <p>
     * Attributes are configurable properties that affect a component's behavior
     * and appearance. Examples include "value", "styleClass", and "rendered".
     * This type is used by {@link AttributePropertyWrapper}.
     * </p>
     * 
     * <p>
     * Example in composite component definition:
     * </p>
     * <pre>{@code
     * <composite:interface>
     *   <composite:attribute name="value" type="java.lang.String" required="true"/>
     *   <composite:attribute name="styleClass" type="java.lang.String"/>
     *   <composite:attribute name="disabled" type="java.lang.Boolean" default="false"/>
     * </composite:interface>
     * }</pre>
     */
    ATTRIBUTE
}
