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
import jakarta.el.ValueExpression;
import jakarta.faces.context.FacesContext;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.beans.FeatureDescriptor;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Abstract base class for wrapping property descriptors of JSF composite components.
 * </p>
 * 
 * <p>
 * This class provides a common structure for different types of composite component properties
 * (attributes, facets, and attached objects). It extracts and organizes metadata from
 * {@link FeatureDescriptor} objects into a standardized display format using
 * {@link LabelValueDisplay} objects.
 * </p>
 * 
 * <p>
 * The class hierarchy includes:
 * </p>
 * <ul>
 *   <li>{@link AttributePropertyWrapper} - For standard component attributes</li>
 *   <li>{@link FacetPropertyWrapper} - For component facets</li>
 *   <li>{@link AttachedObjectPropertyWrapper} - For attached objects like validators</li>
 *   <li>{@link RootComponentPropertyWrapper} - For the root component's metadata</li>
 * </ul>
 * 
 * <p>
 * Each property wrapper provides:
 * </p>
 * <ul>
 *   <li>Access to basic property metadata (name, type, description, etc.)</li>
 *   <li>Access to advanced property metadata (expert, hidden, preferred)</li>
 *   <li>Quality assurance validation for required attributes</li>
 *   <li>Type-specific handling based on the property type</li>
 * </ul>
 * 
 * <p>
 * Usage example:
 * </p>
 * <pre>{@code
 * // Create a property wrapper for an attribute
 * PropertyDescriptor descriptor = ...;
 * AttributePropertyWrapper wrapper = new AttributePropertyWrapper(descriptor);
 * 
 * // Access property metadata
 * List<LabelValueDisplay> metadata = wrapper.getDisplayData();
 * PropertyType type = wrapper.getPropertyType();
 * boolean isFacet = wrapper.isFacet();
 * }</pre>
 * 
 * <p>
 * <em>Thread Safety:</em> This class is not thread-safe. Instances should not be shared
 * between threads without proper synchronization.
 * </p>
 *
 * @since 1.0
 */
@ToString
@EqualsAndHashCode
public abstract class AbstractPropertyWrapper implements Serializable {

    /**
     * <p>
     * Constant for the attribute type property name.
     * </p>
     * <p>
     * This is used to access the type information from the feature descriptor.
     * </p>
     */
    public static final String ATTRIBUTE_TYPE = "type";

    /**
     * <p>
     * Constant for the required attribute property name.
     * </p>
     * <p>
     * This is used to determine whether a property is required or optional.
     * </p>
     */
    public static final String ATTRIBUTE_REQUIRED = "required";

    /**
     * <p>
     * Label key for the required field in the display data.
     * </p>
     * <p>
     * This key is used in {@link LabelValueDisplay} objects to represent the required status.
     * </p>
     */
    public static final String LABEL_REQUIRED = "metadata.required";

    /**
     * <p>
     * Label key for the type field in the display data.
     * </p>
     * <p>
     * This key is used in {@link LabelValueDisplay} objects to represent the property type.
     * </p>
     */
    public static final String LABEL_TYPE = "metadata.type";

    /**
     * <p>
     * Label key for the name field in the display data.
     * </p>
     * <p>
     * This key is used in {@link LabelValueDisplay} objects to represent the property name.
     * </p>
     */
    public static final String LABEL_NAME = "metadata.name";

    /**
     * <p>
     * Label key for the target field in the display data.
     * </p>
     * <p>
     * This key is used in {@link LabelValueDisplay} objects to represent the target
     * of an attached object.
     * </p>
     */
    public static final String LABEL_TARGET = "metadata.target";

    /**
     * <p>
     * Label key for the display name field in the display data.
     * </p>
     * <p>
     * This key is used in {@link LabelValueDisplay} objects to represent the property's
     * display name.
     * </p>
     */
    public static final String LABEL_DISPLAY_NAME = "metadata.displayName";

    /**
     * <p>
     * Label key for the short description field in the display data.
     * </p>
     * <p>
     * This key is used in {@link LabelValueDisplay} objects to represent the property's
     * short description.
     * </p>
     */
    public static final String LABEL_SHORT_DESCRIPTION = "metadata.shortDescription";

    /**
     * <p>
     * Error message for missing type attribute.
     * </p>
     * <p>
     * This message is used when a property does not have a defined type attribute,
     * which is a quality assurance issue.
     * </p>
     */
    public static final String QA_NO_TYPE_DEFINED = "QA Issue: Undefined type attribute found for attribute";

    /**
     * <p>
     * Error message for missing target attribute.
     * </p>
     * <p>
     * This message is used when an attached object property does not have a defined
     * target attribute, which is a quality assurance issue.
     * </p>
     */
    public static final String QA_NO_TARGET_DEFINED = "QA Issue: No target for component defined";

    /**
     * <p>
     * Error message for missing required attribute.
     * </p>
     * <p>
     * This message is used when a property does not have its required status defined,
     * which is a quality assurance issue.
     * </p>
     */
    public static final String QA_REQUIRED_NOT_DEFINED = "QA Issue: Attribute 'required' not defined.";

    /**
     * Serial version UID for serialization compatibility.
     */
    @Serial
    private static final long serialVersionUID = -1868957537088500011L;

    /**
     * <p>
     * The list of basic metadata to be displayed for this property.
     * </p>
     * <p>
     * Each {@link LabelValueDisplay} in this list represents a property attribute
     * such as name, type, required status, etc.
     * </p>
     */
    @Getter
    private final List<LabelValueDisplay> displayData;

