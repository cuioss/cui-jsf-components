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
 * <p>
 * Implementation of {@link AbstractPropertyWrapper} for handling standard attributes
 * of JSF composite components.
 * </p>
 * 
 * <p>
 * Attributes in JSF composite components represent configurable properties that affect
 * the component's behavior and appearance. This wrapper extracts and organizes metadata
 * about these attributes, including:
 * </p>
 * <ul>
 *   <li>Attribute name</li>
 *   <li>Required status (whether the attribute must be provided)</li>
 *   <li>Type information (expected Java type of the attribute value)</li>
 *   <li>Description of the attribute's purpose</li>
 * </ul>
 * 
 * <p>
 * Example of attribute definitions in a composite component:
 * </p>
 * <pre>{@code
 * <composite:interface>
 *   <composite:attribute name="value" type="java.lang.String" required="true"
 *                       shortDescription="The main value for the component"/>
 *   <composite:attribute name="styleClass" type="java.lang.String"
 *                       shortDescription="Additional CSS classes to apply"/>
 *   <composite:attribute name="disabled" type="java.lang.Boolean" default="false"
 *                       shortDescription="Whether the component is disabled"/>
 * </composite:interface>
 * }</pre>
 * 
 * <p>
 * The wrapper handles EL ValueExpression wrapping for attribute types, which is a key
 * aspect of JSF's attribute system. All attributes in JSF can be provided as literal
 * values or as EL expressions that are evaluated at runtime.
 * </p>
 * 
 * <p>
 * <em>Thread Safety:</em> This class is not thread-safe. Instances should not be shared
 * between threads without proper synchronization.
 * </p>
 *
 * @since 1.0
 * @see AbstractPropertyWrapper
 * @see PropertyType#ATTRIBUTE
 */
public class AttributePropertyWrapper extends AbstractPropertyWrapper {

    @Serial
    private static final long serialVersionUID = -5712689755612400568L;

    /**
     * <p>
     * Format string used to wrap type information in EL ValueExpression context.
     * </p>
     * 
     * <p>
     * This indicates that attribute values in JSF are typically passed as
     * ValueExpressions that must evaluate to the specified type. The format
     * includes the actual type name when displayed.
     * </p>
     * 
     * <p>
     * Example output: "javax.el.ValueExpression (must evaluate to java.lang.String)"
     * </p>
     */
    private static final String TYPE_WRAPPER = "javax.el.ValueExpression (must evaluate to %s)";

    /**
     * <p>
     * Constructs an AttributePropertyWrapper for a specific feature descriptor.
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
     * @param featureDescriptor the feature descriptor containing metadata about the attribute
     * 
     * @see #createDisplayValues(FeatureDescriptor)
     */
    public AttributePropertyWrapper(final FeatureDescriptor featureDescriptor) {
        super(featureDescriptor, createDisplayValues(featureDescriptor));
    }

    /**
     * <p>
     * Creates a list of display values from a feature descriptor.
     * </p>
     * 
     * <p>
     * This method extracts the following attribute metadata:
     * </p>
     * <ul>
     *   <li>Name - The identifier of the attribute</li>
     *   <li>Required status - Whether the attribute must be provided</li>
     *   <li>Type - The expected Java type of the attribute value</li>
     *   <li>Description - A short description of the attribute's purpose</li>
     * </ul>
     * 
     * <p>
     * The data is formatted as {@link LabelValueDisplay} objects for consistent
     * rendering in user interfaces.
     * </p>
     *
     * @param descriptor the feature descriptor to extract attribute metadata from
     * @return a list of label-value pairs representing the attribute's metadata
     * 
     * @see #getType(FeatureDescriptor)
     * @see AbstractPropertyWrapper#getRequired(FeatureDescriptor)
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
     * <p>
     * Extracts the type attribute from a feature descriptor.
     * </p>
     * 
     * <p>
     * The type attribute specifies the expected Java type of the attribute value.
     * This method retrieves the type class and formats it in the context of JSF's
     * EL evaluation system.
     * </p>
     * 
     * <p>
     * If no type is defined, a quality assurance issue message is returned.
     * </p>
     *
     * @param featureDescriptor the feature descriptor to extract the type from
     * @return the formatted type information, or a QA issue message if type is not defined
     */
    private static String getType(final FeatureDescriptor featureDescriptor) {
        var type = QA_NO_TYPE_DEFINED;
        final Class<?> clazz = (Class<?>) featureDescriptor.getValue(ATTRIBUTE_TYPE);
        if (null != clazz) {
            type = TYPE_WRAPPER.formatted(clazz.getName());
        }
        return type;
    }

    /**
     * <p>
     * Returns the property type of this wrapper.
     * </p>
     * 
     * <p>
     * For AttributePropertyWrapper, this always returns {@link PropertyType#ATTRIBUTE}
     * to indicate that this wrapper handles standard component attributes.
     * </p>
     *
     * @return the property type {@link PropertyType#ATTRIBUTE}
     */
    @Override
    public PropertyType getPropertyType() {
        return PropertyType.ATTRIBUTE;
    }
}
