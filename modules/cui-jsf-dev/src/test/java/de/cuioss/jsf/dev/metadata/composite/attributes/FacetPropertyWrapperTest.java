package de.cuioss.jsf.dev.metadata.composite.attributes;

class FacetPropertyWrapperTest extends AbstractPropertyWrapperTest<FacetPropertyWrapper> {

    @Override
    public FacetPropertyWrapper getUnderTest() {
        var next = featureDescriptorGenerator.next();
        return new FacetPropertyWrapper(next.getName(), next);
    }

}
