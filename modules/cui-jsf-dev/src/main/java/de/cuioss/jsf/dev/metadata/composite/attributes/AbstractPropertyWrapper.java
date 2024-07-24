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
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.el.ValueExpression;
import jakarta.faces.context.FacesContext;

import de.cuioss.jsf.dev.metadata.composite.util.LabelValueDisplay;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Wraps a concrete Property for composite components.
 * <p>
 * In order to simplify composite component the values to be displayed will be
 * kept in a list of {@link LabelValueDisplay} objects.
 * </p>
 *
 * @author e0571
 */
@ToString
@EqualsAndHashCode
public abstract class AbstractPropertyWrapper implements Serializable {

    /** "type". */
    public static final String ATTRIBUTE_TYPE = "type";

    /** "required". */
    public static final String ATTRIBUTE_REQUIRED = "required";

    /** "metadata.required". */
    public static final String LABEL_REQUIRED = "metadata.required";

    /** "metadata.type". */
    public static final String LABEL_TYPE = "metadata.type";

    /** "metadata.name". */
    public static final String LABEL_NAME = "metadata.name";

    /** "metadata.name". */
    public static final String LABEL_TARGET = "metadata.target";

    /** "metadata.displayName". */
    public static final String LABEL_DISPLAY_NAME = "metadata.displayName";

    /** "metadata.shortDescription". */
    public static final String LABEL_SHORT_DESCRIPTION = "metadata.shortDescription";

    /** "QA Issue: Undefined type attribute found for attribute". */
    public static final String QA_NO_TYPE_DEFINED = "QA Issue: Undefined type attribute found for attribute";

    /** "No target for component defined". */
    public static final String QA_NO_TARGET_DEFINED = "QA Issue: No target for component defined";

    /** "QA Issue: Attribute 'required' not defined.". */
    public static final String QA_REQUIRED_NOT_DEFINED = "QA Issue: Attribute 'required' not defined.";

    /** serialVersionUID. */
    @Serial
    private static final long serialVersionUID = -1868957537088500011L;

    /** The data to be displayed. */
    @Getter
    private final List<LabelValueDisplay> displayData;

    /**
     * The metadata to be displayed, that is more advanced, like expert Mode & co.
     */
    @Getter
    private final List<LabelValueDisplay> advancedMetaData;

    /**
     * Constructor.
     *
     * @param featureDescriptor to be wrapped
     * @param displayData       The data to be displayed
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
     * Extracts the required attribute
     *
     * @param featureDescriptor descriptor to extract the attribute from
     * @return the extracted target attribute
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
     * Extracts the short-description
     *
     * @param featureDescriptor descriptor to extract the description from
     * @return the extracted short-description
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
     * Returns the type of the property.
     *
     * @return the propertyType
     */
    public abstract PropertyType getPropertyType();

    /**
     * @return boolean indicating whether current property represents a facet.
     */
    public boolean isFacet() {
        return PropertyType.FACET.equals(getPropertyType());
    }

    /**
     * @return boolean indicating whether current property represents an attribute.
     */
    public boolean isAttribute() {
        return PropertyType.ATTRIBUTE.equals(getPropertyType());
    }

    /**
     * @return boolean indicating whether current property represents an attached
     *         object.
     */
    public boolean isAttachedObject() {
        return PropertyType.ATTACHED_OBJECT.equals(getPropertyType());
    }
}
