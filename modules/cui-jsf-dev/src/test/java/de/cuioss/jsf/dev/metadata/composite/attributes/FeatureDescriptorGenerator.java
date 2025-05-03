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

import static de.cuioss.test.generator.Generators.booleans;
import static de.cuioss.test.generator.Generators.fixedValues;

import de.cuioss.test.generator.TypedGenerator;

import java.beans.FeatureDescriptor;

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
