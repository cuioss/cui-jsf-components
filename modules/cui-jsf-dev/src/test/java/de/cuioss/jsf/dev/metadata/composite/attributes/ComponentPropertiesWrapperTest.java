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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import java.beans.FeatureDescriptor;

class ComponentPropertiesWrapperTest extends AbstractPropertyWrapperTest<ComponentPropertiesWrapper> {

    @Override
    public ComponentPropertiesWrapper getUnderTest() {
        return new ComponentPropertiesWrapper();
    }

    @Test
    void shouldSensibleDefault() {
        var type = getUnderTest();

        assertFalse(type.isAttachedObjectsHolder());
        assertFalse(type.isAttributeHolder());
        assertFalse(type.isFacetHolder());
    }

    @Test
    void shouldHandleParentDescriptor() {
        assertDoesNotThrow(() -> getUnderTest().addParentComponentDescriptor(featureDescriptorGenerator.next()));
        assertDoesNotThrow(() -> getUnderTest().addParentComponentDescriptor(withAttributes()));
    }

    @Test
    void shouldHandleChildDescriptor() {
        assertDoesNotThrow(() -> getUnderTest().addChildComponentDescriptor(featureDescriptorGenerator.next()));
        assertDoesNotThrow(() -> getUnderTest().addChildComponentDescriptor(withAttributes()));
    }

    FeatureDescriptor withAttributes() {
        var descriptor = featureDescriptorGenerator.next();

        descriptor.setValue("a", "a-value");
        descriptor.setValue("b", "b-value");

        return descriptor;
    }
}
