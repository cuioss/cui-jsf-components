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
package de.cuioss.jsf.bootstrap.menu;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.components.model.menu.NavigationMenuItemSingleImpl;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.component.AbstractUiComponentTest;
import de.cuioss.test.jsf.config.JsfTestConfiguration;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class NavigationMenuComponentTest extends AbstractUiComponentTest<NavigationMenuComponent> {

    @Test
    void resolveRenderedModelTest() {
        var underTest = new NavigationMenuComponent();
        assertFalse(underTest.resolveRendered());
        underTest.setModel(new NavigationMenuItemSingleImpl(10));
        assertTrue(underTest.resolveRendered());
        underTest.setRendered(false);
        assertFalse(underTest.resolveRendered());
    }

    @Test
    void resolveRenderedTest() {
        var underTest = new NavigationMenuComponent();
        assertFalse(underTest.resolveRendered());
        underTest.setModelItems(immutableList(new NavigationMenuItemSingleImpl(10)));
        assertTrue(underTest.resolveRendered());
        underTest.setRendered(false);
        assertFalse(underTest.resolveRendered());
    }

    @Test
    void resolveModelItemsTest() {
        var underTest = new NavigationMenuComponent();
        assertNull(underTest.resolveModelItems());
        underTest.setModel(new NavigationMenuItemSingleImpl(10));
        assertNotNull(underTest.resolveModelItems());
        underTest.setModel(null);
        underTest.setModelItems(immutableList(new NavigationMenuItemSingleImpl(10)));
        assertNotNull(underTest.resolveModelItems());
    }
}
