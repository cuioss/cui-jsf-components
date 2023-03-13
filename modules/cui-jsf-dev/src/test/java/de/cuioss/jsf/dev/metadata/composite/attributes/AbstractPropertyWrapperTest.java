package de.cuioss.jsf.dev.metadata.composite.attributes;

import de.cuioss.test.generator.junit.EnableGeneratorController;
import de.cuioss.test.jsf.junit5.JsfEnabledTestEnvironment;
import de.cuioss.test.valueobjects.junit5.EnableGeneratorRegistry;
import de.cuioss.test.valueobjects.junit5.contracts.ShouldBeNotNull;

@EnableGeneratorRegistry
@EnableGeneratorController
abstract class AbstractPropertyWrapperTest<T> extends JsfEnabledTestEnvironment implements ShouldBeNotNull<T> {

    protected final FeatureDescriptorGenerator featureDescriptorGenerator = new FeatureDescriptorGenerator();

}
