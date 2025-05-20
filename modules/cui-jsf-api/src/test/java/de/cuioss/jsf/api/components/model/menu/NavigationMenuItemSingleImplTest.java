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
package de.cuioss.jsf.api.components.model.menu;

import static de.cuioss.test.generator.Generators.letterStrings;
import static org.junit.jupiter.api.Assertions.assertEquals;

import de.cuioss.jsf.api.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.junit5.EnableJsfEnvironment;
import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.contracts.VerifyConstructor;
import de.cuioss.test.valueobjects.api.generator.PropertyGenerator;
import org.junit.jupiter.api.Test;

@VerifyConstructor(of = "order")
@PropertyGenerator(NavigationMenuItemGenerator.class)
@EnableJsfEnvironment
@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class NavigationMenuItemSingleImplTest extends ValueObjectTest<NavigationMenuItemSingleImpl> {

    @Test
    void shouldResolveLabelValue() {
        var underTest = new NavigationMenuItemSingleImpl(1);
        var value = letterStrings(1, 10).next();
        underTest.setLabelValue(value);
        assertEquals(value, underTest.getResolvedLabel());
    }
}
