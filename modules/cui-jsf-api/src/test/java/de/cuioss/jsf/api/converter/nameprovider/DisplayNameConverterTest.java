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
package de.cuioss.jsf.api.converter.nameprovider;

import de.cuioss.jsf.api.CoreJsfTestConfiguration;
import de.cuioss.test.generator.Generators;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.converter.AbstractSanitizingConverterTest;
import de.cuioss.test.jsf.converter.TestItems;
import de.cuioss.test.jsf.junit5.EnableJsfEnvironment;
import de.cuioss.uimodel.nameprovider.DisplayName;

@EnableJsfEnvironment
@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class DisplayNameConverterTest extends AbstractSanitizingConverterTest<DisplayNameConverter, DisplayName> {

    @Override
    public void populate(final TestItems<DisplayName> testItems) {
        var any = Generators.letterStrings(1, 5).next();
        testItems.addValidObjectWithStringResult(new DisplayName(any), any);
    }

    @Override
    protected DisplayName createTestObjectWithMaliciousContent(String content) {
        return new DisplayName(content);
    }
}
