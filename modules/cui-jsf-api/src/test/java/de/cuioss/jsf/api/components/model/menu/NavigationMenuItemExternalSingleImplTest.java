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
package de.cuioss.jsf.api.components.model.menu;

import static de.cuioss.test.generator.Generators.letterStrings;
import static de.cuioss.tools.collect.CollectionLiterals.immutableList;
import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.jsf.api.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.junit5.EnableJsfEnvironment;
import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.contracts.VerifyConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@VerifyConstructor(of = "order")
@EnableJsfEnvironment
@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class NavigationMenuItemExternalSingleImplTest extends ValueObjectTest<NavigationMenuItemExternalSingleImpl> {

    @BeforeEach
    void setUp(ComponentConfigDecorator decorator) {
        CoreJsfTestConfiguration.configureComponents(decorator);
    }

    @Test
    void shouldResolveLabelValue() {
        var underTest = new NavigationMenuItemExternalSingleImpl(1);
        var value = letterStrings(1, 10).next();
        underTest.setLabelValue(value);
        assertEquals(value, underTest.getResolvedLabel());
    }

    @Test
    void shouldResolveTitle() {
        var underTest = new NavigationMenuItemExternalSingleImpl(1);
        var value = letterStrings(1, 10).next();
        underTest.setTitleValue(value);
        assertEquals(value, underTest.getResolvedTitle());
    }

    @Test
    void shouldDetectTitle() {
        var underTest = new NavigationMenuItemExternalSingleImpl(1);
        assertFalse(underTest.isTitleAvailable());
        var value = letterStrings(1, 10).next();
        underTest.setTitleValue(value);
        assertTrue(underTest.isTitleAvailable());
        assertEquals(value, underTest.getResolvedTitle());
    }

    @Test
    void shouldCompareCorrectly() {
        var one = new NavigationMenuItemExternalSingleImpl(1);
        var two = new NavigationMenuItemExternalSingleImpl(2);
        assertEquals(-1, one.compareTo(two));
    }

    @Test
    void shouldDetectActiveView() {
        var one = new NavigationMenuItemExternalSingleImpl(1);
        assertFalse(one.isActive());

        one.setActiveForAdditionalViewId(immutableList("myview"));
        assertFalse(one.isActive());
    }

}
