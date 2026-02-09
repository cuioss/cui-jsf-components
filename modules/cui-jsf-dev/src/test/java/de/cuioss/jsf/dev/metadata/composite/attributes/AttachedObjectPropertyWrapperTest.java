/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.dev.metadata.composite.attributes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.valueobjects.generator.dynamic.GeneratorResolver;
import jakarta.faces.view.AttachedObjectTarget;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
