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
import jakarta.faces.view.AttachedObjectTarget;

import java.beans.FeatureDescriptor;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Implementation of {@link AbstractPropertyWrapper} for handling attached objects in
 * JSF composite components.
 * </p>
 * 
 * <p>
 * In JSF, attached objects represent elements that can be attached to components, such as:
 * </p>
 * <ul>
 *   <li><strong>Validators</strong> - that validate component input values</li>
 *   <li><strong>Converters</strong> - that convert between model values and UI representations</li>
 *   <li><strong>Behaviors</strong> - that add client-side behaviors to components (like AJAX functionality)</li>
 *   <li><strong>Action Listeners</strong> - that handle action events</li>
 *   <li><strong>Value Change Listeners</strong> - that handle value change events</li>
 * </ul>
 * 
 * <p>
 * This wrapper provides access to metadata about attached objects, including:
 * </p>
 * <ul>
 *   <li>The name of the attached object</li>
 *   <li>The target element to which the object is attached</li>
 * </ul>
 * 
 * <p>
 * Example of an attached object definition in a composite component:
 * </p>
 * <pre>{@code
 * <composite:interface>
 *   <composite:actionSource name="button" targets="myButton"/>
 *   <composite:valueHolder name="input" targets="myInput"/>
 *   <composite:editableValueHolder name="requiredInput" targets="myRequiredInput"/>
 * </composite:interface>
 * }</pre>
 * 
 * <p>
 * <em>Thread Safety:</em> This class is not thread-safe. Instances should not be shared
 * between threads without proper synchronization.
 * </p>
 *
 * @since 1.0
 * @see AbstractPropertyWrapper
 * @see PropertyType#ATTACHED_OBJECT
 * @see jakarta.faces.view.AttachedObjectTarget
 */
public class AttachedObjectPropertyWrapper extends AbstractPropertyWrapper {

    @Serial
    private static final long serialVersionUID = 5441115240469249001L;

    /**
     * <p>
     * Constructs an AttachedObjectPropertyWrapper for a specific attached object target.
     * </p>
     * 
     * <p>
     * This constructor:
     * </p>
     * <ol>
     *   <li>Creates display values from the attached object target</li> 
     *   <li>Passes the feature descriptor and display values to the parent constructor</li>
     * </ol>
     *
     * @param featureDescriptor the feature descriptor containing metadata about the attached object
     * @param attachedObjectTarget the attached object target that defines the attachment points
     * 
     * @see #createDisplayValues(AttachedObjectTarget)
     */
    public AttachedObjectPropertyWrapper(final FeatureDescriptor featureDescriptor,
            final AttachedObjectTarget attachedObjectTarget) {
        super(featureDescriptor, createDisplayValues(attachedObjectTarget));
    }

    /**
     * <p>
     * Creates a list of display values from an attached object target.
     * </p>
     * 
     * <p>
     * This method extracts the following information:
     * </p>
     * <ul>
     *   <li>The name of the attached object</li>
     *   <li>The target element to which the object is attached</li>
     * </ul>
     * 
     * <p>
     * The data is formatted as {@link LabelValueDisplay} objects for consistent
     * rendering in user interfaces.
     * </p>
     *
     * @param attachedObjectTarget the attached object target to extract metadata from
     * @return a list of label-value pairs representing the attached object's metadata
     * 
     * @see #getTarget(AttachedObjectTarget)
     */
    private static List<LabelValueDisplay> createDisplayValues(final AttachedObjectTarget attachedObjectTarget) {
        final List<LabelValueDisplay> result = new ArrayList<>();
        result.add(new LabelValueDisplay(LABEL_NAME, attachedObjectTarget.getName()));
        result.add(new LabelValueDisplay(LABEL_TARGET, getTarget(attachedObjectTarget)));
        return result;
    }

    /**
     * <p>
     * Extracts the target attribute from an attached object target.
     * </p>
     * 
     * <p>
     * The target attribute specifies which component(s) within the composite
     * component implementation the attached object should be applied to.
     * </p>
     * 
     * <p>
     * Note: This method is currently a placeholder and returns a fixed message.
     * A full implementation would extract the actual target information from
     * the {@link AttachedObjectTarget}.
     * </p>
     *
     * @param attachedObjectTarget the attached object target to extract the target from
     * @return the target component identifier, or a placeholder message if not implemented
     */
    private static String getTarget(final AttachedObjectTarget attachedObjectTarget) {
        return "FIXME: to be implemented";
    }

    /**
     * <p>
     * Returns the property type of this wrapper.
     * </p>
     * 
     * <p>
     * For AttachedObjectPropertyWrapper, this always returns {@link PropertyType#ATTACHED_OBJECT}
     * to indicate that this wrapper handles attached objects.
     * </p>
     *
     * @return the property type {@link PropertyType#ATTACHED_OBJECT}
     */
    @Override
    public PropertyType getPropertyType() {
        return PropertyType.ATTACHED_OBJECT;
    }
}
