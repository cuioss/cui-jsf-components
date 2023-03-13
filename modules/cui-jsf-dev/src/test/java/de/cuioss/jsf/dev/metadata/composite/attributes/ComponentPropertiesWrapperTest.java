package de.cuioss.jsf.dev.metadata.composite.attributes;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.beans.FeatureDescriptor;

import org.junit.jupiter.api.Test;

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
