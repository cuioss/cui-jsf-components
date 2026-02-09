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

import de.cuioss.test.generator.junit.EnableGeneratorController;
import de.cuioss.test.jsf.junit5.EnableJsfEnvironment;
import de.cuioss.test.valueobjects.junit5.EnableGeneratorRegistry;
import de.cuioss.test.valueobjects.junit5.contracts.ShouldBeNotNull;

@EnableGeneratorRegistry
@EnableGeneratorController
@EnableJsfEnvironment
abstract class AbstractPropertyWrapperTest<T> implements ShouldBeNotNull<T> {

    protected final FeatureDescriptorGenerator featureDescriptorGenerator = new FeatureDescriptorGenerator();

}
