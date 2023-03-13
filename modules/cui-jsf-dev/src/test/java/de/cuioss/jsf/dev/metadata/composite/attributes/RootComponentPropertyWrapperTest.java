package de.cuioss.jsf.dev.metadata.composite.attributes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class RootComponentPropertyWrapperTest extends AbstractPropertyWrapperTest<RootComponentPropertyWrapper> {

    @Override
    public RootComponentPropertyWrapper getUnderTest() {
        return new RootComponentPropertyWrapper(featureDescriptorGenerator.next());
    }

    @Test
    void shouldBeProperType() {
        assertEquals(PropertyType.ROOT, getUnderTest().getPropertyType());
    }

}