    /**
     * <p>
     * The list of advanced metadata for this property.
     * </p>
     * <p>
     * This includes information like expert mode, hidden status, and preferred status.
     * These attributes are typically not displayed in basic property views.
     * </p>
     */
    @Getter
    private final List<LabelValueDisplay> advancedMetaData;

    /**
     * <p>
     * Constructs an AbstractPropertyWrapper with the given feature descriptor and display data.
     * </p>
     * 
     * <p>
     * This constructor:
     * </p>
     * <ol>
     *   <li>Initializes the display data with the provided list</li>
     *   <li>Creates the advanced metadata list with expert, hidden, and preferred flags</li>
     * </ol>
     * 
     * <p>
     * Subclasses are responsible for populating the displayData list appropriately
     * for their specific property type.
     * </p>
     *
     * @param featureDescriptor the feature descriptor containing the property metadata
     * @param displayData the list to store the display data in, typically pre-populated by the subclass
     */
    protected AbstractPropertyWrapper(final FeatureDescriptor featureDescriptor,
            final List<LabelValueDisplay> displayData) {
        this.displayData = displayData;
        advancedMetaData = new ArrayList<>();
        advancedMetaData
                .add(new LabelValueDisplay("metadata.advanced.expert", String.valueOf(featureDescriptor.isExpert())));
        advancedMetaData
                .add(new LabelValueDisplay("metadata.advanced.hidden", String.valueOf(featureDescriptor.isHidden())));
        advancedMetaData.add(
                new LabelValueDisplay("metadata.advanced.preferred", String.valueOf(featureDescriptor.isPreferred())));
    }

    /**
     * <p>
     * Extracts the required attribute from a feature descriptor.
     * </p>
     * 
     * <p>
     * This method:
     * </p>
     * <ol>
     *   <li>Retrieves the ValueExpression for the required attribute</li>
     *   <li>Evaluates the expression in the current EL context</li>
     *   <li>Returns the string representation of the result</li>
     *   <li>Returns an error message if the required attribute is not defined</li>
     * </ol>
     *
     * @param featureDescriptor the feature descriptor to extract the required attribute from
     * @return the string representation of the required attribute value, or an error message if not defined
     */
    protected static String getRequired(final FeatureDescriptor featureDescriptor) {
        var required = QA_REQUIRED_NOT_DEFINED;
        final var ve = (ValueExpression) featureDescriptor.getValue(ATTRIBUTE_REQUIRED);
        Object object = null;
        if (null != ve) {
            object = ve.getValue(FacesContext.getCurrentInstance().getELContext());
        }
        if (null != object) {
            required = String.valueOf(object);
        }
        return required;
    }

    /**
     * <p>
     * Extracts the short description from a feature descriptor.
     * </p>
     * 
     * <p>
     * This method:
     * </p>
     * <ol>
     *   <li>Retrieves the ValueExpression for the required attribute</li>
     *   <li>Evaluates the expression in the current EL context</li>
     *   <li>Returns the string representation of the result</li>
     *   <li>Returns an error message if the attribute is not defined</li>
     * </ol>
     * 
     * <p>
     * Note: This method appears to be implemented incorrectly as it extracts the required
     * attribute instead of the short description. Subclasses should be aware of this issue
     * and potentially override or avoid using this method.
     * </p>
     *
     * @param featureDescriptor the feature descriptor to extract the short description from
     * @return the string representation of the short description, or an error message if not defined
     */
    protected static String getShortDesctiption(final FeatureDescriptor featureDescriptor) {
        String required;
        final var ve = (ValueExpression) featureDescriptor.getValue(ATTRIBUTE_REQUIRED);
        final var object = ve.getValue(FacesContext.getCurrentInstance().getELContext());
        if (null == object) {
            required = QA_REQUIRED_NOT_DEFINED;
        } else {
            required = String.valueOf(object);
        }
        return required;
    }

    /**
     * <p>
     * Returns the type of the property.
     * </p>
     * 
     * <p>
     * This abstract method must be implemented by subclasses to indicate
     * the specific type of property they represent (attribute, facet, or attached object).
     * </p>
     *
     * @return the property type enum value indicating the type of this property
     * @see PropertyType
     */
    public abstract PropertyType getPropertyType();

    /**
     * <p>
     * Determines whether this property represents a facet.
     * </p>
     * 
     * <p>
     * A facet is a named insertion point in a component where other components
     * can be placed. This method checks if the property type is {@link PropertyType#FACET}.
     * </p>
     *
     * @return {@code true} if this property represents a facet, {@code false} otherwise
     */
    public boolean isFacet() {
        return PropertyType.FACET.equals(getPropertyType());
    }

    /**
     * <p>
     * Determines whether this property represents an attribute.
     * </p>
     * 
     * <p>
     * An attribute is a configurable property of a component that affects its
     * behavior or appearance. This method checks if the property type is
     * {@link PropertyType#ATTRIBUTE}.
     * </p>
     *
     * @return {@code true} if this property represents an attribute, {@code false} otherwise
     */
    public boolean isAttribute() {
        return PropertyType.ATTRIBUTE.equals(getPropertyType());
    }

    /**
     * <p>
     * Determines whether this property represents an attached object.
     * </p>
     * 
     * <p>
     * An attached object is a JSF object that can be attached to a component,
     * such as a validator, converter, or behavior. This method checks if the
     * property type is {@link PropertyType#ATTACHED_OBJECT}.
     * </p>
     *
     * @return {@code true} if this property represents an attached object, {@code false} otherwise
     */
    public boolean isAttachedObject() {
        return PropertyType.ATTACHED_OBJECT.equals(getPropertyType());
    }
}
