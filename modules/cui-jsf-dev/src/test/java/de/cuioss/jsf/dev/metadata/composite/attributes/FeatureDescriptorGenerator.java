package de.cuioss.jsf.dev.metadata.composite.attributes;

import static de.cuioss.test.generator.Generators.booleans;
import static de.cuioss.test.generator.Generators.fixedValues;

import java.beans.FeatureDescriptor;

import de.cuioss.test.generator.TypedGenerator;

class FeatureDescriptorGenerator implements TypedGenerator<FeatureDescriptor> {

    private final TypedGenerator<String> names = fixedValues("root", "faces", "facets", "name");
    private final TypedGenerator<String> displayNames = fixedValues("Some", "Other", "component", "name");

    @Override
    public FeatureDescriptor next() {
        var descriptor = new FeatureDescriptor();
        descriptor.setName(names.next());
        descriptor.setDisplayName(displayNames.next());
        descriptor.setExpert(booleans().next());
        descriptor.setHidden(booleans().next());
        descriptor.setPreferred(booleans().next());
        return descriptor;
    }

}
