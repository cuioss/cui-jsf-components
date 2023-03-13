package de.cuioss.jsf.dev.metadata.composite.attributes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.faces.view.AttachedObjectTarget;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.valueobjects.generator.dynamic.GeneratorResolver;

class AttachedObjectPropertyWrapperTest extends AbstractPropertyWrapperTest<AttachedObjectPropertyWrapper> {

    TypedGenerator<AttachedObjectTarget> attachedObjectTargetGenerator;

    @BeforeEach
    void initAttachedObjectTargetGenerator() {
        attachedObjectTargetGenerator = GeneratorResolver.resolveGenerator(AttachedObjectTarget.class);
    }

    @Override
    public AttachedObjectPropertyWrapper getUnderTest() {
        return new AttachedObjectPropertyWrapper(featureDescriptorGenerator.next(),
                attachedObjectTargetGenerator.next());
    }

    @Test
    void shouldBeProperType() {
        assertEquals(PropertyType.ATTACHED_OBJECT, getUnderTest().getPropertyType());
    }
}
