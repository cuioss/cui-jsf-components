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
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.AttachedObjectTarget;

import de.cuioss.jsf.dev.metadata.composite.util.LabelValueDisplay;

/**
 * Implementation for an attached object.
 *
 * @author e0571
 */
public class AttachedObjectPropertyWrapper extends AbstractPropertyWrapper {

    private static final long serialVersionUID = 5441115240469249001L;

    /**
     * Constructor
     *
     * @param featureDescriptor    to be wrapped
     * @param attachedObjectTarget attached object target
     */
    public AttachedObjectPropertyWrapper(final FeatureDescriptor featureDescriptor,
            final AttachedObjectTarget attachedObjectTarget) {
        super(featureDescriptor, createDisplayValues(attachedObjectTarget));
    }

    /**
     * Creates the data to be displayed.
     *
     * @param featureDescriptor
     * @param attachedObjectTarget
     * @return the list of display data
     */
    private static List<LabelValueDisplay> createDisplayValues(final AttachedObjectTarget attachedObjectTarget) {
        final List<LabelValueDisplay> result = new ArrayList<>();
        result.add(new LabelValueDisplay(LABEL_NAME, attachedObjectTarget.getName()));
        result.add(new LabelValueDisplay(LABEL_TARGET, getTarget(attachedObjectTarget)));
        return result;
    }

    /**
     * Extracts the target attribute
     *
     * @param attachedObjectTarget
     * @return the extracted target attribute
     */
    private static String getTarget(final AttachedObjectTarget attachedObjectTarget) {
        final var type = "FIXME: to be implemented";
        return type;
    }

    @Override
    public PropertyType getPropertyType() {
        return PropertyType.ATTACHED_OBJECT;
    }
}
