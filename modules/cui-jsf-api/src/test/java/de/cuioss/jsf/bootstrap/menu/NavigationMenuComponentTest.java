package de.cuioss.jsf.bootstrap.menu;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.CoreJsfTestConfiguration;
import de.cuioss.jsf.bootstrap.menu.model.NavigationMenuItemSingleImpl;
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
