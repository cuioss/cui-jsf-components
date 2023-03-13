package de.cuioss.jsf.dev.metadata.composite.attributes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class AttributePropertyWrapperTest extends AbstractPropertyWrapperTest<AttributePropertyWrapper> {

    @Override
    public AttributePropertyWrapper getUnderTest() {
        return new AttributePropertyWrapper(featureDescriptorGenerator.next());
    }

    @Test
    void shouldBeProperType() {
        assertEquals(PropertyType.ATTRIBUTE, getUnderTest().getPropertyType());
    }

    @Test
    void shouldReturnDescriptors() {
        assertNotNull(getUnderTest().getAdvancedMetaData());
    }
}
